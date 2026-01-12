package com.censat.redd_projects_api.repository;

import com.censat.redd_projects_api.model.CertifierEntity;
import com.censat.redd_projects_api.model.Project;
import com.censat.redd_projects_api.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByDepartamento(String departamento);

    List<Project> findByStatus(Status status);

    List<Project> findByDepartamentoAndStatus(String departamento, Status status);

    List<Project> findByNameContainingIgnoreCase(String name);

    List<Project> findByCertifier(CertifierEntity certifier);
}