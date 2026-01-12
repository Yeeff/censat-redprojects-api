package com.censat.redd_projects_api.service;

import com.censat.redd_projects_api.dto.ProjectSummary;
import com.censat.redd_projects_api.model.CertifierEntity;
import com.censat.redd_projects_api.model.Project;
import com.censat.redd_projects_api.model.Status;
import com.censat.redd_projects_api.repository.CertifierRepository;
import com.censat.redd_projects_api.repository.ProjectRepository;
import com.censat.redd_projects_api.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private CertifierRepository certifierRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public List<ProjectSummary> getAllProjectsSummary() {
        return projectRepository.findAll().stream()
                .map(ProjectSummary::new)
                .collect(Collectors.toList());
    }

    public Page<ProjectSummary> getFilteredProjectsSummary(String name, String departamento, String estado, Long certifierId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Project> allProjects = projectRepository.findAll();

        // Apply filters
        List<Project> filteredProjects = allProjects.stream()
                .filter(p -> {
                    if (name != null && !name.trim().isEmpty()) {
                        if (p.getName() == null || !p.getName().toLowerCase().contains(name.toLowerCase())) return false;
                    }
                    if (departamento != null && !departamento.trim().isEmpty()) {
                        if (p.getDepartamento() == null || !p.getDepartamento().toLowerCase().contains(departamento.toLowerCase())) return false;
                    }
                    if (estado != null && !estado.trim().isEmpty()) {
                        if (p.getStatus() == null || !p.getStatus().getName().equalsIgnoreCase(estado)) return false;
                    }
                    if (certifierId != null) {
                        if (p.getCertifier() == null || !p.getCertifier().getId().equals(certifierId)) return false;
                    }
                    return true;
                })
                .collect(Collectors.toList());

        // Manual pagination
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), filteredProjects.size());
        List<Project> pageContent = filteredProjects.subList(start, end);

        List<ProjectSummary> summaries = pageContent.stream()
                .map(ProjectSummary::new)
                .collect(Collectors.toList());

        return new PageImpl<>(summaries, pageable, filteredProjects.size());
    }

    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public Project updateProject(Long id, Project projectDetails) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            project.setName(projectDetails.getName());
            project.setLocation(projectDetails.getLocation());
            project.setDepartamento(projectDetails.getDepartamento());
            project.setDescription(projectDetails.getDescription());
            project.setStatus(projectDetails.getStatus());
            project.setStartDate(projectDetails.getStartDate());
            project.setEndDate(projectDetails.getEndDate());
            project.setPddStartDate(projectDetails.getPddStartDate());
            project.setPddEndDate(projectDetails.getPddEndDate());
            project.setHectares(projectDetails.getHectares());
            project.setCommunitiesInvolved(projectDetails.getCommunitiesInvolved());
            project.setDeveloperOrganizations(projectDetails.getDeveloperOrganizations());
            project.setCollaboratingOrganizations(projectDetails.getCollaboratingOrganizations());
            project.setValidator(projectDetails.getValidator());
            project.setProjectVerifiers(projectDetails.getProjectVerifiers());
            project.setCertifier(projectDetails.getCertifier());
            project.setRegistrationDate(projectDetails.getRegistrationDate());
            project.setQueryDate(projectDetails.getQueryDate());
            project.setLink(projectDetails.getLink());
            project.setValidatorString(projectDetails.getValidatorString());
            project.setVerifiersString(projectDetails.getVerifiersString());
            project.setLocationGeometry(projectDetails.getLocationGeometry());
            return projectRepository.save(project);
        }
        return null;
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    public List<Project> getProjectsByDepartamento(String departamento) {
        return projectRepository.findByDepartamento(departamento);
    }

    public List<Project> getProjectsByStatus(Status status) {
        return projectRepository.findByStatus(status);
    }

    public List<Project> getProjectsByDepartamentoAndStatus(String departamento, Status status) {
        return projectRepository.findByDepartamentoAndStatus(departamento, status);
    }

    public List<Project> getProjectsByName(String name) {
        return projectRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Project> getProjectsByCertifier(CertifierEntity certifier) {
        return projectRepository.findByCertifier(certifier);
    }
}