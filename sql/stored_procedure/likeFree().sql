
DROP PROCEDURE IF EXISTS likeFree;

DELIMITER $$
CREATE PROCEDURE likeFree(freeId INT, categ VARCHAR(30))
BEGIN
	DECLARE great INT;
    DECLARE rseq BIGINT;
    SET great = -1;
    SET rseq = -1;
    
    SELECT FR.FR_SEQ INTO rseq 
    FROM TB_FREE FR
    INNER JOIN TB_CATEG CT
    ON FR.CT_CODE = CT.CODE
    WHERE FR.SEQ = freeId AND CT.CATEGORY = categ;
    IF(rseq != -1) THEN
		SELECT FR.GOOD INTO great 
		FROM TB_FREE FR
		INNER JOIN TB_CATEG CT
		ON FR.CT_CODE = CT.CODE
		WHERE FR.FR_SEQ = rseq;
		
		IF(great != -1) THEN
			SET great = great + 1;
			
			UPDATE TB_FREE
			SET GOOD = great
			WHERE FR_SEQ = rseq;
		END IF;
	END IF;
END $$
DELIMITER ;

CALL likeFree(1,'IT서비스');
SELECT * FROM TB_FREE;
