package com.censat.redd_projects_api.service;

import com.censat.redd_projects_api.dto.ProjectSummary;
import com.censat.redd_projects_api.model.CertifierEntity;
import com.censat.redd_projects_api.model.Project;
import com.censat.redd_projects_api.model.Status;
import com.censat.redd_projects_api.repository.CertifierRepository;
import com.censat.redd_projects_api.repository.ProjectRepository;
import com.censat.redd_projects_api.repository.StatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private CertifierRepository certifierRepository;

    public List<Project> getAllProjects() {
        logger.debug("ProjectService: Obteniendo todos los proyectos");
        return projectRepository.findAll();
    }

    public List<ProjectSummary> getAllProjectsSummary() {
        logger.debug("ProjectService: Obteniendo resumen de todos los proyectos");
        return projectRepository.findAll().stream()
                .map(ProjectSummary::new)
                .collect(Collectors.toList());
    }

    public Page<ProjectSummary> getFilteredProjectsSummary(String name, String departamento, String estado, Long certifierId, int page, int size) {
        logger.debug("ProjectService: Obteniendo proyectos filtrados. Filtros: nombre={}, departamento={}, estado={}, certifierId={}", 
                    name, departamento, estado, certifierId);
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

        logger.info("ProjectService: Se encontraron {} proyectos que coinciden con los filtros", filteredProjects.size());
        return new PageImpl<>(summaries, pageable, filteredProjects.size());
    }

    public Optional<Project> getProjectById(Long id) {
        logger.debug("ProjectService: Buscando proyecto con ID: {}", id);
        return projectRepository.findById(id);
    }

    public Project createProject(Project project) {
        logger.info("ProjectService: Creando nuevo proyecto: {}", project.getName());
        Project saved = projectRepository.save(project);
        logger.info("ProjectService: Proyecto creado con ID: {}", saved.getId());
        return saved;
    }

    public Project updateProject(Long id, Project projectDetails) {
        logger.info("ProjectService: Actualizando proyecto con ID: {}", id);
        
        // Log detallado
        logger.info("ProjectService: Datos recibidos - name={}, locationGeometry={}, departamento={}",
            projectDetails.getName(),
            projectDetails.getLocationGeometry() != null ? "presente" : "null",
            projectDetails.getDepartamento());
            
        Optional<Project> optionalProject = projectRepository.findById(id);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            
            // Log del estado anterior
            logger.info("ProjectService: Estado anterior - locationGeometry={}",
                project.getLocationGeometry() != null ? "presente" : "null");
            
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
            
            // Log después de setear
            logger.info("ProjectService: Después de setear - locationGeometry={}",
                project.getLocationGeometry() != null ? "presente" : "null");
            
            Project updated = projectRepository.save(project);
            
            // Log del estado después de guardar
            logger.info("ProjectService: Después de guardar - locationGeometry={}",
                updated.getLocationGeometry() != null ? "presente" : "null");
            
            logger.info("ProjectService: Proyecto {} actualizado exitosamente", id);
            return updated;
        }
        logger.warn("ProjectService: No se encontró el proyecto con ID: {} para actualizar", id);
        return null;
    }

    public void deleteProject(Long id) {
        logger.info("ProjectService: Eliminando proyecto con ID: {}", id);
        projectRepository.deleteById(id);
        logger.info("ProjectService: Proyecto {} eliminado exitosamente", id);
    }

    public List<Project> getProjectsByDepartamento(String departamento) {
        logger.debug("ProjectService: Buscando proyectos por departamento: {}", departamento);
        return projectRepository.findByDepartamento(departamento);
    }

    public List<Project> getProjectsByStatus(Status status) {
        logger.debug("ProjectService: Buscando proyectos por estado: {}", status.getName());
        return projectRepository.findByStatus(status);
    }

    public List<Project> getProjectsByDepartamentoAndStatus(String departamento, Status status) {
        logger.debug("ProjectService: Buscando proyectos por departamento y estado: {}, {}", departamento, status.getName());
        return projectRepository.findByDepartamentoAndStatus(departamento, status);
    }

    public List<Project> getProjectsByName(String name) {
        logger.debug("ProjectService: Buscando proyectos por nombre: {}", name);
        return projectRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Project> getProjectsByCertifier(CertifierEntity certifier) {
        logger.debug("ProjectService: Buscando proyectos por certificador: {}", certifier.getName());
        return projectRepository.findByCertifier(certifier);
    }
}
