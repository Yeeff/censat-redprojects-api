package com.censat.redd_projects_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "zona_reserva_campesina")
public class ZonaReservaCampesina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "objectid")
    private Integer objectId;

    @Column(name = "nombre_zon", nullable = false)
    private String nombreZon;

    @Column(columnDefinition = "TEXT")
    private String geometry;

    // Constructors
    public ZonaReservaCampesina() {}

    public ZonaReservaCampesina(Integer objectId, String nombreZon, String geometry) {
        this.objectId = objectId;
        this.nombreZon = nombreZon;
        this.geometry = geometry;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public String getNombreZon() {
        return nombreZon;
    }

    public void setNombreZon(String nombreZon) {
        this.nombreZon = nombreZon;
    }

    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }
}
