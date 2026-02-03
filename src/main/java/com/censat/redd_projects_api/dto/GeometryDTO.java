package com.censat.redd_projects_api.dto;


import java.util.List;

public class GeometryDTO {

    private String type;
    private Object coordinates; // Cambiar a Object para aceptar cualquier estructura

    public GeometryDTO() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Object coordinates) {
        this.coordinates = coordinates;
    }
}