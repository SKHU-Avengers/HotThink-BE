SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS TB_FR_HISTORY;
DROP TABLE IF EXISTS TB_LIKE;
DROP TABLE IF EXISTS TB_REPORT;
DROP TABLE IF EXISTS TB_RP_LIKE;
DROP TABLE IF EXISTS TB_REPLY;
DROP TABLE IF EXISTS TB_SCRAP;
DROP TABLE IF EXISTS TB_FREE;
DROP TABLE IF EXISTS TB_IDEA_PAYLIST;
DROP TABLE IF EXISTS TB_IDEA;
DROP TABLE IF EXISTS TB_CATEG;
DROP TABLE IF EXISTS TB_CONVERSATION;
DROP TABLE IF EXISTS TB_FOLLOW;
DROP TABLE IF EXISTS TB_PAY;
DROP TABLE IF EXISTS TB_PREFERENCE;
DROP TABLE IF EXISTS TB_USER;




/* Create Tables */

CREATE TABLE TB_CATEG
(
	CODE varchar(30) NOT NULL,
	CATEGORY varchar(30) NOT NULL,
	PRIMARY KEY (CODE),
	UNIQUE (CATEGORY)
);


CREATE TABLE TB_CONVERSATION
(
	UR_FROM int unsigned NOT NULL,
	UR_TO int unsigned NOT NULL,
	AT datetime NOT NULL,
	MSG varchar(1500)
);


CREATE TABLE TB_FOLLOW
(
	SEQ bigint unsigned NOT NULL AUTO_INCREMENT,
	UR_CELEBRITY int unsigned NOT NULL,
	UR_FOLLOWER int unsigned NOT NULL,
	PRIMARY KEY (SEQ),
	UNIQUE (SEQ)
);


CREATE TABLE TB_FREE
(
	FR_SEQ bigint unsigned NOT NULL AUTO_INCREMENT,
	CT_CODE varchar(30) NOT NULL,
	-- 게시물 번호
	SEQ bigint unsigned NOT NULL COMMENT '게시물 번호',
	HITS int unsigned DEFAULT 0 NOT NULL,
	TITLE varchar(150) NOT NULL,
	CREATEAT datetime NOT NULL,
	CONTENTS text NOT NULL,
	GOOD int unsigned DEFAULT 0 NOT NULL,
	THUMBNAILIMG varchar(280),
	UR_SEQ int unsigned NOT NULL,
	PRIMARY KEY (FR_SEQ)
);


CREATE TABLE TB_FR_HISTORY
(
	SEQ bigint unsigned NOT NULL AUTO_INCREMENT,
	-- 수정된 날짜
	UPDATEAT datetime NOT NULL COMMENT '수정된 날짜',
	CONTENTS text NOT NULL,
	TITLE varchar(150) NOT NULL,
	THUMBNAILIMG varchar(280),
	FR_SEQ bigint unsigned NOT NULL,
	PRIMARY KEY (SEQ)
);


CREATE TABLE TB_IDEA
(
	ID_SEQ bigint unsigned NOT NULL AUTO_INCREMENT,
	CT_CODE varchar(30) NOT NULL,
	-- 게시물번호
	SEQ bigint NOT NULL COMMENT '게시물번호',
	HITS int unsigned DEFAULT 0 NOT NULL,
	TITLE varchar(150) NOT NULL,
	STATE varchar(15) NOT NULL,
	-- 다른 회원이 평가한 점수
	MYSCORE int COMMENT '다른 회원이 평가한 점수',
	-- 구매자의 판매자 평가
	SELLERSCORE int COMMENT '구매자의 판매자 평가',
	CREATEAT datetime NOT NULL,
	UPDATEAT datetime,
	CONTENTS text NOT NULL,
	REVIEW text,
	UR_SEQ int unsigned NOT NULL,
	-- 유료정보
	PMATERIAL text COMMENT '유료정보',
	PRIMARY KEY (ID_SEQ),
	UNIQUE (ID_SEQ)
);


