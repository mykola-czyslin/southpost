INSERT INTO user(ID, LOGIN, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, REGISTERED_AT)
    VALUES
      (10001, 'Test Supervisor', 'my.supervisor@mail.net', '$2a$10$dNr/IHHsgX9ator9GKSJT.HHJRR1CRxyYpXkhoxUzD.9LkCaXInUe', 'John', 'Lennon', CURRENT_TIMESTAMP()),
      (10002, 'Test Viewer 01', 'test.viewer.01@mail.net', '$2a$10$dNr/IHHsgX9ator9GKSJT.HHJRR1CRxyYpXkhoxUzD.9LkCaXInUe', 'Paul', 'McCourtney', CURRENT_TIMESTAMP()),
      (10003, 'Test Viewer 02', 'test.viewer.02@mail.net', '$2a$10$dNr/IHHsgX9ator9GKSJT.HHJRR1CRxyYpXkhoxUzD.9LkCaXInUe', 'George', 'Harrison', CURRENT_TIMESTAMP()),
      (10004, 'Test Viewer 03', 'test.viewer.03@mail.net', '$2a$10$dNr/IHHsgX9ator9GKSJT.HHJRR1CRxyYpXkhoxUzD.9LkCaXInUe', 'Ring', 'Starr', CURRENT_TIMESTAMP()),
      (10005, 'Test Viewer 04', 'test.viewer.04@mail.net', '$2a$10$dNr/IHHsgX9ator9GKSJT.HHJRR1CRxyYpXkhoxUzD.9LkCaXInUe', 'Luke', 'Skywalker', CURRENT_TIMESTAMP()),
      (10006, 'Test Remove Viewer', 'test.remove.viewer@mail.net', '$2a$10$dNr/IHHsgX9ator9GKSJT.HHJRR1CRxyYpXkhoxUzD.9LkCaXInUe', 'Obiwan', 'Kenobi', CURRENT_TIMESTAMP()),
      (10007, 'Test Employment Oper', 'test.employment.operator@mail.net', '$2a$10$dNr/IHHsgX9ator9GKSJT.HHJRR1CRxyYpXkhoxUzD.9LkCaXInUe', 'Leya Alpha', 'Organa', CURRENT_TIMESTAMP()),
      (10008, 'Test Property Oper', 'test.property.operator@mail.net', '$2a$10$dNr/IHHsgX9ator9GKSJT.HHJRR1CRxyYpXkhoxUzD.9LkCaXInUe', 'Leya Beta', 'Organa', CURRENT_TIMESTAMP()),
      (10009, 'Test Clinic Oper', 'test.clinic.operator@mail.net', '$2a$10$dNr/IHHsgX9ator9GKSJT.HHJRR1CRxyYpXkhoxUzD.9LkCaXInUe', 'Leya Gamma', 'Organa', CURRENT_TIMESTAMP()),
      (10010, 'Test Law Operator', 'test.law.operator@mail.net', '$2a$10$dNr/IHHsgX9ator9GKSJT.HHJRR1CRxyYpXkhoxUzD.9LkCaXInUe', 'Leya Delta', 'Organa', CURRENT_TIMESTAMP()),
      (10011, 'Test Emp/Prop Oper', 'test.employment-and-property.operator@mail.net', '$2a$10$dNr/IHHsgX9ator9GKSJT.HHJRR1CRxyYpXkhoxUzD.9LkCaXInUe', 'Leya Kappa', 'Organa', CURRENT_TIMESTAMP()),
      (10012, 'Test Clinic/Law Oper', 'test.clinic-and-law.operator@mail.net', '$2a$10$dNr/IHHsgX9ator9GKSJT.HHJRR1CRxyYpXkhoxUzD.9LkCaXInUe', 'Leya', 'Organa', CURRENT_TIMESTAMP()),
      (10013, 'Test Prop/Law/Emp Op', 'test.property-law-and-employment.operator@mail.net', '$2a$10$dNr/IHHsgX9ator9GKSJT.HHJRR1CRxyYpXkhoxUzD.9LkCaXInUe', 'Leya Miu', 'Organa', CURRENT_TIMESTAMP()),
      (10014, 'Test Prp/Emp/Clc/Law', 'test.property-employment-clinic-and-law.operator@mail.net', '$2a$10$dNr/IHHsgX9ator9GKSJT.HHJRR1CRxyYpXkhoxUzD.9LkCaXInUe', 'Leya Niu', 'Organa', CURRENT_TIMESTAMP()),
      (10015, 'Test Administrator', 'test.administrator@mail.net', '$2a$10$dNr/IHHsgX9ator9GKSJT.HHJRR1CRxyYpXkhoxUzD.9LkCaXInUe', 'Han', 'Solo', CURRENT_TIMESTAMP());

