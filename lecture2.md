# GROUPING
The `GROUP BY` clause is used with the SELECT statement to make a group of rows based on the values of a specific column or expression. The SQL AGGREGATE function can be used to get summary information for every group and these are applied to an individual group.
The WHERE clause is used to retrieve rows based on a certain condition, but it can not be applied to grouped result.
In an SQL statement, suppose you are using GROUP BY, if required you can use HAVING instead of WHERE, after GROUP BY.

```SQL
SELECT NATIONALITY, MAX(height), MIN(height), AVG(height) FROM STUDENTS GROUP BY NATIONALITY;
SELECT NATIONALITY, count(*) FROM STUDENTS GROUP BY NATIONALITY;
SELECT NATIONALITY, MAX(WEIGHT*100/HEIGHT), MAX(WEIGHT)*100/max(HEIGHT) FROM STUDENTS GROUP BY NATIONALITY;
SELECT NATIONALITY, SUM(height) FROM STUDENTS GROUP BY NATIONALITY, WEIGHT;
SELECT NATIONALITY, count(height) FROM STUDENTS WHERE HEIGHT > 170 GROUP BY NATIONALITY;
SELECT NATIONALITY, count(height) FROM STUDENTS WHERE HEIGHT < 180 GROUP BY NATIONALITY;
SELECT NATIONALITY, count(height) FROM STUDENTS WHERE HEIGHT < 180 GROUP BY NATIONALITY HAVING count(height) > 1;
```

# RELATIONS
We reuse the running example of yesterday:
* KTH Students
 * have name, surname, nationality, birthday
 * take courses (and eventually get grades)
* KTH professors
 * have name, surname, nationality, birthday, salary
 * teach courses
* KTH courses
 * have name, teaching material
 * are taught by professors and taken by students

## Table Professors
```SQL
CREATE TABLE PUBLIC.TEACHERS (
	PERSONNUMBER INTEGER,
	NAME VARCHAR(100),
	SURNAME VARCHAR(100),
	NATIONALITY VARCHAR(100),
	BIRTHDAY DATE,
	SALARY INTEGER,
	CONSTRAINT TEACHERS_PK PRIMARY KEY (PERSONNUMBER)
);```
We can either run the query fromthe script editor or use the visual interface (p.s. remember to add the primary key).

We populate the table with three initial professors
```SQL
INSERT INTO TEACHERS VALUES (1001, 'Roberto', 'Guanciale', 'Italian', '1979-12-08', 30000);
INSERT INTO TEACHERS VALUES (1002, 'Erik', 'Gates', 'Swedish', '1984-10-23', 29000);
INSERT INTO TEACHERS VALUES (1003, 'Jhon', 'Doe', 'German', '1940-01-23', 31000);
```

## Table courses
The schema
```SQL
CREATE TABLE PUBLIC.COURSES (
	CODE INTEGER,
	NAME VARCHAR(100)
	CONSTRAINT COURSES_PK PRIMARY KEY (CODE)
);
```
and some data
```SQL
INSERT INTO COURSES VALUES (20,'Java 1');
INSERT INTO COURSES VALUES (21,'Java 1');
INSERT INTO COURSES VALUES (22,'DBMS');
INSERT INTO COURSES VALUES (23,'C++');
INSERT INTO COURSES VALUES (24,'Assembler');
INSERT INTO COURSES VALUES (25,'Networking');
INSERT INTO COURSES VALUES (26,'Operating System');
INSERT INTO COURSES VALUES (27,'Python');
``` 

## Relation "who teaches what"
Let assume that for every course there is only one responsible teacher and that the one
teacher can supervise serveral courses. How we can represent this relation?
The simplest approach is to add an addtional attribute (`TEACHER`) to the table `COURSES`.
This attribute represent the personnumber (i.e. primary key) of the teacher responsible for the course.

Add the attribute using the UI or executing the command:
```SQL
ALTER TABLE COURSES ADD COLUMN teacher INTEGER;
```

Update the existing data to set up a proper teacher for each course
```SQL
UPDATE COURSES SET teacher=1001 WHERE code=20;
UPDATE COURSES SET teacher=1001 WHERE code=21;
UPDATE COURSES SET teacher=1001 WHERE code=22;
UPDATE COURSES SET teacher=1002 WHERE code=23;
UPDATE COURSES SET teacher=1002 WHERE code=24;
UPDATE COURSES SET teacher=1003 WHERE code=25;
UPDATE COURSES SET teacher=1003 WHERE code=26;
UPDATE COURSES SET teacher=1003 WHERE code=27;
```

To ensure integrity of the relationship the DBMS must perform the following checks:
* when a new course is inserted or a course is modified, the DBMS must check that the teacher attribute is not NULL and that
a teacher with the corresponding person number exists
* when a teacher is deleted or the teacher's person number is updated, the DBMS must check that no course is currently pointing
to this teacher.

