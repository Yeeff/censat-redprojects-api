package com.censat.redd_projects_api.service;

import com.censat.redd_projects_api.dto.FeatureDTO;
import com.censat.redd_projects_api.dto.GeoJsonFeatureCollectionDTO;
import com.censat.redd_projects_api.model.Department;
import com.censat.redd_projects_api.repository.DepartmentRepository;
import org.locationtech.jts.geom.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wololo.jts2geojson.GeoJSONWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    private static final int SRID = 4326;

    @Transactional
    public List<Department> saveGeoJsonDepartments(GeoJsonFeatureCollectionDTO geoJsonDTO) {
        List<Department> departments = new ArrayList<>();
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), SRID);

        for (FeatureDTO feature : geoJsonDTO.getFeatures()) {
            try {
                String name = feature.getProperties().getDeNombre();
                MultiPolygon multiPolygon = convertToMultiPolygon(feature.getGeometry(), geometryFactory);

                Department department = new Department(name, multiPolygon);
                departments.add(department);
            } catch (Exception e) {
                throw new RuntimeException("Error procesando feature: " + feature.getProperties().getDeNombre(), e);
            }
        }

        return departmentRepository.saveAll(departments);
    }

    // NUEVO MÉTODO: Devuelve en formato GeoJSON usando jts2geojson
    public Map<String, Object> getAllDepartmentsAsGeoJson() {
        List<Department> departments = departmentRepository.findAll();
        GeoJSONWriter writer = new GeoJSONWriter();

        List<Map<String, Object>> features = new ArrayList<>();

        for (Department dept : departments) {
            Map<String, Object> feature = new HashMap<>();
            feature.put("type", "Feature");

            Map<String, Object> properties = new HashMap<>();
            properties.put("id", dept.getId());
            properties.put("name", dept.getName());
            feature.put("properties", properties);

            // Convertir geometría JTS a GeoJSON
            org.wololo.geojson.Geometry geoJsonGeometry = writer.write(dept.getGeometry());
            feature.put("geometry", geoJsonGeometry);

            features.add(feature);
        }

        Map<String, Object> featureCollection = new HashMap<>();
        featureCollection.put("type", "FeatureCollection");
        featureCollection.put("features", features);

        return featureCollection;
    }

    private MultiPolygon convertToMultiPolygon(com.censat.redd_projects_api.dto.GeometryDTO geometryDTO, GeometryFactory geometryFactory) {
        List<Polygon> polygons = new ArrayList<>();

        for (List<List<List<Double>>> polygonCoords : geometryDTO.getCoordinates()) {
            Polygon polygon = createPolygon(polygonCoords, geometryFactory);
            polygons.add(polygon);
        }

        return geometryFactory.createMultiPolygon(polygons.toArray(new Polygon[0]));
    }

    private Polygon createPolygon(List<List<List<Double>>> polygonCoords, GeometryFactory geometryFactory) {
        LinearRing shell = createLinearRing(polygonCoords.get(0), geometryFactory);

        LinearRing[] holes = new LinearRing[polygonCoords.size() - 1];
        for (int i = 1; i < polygonCoords.size(); i++) {
            holes[i - 1] = createLinearRing(polygonCoords.get(i), geometryFactory);
        }

        return geometryFactory.createPolygon(shell, holes);
    }

    private LinearRing createLinearRing(List<List<Double>> coordinates, GeometryFactory geometryFactory) {
        Coordinate[] coords = new Coordinate[coordinates.size()];

        for (int i = 0; i < coordinates.size(); i++) {
            List<Double> point = coordinates.get(i);
            coords[i] = new Coordinate(point.get(0), point.get(1));
        }

        return geometryFactory.createLinearRing(coords);
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
    }

    public Department getDepartmentByName(String name) {
        return departmentRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Department not found with name: " + name));
    }

    @Transactional
    public void deleteAllDepartments() {
        departmentRepository.deleteAll();
    }
}