-- Insert Teachers
INSERT INTO teacher (name, title, birth_date) VALUES
('Andrzej Piątek', 'Profesor nadzwyczajny', '1970-01-01'),
('Halina Sobotka', 'Doktor Profesor Habilitowana', '1983-01-01'),
('Marek Kowalski', 'Profesor zwyczajny', '1965-01-01'),
('Joanna Nowak', 'Znawczyni', '1987-01-01'),
('Tomasz Jawa', 'Bóg Java', '1180-01-01');

-- Insert Subjects
INSERT INTO subject (name, etcs, code_name) VALUES
('Zaawansowana Prokrastynacja', 3, 'ZP1337'),
('Kawaologia stosowana', 2, 'KAW01'),
('Analiza Danych', 5, 'AD101'),
('Wstęp do Programowania', 4, 'WP202'),
('Zmadowana Matematyka', 7, 'MAD'),
('Metody Statystyczne', 3, 'MS303'),
('Sadystyczna Analiza Danych', 7, 'SAD'),
('Programowanie w Javie', 5, 'JAVA501'),
('Sieci Komputerowe', 4, 'NET404'),
('Bezpieczeństwo IT', 3, 'SEC303'),
('Sztuczna Inteligencja', 6, 'AI606'),
('Bazy Danych', 4, 'DB404'),
('Projektowanie UX/UI', 3, 'UX303'),
('Analiza Algorytmów', 5, 'ALG505'),
('Systemy Operacyjne', 5, 'OS505'),
('Technologie Programowania Rozproszonego', 5, 'TPO');

-- Insert ActivityGroups (LECTURES, LESSONS etc.)
INSERT INTO Activity_Group (type, group_number) VALUES
( 0, 5),
( 1, 5),
( 0, 6),
( 1, 6);

-- Insert Students
INSERT INTO student (name, student_index, group_lesson_id, group_lecture_id, password) VALUES
('Zofia Ziewalska', 's123', 0, 1, '123'),
('Janek Drzemka', 's404', 0, 1, '123');

-- Insert Classrooms
INSERT INTO classroom (address) VALUES
('Strefa Chillu A/213'),
('Bufet Główny B/007'),
('Sala Komputerowa C/101'),
('Audytorium D/102'),
('Laboratorium E/304'),
('Komnata Tajemnic HP/404');

-- Inserts activities
INSERT INTO activity (
    group_id, subject_id, classroom_id, teacher_id,
    start_time, duration
) VALUES
      (1, 3, 3, 3,  '2025-05-05T09:00', 90),  -- Analiza Danych (5,0)
      (2, 4, 4, 4,  '2025-05-07T11:00', 60),  -- Wstęp do Programowania (5,1)
      (3, 5, 5, 5,  '2025-05-08T14:00', 90),  -- Zmadowana Matematyka (6,0)

      (1, 1, 1, 1,  '2025-05-12T10:00', 90),  -- Zaawansowana Prokrastynacja (5,0)
      (2, 2, 2, 2,  '2025-05-14T13:00', 60),  -- Kawaologia stosowana (5,1)
      (3, 7, 3, 4,  '2025-05-19T08:00', 90),  -- Sadystyczna Analiza Danych (6,0)
      (2, 6, 4, 3,  '2025-05-21T12:00', 60),  -- Metody Statystyczne (5,1)
      (3, 5, 5, 5,  '2025-05-22T15:00', 90),  -- Zmadowana Matematyka (6,0)

      (1, 8, 1, 1,  '2025-05-13T08:00', 90),  -- Programowanie w Javie (5,0)
      (4, 9, 2, 2,  '2025-05-13T11:00', 60),  -- Sieci Komputerowe (6,1)
      (1, 10, 3, 3, '2025-05-14T14:00', 90),  -- Bezpieczeństwo IT (5,0)
      (4, 11, 4, 4, '2025-05-15T12:00', 60),  -- Sztuczna Inteligencja (6,1)
      (1, 12, 5, 5, '2025-05-16T10:00', 90),  -- Bazy Danych (5,0)

      (3, 13, 1, 1, '2025-05-19T13:00', 90),  -- Projektowanie UX/UI (6,0)
      (2, 14, 2, 2, '2025-05-20T11:00', 60),  -- Analiza Algorytmów (5,1)
      (3, 15, 3, 3, '2025-05-21T08:00', 90),  -- Systemy Operacyjne (6,0)

      (1, 3, 3, 3, '2025-05-26T09:00', 90),   -- Analiza Danych (5,0)
      (2, 4, 4, 4, '2025-05-27T11:00', 60),   -- Wstęp do Programowania (5,1)
      (1, 16, 6, 5, '2025-05-27T15:45', 90),  -- TPO (5,0)
      (3, 5, 5, 5, '2025-05-28T14:00', 90),   -- Zmadowana Matematyka (6,0)
      (1, 1, 1, 1, '2025-05-29T10:00', 90),   -- Zaawansowana Prokrastynacja (5,0)
      (2, 2, 2, 2, '2025-05-30T13:00', 60),   -- Kawaologia stosowana (5,1)

      (1, 1, 1, 1, '2025-06-02T09:00', 90),   -- Zaawansowana Prokrastynacja (5,0)
      (2, 2, 2, 2, '2025-06-04T11:00', 60),   -- Kawaologia stosowana (5,1)
      (3, 3, 3, 3, '2025-06-05T14:00', 90),   -- Analiza Danych (6,0)

      (1, 4, 4, 4, '2025-06-09T10:00', 90),   -- Wstęp do Programowania (5,0)
      (4, 7, 5, 5, '2025-06-11T13:00', 60),   -- Sadystyczna Analiza Danych (6,1)
      (1, 1, 1, 1, '2025-06-16T08:00', 90),   -- Zaawansowana Prokrastynacja (5,0)
      (2, 2, 2, 2, '2025-06-18T12:00', 60),   -- Kawaologia stosowana (5,1)
      (3, 3, 3, 3, '2025-06-20T14:00', 90),   -- Analiza Danych (6,0)

      (1, 8, 1, 1, '2025-06-17T08:00', 90),   -- Programowanie w Javie (5,0)
      (4, 9, 2, 2, '2025-06-18T11:00', 60),   -- Sieci Komputerowe (6,1)
      (1, 10, 3, 3, '2025-06-19T14:00', 90),  -- Bezpieczeństwo IT (5,0)
      (4, 11, 4, 4, '2025-06-20T12:00', 60),  -- Sztuczna Inteligencja (6,1)
      (1, 12, 5, 5, '2025-06-21T10:00', 90),  -- Bazy Danych (5,0)

      (3, 13, 1, 1, '2025-06-23T13:00', 90),  -- Projektowanie UX/UI (6,0)
      (2, 14, 2, 2, '2025-06-24T11:00', 60),  -- Analiza Algorytmów (5,1)
      (3, 15, 3, 3, '2025-06-25T08:00', 90);  -- Systemy Operacyjne (6,0)