package com.censat.redd_projects_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKTReader;
import org.wololo.jts2geojson.GeoJSONWriter;
import java.util.List;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String location;

    @Column
    private String departamento;  // Added for filtering

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id")
    private Status status;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "pdd_start_date")
    private LocalDate pddStartDate;

    @Column(name = "pdd_end_date")
    private LocalDate pddEndDate;

    @Column(columnDefinition = "TEXT")
    private String hectares;

    @Column(columnDefinition = "TEXT")
    private String communitiesInvolved;

    @Column(columnDefinition = "TEXT")
    private String developerOrganizations;

    @Column(length = 500)
    private String projectType;

    @Column(length = 100)
    private String duration;

    @Column(columnDefinition = "TEXT")
    private String collaboratingOrganizations;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "validator_id")
    private Validator validator;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ProjectVerifier> projectVerifiers;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "certifier_id")
    private CertifierEntity certifier;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Column(name = "query_date")
    private LocalDate queryDate;

    @Column(length = 2000)
    private String link;

    // Simple string fields for validator and verifiers (for simplified usage)
    @Column(columnDefinition = "TEXT")
    private String validatorString;

    @Column(columnDefinition = "TEXT")
    private String verifiersString;

    // New fields for Project details
    @Column(name = "area_distribution", columnDefinition = "TEXT")
    private String areaDistribution;

    @Column(name = "project_area_hectares", columnDefinition = "TEXT")
    private String projectAreaHectares;

    @Column(name = "renare_registration", columnDefinition = "TEXT")
    private String renareRegistration;

    @Column(name = "deforestation_drivers", columnDefinition = "TEXT")
    private String deforestationDrivers;

    @Column(name = "violated_safeguards", columnDefinition = "TEXT")
    private String violatedSafeguards;

    @Column(name = "contract_type", columnDefinition = "TEXT")
    private String contractType;

    @Column(name = "carbon_rating", columnDefinition = "TEXT")
    private String carbonRating;

    @JsonIgnore
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

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public LocalDate getPddStartDate() { return pddStartDate; }
    public void setPddStartDate(LocalDate pddStartDate) { this.pddStartDate = pddStartDate; }

    public LocalDate getPddEndDate() { return pddEndDate; }
    public void setPddEndDate(LocalDate pddEndDate) { this.pddEndDate = pddEndDate; }

    public String getHectares() { return hectares; }
    public void setHectares(String hectares) { this.hectares = hectares; }

    public String getCommunitiesInvolved() { return communitiesInvolved; }
    public void setCommunitiesInvolved(String communitiesInvolved) { this.communitiesInvolved = communitiesInvolved; }

    public String getDeveloperOrganizations() { return developerOrganizations; }
    public void setDeveloperOrganizations(String developerOrganizations) { this.developerOrganizations = developerOrganizations; }

    public String getCollaboratingOrganizations() { return collaboratingOrganizations; }
    public void setCollaboratingOrganizations(String collaboratingOrganizations) { this.collaboratingOrganizations = collaboratingOrganizations; }

    public String getProjectType() { return projectType; }
    public void setProjectType(String projectType) { this.projectType = projectType; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public Validator getValidator() { return validator; }
    public void setValidator(Validator validator) { this.validator = validator; }

    public List<ProjectVerifier> getProjectVerifiers() { return projectVerifiers; }
    public void setProjectVerifiers(List<ProjectVerifier> projectVerifiers) { this.projectVerifiers = projectVerifiers; }

    public CertifierEntity getCertifier() { return certifier; }
    public void setCertifier(CertifierEntity certifier) { this.certifier = certifier; }

    public LocalDate getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDate registrationDate) { this.registrationDate = registrationDate; }

    public LocalDate getQueryDate() { return queryDate; }
    public void setQueryDate(LocalDate queryDate) { this.queryDate = queryDate; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    public String getValidatorString() { return validatorString; }
    public void setValidatorString(String validatorString) { this.validatorString = validatorString; }

    public String getVerifiersString() { return verifiersString; }
    public void setVerifiersString(String verifiersString) { this.verifiersString = verifiersString; }

    // New getters and setters
    public String getAreaDistribution() { return areaDistribution; }
    public void setAreaDistribution(String areaDistribution) { this.areaDistribution = areaDistribution; }

    public String getProjectAreaHectares() { return projectAreaHectares; }
    public void setProjectAreaHectares(String projectAreaHectares) { this.projectAreaHectares = projectAreaHectares; }

    public String getRenareRegistration() { return renareRegistration; }
    public void setRenareRegistration(String renareRegistration) { this.renareRegistration = renareRegistration; }

    public String getDeforestationDrivers() { return deforestationDrivers; }
    public void setDeforestationDrivers(String deforestationDrivers) { this.deforestationDrivers = deforestationDrivers; }

    public String getViolatedSafeguards() { return violatedSafeguards; }
    public void setViolatedSafeguards(String violatedSafeguards) { this.violatedSafeguards = violatedSafeguards; }

    public String getContractType() { return contractType; }
    public void setContractType(String contractType) { this.contractType = contractType; }

    public String getCarbonRating() { return carbonRating; }
    public void setCarbonRating(String carbonRating) { this.carbonRating = carbonRating; }

    public Geometry getLocationGeometry() { return locationGeometry; }
    public void setLocationGeometry(Geometry locationGeometry) { this.locationGeometry = locationGeometry; }

    @JsonProperty("locationGeometryWkt")
    public String getLocationGeometryWkt() {
        return locationGeometry != null ? locationGeometry.toText() : null;
    }

    @JsonProperty("locationGeometryWkt")
    public void setLocationGeometryWkt(String wkt) {
        if (wkt != null && !wkt.trim().isEmpty()) {
            try {
                WKTReader reader = new WKTReader();
                this.locationGeometry = reader.read(wkt);
            } catch (Exception e) {
                // Log error or handle
                e.printStackTrace();
            }
        }
    }

    @JsonProperty("locationGeometryGeoJson")
    public String getLocationGeometryGeoJson() {
        if (locationGeometry != null) {
            GeoJSONWriter writer = new GeoJSONWriter();
            return writer.write(locationGeometry).toString();
        }
        return null;
    }
}