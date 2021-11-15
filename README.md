<div style="display:flex;">
<img src = "https://user-images.githubusercontent.com/47360438/140064036-673945d9-b3ff-4677-9d67-dd429b710df6.png" width="100" height="100"> <h1>myStreaming</h1>
</div>

### 버전

mysql v8

tomcat v9

bootstrap-v.4.6

jquery-3.5.1

### member테이블
```
CREATE TABLE `member` (
  `id` varchar(20) NOT NULL,
  `passwd` varchar(60) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `nickname` varchar(20) DEFAULT NULL,
  `birthday` varchar(12) DEFAULT NULL,
  `gender` varchar(1) DEFAULT 'N',
  `email` varchar(60) DEFAULT NULL,
  `reg_date` datetime DEFAULT NULL,
  `recv_email` varchar(2) DEFAULT 'N',
  PRIMARY KEY (`id`)
)
```

### profile테이블
```
CREATE TABLE `profilepic` (
  `uuid` varchar(36) NOT NULL,
  `uploadpath` varchar(12) DEFAULT NULL,
  `filename` varchar(255) DEFAULT NULL,
  `mid` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
)
```

### board테이블
```
CREATE TABLE `board` (
  `num` int NOT NULL,
  `mid` varchar(20) DEFAULT NULL,
  `subject` varchar(50) DEFAULT NULL,
  `content` text,
  `readcount` int DEFAULT NULL,
  `reg_date` datetime DEFAULT NULL,
  `re_ref` int DEFAULT NULL COMMENT '글그룹 번호',
  `re_lev` int DEFAULT NULL COMMENT '화면에 표시할 들여쓰기 레벨',
  `re_seq` int DEFAULT NULL COMMENT '같은 글그룹 안에서의 순번',
  `secret` tinyint DEFAULT NULL,
  PRIMARY KEY (`num`),
  KEY `mid` (`mid`),
  CONSTRAINT `board_ibfk_1` FOREIGN KEY (`mid`) REFERENCES `member` (`id`)
)
```

### attach테이블
```
CREATE TABLE `attach` (
  `uuid` varchar(36) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `uploadpath` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `filename` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `filetype` varchar(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `bno` int DEFAULT NULL,
  PRIMARY KEY (`uuid`)
)
```

### bookmark테이블
```
CREATE TABLE `bookmark` (
  `mid` varchar(20) DEFAULT NULL,
  `streamerId` varchar(40) DEFAULT NULL,
  `streamerName` varchar(40) DEFAULT NULL,
  `streamerLogin` varchar(45) DEFAULT NULL,
  `profileImageUrl` varchar(255) DEFAULT NULL,
  KEY `mid` (`mid`),
  CONSTRAINT `bookmark_ibfk_1` FOREIGN KEY (`mid`) REFERENCES `member` (`id`)
)
```

### search테이블
```
CREATE TABLE `search` (
  `keyword` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `count` int DEFAULT '1',
  `regDate` date DEFAULT NULL,
  PRIMARY KEY (`keyword`)
)
```

### twitchstreamer테이블
```
CREATE TABLE `twitchstreamer` (
  `name` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `id` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `login` varchar(45) COLLATE utf8_bin DEFAULT NULL
)
```
