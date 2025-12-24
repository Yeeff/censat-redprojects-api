-- SQL script to insert test data directly into the database
-- Run this script in the PostGIS database (postgres) after the tables are created
-- Geometries are stored using PostGIS functions:
-- ST_GeomFromText for WKT format with SRID 4326 (WGS84)

-- Enable PostGIS extension if not already enabled
CREATE EXTENSION IF NOT EXISTS postgis;

-- Insert reference data
INSERT INTO statuses (id, name) VALUES
(1, 'CERTIFICADO'),
(2, 'EN_DESARROLLO'),
(3, 'REGISTRO_COMPLETO');

INSERT INTO certifiers (id, name) VALUES
(1, 'VERRA'),
(2, 'COLCX');

INSERT INTO validators (id, name) VALUES
(1, 'Instituto Colombiano de Normas Técnicas y Certificación (ICONTEC)');

INSERT INTO verifiers (id, name) VALUES
(1, 'Instituto Colombiano de Normas Técnicas y Certificación (ICONTEC)'),
(2, 'EPIC Sustainability Services Pvt. Ltd.'),
(3, 'EPIC');

-- Insert test projects
INSERT INTO projects (
    id, name, location, departamento, description, status_id, start_date, end_date,
    pdd_start_date, pdd_end_date, hectares, communities_involved, developer_organizations,
    collaborating_organizations, validator_id, certifier_id, registration_date,
    query_date, link, location_geometry
) VALUES (
    1,
    'REDD+ Project Resguardo Indígena Unificado Selva de Mataven (RIU SM)',
    'Cumaribo, Vichada',
    'Vichada',
    'Proyecto de reducción de emisiones por deforestación y degradación forestal en la Selva de Matavén.',
    1, -- CERTIFICADO
    '2013-01-01',
    '2042-12-31',
    '2013-01-01',
    '2042-12-31',
    1150212.00,
    'Asociación de Cabildos y Autoridades Tradicionales Indígenas de la Selva de Matavén – ACATISEMA',
    'MEDIAMOS F&M S.A.S., Asociación de Cabildos y Autoridades Tradicionales Indígenas de la Selva de Matavén – ACATISEMA',
    'Federación Nacional de Cacaoteros – FEDECACAO, SENA, Fondo Acción, Fundación Natura, Bancolombia',
    1, -- ICONTEC
    1, -- VERRA
    '2016-08-12',
    '2025-09-22',
    'https://example.com/project-link',
    ST_GeomFromText('POLYGON((-70.0 5.0, -69.0 5.0, -69.0 4.0, -70.0 4.0, -70.0 5.0))', 4326)
);

INSERT INTO projects (
    id, name, location, departamento, description, status_id, start_date, end_date,
    pdd_start_date, pdd_end_date, hectares, communities_involved, developer_organizations,
    collaborating_organizations, validator_id, certifier_id, registration_date,
    query_date, link, location_geometry
) VALUES (
    2,
    'Proyecto REDD+ Bosques Andinos',
    'Páramo de Sumapaz, Cundinamarca',
    'Cundinamarca',
    'Iniciativa para proteger los bosques andinos y reducir la deforestación.',
    2, -- EN_DESARROLLO
    '2020-01-01',
    '2035-12-31',
    '2020-01-01',
    '2035-12-31',
    50000.00,
    'Comunidad Indígena Páramo de Sumapaz',
    'Fundación Ambiental',
    'Gobierno de Cundinamarca, WWF',
    1, -- ICONTEC
    2, -- COLCX
    '2019-05-10',
    '2025-09-22',
    'https://example.com/bosques-andinos',
    ST_GeomFromText('POLYGON((-74.5 4.5, -74.0 4.4, -73.8 4.1, -74.1 3.9, -74.4 4.0, -74.5 4.5))', 4326)
);

INSERT INTO projects (
    id, name, location, departamento, description, status_id, start_date, end_date,
    pdd_start_date, pdd_end_date, hectares, communities_involved, developer_organizations,
    collaborating_organizations, validator_id, certifier_id, registration_date,
    query_date, link, location_geometry
) VALUES (
    3,
    'REDD+ Pacífico Colombiano',
    'Chocó',
    'Chocó',
    'Proyecto para conservar los bosques del Pacífico colombiano.',
    3, -- REGISTRO_COMPLETO
    '2018-06-01',
    '2040-05-31',
    '2018-06-01',
    '2040-05-31',
    200000.00,
    'Comunidades Afrodescendientes del Pacífico',
    'Corporación Ambiental del Pacífico',
    'Ministerio de Ambiente, USAID',
    1, -- ICONTEC
    1, -- VERRA
    '2017-03-15',
    '2025-09-22',
    'https://example.com/pacifico',
    ST_GeomFromText('POLYGON((-77.2 6.2, -76.8 6.1, -76.5 5.8, -76.7 5.5, -77.0 5.3, -77.3 5.6, -77.5 5.9, -77.2 6.2))', 4326)
);

-- Insert project-verifier relationships
INSERT INTO project_verifiers (project_id, verifier_id) VALUES
(1, 1), -- Project 1 - ICONTEC
(1, 2), -- Project 1 - EPIC
(2, 1), -- Project 2 - ICONTEC
(3, 3); -- Project 3 - EPIC