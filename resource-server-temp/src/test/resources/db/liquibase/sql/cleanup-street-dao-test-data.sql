delete from address_street
where SETTLEMENT_ID in (select s.ID from address_settlement s JOIN address_district d on d.ID = s.DISTRICT_ID
                        where d.REGION_ID = 'TEST');

commit;