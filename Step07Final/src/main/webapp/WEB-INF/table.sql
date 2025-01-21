-- 가입된 회원정보를 저장할 테이블 --
-- 가입되면 일반 직원--
-- profileImage를 작성하지않고 row만들면, 비어있다=NULL --
CREATE TABLE users(
	num NUMBER PRIMARY KEY,
	userName VARCHAR2(100) UNIQUE,
	password VARCHAR2(100) NOT NULL,
	email VARCHAR2(100) UNIQUE,
	profileImage VARCHAR2(100),
	role VARCHAR2(10) DEFAULT 'USER' CHECK (role IN('USER','STAFF','ADMIN')),
	createdAt DATE DEFAULT SYSDATE,
	updatedAt DATE DEFAULT SYSDATE
);

-- 회원번호를 얻어낼 시퀀스 --
CREATE SEQUENCE users_seq;