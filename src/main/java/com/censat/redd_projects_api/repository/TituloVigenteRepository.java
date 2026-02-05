package com.censat.redd_projects_api.repository;

import com.censat.redd_projects_api.model.TituloVigente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TituloVigenteRepository extends JpaRepository<TituloVigente, Long> {
    Optional<TituloVigente> findByCodigoExp(String codigoExp);
}
