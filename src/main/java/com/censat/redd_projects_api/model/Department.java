package com.censat.redd_projects_api.model;


import jakarta.persistence.*;
import org.locationtech.jts.geom.MultiPolygon;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "geometry", columnDefinition = "geometry(MultiPolygon, 4326)")
    private MultiPolygon geometry;

    // Constructors
    public Department() {
    }

    public Department(String name, MultiPolygon geometry) {
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

    public MultiPolygon getGeometry() {
        return geometry;
    }

    public void setGeometry(MultiPolygon geometry) {
        this.geometry = geometry;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", geometry=" + (geometry != null ? "MultiPolygon" : "null") +
                '}';
    }
}
