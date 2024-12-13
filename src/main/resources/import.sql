-- GEFP-9-AWS
-- GEFP-18-SA, ändrade upplägget så alla har varsin INSERT INTO rad
INSERT INTO games (game_name) VALUES ('Guitar Hero');
INSERT INTO games (game_name) VALUES ('Mario Kart');
INSERT INTO games (game_name) VALUES ('Tibia');

INSERT INTO games (game_name) VALUES ('Pong');
INSERT INTO games (game_name) VALUES ('Battleship');
INSERT INTO games (game_name) VALUES ('Counter-Strike');
INSERT INTO games (game_name) VALUES ('Path of Exile');

-- GEFP-26-SA
INSERT INTO games (game_name) VALUES ('Marvel Rivals');
INSERT INTO games (game_name) VALUES ('Overwatch');
INSERT INTO games (game_name) VALUES ('Valorant');

-- GEFP-9-AWS
--GEFP-26-SA, lägger in game_id
--Guitar Hero
INSERT INTO teams (team_name,game_id) VALUES ('Ctrl Alt Defeat',1);
INSERT INTO teams (team_name, game_id) VALUES ('Sassy Sasquatches',1);
--MarioKart
INSERT INTO teams (team_name,game_id) VALUES ('Game of Throws',2);
INSERT INTO teams (team_name,game_id) VALUES ('Fast but Last',2);
--Tibia
INSERT INTO teams (team_name,game_id) VALUES ('404: Team Name Not Found',3);
INSERT INTO teams (team_name,game_id) VALUES ('Unicorn Apocalypse',3);

--GEFP-26-SA
--Pong
INSERT INTO teams(team_name,game_id)VALUES ('Pixel Paddlers',4);
INSERT INTO teams(team_name,game_id)VALUES ('Bounce Battalion',4);

    --Battleship
--GEFP-9-AWS
INSERT INTO teams (team_name,game_id) VALUES ('Bug Slayers',5);

-- GEFP-26-SA
INSERT INTO teams (team_name,game_id) VALUES ('Ashes',5);
--CounterStrike
INSERT INTO teams (team_name,game_id) VALUES ('Spirit',6);
INSERT INTO teams (team_name,game_id) VALUES ('MOUZ',6);
--Path of Exile
INSERT INTO teams (team_name,game_id) VALUES ('Dragon hunters',7);
INSERT INTO teams (team_name,game_id) VALUES ('Salem witches',7);
--Marvel Rivals
INSERT INTO teams (team_name,game_id) VALUES ('Asgardians',8);
INSERT INTO teams (team_name,game_id) VALUES ('Spiders squad',8);
--Overwatch
INSERT INTO teams (team_name,game_id) VALUES ('Demon killers',9);
INSERT INTO teams (team_name,game_id) VALUES ('Doom-slayers',9);
--Valorant
INSERT INTO teams (team_name,game_id) VALUES ('FNATIC',10);
INSERT INTO teams (team_name,game_id) VALUES ('Heretics',10);



-- GEFP-18-SA, ändrade stavfelet på street_address
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Alma', 'Johansson', 'AmazingAlma', 'Blomvägen 10', '54321', 'Linköping', 'Sweden', 'alma.johansson@example.se', 1,1);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Erik', 'Nilsson', 'EagleErik', 'Kyrkogatan 22', '13579', 'Helsingborg', 'Sweden', 'erik.nilsson@example.se', 1, 2);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Saga', 'Karlsson', 'SwiftSaga', 'Vallgatan 14', '24680', 'Örebro', 'Sweden', 'saga.karlsson@example.se', 2, 3);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Johan', 'Svensson', 'JollyJohan', 'Åkervägen 7', '90210', 'Gävle', 'Sweden', 'johan.svensson@example.se', 2, 3);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Maria', 'Persson', 'MightyMaria', 'Kaptensgatan 4', '33133', 'Kalmar', 'Sweden', 'maria.persson@example.se', 2, 4);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Oskar', 'Lindgren', 'LuckyOskar', 'Torggatan 9', '55112', 'Umeå', 'Sweden', 'oskar.lindgren@example.se', 2, 4);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Emma', 'Larsson', 'EliteEmma', 'Kullerstensvägen 6', '44123', 'Sundsvall', 'Sweden', 'emma.larsson@example.se', 3, 5);

