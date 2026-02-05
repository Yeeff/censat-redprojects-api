package com.censat.redd_projects_api.controller;

import com.censat.redd_projects_api.dto.GeoJsonFeatureCollectionDTO;
import com.censat.redd_projects_api.model.Runap;
import com.censat.redd_projects_api.service.RunapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/runap")
public class RunapController {

    @Autowired
    private RunapService runapService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadGeoJson(@RequestBody GeoJsonFeatureCollectionDTO geoJsonDTO) {
        try {
            List<Runap> savedRunaps = runapService.saveGeoJsonRunaps(geoJsonDTO);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "RUNAP areas saved successfully");
            response.put("count", savedRunaps.size());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error processing GeoJSON");
            error.put("details", e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @GetMapping("/geojson")
    public ResponseEntity<Map<String, Object>> getAllRunapsAsGeoJson() {
        Map<String, Object> geoJson = runapService.getAllRunapsAsGeoJson();
        return ResponseEntity.ok(geoJson);
    }

    @GetMapping
    public ResponseEntity<List<Runap>> getAllRunaps() {
        List<Runap> runaps = runapService.getAllRunaps();
        return ResponseEntity.ok(runaps);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRunapById(@PathVariable Long id) {
        try {
            Runap runap = runapService.getRunapById(id);
            return ResponseEntity.ok(runap);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "RUNAP area not found");
            error.put("details", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getRunapByName(@PathVariable String name) {
        try {
            Runap runap = runapService.getRunapByName(name);
            return ResponseEntity.ok(runap);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "RUNAP area not found");
            error.put("details", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllRunaps() {
        try {
            runapService.deleteAllRunaps();

            Map<String, String> response = new HashMap<>();
            response.put("message", "All RUNAP areas have been deleted");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error deleting RUNAP areas");
            error.put("details", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
