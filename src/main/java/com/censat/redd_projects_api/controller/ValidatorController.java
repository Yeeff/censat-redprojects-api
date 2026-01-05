package com.censat.redd_projects_api.controller;

import com.censat.redd_projects_api.model.Validator;
import com.censat.redd_projects_api.repository.ValidatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/validators")
public class ValidatorController {

    @Autowired
    private ValidatorRepository validatorRepository;

    @GetMapping
    public List<Validator> getAllValidators() {
        return validatorRepository.findAll();
    }
}