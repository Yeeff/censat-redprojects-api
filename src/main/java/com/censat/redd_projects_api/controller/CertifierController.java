package com.censat.redd_projects_api.controller;

import com.censat.redd_projects_api.model.CertifierEntity;
import com.censat.redd_projects_api.repository.CertifierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certifiers")
public class CertifierController {

    @Autowired
    private CertifierRepository certifierRepository;

    @GetMapping
    public List<CertifierEntity> getAllCertifiers() {
        return certifierRepository.findAll();
    }
}