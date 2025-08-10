CREATE TABLE IF NOT EXISTS students (
   id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
   name VARCHAR(100) NOT NULL,
   furigana VARCHAR(100) NOT NULL,
   nickname VARCHAR(100),
   mail VARCHAR(100) NOT NULL,
   region VARCHAR(100),
   age INT,
   gender VARCHAR(100),
   remark VARCHAR(100),
   isdeleted VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS students_courses (
   id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
   students_id INT NOT NULL,
   courses_name VARCHAR(100) NOT NULL,
   start_date TIMESTAMP DEFAULT NULL,
   end_date TIMESTAMP DEFAULT NULL
);


CREATE TABLE IF NOT EXISTS student_appstatus (
   id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
   student_course_id INT NOT NULL,
   status VARCHAR(100) NOT NULL
);


