package com.censat.redd_projects_api.service;

import com.censat.redd_projects_api.dto.GeoJsonFeatureCollectionDTO;
import com.censat.redd_projects_api.model.TituloVigente;
import com.censat.redd_projects_api.repository.TituloVigenteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TituloVigenteService {

    @Autowired
    private TituloVigenteRepository tituloVigenteRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<TituloVigente> saveGeoJsonTitulosVigentes(GeoJsonFeatureCollectionDTO geoJsonDTO) {
        List<TituloVigente> savedTitulos = geoJsonDTO.getFeatures().stream()
            .map(feature -> {
                try {
                    String geometryJson = objectMapper.writeValueAsString(feature.getGeometry());
                    TituloVigente titulo = new TituloVigente();
                    
                    // Map properties from GeoJSON
                    Object fidObj = feature.getProperties().get("FID");
                    if (fidObj != null) {
                        titulo.setFid(Integer.parseInt(fidObj.toString()));
                    }
                    
                    Object codigoExpObj = feature.getProperties().get("CODIGO_EXP");
                    if (codigoExpObj != null) {
                        titulo.setCodigoExp(codigoExpObj.toString());
                    }
                    
                    Object tituloEstObj = feature.getProperties().get("TITULO_EST");
                    if (tituloEstObj != null) {
                        titulo.setTituloEst(tituloEstObj.toString());
                    }
                    
                    Object modalidadObj = feature.getProperties().get("MODALIDAD");
                    if (modalidadObj != null) {
                        titulo.setModalidad(modalidadObj.toString());
                    }
                    
                    Object municipiosObj = feature.getProperties().get("MUNICIPIOS");
                    if (municipiosObj != null) {
                        titulo.setMunicipios(municipiosObj.toString());
                    }
                    
                    Object departamenObj = feature.getProperties().get("DEPARTAMEN");
                    if (departamenObj != null) {
                        titulo.setDepartamen(departamenObj.toString());
                    }
                    
                    Object areaHaObj = feature.getProperties().get("AREA_HA");
                    if (areaHaObj != null) {
                        titulo.setAreaHa(Double.parseDouble(areaHaObj.toString()));
                    }
                    
                    Object clasificacObj = feature.getProperties().get("CLASIFICAC");
                    if (clasificacObj != null) {
                        titulo.setClasificac(clasificacObj.toString());
                    }
                    
                    Object etapaObj = feature.getProperties().get("ETAPA");
                    if (etapaObj != null) {
                        titulo.setEtapa(etapaObj.toString());
                    }
                    
                    Object mineralesObj = feature.getProperties().get("MINERALES");
                    if (mineralesObj != null) {
                        titulo.setMinerales(mineralesObj.toString());
                    }
                    
                    titulo.setGeometry(geometryJson);
                    return tituloVigenteRepository.save(titulo);
                } catch (Exception e) {
                    throw new RuntimeException("Error processing feature: " + e.getMessage());
                }
            })
            .toList();
        return savedTitulos;
    }

    public Map<String, Object> getAllTitulosVigentesAsGeoJson() {
        List<TituloVigente> titulos = tituloVigenteRepository.findAll();
        Map<String, Object> geoJson = new HashMap<>();
        geoJson.put("type", "FeatureCollection");
        List<Map<String, Object>> features = titulos.stream()
            .map(titulo -> {
                Map<String, Object> feature = new HashMap<>();
                feature.put("type", "Feature");
                Map<String, Object> properties = new HashMap<>();
                properties.put("FID", titulo.getFid());
                properties.put("CODIGO_EXP", titulo.getCodigoExp());
                properties.put("TITULO_EST", titulo.getTituloEst());
                properties.put("MODALIDAD", titulo.getModalidad());
                properties.put("MUNICIPIOS", titulo.getMunicipios());
                properties.put("DEPARTAMEN", titulo.getDepartamen());
                properties.put("AREA_HA", titulo.getAreaHa());
                properties.put("CLASIFICAC", titulo.getClasificac());
                properties.put("ETAPA", titulo.getEtapa());
                properties.put("MINERALES", titulo.getMinerales());
                feature.put("properties", properties);
                try {
                    feature.put("geometry", objectMapper.readValue(titulo.getGeometry(), Map.class));
                } catch (Exception e) {
                    throw new RuntimeException("Error parsing geometry: " + e.getMessage());
                }
                return feature;
            })
            .toList();
        geoJson.put("features", features);
        return geoJson;
    }

    public List<TituloVigente> getAllTitulosVigentes() {
        return tituloVigenteRepository.findAll();
    }

    public TituloVigente getTituloVigenteById(Long id) {
        return tituloVigenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TituloVigente not found with id: " + id));
    }

    public TituloVigente getTituloVigenteByCodigoExp(String codigoExp) {
        return tituloVigenteRepository.findByCodigoExp(codigoExp)
                .orElseThrow(() -> new RuntimeException("TituloVigente not found with codigo_exp: " + codigoExp));
    }

    public void deleteAllTitulosVigentes() {
        tituloVigenteRepository.deleteAll();
    }
}
