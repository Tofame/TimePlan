-- Insert Teachers
INSERT INTO teacher (name, title, age) VALUES ('Dr. Andrzej Piątek', 'Profesor nadzwyczajny', 55);
INSERT INTO teacher (name, title, age) VALUES ('mgr Halina Sobotka', 'Adiunkt', 42);

-- Insert Subjects
INSERT INTO subject (name, etcs, code_name) VALUES ('Zaawansowana Prokrastynacja', 3, 'ZP1337');
INSERT INTO subject (name, etcs, code_name) VALUES ('Kawaologia stosowana', 2, 'KAW01');

-- Insert Students
INSERT INTO student (name, student_index, group_lesson, group_lecture)
VALUES ('Zofia Ziewalska', 'S99999', 5, 5);
INSERT INTO student (name, student_index, group_lesson, group_lecture)
VALUES ('Janek Drzemka', 'S88888', 5, 5);

-- Insert Classrooms
INSERT INTO classroom (adress) VALUES ('Strefa Chillu A/213');
INSERT INTO classroom (adress) VALUES ('Bufet Główny B/007');

-- Inserts activities
INSERT INTO activity (
    type, group_number, subject_id, classroom_id, teacher_id,
    student_count, start_time, duration
) VALUES
      (0, 5, 1, 1, 1, 12, '2025-06-06T13:00:00', 90),
      (0, 5, 1, 1, 1, 12, '2025-06-27T11:00:00', 90),
      (0, 5, 1, 1, 1, 12, '2025-06-23T11:00:00', 90),
      (1, 5, 2, 2, 2, 30, '2025-06-06T13:00:00', 60);