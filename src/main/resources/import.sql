-- GEFP-9-AWS
-- GEFP-18-SA, ändrade upplägget så alla har varsin INSERT INTO rad
INSERT INTO games (game_name) VALUES ('Guitar Hero');--1
INSERT INTO games (game_name) VALUES ('Mario Kart');--2
INSERT INTO games (game_name) VALUES ('Tibia');--3
INSERT INTO games (game_name) VALUES ('Pong');--4
INSERT INTO games (game_name) VALUES ('Battleship');--5
INSERT INTO games (game_name) VALUES ('Counter-Strike');--6
INSERT INTO games (game_name) VALUES ('Path of Exile');--7

-- GEFP-26-SA
INSERT INTO games (game_name) VALUES ('Marvel Rivals');--8
INSERT INTO games (game_name) VALUES ('Overwatch');--9
INSERT INTO games (game_name) VALUES ('Valorant');--10

-- GEFP-9-AWS
--GEFP-26-SA, lägger in game_id
INSERT INTO teams (team_name,game_id) VALUES ('Ctrl Alt Defeat',1);--1
INSERT INTO teams (team_name, game_id) VALUES ('Sassy Sasquatches',1);--2
INSERT INTO teams (team_name,game_id) VALUES ('Game of Throws',2);--3
INSERT INTO teams (team_name,game_id) VALUES ('Fast but Last',3);--4
INSERT INTO teams (team_name,game_id) VALUES ('404: Team Name Not Found',4);--5
INSERT INTO teams (team_name,game_id) VALUES ('Unicorn Apocalypse',4);--6
INSERT INTO teams (team_name,game_id) VALUES ('Bug Slayers',5);--7

-- GEFP-26-SA
INSERT INTO teams (team_name,game_id) VALUES ('Demon killers',5);--8
INSERT INTO teams (team_name,game_id) VALUES ('Spirit',6);--9
INSERT INTO teams (team_name,game_id) VALUES ('MOUZ',6);--10
INSERT INTO teams (team_name,game_id) VALUES ('Dragon hunters',7);--11
INSERT INTO teams (team_name,game_id) VALUES ('Salem witches',7);--12
INSERT INTO teams (team_name,game_id) VALUES ('Asgardians',8);--13
INSERT INTO teams (team_name,game_id) VALUES ('Spiders rule',8);--14
INSERT INTO teams (team_name,game_id) VALUES ('FNATIC',9);--15
INSERT INTO teams (team_name,game_id) VALUES ('Heretics',9);--16

-- GEFP-18-SA, ändrade stavfelet på street_address
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Alma', 'Johansson', 'AmazingAlma', 'Blomvägen 10', '54321', 'Linköping', 'Sweden', 'alma.johansson@example.se', 1, 1);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Erik', 'Nilsson', 'EagleErik', 'Kyrkogatan 22', '13579', 'Helsingborg', 'Sweden', 'erik.nilsson@example.se', 1, 2);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Saga', 'Karlsson', 'SwiftSaga', 'Vallgatan 14', '24680', 'Örebro', 'Sweden', 'saga.karlsson@example.se', 2, 3);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Johan', 'Svensson', 'JollyJohan', 'Åkervägen 7', '90210', 'Gävle', 'Sweden', 'johan.svensson@example.se', 2, 3);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Maria', 'Persson', 'MightyMaria', 'Kaptensgatan 4', '33133', 'Kalmar', 'Sweden', 'maria.persson@example.se', 3, 4);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Oskar', 'Lindgren', 'LuckyOskar', 'Torggatan 9', '55112', 'Umeå', 'Sweden', 'oskar.lindgren@example.se', 3, 4);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Emma', 'Larsson', 'EliteEmma', 'Kullerstensvägen 6', '44123', 'Sundsvall', 'Sweden', 'emma.larsson@example.se', 4, 5);

