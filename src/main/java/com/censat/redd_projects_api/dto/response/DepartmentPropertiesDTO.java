package com.censat.redd_projects_api.dto.response;

public class DepartmentPropertiesDTO {

    private Long id;
    private String name;

    public DepartmentPropertiesDTO() {
    }

    public DepartmentPropertiesDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}