package com.censat.redd_projects_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "runap")
public class Runap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fid")
    private Integer fid;

    @Column(name = "condicion")
    private String condicion;

    @Column(name = "ap_nombre", nullable = false)
    private String apNombre;

    @Column(name = "ap_categor")
    private String apCategor;

    @Column(columnDefinition = "TEXT")
    private String geometry;

    // Constructors
    public Runap() {}

    public Runap(Integer fid, String condicion, String apNombre, String apCategor, String geometry) {
        this.fid = fid;
        this.condicion = condicion;
        this.apNombre = apNombre;
        this.apCategor = apCategor;
        this.geometry = geometry;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public String getApNombre() {
        return apNombre;
    }

    public void setApNombre(String apNombre) {
        this.apNombre = apNombre;
    }

    public String getApCategor() {
        return apCategor;
    }

    public void setApCategor(String apCategor) {
        this.apCategor = apCategor;
    }

    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }
}