-- GEFP-26-SA
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Maja', 'Karlsson', 'SuperMaja', 'Storgatan 45', '35213', 'Växjö', 'Sweden', 'maja.karlsson@example.se', 4, 6);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Liam', 'Svensson', 'LiamTheLion', 'Solgatan 22', '45678', 'Malmö', 'Sweden', 'liam.svensson@example.se', 5, 7);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Julia', 'Nordin', 'JuJu', 'Ekgatan 11', '23456', 'Gävle', 'Sweden', 'julia.nordin@example.se', 5, 8);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Max', 'Bergström', 'MaxPower', 'Björkgatan 14', '74321', 'Uppsala', 'Sweden', 'max.bergstrom@example.se', 6, 9);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Freja', 'Nilsson', 'FrejTheBest', 'Hälsingegatan 33', '64532', 'Helsingborg', 'Sweden', 'freja.nilsson@example.se', 6, 9);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Theo', 'Olsson', 'TheoMaster', 'Lövvägen 7', '17890', 'Göteborg', 'Sweden', 'theo.olsson@example.se', 6, 9);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Alice', 'Eriksson', 'CharmingAlice', 'Fjällgatan 26', '56123', 'Östersund', 'Sweden', 'alice.eriksson@example.se', 6, 10);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Oscar', 'Johansson', 'Oskie', 'Hamnvägen 9', '16456', 'Västerås', 'Sweden', 'oscar.johansson@example.se', 6, 10);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Elin', 'Persson', 'ElinIncredible', 'Husvägen 28', '56789', 'Jönköping', 'Sweden', 'elin.persson@example.se' 6, 10);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Lucas', 'Andersson', 'LuckyLucas', 'Långgatan 44', '99821', 'Norrköping', 'Sweden', 'lucas.andersson@example.se', 7, 11);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Sofia', 'Wikström', 'SofieStar', 'Vallgatan 55', '21034', 'Lund', 'Sweden', 'sofia.wikstrom@example.se', 7, 11);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Hugo', 'Johansson', 'HugoHero', 'Ängsgatan 13', '19876', 'Borås', 'Sweden', 'hugo.johansson@example.se', 7, 12);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Astrid', 'Larsson', 'AstridMagic', 'Östergatan 17', '78965', 'Karlstad', 'Sweden', 'astrid.larsson@example.se', 7, 12);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Noah', 'Hallberg', 'NoahKing', 'Mossvägen 5', '43789', 'Kristianstad', 'Sweden', 'noah.hallberg@example.se', 8, 13);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Vera', 'Mårtensson', 'VeraSparkle', 'Vallgatan 66', '54923', 'Skellefteå', 'Sweden', 'vera.martensson@example.se', 8, 13);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Ivar', 'Lindqvist', 'IvarKnight', 'Klimvägen 18', '98321', 'Falun', 'Sweden', 'ivar.lindqvist@example.se', 8, 13);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Ella', 'Gustafsson', 'EllaPower', 'Torggatan 8', '21456', 'Sundsvall', 'Sweden', 'ella.gustafsson@example.se', 9, 14);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Sebastian', 'Håkansson', 'SebStar', 'Grangatan 29', '32014', 'Umeå', 'Sweden', 'sebastian.hakansson@example.se', 9, 14);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Ella', 'Lundqvist', 'EllaJoy', 'Västervägen 12', '76543', 'Trollhättan', 'Sweden', 'ella.lundqvist@example.se', 9, 14);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Maxim', 'Persson', 'Maximus', 'Torggatan 31', '45987', 'Uppsala', 'Sweden', 'maxim.persson@example.se', 10, 15);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Signe', 'Åberg', 'SigneSunshine', 'Gågatan 19', '63901', 'Luleå', 'Sweden', 'signe.aberg@example.se', 10, 15);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Olof', 'Jonsson', 'OlofPower', 'Smedgatan 5', '91234', 'Halmstad', 'Sweden', 'olof.jonsson@example.se', 10, 15);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Lova', 'Hansson', 'LovaLicious', 'Havsvägen 2', '65892', 'Östersund', 'Sweden', 'lova.hansson@example.se', 10, 16);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Alfred', 'Nyström', 'AlfieChampion', 'Blomstervägen 16', '84765', 'Nyköping', 'Sweden', 'alfred.nystrom@example.se', 10, 16);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Freja', 'Möller', 'FrejaDream', 'Västra Vägen 23', '54312', 'Karlskrona', 'Sweden', 'freja.moller@example.se', 10, 16);

INSERT INTO matches (match_name, date, game_id,player_id) VALUES ('Ctrl Alt Defeat vs Sassy Sasquatches', '2024-12-01', 1,1);-- GEFP-22-SA, satte in en player_id här
INSERT INTO matches (match_name, date, game_id,team_id) VALUES ('Game of Throws vs Fast but Last', '2024-12-02', 2);
INSERT INTO matches (match_name, date, game_id) VALUES ('404: Team Name Not Found vs Unicorn Apocalypse', '2024-12-03', 3);
