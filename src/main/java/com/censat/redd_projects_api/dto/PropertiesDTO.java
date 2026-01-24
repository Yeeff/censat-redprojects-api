package com.censat.redd_projects_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PropertiesDTO {

    @JsonProperty("DeNombre")
    private String deNombre;

    // Constructors
    public PropertiesDTO() {
    }

    // Getters and Setters
    public String getDeNombre() {
        return deNombre;
    }

    public void setDeNombre(String deNombre) {
        this.deNombre = deNombre;
    }
}
