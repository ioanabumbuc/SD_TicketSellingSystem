INSERT INTO Bands (id, band_name, genre)
VALUES
(1, 'The neighbourhood', 'Alternative'),
(2, 'Mild Orange', 'Indie'),
(3, 'Cage The Elephant', 'Alternative Rock'),
(4, 'Hozier', 'Soul'),
(5, 'Mitski', 'Indie Rock'),
(6, 'Ariana Grande', 'Pop'),
(7, 'Doja Cat', 'Rap'),
(8, 'Billie Eilish', 'Pop'),
(9, 'Imagine Dragons', 'Rock');

INSERT INTO Concert (id, title, available_tickets, concert_date)
VALUES
(1, 'UNTOLD', 100, '2023-04-01'),
(2, 'Neversea', 50, '2023-05-15'),
(3, 'Electric Castle', 200, '2023-06-20'),
(4, 'Summerwell', 75, '2023-07-10');

INSERT INTO Ticket (id, ticket_code, concert_id,buyer_name )
VALUES
(1, 'cb483e0e-c66c-11ed-afa1-0242ac120002', 1, 'ioio'),
(2, 'cb484084-c66c-11ed-afa1-0242ac120002', 1, 'cupsa'),
(3, 'd2959206-c66c-11ed-afa1-0242ac120002', 2, 'cris'),
(4, 'd5dea808-c66c-11ed-afa1-0242ac120002', 3, 'alex');

INSERT INTO concert_band (concert_id, band_id)
VALUES
(1, 8),
(1, 9),
(1, 1),
(1, 5),

(2, 5),
(2, 6),
(2, 7),

(3, 1),
(3, 2),
(3, 3),
(3, 8),
(3, 9),

(4, 4),
(4, 5),
(4, 7),
(4, 9);


