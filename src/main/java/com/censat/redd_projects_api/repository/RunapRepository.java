package com.censat.redd_projects_api.repository;

import com.censat.redd_projects_api.model.Runap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RunapRepository extends JpaRepository<Runap, Long> {
    Optional<Runap> findByApNombre(String apNombre);
}
