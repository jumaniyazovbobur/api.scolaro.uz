CREATE TABLE app_application
(
    id                              VARCHAR(255) NOT NULL,
    visible                         BOOLEAN,
    created_date                    TIMESTAMP WITHOUT TIME ZONE,
    deleted_date                    TIMESTAMP WITHOUT TIME ZONE,
    deleted_id                      VARCHAR(255),
    student_id                      VARCHAR(255),
    consulting_id                   VARCHAR(255),
    consulting_profile_id           VARCHAR(255),
    university_id                   BIGINT,
    status                          VARCHAR(255),
    started_date                    TIMESTAMP WITHOUT TIME ZONE,
    finished_date                   TIMESTAMP WITHOUT TIME ZONE,
    canceled_date                   TIMESTAMP WITHOUT TIME ZONE,
    consulting_tariff_id            VARCHAR(255),
    consulting_step_id              VARCHAR(255),
    consulting_step_level_id        VARCHAR(255),
    consulting_step_level_status_id VARCHAR(255),
    application_number              BIGINT,
    CONSTRAINT pk_app_application PRIMARY KEY (id)
);

CREATE TABLE app_application_level_attach
(
    id                       VARCHAR(255) NOT NULL,
    visible                  BOOLEAN,
    created_date             TIMESTAMP WITHOUT TIME ZONE,
    deleted_date             TIMESTAMP WITHOUT TIME ZONE,
    deleted_id               VARCHAR(255),
    consulting_step_level_id VARCHAR(255),
    attach_id                VARCHAR(255),
    attach_type              VARCHAR(255),
    CONSTRAINT pk_app_application_level_attach PRIMARY KEY (id)
);

CREATE TABLE app_application_level_status
(
    id                            VARCHAR(255) NOT NULL,
    visible                       BOOLEAN,
    created_date                  TIMESTAMP WITHOUT TIME ZONE,
    deleted_date                  TIMESTAMP WITHOUT TIME ZONE,
    deleted_id                    VARCHAR(255),
    app_application_id            VARCHAR(255),
    consulting_step_level_id      VARCHAR(255),
    application_step_level_status VARCHAR(255),
    deadline                      date,
    description                   VARCHAR(255),
    payment_date                  TIMESTAMP WITHOUT TIME ZONE,
    amount                        BIGINT,
    CONSTRAINT pk_app_application_level_status PRIMARY KEY (id)
);

CREATE TABLE app_sms
(
    id           VARCHAR(255) NOT NULL,
    phone        VARCHAR(255),
    content      VARCHAR(255),
    status       VARCHAR(255),
    type         VARCHAR(255),
    sms_code     VARCHAR(255),
    created_date TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_app_sms PRIMARY KEY (id)
);

CREATE TABLE attach
(
    id           VARCHAR(255) NOT NULL,
    create_id    VARCHAR(255),
    path         VARCHAR(255),
    extension    VARCHAR(255),
    origen_name  VARCHAR(255),
    size         BIGINT,
    created_date TIMESTAMP WITHOUT TIME ZONE,
    visible      BOOLEAN,
    CONSTRAINT pk_attach PRIMARY KEY (id)
);

CREATE TABLE consulting
(
    id           VARCHAR(255) NOT NULL,
    visible      BOOLEAN,
    created_date TIMESTAMP WITHOUT TIME ZONE,
    deleted_date TIMESTAMP WITHOUT TIME ZONE,
    deleted_id   VARCHAR(255),
    name         VARCHAR(255),
    address      TEXT,
    status       VARCHAR(255),
    photo_id     VARCHAR(255),
    about        TEXT,
    balance      BIGINT,
    manager_id   VARCHAR(255),
    CONSTRAINT pk_consulting PRIMARY KEY (id)
);

