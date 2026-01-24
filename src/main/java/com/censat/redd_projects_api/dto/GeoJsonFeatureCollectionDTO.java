package com.censat.redd_projects_api.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class GeoJsonFeatureCollectionDTO {

    private String type;
    private String name;
    private CrsDTO crs;
    private List<FeatureDTO> features;

    // Constructors
    public GeoJsonFeatureCollectionDTO() {
    }

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CrsDTO getCrs() {
        return crs;
    }

    public void setCrs(CrsDTO crs) {
        this.crs = crs;
    }

    public List<FeatureDTO> getFeatures() {
        return features;
    }

    public void setFeatures(List<FeatureDTO> features) {
        this.features = features;
    }
}