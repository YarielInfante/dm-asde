--liquibase formatted SQL

--changeset yinfante:1
CREATE TABLE case_type
(
  id          BIGINT(20) PRIMARY KEY NOT NULL,
  description VARCHAR(255),
  name        VARCHAR(50)
);
CREATE TABLE status
(
  id          BIGINT(20) PRIMARY KEY NOT NULL,
  description VARCHAR(255),
  name        VARCHAR(50)
);

CREATE TABLE user
(
  id        BIGINT(20) PRIMARY KEY NOT NULL,
  active    BIT(1)                 NOT NULL,
  blocked   BIT(1)                 NOT NULL,
  email     VARCHAR(30)            NOT NULL,
  last_name VARCHAR(50),
  name      VARCHAR(50),
  password  VARCHAR(255)           NOT NULL,
  user_type VARCHAR(50)            NOT NULL
);
CREATE UNIQUE INDEX UK_ob8kqyqqgmefl0aco34akdtpe ON user (email);


CREATE TABLE `dm_case` (
  `id`           BIGINT(20) NOT NULL,
  `address`      VARCHAR(255) DEFAULT NULL,
  `date`         DATETIME     DEFAULT NULL,
  `description`  VARCHAR(255) DEFAULT NULL,
  `lat`          FLOAT        DEFAULT NULL,
  `lng`          FLOAT        DEFAULT NULL,
  `case_type_id` BIGINT(20) NOT NULL,
  `status_id`    BIGINT(20) NOT NULL,
  `user_id`      BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK14x9k4dwk7wto6bcbvuyis051` (`case_type_id`),
  KEY `FKtibrtoigme5l9d7ejip1uxwo8` (`status_id`),
  KEY `FK5ph39o2v8mx0v8dqqvxis7nnu` (`user_id`),
  CONSTRAINT `FK14x9k4dwk7wto6bcbvuyis051` FOREIGN KEY (`case_type_id`) REFERENCES `case_type` (`id`),
  CONSTRAINT `FK5ph39o2v8mx0v8dqqvxis7nnu` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKtibrtoigme5l9d7ejip1uxwo8` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;


CREATE TABLE hibernate_sequence
(
  next_val BIGINT(20)
);
CREATE TABLE role
(
  id          BIGINT(20) PRIMARY KEY NOT NULL,
  description VARCHAR(255),
  name        VARCHAR(50),
  system_role BIT(1)                 NOT NULL
);

CREATE TABLE user_role
(
  id      BIGINT(20) PRIMARY KEY NOT NULL,
  role_id BIGINT(20)             NOT NULL,
  user_id BIGINT(20)             NOT NULL,
  CONSTRAINT FK859n2jvi8ivhui0rl0esws6o FOREIGN KEY (user_id) REFERENCES user (id),
  CONSTRAINT FKa68196081fvovjhkek5m97n3y FOREIGN KEY (role_id) REFERENCES role (id)
);
CREATE INDEX FK859n2jvi8ivhui0rl0esws6o ON user_role (user_id);
CREATE INDEX FKa68196081fvovjhkek5m97n3y ON user_role (role_id);

--changeset yinfante:2
INSERT INTO dm.user (id, active, blocked, email, last_name, NAME, PASSWORD, user_type) VALUES (1, TRUE, FALSE, 'yarielinfante@gmail.com', 'Infante', 'Yariel', 'sha1:64000:18:GKfdOwLNi+H0O4+OmBHYe3SHPzBI+lwe:ZqMkqvKlAZlw8j1ds+3AIape', 0);
INSERT INTO dm.status (id, description, name) VALUES (1, 'Nuevo', 'Nuevo');
INSERT INTO dm.case_type (id, description, name) VALUES (1, 'Vertedero', 'Vertedero');
INSERT INTO dm.case_type (id, description, name) VALUES (2, 'No Pasa el Camión', 'No Pasa el Camión');
INSERT INTO dm.case_type (id, description, name) VALUES (3, 'Parque Descuidado', 'Parque Descuidado');
INSERT INTO dm.case_type (id, description, name) VALUES (4, 'Calles no Asfaltadas', 'Calles no Asfaltadas');
INSERT INTO dm.case_type (id, description, name) VALUES (5, 'Escombros', 'Escombros');
INSERT INTO dm.case_type (id, description, name) VALUES (6, 'Alumbrado de Parque.', 'Alumbrado de Parque.');
INSERT INTO dm.case_type (id, description, name) VALUES (7, 'Drenaje Tapado.', 'Drenaje Tapado.');
INSERT INTO dm.case_type (id, description, name) VALUES (8, 'Semáforo Dañado', 'Semáforo Dañado');
INSERT INTO dm.case_type (id, description, name) VALUES (9, 'Arboles Caídos', 'Arboles Caídos');
INSERT INTO dm.case_type (id, description, name) VALUES (10, 'Otros', 'Otros');
INSERT INTO dm.dm_case (id, address, date, description, lat, lng, case_type_id, status_id, user_id)
VALUES (1, 'Invivienda', NOW(), 'no'' ta llevando el ...', 2313.21, 213.231, 1, 1, 1);
INSERT INTO dm.dm_case (id, address, date, description, lat, lng, case_type_id, status_id, user_id)
VALUES (4, 'Los mina updated', NOW(), 'no'' ta llevando el ...', 2313.21, 213.231, 1, 1, 1);
INSERT INTO dm.dm_case (id, address, date, description, lat, lng, case_type_id, status_id, user_id)
VALUES (5, 'Los mina updated', NOW(), 'no'' ta llevando el ...', 2313.21, 213.231, 1, 1, 1);
INSERT INTO dm.dm_case (id, address, date, description, lat, lng, case_type_id, status_id, user_id)
VALUES (6, 'Los mina updated', NOW(), 'no'' ta llevando el ...', 2313.21, 213.231, 1, 1, 1);
INSERT INTO dm.dm_case (id, address, date, description, lat, lng, case_type_id, status_id, user_id)
VALUES (7, 'Los mina updated', NOW(), 'no'' ta llevando el ...', 2313.21, 213.231, 1, 1, 1);
INSERT INTO dm.dm_case (id, address, date, description, lat, lng, case_type_id, status_id, user_id)
VALUES (8, 'Los mina updated', NOW(), 'no'' ta llevando el ...', 2313.21, 213.231, 1, 1, 1);
INSERT INTO dm.dm_case (id, address, date, description, lat, lng, case_type_id, status_id, user_id)
VALUES (9, 'Los mina updated', NOW(), 'no'' ta llevando el ...', 2313.21, 213.231, 1, 1, 1);
INSERT INTO dm.dm_case (id, address, date, description, lat, lng, case_type_id, status_id, user_id)
VALUES (10, 'Los mina updated', NOW(), 'no'' ta llevando el ...', 2313.21, 213.231, 1, 1, 1);
INSERT INTO dm.dm_case (id, address, date, description, lat, lng, case_type_id, status_id, user_id)
VALUES (11, 'Los mina updated', NOW(), 'no'' ta llevando el ...', 2313.21, 213.231, 1, 1, 1);
INSERT INTO dm.dm_case (id, address, date, description, lat, lng, case_type_id, status_id, user_id)
VALUES (12, 'Los mina updated', NOW(), 'no'' ta llevando el ...', 2313.21, 213.231, 1, 1, 1);
INSERT INTO dm.dm_case (id, address, date, description, lat, lng, case_type_id, status_id, user_id)
VALUES (13, 'Los mina updated', NOW(), 'no'' ta llevando el ...', 2313.21, 213.231, 1, 1, 1);
INSERT INTO dm.dm_case (id, address, date, description, lat, lng, case_type_id, status_id, user_id)
VALUES (14, 'Los mina updated', NOW(), 'no'' ta llevando el ...', 2313.21, 213.231, 1, 1, 1);
INSERT INTO dm.dm_case (id, address, date, description, lat, lng, case_type_id, status_id, user_id)
VALUES (15, 'Los mina updated', NOW(), 'no'' ta llevando el ...', 2313.21, 213.231, 1, 1, 1);
INSERT INTO dm.dm_case (id, address, date, description, lat, lng, case_type_id, status_id, user_id)
VALUES (16, 'Los mina updated', NOW(), 'no'' ta llevando el ...', 2313.21, 213.231, 1, 1, 1);
INSERT INTO dm.dm_case (id, address, date, description, lat, lng, case_type_id, status_id, user_id)
VALUES (17, 'Los mina updated', NOW(), 'no'' ta llevando el ...', 2313.21, 213.231, 1, 1, 1);
INSERT INTO dm.dm_case (id, address, date, description, lat, lng, case_type_id, status_id, user_id)
VALUES (18, 'Los mina updated', NOW(), 'no'' ta llevando el ...', 2313.21, 213.231, 1, 1, 1);
INSERT INTO dm.dm_case (id, address, date, description, lat, lng, case_type_id, status_id, user_id)
VALUES (19, 'Los mina updated', NOW(), 'no'' ta llevando el ...', 2313.21, 213.231, 1, 1, 1);
INSERT INTO dm.dm_case (id, address, date, description, lat, lng, case_type_id, status_id, user_id)
VALUES (20, 'Los mina updated', NOW(), 'no'' ta llevando el ...', 2313.21, 213.231, 1, 1, 1);
INSERT INTO dm.dm_case (id, address, date, description, lat, lng, case_type_id, status_id, user_id)
VALUES (21, 'Los mina updated', NOW(), 'no'' ta llevando el ...', 2313.21, 213.231, 1, 1, 1);
INSERT INTO dm.dm_case (id, address, date, description, lat, lng, case_type_id, status_id, user_id)
VALUES (22, 'Los mina updated', NOW(), 'no'' ta llevando el ...', 2313.21, 213.231, 1, 1, 1);
INSERT INTO dm.dm_case (id, address, date, description, lat, lng, case_type_id, status_id, user_id)
VALUES (23, 'Los mina updated', NOW(), 'no'' ta llevando el ...', 2313.21, 213.231, 1, 1, 1);
INSERT INTO dm.dm_case (id, address, date, description, lat, lng, case_type_id, status_id, user_id)
VALUES (24, 'Los mina updated', NOW(), 'no'' ta llevando el ...', 2313.21, 213.231, 1, 1, 1);
INSERT INTO dm.dm_case (id, address, date, description, lat, lng, case_type_id, status_id, user_id)
VALUES (25, 'Los mina updated', NOW(), 'no'' ta llevando el ...', 2313.21, 213.231, 1, 1, 1);
INSERT INTO dm.dm_case (id, address, date, description, lat, lng, case_type_id, status_id, user_id)
VALUES (26, 'Los mina updated', NOW(), 'no'' ta llevando el ...', 2313.21, 213.231, 1, 1, 1);
INSERT INTO dm.dm_case (id, address, date, description, lat, lng, case_type_id, status_id, user_id)
VALUES (27, 'Los mina updated', NOW(), 'no'' ta llevando el ...', 2313.21, 213.231, 1, 1, 1);
INSERT INTO dm.dm_case (id, address, date, description, lat, lng, case_type_id, status_id, user_id)
VALUES (28, 'Los mina updated', NOW(), 'no'' ta llevando el ...', 2313.21, 213.231, 1, 1, 1);
INSERT INTO dm.dm_case (id, address, date, description, lat, lng, case_type_id, status_id, user_id)
VALUES (29, 'Los mina updated', NOW(), 'no'' ta llevando el ...', 2313.21, 213.231, 1, 1, 1);
INSERT INTO dm.dm_case (id, address, date, description, lat, lng, case_type_id, status_id, user_id)
VALUES (30, 'Los mina updated', NOW(), 'no'' ta llevando el ...', 2313.21, 213.231, 1, 1, 1);
INSERT INTO dm.dm_case (id, address, date, description, lat, lng, case_type_id, status_id, user_id)
VALUES (31, 'Los mina updated', NOW(), 'no'' ta llevando el ...', 2313.21, 213.231, 1, 1, 1);

--changeset yinfante:3
INSERT INTO hibernate_sequence VALUES (2);

--changeset yinfante:4
CREATE TABLE `case_attachment` (
  `id`      BIGINT(20) NOT NULL,
  `type`    INT(1)     NOT NULL
  COMMENT '0-imagen  1-video  2- audio  3-otros',
  `url`     VARCHAR(255) DEFAULT NULL,
  `case_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlddke1cw80p8k2ijqye75vhey` (`case_id`),
  CONSTRAINT `FKlddke1cw80p8k2ijqye75vhey` FOREIGN KEY (`case_id`) REFERENCES `dm_case` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1