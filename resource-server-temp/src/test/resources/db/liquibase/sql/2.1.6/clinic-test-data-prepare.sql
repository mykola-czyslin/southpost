# prepare list of 5-10 clinics, users: 10009, 10012, 10014, 10015
# locations: 10001040, 10001042, 10001116, 10001075, 10001076, 10001077, 10001097, 10001129, 10001099
# dentistry: 10001084, 10001094, 10001107, 10001117
INSERT INTO `clinic` (ID, DISPLAY_NAME, SEARCH_NAME, CLINIC_TYPE, DESCRIPTION, LOCATION_ID, MODIFICATION_TIME, MODIFIED_BY_USER_ID)
VALUES (101, 'Нижньореґресіївська Поліклініка №1', 'НИЖНЬОРЕҐРЕСІЇВСЬКА ПОЛІКЛІНІКА №1', 'AMBULATORY', 'Селищна поліклініка №1', 10001040, CURRENT_TIMESTAMP(), 10009);
INSERT INTO `clinic` (ID, DISPLAY_NAME, SEARCH_NAME, CLINIC_TYPE, DESCRIPTION, LOCATION_ID, MODIFICATION_TIME, MODIFIED_BY_USER_ID)
VALUES (102, 'Нижньореґресіївська Клінічна Лікарня №2', 'НИЖНЬОРЕҐРЕСІЇВСЬКА КЛІНІЧНА ЛІКАРНЯ №2', 'STATIONARY', '', 10001042, CURRENT_TIMESTAMP(), 10009);
INSERT INTO `clinic` (ID, DISPLAY_NAME, SEARCH_NAME, CLINIC_TYPE, DESCRIPTION, LOCATION_ID, MODIFICATION_TIME, MODIFIED_BY_USER_ID)
VALUES (103, 'Нижньотестіївська Клінічна Лікарня №2', 'НИЖНЬОТЕСТІЇВСЬКА КЛІНІЧНА ЛІКАРНЯ №2', 'STATIONARY', '', 10001116, CURRENT_TIMESTAMP(), 10012);
INSERT INTO `clinic` (ID, DISPLAY_NAME, SEARCH_NAME, CLINIC_TYPE, DESCRIPTION, LOCATION_ID, MODIFICATION_TIME, MODIFIED_BY_USER_ID)
VALUES (104, 'Новоюніттестівська об''єднана клінічна лікарня №1', 'НОВОЮНІТТЕСТІВСЬКА ОБ''ЄДНАНА КЛІНІЧНА ЛІКАРНЯ №1', 'MIXED', '', 10001075, CURRENT_TIMESTAMP(), 10009);
INSERT INTO `clinic` (ID, DISPLAY_NAME, SEARCH_NAME, CLINIC_TYPE, DESCRIPTION, LOCATION_ID, MODIFICATION_TIME, MODIFIED_BY_USER_ID)
VALUES (105, 'Новоюніттестівський шкірно-венерологічний диспансер №5', 'НОВОЮНІТТЕСТІВСЬКИЙ ШКІРНО-ВЕНЕРОЛОГІЧНИЙ ДИСПАНСЕР №5', 'AMBULATORY', '', 10001076, CURRENT_TIMESTAMP(), 10014);
INSERT INTO `clinic` (ID, DISPLAY_NAME, SEARCH_NAME, CLINIC_TYPE, DESCRIPTION, LOCATION_ID, MODIFICATION_TIME, MODIFIED_BY_USER_ID)
VALUES (106, 'Новоюніттестівська стоматологічна поліклініка №3', 'НОВОЮНІТТЕСТІВСЬКА СТОМАТОЛОГІЧНА ПОЛІКЛІНІКА №3', 'AMBULATORY', '', 10001077, CURRENT_TIMESTAMP(), 10012);
INSERT INTO `clinic` (ID, DISPLAY_NAME, SEARCH_NAME, CLINIC_TYPE, DESCRIPTION, LOCATION_ID, MODIFICATION_TIME, MODIFIED_BY_USER_ID)
VALUES (107, 'Забутотрекінжанска сільська поліклініка №3', 'ЗАБУТОТРЕКІНЖАНСКА СІЛЬСЬКА ПОЛІКЛІНІКА №3', 'AMBULATORY', '', 10001097, CURRENT_TIMESTAMP(), 10009);
INSERT INTO `clinic` (ID, DISPLAY_NAME, SEARCH_NAME, CLINIC_TYPE, DESCRIPTION, LOCATION_ID, MODIFICATION_TIME, MODIFIED_BY_USER_ID)
VALUES (108, 'Малогноярська Клінічна Лікарня №8', 'МАЛОГНОЯРСЬКА КЛІНІЧНА ЛІКАРНЯ №8', 'STATIONARY', '', 10001129, CURRENT_TIMESTAMP(), 10015);
INSERT INTO `clinic` (ID, DISPLAY_NAME, SEARCH_NAME, CLINIC_TYPE, DESCRIPTION, LOCATION_ID, MODIFICATION_TIME, MODIFIED_BY_USER_ID)
VALUES (109, 'Гнійнокуренівський фельдшерський пункт №2101', 'ГНІЙНОКУРЕНІВСЬКИЙ ФЕЛЬДШЕРСЬКИЙ ПУНКТ №2101', 'MIXED', '', 10001136, CURRENT_TIMESTAMP(), 10012);
INSERT INTO `clinic` (ID, DISPLAY_NAME, SEARCH_NAME, CLINIC_TYPE, DESCRIPTION, LOCATION_ID, MODIFICATION_TIME, MODIFIED_BY_USER_ID)
VALUES (110, 'Стоматологічний кабінет "Лана", м. Старий Тестіїв', 'СТОМАТОЛОГІЧНИЙ КАБІНЕТ "ЛАНА", М. СТАРИЙ ТЕСТІЇВ', 'AMBULATORY', '', 10001084, CURRENT_TIMESTAMP(), 10009);
INSERT INTO `clinic` (ID, DISPLAY_NAME, SEARCH_NAME, CLINIC_TYPE, DESCRIPTION, LOCATION_ID, MODIFICATION_TIME, MODIFIED_BY_USER_ID)
VALUES (111, 'Стоматологічний кабінет "Чарівна посмішка", м. Старий Тестіїв', 'СТОМАТОЛОГІЧНИЙ КАБІНЕТ "ЧАРІВНА ПОСМІШКА", М. СТАРИЙ ТЕСТІЇВ', 'AMBULATORY', '', 10001094, CURRENT_TIMESTAMP(), 10012);
INSERT INTO `clinic` (ID, DISPLAY_NAME, SEARCH_NAME, CLINIC_TYPE, DESCRIPTION, LOCATION_ID, MODIFICATION_TIME, MODIFIED_BY_USER_ID)
VALUES (112, 'Стоматологічний кабінет "Мегалодон", м. Верхній Тестіїв', 'СТОМАТОЛОГІЧНИЙ КАБІНЕТ "МЕГАЛОДОН", М. ВЕРХНІЙ ТЕСТІЇВ', 'AMBULATORY', '', 10001107, CURRENT_TIMESTAMP(), 10014);
INSERT INTO `clinic` (ID, DISPLAY_NAME, SEARCH_NAME, CLINIC_TYPE, DESCRIPTION, LOCATION_ID, MODIFICATION_TIME, MODIFIED_BY_USER_ID)
VALUES (113, 'Стоматологічний кабінет "Смайлодон", СМТ Нижньотестіївське', 'СТОМАТОЛОГІЧНИЙ КАБІНЕТ "СМАЙЛОДОН", СМТ НИЖНЬОТЕСТІЇВСЬКЕ', 'AMBULATORY', '', 10001117, CURRENT_TIMESTAMP(), 10015);