-- GEFP-26-SA
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Liam', 'Andersson', 'LiamMaster', 'Höga Gatan 12', '12345', 'Göteborg', 'Sweden', 'liam.andersson@example.se', 3, 6);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Emma', 'Johansson', 'EmmaPongQueen', 'Blommavägen 8', '67890', 'Stockholm', 'Sweden', 'emma.johansson@example.se' , 4, 7);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Maja', 'Karlsson', 'SuperMaja', 'Storgatan 45', '35213', 'Växjö', 'Sweden', 'maja.karlsson@example.se', 4, 8);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Liam', 'Svensson', 'LiamTheLion', 'Solgatan 22', '45678', 'Malmö', 'Sweden', 'liam.svensson@example.se', 5, 9);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Julia', 'Nordin', 'JuJu', 'Ekgatan 11', '23456', 'Gävle', 'Sweden', 'julia.nordin@example.se', 5, 10);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Max', 'Bergström', 'MaxPower', 'Björkgatan 14', '74321', 'Uppsala', 'Sweden', 'max.bergstrom@example.se', 6, 11);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Freja', 'Nilsson', 'FrejTheBest', 'Hälsingegatan 33', '64532', 'Helsingborg', 'Sweden', 'freja.nilsson@example.se', 6, 11);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Theo', 'Olsson', 'TheoMaster', 'Lövvägen 7', '17890', 'Göteborg', 'Sweden', 'theo.olsson@example.se', 6, 11);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Alice', 'Eriksson', 'CharmingAlice', 'Fjällgatan 26', '56123', 'Östersund', 'Sweden', 'alice.eriksson@example.se', 6, 12);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Oscar', 'Johansson', 'Oskie', 'Hamnvägen 9', '16456', 'Västerås', 'Sweden', 'oscar.johansson@example.se', 6, 12);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Elin', 'Persson', 'ElinIncredible', 'Husvägen 28', '56789', 'Jönköping', 'Sweden', 'elin.persson@example.se', 6, 12);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Lucas', 'Andersson', 'LuckyLucas', 'Långgatan 44', '99821', 'Norrköping', 'Sweden', 'lucas.andersson@example.se', 7, 13);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Sofia', 'Wikström', 'SofieStar', 'Vallgatan 55', '21034', 'Lund', 'Sweden', 'sofia.wikstrom@example.se', 7, 13);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Hugo', 'Johansson', 'HugoHero', 'Ängsgatan 13', '19876', 'Borås', 'Sweden', 'hugo.johansson@example.se', 7, 14);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Astrid', 'Larsson', 'AstridMagic', 'Östergatan 17', '78965', 'Karlstad', 'Sweden', 'astrid.larsson@example.se', 7, 14);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Noah', 'Hallberg', 'NoahKing', 'Mossvägen 5', '43789', 'Kristianstad', 'Sweden', 'noah.hallberg@example.se', 8, 15);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Vera', 'Mårtensson', 'VeraSparkle', 'Vallgatan 66', '54923', 'Skellefteå', 'Sweden', 'vera.martensson@example.se', 8, 15);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Ivar', 'Lindqvist', 'IvarKnight', 'Klimvägen 18', '98321', 'Falun', 'Sweden', 'ivar.lindqvist@example.se', 8, 15);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Ella', 'Gustafsson', 'EllaPower', 'Torggatan 8', '21456', 'Sundsvall', 'Sweden', 'ella.gustafsson@example.se', 8, 16);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Sebastian', 'Håkansson', 'SebStar', 'Grangatan 29', '32014', 'Umeå', 'Sweden', 'sebastian.hakansson@example.se', 8, 16);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Ella', 'Lundqvist', 'EllaJoy', 'Västervägen 12', '76543', 'Trollhättan', 'Sweden', 'ella.lundqvist@example.se', 8, 16);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Maxim', 'Persson', 'Maximus', 'Torggatan 31', '45987', 'Uppsala', 'Sweden', 'maxim.persson@example.se', 9, 17);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Signe', 'Åberg', 'SigneSunshine', 'Gågatan 19', '63901', 'Luleå', 'Sweden', 'signe.aberg@example.se', 9, 17);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Olof', 'Jonsson', 'OlofPower', 'Smedgatan 5', '91234', 'Halmstad', 'Sweden', 'olof.jonsson@example.se', 9, 17);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Lova', 'Hansson', 'LovaLicious', 'Havsvägen 2', '65892', 'Östersund', 'Sweden', 'lova.hansson@example.se', 9, 18);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Alfred', 'Nyström', 'AlfieChampion', 'Blomstervägen 16', '84765', 'Nyköping', 'Sweden', 'alfred.nystrom@example.se', 9, 18);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Freja', 'Möller', 'FrejaDream', 'Västra Vägen 23', '54312', 'Karlskrona', 'Sweden', 'freja.moller@example.se', 9, 18);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Nils', 'Johansson', 'NilsPlay', 'Södra Gatan 12', '34321', 'Malmö', 'Sweden', 'nils.johansson@example.se', 10, 19);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Emilia', 'Lindqvist', 'EmiliaVibes', 'Kungsgatan 45', '21145', 'Stockholm', 'Sweden', 'emilia.lindqvist@example.se', 10, 19);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Axel', 'Andersson', 'AxelForce', 'Lilla Torg 8', '22113', 'Lund', 'Sweden', 'axel.andersson@example.se', 10, 19);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Isabelle', 'Svensson', 'IsabelleStorm', 'Högalidsgatan 19', '11862', 'Göteborg', 'Sweden', 'isabelle.svensson@example.se', 10, 20);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Victor', 'Eriksson', 'VictorQuest', 'Storgatan 101', '75112', 'Uppsala', 'Sweden', 'victor.eriksson@example.se', 10, 20);
INSERT INTO players (first_name, last_name, nickname,street_address,zip_code,city,country,email,game_id,team_id) VALUES ('Olivia', 'Karlsson', 'OliviaPower', 'Västerlånggatan 56', '11223', 'Västerås', 'Sweden', 'olivia.karlsson@example.se', 10, 20);


