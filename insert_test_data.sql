-- SQL script to insert test data directly into the database
-- Run this script in the PostGIS database (postgres) after the tables are created
-- Geometries are stored using PostGIS functions:
-- ST_GeomFromText for WKT format with SRID 4326 (WGS84)
-- Sample geometries: polygons for areas, points for specific locations

-- Enable PostGIS extension if not already enabled
CREATE EXTENSION IF NOT EXISTS postgis;

-- Insert test projects
INSERT INTO projects (
    id, name, location, departamento, description, status, start_date, end_date,
    pdd_start_date, pdd_end_date, hectares, developer_organizations,
    collaborating_organizations, validator, certifier, registration_date,
    query_date, link, location_geometry
) VALUES (
    1,
    'REDD+ Project Resguardo Indígena Unificado Selva de Mataven (RIU SM)',
    'Cumaribo, Vichada',
    'Vichada',
    'Proyecto de reducción de emisiones por deforestación y degradación forestal en la Selva de Matavén.',
    'CERTIFICADO',
    '2013-01-01',
    '2042-12-31',
    '2013-01-01',
    '2042-12-31',
    1150212.00,
    'MEDIAMOS F&M S.A.S., Asociación de Cabildos y Autoridades Tradicionales Indígenas de la Selva de Matavén – ACATISEMA',
    'Federación Nacional de Cacaoteros – FEDECACAO, SENA, Fondo Acción, Fundación Natura, Bancolombia',
    'Instituto Colombiano de Normas Técnicas y Certificación (ICONTEC)',
    'VERRA',
    '2016-08-12',
    '2025-09-22',
    'https://example.com/project-link',
    ST_GeomFromText('POLYGON((-70.0 5.0, -69.0 5.0, -69.0 4.0, -70.0 4.0, -70.0 5.0))', 4326) -- Sample polygon for Vichada area
);

INSERT INTO projects (
    id, name, location, departamento, description, status, start_date, end_date,
    pdd_start_date, pdd_end_date, hectares, developer_organizations,
    collaborating_organizations, validator, certifier, registration_date,
    query_date, link, location_geometry
) VALUES (
    2,
    'Proyecto REDD+ Bosques Andinos',
    'Páramo de Sumapaz, Cundinamarca',
    'Cundinamarca',
    'Iniciativa para proteger los bosques andinos y reducir la deforestación.',
    'EN_DESARROLLO',
    '2020-01-01',
    '2035-12-31',
    '2020-01-01',
    '2035-12-31',
    50000.00,
    'Fundación Ambiental',
    'Gobierno de Cundinamarca, WWF',
    'ICONTEC',
    'COLCX',
    '2019-05-10',
    '2025-09-22',
    'https://example.com/bosques-andinos',
    ST_GeomFromText('POINT(-74.2 4.3)', 4326) -- Sample point for Cundinamarca area
);

INSERT INTO projects (
    id, name, location, departamento, description, status, start_date, end_date,
    pdd_start_date, pdd_end_date, hectares, developer_organizations,
    collaborating_organizations, validator, certifier, registration_date,
    query_date, link, location_geometry
) VALUES (
    3,
    'REDD+ Pacífico Colombiano',
    'Chocó',
    'Chocó',
    'Proyecto para conservar los bosques del Pacífico colombiano.',
    'REGISTRO_COMPLETO',
    '2018-06-01',
    '2040-05-31',
    '2018-06-01',
    '2040-05-31',
    200000.00,
    'Corporación Ambiental del Pacífico',
    'Ministerio de Ambiente, USAID',
    'ICONTEC',
    'VERRA',
    '2017-03-15',
    '2025-09-22',
    'https://example.com/pacifico',
    ST_GeomFromText('POLYGON((-77.2 6.2, -76.8 6.1, -76.5 5.8, -76.7 5.5, -77.0 5.3, -77.3 5.6, -77.5 5.9, -77.2 6.2))', 4326) -- Irregular polygon for Chocó area
);

-- Insert communities for project 1
INSERT INTO project_communities (project_id, community) VALUES
(1, 'Asociación de Cabildos y Autoridades Tradicionales Indígenas de la Selva de Matavén – ACATISEMA');

-- Insert communities for project 2
INSERT INTO project_communities (project_id, community) VALUES
(2, 'Comunidad Indígena Páramo de Sumapaz');

-- Insert communities for project 3
INSERT INTO project_communities (project_id, community) VALUES
(3, 'Comunidades Afrodescendientes del Pacífico');

-- Insert verifiers for project 1
INSERT INTO project_verifiers (project_id, verifier) VALUES
(1, 'Instituto Colombiano de Normas Técnicas y Certificación (ICONTEC)'),
(1, 'EPIC Sustainability Services Pvt. Ltd.'),
(1, 'EPIC Sustainability Services Pvt. Ltd.');

-- Insert verifiers for project 2
INSERT INTO project_verifiers (project_id, verifier) VALUES
(2, 'ICONTEC');

-- Insert verifiers for project 3
INSERT INTO project_verifiers (project_id, verifier) VALUES
(3, 'EPIC');

-- Reset sequence if needed (for auto-generated IDs)
-- ALTER SEQUENCE projects_id_seq RESTART WITH 4;