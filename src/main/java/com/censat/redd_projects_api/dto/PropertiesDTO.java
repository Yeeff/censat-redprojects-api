package com.censat.redd_projects_api.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class PropertiesDTO {

    @JsonProperty("DeNombre")
    private String deNombre;

    // Mapa dinámico para cualquier otra propiedad
    private Map<String, Object> additionalProperties = new HashMap<>();

    // Constructors
    public PropertiesDTO() {
    }

    // Este método captura cualquier propiedad JSON que no esté mapeada explícitamente
    @JsonAnySetter
    public void setAdditionalProperty(String key, Object value) {
        this.additionalProperties.put(key, value);
    }

    // Método útil para obtener el nombre sin importar el campo
    public String getName() {
        if (deNombre != null) return deNombre;
        return (String) additionalProperties.get("NOMBRE");
    }

    // Getters and Setters
    public String getDeNombre() {
        return deNombre;
    }

    public void setDeNombre(String deNombre) {
        this.deNombre = deNombre;
    }

    public Object get(String key) {
        return additionalProperties.get(key);
    }
}
