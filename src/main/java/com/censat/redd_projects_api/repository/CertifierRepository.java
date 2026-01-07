package com.censat.redd_projects_api.repository;

import com.censat.redd_projects_api.model.CertifierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CertifierRepository extends JpaRepository<CertifierEntity, Long> {
    Optional<CertifierEntity> findByName(String name);
}