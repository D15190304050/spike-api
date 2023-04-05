DROP TABLE IF EXISTS `account_base_info`;
CREATE TABLE `account_base_info`
(
    `id`                 BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'ID of the user.',
    `phone_number`       VARCHAR(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin           DEFAULT NULL COMMENT 'Phone number of the user.',
    `encrypted_password` VARCHAR(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'Encrypted password.',
    `username`           VARCHAR(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'Username.',
    `nickname`           VARCHAR(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'Nickname.',
    `avatar_url`         VARCHAR(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'URL of avatar.',
    `state`              INT                                                    NOT NULL DEFAULT 0 COMMENT 'State of the account: 0 - normal; 1 - deleted',
    `update_time`        DATETIME                                               NOT NULL DEFAULT NOW() COMMENT 'Last update time.',
    KEY `idx_username` (`username`),
    KEY `idx_phone_number` (`phone_number`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_bin
    COMMENT = 'Base information of accounts.';