INSERT INTO `clinic_service` (CLINIC_ID, SERVICE)
VALUES (101, 'SURGERY'),
       (101, 'GYNECOLOGY'),
       (101, 'GENERAL_THERAPY'),
       (101, 'GASTROENTEROLOGY'),
       (101, 'NEUROPATHOLOGY'),
       (101, 'OPHTHALMOLOGY'),
       (101, 'ENDOCRINOLOGY'),
       (101, 'UROLOGY'),
       (101, 'TRAUMATOLOGY'),
       (101, 'PSYCHIATRY'),
       (101, 'PULMONOLOGY'),
       (101, 'INFECTION_DECEASE_THERAPY'),
       (101, 'VENEREOLOGY'),
       (102, 'SURGERY'),
       (102, 'GYNECOLOGY'),
       (102, 'GENERAL_THERAPY'),
       (102, 'GASTROENTEROLOGY'),
       (102, 'ENDOCRINOLOGY'),
       (102, 'UROLOGY'),
       (102, 'TRAUMATOLOGY'),
       (102, 'PULMONOLOGY'),
       (102, 'INFECTION_DECEASE_THERAPY'),
       (102, 'VENEREOLOGY'), --
       (103, 'SURGERY'),
       (103, 'GYNECOLOGY'),
       (103, 'GENERAL_THERAPY'),
       (103, 'GASTROENTEROLOGY'),
       (103, 'ENDOCRINOLOGY'),
       (103, 'UROLOGY'),
       (103, 'TRAUMATOLOGY'),
       (103, 'PULMONOLOGY'),
       (103, 'INFECTION_DECEASE_THERAPY'),
       (103, 'VENEREOLOGY'),
       (104, 'SURGERY'),
       (104, 'GYNECOLOGY'),
       (104, 'GENERAL_THERAPY'),
       (104, 'GASTROENTEROLOGY'),
       (104, 'NEUROPATHOLOGY'),
       (104, 'OPHTHALMOLOGY'),
       (104, 'ENDOCRINOLOGY'),
       (104, 'UROLOGY'),
       (104, 'TRAUMATOLOGY'),
       (104, 'PSYCHIATRY'),
       (104, 'PULMONOLOGY'),
       (104, 'INFECTION_DECEASE_THERAPY'),
       (105, 'VENEREOLOGY'),
       (106, 'DENTISTRY'),
       (106, 'DENTAL_SURGERY'),
       (106, 'DENTAL_PROSTHESIS'),
       (107, 'SURGERY'),
       (107, 'GYNECOLOGY'),
       (107, 'GENERAL_THERAPY'),
       (107, 'GASTROENTEROLOGY'),
       (107, 'NEUROPATHOLOGY'),
       (107, 'OPHTHALMOLOGY'),
       (107, 'ENDOCRINOLOGY'),
       (107, 'UROLOGY'),
       (107, 'TRAUMATOLOGY'),
       (107, 'PSYCHIATRY'),
       (107, 'PULMONOLOGY'),
       (107, 'INFECTION_DECEASE_THERAPY'),
       (107, 'VENEREOLOGY'),
       (108, 'SURGERY'),
       (108, 'GYNECOLOGY'),
       (108, 'GENERAL_THERAPY'),
       (108, 'GASTROENTEROLOGY'),
       (108, 'ENDOCRINOLOGY'),
       (108, 'UROLOGY'),
       (108, 'TRAUMATOLOGY'),
       (108, 'PULMONOLOGY'),
       (108, 'INFECTION_DECEASE_THERAPY'),
       (108, 'VENEREOLOGY'),
       (109, 'TRAUMATOLOGY'),
       (109, 'GYNECOLOGY'),
       (109, 'GENERAL_THERAPY'),
       (110, 'DENTISTRY'),
       (110, 'DENTAL_PROSTHESIS'),
       (111, 'DENTISTRY'),
       (111, 'DENTAL_PROSTHESIS'),
       (112, 'DENTISTRY'),
       (113, 'DENTISTRY'),
       (113, 'DENTAL_PROSTHESIS');

