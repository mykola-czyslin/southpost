# noinspection SqlWithoutWhereForFile


DELETE FROM `dwelling`;

DELETE FROM `dwelling_phones`;

DELETE FROM `dwelling_emails`;

DELETE FROM `phone` WHERE ID BETWEEN 2010101 AND 2010112;

DELETE FROM `email_address` WHERE ID BETWEEN 2010101 AND 2010110;