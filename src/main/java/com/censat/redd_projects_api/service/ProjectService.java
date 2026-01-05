package com.censat.redd_projects_api.service;

import com.censat.redd_projects_api.dto.ProjectSummary;
import com.censat.redd_projects_api.model.Project;
import com.censat.redd_projects_api.model.Status;
import com.censat.redd_projects_api.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public List<ProjectSummary> getAllProjectsSummary() {
        return projectRepository.findAll().stream()
                .map(ProjectSummary::new)
                .collect(Collectors.toList());
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
}