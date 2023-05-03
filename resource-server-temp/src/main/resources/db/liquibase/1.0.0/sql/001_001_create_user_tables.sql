CREATE TABLE user (
  ID SERIAL,
  LOGIN             VARCHAR(20) CHARSET UTF8 COLLATE utf8_unicode_ci NOT NULL,
  EMAIL             VARCHAR(64) CHARSET UTF8 COLLATE utf8_unicode_ci NOT NULL,
  PASSWORD          VARCHAR(256) CHARSET UTF8 NOT NULL,
  FIRST_NAME        VARCHAR(40) CHARSET UTF8 COLLATE utf8_unicode_ci NOT NULL,
  LAST_NAME         VARCHAR(40) CHARSET UTF8 COLLATE utf8_unicode_ci NOT NULL,
  REGISTERED_AT     DATETIME NOT NULL,
  CONSTRAINT USER_P PRIMARY KEY (ID),
  CONSTRAINT USER_LOGIN_U UNIQUE(LOGIN),
  CONSTRAINT USER_EMAIL_U UNIQUE(EMAIL),
  INDEX user_first_name_i (FIRST_NAME),
  INDEX user_last_name_i (LAST_NAME),
  INDEX user_full_name_i (FIRST_NAME, LAST_NAME)
) ENGINE=INNODB;

CREATE TABLE user_role (
  USER_ID BIGINT UNSIGNED NOT NULL,
  ROLE_NAME ENUM ('VIEWER', 'OPERATOR', 'ADMINISTRATOR', 'SUPERVISOR'),
  CONSTRAINT user_role_p PRIMARY KEY (USER_ID, ROLE_NAME),
  CONSTRAINT user_role_user_f FOREIGN KEY(USER_ID)
    REFERENCES user(id) ON DELETE CASCADE
) ENGINE = INNODB;
