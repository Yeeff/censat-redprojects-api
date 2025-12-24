package com.censat.redd_projects_api.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "verifiers")
public class Verifier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "verifier", cascade = CascadeType.ALL)
    private List<ProjectVerifier> projectVerifiers;

    // Constructors
    public Verifier() {}

    public Verifier(String name) {
        this.name = name;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<ProjectVerifier> getProjectVerifiers() { return projectVerifiers; }
    public void setProjectVerifiers(List<ProjectVerifier> projectVerifiers) { this.projectVerifiers = projectVerifiers; }
}