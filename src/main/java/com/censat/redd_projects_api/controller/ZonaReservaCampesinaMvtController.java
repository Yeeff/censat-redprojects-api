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
@RequestMapping("/api/mvt/zona-reserva-campesina")
public class ZonaReservaCampesinaMvtController {

    private static final Logger logger = LoggerFactory.getLogger(ZonaReservaCampesinaMvtController.class);

    @Autowired
    private DataSource dataSource;

    /**
     * Get vector tiles for Zonas de Reserva Campesina
     *
     * URL format: /api/mvt/zona-reserva-campesina/{z}/{x}/{y}
     * Returns MVT tiles with ZRC boundaries
     * Note: geometry is stored as GeoJSON text, so we use ST_GeomFromGeoJSON
     */
    @GetMapping("/{z}/{x}/{y}")
    public ResponseEntity<byte[]> getZonaReservaCampesinaMvt(
            @PathVariable int z,
            @PathVariable int x,
            @PathVariable int y) {

        logger.info("MVT request (zona-reserva-campesina): z={}, x={}, y={}", z, x, y);

        String sql = """
            SELECT ST_AsMVT(tile, 'zona_reserva_campesina', 4096, 'mvt_geom')
            FROM (
                SELECT 
                    zrc.id,
                    zrc.objectid,
                    zrc.nombre_zon,
                    ST_AsMVTGeom(
                        ST_Transform(ST_GeomFromGeoJSON(zrc.geometry), 3857),
                        ST_TileEnvelope(?, ?, ?),
                        4096, 256, true
                    ) AS mvt_geom
                FROM zona_reserva_campesina zrc
                WHERE zrc.geometry IS NOT NULL
                AND ST_Intersects(
                    ST_Transform(ST_GeomFromGeoJSON(zrc.geometry), 3857),
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

                    logger.info("MVT response (zona-reserva-campesina): {} bytes for tile z={}, x={}, y={}",
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
            logger.error("SQL Error generating MVT tile (zona-reserva-campesina) z={}, x={}, y={}: {}", z, x, y, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