CREATE TABLE consulting_comment
(
    id            VARCHAR(255) NOT NULL,
    visible       BOOLEAN,
    created_date  TIMESTAMP WITHOUT TIME ZONE,
    deleted_date  TIMESTAMP WITHOUT TIME ZONE,
    deleted_id    VARCHAR(255),
    content       TEXT,
    consulting_id VARCHAR(255),
    student_id    VARCHAR(255),
    CONSTRAINT pk_consulting_comment PRIMARY KEY (id)
);

CREATE TABLE consulting_profile
(
    id            VARCHAR(255) NOT NULL,
    visible       BOOLEAN,
    created_date  TIMESTAMP WITHOUT TIME ZONE,
    deleted_date  TIMESTAMP WITHOUT TIME ZONE,
    deleted_id    VARCHAR(255),
    fire_base_id  VARCHAR(255),
    name          VARCHAR(255),
    surname       VARCHAR(255),
    phone         VARCHAR(255),
    password      VARCHAR(255),
    status        VARCHAR(255),
    photo_id      VARCHAR(255),
    temp_phone    VARCHAR(255),
    temp_sms_code VARCHAR(255),
    country_id    BIGINT,
    adress        TEXT,
    consulting_id VARCHAR(255),
    is_online     BOOLEAN,
    lang          VARCHAR(255),
    CONSTRAINT pk_consulting_profile PRIMARY KEY (id)
);

CREATE TABLE consulting_step
(
    id            VARCHAR(255) NOT NULL,
    visible       BOOLEAN,
    created_date  TIMESTAMP WITHOUT TIME ZONE,
    deleted_date  TIMESTAMP WITHOUT TIME ZONE,
    deleted_id    VARCHAR(255),
    name          VARCHAR(255),
    step_type     VARCHAR(255),
    order_number  INTEGER,
    description   TEXT,
    consulting_id VARCHAR(255),
    CONSTRAINT pk_consulting_step PRIMARY KEY (id)
);

CREATE TABLE consulting_step_level
(
    id                 VARCHAR(255) NOT NULL,
    visible            BOOLEAN,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    deleted_date       TIMESTAMP WITHOUT TIME ZONE,
    deleted_id         VARCHAR(255),
    name_uz            VARCHAR(255),
    name_ru            VARCHAR(255),
    name_en            VARCHAR(255),
    order_number       INTEGER,
    description_uz     VARCHAR(255),
    description_ru     VARCHAR(255),
    description_en     VARCHAR(255),
    consulting_step_id VARCHAR(255),
    consulting_id      VARCHAR(255),
    started_date       TIMESTAMP WITHOUT TIME ZONE,
    finished_date      TIMESTAMP WITHOUT TIME ZONE,
    step_level_status  VARCHAR(255),
    CONSTRAINT pk_consulting_step_level PRIMARY KEY (id)
);

CREATE TABLE consulting_tariff
(
    id             VARCHAR(255) NOT NULL,
    visible        BOOLEAN,
    created_date   TIMESTAMP WITHOUT TIME ZONE,
    deleted_date   TIMESTAMP WITHOUT TIME ZONE,
    deleted_id     VARCHAR(255),
    description_uz TEXT,
    description_ru TEXT,
    description_en TEXT,
    name           TEXT,
    price          DOUBLE PRECISION,
    consulting_id  VARCHAR(255),
    status         VARCHAR(255),
    tariff_type    VARCHAR(255),
    order_number   INTEGER,
    CONSTRAINT pk_consulting_tariff PRIMARY KEY (id)
);

CREATE TABLE consulting_university
(
    id            VARCHAR(255) NOT NULL,
    visible       BOOLEAN,
    created_date  TIMESTAMP WITHOUT TIME ZONE,
    deleted_date  TIMESTAMP WITHOUT TIME ZONE,
    deleted_id    VARCHAR(255),
    consulting_id VARCHAR(255),
    university_id BIGINT,
    CONSTRAINT pk_consulting_university PRIMARY KEY (id)
);

