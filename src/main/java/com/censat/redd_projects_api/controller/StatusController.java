package com.censat.redd_projects_api.controller;

import com.censat.redd_projects_api.model.Status;
import com.censat.redd_projects_api.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statuses")
public class StatusController {

    @Autowired
    private StatusRepository statusRepository;

    @GetMapping
    public List<Status> getAllStatuses() {
        return statusRepository.findAll();
    }
}