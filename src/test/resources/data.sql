INSERT INTO students (student_id,name,furigana,nickname,email,address,age,gender,is_deleted)
VALUES
('1','山本太郎','ヤマモトタロウ','タロ','taro@exampl.com','東京',25,'男性',0),
('2','鈴木一郎','スズキイチロウ','イチ','ichiro@exampl.com','大阪',30,'男性',0),
('3','田中花子','タナカハナコ','ハナ','hana@exampl.com','北海道',22,'女性',0),
('4','佐藤良子','サトウリョウコ','リョウ','ryoko@exampl.com','福岡',28,'女性',0),
('5','伊藤遥','イトウハルカ','ハル','haruka@exampl.com','愛知',35,'その他',0),
('6','佐藤健','サトウケン','ケン','ken.sato@exampl.com','京都',28,'男性',1);

INSERT INTO students_courses (student_id,course_id,start_date,expected_completion_date)
VALUES
('1','00001','2023-09-01','2024-09-01'),
('2','00002','2024-04-01','2025-04-01'),
('3','00001','2023-09-01','2024-09-01'),
('4','00004','2023-09-01','2024-09-01'),
('5','00005','2023-09-01','2024-09-01'),
('6','00001','2025-05-08','2026-05-08');

INSERT INTO courses (course_id,course)
VALUES
('00001','Javaコース'),
('00002','AWSコース'),
('00003','VBAコース'),
('00004','デザインコース'),
('00005','Web制作コース');

INSERT INTO course_statuses (students_courses_id,status)
VALUES
(1,'修了'),
(2,'仮申込'),
(3,'本申込'),
(4,'受講中'),
(5,'修了'),
(6,'仮申込');
