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
@RequestMapping("/api/mvt/departments")
public class DepartmentMvtController {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentMvtController.class);

    @Autowired
    private DataSource dataSource;

    /**
     * Get vector tiles for departments
     *
     * URL format: /api/mvt/departments/{z}/{x}/{y}
     * Returns MVT tiles with department boundaries
     */
    @GetMapping("/{z}/{x}/{y}")
    public ResponseEntity<byte[]> getDepartmentMvt(
            @PathVariable int z,
            @PathVariable int x,
            @PathVariable int y) {

        logger.info("MVT request (departments): z={}, x={}, y={}", z, x, y);

        String sql = """
            SELECT ST_AsMVT(tile, 'departments', 4096, 'mvt_geom')
            FROM (
                SELECT 
                    d.id,
                    d.name,
                    ST_AsMVTGeom(
                        ST_Transform(ST_SetSRID(d.geometry, 4326), 3857),
                        ST_TileEnvelope(?, ?, ?),
                        4096, 256, true
                    ) AS mvt_geom
                FROM departments d
                WHERE d.geometry IS NOT NULL
                AND ST_Intersects(
                    ST_Transform(ST_SetSRID(d.geometry, 4326), 3857),
                    ST_TileEnvelope(?, ?, ?)
                )
            ) AS tile
            """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            int i = 1;

            // ST_TileEnvelope for ST_AsMVTGeom
            stmt.setInt(i++, z);
            stmt.setInt(i++, x);
            stmt.setInt(i++, y);

            // ST_TileEnvelope for ST_Intersects
            stmt.setInt(i++, z);
            stmt.setInt(i++, x);
            stmt.setInt(i++, y);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    byte[] mvtBytes = rs.getBytes(1);

                    logger.info("MVT response (departments): {} bytes for tile z={}, x={}, y={}",
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
            logger.error("SQL Error generating MVT tile (departments) z={}, x={}, y={}: {}", z, x, y, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
