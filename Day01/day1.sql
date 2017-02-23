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

-- If you want a new empry student table
CREATE TABLE STUDENTS (
	PERSONNUMBER INTEGER,
	NAME VARCHAR(100),
	SURNAME VARCHAR(100),
	BIRTHDAY DATE,
	WEIGHT FLOAT,
	HEIGHT FLOAT,
	NATIONALITY VARCHAR(100)
) ;
ALTER TABLE STUDENTS ADD CONSTRAINT STUDENTS_PK PRIMARY KEY (PERSONNUMBER) ;

insert into students values(1001, 'Roberto', 'Guanciale', '1979-12-08', 72, 178, 'Italian');
insert into students values(1002, 'Bill', 'Gates', '1960-06-05', 65, 178, 'Italian');
insert into students values(1003, 'Erik', 'Erikson', '1980-03-05', 78, 181, 'Swedish');
insert into students values(1004, 'Erik', 'Anderson', '1981-02-02', 75, 190, 'Swedish');
 


-- Updating
SELECT * FROM STUDENTS;

UPDATE STUDENTS SET WEIGHT = 50 WHERE PERSONNUMBER = 1002;

SELECT * FROM STUDENTS;

UPDATE STUDENTS SET WEIGHT = 51, HEIGHT=168 WHERE PERSONNUMBER = 1002;

SELECT * FROM STUDENTS;

-- UPDATE - DELETE

SELECT * FROM STUDENTS;


DELETE FROM STUDENTS WHERE PERSONNUMBER = 1001;
SELECT * FROM STUDENTS;

DELETE FROM STUDENTS WHERE HEIGHT > 175;
SELECT * FROM STUDENTS;

UPDATE STUDENTS SET WEIGHT = 50 WHERE PERSONNUMBER = 1002;
SELECT * FROM STUDENTS;
UPDATE STUDENTS SET WEIGHT = 51, HEIGHT=168 WHERE PERSONNUMBER = 2;
SELECT * FROM STUDENTS;


