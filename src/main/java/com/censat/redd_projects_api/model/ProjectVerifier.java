package com.censat.redd_projects_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "project_verifiers")
public class ProjectVerifier {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "verifier_id", nullable = false)
    private Verifier verifier;

    // Constructors
    public ProjectVerifier() {}

    public ProjectVerifier(Project project, Verifier verifier) {
        this.project = project;
        this.verifier = verifier;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }

    public Verifier getVerifier() { return verifier; }
    public void setVerifier(Verifier verifier) { this.verifier = verifier; }
}