INSERT INTO user_role (user_id, role_name)
    SELECT user.id, 'VIEWER' FROM user
    WHERE LOGIN LIKE 'Test %';

INSERT INTO user_role (user_id, role_name)
  SELECT user.id, 'OPERATOR' FROM user
  WHERE LOGIN LIKE 'Test %Operator%';

INSERT INTO user_role (user_id, role_name)
  SELECT user.id, 'OPERATOR' FROM user
  WHERE LOGIN LIKE 'Test %Administrator%';

INSERT INTO user_role (user_id, role_name)
  SELECT user.id, 'ADMINISTRATOR' FROM user
  WHERE LOGIN LIKE 'Test %Administrator%';


INSERT INTO user_role (user_id, role_name)
  SELECT user.id, 'OPERATOR' FROM user
  WHERE LOGIN LIKE 'Test %Supervisor%';

INSERT INTO user_role (user_id, role_name)
  SELECT user.id, 'ADMINISTRATOR' FROM user
  WHERE LOGIN LIKE 'Test %Supervisor%';;

INSERT INTO user_role (user_id, role_name)
  SELECT user.id, 'SUPERVISOR' FROM user
  WHERE LOGIN LIKE 'Test %Supervisor%';

INSERT INTO `user_declared_activity` (USER_ID, ACTIVITY_KIND)
SELECT `user`.ID, 'EMPLOYER'
    FROM `user`
WHERE `user`.LOGIN LIKE 'Test %Employment%Operator';

INSERT INTO `user_declared_activity` (USER_ID, ACTIVITY_KIND)
SELECT `user`.ID, 'PROPERTY_AGENT'
FROM `user`
WHERE `user`.LOGIN LIKE 'Test %Property%Operator';

INSERT INTO `user_declared_activity` (USER_ID, ACTIVITY_KIND)
SELECT `user`.ID, 'LAW_ASSISTANT'
FROM `user`
WHERE `user`.LOGIN LIKE 'Test %Law%Operator';

INSERT INTO `user_declared_activity` (USER_ID, ACTIVITY_KIND)
SELECT `user`.ID, 'CLINIC_ADVISOR'
FROM `user`
WHERE `user`.LOGIN LIKE 'Test %Clinic%Operator';

INSERT INTO `user_declared_activity` (USER_ID, ACTIVITY_KIND)
SELECT `user`.ID, 'VISITOR'
FROM `user`
WHERE `user`.LOGIN LIKE 'Test %Operator';

-- confirmed activity

INSERT INTO `user_confirmed_activity` (USER_ID, ACTIVITY_KIND)
SELECT `user`.ID, 'EMPLOYER'
FROM `user`
WHERE `user`.LOGIN LIKE 'Test %Employment%Operator';

INSERT INTO `user_confirmed_activity` (USER_ID, ACTIVITY_KIND)
SELECT `user`.ID, 'PROPERTY_AGENT'
FROM `user`
WHERE `user`.LOGIN LIKE 'Test %Property%Operator';

INSERT INTO `user_confirmed_activity` (USER_ID, ACTIVITY_KIND)
SELECT `user`.ID, 'LAW_ASSISTANT'
FROM `user`
WHERE `user`.LOGIN LIKE 'Test %Law%Operator';

INSERT INTO `user_confirmed_activity` (USER_ID, ACTIVITY_KIND)
SELECT `user`.ID, 'CLINIC_ADVISOR'
FROM `user`
WHERE `user`.LOGIN LIKE 'Test %Clinic%Operator';

INSERT INTO `user_confirmed_activity` (USER_ID, ACTIVITY_KIND)
SELECT `user`.ID, 'VISITOR'
FROM `user`
WHERE `user`.LOGIN LIKE 'Test %Operator';
