package com.censat.redd_projects_api.dto;

public class FeatureDTO {

    private String type;
    private PropertiesDTO properties;
    private GeometryDTO geometry;

    // Constructors
    public FeatureDTO() {
    }

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PropertiesDTO getProperties() {
        return properties;
    }

    public void setProperties(PropertiesDTO properties) {
        this.properties = properties;
    }

    public GeometryDTO getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryDTO geometry) {
        this.geometry = geometry;
    }
}
