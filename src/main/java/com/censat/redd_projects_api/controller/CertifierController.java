package com.censat.redd_projects_api.controller;

import com.censat.redd_projects_api.model.CertifierEntity;
import com.censat.redd_projects_api.service.CertifierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certifiers")
public class CertifierController {

    @Autowired
    private CertifierService certifierService;

    @GetMapping
    public List<CertifierEntity> getAllCertifiers() {
        return certifierService.getAllCertifiers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertifierEntity> getCertifierById(@PathVariable Long id) {
        return certifierService.getCertifierById(id)
                .map(certifier -> ResponseEntity.ok(certifier))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public CertifierEntity createCertifier(@RequestBody CertifierEntity certifier) {
        return certifierService.createCertifier(certifier);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CertifierEntity> updateCertifier(@PathVariable Long id, @RequestBody CertifierEntity certifierDetails) {
        CertifierEntity updatedCertifier = certifierService.updateCertifier(id, certifierDetails);
        if (updatedCertifier != null) {
            return ResponseEntity.ok(updatedCertifier);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCertifier(@PathVariable Long id) {
        certifierService.deleteCertifier(id);
        return ResponseEntity.noContent().build();
    }
}