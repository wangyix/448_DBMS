CREATE TABLE DEPARTMENT(deptid INT CHECK(deptid>0 AND deptid<100), dname CHAR(30), location CHAR(30), PRIMARY KEY(deptid));
CREATE TABLE FACULTY(fid INT CHECK(fid!=0), fname CHAR(30), dept INT, PRIMARY KEY(fid), FOREIGN KEY(dept) REFERENCES DEPARTMENT(deptid));
CREATE TABLE STUDENT(snum INT, sname CHAR(30), dep INT, slevel CHAR(10) CHECK(slevel='JR' OR slevel='SR' OR slevel='SO' OR slevel='FR'), age INT CHECK(age>16), PRIMARY KEY(snum), FOREIGN KEY (dep) REFERENCES DEPARTMENT(deptid));
CREATE TABLE CLASS(cname CHAR(30), meets_at CHAR(30), room CHAR(10), faculty_id INT, PRIMARY KEY(cname), FOREIGN KEY(faculty_id) REFERENCES FACULTY(fid));
CREATE TABLE ENROLLED(student_num INT, class_name CHAR(30), PRIMARY KEY(student_num, class_name), FOREIGN KEY(student_num) REFERENCES STUDENT(snum), FOREIGN KEY(class_name) REFERENCES CLASS(cname));
CREATE TABLE ENROLLED(student_num INT, class_name CHAR(30), PRIMARY KEY(student_num, class_name), FOREIGN KEY(student_num) REFERENCES STUDENT(snum), FOREIGN KEY(class_name) REFERENCES CLASS(cname));
CREATE TABLE GRADE(student_num INT, class_name CHAR(30), PRIMARY KEY(student_num, class_name), FOREIGN KEY(student_num) REFERENCES STUDENT(num), FOREIGN KEY(class_name) REFERENCES CLASS(cname));
INSERT INTO DEPARTMENT VALUES (101, 'Computer Sciences','West Lafayette');
INSERT INTO FACULTY VALUES (0,'Layton',11);
INSERT INTO STUDENT VALUES (16711,'A.Smith',22,'A',20);
INSERT INTO DEPARTMENT VALUES (11,'Computer Sciences','West Lafayette');
INSERT INTO DEPARTMENT VALUES (22,'Management','West Lafayette');
INSERT INTO DEPARTMENT VALUES (33,'Medical Education','Purdue Calumet');
INSERT INTO DEPARTMENT VALUES (44,'Education','Purdue North Central');
INSERT INTO DEPARTMENT VALUES (1,'Pharmacal Sciences','Indianapolis');
INSERT INTO DEPARTMENT VALUES (66,'Physics', 'West Lafayette');
INSERT INTO STUDENT VALUES (14181,'Jack',11,'SO',17);
INSERT INTO STUDENT VALUES (16711,'A.Smith',22,'FR',20);
INSERT INTO STUDENT VALUES (12341,'Banks',33,'SR',18);
INSERT INTO STUDENT VALUES (37261,'M.Lee',1,'SO',22);
INSERT INTO STUDENT VALUES (48291,'Bale',33,'JR',22);
INSERT INTO STUDENT VALUES (57651,'Lim',11,'SR',19);
INSERT INTO STUDENT VALUES (10191,'Sharon',22,'FR',22);
INSERT INTO STUDENT VALUES (73571,'Johnson',11,'JR',27);
INSERT INTO STUDENT VALUES (80161,'E.Cho',1,'JR',27);
INSERT INTO STUDENT VALUES (80162,'Angin',11,'SR',25);
INSERT INTO FACULTY VALUES (1010,'Layton',11);
INSERT INTO FACULTY VALUES (1020,'Jungles',22);
INSERT INTO FACULTY VALUES (1030,'Guzaldo',1);
INSERT INTO FACULTY VALUES (1040,'Boling',44);
INSERT INTO FACULTY VALUES (1050,'Mason',11);
INSERT INTO FACULTY VALUES (1060,'Zwink',22);
INSERT INTO FACULTY VALUES (1070,'Walton',1);
INSERT INTO FACULTY VALUES (1080,'Teach',1);
INSERT INTO FACULTY VALUES (1090,'Jason',1);
INSERT INTO CLASS VALUES ('ENG400','8:30','U003',1040);
INSERT INTO CLASS VALUES ('ENG320', '10:30','R128',1040);
INSERT INTO CLASS VALUES ('COM10000', '12:30','L108',1040);
INSERT INTO CLASS VALUES ('ME30800', '12:00','R128',1020);
INSERT INTO CLASS VALUES ('CS448', '11:00','R128',1010);
INSERT INTO CLASS VALUES ('HIS21000', '11:00','L108',1040);
INSERT INTO CLASS VALUES ('MATH27500', '15:30','L108',1050);
INSERT INTO CLASS VALUES ('STAT11000', '13:00','R128',1050);
INSERT INTO CLASS VALUES ('PHYS10000', '14:00','U003',1010);
INSERT INTO ENROLLED VALUES (14181,'CS448');
INSERT INTO ENROLLED VALUES (14181,'MATH27500');
INSERT INTO ENROLLED VALUES (12341,'ENG400');
INSERT INTO ENROLLED VALUES (12341,'MATH27500');
INSERT INTO ENROLLED VALUES (80161,'ENG400');
INSERT INTO ENROLLED VALUES (80161,'ENG320');
INSERT INTO ENROLLED VALUES (80161,'HIS21000');
INSERT INTO ENROLLED VALUES (80161,'STAT11000');
INSERT INTO ENROLLED VALUES (14181,'STAT11000');
INSERT INTO ENROLLED VALUES (12341,'COM10000');
INSERT INTO ENROLLED VALUES (16711,'ENG400');
INSERT INTO ENROLLED VALUES (16711,'STAT11000');
INSERT INTO ENROLLED VALUES (12341,'HIS21000');
INSERT INTO ENROLLED VALUES (57651,'PHYS10000');
INSERT INTO ENROLLED VALUES (57651,'ENG320');
INSERT INTO ENROLLED VALUES (57651,'COM10000');
CREATE USER user1 User-A;
CREATE USER user2 User-B;
DELETE USER user3;
CREATE USER user4 User-A;
DELETE USER user4;
CREATE SUBSCHEMA STUDENT sname,slevel;
CREATE SUBSCHEMA DEPARTMENT dname,location;
CREATE SUBSCHEMA GRADE cname;
CREATE SUBSCHEMA FACULTY fffid,fname;
HELP TABLES;
HELP DESCRIBE CLASS;
QUIT;