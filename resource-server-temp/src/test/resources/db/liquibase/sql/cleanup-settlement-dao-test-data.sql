-- cleanup test settlements
DELETE FROM address_settlement WHERE DISTRICT_ID IN (SELECT ID FROM address_district WHERE REGION_ID = 'TEST');
-- cleanup test district
DELETE FROM address_district WHERE REGION_ID = 'TEST';
-- cleanup test region
DELETE FROM address_region
WHERE ID = 'TEST';

commit;