package com.censat.redd_projects_api.repository;

import com.censat.redd_projects_api.model.Validator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidatorRepository extends JpaRepository<Validator, Long> {
}