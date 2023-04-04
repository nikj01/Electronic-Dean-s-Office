INSERT INTO Faculties (name, deleted) VALUES
('FM', false),
('fac-1', false),
('fac-2', false),
('fac-3', false);

INSERT INTO departments (name, faculty_id, deleted) VALUES
('AVP', '1', false),
('dep-1', '1', false),
('dep-2', '2', false),
('dep-3', '2', false),
('dep-4', '3', false),
('dep-5', '3', false),
('dep-6', '4', false),
('dep-7', '4', false);

INSERT INTO People (uid, date_of_birth, email, name, patronymic, password, surname, deleted) VALUES
(100, '1963-05-05', 'kostikov_email_100@gmail.com', 'Oleksandr', 'Anatoliyovych', 'qwerty00', 'Kostikov', false),
(102, '1970-01-06', 'teacher_email_102@gmail.com', 'teacher_102 name', 'teacher_102 patr', 'qwerty01', 'teacher_102 sur', false),
(101, '1970-01-01', 'teacher_email_101@gmail.com', 'teacher_101 name', 'teacher_101 patr', 'qwerty00', 'teacher_101 sur', false),
(103, '1973-01-11', 'teacher_email_103@gmail.com', 'teacher_103 name', 'teacher_103 patr', 'qwerty02', 'teacher_103 sur', false),
(104, '1973-01-16', 'teacher_email_104@gmail.com', 'teacher_104 name', 'teacher_104 patr', 'qwerty03', 'teacher_104 sur', false),
(105, '1976-08-21', 'teacher_email_105@gmail.com', 'teacher_105 name', 'teacher_105 patr', 'qwerty04', 'teacher_105 sur', false),
(106, '1976-08-26', 'teacher_email_106@gmail.com', 'teacher_106 name', 'teacher_106 patr', 'qwerty05', 'teacher_106 sur', false),
(107, '1979-08-13', 'teacher_email_107@gmail.com', 'teacher_107 name', 'teacher_107 patr', 'qwerty06', 'teacher_107 sur', false),
(108, '1979-08-28', 'teacher_email_108@gmail.com', 'teacher_108 name', 'teacher_108 patr', 'qwerty07', 'teacher_108 sur', false),
(109, '1982-12-01', 'teacher_email_109@gmail.com', 'teacher_109 name', 'teacher_109 patr', 'qwerty08', 'teacher_109 sur', false),
(110, '1982-11-04', 'teacher_email_110@gmail.com', 'teacher_110 name', 'teacher_110 patr', 'qwerty09', 'teacher_110 sur', false),
(111, '1985-10-07', 'teacher_email_111@gmail.com', 'teacher_111 name', 'teacher_111 patr', 'qwerty10', 'teacher_111 sur', false),
(112, '1985-10-10', 'teacher_email_112@gmail.com', 'teacher_112 name', 'teacher_112 patr', 'qwerty11', 'teacher_112 sur', false),
(113, '1988-01-13', 'teacher_email_113@gmail.com', 'teacher_113 name', 'teacher_113 patr', 'qwerty12', 'teacher_113 sur', false),
(114, '1988-05-16', 'teacher_email_114@gmail.com', 'teacher_114 name', 'teacher_114 patr', 'qwerty13', 'teacher_114 sur', false),
(115, '1991-02-19', 'teacher_email_115@gmail.com', 'teacher_115 name', 'teacher_115 patr', 'qwerty14', 'teacher_115 sur', false),
(116, '1991-08-01', 'teacher_email_116@gmail.com', 'teacher_116 name', 'teacher_116 patr', 'qwerty15', 'teacher_116 sur', false),
(117, '1994-12-18', 'teacher_email_117@gmail.com', 'teacher_117 name', 'teacher_117 patr', 'qwerty16', 'teacher_117 sur', false),
(118, '1994-11-16', 'teacher_email_118@gmail.com', 'teacher_118 name', 'teacher_118 patr', 'qwerty17', 'teacher_118 sur', false),
(119, '1997-04-24', 'teacher_email_119@gmail.com', 'teacher_119 name', 'teacher_119 patr', 'qwerty18', 'teacher_119 sur', false),
(120, '7777-05-05', '7777prepod@gmail.com', '7777prepod', '7777prepod', '7777prepod', '7777prepod', true),

