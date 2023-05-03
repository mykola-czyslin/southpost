INSERT INTO user(ID, LOGIN, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, REGISTERED_AT)
    VALUES
      (10001, 'Test Supervisor', 'my.supervisor@mail.net', '$2a$10$dNr/IHHsgX9ator9GKSJT.HHJRR1CRxyYpXkhoxUzD.9LkCaXInUe', 'John', 'Lennon', CURTIME()),
      (10002, 'Test Viewer 01', 'test.viewer.01@mail.net', '$2a$10$dNr/IHHsgX9ator9GKSJT.HHJRR1CRxyYpXkhoxUzD.9LkCaXInUe', 'Paul', 'McCourtney', CURTIME()),
      (10003, 'Test Viewer 02', 'test.viewer.02@mail.net', '$2a$10$dNr/IHHsgX9ator9GKSJT.HHJRR1CRxyYpXkhoxUzD.9LkCaXInUe', 'George', 'Harrison', CURTIME()),
      (10004, 'Test Viewer 03', 'test.viewer.03@mail.net', '$2a$10$dNr/IHHsgX9ator9GKSJT.HHJRR1CRxyYpXkhoxUzD.9LkCaXInUe', 'Ring', 'Starr', CURTIME()),
      (10005, 'Test Viewer 04', 'test.viewer.04@mail.net', '$2a$10$dNr/IHHsgX9ator9GKSJT.HHJRR1CRxyYpXkhoxUzD.9LkCaXInUe', 'Luke', 'Skywalker', CURTIME()),
      (10006, 'Test Remove Viewer', 'test.remove.viewer@mail.net', '$2a$10$dNr/IHHsgX9ator9GKSJT.HHJRR1CRxyYpXkhoxUzD.9LkCaXInUe', 'Obiwan', 'Kenobi', CURTIME()),
      (10007, 'Test Operator', 'test.operator@mail.net', '$2a$10$dNr/IHHsgX9ator9GKSJT.HHJRR1CRxyYpXkhoxUzD.9LkCaXInUe', 'Leya', 'Organa', CURTIME()),
      (10008, 'Test Administrator', 'test.administrator@mail.net', '$2a$10$dNr/IHHsgX9ator9GKSJT.HHJRR1CRxyYpXkhoxUzD.9LkCaXInUe', 'Han', 'Solo', CURTIME());

INSERT INTO user_role (user_id, role_name)
    SELECT user.id, 'VIEWER' FROM user
    WHERE id in (10001, 10002, 10003, 10004, 10005, 10006, 10007, 10008);

INSERT INTO user_role (user_id, role_name)
  SELECT user.id, 'OPERATOR' FROM user
  WHERE id = 10007;

INSERT INTO user_role (user_id, role_name)
  SELECT user.id, 'OPERATOR' FROM user
  WHERE id = 10008;

INSERT INTO user_role (user_id, role_name)
  SELECT user.id, 'ADMINISTRATOR' FROM user
  WHERE id = 10008;


INSERT INTO user_role (user_id, role_name)
  SELECT user.id, 'OPERATOR' FROM user
  WHERE id = 10001;

INSERT INTO user_role (user_id, role_name)
  SELECT user.id, 'ADMINISTRATOR' FROM user
  WHERE id = 10001;

INSERT INTO user_role (user_id, role_name)
  SELECT user.id, 'SUPERVISOR' FROM user
  WHERE id = 10001;

commit;