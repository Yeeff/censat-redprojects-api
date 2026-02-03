package com.censat.redd_projects_api.service;

import com.censat.redd_projects_api.dto.GeoJsonFeatureCollectionDTO;
import com.censat.redd_projects_api.model.IndigenousReserve;
import com.censat.redd_projects_api.repository.IndigenousReserveRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IndigenousReserveService {

    @Autowired
    private IndigenousReserveRepository indigenousReserveRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<IndigenousReserve> saveGeoJsonIndigenousReserves(GeoJsonFeatureCollectionDTO geoJsonDTO) {
        List<IndigenousReserve> savedReserves = geoJsonDTO.getFeatures().stream()
            .map(feature -> {
                try {
                    String geometryJson = objectMapper.writeValueAsString(feature.getGeometry());
                    IndigenousReserve reserve = new IndigenousReserve();
                    reserve.setName((String) feature.getProperties().get("NOMBRE"));
                    reserve.setPueblo((String) feature.getProperties().get("PUEBLO"));
                    reserve.setGeometry(geometryJson);
                    return indigenousReserveRepository.save(reserve);
                } catch (Exception e) {
                    throw new RuntimeException("Error processing feature: " + e.getMessage());
                }
            })
            .toList();
        return savedReserves;
    }

    public Map<String, Object> getAllIndigenousReservesAsGeoJson() {
        List<IndigenousReserve> reserves = indigenousReserveRepository.findAll();
        Map<String, Object> geoJson = new HashMap<>();
        geoJson.put("type", "FeatureCollection");
        List<Map<String, Object>> features = reserves.stream()
            .map(reserve -> {
                Map<String, Object> feature = new HashMap<>();
                feature.put("type", "Feature");
                Map<String, Object> properties = new HashMap<>();
                properties.put("NOMBRE", reserve.getName());
                properties.put("PUEBLO", reserve.getPueblo());
                feature.put("properties", properties);
                try {
                    feature.put("geometry", objectMapper.readValue(reserve.getGeometry(), Map.class));
                } catch (Exception e) {
                    throw new RuntimeException("Error parsing geometry: " + e.getMessage());
                }
                return feature;
            })
            .toList();
        geoJson.put("features", features);
        return geoJson;
    }

    
}