# phones
INSERT INTO `phone` (ID, DISPLAY_PHONE_NUMBER, SEARCH_PHONE_NUMBER, DESCRIPTION)
VALUES (3000001, '+38 (071) 991-01-01', '+380719910101', 'Нижньореґресіївська Поліклініка №1, реєстратура'),
       (3000002, '+38 (071) 992-01-01', '+380719920101', 'Нижньореґресіївська Клінічна Лікарня №2, приймальне відділення'),
       (3000003, '+38 (072) 991-02-01', '+380729910201', 'Нижньотестіївська Клінічна Лікарня №2, приймальне відділення'),
       (3000004, '+38 (073) 991-03-01', '+380739910301', 'Новоюніттестівська об''єднана клінічна лікарня №1, реєстратура'),
       (3000005, '+38 (073) 991-03-02', '+380739910302', 'Новоюніттестівська об''єднана клінічна лікарня №1, приймальне відділення'),
       (3000006, '+38 (073) 992-01-01', '+380739920101', 'Новоюніттестівський шкірно-венерологічний диспансер №5, реєстратура'),
       (3000007, '+38 (073) 993-01-01', '+380739930101', 'Новоюніттестівська стоматологічна поліклініка №3, реєстратура'),
       (3000008, '+38 (074) 991-01-01', '+380749910101', 'Забутотрекінжанска сільська поліклініка №3, реєстратура'),
       (3000009, '+38 (075) 991-01-01', '+380759910101', 'Малогноярська Клінічна Лікарня №8, приймальне відділення'),
       (3000010, '+38 (076) 992-01-01', '+380769920101', 'Гнійнокуренівський фельдшерський пункт №2101'),
       (3000011, '+38 (077) 881-01-01', '+380778810101', 'Стоматологічний кабінет "Лана", м. Старий Тестіїв'),
       (3000012, '+38 (077) 882-07-89', '+380778820789', 'Стоматологічний кабінет "Чарівна посмішка", м. Старий Тестіїв'),
       (3000013, '+38 (078) 881-81-01', '+380788818101', 'Стоматологічний кабінет "Мегалодон", м. Верхній Тестіїв'),
       (3000014, '+38 (078) 882-91-01', '+380788829101', 'Стоматологічний кабінет "Смайлодон", СМТ Нижньотестіївське');

