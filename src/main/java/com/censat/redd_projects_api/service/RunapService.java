package com.censat.redd_projects_api.service;

import com.censat.redd_projects_api.dto.GeoJsonFeatureCollectionDTO;
import com.censat.redd_projects_api.model.Runap;
import com.censat.redd_projects_api.repository.RunapRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RunapService {

    @Autowired
    private RunapRepository runapRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Runap> saveGeoJsonRunaps(GeoJsonFeatureCollectionDTO geoJsonDTO) {
        List<Runap> savedRunaps = geoJsonDTO.getFeatures().stream()
            .map(feature -> {
                try {
                    String geometryJson = objectMapper.writeValueAsString(feature.getGeometry());
                    Runap runap = new Runap();
                    
                    // Map properties from GeoJSON
                    Object fidObj = feature.getProperties().get("FID");
                    if (fidObj != null) {
                        runap.setFid(Integer.parseInt(fidObj.toString()));
                    }
                    
                    Object condicionObj = feature.getProperties().get("condicion");
                    if (condicionObj != null) {
                        runap.setCondicion(condicionObj.toString());
                    } else {
                        runap.setCondicion("REGISTRADA"); // Default value
                    }
                    
                    Object apNombreObj = feature.getProperties().get("ap_nombre");
                    if (apNombreObj != null) {
                        runap.setApNombre(apNombreObj.toString());
                    }
                    
                    Object apCategorObj = feature.getProperties().get("ap_categor");
                    if (apCategorObj != null) {
                        runap.setApCategor(apCategorObj.toString());
                    }
                    
                    runap.setGeometry(geometryJson);
                    return runapRepository.save(runap);
                } catch (Exception e) {
                    throw new RuntimeException("Error processing feature: " + e.getMessage());
                }
            })
            .toList();
        return savedRunaps;
    }

    public Map<String, Object> getAllRunapsAsGeoJson() {
        List<Runap> runaps = runapRepository.findAll();
        Map<String, Object> geoJson = new HashMap<>();
        geoJson.put("type", "FeatureCollection");
        List<Map<String, Object>> features = runaps.stream()
            .map(runap -> {
                Map<String, Object> feature = new HashMap<>();
                feature.put("type", "Feature");
                Map<String, Object> properties = new HashMap<>();
                properties.put("FID", runap.getFid());
                properties.put("condicion", runap.getCondicion());
                properties.put("ap_nombre", runap.getApNombre());
                properties.put("ap_categor", runap.getApCategor());
                feature.put("properties", properties);
                try {
                    feature.put("geometry", objectMapper.readValue(runap.getGeometry(), Map.class));
                } catch (Exception e) {
                    throw new RuntimeException("Error parsing geometry: " + e.getMessage());
                }
                return feature;
            })
            .toList();
        geoJson.put("features", features);
        return geoJson;
    }

    public List<Runap> getAllRunaps() {
        return runapRepository.findAll();
    }

    public Runap getRunapById(Long id) {
        return runapRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Runap not found with id: " + id));
    }

    public Runap getRunapByName(String name) {
        return runapRepository.findByApNombre(name)
                .orElseThrow(() -> new RuntimeException("Runap not found with name: " + name));
    }

    public void deleteAllRunaps() {
        runapRepository.deleteAll();
    }
}
