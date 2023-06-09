INSERT INTO contact (ID, DISPLAY_NAME, SEARCH_NAME, CONTACT_DESCRIPTION, LOCATION_ID)
VALUES
  (20001, 'Відділ кадрів Верхньотестіївської міської лікарні №1', 'ТЕСТ-ВІДДІЛ КАДРІВ ВЕРХНЬОТЕСТІЇВСЬКОЇ МІСЬКОЇ ЛІКАРНІ №1', 'Відділ кадрів Верхньотестіївської міської лікарні №1', 10001001),
  (20002, 'Відділ кадрів заводу "Метеор"', 'ТЕСТ-ВІДДІЛ КАДРІВ ЗАВОДУ "МЕТЕОР"', 'Відділ кадрів заводу "Метеор". Секретар.', 10001002),
  (20003, 'Петренко Світлана', 'ТЕСТ-ПЕТРЕНКО СВІТЛАНА', 'Петренко Світлана (Перукар)', NULL),
  (20004, 'Зінченко Віка', 'ТЕСТ-ЗІНЧЕНКО ВІКА', 'Зінченко Віка (Манікюр)', 10001004);


INSERT INTO phone (ID, DISPLAY_PHONE_NUMBER, SEARCH_PHONE_NUMBER, DESCRIPTION)
    VALUES
      (1001, '+38(099) 999-99-99', 'T+380999999999', 'Тестовий телефон № 0001'),
      (1002, '+38(098) 999-99-99', 'T+380989999999', 'Тестовий телефон № 0002'),
      (1003, '+38(097) 999-99-99', 'T+380979999999', 'Тестовий телефон № 0003'),
      (1004, '+38(096) 999-99-99', 'T+380969999999', 'Тестовий телефон № 0004'),
      (1005, '+38(095) 999-99-99', 'T+380959999999', 'Тестовий телефон № 0005'),
      (1006, '+38(094) 999-99-99', 'T+380949999999', 'Тестовий телефон № 0006'),
      (1007, '+38(093) 999-99-99', 'T+380939999999', 'Тестовий телефон № 0007'),
      (1008, '+38(092) 999-99-99', 'T+380929999999', 'Тестовий телефон № 0008'),
      (1009, '+38(091) 999-99-99', 'T+380919999999', 'Тестовий телефон № 0009'),
      (1010, '+38(090) 999-99-99', 'T+380909999999', 'Тестовий телефон № 0010'),
      (1011, '+38(089) 999-99-99', 'T+380899999999', 'Тестовий телефон № 0011'),
      (1012, '+38(088) 999-99-99', 'T+380889999999', 'Тестовий телефон № 0012'),
      (1013, '+38(087) 999-99-99', 'T+380879999999', 'Тестовий телефон № 0013'),
      (1014, '+38(086) 999-99-99', 'T+380869999999', 'Тестовий телефон № 0014');

INSERT INTO contact_phone(PHONE_ID, CONTACT_ID, ORDINAL_INDEX)
    VALUES
      (1001, 20001, 0),
      (1002, 20001, 1),
      (1003, 20001, 2),
      (1004, 20002, 0),
      (1005, 20002, 1),
      (1006, 20002, 2),
      (1007, 20002, 3),
      (1008, 20003, 0),
      (1009, 20003, 1),
      (1010, 20003, 2),
      (1011, 20004, 0),
      (1012, 20004, 1),
      (1013, 20004, 2),
      (1014, 20004, 3);

INSERT INTO email_address(ID, EMAIL_ADDRESS, DESCRIPTION)
VALUES
  (20101, 'reception.werchnij-testijiv.likarnia.1@mail.gov.ua', 'Рецепціоніст'),
  (20102, 'wk.werchnij-testijiv.likarnia.1@mail.gov.ua', 'Відділ кадрів'),
  (20103, 'holowlikar.werchnij-testijiv.lilarnia@mail.gov.ua', 'Головлікар'),
  (20104, 'widdil-kadriv@meteor.plant.com.ua', 'Відділ кадрів аводу "Метеор", Тестіїв'),
  (20105, 'switlana.petrenko88@ukr.net', 'Світлана Петренко, основна поштова скринька'),
  (20106, 'switlana.petrenko88@gmail.com', 'Світлана Петренко, акаунт gmail'),
  (20107, 'wika-zinch-1993@ukr.net', 'Віка Зінченко, робоча поштова скринька'),
  (20108, 'victoria.zinchenko@yahoo.com', '-');

INSERT INTO contact_email_address (EMAIL_ADDRESS_ID, CONTACT_ID, ORDINAL_INDEX)
VALUES
  (20101, 20001, 0),
  (20102, 20001, 1),
  (20103, 20001, 2),
  (20104, 20002, 0),
  (20105, 20003, 0),
  (20106, 20003, 1),
  (20107, 20004, 0),
  (20108, 20004, 1);

