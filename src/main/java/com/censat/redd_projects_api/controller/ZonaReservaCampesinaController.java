package com.censat.redd_projects_api.controller;

import com.censat.redd_projects_api.dto.GeoJsonFeatureCollectionDTO;
import com.censat.redd_projects_api.model.ZonaReservaCampesina;
import com.censat.redd_projects_api.service.ZonaReservaCampesinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/zona-reserva-campesina")
public class ZonaReservaCampesinaController {

    @Autowired
    private ZonaReservaCampesinaService zonaReservaCampesinaService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadGeoJson(@RequestBody GeoJsonFeatureCollectionDTO geoJsonDTO) {
        try {
            List<ZonaReservaCampesina> savedZonas = zonaReservaCampesinaService.saveGeoJsonZonasReservaCampesina(geoJsonDTO);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Zonas de Reserva Campesina saved successfully");
            response.put("count", savedZonas.size());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error processing GeoJSON");
            error.put("details", e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @GetMapping("/geojson")
    public ResponseEntity<Map<String, Object>> getAllZonasReservaCampesinaAsGeoJson() {
        Map<String, Object> geoJson = zonaReservaCampesinaService.getAllZonasReservaCampesinaAsGeoJson();
        return ResponseEntity.ok(geoJson);
    }

    @GetMapping
    public ResponseEntity<List<ZonaReservaCampesina>> getAllZonasReservaCampesina() {
        List<ZonaReservaCampesina> zonas = zonaReservaCampesinaService.getAllZonasReservaCampesina();
        return ResponseEntity.ok(zonas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getZonaReservaCampesinaById(@PathVariable Long id) {
        try {
            ZonaReservaCampesina zona = zonaReservaCampesinaService.getZonaReservaCampesinaById(id);
            return ResponseEntity.ok(zona);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "ZonaReservaCampesina not found");
            error.put("details", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @GetMapping("/nombre-zon/{nombreZon}")
    public ResponseEntity<?> getZonaReservaCampesinaByNombreZon(@PathVariable String nombreZon) {
        try {
            ZonaReservaCampesina zona = zonaReservaCampesinaService.getZonaReservaCampesinaByNombreZon(nombreZon);
            return ResponseEntity.ok(zona);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "ZonaReservaCampesina not found");
            error.put("details", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllZonasReservaCampesina() {
        try {
            zonaReservaCampesinaService.deleteAllZonasReservaCampesina();

            Map<String, String> response = new HashMap<>();
            response.put("message", "All Zonas de Reserva Campesina have been deleted");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error deleting Zonas de Reserva Campesina");
            error.put("details", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
