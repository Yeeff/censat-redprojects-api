package com.censat.redd_projects_api.service;

import com.censat.redd_projects_api.model.CertifierEntity;
import com.censat.redd_projects_api.repository.CertifierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CertifierService {

    @Autowired
    private CertifierRepository certifierRepository;

    public List<CertifierEntity> getAllCertifiers() {
        return certifierRepository.findAll();
    }

    public Optional<CertifierEntity> getCertifierById(Long id) {
        return certifierRepository.findById(id);
    }

    public CertifierEntity createCertifier(CertifierEntity certifier) {
        return certifierRepository.save(certifier);
    }

    public CertifierEntity updateCertifier(Long id, CertifierEntity certifierDetails) {
        Optional<CertifierEntity> optionalCertifier = certifierRepository.findById(id);
        if (optionalCertifier.isPresent()) {
            CertifierEntity certifier = optionalCertifier.get();
            certifier.setName(certifierDetails.getName());
            certifier.setDescription(certifierDetails.getDescription());
            return certifierRepository.save(certifier);
        }
        return null;
    }

    public void deleteCertifier(Long id) {
        certifierRepository.deleteById(id);
    }

    public Optional<CertifierEntity> getCertifierByName(String name) {
        return certifierRepository.findByName(name);
    }
}