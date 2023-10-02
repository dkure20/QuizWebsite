CREATE DATABASE IF NOT EXISTS tank_database;

USE tank_database;

CREATE TABLE IF NOT EXISTS users
(
    Id       INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    Username VARCHAR(30)  NOT NULL,
    Password VARCHAR(150) NOT NULL
);

CREATE TABLE IF NOT EXISTS quizzes
(
    id                      INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name                    VARCHAR(30)  NOT NULL,
    description             VARCHAR(150) NOT NULL,
    creator_id              INT,
    has_random_questions    BOOLEAN,
    is_one_page             BOOLEAN,
    is_immediate_correction BOOLEAN,
    has_practice_mode       BOOLEAN,
    creation_date_time      TIMESTAMP,
    submission_count        INT
);

CREATE TABLE IF NOT EXISTS questions
(
    quiz_id       INT          NOT NULL,
    question_id   INT          NOT NULL AUTO_INCREMENT primary key,
    question_type INT          NOT NULL,
    content       VARCHAR(300) NOT NULL
);

CREATE TABLE IF NOT EXISTS answers
(
    question_id INT          NOT NULL,
    answer_id   INT          NOT NULL AUTO_INCREMENT primary key,
    content     VARCHAR(150) NOT NULL,
    is_correct  BOOLEAN
);

CREATE TABLE IF NOT EXISTS question_types
(
    type_id INT          NOT NULL primary key,
    type    VARCHAR(150) NOT NULL
);

INSERT INTO question_types (type_id, type)
VALUES (0, 'Question-Response'),
       (1, 'Fill in the Blank'),
       (2, 'Multiple Choice'),
       (3, 'Picture-Response Questions');

CREATE TABLE IF NOT EXISTS mails
(
    Id              serial primary key,
    from_Id         bigint unsigned,
    to_Id           bigint unsigned,
    message_type    varchar(5),
    message_title   varchar(200),
    message_content varchar(1000),
    send_date       date
);
CREATE TABLE IF NOT EXISTS friends
(
    first_person  INT NOT NULL,
    second_person INT NOT NULL
);

INSERT INTO friends (first_person, second_person)
VALUES (2, 3),
       (3, 2);

CREATE TABLE IF NOT EXISTS announcements
(
    admin_id        INT           NOT NULL,
    announcement_id INT           NOT NULL AUTO_INCREMENT primary key,
    announcement    VARCHAR(1000) NOT NULL
);

CREATE TABLE IF NOT EXISTS quiz_results
(
    quiz_id       INT NOT NULL,
    user_id       INT NOT NULL,
    result_id     serial primary key,
    score         INT NOT NULL,
    percent       INT NOT NULL,
    quiz_duration INT NOT NULL,
    finish_time   TIMESTAMP
);

