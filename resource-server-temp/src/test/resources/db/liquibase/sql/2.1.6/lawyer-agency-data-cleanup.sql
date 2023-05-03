# noinspection SqlWithoutWhereForFile

DELETE FROM `lawyer_agency_emails`;

DELETE FROM `lawyer_agency_phones`;

DELETE FROM `lawyer_agency_cases`;

DELETE FROM `lawyer_agency`;

DELETE FROM `phone` WHERE ID BETWEEN 4000001 AND 4000015;

DELETE FROM `email_address` WHERE ID BETWEEN 4000001 AND 4000015;