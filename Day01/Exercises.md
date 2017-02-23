# EXERCISES
* Read http://www.w3resource.com/sql/tutorials.php
* Try http://www.w3resource.com/sql-exercises/

## SALESMAN
Design a schema (table) to handle salesmen.
Every salesman has a unique identifier, name, city and commission (a percentage)

Fill the table with the following data

salesman_id | name | city | commission
------------|------|------|------------
5001 | James Hoog | New York | 0.15
5002 | Nail Knite  | Paris | 0.13
5005 | Pit Alex | London | 0.11
5006 | Mc Lyon | Paris | 0.14
5003 | Lauson Hen | Stockholm | 0.12

Add the following salesman using an INSERT statement

salesman_id | name | city | commission
------------|------|------|------------
5007 | Paul Adam | Rome | 0.13


* Write a SQL statement to display all the information of all salesmen
* Write a query to display the sum of two numbers 10 and 15 from every salesman
* Write a query to display the result of an arithmetic expression for every salesman
* Write a query to display three numbers in three columns for every salesman
* Write a SQL statement to display a string "This is SQL Exercise, Practice and Solution" for every salesman
* Write a SQL statement to display specific columns like name and commission for all the salesmen
* Write a query which will retrieve the name of all salesmen, getting orders from the commission
* Write a query which will retrieve the name of all salesmen, getting orders from the commission (inverse order)
* Write a SQL statement to display names and city of salesman, who belongs to the city of Paris
* Write a SQL statement to display all the information for those salesmen with a commission up to 0.11
* Write a SQL query to display the salesman ID followed by its location for each salesman which have a commission above 0.11

# CRUD
Design a schema and build a CRUD CLI for the salesmen table

Extend the CRUD to print the result of some of the queries of the previous exercise.
