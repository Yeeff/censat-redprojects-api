package com.censat.redd_projects_api.repository;

import com.censat.redd_projects_api.model.IndigenousReserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndigenousReserveRepository extends JpaRepository<IndigenousReserve, Long> {
    IndigenousReserve findByName(String name);
}