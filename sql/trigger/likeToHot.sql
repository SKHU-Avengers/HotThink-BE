DROP TRIGGER IF EXISTS like_to_hot;
DELIMITER $$
CREATE TRIGGER like_to_hot
AFTER INSERT on TB_LIKE
FOR EACH ROW
BEGIN
	DECLARE _like_cnt int;  -- 좋아요 수
	IF(new.BOARD_TYPE = 'FREE') THEN
        SELECT COUNT(*) INTO _like_cnt FROM TB_LIKE
        WHERE BOARD_SEQ = new.BOARD_SEQ AND BOARD_TYPE = new.BOARD_TYPE;
        IF(_like_cnt >= 1) THEN
            UPDATE TB_BOARD SET BOARD_TYPE = 'HOT'
            WHERE BOARD_TYPE = 'FREE' AND BD_SEQ = new.BOARD_SEQ;
        END IF;
    END IF;
END $$
DELIMITER ;