CREATE TABLE TB_IDEA_PAYLIST
(
	SEQ bigint unsigned NOT NULL AUTO_INCREMENT,
	-- 거래가격
	PRICE double unsigned NOT NULL COMMENT '거래가격',
	-- 거래날짜
	PAYAT datetime NOT NULL COMMENT '거래날짜',
	-- 결제수단
	PAYMETHOD varchar(20) NOT NULL COMMENT '결제수단',
	UR_BUYER int unsigned NOT NULL,
	UR_SELLER int unsigned NOT NULL,
	ID_SEQ bigint unsigned NOT NULL,
	PRIMARY KEY (SEQ),
	UNIQUE (UR_BUYER),
	UNIQUE (UR_SELLER),
	UNIQUE (ID_SEQ)
);


CREATE TABLE TB_LIKE
(
	SEQ bigint unsigned NOT NULL AUTO_INCREMENT,
	UR_SEQ int unsigned NOT NULL,
	FR_SEQ bigint unsigned NOT NULL,
	PRIMARY KEY (SEQ),
	UNIQUE (UR_SEQ)
);


CREATE TABLE TB_PAY
(
	UR_SEQ int unsigned NOT NULL,
	-- 유료회원 종류
	PROTYPE varchar(10) DEFAULT 'NONE' NOT NULL COMMENT '유료회원 종류',
	START datetime,
	END datetime
);


CREATE TABLE TB_PREFERENCE
(
	SEQ bigint unsigned NOT NULL AUTO_INCREMENT,
	PREFERENCE varchar(20) NOT NULL,
	UR_SEQ int unsigned NOT NULL,
	PRIMARY KEY (SEQ),
	UNIQUE (UR_SEQ)
);


CREATE TABLE TB_REPLY
(
	SEQ bigint unsigned NOT NULL AUTO_INCREMENT,
	CONTENTS varchar(2000) NOT NULL,
	AT datetime NOT NULL,
	ADOPT varchar(10) NOT NULL,
	GOOD int unsigned DEFAULT 0 NOT NULL,
	FR_SEQ bigint unsigned NOT NULL,
	RE_SEQ bigint unsigned NOT NULL,
	UR_SEQ int unsigned NOT NULL,
	PRIMARY KEY (SEQ)
);


CREATE TABLE TB_REPORT
(
	SEQ int unsigned NOT NULL AUTO_INCREMENT,
	REASON varchar(100) NOT NULL,
	DETAIL varchar(3000),
	CT_CODE varchar(30) NOT NULL,
	UR_SEQ int unsigned NOT NULL,
	ID_SEQ bigint unsigned,
	FR_SEQ bigint unsigned,
	RE_SEQ bigint unsigned,
	PRIMARY KEY (SEQ),
	UNIQUE (SEQ)
);


CREATE TABLE TB_RP_LIKE
(
	SEQ bigint unsigned NOT NULL AUTO_INCREMENT,
	UR_SEQ int unsigned NOT NULL,
	RE_SEQ bigint unsigned NOT NULL,
	PRIMARY KEY (SEQ),
	UNIQUE (UR_SEQ)
);


CREATE TABLE TB_SCRAP
(
	SEQ bigint unsigned NOT NULL AUTO_INCREMENT,
	UR_SEQ int unsigned NOT NULL,
	FR_SEQ bigint unsigned NOT NULL,
	ID_SEQ bigint unsigned NOT NULL,
	CODE varchar(30) NOT NULL,
	PRIMARY KEY (SEQ)
);


CREATE TABLE TB_USER
(
	SEQ int unsigned NOT NULL AUTO_INCREMENT,
	EMAIL varchar(30) NOT NULL,
	NICKNAME varchar(20) NOT NULL,
	UNAME varchar(20),
	PW varchar(100) NOT NULL,
	TEL varchar(15),
	AUTH varchar(30) DEFAULT 'NONE' NOT NULL,
	UPOINT int DEFAULT 0 NOT NULL,
	-- 리얼띵크 작성권
	REALTICKET smallint unsigned DEFAULT 0 NOT NULL COMMENT '리얼띵크 작성권',
	PRIMARY KEY (SEQ),
	UNIQUE (SEQ),
	UNIQUE (EMAIL),
	UNIQUE (NICKNAME)
);