CREATE TABLE continent
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    visible      BOOLEAN,
    created_date TIMESTAMP WITHOUT TIME ZONE,
    deleted_date TIMESTAMP WITHOUT TIME ZONE,
    created_id   VARCHAR(255),
    deleted_id   VARCHAR(255),
    name_uz      VARCHAR(255),
    name_ru      VARCHAR(255),
    name_en      VARCHAR(255),
    order_number INTEGER,
    CONSTRAINT pk_continent PRIMARY KEY (id)
);

CREATE TABLE continent_country
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    visible      BOOLEAN,
    created_date TIMESTAMP WITHOUT TIME ZONE,
    deleted_date TIMESTAMP WITHOUT TIME ZONE,
    created_id   VARCHAR(255),
    deleted_id   VARCHAR(255),
    continent_id BIGINT,
    country_id   BIGINT,
    order_number INTEGER,
    CONSTRAINT pk_continent_country PRIMARY KEY (id)
);

CREATE TABLE country
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    visible      BOOLEAN,
    created_date TIMESTAMP WITHOUT TIME ZONE,
    deleted_date TIMESTAMP WITHOUT TIME ZONE,
    created_id   VARCHAR(255),
    deleted_id   VARCHAR(255),
    name_uz      VARCHAR(255),
    name_ru      VARCHAR(255),
    name_en      VARCHAR(255),
    CONSTRAINT pk_country PRIMARY KEY (id)
);

CREATE TABLE district
(
    id           INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    region_id    INTEGER,
    name_uz      VARCHAR(255),
    name_ru      VARCHAR(255),
    name_en      VARCHAR(255),
    created_date TIMESTAMP WITHOUT TIME ZONE,
    visible      BOOLEAN,
    county       VARCHAR(255),
    CONSTRAINT pk_district PRIMARY KEY (id)
);

CREATE TABLE faculty
(
    id           VARCHAR(255) NOT NULL,
    visible      BOOLEAN,
    created_date TIMESTAMP WITHOUT TIME ZONE,
    deleted_date TIMESTAMP WITHOUT TIME ZONE,
    deleted_id   VARCHAR(255),
    name_uz      VARCHAR(255),
    name_ru      VARCHAR(255),
    name_en      VARCHAR(255),
    parent_id    VARCHAR(255),
    order_number INTEGER,
    CONSTRAINT pk_faculty PRIMARY KEY (id)
);

CREATE TABLE feedback
(
    id            VARCHAR(255) NOT NULL,
    visible       BOOLEAN,
    created_date  TIMESTAMP WITHOUT TIME ZONE,
    deleted_date  TIMESTAMP WITHOUT TIME ZONE,
    deleted_id    VARCHAR(255),
    content       TEXT,
    person_id     VARCHAR(255),
    feedback_type VARCHAR(255),
    CONSTRAINT pk_feedback PRIMARY KEY (id)
);

CREATE TABLE land
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    visible      BOOLEAN,
    created_date TIMESTAMP WITHOUT TIME ZONE,
    deleted_date TIMESTAMP WITHOUT TIME ZONE,
    created_id   VARCHAR(255),
    deleted_id   VARCHAR(255),
    name         VARCHAR(255),
    order_by     INTEGER,
    CONSTRAINT pk_land PRIMARY KEY (id)
);

CREATE TABLE land_country
(
    id           VARCHAR(255) NOT NULL,
    visible      BOOLEAN,
    created_date TIMESTAMP WITHOUT TIME ZONE,
    deleted_date TIMESTAMP WITHOUT TIME ZONE,
    deleted_id   VARCHAR(255),
    land_id      BIGINT,
    country_id   BIGINT,
    CONSTRAINT pk_land_country PRIMARY KEY (id)
);

