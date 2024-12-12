package com.example.piedpiperdb.View;

import com.example.piedpiperdb.DAO.GameDAO;
import com.example.piedpiperdb.DAO.MatchDAO;
import com.example.piedpiperdb.DAO.PlayerDAO;
import com.example.piedpiperdb.DAO.TeamDAO;
import com.example.piedpiperdb.Entities.Game;
import com.example.piedpiperdb.Entities.Match;
import com.example.piedpiperdb.Entities.Player;
import com.example.piedpiperdb.Entities.Team;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


//GEFP-19-AA
public class PlayerView extends AbstractScene{

    private static VBox getIdBox;
    private static VBox vBox;

    private static PlayerDAO playerDAO = new PlayerDAO();
    private static GameDAO gameDAO = new GameDAO();
    private static TeamDAO teamDAO = new TeamDAO();
    private static MatchDAO matchDAO = new MatchDAO();

    private static TextField firstNameField;
    private static TextField lastNameField;
    private static TextField nicknameField;
    private static TextField streetAddressField;
    private static TextField zipCodeField;
    private static TextField cityField;
    private static TextField countryField;
    private static TextField emailField;
    private static ComboBox<String> gameField;
    private static ComboBox<String> teamField;
    private static ComboBox<String> matchField;


    public static Scene playerScene(Stage window){
        Scene baseScene = AbstractScene.getScene(window); // Skapar en sen från mallen i abstract Scene

        //AnchorPane anchorPane = AbstractScene.anchorPane;
        VBox vBox = AbstractScene.leftVbox;



        HelloApplication helloApp = new HelloApplication();
        AbstractScene.back.setOnAction(e->{
            try {
                helloApp.start(window);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        //addCustomComponents(anchorPane);
        addCustomComponents(vBox);



       //AnchorPane rootPane = (AnchorPane) baseScene.getRoot();

        return baseScene;
    }

    private static void addCustomComponents(VBox vBox){
            List<Game> games = gameDAO.getAllGames();
            List<CheckBox> checkBoxes = new ArrayList<>();


            for (Game game : games) {
                String name = game.getGameName();
                int gameId = game.getGameId();
                System.out.println(gameId);
                CheckBox checkBox  = new CheckBox(name + ", GameID: " + gameId );
                checkBoxes.add(checkBox);
            }

            Button getAllPlayers = new Button("Show all players");
            getAllPlayers.getStyleClass().add("standardButton");
            getAllPlayers.setMinSize(160, 30);
            getAllPlayers.setOnAction(event -> {
                List<Player> players = playerDAO.getAllPlayers();
                System.out.println(players.size());
                showTable(anchorPane, players );
            });


            Button selectedPlayers = new Button("Show players from \nselected game or games");
            selectedPlayers.getStyleClass().add("standardButton");
            selectedPlayers.setMinSize(160, 50);
            selectedPlayers.setOnAction(actionEvent -> {
                clearResaultBox(getIdBox, PlayerView.vBox);
                List<String> selections = ConfirmBox.displayCheckBoxOptions("Select game or games", checkBoxes);
                List<Integer> ids = new ArrayList<>();
                for (String selection : selections) {
                    try {
                        String[] parts = selection.split(" ");

                        String lastPart = parts[parts.length - 1];
                        int id = Integer.parseInt(lastPart);
                        ids.add(id);
                    } catch (NumberFormatException e) {
                        System.out.println(e.getMessage());
                        System.out.println("Could not parse id: " + selection);
                    }
                    System.out.println("IDs to query: " + ids);
                }
                List<Player> players = playerDAO.getAllPlayersFromSelectedGame(ids);
                System.out.println(players.size());
                showTable(anchorPane, players );
            });


            Button addNewPlayerButton = new Button("Add new player");
            addNewPlayerButton.getStyleClass().add("standardButton");
            addNewPlayerButton.setMinSize(160, 30);
            addNewPlayerButton.setOnAction(e -> {
                clearResaultBox(getIdBox, PlayerView.vBox);
                showAddPlayerForm(anchorPane);
            });

            Button updatePlayerByIdButton = new Button("Update player by ID");
            updatePlayerByIdButton.getStyleClass().add("standardButton");
            updatePlayerByIdButton.setMinSize(160, 30);
            updatePlayerByIdButton.setOnAction(e -> {
                clearResaultBox(getIdBox, PlayerView.vBox);
                showUpdatePlayerForm(anchorPane);
            });

            Button deletePlayerByIdButton = new Button("Delete player by ID");
            deletePlayerByIdButton.getStyleClass().add("standardButton");
            deletePlayerByIdButton.setMinSize(160, 30);
            deletePlayerByIdButton.setOnAction(e -> {
                clearResaultBox(getIdBox, PlayerView.vBox);
                showDeletePlayerForm(anchorPane);
            });

            vBox.getChildren().addAll(getAllPlayers, selectedPlayers, addNewPlayerButton, updatePlayerByIdButton, deletePlayerByIdButton );


    }

    public static void showTable(AnchorPane anchorPane, List<Player> players){
        vBox = createResultBox();
        vBox.getStyleClass().add("textFieldOne");
        TableView<Player> table = createPlayerTable(players); //Skapa tableView
        table.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(table, Priority.ALWAYS);
        vBox.getChildren().addAll(table);

        anchorPane.getChildren().add(vBox);
    }

    private static TableView<Player> createPlayerTable(List<Player> players){
        ObservableList<Player> observableList = FXCollections.observableList(players);

        TableView<Player> table = new TableView<>();

        TableColumn<Player, Integer> player_id = new TableColumn<>("Player ID");
        player_id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Player, String> nickname = new TableColumn<>("Nickname");
        nickname.setCellValueFactory(new PropertyValueFactory<>("nickname"));

        TableColumn<Player, String> fullName = new TableColumn<>("Name");
        fullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));

        TableColumn<Player, String> address = new TableColumn<>("Address");
        address.setCellValueFactory(new PropertyValueFactory<>("fullAddress"));

        TableColumn<Player, String> country = new TableColumn<>("Country");
        country.setCellValueFactory(new PropertyValueFactory<>("country"));

        TableColumn<Player, String> email = new TableColumn<>("E-mail");
        email.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Player, String> game_name = new TableColumn<>("Game");
        game_name.setCellValueFactory(new PropertyValueFactory<>("gameName"));

        TableColumn<Player, String> team_name = new TableColumn<>("Team");
        team_name.setCellValueFactory(new PropertyValueFactory<>("teamName"));

        TableColumn<Player, String> match = new TableColumn<>("Match");
        match.setCellValueFactory(new PropertyValueFactory<>("matchInfo"));

        table.getColumns().addAll(player_id, nickname, fullName, address, country, email, game_name, team_name, match);
        table.setItems(observableList);
        return table;
    }

    private static void showAddPlayerForm(AnchorPane anchorPane){
        firstNameField = new TextField();
        lastNameField = new TextField();
        nicknameField = new TextField();
        streetAddressField = new TextField();
        zipCodeField = new TextField();
        cityField = new TextField();
        countryField = new TextField();
        emailField = new TextField();

        gameField = new ComboBox<>();
        teamField = new ComboBox<>();
        matchField = new ComboBox<>();

        vBox = createResultBox();

        HBox firstNameBox = createResultBoxContentBox(" First Name* ","First Name", firstNameField, false);
        HBox lastNameBox = createResultBoxContentBox(" Last Name* ","Last Name", lastNameField, false);
        HBox nicknameBox = createResultBoxContentBox(" Nickname* ","Nickname", nicknameField, false);
        HBox addressBox = createResultBoxContentBox(" Street Address ","Street Address", streetAddressField, false);
        HBox zipBox = createResultBoxContentBox(" Zip Code ","Zip Code", zipCodeField, false);
        HBox cityBox = createResultBoxContentBox(" City ","City", cityField, false);
        HBox countryBox = createResultBoxContentBox(" Country ","Country", countryField, false);
        HBox emailBox = createResultBoxContentBox(" E-mail* ", "E-mail", emailField, false);

        List<Game> games = gameDAO.getAllGames();
        HBox gameBox = createResultBoxContentBox(
                " Game ", "Select Game",gameField, games, game -> game.getGameId() + ", " + game.getGameName()
                );


        List<Team> teams = teamDAO.getAllTeams();
        HBox teamBox = createResultBoxContentBox(
                " Team ", "Select Team", teamField, teams, team -> team.getTeamId() + ", " + team.getTeamName()
                );


        List<Match> matches = matchDAO.getAllMatches();
        HBox matchBox = createResultBoxContentBox(
                " Match ", "Select Match", matchField, matches, match -> match.getMatchId() + ", " + match.getMatchName()
                );


        vBox.getChildren().addAll(firstNameBox, lastNameBox, nicknameBox, addressBox,zipBox,cityBox, countryBox, emailBox, gameBox, teamBox, matchBox);

        Button saveButton = new Button("Save Player");
        saveButton.getStyleClass().add("standardButton");
        saveButton.setMinSize(160, 30);
        saveButton.setOnAction(event -> {
            Player player = null;
            try {
                if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || nicknameField.getText().isEmpty() || emailField.getText().isEmpty()) {
                    AlertBox.displayAlertBox("Error", "Please fill in all mandatory fields. Fields marked with *.");
                    return;
                } else if (!playerDAO.isNicknameUnique(nicknameField.getText())) {
                    AlertBox.displayAlertBox("Error", "Nickname is already taken. Try a different one!");
                    return;
                } else if (!playerDAO.isEmailUnique(emailField.getText())) {
                    AlertBox.displayAlertBox("Error", "E-mail is already taken. Try a different one!");
                    return;
                } else {
                    try {
                        player = new Player(firstNameField.getText(), lastNameField.getText(), nicknameField.getText(), streetAddressField.getText(), zipCodeField.getText(), cityField.getText(), countryField.getText(), emailField.getText());

                        // Hämta valda spel, lag och matcher från ComboBox
                        String selectedGameValue = gameField.getValue();
                        String selectedTeamValue = teamField.getValue();
                        String selectedMatchValue = matchField.getValue();


                        if (selectedGameValue != null && !selectedGameValue.isEmpty()) {
                            int gameId = Integer.parseInt(selectedGameValue.split(",")[0].trim());
                            Game selectedGame = gameDAO.getGameById(gameId); // Hämta Game-objektet
                            player.setGameId(selectedGame); // Associera spelet med spelaren
                        }

                        if (selectedTeamValue != null && !selectedGameValue.isEmpty()) {
                            int teamId = Integer.parseInt(selectedTeamValue.split(",")[0].trim());
                            Team selectedTeam = teamDAO.getTeamById(teamId); // Hämta Team-objektet
                            player.setTeamId(selectedTeam); // Associera laget med spelaren
                        }

                        if (selectedMatchValue != null && !selectedGameValue.isEmpty()) {
                            int matchId = Integer.parseInt(selectedMatchValue.split(",")[0].trim());
                            Match selectedMatch = matchDAO.getMatchById(matchId); // Hämta Match-objektet
                            player.setMatchId(selectedMatch); // Associera matchen med spelaren
                        }


                        Label labelSaved = new Label(" Player saved to database! ");
                        labelSaved.getStyleClass().add("standardLabel");
                        vBox.getChildren().add(labelSaved);

                    } catch (Exception e) {
                        e.printStackTrace();


                    }
                }


                PlayerDAO newPlayer = new PlayerDAO();
                newPlayer.savePlayer(player);
                System.out.println(player.toString());
            } catch (Exception e) {
                e.printStackTrace();
                AlertBox.displayAlertBox("Error", "An error occurred while saving new player!");
            }

        });




        vBox.getChildren().add(saveButton);

        anchorPane.getChildren().add(vBox);

    }

