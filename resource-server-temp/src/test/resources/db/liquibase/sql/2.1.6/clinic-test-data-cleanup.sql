# noinspection SqlWithoutWhereForFile
DELETE FROM `clinic_phones`;

DELETE FROM `phone`
WHERE ID BETWEEN 3000001 AND 3000014;

DELETE FROM `clinic_emails`;

DELETE FROM `email_address`
WHERE ID BETWEEN 3000001 AND 3000014;

DELETE FROM `clinic`;