(500, '1988-03-09', 'dw_email_1@gmail.com', 'dw_01 name', 'dw_01 patr', 'qwerty19', 'dw_01 sur', false),
(501, '1975-10-29', 'dw_email_2@gmail.com', 'dw_02 name', 'dw_02 patr', 'qwerty20', 'dw_02 sur', false),
(502, '1980-05-13', 'dw_email_3@gmail.com', 'dw_03 name', 'dw_03 patr', 'qwerty21', 'dw_03 sur', false),
(503, '7777-05-13', '777dw@gmail.com', '777dw name', '777dw patr', '777dw', '777 sur', true),
(504, '1988-03-09', 'dw_email_11@gmail.com', 'dw_011 name', 'dw_011 patr', 'qwerty19', 'dw_011 sur', false),
(505, '1975-10-29', 'dw_email_22@gmail.com', 'dw_022 name', 'dw_022 patr', 'qwerty20', 'dw_022 sur', false),
(506, '1980-05-13', 'dw_email_33@gmail.com', 'dw_033 name', 'dw_033 patr', 'qwerty21', 'dw_033 sur', false),
(507, '7777-05-13', '7777dw@gmail.com', '7777dw name', '7777dw patr', '7777dw', '7777 sur', true),

(12723903, '2001-11-03', 'nik.jurawlyow2001@gmail.com', 'Mykyta', 'Olehovych', 'qwerty777', 'Zhuravlov', false),
(12723904, '2001-07-01', 'st_email_04@gmail.com', 'st_04 name', 'st_04 patr', 'qwerty22', 'st_04 sur', false),
(12723905, '2001-09-06', 'st_email_05@gmail.com', 'st_05 name', 'st_05 patr', 'qwerty23', 'st_05 sur', false),
(12723906, '2001-09-11', 'st_email_06@gmail.com', 'st_06 name', 'st_06 patr', 'qwerty24', 'st_06 sur', false),
(12723907, '2001-05-16', 'st_email_07@gmail.com', 'st_07 name', 'st_07 patr', 'qwerty25', 'st_07 sur', false),
(12723908, '2001-08-21', 'st_email_08@gmail.com', 'st_08 name', 'st_08 patr', 'qwerty26', 'st_08 sur', false),
(12723909, '2001-07-26', 'st_email_09@gmail.com', 'st_09 name', 'st_09 patr', 'qwerty27', 'st_09 sur', false),
(12723910, '2001-07-13', 'st_email_10@gmail.com', 'st_10 name', 'st_10 patr', 'qwerty28', 'st_10 sur', false),
(12723911, '2001-09-28', 'st_email_11@gmail.com', 'st_11 name', 'st_11 patr', 'qwerty29', 'st_11 sur', false),
(12723912, '2001-09-01', 'st_email_12@gmail.com', 'st_12 name', 'st_12 patr', 'qwerty30', 'st_12 sur', false),
(12723913, '2001-08-04', 'st_email_13@gmail.com', 'st_13 name', 'st_13 patr', 'qwerty31', 'st_13 sur', false),
(12723914, '2001-03-07', 'st_email_14@gmail.com', 'st_14 name', 'st_14 patr', 'qwerty32', 'st_14 sur', false),
(12723915, '2001-04-10', 'st_email_15@gmail.com', 'st_15 name', 'st_15 patr', 'qwerty33', 'st_15 sur', false),
(12723916, '2001-11-13', 'st_email_16@gmail.com', 'st_16 name', 'st_16 patr', 'qwerty34', 'st_16 sur', false),
(12723917, '2001-12-16', 'st_email_17@gmail.com', 'st_17 name', 'st_17 patr', 'qwerty35', 'st_17 sur', false),
(12723918, '2001-01-19', 'st_email_18@gmail.com', 'st_18 name', 'st_18 patr', 'qwerty36', 'st_18 sur', false),
(12723919, '2001-05-01', 'st_email_19@gmail.com', 'st_19 name', 'st_19 patr', 'qwerty37', 'st_19 sur', false),
(12723920, '2001-05-18', 'st_email_20@gmail.com', 'st_20 name', 'st_20 patr', 'qwerty38', 'st_20 sur', false),
(12723921, '2001-06-16', 'st_email_21@gmail.com', 'st_21 name', 'st_21 patr', 'qwerty39', 'st_21 sur', false),
(12723922, '2001-01-24', 'st_email_22@gmail.com', 'st_22 name', 'st_22 patr', 'qwerty40', 'st_22 sur', false),
(12723333, '2001-01-24', '12723333@gmail.com', '12723333 name', '12723333 patr', '12723333', '12723333 sur', true);
-- (12723350, '2001-01-24', '50@gmail.com', '50 name', '50 patr', '50', '50 sur', false),
-- (12723351, '2001-01-24', '51@gmail.com', '51 name', '51 patr', '51', '51 sur', false);

