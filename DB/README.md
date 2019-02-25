# Start

#### MySQL/MariaDB 접속 ####

```
mysql -u root -p
```

이후 패스워드 입력(입력 동작이 안 보이지만, 그냥 암호 입력 후 엔터치면 됨.)

#### SimpleSNS 디비 접속 ####

```
USE SimpleSNS;
```

#### 기본 동작 ####

```
SHOW DATABASES; // DBMS의 DB목록을 보여줍니다.
CREATE DATABASE DB_NAME; // DB_NAME이란 데이터베이스를 생성합니다. 
USE DB_NAME; // DD_NAME이란 데이터베이스를 사용합니다.
SHOW TABLES; // 접속된 DB내의 테이블 목록을 보여줍니다.
DESC TABLE_NAME; // 테이블의 속성 및 구성을 보여줍니다.
```

## OLD

```
CREATE TABLE member (
p_key BIGINT UNSIGNED PRIMARY KEY,
e_mail VARCHAR(64) NOT NULL UNIQUE KEY,
password VARCHAR(20) NOT NULL,
nickname VARCHAR(12) NOT NULL,
device_id VARCHAR(100),
login_method INT NOT NULL,
token VARCHAR(32) NOT NULL,
gender INT,
profile VARCHAR(50),
recent_date DATETIME NOT NULL
);
```

```
CREATE TABLE email_verify(
e_mail VARCHAR(64) NOT NULL,
verify_number VARCHAR(5)  NOT NULL,
request_time DATETIME NOT NULL,
verified_status INT DEFAULT 0
);
```

## NEW

#### ERD

<image src="https://lh6.googleusercontent.com/a5GvlaeEcSD_TCVLzm8R-R-wePJY6hatHGWiv6cjdfQumeHJunCJJveWlAEMKLlGl9kHho1sz6xXXxTsXWCd38oiA2_l79Kifb6ioceunCQmCRPzxmWhd_4VLNe3kNGBCk7H_lepvww"/>

#### Create Tables

- EMAIL_VERIFICATION

```
DROP TABLE IF EXISTS `MEMBER`;
```

```
CREATE TABLE member (
  `uid` BIGINT(20) NOT NULL AUTO_INCREMENT DEFAULT NULL,
  `email` VARCHAR(64) NOT NULL,
  `username` VARCHAR(25) NOT NULL,
  `password` VARCHAR(20) NOT NULL,
  `introduction` VARCHAR(300) NULL DEFAULT NULL,
  `photo_url` VARCHAR(50) NULL DEFAULT NULL,
  `token` VARCHAR(32) NOT NULL,
  `login_method` INTEGER(11) NOT NULL,
  `device_id` VARCHAR(100) NULL DEFAULT NULL,
  `last_login_date` DATETIME NOT NULL,
  PRIMARY KEY (`uid`)
);	
```

- Table 'EMAIL_VERIFICATION'

```
DROP TABLE IF EXISTS `EMAIL_VERIFICATION`;
```

```
CREATE TABLE email_verification (
  `eid` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(64) NOT NULL,
  `code` VARCHAR(5) NOT NULL,
  `req_time` DATETIME NOT NULL DEFAULT now(),
  `status` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`eid`)
);
```

- Table 'FEED'

```
DROP TABLE IF EXISTS `FEED`;
```

```
CREATE TABLE feed (
  `fid` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `uid` BIGINT(20) NOT NULL,
  `reg_time` DATETIME NOT NULL DEFAULT now(),
  `description` VARCHAR(300) NULL DEFAULT NULL,
  `mod_time` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`fid`, `uid`)
);
```

- Table 'MEDIA'


```
DROP TABLE IF EXISTS media;
```

```
CREATE TABLE media (
  `mid` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `fid` BIGINT(20) NOT NULL,
  `file_type` VARCHAR(10) NOT NULL,
  `url` VARCHAR(30) NOT NULL,
  `width` INTEGER(6) NOT NULL,
  `height` INTEGER(6) NOT NULL,
  PRIMARY KEY (`mid`, `fid`)
);
```

- Table 'COMMENT'


```
DROP TABLE IF EXISTS comment;
```

```
CREATE TABLE comment(
  `cid` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `fid` BIGINT(20) NOT NULL,
  `uid` BIGINT(25) NOT NULL,
  `com_text` VARCHAR(100) NOT NULL,
  `pid` BIGINT(20) NULL DEFAULT NULL COMMENT '대댓글을 위한 속성',
  PRIMARY KEY (`cid`, `fid`, `uid`)
);
```

- Table 'LIKE'

```
DROP TABLE IF EXISTS `LIKE`;
```

```
CREATE TABLE reaction(
  `lid` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `fid` BIGINT(20) NOT NULL,
  `uid` BIGINT(25) NOT NULL,
  PRIMARY KEY (`lid`, `fid`, `uid`)
);
```

- Table 'TAG'

```
DROP TABLE IF EXISTS `TAG`;
```

```
CREATE TABLE tag(
  `uid` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `feed_seq` BIGINT(20) NOT NULL,
  `name` VARCHAR(20) NOT NULL,
  `location` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`uid`, `feed_seq`)
);
```

- Table 'FOLLOW'

```
DROP TABLE IF EXISTS `FOLLOW`;
```

```
CREATE TABLE follow (
  `uid` INTEGER NULL DEFAULT NULL,
  `following_uid` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`uid`, `following_uid`)
);
```

- Foreign Keys (미완성)

```
alter table trade add foreign key (seller_id) REFERENCES user(uid);

alter table user drop column score;

ALTER TABLE user DROP COLUMN 
ALTER TABLE member ADD FOREIGN KEY (uid) REFERENCES follow(uid);
ALTER TABLE member ADD FOREIGN KEY (uid) REFERENCES follow (following_uid);
ALTER TABLE member ADD FOREIGN KEY (uid) REFERENCES feed (uid);
ALTER TABLE member ADD FOREIGN KEY (uid) REFERENCES reaction (uid);
ALTER TABLE member ADD FOREIGN KEY (uid) REFERENCES comment(uid);
ALTER TABLE feed ADD FOREIGN KEY (fid) REFERENCES media (fid);
ALTER TABLE feed ADD FOREIGN KEY (fid) REFERENCES comment (fid);
ALTER TABLE feed ADD FOREIGN KEY (fid) REFERENCES like (fid);
ALTER TABLE feed ADD FOREIGN KEY (fid) REFERENCES tag (feed_seq);
```

- Table Properties(미완성)

```
ALTER TABLE `MEMBER` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
ALTER TABLE `EMAIL_VERIFICATION` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
ALTER TABLE `FEED` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
ALTER TABLE `MEDIA` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
ALTER TABLE `COMMENT` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
ALTER TABLE `LIKE` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
ALTER TABLE `TAG` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
ALTER TABLE `FOLLOW` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
```
