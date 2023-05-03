DELETE FROM email_address
WHERE ID IN (
    SELECT EMAIL_ADDRESS_ID FROM contact_email_address
    WHERE CONTACT_ID IN (
      SELECT ID FROM contact WHERE LOCATION_ID in (
        SELECT ID FROM address_location
        WHERE STREET_ID IN (SELECT ID FROM address_street
        WHERE SETTLEMENT_ID IN (SELECT s.ID FROM address_settlement s JOIN address_district d ON d.ID = s.DISTRICT_ID
        WHERE d.REGION_ID = 'TEST'))
      ) OR SEARCH_NAME LIKE '%ТЕСТ%'
    )
);

DELETE FROM contact_email_address
WHERE CONTACT_ID IN (
  SELECT ID FROM contact WHERE LOCATION_ID in (
    SELECT ID FROM address_location
    WHERE STREET_ID IN (SELECT ID FROM address_street
    WHERE SETTLEMENT_ID IN (SELECT s.ID FROM address_settlement s JOIN address_district d ON s.DISTRICT_ID = d.ID
    WHERE d.REGION_ID = 'TEST'))
  ) OR SEARCH_NAME LIKE '%ТЕСТ%'
);


DELETE FROM phone
WHERE ID IN (
  SELECT PHONE_ID FROM contact_phone
  WHERE CONTACT_ID IN (
    SELECT ID FROM contact WHERE LOCATION_ID in (
      SELECT ID FROM address_location
      WHERE STREET_ID IN (SELECT ID FROM address_street
      WHERE SETTLEMENT_ID IN (SELECT s.ID FROM address_settlement s JOIN address_district d ON s.DISTRICT_ID = d.ID
      WHERE d.REGION_ID = 'TEST'))
    ) OR SEARCH_NAME LIKE '%ТЕСТ%'
  )
);

DELETE FROM contact_phone
WHERE CONTACT_ID IN (
  SELECT ID FROM contact WHERE LOCATION_ID in (
    SELECT ID FROM address_location
    WHERE STREET_ID IN (SELECT ID FROM address_street
    WHERE SETTLEMENT_ID IN (SELECT s.ID FROM address_settlement s JOIN address_district d ON s.DISTRICT_ID = d.ID
    WHERE d.REGION_ID = 'TEST'))
  ) OR SEARCH_NAME LIKE '%ТЕСТ%'
);

DELETE FROM contact WHERE LOCATION_ID in (
    SELECT ID FROM address_location
    WHERE STREET_ID IN (SELECT ID FROM address_street
                        WHERE SETTLEMENT_ID IN (SELECT s.ID FROM address_settlement s JOIN address_district d ON s.DISTRICT_ID = d.ID
                                                WHERE d.REGION_ID = 'TEST'))
) OR SEARCH_NAME LIKE '%ТЕСТ%';

COMMIT;