CREATE TABLE notification_history
(
    id                VARCHAR(255) NOT NULL,
    visible           BOOLEAN,
    created_date      TIMESTAMP WITHOUT TIME ZONE,
    deleted_date      TIMESTAMP WITHOUT TIME ZONE,
    deleted_id        VARCHAR(255),
    title             VARCHAR(255),
    body              VARCHAR(255),
    to_profile_id     VARCHAR(255),
    from_profile_id   VARCHAR(255),
    to_profile_type   VARCHAR(255),
    from_profile_type VARCHAR(255),
    firebase_token    VARCHAR(255),
    type              SMALLINT,
    is_read           BOOLEAN,
    data              TEXT,
    CONSTRAINT pk_notification_history PRIMARY KEY (id)
);

CREATE TABLE person_role
(
    id           VARCHAR(255) NOT NULL,
    visible      BOOLEAN,
    created_date TIMESTAMP WITHOUT TIME ZONE,
    deleted_date TIMESTAMP WITHOUT TIME ZONE,
    deleted_id   VARCHAR(255),
    person_id    VARCHAR(255),
    role         VARCHAR(255),
    CONSTRAINT pk_person_role PRIMARY KEY (id)
);

CREATE TABLE profile
(
    id            VARCHAR(255) NOT NULL,
    visible       BOOLEAN,
    created_date  TIMESTAMP WITHOUT TIME ZONE,
    deleted_date  TIMESTAMP WITHOUT TIME ZONE,
    deleted_id    VARCHAR(255),
    fire_base_id  VARCHAR(255),
    name          VARCHAR(255),
    surname       VARCHAR(255),
    phone         VARCHAR(255),
    password      VARCHAR(255),
    status        VARCHAR(255),
    photo_id      VARCHAR(255),
    temp_phone    VARCHAR(255),
    temp_sms_code VARCHAR(255),
    nick_name     VARCHAR(255),
    country_id    BIGINT,
    adress        TEXT,
    gender        VARCHAR(255),
    balance       BIGINT,
    is_online     BOOLEAN,
    lang          VARCHAR(255),
    CONSTRAINT pk_profile PRIMARY KEY (id)
);

CREATE TABLE region
(
    id           INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name_uz      VARCHAR(255),
    name_ru      VARCHAR(255),
    name_en      VARCHAR(255),
    created_date TIMESTAMP WITHOUT TIME ZONE,
    visible      BOOLEAN,
    CONSTRAINT pk_region PRIMARY KEY (id)
);

CREATE TABLE scholar_ship
(
    id            VARCHAR(255) NOT NULL,
    visible       BOOLEAN,
    created_date  TIMESTAMP WITHOUT TIME ZONE,
    deleted_date  TIMESTAMP WITHOUT TIME ZONE,
    deleted_id    VARCHAR(255),
    name          VARCHAR(255),
    description   TEXT,
    expired_date  date,
    start_date    date,
    creator_id    VARCHAR(255),
    university_id BIGINT,
    price         INTEGER,
    photo_id      VARCHAR(255),
    CONSTRAINT pk_scholar_ship PRIMARY KEY (id)
);

CREATE TABLE scholar_ship_degree_types
(
    id              VARCHAR(255) NOT NULL,
    visible         BOOLEAN,
    created_date    TIMESTAMP WITHOUT TIME ZONE,
    deleted_date    TIMESTAMP WITHOUT TIME ZONE,
    deleted_id      VARCHAR(255),
    scholar_ship_id VARCHAR(255),
    degree_type     VARCHAR(255),
    CONSTRAINT pk_scholar_ship_degree_types PRIMARY KEY (id)
);

CREATE TABLE simple_message
(
    id                    VARCHAR(255) NOT NULL,
    visible               BOOLEAN,
    created_date          TIMESTAMP WITHOUT TIME ZONE,
    deleted_date          TIMESTAMP WITHOUT TIME ZONE,
    deleted_id            VARCHAR(255),
    attach_id             VARCHAR(255),
    application_id        VARCHAR(255),
    profile_id            VARCHAR(255),
    consulting_id         VARCHAR(255),
    consulting_profile_id VARCHAR(255),
    is_student_read       BOOLEAN,
    is_consulting_read    BOOLEAN,
    message               TEXT,
    message_type          VARCHAR(255),
    CONSTRAINT pk_simple_message PRIMARY KEY (id)
);

