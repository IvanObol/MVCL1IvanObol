-- ===== KINA =====
INSERT INTO cinema (id, name) VALUES (1, 'Kinoza Arkadia');
INSERT INTO cinema (id, name) VALUES (2, 'Kinoza Mokotów');
INSERT INTO cinema (id, name) VALUES (3, 'Kinoza Centrum');
INSERT INTO cinema (id, name) VALUES (4, 'Kinoza Wawer');
INSERT INTO cinema (id, name) VALUES (5, 'Kinoza Praga');

-- ===== FILMY: Teraz w kinie =====
INSERT INTO movie (id, title, director, rating, genre, poster_url, description, duration, age_limit, country, release_year, language, format, status)
VALUES (1, 'Diuna: Część druga', 'Denis Villeneuve', 8.9, 'Sci-Fi', '/images/dune.jpg',
        'Paul Atryda łączy siły z Fremenami, aby pomścić swoją rodzinę. Stojąc przed wyborem między miłością a losem wszechświata, próbuje zapobiec straszliwej przyszłości, którą tylko on może przewidzieć.',
        166, '12+', 'USA', 2024, 'PL', 'IMAX', 'NOW_PLAYING');

INSERT INTO movie (id, title, director, rating, genre, poster_url, description, duration, age_limit, country, release_year, language, format, status)
VALUES (2, 'Siedem', 'David Fincher', 8.8, 'Thriller', '/images/seven.jpg',
        'Dwaj policjanci polują na seryjnego mordercę, który wybiera ofiary według siedmiu grzechów głównych.',
        127, '16+', 'USA', 1995, 'PL', '2D', 'NOW_PLAYING');

INSERT INTO movie (id, title, director, rating, genre, poster_url, description, duration, age_limit, country, release_year, language, format, status)
VALUES (3, 'Kill Bill', 'Quentin Tarantino', 8.2, 'Akcja', '/images/kill-bill.jpg',
        'Była zabójczyni budzi się ze śpiączki i rusza w krwawą podróż zemsty.',
        111, '18+', 'USA', 2003, 'PL', '2D', 'NOW_PLAYING');

INSERT INTO movie (id, title, director, rating, genre, poster_url, description, duration, age_limit, country, release_year, language, format, status)
VALUES (4, 'Maryja. Matka papieża', 'Anna Kowalska', 7.5, 'Dokument', '/images/matka.jpg',
        '- Poruszająca opowieść o duchowej sile, macierzyństwie i historii kobiety, której życie zostało nierozerwalnie związane z jedną z najważniejszych postaci XX wieku.',
        82, '12+', 'Polska', 2025, 'PL', '2D', 'NOW_PLAYING');

-- ===== FILMY: Premiery i zapowiedzi =====
INSERT INTO movie (id, title, director, rating, genre, poster_url, description, duration, age_limit, country, release_year, language, format, status)
VALUES (5, 'Toy Story', 'John Lasseter', 8.3, 'Animacja', '/images/toj-story.jpg',
        'Kiedy chłopiec wychodzi z pokoju, jego zabawki ożywają i przeżywają niezwykłe przygody.',
        81, '0+', 'USA', 1995, 'PL', '2D', 'COMING_SOON');

INSERT INTO movie (id, title, director, rating, genre, poster_url, description, duration, age_limit, country, release_year, language, format, status)
VALUES (6, 'Chronologia wody', 'Kristen Stewart', 4.7, 'Dramat', '/images/chronologja.jpg',
        'Adaptacja głośnej książki o sile pisania, traumie i odnajdywaniu siebie.',
        130, '16+', 'USA', 2026, 'PL', '2D', 'COMING_SOON');