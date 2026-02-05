package com.censat.redd_projects_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "territorios_campesinos_agroalimentarios")
public class TerritorioCampesinoAgroalimentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String departamento;

    @Column(nullable = false)
    private String municipio;

    @Column(nullable = false)
    private String tecam;

    @Column(columnDefinition = "TEXT")
    private String geometry;

    // Constructors
    public TerritorioCampesinoAgroalimentario() {}

    public TerritorioCampesinoAgroalimentario(String departamento, String municipio, String tecam, String geometry) {
        this.departamento = departamento;
        this.municipio = municipio;
        this.tecam = tecam;
        this.geometry = geometry;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getTecam() {
        return tecam;
    }

    public void setTecam(String tecam) {
        this.tecam = tecam;
    }

    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }
}