CREATE TABLE IF NOT EXISTS `user`
(
    `id`        INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `userName`  VARCHAR(20) NOT NULL UNIQUE,
    `firstName` VARCHAR(30),
    `lastName`  VARCHAR(30)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;

CREATE TABLE IF NOT EXISTS `task`
(
    `id`          INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `title`       VARCHAR(20) NOT NULL,
    `description` VARCHAR(30)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;

CREATE TABLE IF NOT EXISTS `user_task`
(
    `user_id` INT NOT NULL,
    `task_id` INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (task_id) REFERENCES task (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;