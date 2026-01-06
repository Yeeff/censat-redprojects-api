package com.censat.redd_projects_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "certifiers")
public class CertifierEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    // Constructors
    public CertifierEntity() {}

    public CertifierEntity(String name) {
        this.name = name;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}