To inform the DBMS about the relation we must create a foreing key.

1. Open the `COURSES` table and select the **Foreign key** tab.
2. Right click on the white area and select **Create new Foreign Key**.
3. Select `TEACHERS` as **reference table**. 
4. Select the attribute **teacher** as column
5. Press **ok**.

![new-connection](../images/cut-5-extkey-1.png)

The relational diagram is shown by clicking **Diagram**

![new-connection](../images/cut-5-extkey-2.png)


## Querying multiple tables
SQL query can access multiple tables. The simple mechanisms consists of table product,
which associate every element of the first table to every element of the second one.
Let `A` and `B` be two tables containing the records `a1,a2,a3` and `b1, b2, b3` respectively.
The query 
```SQL
SELECT * FROM A, B
```
returns the cartesian product of the two tables

A    | B
-----|------
a1| b1
a2| b1
a3| b1
a1| b2
a2| b2
a3| b2
a1| b3
a2| b3
a3| b3

Some example of cartesian products on the tables of the example
```SQL
SELECT * FROM COURSES, TEACHERS;
SELECT * FROM COURSES, TEACHERS, STUDENTS;
```

To extract the courses with the corresponding respinsible teacher, we can filter out
the cartesian product, using the foreign key:
```SQL
SELECT * FROM COURSES, TEACHERS WHERE courses.TEACHER = teachers.PERSONNUMBER;
```

Alternatively, we can use the `JOIN` operator:
```SQL
SELECT * FROM COURSES JOIN TEACHERS ON courses.TEACHER = teachers.PERSONNUMBER;
```

Hereafter an example that mixes both `JOIN` and `WHERE`. It returs
all courses whose name start in J and the corresponding supervisor 
```SQL
SELECT * FROM COURSES JOIN TEACHERS ON courses.TEACHER = teachers.PERSONNUMBER WHERE courses.NAME LIKE 'J%';
```

Join and cartesian products can operate on the same relation. The following (equivalent) queries 
return for every students all students that are shorter 
```SQL
SELECT *
FROM STUDENTS AS S1,
     STUDENTS AS S2
WHERE S1.height < S2.HEIGHT;

SELECT *
FROM STUDENTS AS S1
JOIN STUDENTS AS S2 ON S1.height < S2.HEIGHT;
```

The result of join and cartesian product is a table that we can group. The following
queryes return for every student the number of shorter students
```SQL
SELECT PERSONNUMBER, count(*)
FROM STUDENTS AS S1
JOIN STUDENTS AS S2 ON S1.height < S2.HEIGHT
GROUP BY PERSONNUMBER;

SELECT s1.PERSONNUMBER, name, count(*)
FROM STUDENTS AS S1
JOIN STUDENTS AS S2 ON S1.height < S2.HEIGHT
GROUP BY s1.PERSONNUMBER, s1.NAME;
```

## Left join
The following query returns all teacher and the exams they supervise
```SQL
SELECT *
FROM TEACHERS 
JOIN COURSES ON courses.TEACHER = teachers.PERSONNUMBER;
```

Add a new teacher
```SQL
INSERT INTO TEACHERS VALUES (1004, 'Cristiano', 'Ronaldo', 'Portuguese', '1940-01-23', 32000);
```

If we re-execute the query, we do not find the new teacher. In fact,
there is no course handled by Cristiano, thus no row is jeld of this teacher.
To handle this issue, we can use the `LEFT JOIN` operator; in case no row of the second table satisfies the
JOIN contition, a row contining NULL attributes is returned
```SQL
SELECT *
FROM TEACHERS 
LEFT JOIN COURSES ON courses.TEACHER = teachers.PERSONNUMBER;
```
Notice that the order count, the following query yield a different result
```SQL
SELECT *
FROM COURSES
LEFT JOIN TEACHERS ON courses.TEACHER = teachers.PERSONNUMBER;
```

## Many-to-many relationship
How model the relationship "Who takes what?". Every student can take more than one course, so we can
not just add an attribute with the course code to the table `STUDENTS`. Similarly,
the same course can be taken by more than a student, so we can not simply
add an attribute with the student's personnumber tothe table `COURSES`.
The standard solution is to add a new table, used explicitely to model this `n-to-n` relationship.

```SQL
CREATE TABLE STUDENTS_COURSES (
	studentcode VARCHAR(100),,
	coursecode INTEGER
);
```
This table has two foreign keys: `studentcode` pointing to the attribute `personnumber` of the table `STUDENT`,
and `coursecode` pointing to the attribute `code` of the table `COURSES`
```SQL
ALTER TABLE PUBLIC.STUDENTS_COURSES ADD CONSTRAINT STUDENTS_COURSES_STUDENTS_FK FOREIGN KEY (STUDENTCODE) REFERENCES PUBLIC.STUDENTS(PERSONNUMBER);
ALTER TABLE PUBLIC.STUDENTS_COURSES ADD CONSTRAINT STUDENTS_COURSES_COURSES_FK FOREIGN KEY (COURSECODE) REFERENCES PUBLIC.COURSES(CODE);
```