CREATE TABLE sms_history
(
    id           VARCHAR(255) NOT NULL,
    visible      BOOLEAN,
    created_date TIMESTAMP WITHOUT TIME ZONE,
    deleted_date TIMESTAMP WITHOUT TIME ZONE,
    deleted_id   VARCHAR(255),
    sms_code     VARCHAR(255),
    phone        VARCHAR(255),
    type         VARCHAR(255),
    status       VARCHAR(255),
    sms_count    INTEGER,
    used_time    TIMESTAMP WITHOUT TIME ZONE,
    sms_text     TEXT,
    CONSTRAINT pk_sms_history PRIMARY KEY (id)
);

CREATE TABLE sms_token
(
    email        VARCHAR(255) NOT NULL,
    token        TEXT,
    created_date date,
    CONSTRAINT pk_sms_token PRIMARY KEY (email)
);

CREATE TABLE transactions
(
    id                    VARCHAR(255) NOT NULL,
    visible               BOOLEAN,
    created_date          TIMESTAMP WITHOUT TIME ZONE,
    deleted_date          TIMESTAMP WITHOUT TIME ZONE,
    deleted_id            VARCHAR(255),
    profile_id            VARCHAR(255),
    profile_type          VARCHAR(255),
    amount                BIGINT,
    payme_transactions_id VARCHAR(255),
    transaction_type      VARCHAR(255),
    status                VARCHAR(255),
    payment_type          VARCHAR(255),
    create_time           BIGINT,
    perform_time          TIMESTAMP WITHOUT TIME ZONE,
    cancel_time           TIMESTAMP WITHOUT TIME ZONE,
    reason                INTEGER,
    state                 VARCHAR(255),
    transform_id          VARCHAR(255),
    transform_order       INTEGER,
    CONSTRAINT pk_transactions PRIMARY KEY (id)
);

CREATE TABLE transform
(
    id                          VARCHAR(255) NOT NULL,
    visible                     BOOLEAN,
    created_date                TIMESTAMP WITHOUT TIME ZONE,
    deleted_date                TIMESTAMP WITHOUT TIME ZONE,
    deleted_id                  VARCHAR(255),
    student_id                  VARCHAR(255),
    consulting_id               VARCHAR(255),
    application_id              VARCHAR(255),
    step_level_id               VARCHAR(255),
    application_level_status_id VARCHAR(255),
    amount                      BIGINT,
    CONSTRAINT pk_transform PRIMARY KEY (id)
);

CREATE TABLE university
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    visible      BOOLEAN,
    created_date TIMESTAMP WITHOUT TIME ZONE,
    deleted_date TIMESTAMP WITHOUT TIME ZONE,
    created_id   VARCHAR(255),
    deleted_id   VARCHAR(255),
    name         VARCHAR(255),
    web_site     VARCHAR(255),
    rating       BIGINT,
    country_id   BIGINT,
    description  TEXT,
    photo_id     VARCHAR(255),
    logo_id      VARCHAR(255),
    CONSTRAINT pk_university PRIMARY KEY (id)
);

CREATE TABLE university_degree_type
(
    id            VARCHAR(255) NOT NULL,
    visible       BOOLEAN,
    created_date  TIMESTAMP WITHOUT TIME ZONE,
    deleted_date  TIMESTAMP WITHOUT TIME ZONE,
    deleted_id    VARCHAR(255),
    university_id BIGINT,
    degree_type   VARCHAR(255),
    CONSTRAINT pk_university_degree_type PRIMARY KEY (id)
);

CREATE TABLE university_faculty
(
    id            VARCHAR(255) NOT NULL,
    visible       BOOLEAN,
    created_date  TIMESTAMP WITHOUT TIME ZONE,
    deleted_date  TIMESTAMP WITHOUT TIME ZONE,
    deleted_id    VARCHAR(255),
    university_id BIGINT,
    faculty_id    VARCHAR(255),
    CONSTRAINT pk_university_faculty PRIMARY KEY (id)
);

