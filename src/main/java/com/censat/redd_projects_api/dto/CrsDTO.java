package com.censat.redd_projects_api.dto;

public class CrsDTO {

    private String type;
    private CrsPropertiesDTO properties;

    // Constructors
    public CrsDTO() {
    }

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CrsPropertiesDTO getProperties() {
        return properties;
    }

    public void setProperties(CrsPropertiesDTO properties) {
        this.properties = properties;
    }
}
