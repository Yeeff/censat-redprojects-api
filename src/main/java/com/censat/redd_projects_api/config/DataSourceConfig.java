package com.censat.redd_projects_api.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
        String instance   = System.getenv("DB_INSTANCE");
        String dbName     = System.getenv("DB_NAME")     != null ? System.getenv("DB_NAME")     : "redd_db";
        String dbUser     = System.getenv("DB_USER")     != null ? System.getenv("DB_USER")     : "postgres";
        String dbPassword = System.getenv("DB_PASSWORD") != null ? System.getenv("DB_PASSWORD") : "password";

        if (instance != null && !instance.isEmpty()) {
            // Cloud Run + Cloud SQL proxy automático (PostgreSQL)
            String url = "jdbc:postgresql:///"+  dbName +
                    "?cloudSqlInstance=" + instance +
                    "&socketFactory=com.google.cloud.sql.postgres.SocketFactory";
            return DataSourceBuilder.create()
                    .url(url)
                    .username(dbUser)
                    .password(dbPassword)
                    .build();
        } else {
            // Local
            String dbHost = System.getenv("DB_HOST") != null ? System.getenv("DB_HOST") : "localhost";
            String dbPort = System.getenv("DB_PORT") != null ? System.getenv("DB_PORT") : "5432";
            String url = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;
            return DataSourceBuilder.create()
                    .url(url)
                    .username(dbUser)
                    .password(dbPassword)
                    .build();
        }
    }
}