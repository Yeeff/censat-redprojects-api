package com.censat.redd_projects_api.dto;

import com.censat.redd_projects_api.model.CertifierEntity;
import com.censat.redd_projects_api.model.Project;
import com.censat.redd_projects_api.model.Status;
import com.censat.redd_projects_api.model.Validator;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ProjectSummary {

    private Long id;
    private String name;
    private String location;
    private String departamento;
    private String description;
    private Status status;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate pddStartDate;
    private LocalDate pddEndDate;
    private String hectares;
    private String communitiesInvolved;
    private String developerOrganizations;
    private String collaboratingOrganizations;
    private Validator validator;
    private CertifierEntity certifier;
    private LocalDate registrationDate;
    private LocalDate queryDate;
    private String link;

    // New fields
    private String areaDistribution;
    private String projectAreaHectares;
    private String renareRegistration;
    private String deforestationDrivers;
    private String violatedSafeguards;
    private String contractType;
    private String carbonRating;

    // Default constructor
    public ProjectSummary() {}

    // Constructor from Project
    public ProjectSummary(Project project) {
        this.id = project.getId();
        this.name = project.getName();
        this.location = project.getLocation();
        this.departamento = project.getDepartamento();
        this.description = project.getDescription();
        this.status = project.getStatus();
        this.startDate = project.getStartDate();
        this.endDate = project.getEndDate();
        this.pddStartDate = project.getPddStartDate();
        this.pddEndDate = project.getPddEndDate();
        this.hectares = project.getHectares();
        this.communitiesInvolved = project.getCommunitiesInvolved();
        this.developerOrganizations = project.getDeveloperOrganizations();
        this.collaboratingOrganizations = project.getCollaboratingOrganizations();
        this.validator = project.getValidator();
        this.certifier = project.getCertifier();
        this.registrationDate = project.getRegistrationDate();
        this.queryDate = project.getQueryDate();
        this.link = project.getLink();
        this.areaDistribution = project.getAreaDistribution();
        this.projectAreaHectares = project.getProjectAreaHectares();
        this.renareRegistration = project.getRenareRegistration();
        this.deforestationDrivers = project.getDeforestationDrivers();
        this.violatedSafeguards = project.getViolatedSafeguards();
        this.contractType = project.getContractType();
        this.carbonRating = project.getCarbonRating();
    }

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

    public Validator getValidator() { return validator; }
    public void setValidator(Validator validator) { this.validator = validator; }

    public CertifierEntity getCertifier() { return certifier; }
    public void setCertifier(CertifierEntity certifier) { this.certifier = certifier; }

    public LocalDate getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDate registrationDate) { this.registrationDate = registrationDate; }

    public LocalDate getQueryDate() { return queryDate; }
    public void setQueryDate(LocalDate queryDate) { this.queryDate = queryDate; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

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
}