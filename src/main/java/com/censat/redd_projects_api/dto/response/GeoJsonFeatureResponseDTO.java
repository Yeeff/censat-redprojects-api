package com.censat.redd_projects_api.dto.response;


public class GeoJsonFeatureResponseDTO {

    private String type = "Feature";
    private DepartmentPropertiesDTO properties;
    private Object geometry;

    public GeoJsonFeatureResponseDTO() {
    }

    public GeoJsonFeatureResponseDTO(DepartmentPropertiesDTO properties, Object geometry) {
        this.properties = properties;
        this.geometry = geometry;
    }

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DepartmentPropertiesDTO getProperties() {
        return properties;
    }

    public void setProperties(DepartmentPropertiesDTO properties) {
        this.properties = properties;
    }

    public Object getGeometry() {
        return geometry;
    }

    public void setGeometry(Object geometry) {
        this.geometry = geometry;
    }
}