    private static void showUpdatePlayerForm(AnchorPane anchorPane){
        System.out.println("update player");

        firstNameField = new TextField();
        lastNameField = new TextField();
        nicknameField = new TextField();
        streetAddressField = new TextField();
        zipCodeField = new TextField();
        cityField = new TextField();
        countryField = new TextField();
        emailField = new TextField();

        gameField = new ComboBox<>();
        teamField = new ComboBox<>();
        matchField = new ComboBox<>();

        getIdBox = createResultBox();
        vBox = createResultBox(230.0);

        TextField playerInfield = new TextField();
        HBox playerIdBox = createResultBoxContentBox("Enter Player ID: ", "Player ID", playerInfield,false);

        Label labelNoPlayerId = new Label(" No player found! Enter a different ID (only numbers allowed). ");
        labelNoPlayerId.getStyleClass().add("standardLabel");

        Button getButton = new Button("Get Player from database");

        getButton.getStyleClass().add("standardButton");
        getButton.setOnAction(event -> {
            try {
                Player playerToUpdate = playerDAO.getPlayer(Integer.parseInt(playerInfield.getText()));
                System.out.println(playerToUpdate.toString());

                if (playerToUpdate == null) {
                    System.out.println(playerInfield.getText());
                    vBox.getChildren().add(labelNoPlayerId);
                    return;
                } else {
                    if (!vBox.getChildren().isEmpty()) {
                        vBox.getChildren().clear();
                    }
                    System.out.println(playerToUpdate.toString());

                    HBox fistNameBox = createResultBoxContentBox(" First Name* ", playerToUpdate.getFirstName(),firstNameField, true );
                    HBox lastNameBox = createResultBoxContentBox(" Last Name* ", playerToUpdate.getLastName(),lastNameField, true );
                    HBox nicknameBox = createResultBoxContentBox(" Nickname* ", playerToUpdate.getNickname(),nicknameField, true );
                    HBox addressBox = createResultBoxContentBox(" Street Address ", playerToUpdate.getStreetAddress(),streetAddressField, true );
                    HBox zipBox = createResultBoxContentBox(" Zip Code ", playerToUpdate.getZipCode(),zipCodeField, true );
                    HBox cityBox = createResultBoxContentBox(" City ", playerToUpdate.getCity(),cityField, true );
                    HBox countryBox = createResultBoxContentBox(" Country ", playerToUpdate.getCountry(),countryField, true );
                    HBox emailBox = createResultBoxContentBox(" E-mail ", playerToUpdate.getEmail(),emailField, true );

                    List<Game> games = gameDAO.getAllGames();
                    Game game = playerToUpdate.getGameId();
                    String gameID = game != null ? String.valueOf(game.getGameId()) : "0";
                    String selectedGameValue = gameID + ", " + playerToUpdate.getGameName();
                    System.out.println(selectedGameValue);
                    HBox gameBox = createResultBoxContentBoxComboBoxUpdate(" Game ", gameField, games, g -> g.getGameId() + ", " + g.getGameName(), selectedGameValue );

                    List<Team> teams = teamDAO.getAllTeams();
                    Team team = playerToUpdate.getTeamId();
                    String teamID = team != null ? String.valueOf(team.getTeamId()) : "0";
                    String selectedTeamValue = teamID + ", " + playerToUpdate.getTeamName();
                    HBox teamBox = createResultBoxContentBoxComboBoxUpdate(" Team ", teamField, teams, t -> t.getTeamId() + ", " + t.getTeamName(), selectedTeamValue );

                    List<Match> matches = matchDAO.getAllMatches();
                    Match match = playerToUpdate.getMatchId();
                    String matchID = match != null ? String.valueOf(match.getMatchId()) : "0";
                    String selectedMatchValue = matchID+ ", " + playerToUpdate.getMatchName();
                    HBox matchBox = createResultBoxContentBoxComboBoxUpdate(" Match ", matchField, matches, t -> t.getMatchId() + ", " + t.getMatchName(), selectedMatchValue );

                    Button updateButton = new Button("Update Player");
                    updateButton.getStyleClass().add("standardButton");
                    updateButton.setOnAction(e -> {

                        try {
                            // Kontrollera om nickname har ändrats och är unikt
                            if (!nicknameField.getText().equals(playerToUpdate.getNickname())
                                    && !playerDAO.isNicknameUnique(nicknameField.getText())) {
                                AlertBox.displayAlertBox("Error", "Nickname is already taken. Try a different one!");
                                return;
                            }

                            // Kontrollera om email har ändrats och är unikt
                            if (!emailField.getText().equals(playerToUpdate.getEmail())
                                    && !playerDAO.isEmailUnique(emailField.getText())) {
                                AlertBox.displayAlertBox("Error", "Email is already taken. Try a different one!");
                                return;
                            }

                            playerToUpdate.setFirstName(firstNameField.getText());
                            playerToUpdate.setLastName(lastNameField.getText());
                            playerToUpdate.setNickname(nicknameField.getText());
                            playerToUpdate.setStreetAddress(streetAddressField.getText());
                            playerToUpdate.setZipCode(zipCodeField.getText());
                            playerToUpdate.setCity(cityField.getText());
                            playerToUpdate.setCountry(countryField.getText());
                            playerToUpdate.setEmail(emailField.getText());

                            if (gameField.getValue() != null && !selectedGameValue.isEmpty()) {
                                String selectedGame = gameField.getValue();
                                int gameId = Integer.parseInt(selectedGame.split(",")[0].trim());
                                Game selectedgame = gameDAO.getGameById(gameId);
                                playerToUpdate.setGameId(selectedgame);
                            }

                            if (teamField.getValue() != null && !selectedGameValue.isEmpty()) {
                                String selectedTeam = teamField.getValue();
                                int teamId = Integer.parseInt(selectedTeam.split(",")[0].trim());
                                Team selectedteam = teamDAO.getTeamById(teamId);
                                playerToUpdate.setTeamId(selectedteam);
                            }

                            if (matchField.getValue() != null && !selectedGameValue.isEmpty()) {
                                String selectedMatch = matchField.getValue();
                                int matchId = Integer.parseInt(selectedMatch.split(",")[0].trim());
                                Match selectedmatch = matchDAO.getMatchById(matchId);
                                playerToUpdate.setMatchId(selectedmatch);
                            }

                            playerDAO.updatePlayer(playerToUpdate);
                            Label labelSaved = new Label(" Player saved and updated in the database! ");
                            labelSaved.getStyleClass().add("standardLabel");
                            vBox.getChildren().add(labelSaved);
                        } catch (Exception ex){
                            ex.printStackTrace();
                            AlertBox.displayAlertBox("Error", "An error occurred while updating player!");
                        }

                    });


                    vBox.getChildren().addAll(fistNameBox, lastNameBox, nicknameBox, addressBox, zipBox, cityBox, countryBox, emailBox,gameBox,teamBox,matchBox, updateButton);

                }
            } catch (Exception e){
                System.out.println(e.getMessage());
                vBox.getChildren().add(labelNoPlayerId);
            }
        });

        getIdBox.getChildren().addAll(playerIdBox, getButton);
        anchorPane.getChildren().addAll(getIdBox, vBox);
    }

