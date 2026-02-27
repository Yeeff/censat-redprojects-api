package com.censat.redd_projects_api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReddProjectsApiApplication {

    private static final Logger logger = LoggerFactory.getLogger(ReddProjectsApiApplication.class);

    public static void main(String[] args) {
        logger.info("========================================");
        logger.info("CENSAT REDD+ Projects API - Iniciando...");
        logger.info("========================================");
        
        SpringApplication.run(ReddProjectsApiApplication.class, args);
        
        logger.info("========================================");
        logger.info("API iniciada exitosamente!");
        logger.info("========================================");
    }

}
