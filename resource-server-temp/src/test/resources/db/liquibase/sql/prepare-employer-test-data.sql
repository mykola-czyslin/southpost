INSERT INTO employer (ID, DISPLAY_NAME, SEARCH_VALUE, SETTLEMENT_ID, WEBSITE)
VALUES
  (1001, 'Верхньотестіївська міська лікарня №1', 'ВЕРХНЬОТЕСТІЇВСЬКА МІСЬКА ЛІКАРНЯ №1', 20010010, 'http://likarnia.1.testijiw.gov.ua'), -- Верхній Тестіїв
  (1002, 'Завод "Метеор"', 'ЗАВОД "МЕТЕОР"', 20010004, 'http://www.meteor.plant.con.ua/'), -- Новий Тестіів
  (1003, 'Кафе "Дніпровська Хвиля"', 'КАФЕ "ДНІПРОВСЬКА ХВИЛЯ"', 20010005, NULL), -- Верхні Забрьохи
  (1004, 'Салон краси "Лілея"', 'САЛОН КРАСИ "ЛІЛЕЯ"', 20010010, 'http://www.lileya.beauty.com.ua/'); -- Верхній Тестіїв

INSERT INTO employer_contact(CONTACT_ID, EMPLOYER_ID, ORDINAL_INDEX)
VALUES
  (20001, 1001, 0),
  (20002, 1002, 0),
  (20003, 1003, 0),
  (20004, 1004, 0);



