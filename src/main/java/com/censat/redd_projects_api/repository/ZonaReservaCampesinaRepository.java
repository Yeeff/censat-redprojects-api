package com.censat.redd_projects_api.repository;

import com.censat.redd_projects_api.model.ZonaReservaCampesina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ZonaReservaCampesinaRepository extends JpaRepository<ZonaReservaCampesina, Long> {
    Optional<ZonaReservaCampesina> findByNombreZon(String nombreZon);
}
