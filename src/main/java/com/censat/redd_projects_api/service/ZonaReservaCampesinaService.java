package com.censat.redd_projects_api.service;

import com.censat.redd_projects_api.dto.GeoJsonFeatureCollectionDTO;
import com.censat.redd_projects_api.model.ZonaReservaCampesina;
import com.censat.redd_projects_api.repository.ZonaReservaCampesinaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ZonaReservaCampesinaService {

    @Autowired
    private ZonaReservaCampesinaRepository zonaReservaCampesinaRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<ZonaReservaCampesina> saveGeoJsonZonasReservaCampesina(GeoJsonFeatureCollectionDTO geoJsonDTO) {
        List<ZonaReservaCampesina> savedZonas = geoJsonDTO.getFeatures().stream()
            .map(feature -> {
                try {
                    String geometryJson = objectMapper.writeValueAsString(feature.getGeometry());
                    ZonaReservaCampesina zona = new ZonaReservaCampesina();
                    
                    // Map properties from GeoJSON
                    Object objectIdObj = feature.getProperties().get("OBJECTID");
                    if (objectIdObj != null) {
                        zona.setObjectId(Integer.parseInt(objectIdObj.toString()));
                    }
                    
                    Object nombreZonObj = feature.getProperties().get("NOMBRE_ZON");
                    if (nombreZonObj != null) {
                        zona.setNombreZon(nombreZonObj.toString());
                    }
                    
                    zona.setGeometry(geometryJson);
                    return zonaReservaCampesinaRepository.save(zona);
                } catch (Exception e) {
                    throw new RuntimeException("Error processing feature: " + e.getMessage());
                }
            })
            .toList();
        return savedZonas;
    }

    public Map<String, Object> getAllZonasReservaCampesinaAsGeoJson() {
        List<ZonaReservaCampesina> zonas = zonaReservaCampesinaRepository.findAll();
        Map<String, Object> geoJson = new HashMap<>();
        geoJson.put("type", "FeatureCollection");
        List<Map<String, Object>> features = zonas.stream()
            .map(zona -> {
                Map<String, Object> feature = new HashMap<>();
                feature.put("type", "Feature");
                Map<String, Object> properties = new HashMap<>();
                properties.put("OBJECTID", zona.getObjectId());
                properties.put("NOMBRE_ZON", zona.getNombreZon());
                feature.put("properties", properties);
                try {
                    feature.put("geometry", objectMapper.readValue(zona.getGeometry(), Map.class));
                } catch (Exception e) {
                    throw new RuntimeException("Error parsing geometry: " + e.getMessage());
                }
                return feature;
            })
            .toList();
        geoJson.put("features", features);
        return geoJson;
    }

    public List<ZonaReservaCampesina> getAllZonasReservaCampesina() {
        return zonaReservaCampesinaRepository.findAll();
    }

    public ZonaReservaCampesina getZonaReservaCampesinaById(Long id) {
        return zonaReservaCampesinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ZonaReservaCampesina not found with id: " + id));
    }

    public ZonaReservaCampesina getZonaReservaCampesinaByNombreZon(String nombreZon) {
        return zonaReservaCampesinaRepository.findByNombreZon(nombreZon)
                .orElseThrow(() -> new RuntimeException("ZonaReservaCampesina not found with nombre_zon: " + nombreZon));
    }

    public void deleteAllZonasReservaCampesina() {
        zonaReservaCampesinaRepository.deleteAll();
    }
}