    private static void showDeletePlayerForm(AnchorPane anchorPane) {
        getIdBox = createResultBox();

        TextField playerInfield = new TextField();
        HBox playerIdBox = createResultBoxContentBox("Enter Player ID: ", "Player ID", playerInfield, false );

        vBox = createResultBox(250.0);

        Label labelNoPlayerId = new Label(" No player found! Enter a different ID (only numbers allowed). ");
        labelNoPlayerId.getStyleClass().add("standardLabel");

        Button getButton = new Button("Get Player from database");
        getButton.getStyleClass().add("standardButton");
        getButton.setOnAction(event -> {
            try {
                Player playerToDelete = playerDAO.getPlayer(Integer.parseInt(playerInfield.getText()));

                if (playerToDelete == null) {
                    vBox.getChildren().add(labelNoPlayerId);
                    return;
                } else {
                    if (!vBox.getChildren().isEmpty()) {
                        vBox.getChildren().clear();
                    }

                    Label player = new Label();
                    player.setText(
                            "Namn: " + playerToDelete.getFullName() + "\n" +
                            "Nickname: " + playerToDelete.getNickname() + "\n" +
                            "Address: " + playerToDelete.getFullAddress() + "\n" +
                            "Country: " + playerToDelete.getCountry() + "\n" +
                            "E-mail: " + playerToDelete.getEmail() + "\n" +
                            "Game: " + playerToDelete.getGameName() + "\n" +
                            "Team: " + playerToDelete.getTeamName() + "\n" +
                            "Match: " + playerToDelete.getMatchName()
                    );
                    player.getStyleClass().add("standardLabel");
                    vBox.getChildren().add(player);

                    Button deleteButton = new Button("Delete Player");
                    deleteButton.getStyleClass().add("standardButton");
                    deleteButton.setOnAction(e -> {
                        boolean deletePlayer = ConfirmBox.display("Delete Player", "Are you sure you want to delete this player from the database?");
                        if (deletePlayer) {
                          boolean deleted = playerDAO.deletePlayerById(playerToDelete.getId());
                          if (deleted) {
                              Label labelDeleted = new Label(" Player deleted. Database up to date! ");
                              labelDeleted.getStyleClass().add("standardLabel");
                              vBox.getChildren().add(labelDeleted);
                          }

                        } else {
                            Label labelNotDeleted = new Label("Player NOT deleted");
                            vBox.getChildren().add(labelNotDeleted);
                        }
                    });
                    vBox.getChildren().add(deleteButton);

                }
            } catch (Exception e){
            e.printStackTrace();
            }
        } );

        getIdBox.getChildren().addAll(playerIdBox, getButton);
        anchorPane.getChildren().addAll(getIdBox, vBox);

    }

