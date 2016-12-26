-- http://www.w3resource.com/sql/creating-and-maintaining-tables/sql-create-table.php
-- http://www.w3resource.com/sql/creating-and-maintaining-tables/primary-key.php

insert into students values(1001, 'Roberto', 'Guanciale', '1979-12-08');
insert into students values(1002, 'Bill', 'Gates', '1960-06-05');
insert into students values(1003, 'Erik', 'Erikson', '1980-03-05');
insert into students values(1004, 'Erik', 'Anderson', ':-)');

-- change tables adding the columns

-- Example

CREATE TABLE agents(  
agent_code char(6) NOT NULL  PRIMARY KEY,  
agent_name char(40),  
working_area char(35),  
commission decimal(10,2),  
phone_no char(15) NULL,
country varchar(20));  

INSERT INTO agents VALUES ('A007', 'Ramasundar', 'Bangalore', 0.15, '077-25814763', NULL);
INSERT INTO agents VALUES ('A003', 'Alex', 'London', 0.13,' 075-12458969', NULL);
INSERT INTO agents VALUES ('A008', 'Alford', 'New York', 0.12,' 044-25874365', NULL);
INSERT INTO agents VALUES ('A011', 'Ravi Kumar', 'Bangalore', 0.15, '077-45625874', NULL);
INSERT INTO agents VALUES ('A010', 'Santakumar', 'Chennai', 0.14, '007-22388644', NULL);
INSERT INTO agents VALUES ('A012', 'Lucida', 'San Jose', 0.12, '044-52981425', NULL);
INSERT INTO agents VALUES ('A005', 'Anderson', 'Brisban', 0.13, '045-21447739', NULL);
INSERT INTO agents VALUES ('A001', 'Subbarao', 'Bangalore', 0.14, '077-12346674', NULL);
INSERT INTO agents VALUES ('A002', 'Mukesh', 'Mumbai', 0.11, '029-12358964', NULL);
INSERT INTO agents VALUES ('A006', 'McDen', 'London', 0.15, '078-22255588', NULL);
INSERT INTO agents VALUES ('A004', 'Ivan', 'Torento', 0.15, '008-22544166', NULL);
INSERT INTO agents VALUES ('A009', 'Benjamin', 'Hampshair', 0.11, '008-22536178', NULL);         


-- SELECT STATEMENT
select * from students;
SELECT * FROM agents;  

-- Projection
select name from students;
select name,surname from students;
select surname,birthday from students;
select nationality,birthday from students;

SELECT agent_name,working_area,commission  
FROM agents;  

-- Selection
select *
from students
where nationality='Swedish';


select name,surname,birthday
from students
where nationality='Swedish';


SELECT name, weight
FROM students;

SELECT name, weight + 10
FROM students;

SELECT name, weight * 10
FROM students;

SELECT name, weight / 10
FROM students;

SELECT name, weight / height
FROM students;

SELECT name, (height>175), WEIGHT > 78 
FROM students;

SELECT name, (height>170) OR (WEIGHT > 40) 
FROM students;

SELECT name + ' ' + surname 
FROM students;

select *
from students
where nationality='Swedish';

select *
from students
where WEIGHT >= 76;

select *
from students
where WEIGHT >= 76
 AND HEIGHT > 180;

select *
from students
where BIRTHDAY < '1979-01-01';

SELECT *
FROM students
WHERE SURNAME LIKE 'G%';

SELECT nationality
FROM students;

SELECT DISTINCT nationality
FROM students;


-- ORDERING

SELECT name, NATIONALITY
FROM students
WHERE HEIGHT >= 175
ORDER BY NATIONALITY DESC, WEIGHT DESC;

SELECT * FROM AGENTS;
SELECT AGENT_NAME, WORKING_AREA FROM AGENTS WHERE WORKING_AREA='London';

SELECT * FROM agents WHERE commission <= 0.11;

SELECT agent_code,WORKING_AREA FROM agents WHERE commission > 0.11;

-- UPDATE - DELETE

SELECT * FROM STUDENTS;



DELETE FROM STUDENTS WHERE PERSONNUMBER = 1;
SELECT * FROM STUDENTS;

DELETE FROM STUDENTS WHERE HEIGHT > 175;
SELECT * FROM STUDENTS;

UPDATE STUDENTS SET WEIGHT = 50 WHERE PERSONNUMBER = 2;
SELECT * FROM STUDENTS;
UPDATE STUDENTS SET WEIGHT = 51, HEIGHT=168 WHERE PERSONNUMBER = 2;
SELECT * FROM STUDENTS;

DELETE FROM STUDENTS WHERE PERSONNUMBER=100;


-- JDBC





SELECT NATIONALITY, MAX(height), MIN(height), AVG(height) FROM STUDENTS GROUP BY NATIONALITY;
SELECT NATIONALITY, count(*) FROM STUDENTS GROUP BY NATIONALITY;
SELECT NATIONALITY, MAX(WEIGHT*100/HEIGHT), MAX(WEIGHT)*100/max(HEIGHT) FROM STUDENTS GROUP BY NATIONALITY;
SELECT NATIONALITY, SUM(height) FROM STUDENTS GROUP BY NATIONALITY, WEIGHT;
SELECT NATIONALITY, count(height) FROM STUDENTS WHERE HEIGHT > 170 GROUP BY NATIONALITY;
SELECT NATIONALITY, count(height) FROM STUDENTS WHERE HEIGHT < 180 GROUP BY NATIONALITY;
SELECT NATIONALITY, count(height) FROM STUDENTS WHERE HEIGHT < 180 GROUP BY NATIONALITY HAVING count(height) > 1;


