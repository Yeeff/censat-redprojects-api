package com.censat.redd_projects_api.dto;


import java.util.List;

public class GeometryDTO {

    private String type;
    private List<List<List<List<Double>>>> coordinates;

    // Constructors
    public GeometryDTO() {
    }

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<List<List<List<Double>>>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<List<List<List<Double>>>> coordinates) {
        this.coordinates = coordinates;
    }
}