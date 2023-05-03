DELETE FROM user
WHERE login like 'Test %';

DELETE FROM user
WHERE login like 'New User %';

commit;