INSERT INTO matches (match_name, date, game_id) VALUES ('Ctrl Alt Defeat vs Sassy Sasquatches', '2024-12-01', 1);
INSERT INTO matches (match_name, date, game_id) VALUES ('Game of Throws vs Fast but Last', '2024-12-02', 2);
INSERT INTO matches (match_name, date, game_id) VALUES ('404: Team Name Not Found vs Unicorn Apocalypse', '2024-12-03', 3);

--GEFP-26-SA
--Fixa datum
INSERT INTO matches (match_name, date, game_id) VALUES ('Pixel Paddlers vs Bounce Battalion', '2024-12-04', 4);
INSERT INTO matches (match_name, date, game_id) VALUES ('Bug Slayers vs Ashes', '2024-12-05', 5);
INSERT INTO matches (match_name, date, game_id) VALUES ('Spirit vs MOUZ', '2024-12-06', 6);
INSERT INTO matches (match_name, date, game_id) VALUES ('Dragon hunters vs Salem witches', '2024-12-07', 7);
INSERT INTO matches (match_name, date, game_id) VALUES ('Asgardians vs Spiders squad', '2024-12-08', 8);
INSERT INTO matches (match_name, date, game_id) VALUES ('Demon killers vs Doom-slayers', '2024-12-09', 9);
INSERT INTO matches (match_name, date, game_id) VALUES ('FNATIC vs Heretics', '2024-12-10', 10);

--GEFP-26-SA
INSERT INTO teams_matches(Team_team_id, matchesInTeam_match_id) VALUES (1,1);
INSERT INTO teams_matches(Team_team_id, matchesInTeam_match_id) VALUES (2,1);
INSERT INTO teams_matches(Team_team_id, matchesInTeam_match_id) VALUES (3,2);
INSERT INTO teams_matches(Team_team_id, matchesInTeam_match_id) VALUES (4,2);
INSERT INTO teams_matches(Team_team_id, matchesInTeam_match_id) VALUES (5,3);
INSERT INTO teams_matches(Team_team_id, matchesInTeam_match_id) VALUES (6,3);
INSERT INTO teams_matches(Team_team_id, matchesInTeam_match_id) VALUES (7,4);
INSERT INTO teams_matches(Team_team_id, matchesInTeam_match_id) VALUES (8,4);
INSERT INTO teams_matches(Team_team_id, matchesInTeam_match_id) VALUES (9,5);
INSERT INTO teams_matches(Team_team_id, matchesInTeam_match_id) VALUES (10,5);
INSERT INTO teams_matches(Team_team_id, matchesInTeam_match_id) VALUES (11,6);
INSERT INTO teams_matches(Team_team_id, matchesInTeam_match_id) VALUES (12,6);
INSERT INTO teams_matches(Team_team_id, matchesInTeam_match_id) VALUES (13,7);
INSERT INTO teams_matches(Team_team_id, matchesInTeam_match_id) VALUES (14,7);
INSERT INTO teams_matches(Team_team_id, matchesInTeam_match_id) VALUES (15,8);
INSERT INTO teams_matches(Team_team_id, matchesInTeam_match_id) VALUES (16,8);
INSERT INTO teams_matches(Team_team_id, matchesInTeam_match_id) VALUES (17,9);
INSERT INTO teams_matches(Team_team_id, matchesInTeam_match_id) VALUES (18,9);
INSERT INTO teams_matches(Team_team_id, matchesInTeam_match_id) VALUES (19,10);
INSERT INTO teams_matches(Team_team_id, matchesInTeam_match_id) VALUES (20,10);

--GEFP-26-SA, lägga in hur?
INSERT INTO matches_teams(Match_match_id,teams_ORDER,teams_team_id) VALUES (1,1,1);