# clinic phones
INSERT INTO `clinic_phones` (PHONE_ID, CLINIC_ID, ORDINAL_INDEX)
VALUES (3000001, 101, 0),
       (3000002, 102, 0),
       (3000003, 103, 0),
       (3000004, 104, 0),
       (3000005, 104, 1),
       (3000006, 105, 0),
       (3000007, 106, 0),
       (3000008, 107, 0),
       (3000009, 108, 0),
       (3000010, 109, 0),
       (3000011, 110, 0),
       (3000012, 111, 0),
       (3000013, 112, 0),
       (3000014, 113, 0);

# emails
INSERT INTO `email_address` (ID, EMAIL_ADDRESS, DESCRIPTION)
VALUES (3000001, 'registry@poliklinik1.nyzhni-regresiji.net', 'Нижньореґресіївська Поліклініка №1, реєстратура'),
       (3000002, 'reception@likarnia2.nyzhni-rergresiji.net', 'Нижньореґресіївська Клінічна Лікарня №2, приймальне відділення'),
       (3000003, 'reception@likarnia2.nyzhnij-testijiw.net', 'Нижньотестіївська Клінічна Лікарня №2, приймальне відділення'),
       (3000004, 'rejestry@objednana-likarnia-1.nowi-junit-testy.net', 'Новоюніттестівська об''єднана клінічна лікарня №1, реєстратура'),
       (3000005, 'reception@objednana-likarnia-1.nowi-junit-testy.net', 'Новоюніттестівська об''єднана клінічна лікарня №1, приймальне відділення'),
       (3000006, 'rejestry@dermato-venerologichnyj-dyspanser-5.nowi-unit-testy.net', 'Новоюніттестівський шкірно-венерологічний диспансер №5, реєстратура'),
       (3000007, 'rejestry@stomat-poliklinika-3.novi-unit-testy.net', 'Новоюніттестівська стоматологічна поліклініка №3, реєстратура'),
       (3000008, 'rejestry@poliklinika-3.zabuti-trekingy.net', 'Забутотрекінжанска сільська поліклініка №3, реєстратура'),
       (3000009, 'reception@likarnia-8.mala-gnojarka.net', 'Малогноярська Клінічна Лікарня №8, приймальне відділення'),
       (3000010, 'contact@feldsher-2101.gnijni-kureni.net', 'Гнійнокуренівський фельдшерський пункт №2101'),
       (3000011, 'lana-dent78@mymail.com', 'Стоматологічний кабінет "Лана", м. Старий Тестіїв'),
       (3000012, 'charming-smile123@mymail.com', 'Стоматологічний кабінет "Чарівна посмішка", м. Старий Тестіїв'),
       (3000013, 'megalodon-werchniogo-testijiwa@mymail.com', 'Стоматологічний кабінет "Мегалодон", м. Верхній Тестіїв'),
       (3000014, 'smilodon-998@mymail.com', 'Стоматологічний кабінет "Смайлодон", СМТ Нижньотестіївське');

INSERT INTO `clinic_emails` (EMAIL_ADDRESS_ID, CLINIC_ID, ORDINAL_INDEX)
VALUES (3000001, 101, 0),
       (3000002, 102, 0),
       (3000003, 103, 0),
       (3000004, 104, 0),
       (3000005, 104, 1),
       (3000006, 105, 0),
       (3000007, 106, 0),
       (3000008, 107, 0),
       (3000009, 108, 0),
       (3000010, 109, 0),
       (3000011, 110, 0),
       (3000012, 111, 0),
       (3000013, 112, 0),
       (3000014, 113, 0);