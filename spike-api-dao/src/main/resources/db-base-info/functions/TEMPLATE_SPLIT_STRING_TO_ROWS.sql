SET @ids = '1,2,3,4';

SELECT *
FROM ${tableName}
WHERE id IN
      (SELECT SUBSTRING_INDEX(substring_index(@ids, ',', b.help_topic_id + 1), ',', -1) AS name
       FROM mysql.help_topic b
       WHERE b.help_topic_id < LENGTH(@ids) - LENGTH(REPLACE(@ids, ',', '')) + 1);