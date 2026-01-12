package com.censat.redd_projects_api.controller;

import com.censat.redd_projects_api.dto.ProjectSummary;
import com.censat.redd_projects_api.model.CertifierEntity;
import com.censat.redd_projects_api.model.Project;
import com.censat.redd_projects_api.model.Status;
import com.censat.redd_projects_api.repository.CertifierRepository;
import com.censat.redd_projects_api.repository.ProjectRepository;
import com.censat.redd_projects_api.repository.StatusRepository;
import com.censat.redd_projects_api.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

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
        return projectService.getAllProjects();
    }

    @GetMapping("/list")
    public List<ProjectSummary> getAllProjectsSummary(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String departamento,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) Long certifierId) {
        return projectService.getFilteredProjectsSummary(name, departamento, estado, certifierId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id)
                .map(project -> ResponseEntity.ok(project))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Project createProject(@RequestBody Project project) {
        return projectService.createProject(project);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project projectDetails) {
        Project updatedProject = projectService.updateProject(id, projectDetails);
        if (updatedProject != null) {
            return ResponseEntity.ok(updatedProject);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/departamento/{departamento}")
    public List<Project> getProjectsByDepartamento(@PathVariable String departamento) {
        return projectService.getProjectsByDepartamento(departamento);
    }

    @GetMapping("/status/{statusName}")
    public List<Project> getProjectsByStatus(@PathVariable String statusName) {
        Status status = statusRepository.findByName(statusName).orElse(null);
        if (status == null) return List.of();
        return projectService.getProjectsByStatus(status);
    }

    @GetMapping("/filter")
    public List<Project> getProjectsByDepartamentoAndStatus(
            @RequestParam String departamento,
            @RequestParam String statusName) {
        Status status = statusRepository.findByName(statusName).orElse(null);
        if (status == null) return List.of();
        return projectService.getProjectsByDepartamentoAndStatus(departamento, status);
    }
}