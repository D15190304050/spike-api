DROP TABLE IF EXISTS `role`;

CREATE TABLE `role`
(
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'ID of the role.',
    `parent_id` BIGINT NOT NULL DEFAULT -1 COMMENT 'ID of parent role.',
    `name` VARCHAR(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'Name of the role',
    `description` VARCHAR(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'Description of the role',
    `resource_type_id` INT NOT NULL COMMENT 'ID of resource type: 1 - menu; 2 - button.',
    `resource_path` VARCHAR(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'Path to access the resource.',
    `state` INT NOT NULL DEFAULT 0 COMMENT 'State of the role: 0 - normal; 1 - forbidden; -1 - deleted.',
    `level` INT NOT NULL DEFAULT 1 COMMENT 'Level of the role.',
    `creation_time` DATETIME NOT NULL DEFAULT NOW() COMMENT 'Creation time of the role.',
    `creator_id` BIGINT NOT NULL COMMENT 'ID of the account that creates the role.',
    `update_time` DATETIME NOT NULL DEFAULT NOW() COMMENT 'Last update time of the role.',
    `updater_id` BIGINT NOT NULL COMMENT 'ID of the account that updates the role last time.',
    KEY `idx_name` (`name`),
    KEY `idx_parent_id` (`parent_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_bin
    COMMENT = 'Roles.';