INSERT INTO person_roles (person_uid, roles) VALUES
(100,3),
(102,3),
(101,3),
(103,3),
(104,3),
(105,3),
(106,3),
(107,3),
(108,3),
(109,3),
(110,3),
(111,3),
(112,3),
(113,3),
(114,3),
(115,3),
(116,3),
(117,3),
(118,3),
(119,3),
(120,3),

(500,1),
(500,2),
(501,2),
(502,2),
(503,2),
(504,1),
(505,2),
(506,2),
(507,2),

(12723903,0),
(12723904,4),
(12723905,4),
(12723906,4),
(12723907,4),
(12723908,4),
(12723909,4),
(12723910,4),
(12723911,4),
(12723912,4),
(12723913,4),
(12723914,4),
(12723915,4),
(12723916,4),
(12723917,4),
(12723918,4),
(12723919,4),
(12723920,4),
(12723921,4),
(12723922,4),
(12723333,4);
-- (12723350,4),
-- (12723351,4);

INSERT INTO Deanery_workers (uid, faculty_id) VALUES
(500, '1'),
(501, '1'),
(502, '1'),
(503, '1'),
(504, '2'),
(505, '2'),
(506, '2'),
(507, '2');

INSERT INTO Teachers (uid, department_id) VALUES
(100, '1'),
(101, '1'),
(102, '1'),
(103, '1'),
(104, '1'),
(105, '2'),
(106, '2'),
(107, '2'),
(108, '2'),
(109, '2'),
(110, '3'),
(111, '3'),
(112, '3'),
(113, '3'),
(114, '3'),
(115, '4'),
(116, '4'),
(117, '4'),
(118, '4'),
(119, '4');

INSERT INTO Stundent_groups (name, curator_uid, department_id, group_leader_uid, deleted) VALUES
('KI 19-1', 100, '1', null, false),
('KI 19-1t', 101, '1', null, false),
('APP 19-1', 100, '1', null, false),
('APP 19-1t', 101, '1', null, false),
('KH 19-1', 105, '2', null, false),
('KH 19-2', 106, '2', null, false),
('KM 19-1', 110, '3', null, false),
('KM 19-1t', 111, '3', null, false),
('KK 19-1', 115, '4', null, false),
('KK 19-1t', 116, '4', null, false);
-- ('KI 22-1', 100, '1', null, false);

INSERT INTO Students (uid, group_id) VALUES
(12723903, '1'),
(12723904, '1'),
(12723905, '1'),
(12723906, '1'),
(12723907, '1'),
(12723333, '1');
-- (12723350, '11'),
-- (12723351, '11');
--
-- INSERT INTO Teachers_journals (id, teacher_uid) VALUES
-- (DEFAULT, 100),
-- (DEFAULT, 101),
-- (DEFAULT, 102);
--
-- INSERT INTO Journals_pages (id, page_name, journal_id) VALUES
-- (DEFAULT, 'IPZ', 1),
-- (DEFAULT, 'AKM', 1);
--
-- -- INSERT INTO Events (id, date, description, event_theme, event_type, page_id) VALUES
-- -- (DEFAULT, '2023-02-28', 'descr1', 'Theme 1', 0, 1),
-- -- (DEFAULT, '2023-02-28', 'descr2', 'Theme 2', 2, 1);
-- --
-- -- INSERT INTO events_student_groups (event_id, student_groups_name) VALUES
-- -- (1, 'KI 19-1');
-- --
-- -- INSERT INTO reports (id, event_id) VALUES
-- -- (DEFAULT, 1);
--