ALTER TABLE app_application
    ADD CONSTRAINT uc_app_application_consulting_step UNIQUE (consulting_step_id);

ALTER TABLE app_application
    ADD CONSTRAINT uc_app_application_consulting_step_level UNIQUE (consulting_step_level_id);

ALTER TABLE app_application
    ADD CONSTRAINT uc_app_application_consulting_step_level_status UNIQUE (consulting_step_level_status_id);

ALTER TABLE app_application
    ADD CONSTRAINT uc_app_application_consulting_tariff UNIQUE (consulting_tariff_id);

ALTER TABLE consulting_profile
    ADD CONSTRAINT uc_consulting_profile_consulting UNIQUE (consulting_id);

ALTER TABLE app_application_level_attach
    ADD CONSTRAINT FK_APP_APPLICATION_LEVEL_ATTACH_ON_ATTACH FOREIGN KEY (attach_id) REFERENCES attach (id);

ALTER TABLE app_application_level_attach
    ADD CONSTRAINT FK_APP_APPLICATION_LEVEL_ATTACH_ON_CONSULTING_STEP_LEVEL FOREIGN KEY (consulting_step_level_id) REFERENCES consulting_step_level (id);

ALTER TABLE app_application_level_status
    ADD CONSTRAINT FK_APP_APPLICATION_LEVEL_STATUS_ON_APP_APPLICATION FOREIGN KEY (app_application_id) REFERENCES app_application (id);

ALTER TABLE app_application_level_status
    ADD CONSTRAINT FK_APP_APPLICATION_LEVEL_STATUS_ON_CONSULTING_STEP_LEVEL FOREIGN KEY (consulting_step_level_id) REFERENCES consulting_step_level (id);

ALTER TABLE app_application
    ADD CONSTRAINT FK_APP_APPLICATION_ON_CONSULTING FOREIGN KEY (consulting_id) REFERENCES consulting (id);

ALTER TABLE app_application
    ADD CONSTRAINT FK_APP_APPLICATION_ON_CONSULTING_PROFILE FOREIGN KEY (consulting_profile_id) REFERENCES consulting_profile (id);

ALTER TABLE app_application
    ADD CONSTRAINT FK_APP_APPLICATION_ON_CONSULTING_STEP FOREIGN KEY (consulting_step_id) REFERENCES consulting_step (id);

ALTER TABLE app_application
    ADD CONSTRAINT FK_APP_APPLICATION_ON_CONSULTING_STEP_LEVEL FOREIGN KEY (consulting_step_level_id) REFERENCES consulting_step_level (id);

ALTER TABLE app_application
    ADD CONSTRAINT FK_APP_APPLICATION_ON_CONSULTING_STEP_LEVEL_STATUS FOREIGN KEY (consulting_step_level_status_id) REFERENCES app_application_level_status (id);

ALTER TABLE app_application
    ADD CONSTRAINT FK_APP_APPLICATION_ON_CONSULTING_TARIFF FOREIGN KEY (consulting_tariff_id) REFERENCES consulting_tariff (id);

ALTER TABLE app_application
    ADD CONSTRAINT FK_APP_APPLICATION_ON_STUDENT FOREIGN KEY (student_id) REFERENCES profile (id);

ALTER TABLE app_application
    ADD CONSTRAINT FK_APP_APPLICATION_ON_UNIVERSITY FOREIGN KEY (university_id) REFERENCES university (id);

ALTER TABLE consulting_comment
    ADD CONSTRAINT FK_CONSULTING_COMMENT_ON_CONSULTING FOREIGN KEY (consulting_id) REFERENCES consulting (id);

ALTER TABLE consulting_comment
    ADD CONSTRAINT FK_CONSULTING_COMMENT_ON_STUDENT FOREIGN KEY (student_id) REFERENCES profile (id);