/* Create Foreign Keys */

ALTER TABLE TB_FREE
	ADD FOREIGN KEY (CT_CODE)
	REFERENCES TB_CATEG (CODE)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_IDEA
	ADD FOREIGN KEY (CT_CODE)
	REFERENCES TB_CATEG (CODE)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_REPORT
	ADD FOREIGN KEY (CT_CODE)
	REFERENCES TB_CATEG (CODE)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_SCRAP
	ADD FOREIGN KEY (CODE)
	REFERENCES TB_CATEG (CODE)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_FR_HISTORY
	ADD FOREIGN KEY (FR_SEQ)
	REFERENCES TB_FREE (FR_SEQ)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_LIKE
	ADD FOREIGN KEY (FR_SEQ)
	REFERENCES TB_FREE (FR_SEQ)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_REPLY
	ADD FOREIGN KEY (FR_SEQ)
	REFERENCES TB_FREE (FR_SEQ)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_REPORT
	ADD FOREIGN KEY (FR_SEQ)
	REFERENCES TB_FREE (FR_SEQ)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_SCRAP
	ADD FOREIGN KEY (FR_SEQ)
	REFERENCES TB_FREE (FR_SEQ)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_IDEA_PAYLIST
	ADD FOREIGN KEY (ID_SEQ)
	REFERENCES TB_IDEA (ID_SEQ)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_REPORT
	ADD FOREIGN KEY (ID_SEQ)
	REFERENCES TB_IDEA (ID_SEQ)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_SCRAP
	ADD FOREIGN KEY (ID_SEQ)
	REFERENCES TB_IDEA (ID_SEQ)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_REPLY
	ADD FOREIGN KEY (RE_SEQ)
	REFERENCES TB_REPLY (SEQ)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_REPORT
	ADD FOREIGN KEY (RE_SEQ)
	REFERENCES TB_REPLY (SEQ)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_RP_LIKE
	ADD FOREIGN KEY (RE_SEQ)
	REFERENCES TB_REPLY (SEQ)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_CONVERSATION
	ADD FOREIGN KEY (UR_TO)
	REFERENCES TB_USER (SEQ)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_CONVERSATION
	ADD FOREIGN KEY (UR_FROM)
	REFERENCES TB_USER (SEQ)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_FOLLOW
	ADD FOREIGN KEY (UR_FOLLOWER)
	REFERENCES TB_USER (SEQ)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_FOLLOW
	ADD FOREIGN KEY (UR_CELEBRITY)
	REFERENCES TB_USER (SEQ)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_FREE
	ADD FOREIGN KEY (UR_SEQ)
	REFERENCES TB_USER (SEQ)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_IDEA
	ADD FOREIGN KEY (UR_SEQ)
	REFERENCES TB_USER (SEQ)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_IDEA_PAYLIST
	ADD FOREIGN KEY (UR_BUYER)
	REFERENCES TB_USER (SEQ)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_IDEA_PAYLIST
	ADD FOREIGN KEY (UR_SELLER)
	REFERENCES TB_USER (SEQ)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_LIKE
	ADD FOREIGN KEY (UR_SEQ)
	REFERENCES TB_USER (SEQ)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_PAY
	ADD FOREIGN KEY (UR_SEQ)
	REFERENCES TB_USER (SEQ)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_PREFERENCE
	ADD FOREIGN KEY (UR_SEQ)
	REFERENCES TB_USER (SEQ)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_REPLY
	ADD FOREIGN KEY (UR_SEQ)
	REFERENCES TB_USER (SEQ)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_REPORT
	ADD FOREIGN KEY (UR_SEQ)
	REFERENCES TB_USER (SEQ)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_RP_LIKE
	ADD FOREIGN KEY (UR_SEQ)
	REFERENCES TB_USER (SEQ)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_SCRAP
	ADD FOREIGN KEY (UR_SEQ)
	REFERENCES TB_USER (SEQ)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


