DROP TABLE IF EXISTS `role_permission`;

CREATE TABLE `role_permission`
(
    `id`            BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'ID of the role-permission relationship.',
    `role_id`       BIGINT   NOT NULL COMMENT 'ID of the role.',
    `permission_id` BIGINT   NOT NULL COMMENT 'ID of the permission.',
    `state`         INT      NOT NULL DEFAULT 0 COMMENT 'State of the role: 0 - normal; -1 - deleted.',
    `creation_time` DATETIME NOT NULL DEFAULT NOW() COMMENT 'Creation time of the relationship.',
    `creator_id`    BIGINT   NOT NULL COMMENT 'ID of the account that creates the relationship.',
    `update_time`   DATETIME NOT NULL DEFAULT NOW() COMMENT 'Last update time of the relationship.',
    `updater_id`    BIGINT   NOT NULL COMMENT 'ID of the account that updates the relationship last time.',
    KEY `idx_role_id` (`role_id`),
    KEY `idx_account_id` (`permission_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_bin
    COMMENT = 'Relationships between roles and permissions.';