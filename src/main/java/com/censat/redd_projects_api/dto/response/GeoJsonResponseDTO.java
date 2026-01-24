package com.censat.redd_projects_api.dto.response;

import java.util.List;

public class GeoJsonResponseDTO {

    private String type = "FeatureCollection";
    private List<GeoJsonFeatureResponseDTO> features;

    public GeoJsonResponseDTO() {
    }

    public GeoJsonResponseDTO(List<GeoJsonFeatureResponseDTO> features) {
        this.features = features;
    }

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<GeoJsonFeatureResponseDTO> getFeatures() {
        return features;
    }

    public void setFeatures(List<GeoJsonFeatureResponseDTO> features) {
        this.features = features;
    }
}