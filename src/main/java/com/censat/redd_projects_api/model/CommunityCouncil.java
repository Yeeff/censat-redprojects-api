package com.censat.redd_projects_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "community_councils")
public class CommunityCouncil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String geometry;

    // Constructors
    public CommunityCouncil() {}

    public CommunityCouncil(String name, String geometry) {
        this.name = name;
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

    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }
}