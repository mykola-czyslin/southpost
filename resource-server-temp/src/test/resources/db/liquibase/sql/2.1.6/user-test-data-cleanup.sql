DELETE FROM `employer_vacancy` WHERE OPERATOR_ID IN (SELECT u.ID FROM `user` u WHERE u.LOGIN LIKE 'Test %');

DELETE FROM user
WHERE LOGIN LIKE 'Test %';

DELETE FROM user
WHERE LOGIN LIKE 'New User %';
