DELETE FROM address_location
WHERE STREET_ID IN (SELECT ID FROM address_street
                    WHERE SETTLEMENT_ID IN (SELECT s.ID FROM address_settlement s JOIN address_district d ON d.ID = s.DISTRICT_ID
                                            WHERE d.REGION_ID = 'TEST'));