ALTER TABLE consulting
    ADD CONSTRAINT FK_CONSULTING_ON_MANAGER FOREIGN KEY (manager_id) REFERENCES consulting_profile (id);

ALTER TABLE consulting
    ADD CONSTRAINT FK_CONSULTING_ON_PHOTO FOREIGN KEY (photo_id) REFERENCES attach (id);

ALTER TABLE consulting_profile
    ADD CONSTRAINT FK_CONSULTING_PROFILE_ON_CONSULTING FOREIGN KEY (consulting_id) REFERENCES consulting (id);

ALTER TABLE consulting_profile
    ADD CONSTRAINT FK_CONSULTING_PROFILE_ON_COUNTRY FOREIGN KEY (country_id) REFERENCES country (id);

ALTER TABLE consulting_profile
    ADD CONSTRAINT FK_CONSULTING_PROFILE_ON_PHOTO FOREIGN KEY (photo_id) REFERENCES attach (id);

ALTER TABLE consulting_step_level
    ADD CONSTRAINT FK_CONSULTING_STEP_LEVEL_ON_CONSULTING FOREIGN KEY (consulting_id) REFERENCES consulting (id);

ALTER TABLE consulting_step_level
    ADD CONSTRAINT FK_CONSULTING_STEP_LEVEL_ON_CONSULTING_STEP FOREIGN KEY (consulting_step_id) REFERENCES consulting_step (id);

ALTER TABLE consulting_step
    ADD CONSTRAINT FK_CONSULTING_STEP_ON_CONSULTING FOREIGN KEY (consulting_id) REFERENCES consulting (id);

ALTER TABLE consulting_tariff
    ADD CONSTRAINT FK_CONSULTING_TARIFF_ON_CONSULTING FOREIGN KEY (consulting_id) REFERENCES consulting (id);

ALTER TABLE consulting_university
    ADD CONSTRAINT FK_CONSULTING_UNIVERSITY_ON_CONSULTING FOREIGN KEY (consulting_id) REFERENCES consulting (id);

ALTER TABLE consulting_university
    ADD CONSTRAINT FK_CONSULTING_UNIVERSITY_ON_UNIVERSITY FOREIGN KEY (university_id) REFERENCES university (id);

ALTER TABLE continent_country
    ADD CONSTRAINT FK_CONTINENT_COUNTRY_ON_CONTINENT FOREIGN KEY (continent_id) REFERENCES continent (id);

ALTER TABLE continent_country
    ADD CONSTRAINT FK_CONTINENT_COUNTRY_ON_COUNTRY FOREIGN KEY (country_id) REFERENCES country (id);

ALTER TABLE continent_country
    ADD CONSTRAINT FK_CONTINENT_COUNTRY_ON_CREATED FOREIGN KEY (created_id) REFERENCES profile (id);

ALTER TABLE continent_country
    ADD CONSTRAINT FK_CONTINENT_COUNTRY_ON_DELETED FOREIGN KEY (deleted_id) REFERENCES profile (id);

ALTER TABLE continent
    ADD CONSTRAINT FK_CONTINENT_ON_CREATED FOREIGN KEY (created_id) REFERENCES profile (id);

ALTER TABLE continent
    ADD CONSTRAINT FK_CONTINENT_ON_DELETED FOREIGN KEY (deleted_id) REFERENCES profile (id);

ALTER TABLE country
    ADD CONSTRAINT FK_COUNTRY_ON_CREATED FOREIGN KEY (created_id) REFERENCES profile (id);

ALTER TABLE country
    ADD CONSTRAINT FK_COUNTRY_ON_DELETED FOREIGN KEY (deleted_id) REFERENCES profile (id);

ALTER TABLE district
    ADD CONSTRAINT FK_DISTRICT_ON_REGION FOREIGN KEY (region_id) REFERENCES region (id) ON DELETE CASCADE;

