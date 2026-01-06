package com.censat.redd_projects_api.service;

import com.censat.redd_projects_api.model.Status;
import com.censat.redd_projects_api.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;

    public List<Status> getAllStatuses() {
        return statusRepository.findAll();
    }

    public Optional<Status> getStatusById(Long id) {
        return statusRepository.findById(id);
    }

    public Status createStatus(Status status) {
        return statusRepository.save(status);
    }

    public Status updateStatus(Long id, Status statusDetails) {
        Optional<Status> optionalStatus = statusRepository.findById(id);
        if (optionalStatus.isPresent()) {
            Status status = optionalStatus.get();
            status.setName(statusDetails.getName());
            return statusRepository.save(status);
        }
        return null;
    }

    public void deleteStatus(Long id) {
        statusRepository.deleteById(id);
    }

    public Optional<Status> getStatusByName(String name) {
        return statusRepository.findByName(name);
    }
}