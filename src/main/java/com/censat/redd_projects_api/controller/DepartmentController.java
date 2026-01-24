package com.censat.redd_projects_api.controller;

import com.censat.redd_projects_api.dto.GeoJsonFeatureCollectionDTO;
import com.censat.redd_projects_api.model.Department;
import com.censat.redd_projects_api.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadGeoJson(@RequestBody GeoJsonFeatureCollectionDTO geoJsonDTO) {
        try {
            List<Department> savedDepartments = departmentService.saveGeoJsonDepartments(geoJsonDTO);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Departamentos guardados exitosamente");
            response.put("count", savedDepartments.size());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al procesar el GeoJSON");
            error.put("details", e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    // ENDPOINT que devuelve GeoJSON estándar
    @GetMapping("/geojson")
    public ResponseEntity<Map<String, Object>> getAllDepartmentsAsGeoJson() {
        Map<String, Object> geoJson = departmentService.getAllDepartmentsAsGeoJson();
        return ResponseEntity.ok(geoJson);
    }

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDepartmentById(@PathVariable Long id) {
        try {
            Department department = departmentService.getDepartmentById(id);
            return ResponseEntity.ok(department);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Departamento no encontrado");
            error.put("details", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getDepartmentByName(@PathVariable String name) {
        try {
            Department department = departmentService.getDepartmentByName(name);
            return ResponseEntity.ok(department);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Departamento no encontrado");
            error.put("details", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllDepartments() {
        try {
            departmentService.deleteAllDepartments();

            Map<String, String> response = new HashMap<>();
            response.put("message", "Todos los departamentos han sido eliminados");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al eliminar departamentos");
            error.put("details", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}