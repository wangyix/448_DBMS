CS448 - Project 2
Author: Yixin Wang
Login: wang420
Email: wang420@purdue.edu


Java project executable is 448_DBMS.jar.
To execute, cd to the project directory.
There are 3 ways to execute:

java -jar 448_DBMS.jar 
java -jar 448_DBMS.jar username
java -jar 448_DBMS.jar username < commands.sql

The first option will prompt for the username, then execute in interactive mode.
The second option will log in using the username arg  and execute in interactive mode.
The third option will log in using the username arg and execute in batch mode, executing
all the commands in the given .sql file.

Note: if using input redirection for executing SQL commands in a file, the
program will execute the QUIT command when the end of file if no QUIT command
exists in the file.
