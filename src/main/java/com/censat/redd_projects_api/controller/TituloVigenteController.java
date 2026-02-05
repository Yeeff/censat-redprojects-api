package com.censat.redd_projects_api.controller;

import com.censat.redd_projects_api.dto.GeoJsonFeatureCollectionDTO;
import com.censat.redd_projects_api.model.TituloVigente;
import com.censat.redd_projects_api.service.TituloVigenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/titulo-vigente")
public class TituloVigenteController {

    @Autowired
    private TituloVigenteService tituloVigenteService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadGeoJson(@RequestBody GeoJsonFeatureCollectionDTO geoJsonDTO) {
        try {
            List<TituloVigente> savedTitulos = tituloVigenteService.saveGeoJsonTitulosVigentes(geoJsonDTO);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Titulos vigentes saved successfully");
            response.put("count", savedTitulos.size());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error processing GeoJSON");
            error.put("details", e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @GetMapping("/geojson")
    public ResponseEntity<Map<String, Object>> getAllTitulosVigentesAsGeoJson() {
        Map<String, Object> geoJson = tituloVigenteService.getAllTitulosVigentesAsGeoJson();
        return ResponseEntity.ok(geoJson);
    }

    @GetMapping
    public ResponseEntity<List<TituloVigente>> getAllTitulosVigentes() {
        List<TituloVigente> titulos = tituloVigenteService.getAllTitulosVigentes();
        return ResponseEntity.ok(titulos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTituloVigenteById(@PathVariable Long id) {
        try {
            TituloVigente titulo = tituloVigenteService.getTituloVigenteById(id);
            return ResponseEntity.ok(titulo);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "TituloVigente not found");
            error.put("details", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @GetMapping("/codigo-exp/{codigoExp}")
    public ResponseEntity<?> getTituloVigenteByCodigoExp(@PathVariable String codigoExp) {
        try {
            TituloVigente titulo = tituloVigenteService.getTituloVigenteByCodigoExp(codigoExp);
            return ResponseEntity.ok(titulo);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "TituloVigente not found");
            error.put("details", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllTitulosVigentes() {
        try {
            tituloVigenteService.deleteAllTitulosVigentes();

            Map<String, String> response = new HashMap<>();
            response.put("message", "All TitulosVigentes have been deleted");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error deleting TitulosVigentes");
            error.put("details", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
