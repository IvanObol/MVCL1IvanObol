INSERT INTO movie (title, director, rating, genre, poster_url, description) VALUES ('Diuna', 'Denis Villeneuve', 9.0, 'Sci-Fi', '/images/dune.jpg', 'Szlachetny ród Atrydów przybywa na niebezpieczną planetę Arrakis...');

INSERT INTO movie (title, director, rating, genre, poster_url, description) VALUES ('Siedem', 'David Fincher', 8.8, 'Thriller', '/images/seven.jpg', 'Dwaj policjanci polują na mordercę, który wybiera ofiary według siedmiu grzechów głównych.');

INSERT INTO movie (title, director, rating, genre, poster_url, description) VALUES ('Toy Story', 'John Lasseter', 8.3, 'Animacja', '/images/toj-story.jpg', 'Kiedy chłopiec wychodzi z pokoju, jego zabawki ożywają...');

INSERT INTO movie (title, director, rating, genre, poster_url, description) VALUES ('Kill Bill', 'Quentin Tarantino', 8.2, 'Thriller', '/images/kill-bill.jpg', 'Ciężarna płatna zabójczyni o pseudonimie Czarna Mamba zostaje postrzelona przez mężczyznę o imieniu Bill podczas swojego ślubu. Kula w głowie ofiary, krew na jej sukni ślubnej, ciemność... Ale głowa Czarnej Mamby okazała się twarda');

INSERT INTO seat (row_num, seat_num, is_reserved, movie_id) VALUES (1, 1, false, 1);

INSERT INTO seat (row_num, seat_num, is_reserved, movie_id) VALUES (1, 2, false, 1);

INSERT INTO seat (row_num, seat_num, is_reserved, movie_id) VALUES (1, 3, true, 1);
