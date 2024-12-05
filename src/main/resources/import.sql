-- GEFP-9-AWS
INSERT INTO games (game_name)
VALUES
('Guitar Hero'),
('Mario Kart'),
('Tibia'),
('Pong'),
('Battleship'),
('Counter-Strike'),
('Path of Exile');

INSERT INTO teams (team_name)
VALUES
('Ctrl Alt Defeat'),
('Sassy Sasquatches'),
('Game of Throws'),
('Fast but Last'),
('404: Team Name Not Found'),
('Unicorn Apocalypse'),
('Bug Slayers');

INSERT INTO players (first_name, last_name, nickname,street_adress,zip_code,city,country,email,game_id,team_id)
VALUES
('Alma', 'Johansson', 'AmazingAlma', 'Blomvägen 10', '54321', 'Linköping', 'Sweden', 'alma.johansson@example.se', 1, 1),
('Erik', 'Nilsson', 'EagleErik', 'Kyrkogatan 22', '13579', 'Helsingborg', 'Sweden', 'erik.nilsson@example.se', 1, 2),
('Saga', 'Karlsson', 'SwiftSaga', 'Vallgatan 14', '24680', 'Örebro', 'Sweden', 'saga.karlsson@example.se', 2, 3),
('Johan', 'Svensson', 'JollyJohan', 'Åkervägen 7', '90210', 'Gävle', 'Sweden', 'johan.svensson@example.se', 2, 3),
('Maria', 'Persson', 'MightyMaria', 'Kaptensgatan 4', '33133', 'Kalmar', 'Sweden', 'maria.persson@example.se', 3, 4),
('Oskar', 'Lindgren', 'LuckyOskar', 'Torggatan 9', '55112', 'Umeå', 'Sweden', 'oskar.lindgren@example.se', 3, 4),
('Emma', 'Larsson', 'EliteEmma', 'Kullerstensvägen 6', '44123', 'Sundsvall', 'Sweden', 'emma.larsson@example.se', 4, 5);

INSERT INTO matches (match_name, date, game_id)
VALUES
('Ctrl Alt Defeat vs Sassy Sasquatches', '2024-12-01', 1),
('Game of Throws vs Fast but Last', '2024-12-02', 2),
('404: Team Name Not Found vs Unicorn Apocalypse', '2024-12-03', 3);