ALTER TABLE faculty
    ADD CONSTRAINT FK_FACULTY_ON_PARENT FOREIGN KEY (parent_id) REFERENCES faculty (id);

ALTER TABLE land
    ADD CONSTRAINT FK_LAND_ON_CREATED FOREIGN KEY (created_id) REFERENCES profile (id);

ALTER TABLE land
    ADD CONSTRAINT FK_LAND_ON_DELETED FOREIGN KEY (deleted_id) REFERENCES profile (id);

ALTER TABLE profile
    ADD CONSTRAINT FK_PROFILE_ON_COUNTRY FOREIGN KEY (country_id) REFERENCES country (id);

ALTER TABLE profile
    ADD CONSTRAINT FK_PROFILE_ON_PHOTO FOREIGN KEY (photo_id) REFERENCES attach (id);

ALTER TABLE scholar_ship_degree_types
    ADD CONSTRAINT FK_SCHOLAR_SHIP_DEGREE_TYPES_ON_SCHOLAR_SHIP FOREIGN KEY (scholar_ship_id) REFERENCES scholar_ship (id);

ALTER TABLE scholar_ship
    ADD CONSTRAINT FK_SCHOLAR_SHIP_ON_PHOTO FOREIGN KEY (photo_id) REFERENCES attach (id);

ALTER TABLE scholar_ship
    ADD CONSTRAINT FK_SCHOLAR_SHIP_ON_UNIVERSITY FOREIGN KEY (university_id) REFERENCES university (id);

ALTER TABLE simple_message
    ADD CONSTRAINT FK_SIMPLE_MESSAGE_ON_APPLICATION FOREIGN KEY (application_id) REFERENCES app_application (id);

ALTER TABLE simple_message
    ADD CONSTRAINT FK_SIMPLE_MESSAGE_ON_ATTACH FOREIGN KEY (attach_id) REFERENCES attach (id);

ALTER TABLE simple_message
    ADD CONSTRAINT FK_SIMPLE_MESSAGE_ON_CONSULTING FOREIGN KEY (consulting_id) REFERENCES consulting (id);

ALTER TABLE simple_message
    ADD CONSTRAINT FK_SIMPLE_MESSAGE_ON_CONSULTING_PROFILE FOREIGN KEY (consulting_profile_id) REFERENCES consulting_profile (id);

ALTER TABLE simple_message
    ADD CONSTRAINT FK_SIMPLE_MESSAGE_ON_PROFILE FOREIGN KEY (profile_id) REFERENCES profile (id);

ALTER TABLE university_degree_type
    ADD CONSTRAINT FK_UNIVERSITY_DEGREE_TYPE_ON_UNIVERSITY FOREIGN KEY (university_id) REFERENCES university (id);

ALTER TABLE university_faculty
    ADD CONSTRAINT FK_UNIVERSITY_FACULTY_ON_FACULTY FOREIGN KEY (faculty_id) REFERENCES faculty (id);

ALTER TABLE university_faculty
    ADD CONSTRAINT FK_UNIVERSITY_FACULTY_ON_UNIVERSITY FOREIGN KEY (university_id) REFERENCES university (id);

ALTER TABLE university
    ADD CONSTRAINT FK_UNIVERSITY_ON_COUNTRY FOREIGN KEY (country_id) REFERENCES country (id);

ALTER TABLE university
    ADD CONSTRAINT FK_UNIVERSITY_ON_CREATED FOREIGN KEY (created_id) REFERENCES profile (id);

ALTER TABLE university
    ADD CONSTRAINT FK_UNIVERSITY_ON_DELETED FOREIGN KEY (deleted_id) REFERENCES profile (id);

ALTER TABLE university
    ADD CONSTRAINT FK_UNIVERSITY_ON_LOGO FOREIGN KEY (logo_id) REFERENCES attach (id);

ALTER TABLE university
    ADD CONSTRAINT FK_UNIVERSITY_ON_PHOTO FOREIGN KEY (photo_id) REFERENCES attach (id);