![new-connection](../images/cut-6-nton-1.png)

Finally, the attribute pair `studentcode,coursecode` is the primary key of the table:
the same student can not take the same course twice.
```SQL
ALTER TABLE PUBLIC.STUDENTS_COURSES ADD CONSTRAINT STUDENTS_COURSES_PK PRIMARY KEY (STUDENTCODE,COURSECODE);
```

![new-connection](../images/cut-6-nton-2.png)

Add some entries to the relation
```SQL
INSERT INTO STUDENTS_COURSES VALUES (1002, 20);
INSERT INTO STUDENTS_COURSES VALUES (1002, 21);
INSERT INTO STUDENTS_COURSES VALUES (1003, 21);
INSERT INTO STUDENTS_COURSES VALUES (1003, 22);
INSERT INTO STUDENTS_COURSES VALUES (1003, 23);
```


In the following we report some queries on the database

### List students and the exams they take
```SQL
SELECT *
FROM STUDENTS as S
JOIN STUDENTS_COURSES as SC ON S.personnumber = SC.studentcode
JOIN COURSES as C ON SC.coursecode = C.code;
```

### List students and the exams they take (including students that do not take any exam)
```SQL
SELECT *
FROM STUDENTS as S
LEFT JOIN STUDENTS_COURSES as SC ON S.personnumber = SC.studentcode
LEFT JOIN COURSES as C ON SC.coursecode = C.code;
```

### List student name, surname  and the name of exams they take (including students that do not take any exam)
```SQL
SELECT S.name, S.surname, C.name
FROM STUDENTS as S
LEFT JOIN STUDENTS_COURSES as SC ON S.personnumber = SC.studentcode
LEFT JOIN COURSES as C ON SC.coursecode = C.code;
```

### List students and the number of exams they take
```SQL
SELECT  S.name, S.surname, count(*)
FROM STUDENTS as S
JOIN STUDENTS_COURSES as SC ON S.personnumber = SC.studentcode
GROUP BY S.name, S.surname;
```

### List courses and the number of students
To be fixed
```SQL
SELECT  C.name, count(*)
FROM COURSES as C
JOIN STUDENTS_COURSES as SC ON C.code = SC.coursecode
GROUP BY C.name;
```

### List courses and the number of students (including courses with no student)
To be fixed
```SQL
SELECT  C.name, SUM(isnull(SC.coursecode, 0))
FROM COURSES as C
LEFT JOIN STUDENTS_COURSES as SC ON C.code = SC.coursecode
GROUP BY C.name;
```

### List students and their examiners
```SQL
SELECT S.name, S.surname, T.name, T.NAME
FROM STUDENTS as S
LEFT JOIN STUDENTS_COURSES as SC ON S.personnumber = SC.studentcode
LEFT JOIN COURSES as C ON SC.coursecode = C.code
LEFT JOIN TEACHERS as T ON C.teacher = T.personnumber
;
```

# Last note on JDBC
Additionally to provide common mechanism to access and manage data,
DBMS provides a transactional abstraction to the database.

A **transaction** represents a unit of work performed within a DBMS against a database, and treated in a coherent and reliable way independent of other transactions. A 
transaction generally represents any change in database. Transactions in a database environment have two main purposes:

* To provide reliable units of work that allow correct recovery from failures and keep a database consistent even in cases of system failure, when execution stops  and many operations upon a database remain uncompleted, with unclear status.
* To provide isolation between programs accessing a database concurrently. If this isolation is not provided, the programs' outcomes are possibly erroneous.

A database transaction, by definition, must be atomic, consistent, isolated and durable (ACID).
Practically, the common pattern is
A simple transaction is usually issued to the database system in a language like SQL wrapped in a transaction, using a pattern similar to the following:

1. Begin the transaction
2. Execute a set of data manipulations and/or queries
3. If no errors occur then commit the transaction and end it
4. If errors occur then rollback the transaction and end it

If no errors occurred during the execution of the transaction then the system commits the transaction.
A transaction commit operation applies all data manipulations within the scope of the transaction and persists the results to the database. If an error occurs during the transaction, or if the user specifies a rollback operation, the data manipulations within the transaction are not persisted to the database. In no case can a partial transaction be committed to the database since that would leave the database in an inconsistent state.

```JAVA
con.setAutoCommit(false);
try {
	updateSales = con.prepareStatement(updateString);
	updateSales.executeUpdate();

	updateTotal = con.prepareStatement(updateStatement);
	updateTotal.executeUpdate();

	con.commit();
} catch (Exception e ) {
	con.rollback();
}
con.close();
```



