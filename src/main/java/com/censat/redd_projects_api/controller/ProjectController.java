package com.censat.redd_projects_api.controller;

import com.censat.redd_projects_api.dto.ProjectSummary;
import com.censat.redd_projects_api.model.CertifierEntity;
import com.censat.redd_projects_api.model.Project;
import com.censat.redd_projects_api.model.Status;
import com.censat.redd_projects_api.repository.CertifierRepository;
import com.censat.redd_projects_api.repository.ProjectRepository;
import com.censat.redd_projects_api.repository.StatusRepository;
import com.censat.redd_projects_api.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private CertifierRepository certifierRepository;

    @GetMapping
    public List<Project> getAllProjects() {
        logger.info("GET /api/projects - Obteniendo todos los proyectos");
        List<Project> projects = projectService.getAllProjects();
        logger.info("GET /api/projects - Se encontraron {} proyectos", projects.size());
        return projects;
    }

    @GetMapping("/list")
    public Page<ProjectSummary> getAllProjectsSummary(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String departamento,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) Long certifierId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        logger.info("GET /api/projects/list - Obteniendo lista paginada. Page: {}, Size: {}, Filters: nombre={}, departamento={}, estado={}, certifierId={}", 
                    page, size, name, departamento, estado, certifierId);
        Page<ProjectSummary> result = projectService.getFilteredProjectsSummary(name, departamento, estado, certifierId, page, size);
        logger.info("GET /api/projects/list - Se encontraron {} proyectos en página {}", result.getNumberOfElements(), page);
        return result;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        logger.info("GET /api/projects/{} - Obteniendo proyecto por ID", id);
        return projectService.getProjectById(id)
                .map(project -> {
                    logger.info("GET /api/projects/{} - Proyecto encontrado: {}", id, project.getName());
                    return ResponseEntity.ok(project);
                })
                .orElseGet(() -> {
                    logger.warn("GET /api/projects/{} - Proyecto no encontrado", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping
    public Project createProject(@RequestBody Project project) {
        logger.info("POST /api/projects - Creando nuevo proyecto: {}", project.getName());
        Project created = projectService.createProject(project);
        logger.info("POST /api/projects - Proyecto creado exitosamente con ID: {}", created.getId());
        return created;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project projectDetails) {
        logger.info("PUT /api/projects/{} - Actualizando proyecto", id);
        Project updatedProject = projectService.updateProject(id, projectDetails);
        if (updatedProject != null) {
            logger.info("PUT /api/projects/{} - Proyecto actualizado exitosamente: {}", id, updatedProject.getName());
            return ResponseEntity.ok(updatedProject);
        }
        logger.warn("PUT /api/projects/{} - Proyecto no encontrado para actualizar", id);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        logger.info("DELETE /api/projects/{} - Eliminando proyecto", id);
        projectService.deleteProject(id);
        logger.info("DELETE /api/projects/{} - Proyecto eliminado exitosamente", id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/departamento/{departamento}")
    public List<Project> getProjectsByDepartamento(@PathVariable String departamento) {
        logger.info("GET /api/projects/departamento/{} - Obteniendo proyectos por departamento", departamento);
        List<Project> projects = projectService.getProjectsByDepartamento(departamento);
        logger.info("GET /api/projects/departamento/{} - Se encontraron {} proyectos", departamento, projects.size());
        return projects;
    }

    @GetMapping("/status/{statusName}")
    public List<Project> getProjectsByStatus(@PathVariable String statusName) {
        logger.info("GET /api/projects/status/{} - Obteniendo proyectos por estado", statusName);
        Status status = statusRepository.findByName(statusName).orElse(null);
        if (status == null) {
            logger.warn("GET /api/projects/status/{} - Estado no encontrado", statusName);
            return List.of();
        }
        List<Project> projects = projectService.getProjectsByStatus(status);
        logger.info("GET /api/projects/status/{} - Se encontraron {} proyectos", statusName, projects.size());
        return projects;
    }

    @GetMapping("/filter")
    public List<Project> getProjectsByDepartamentoAndStatus(
            @RequestParam String departamento,
            @RequestParam String statusName) {
        logger.info("GET /api/projects/filter - Obteniendo proyectos. Departamento: {}, Estado: {}", departamento, statusName);
        Status status = statusRepository.findByName(statusName).orElse(null);
        if (status == null) {
            logger.warn("GET /api/projects/filter - Estado no encontrado: {}", statusName);
            return List.of();
        }
        List<Project> projects = projectService.getProjectsByDepartamentoAndStatus(departamento, status);
        logger.info("GET /api/projects/filter - Se encontraron {} proyectos", projects.size());
        return projects;
    }
}
