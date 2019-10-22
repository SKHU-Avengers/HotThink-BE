
DROP PROCEDURE IF EXISTS likeFree;

DELIMITER $$
CREATE PROCEDURE likeFree(freeId BIGINT)
BEGIN
	DECLARE great INT;
    SET great = -1;

    SELECT FR.GOOD INTO great
    FROM TB_FREE FR
    INNER JOIN TB_CATEG CT
    ON FR.CT_CODE = CT.CODE
    WHERE FR.FR_SEQ = freeId;

    IF(great != -1) THEN
        SET great = great + 1;

        UPDATE TB_FREE
        SET GOOD = great
        WHERE FR_SEQ = freeId;
    END IF;

END $$
DELIMITER ;

CALL likeFree(1);
SELECT * FROM TB_FREE;
