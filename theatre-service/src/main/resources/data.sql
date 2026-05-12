-- ───── THEATRES ─────
INSERT INTO theatres (name, city, address) VALUES
                                               ('PVR Nashik',        'Nashik',  'College Road, Nashik'),
                                               ('INOX Pune',         'Pune',    'FC Road, Shivajinagar'),
                                               ('Cinepolis Mumbai',  'Mumbai',  'Linking Road, Bandra'),
                                               ('PVR Aurangabad',    'Aurangabad', 'Prozone Mall');

-- ───── SCREENS ─────
-- PVR Nashik (theatre_id = 1)
INSERT INTO screens (name, total_seats, theatre_id) VALUES
                                                        ('Screen 1', 100, 1),
                                                        ('Screen 2', 80,  1);

-- INOX Pune (theatre_id = 2)
INSERT INTO screens (name, total_seats, theatre_id) VALUES
                                                        ('Screen 1', 120, 2),
                                                        ('Screen 2', 90,  2);

-- Cinepolis Mumbai (theatre_id = 3)
INSERT INTO screens (name, total_seats, theatre_id) VALUES
    ('Screen 1', 150, 3);

-- PVR Aurangabad (theatre_id = 4)
INSERT INTO screens (name, total_seats, theatre_id) VALUES
    ('Screen 1', 100, 4);

-- ───── SEATS — PVR Nashik Screen 1 (screen_id = 1) ─────
INSERT INTO seats (seat_number, seat_type, screen_id) VALUES
                                                          ('A1', 'REGULAR', 1), ('A2', 'REGULAR', 1), ('A3', 'REGULAR', 1),
                                                          ('A4', 'REGULAR', 1), ('A5', 'REGULAR', 1),
                                                          ('B1', 'REGULAR', 1), ('B2', 'REGULAR', 1), ('B3', 'REGULAR', 1),
                                                          ('B4', 'REGULAR', 1), ('B5', 'REGULAR', 1),
                                                          ('C1', 'PREMIUM', 1), ('C2', 'PREMIUM', 1), ('C3', 'PREMIUM', 1),
                                                          ('C4', 'PREMIUM', 1), ('C5', 'PREMIUM', 1),
                                                          ('D1', 'VIP',     1), ('D2', 'VIP',     1), ('D3', 'VIP',     1);

-- ───── SEATS — PVR Nashik Screen 2 (screen_id = 2) ─────
INSERT INTO seats (seat_number, seat_type, screen_id) VALUES
                                                          ('A1', 'REGULAR', 2), ('A2', 'REGULAR', 2), ('A3', 'REGULAR', 2),
                                                          ('B1', 'REGULAR', 2), ('B2', 'REGULAR', 2), ('B3', 'REGULAR', 2),
                                                          ('C1', 'PREMIUM', 2), ('C2', 'PREMIUM', 2),
                                                          ('D1', 'VIP',     2), ('D2', 'VIP',     2);

-- ───── SEATS — INOX Pune Screen 1 (screen_id = 3) ─────
INSERT INTO seats (seat_number, seat_type, screen_id) VALUES
                                                          ('A1', 'REGULAR', 3), ('A2', 'REGULAR', 3), ('A3', 'REGULAR', 3),
                                                          ('A4', 'REGULAR', 3), ('A5', 'REGULAR', 3),
                                                          ('B1', 'REGULAR', 3), ('B2', 'REGULAR', 3), ('B3', 'REGULAR', 3),
                                                          ('C1', 'PREMIUM', 3), ('C2', 'PREMIUM', 3), ('C3', 'PREMIUM', 3),
                                                          ('D1', 'VIP',     3), ('D2', 'VIP',     3);

-- ───── SHOWS ─────
-- movieId 1 = Pushpa 2, movieId 2 = Kalki, movieId 3 = Stree 2

-- PVR Nashik Screen 1 (screen_id = 1)
INSERT INTO shows (movie_id, screen_id, show_date, show_time, show_status, price) VALUES
                                                                                      (1, 1, '2026-05-15', '10:00:00', 'SCHEDULED', 200.0),
                                                                                      (1, 1, '2026-05-15', '14:00:00', 'SCHEDULED', 250.0),
                                                                                      (1, 1, '2026-05-15', '18:00:00', 'SCHEDULED', 300.0),
                                                                                      (1, 1, '2026-05-15', '21:30:00', 'SCHEDULED', 350.0),
                                                                                      (2, 1, '2026-05-16', '11:00:00', 'SCHEDULED', 220.0),
                                                                                      (2, 1, '2026-05-16', '15:00:00', 'SCHEDULED', 270.0),
                                                                                      (3, 1, '2026-05-17', '18:00:00', 'SCHEDULED', 200.0);

-- PVR Nashik Screen 2 (screen_id = 2)
INSERT INTO shows (movie_id, screen_id, show_date, show_time, show_status, price) VALUES
                                                                                      (2, 2, '2026-05-15', '10:00:00', 'SCHEDULED', 180.0),
                                                                                      (3, 2, '2026-05-15', '14:00:00', 'SCHEDULED', 200.0),
                                                                                      (1, 2, '2026-05-15', '19:00:00', 'SCHEDULED', 280.0);

-- INOX Pune Screen 1 (screen_id = 3)
INSERT INTO shows (movie_id, screen_id, show_date, show_time, show_status, price) VALUES
                                                                                      (1, 3, '2026-05-15', '09:00:00', 'SCHEDULED', 190.0),
                                                                                      (1, 3, '2026-05-15', '13:00:00', 'SCHEDULED', 240.0),
                                                                                      (2, 3, '2026-05-16', '17:00:00', 'SCHEDULED', 260.0),
                                                                                      (3, 3, '2026-05-17', '20:00:00', 'SCHEDULED', 300.0);