    protected static void addCustomComponents(AnchorPane anchorPane, List<Player> players){


    }

    private static VBox createResultBox() {
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(20));
        vBox.setSpacing(10);
        vBox.getStyleClass().add("backgroundTeaGreen");
        AnchorPane.setTopAnchor(vBox, 140.0); // Y för toppen
        AnchorPane.setLeftAnchor(vBox, 210.0); // X för vänster
        AnchorPane.setRightAnchor(vBox, anchorPane.getWidth() - 210.0 - (HelloApplication.width - 235)); // Höger
        AnchorPane.setBottomAnchor(vBox, anchorPane.getHeight() - 140.0 - (HelloApplication.height - 160)); //
       /* AnchorPane.setTopAnchor(vBox, 150.0);
        AnchorPane.setLeftAnchor(vBox, 220.0);
        AnchorPane.setRightAnchor(vBox, 30.0);
        AnchorPane.setBottomAnchor(vBox, 30.0);*/
        return vBox;
    }

    private static VBox createResultBox(Double topAnchor) {
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(20));
        vBox.setSpacing(10);
        vBox.getStyleClass().add("backgroundTeaGreen");
        AnchorPane.setTopAnchor(vBox, topAnchor);
        AnchorPane.setLeftAnchor(vBox, 220.0);
        AnchorPane.setRightAnchor(vBox, 30.0);
        AnchorPane.setBottomAnchor(vBox, 30.0);
        return vBox;
    }

    private static HBox createResultBoxContentBox(String label, String prompt, TextField field, boolean update){
        HBox box = new HBox(5);
        Label l = new Label(label);
        l.getStyleClass().add("standardLabel");
        field.getStyleClass().add("textFieldOne");
        if (update){
            field.setText(prompt);
        } else {
            field.setPromptText(prompt);
        }
        box.getChildren().addAll(l, field);
        return  box;
    }

    private static <T> HBox createResultBoxContentBox(String label, String prompt, ComboBox<String> comboBox, List<T> items, Function<T, String> itemMapper) {
        HBox box = new HBox(5);
        Label l = new Label(label);
        l.getStyleClass().add("standardLabel");
        comboBox.setPromptText(prompt);
        comboBox.getStyleClass().add("textFieldOne");

        // Mappa objekt till strängar och lägg till i ComboBox
        for (T item : items) {
            comboBox.getItems().add(itemMapper.apply(item));
        }

        box.getChildren().addAll(l, comboBox);
        return box;
    }

    private static <T> HBox createResultBoxContentBoxComboBoxUpdate(String label, ComboBox<String> comboBox, List<T> items, Function<T, String> itemMapper, String selectedValue) {
        HBox box = new HBox(5);
        Label l = new Label(label);
        l.getStyleClass().add("standardLabel");

        if (selectedValue == null || selectedValue.isEmpty()) {
            comboBox.setPromptText("Select an option");
        }

        comboBox.getStyleClass().add("textFieldOne");

        for (T item : items) {
            String mappedValue = itemMapper.apply(item);
            comboBox.getItems().add(mappedValue);

            // Sätt valt objekt
            if (selectedValue != null && mappedValue.equals(selectedValue)) {
                comboBox.setValue(mappedValue);
            }
        }
        if (selectedValue != null) {
            comboBox.setValue(selectedValue);
        }

        box.getChildren().addAll(l, comboBox);
        return box;
    }

    private static void clearResaultBox (VBox a, VBox b){
        if (a != null && !a.getChildren().isEmpty()) {
            a.getChildren().clear();
        }
        if (b != null && !b.getChildren().isEmpty()) {
            b.getChildren().clear();
        }
    }


}
