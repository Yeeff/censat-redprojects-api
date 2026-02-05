package com.censat.redd_projects_api.service;

import com.censat.redd_projects_api.dto.GeoJsonFeatureCollectionDTO;
import com.censat.redd_projects_api.dto.PropertiesDTO;
import com.censat.redd_projects_api.model.TerritorioCampesinoAgroalimentario;
import com.censat.redd_projects_api.repository.TerritorioCampesinoAgroalimentarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TerritorioCampesinoAgroalimentarioService {

    @Autowired
    private TerritorioCampesinoAgroalimentarioRepository territorioCampesinoAgroalimentarioRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public List<TerritorioCampesinoAgroalimentario> saveGeoJsonTerritorioCampesinoAgroalimentarios(GeoJsonFeatureCollectionDTO geoJsonDTO) {
        List<TerritorioCampesinoAgroalimentario> savedTerritorios = new ArrayList<>();

        for (var feature : geoJsonDTO.getFeatures()) {
            try {
                PropertiesDTO properties = feature.getProperties();
                String geometryJson = objectMapper.writeValueAsString(feature.getGeometry());

                String departamento = (String) properties.get("DEPARTAMENTO");
                String municipio = (String) properties.get("MUNICIPIO");
                String tecam = (String) properties.get("TECAM");

                TerritorioCampesinoAgroalimentario territorio = new TerritorioCampesinoAgroalimentario(
                    departamento, municipio, tecam, geometryJson
                );

                savedTerritorios.add(territorioCampesinoAgroalimentarioRepository.save(territorio));
            } catch (Exception e) {
                throw new RuntimeException("Error processing feature: " + e.getMessage(), e);
            }
        }

        return savedTerritorios;
    }

    public Map<String, Object> getAllTerritorioCampesinoAgroalimentariosAsGeoJson() {
        List<TerritorioCampesinoAgroalimentario> territorios = territorioCampesinoAgroalimentarioRepository.findAll();

        Map<String, Object> geoJson = new HashMap<>();
        geoJson.put("type", "FeatureCollection");

        List<Map<String, Object>> features = new ArrayList<>();
        for (TerritorioCampesinoAgroalimentario territorio : territorios) {
            Map<String, Object> feature = new HashMap<>();
            feature.put("type", "Feature");

            Map<String, Object> properties = new HashMap<>();
            properties.put("DEPARTAMENTO", territorio.getDepartamento());
            properties.put("MUNICIPIO", territorio.getMunicipio());
            properties.put("TECAM", territorio.getTecam());
            feature.put("properties", properties);

            try {
                Map<String, Object> geometry = objectMapper.readValue(territorio.getGeometry(), Map.class);
                feature.put("geometry", geometry);
            } catch (Exception e) {
                throw new RuntimeException("Error parsing geometry: " + e.getMessage(), e);
            }

            features.add(feature);
        }

        geoJson.put("features", features);
        return geoJson;
    }
}