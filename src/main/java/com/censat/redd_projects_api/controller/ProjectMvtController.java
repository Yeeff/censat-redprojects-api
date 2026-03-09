package com.censat.redd_projects_api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RestController
@RequestMapping("/api/mvt/projects")
public class ProjectMvtController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectMvtController.class);

    @Autowired
    private DataSource dataSource;

    /**
     * Debug endpoint to check which tile contains a specific project
     */
    @GetMapping("/debug/tile/{projectId}")
    public ResponseEntity<String> debugTile(@PathVariable Long projectId) {
        StringBuilder sb = new StringBuilder();
        sb.append("Project tile coordinates:\n");

        String centroidSql = "SELECT id, name, " +
                "ST_SRID(location_geometry) as srid, " +
                "ST_X(ST_Centroid(ST_SetSRID(location_geometry, 4326))) as lon, " +
                "ST_Y(ST_Centroid(ST_SetSRID(location_geometry, 4326))) as lat " +
                "FROM projects WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(centroidSql)) {

            stmt.setLong(1, projectId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    double lon = rs.getDouble("lon");
                    double lat = rs.getDouble("lat");
                    int srid = rs.getInt("srid");
                    sb.append(String.format("Project: %s\n", rs.getString("name")));
                    sb.append(String.format("SRID stored: %d\n", srid));
                    sb.append(String.format("Centroid: lon=%.4f, lat=%.4f\n\n", lon, lat));

                    for (int z = 4; z <= 12; z++) {
                        int[] tile = getTileCoordinates(lon, lat, z);
                        sb.append(String.format("Zoom %d: /api/mvt/projects/%d/%d/%d\n",
                                z, z, tile[0], tile[1]));
                    }
                } else {
                    sb.append("Project not found");
                }
            }
        } catch (SQLException e) {
            logger.error("Debug query error", e);
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }

        return ResponseEntity.ok(sb.toString());
    }

    private int[] getTileCoordinates(double lon, double lat, int zoom) {
        int n = 1 << zoom;
        int x = (int) Math.floor((lon + 180.0) / 360.0 * n);
        double latRad = Math.toRadians(lat);
        double mercatorY = Math.log(Math.tan(Math.PI / 4 + latRad / 2));
        int y = (int) Math.floor((1 - mercatorY / Math.PI) / 2 * n);
        return new int[]{x, y};
    }

    /**
     * Get vector tiles for projects
     *
     * URL format: /api/mvt/projects/{z}/{x}/{y}
     * Optional filters: statusId, departamento
     * NOTE: geometries are stored with SRID=0, ST_SetSRID(geom, 4326) is applied before processing
     */
    @GetMapping("/{z}/{x}/{y}")
    public ResponseEntity<byte[]> getProjectMvt(
            @PathVariable int z,
            @PathVariable int x,
            @PathVariable int y,
            @RequestParam(required = false) Long statusId,
            @RequestParam(required = false) String departamento) {

        logger.info("MVT request: z={}, x={}, y={}, statusId={}, departamento={}", z, x, y, statusId, departamento);

        // ST_TileEnvelope requires PostGIS >= 3.0
        // Uses ST_Transform to convert geometries from 4326 to 3857 (Web Mercator) for MVT
        StringBuilder sqlBuilder = new StringBuilder("""
                SELECT ST_AsMVT(tile, 'projects', 4096, 'mvt_geom')
                FROM (
                    SELECT
                        p.id,
                        p.name,
                        LOWER(p.name) AS name_lower,
                        p.location,
                        p.departamento,
                        p.description,
                        p.project_type,
                        p.hectares,
                        p.carbon_rating,
                        p.contract_type,
                        p.duration,
                        COALESCE(s.name, '') AS status,
                        p.status_id,
                        COALESCE(c.name, '') AS certifier,
                        p.certifier_id,
                        COALESCE(v.name, '') AS validator,
                        p.validator_id,
                        p.start_date::text,
                        p.end_date::text,
                        p.registration_date::text,
                        ST_AsMVTGeom(
                            ST_Transform(ST_SetSRID(p.location_geometry, 4326), 3857),
                            ST_TileEnvelope(?, ?, ?),
                            4096, 256, true
                        ) AS mvt_geom
                    FROM projects p
                    LEFT JOIN statuses s ON p.status_id = s.id
                    LEFT JOIN certifiers c ON p.certifier_id = c.id
                    LEFT JOIN validators v ON p.validator_id = v.id
                    WHERE p.location_geometry IS NOT NULL
                    AND ST_Intersects(
                        ST_Transform(ST_SetSRID(p.location_geometry, 4326), 3857),
                        ST_TileEnvelope(?, ?, ?)
                    )
                """);

        if (statusId != null) {
            sqlBuilder.append("AND p.status_id = ? ");
        }
        if (departamento != null && !departamento.isEmpty()) {
            sqlBuilder.append("AND p.departamento = ? ");
        }

        sqlBuilder.append(") AS tile");

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sqlBuilder.toString())) {

            int i = 1;

            // ST_TileEnvelope for ST_AsMVTGeom
            stmt.setInt(i++, z);
            stmt.setInt(i++, x);
            stmt.setInt(i++, y);

            // ST_TileEnvelope for ST_Intersects
            stmt.setInt(i++, z);
            stmt.setInt(i++, x);
            stmt.setInt(i++, y);

            if (statusId != null) {
                stmt.setLong(i++, statusId);
            }
            if (departamento != null && !departamento.isEmpty()) {
                stmt.setString(i++, departamento);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    byte[] mvtBytes = rs.getBytes(1);

                    logger.info("MVT response: {} bytes for tile z={}, x={}, y={}",
                            mvtBytes != null ? mvtBytes.length : 0, z, x, y);

                    if (mvtBytes == null || mvtBytes.length == 0) {
                        return ResponseEntity.noContent().build();
                    }

                    HttpHeaders headers = new HttpHeaders();
                    headers.set("Content-Type", "application/vnd.mapbox-vector-tile");
                    headers.set("Cache-Control", "public, max-age=3600");

                    return ResponseEntity.ok()
                            .headers(headers)
                            .body(mvtBytes);
                }
            }

            return ResponseEntity.noContent().build();

        } catch (SQLException e) {
            logger.error("SQL Error generating MVT tile z={}, x={}, y={}: {}", z, x, y, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
}