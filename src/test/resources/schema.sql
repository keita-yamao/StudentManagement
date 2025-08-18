CREATE TABLE IF NOT EXISTS students
(
  student_id VARCHAR(10) PRIMARY KEY,
  name VARCHAR(60) NOT NULL,
  furigana VARCHAR(60) NOT NULL,
  nickname VARCHAR(60) NOT NULL,
  email VARCHAR(254) NOT NULL,
  address VARCHAR(161),
  age INT,
  gender VARCHAR(20),
  remark VARCHAR(255),
  is_deleted boolean NOT NULL
);

CREATE TABLE IF NOT EXISTS students_courses
(
  id INT AUTO_INCREMENT PRIMARY KEY,
  student_id VARCHAR(10) NOT NULL,
  course_id VARCHAR(5) NOT NULL,
  start_date DATE,
  expected_completion_date DATE
);

CREATE TABLE IF NOT EXISTS courses
(
  course_id VARCHAR(5) PRIMARY KEY,
  course VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS course_statuses
(
id INT AUTO_INCREMENT PRIMARY KEY,
students_courses_id INT NOT NULL,
status VARCHAR(5) NOT NULL
);
