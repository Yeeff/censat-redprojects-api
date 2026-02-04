package com.censat.redd_projects_api.repository;

import com.censat.redd_projects_api.model.CommunityCouncil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityCouncilRepository extends JpaRepository<CommunityCouncil, Long> {
}