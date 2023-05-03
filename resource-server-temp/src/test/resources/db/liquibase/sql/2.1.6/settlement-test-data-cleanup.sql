# noinspection SqlWithoutWhereForFile

DELETE FROM `clinic_phones`;

DELETE FROM `clinic_emails`;

DELETE FROM `clinic`;

DELETE FROM `dwelling_phones`;

DELETE FROM `dwelling_emails`;

DELETE FROM `dwelling`;

DELETE FROM `employer_vacancy`;

DELETE FROM `employer_phones`;

DELETE FROM `employer_emails`;

DELETE FROM `employer`;

DELETE FROM `phone`;

DELETE FROM `email_address`;

-- cleanup locations
DELETE FROM `address_location`;

-- cleanup streets
DELETE FROM `address_street`;


-- cleanup test settlements
DELETE FROM `address_settlement` WHERE DISTRICT_ID IN (SELECT ID FROM address_district WHERE REGION_ID = 'TEST');
-- cleanup test district
DELETE FROM `address_district` WHERE REGION_ID = 'TEST';
-- cleanup test region
DELETE FROM `address_region`
WHERE ID = 'TEST';