-- END LECTURE ONE



CREATE TABLE PUBLIC.TEACHERS (
	PERSONNUMBER INTEGER,
	NAME VARCHAR(100),
	SURANME VARCHAR(100),
	NATIONALITY VARCHAR(100),
	BIRTHDAY DATE,
	SALARY INTEGER,
	CONSTRAINT TEACHERS_PK PRIMARY KEY (PERSONNUMBER)
);
CREATE TABLE PUBLIC.COURSES (
	CODE INTEGER,
	NAME VARCHAR(100),
	TEACHER INTEGER,
	CONSTRAINT COURSES_PK PRIMARY KEY (CODE),
	CONSTRAINT COURSES_TEACHERS_FK FOREIGN KEY (TEACHER) REFERENCES PUBLIC.TEACHERS(PERSONNUMBER)
);

INSERT INTO TEACHERS VALUES (1001, 'Roberto', 30000);
INSERT INTO TEACHERS VALUES (1002, 'Erik', 29000);
INSERT INTO TEACHERS VALUES (1003, 'Jhon', 31000);

INSERT INTO COURSES VALUES (20,'Java 1', 1001);
INSERT INTO COURSES VALUES (21,'Java 1', 1001);
INSERT INTO COURSES VALUES (22,'DBMS', 1001);
INSERT INTO COURSES VALUES (23,'C++', 1002);
INSERT INTO COURSES VALUES (24,'Assembler', 1002);
INSERT INTO COURSES VALUES (25,'Networking', 1003);
INSERT INTO COURSES VALUES (26,'Operating System', 1003);
INSERT INTO COURSES VALUES (27,'Python', 1003);

SELECT * FROM COURSES, TEACHERS;
SELECT * FROM COURSES, TEACHERS, STUDENTS;

SELECT * FROM COURSES, TEACHERS WHERE courses.TEACHER = TEACHERs.PERSONNUMBER;
SELECT * FROM COURSES JOIN TEACHERS ON courses.TEACHER = TEACHERs.PERSONNUMBER;
SELECT * FROM COURSES JOIN TEACHERS ON courses.TEACHER = TEACHERs.PERSONNUMBER WHERE courses.NAME LIKE 'J%';

SELECT * FROM STUDENTS AS S1, STUDENTS AS S2 WHERE S1.height < S2.HEIGHT;
SELECT * FROM STUDENTS AS S1 JOIN STUDENTS AS S2 ON S1.height < S2.HEIGHT;
SELECT PERSONNUMBER, count(*) FROM STUDENTS AS S1 JOIN STUDENTS AS S2 ON S1.height < S2.HEIGHT GROUP BY PERSONNUMBER;
SELECT s1.PERSONNUMBER, name, count(*) FROM STUDENTS AS S1 JOIN STUDENTS AS S2 ON S1.height < S2.HEIGHT GROUP BY s1.PERSONNUMBER, s1.NAME;

SELECT * FROM STUDENTS AS S1 LEFT JOIN STUDENTS AS S2 ON S1.height < S2.HEIGHT;
SELECT s1.PERSONNUMBER, name, count(*) FROM STUDENTS AS S1 LEFT JOIN STUDENTS AS S2 ON S1.height < S2.HEIGHT GROUP BY s1.PERSONNUMBER, s1.NAME;


CREATE TABLE STUDENTS_COURSES (
	studentcode VARCHAR(100),
	coursecode INTEGER
);

INSERT INTO STUDENTS_COURSES VALUES (1002, 20);
INSERT INTO STUDENTS_COURSES VALUES (1002, 21);
INSERT INTO STUDENTS_COURSES VALUES (1003, 21);
INSERT INTO STUDENTS_COURSES VALUES (1003, 22);
INSERT INTO STUDENTS_COURSES VALUES (1003, 23);


SELECT *
FROM STUDENTS as S
JOIN STUDENTS_COURSES as SC ON S.personnumber = SC.studentcode
JOIN COURSES as C ON SC.coursecode = C.code;

SELECT *
FROM STUDENTS as S
LEFT JOIN STUDENTS_COURSES as SC ON S.personnumber = SC.studentcode
LEFT JOIN COURSES as C ON SC.coursecode = C.code;

SELECT S.name, S.surname, C.name
FROM STUDENTS as S
LEFT JOIN STUDENTS_COURSES as SC ON S.personnumber = SC.studentcode
LEFT JOIN COURSES as C ON SC.coursecode = C.code;

SELECT  S.name, S.surname, count(*)
FROM STUDENTS as S
JOIN STUDENTS_COURSES as SC ON S.personnumber = SC.studentcode
GROUP BY S.name, S.surname;

SELECT  C.name, count(*)
FROM COURSES as C
JOIN STUDENTS_COURSES as SC ON C.code = SC.coursecode
GROUP BY C.name;

SELECT  C.name, SUM(isnull(SC.studentcode, 0))
FROM COURSES as C
JOIN STUDENTS_COURSES as SC ON C.code = SC.coursecode
GROUP BY C.name;


SELECT S.name, S.surname, T.name, T.NAME
FROM STUDENTS as S
LEFT JOIN STUDENTS_COURSES as SC ON S.personnumber = SC.studentcode
LEFT JOIN COURSES as C ON SC.coursecode = C.code
LEFT JOIN TEACHERS as T ON C.teacher = T.personnumber
;

