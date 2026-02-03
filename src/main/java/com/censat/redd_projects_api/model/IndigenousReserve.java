package com.censat.redd_projects_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "indigenous_reserves")
public class IndigenousReserve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String pueblo;

    @Column(columnDefinition = "TEXT")
    private String geometry;

    // Constructors
    public IndigenousReserve() {}

    public IndigenousReserve(String name, String pueblo, String geometry) {
        this.name = name;
        this.pueblo = pueblo;
        this.geometry = geometry;
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

    public String getPueblo() {
        return pueblo;
    }

    public void setPueblo(String pueblo) {
        this.pueblo = pueblo;
    }

    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }
}