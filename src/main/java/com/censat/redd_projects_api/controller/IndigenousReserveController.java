package com.censat.redd_projects_api.controller;

import com.censat.redd_projects_api.dto.GeoJsonFeatureCollectionDTO;
import com.censat.redd_projects_api.model.IndigenousReserve;
import com.censat.redd_projects_api.service.IndigenousReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/indigenous-reserves")
public class IndigenousReserveController {

    @Autowired
    private IndigenousReserveService indigenousReserveService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadGeoJson(@RequestBody GeoJsonFeatureCollectionDTO geoJsonDTO) {
        try {
            List<IndigenousReserve> savedReserves = indigenousReserveService.saveGeoJsonIndigenousReserves(geoJsonDTO);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Resguardos indígenas guardados exitosamente");
            response.put("count", savedReserves.size());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al procesar el GeoJSON");
            error.put("details", e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    // ENDPOINT que devuelve GeoJSON estándar
    @GetMapping("/geojson")
    public ResponseEntity<Map<String, Object>> getAllIndigenousReservesAsGeoJson() {
        Map<String, Object> geoJson = indigenousReserveService.getAllIndigenousReservesAsGeoJson();
        return ResponseEntity.ok(geoJson);
    }

}