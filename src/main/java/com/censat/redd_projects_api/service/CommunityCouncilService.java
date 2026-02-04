package com.censat.redd_projects_api.service;

import com.censat.redd_projects_api.dto.GeoJsonFeatureCollectionDTO;
import com.censat.redd_projects_api.model.CommunityCouncil;
import com.censat.redd_projects_api.repository.CommunityCouncilRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommunityCouncilService {

    @Autowired
    private CommunityCouncilRepository communityCouncilRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public List<CommunityCouncil> saveGeoJsonCommunityCouncils(GeoJsonFeatureCollectionDTO geoJsonDTO) {
        List<CommunityCouncil> councils = new ArrayList<>();

        for (var feature : geoJsonDTO.getFeatures()) {
            try {
                String name = feature.getProperties().getName();
                String geometryJson = objectMapper.writeValueAsString(feature.getGeometry());

                CommunityCouncil council = new CommunityCouncil(name, geometryJson);
                councils.add(communityCouncilRepository.save(council));
            } catch (Exception e) {
                throw new RuntimeException("Error processing feature: " + e.getMessage());
            }
        }

        return councils;
    }

    public Map<String, Object> getAllCommunityCouncilsAsGeoJson() {
        List<CommunityCouncil> councils = communityCouncilRepository.findAll();

        Map<String, Object> geoJson = new HashMap<>();
        geoJson.put("type", "FeatureCollection");

        List<Map<String, Object>> features = new ArrayList<>();
        for (CommunityCouncil council : councils) {
            Map<String, Object> feature = new HashMap<>();
            feature.put("type", "Feature");

            Map<String, Object> properties = new HashMap<>();
            properties.put("name", council.getName());
            feature.put("properties", properties);

            try {
                Map<String, Object> geometry = objectMapper.readValue(council.getGeometry(), Map.class);
                feature.put("geometry", geometry);
            } catch (Exception e) {
                throw new RuntimeException("Error parsing geometry: " + e.getMessage());
            }

            features.add(feature);
        }

        geoJson.put("features", features);
        return geoJson;
    }
}