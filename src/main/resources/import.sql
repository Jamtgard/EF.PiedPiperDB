-- GEFP-9-AWS
-- GEFP-18-SA, ändrade upplägget så alla har varsin INSERT INTO rad
INSERT INTO games (game_name) VALUES ('Guitar Hero');
INSERT INTO games (game_name) VALUES ('Mario Kart');
INSERT INTO games (game_name) VALUES ('Tibia');
INSERT INTO games (game_name) VALUES ('Pong');
INSERT INTO games (game_name) VALUES ('Battleship');
INSERT INTO games (game_name) VALUES ('Counter-Strike');
INSERT INTO games (game_name) VALUES ('Path of Exile');

INSERT INTO teams (team_name) VALUES ('Ctrl Alt Defeat');
INSERT INTO teams (team_name) VALUES ('Sassy Sasquatches');
INSERT INTO teams (team_name) VALUES ('Game of Throws');
INSERT INTO teams (team_name) VALUES ('Fast but Last');
INSERT INTO teams (team_name) VALUES ('404: Team Name Not Found');
INSERT INTO teams (team_name) VALUES ('Unicorn Apocalypse');
INSERT INTO teams (team_name) VALUES ('Bug Slayers');

-- GEFP-18-SA, ändrade stavfelet på street_address
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Alma', 'Johansson', 'AmazingAlma', 'Blomvägen 10', '54321', 'Linköping', 'Sweden', 'alma.johansson@example.se', 1, 1);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Erik', 'Nilsson', 'EagleErik', 'Kyrkogatan 22', '13579', 'Helsingborg', 'Sweden', 'erik.nilsson@example.se', 1, 2);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Saga', 'Karlsson', 'SwiftSaga', 'Vallgatan 14', '24680', 'Örebro', 'Sweden', 'saga.karlsson@example.se', 2, 3);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Johan', 'Svensson', 'JollyJohan', 'Åkervägen 7', '90210', 'Gävle', 'Sweden', 'johan.svensson@example.se', 2, 3);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Maria', 'Persson', 'MightyMaria', 'Kaptensgatan 4', '33133', 'Kalmar', 'Sweden', 'maria.persson@example.se', 3, 4);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Oskar', 'Lindgren', 'LuckyOskar', 'Torggatan 9', '55112', 'Umeå', 'Sweden', 'oskar.lindgren@example.se', 3, 4);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Emma', 'Larsson', 'EliteEmma', 'Kullerstensvägen 6', '44123', 'Sundsvall', 'Sweden', 'emma.larsson@example.se', 4, 5);

INSERT INTO matches (match_name, date, game_id,player_id) VALUES ('Ctrl Alt Defeat vs Sassy Sasquatches', '2024-12-01', 1,1);-- GEFP-22-SA, satte in en player_id här
INSERT INTO matches (match_name, date, game_id) VALUES ('Game of Throws vs Fast but Last', '2024-12-02', 2);
INSERT INTO matches (match_name, date, game_id) VALUES ('404: Team Name Not Found vs Unicorn Apocalypse', '2024-12-03', 3);
