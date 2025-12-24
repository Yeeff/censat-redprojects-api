package com.censat.redd_projects_api.model;

import jakarta.persistence.*;
import org.locationtech.jts.geom.Geometry;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String location;

    @Column
    private String departamento;  // Added for filtering

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "pdd_start_date")
    private LocalDate pddStartDate;

    @Column(name = "pdd_end_date")
    private LocalDate pddEndDate;

    @Column(precision = 15, scale = 2)
    private BigDecimal hectares;

    @ElementCollection
    @CollectionTable(name = "project_communities", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "community")
    private List<String> communitiesInvolved;

    @Column(length = 1000)
    private String developerOrganizations;

    @Column(length = 1000)
    private String collaboratingOrganizations;

    @Column(length = 500)
    private String validator;

    @ElementCollection
    @CollectionTable(name = "project_verifiers", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "verifier")
    private List<String> verifiers;

    @Enumerated(EnumType.STRING)
    private Certifier certifier;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Column(name = "query_date")
    private LocalDate queryDate;

    @Column(length = 2000)
    private String link;

    @Column(columnDefinition = "geometry")
    private Geometry locationGeometry;

    // Constructors
    public Project() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public ProjectStatus getStatus() { return status; }
    public void setStatus(ProjectStatus status) { this.status = status; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public LocalDate getPddStartDate() { return pddStartDate; }
    public void setPddStartDate(LocalDate pddStartDate) { this.pddStartDate = pddStartDate; }

    public LocalDate getPddEndDate() { return pddEndDate; }
    public void setPddEndDate(LocalDate pddEndDate) { this.pddEndDate = pddEndDate; }

    public BigDecimal getHectares() { return hectares; }
    public void setHectares(BigDecimal hectares) { this.hectares = hectares; }

    public List<String> getCommunitiesInvolved() { return communitiesInvolved; }
    public void setCommunitiesInvolved(List<String> communitiesInvolved) { this.communitiesInvolved = communitiesInvolved; }

    public String getDeveloperOrganizations() { return developerOrganizations; }
    public void setDeveloperOrganizations(String developerOrganizations) { this.developerOrganizations = developerOrganizations; }

    public String getCollaboratingOrganizations() { return collaboratingOrganizations; }
    public void setCollaboratingOrganizations(String collaboratingOrganizations) { this.collaboratingOrganizations = collaboratingOrganizations; }

    public String getValidator() { return validator; }
    public void setValidator(String validator) { this.validator = validator; }

    public List<String> getVerifiers() { return verifiers; }
    public void setVerifiers(List<String> verifiers) { this.verifiers = verifiers; }

    public Certifier getCertifier() { return certifier; }
    public void setCertifier(Certifier certifier) { this.certifier = certifier; }

    public LocalDate getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDate registrationDate) { this.registrationDate = registrationDate; }

    public LocalDate getQueryDate() { return queryDate; }
    public void setQueryDate(LocalDate queryDate) { this.queryDate = queryDate; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    public Geometry getLocationGeometry() { return locationGeometry; }
    public void setLocationGeometry(Geometry locationGeometry) { this.locationGeometry = locationGeometry; }
}