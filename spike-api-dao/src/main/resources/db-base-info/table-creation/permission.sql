DROP TABLE IF EXISTS `permission`;

CREATE TABLE `permission`
(
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'ID of the permission.',
    `parent_id` BIGINT NOT NULL DEFAULT -1 COMMENT 'ID of parent permission.',
    `name` VARCHAR(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'Name of the permission',
    `description` VARCHAR(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'Description of the permission',
    `resource_type_id` INT NOT NULL COMMENT 'ID of resource type: 1 - menu; 2 - button.',
    `resource_path` VARCHAR(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'Path to access the resource.',
    `component_path` VARCHAR(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'Path of the component.',
    `icon_image_url` VARCHAR(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'Image URL of the icon, used for special icon.',
    `state` INT NOT NULL DEFAULT 0 COMMENT 'State of the permission: 0 - normal; 1 - forbidden; -1 - deleted.',
    `level` INT NOT NULL DEFAULT 1 COMMENT 'Level of the permission.',
    `creation_time` DATETIME NOT NULL DEFAULT NOW() COMMENT 'Creation time of the permission.',
    `creator_id` BIGINT NOT NULL COMMENT 'ID of the account that creates the permission.',
    `update_time` DATETIME NOT NULL DEFAULT NOW() COMMENT 'Last update time of the permission.',
    `updater_id` BIGINT NOT NULL COMMENT 'ID of the account that updates the permission last time.',
    KEY `idx_name` (`name`),
    KEY `idx_parent_id` (`parent_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_bin
    COMMENT = 'Permissions.';