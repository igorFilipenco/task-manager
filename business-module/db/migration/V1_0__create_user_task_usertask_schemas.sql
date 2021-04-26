CREATE TABLE IF NOT EXISTS `user`
(
    `id`        BIGINT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `userName`  VARCHAR(20) NOT NULL UNIQUE,
    `firstName` VARCHAR(30),
    `lastName`  VARCHAR(30)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;

CREATE TABLE IF NOT EXISTS `task`
(
    `id`          BIGINT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `title`       VARCHAR(20) NOT NULL,
    `description` VARCHAR(30)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;

CREATE TABLE IF NOT EXISTS `user_task`
(
    `user_id` BIGINT NOT NULL,
    `task_id` BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (task_id) REFERENCES task (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;