package com.censat.redd_projects_api.controller;

import com.censat.redd_projects_api.dto.GeoJsonFeatureCollectionDTO;
import com.censat.redd_projects_api.model.TerritorioCampesinoAgroalimentario;
import com.censat.redd_projects_api.service.TerritorioCampesinoAgroalimentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/territorios-campesinos-agroalimentarios")
public class TerritorioCampesinoAgroalimentarioController {

    @Autowired
    private TerritorioCampesinoAgroalimentarioService territorioCampesinoAgroalimentarioService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadGeoJson(@RequestBody GeoJsonFeatureCollectionDTO geoJsonDTO) {
        try {
            List<TerritorioCampesinoAgroalimentario> savedTerritorios = territorioCampesinoAgroalimentarioService.saveGeoJsonTerritorioCampesinoAgroalimentarios(geoJsonDTO);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Territorios campesinos agroalimentarios guardados exitosamente");
            response.put("count", savedTerritorios.size());

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
    public ResponseEntity<Map<String, Object>> getAllTerritorioCampesinoAgroalimentariosAsGeoJson() {
        Map<String, Object> geoJson = territorioCampesinoAgroalimentarioService.getAllTerritorioCampesinoAgroalimentariosAsGeoJson();
        return ResponseEntity.ok(geoJson);
    }

}