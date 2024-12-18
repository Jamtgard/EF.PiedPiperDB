package com.example.piedpiperdb.View;

import com.example.piedpiperdb.DAO.JavaFXActions.ChangeSceneAction;
import com.example.piedpiperdb.DAO.JavaFXActions.PlayerActions;
import com.example.piedpiperdb.Entities.Game;
import com.example.piedpiperdb.Entities.Player;
import com.example.piedpiperdb.Entities.Team;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


//GEFP-19-AA //GEFP-30-AA Delat upp koden i PlayerAction och PlayerView
public class PlayerView extends AbstractScene{

    private static VBox getIdBox;
    private static VBox resultBox;

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


    public static Scene playerScene(Stage window){
        Scene baseScene = AbstractScene.getScene(window); // Skapar en sen från mallen i abstract Scene

        VBox vBox = AbstractScene.leftVbox;

        AbstractScene.back.setOnAction(e->{
            ChangeSceneAction.toStartPage(window);
        });

        addCustomComponents(vBox);

        return baseScene;
    }

    private static void addCustomComponents(VBox vBox){

            Button getAllPlayers = creatButton("Show all players");
            getAllPlayers.setOnAction(event -> {
                 clearResultBox(resultBox, getIdBox);
                 resultBox = createResultBox();
                 resultBox = PlayerActions.getTableViewAllPlayers(resultBox);
                 anchorPane.getChildren().add(resultBox);
            });

            Button selectedPlayers = new Button("Show players from \nselected game or games");
            selectedPlayers.getStyleClass().add("standardButton");
            selectedPlayers.setMinSize(160, 50);
            selectedPlayers.setOnAction(actionEvent -> {
                clearResultBox(getIdBox, resultBox);
                resultBox = createResultBox();
                List<CheckBox> checkBoxes = PlayerActions.gameCheckBoxes();
                List<String> selections = ConfirmBox.displayCheckBoxOptions("Select game or games", checkBoxes);
                resultBox = PlayerActions.getTableViewSelectedPlayers(resultBox, selections);
                anchorPane.getChildren().add(resultBox);
            });

            Button addNewPlayerButton = creatButton("Add new player");
            addNewPlayerButton.setOnAction(e -> {
                clearResultBox(getIdBox, resultBox);
                showAddPlayerForm(anchorPane);
            });

            Button updatePlayerByIdButton = creatButton("Update player by id");
            updatePlayerByIdButton.setOnAction(e -> {
                clearResultBox(getIdBox, resultBox);
                showUpdatePlayerForm(anchorPane);
            });

            Button deletePlayerByIdButton = creatButton("Delete player by id");
            deletePlayerByIdButton.setOnAction(e -> {
                clearResultBox(getIdBox, resultBox);
                showDeletePlayerForm(anchorPane);
            });

            vBox.getChildren().addAll(getAllPlayers, selectedPlayers, addNewPlayerButton, updatePlayerByIdButton, deletePlayerByIdButton );
    }

    public static void showAddPlayerForm(AnchorPane anchorPane) {
        try {
            initializeTextFieldsPlayerInfo();

            resultBox = createResultBox();

            resultBox.getChildren().addAll(
                    createResultBoxContentBox("First Name*", "First Name", firstNameField, false),
                    createResultBoxContentBox("Last Name*", "Last Name", lastNameField, false),
                    createResultBoxContentBox("Nickname*", "Nickname", nicknameField, false),
                    createResultBoxContentBox("Street Address", "Street Address", streetAddressField, false),
                    createResultBoxContentBox("Zip Code", "Zip Code", zipCodeField, false),
                    createResultBoxContentBox("City", "City", cityField, false),
                    createResultBoxContentBox("Country", "Country", countryField, false),
                    createResultBoxContentBox("E-mail*", "E-mail", emailField, false)
            );
            List<Game> games = PlayerActions.getAllGames();
            resultBox.getChildren().add(createResultBoxContentBoxComboBox(
                    "Game", "Select Game", gameField, games, game -> game.getGameId() + ", " + game.getGameName()
            ));

            List<Team> teams = new ArrayList<>();
            resultBox.getChildren().add(createResultBoxContentBoxComboBox(
                    "Team", "Select Team", teamField, teams, team -> team.getTeamId() + ", " + team.getTeamName()
            ));

            addGameFieldListener(gameField, teamField);

            Button saveButton = creatButton("Save Player");
            saveButton.setOnAction(event -> {
                if (validateInputNewPlayer(
                        firstNameField.getText(), lastNameField.getText(), nicknameField.getText(), emailField.getText())) {

                    Player player = PlayerActions.createPlayerFromFields(
                            firstNameField.getText(), lastNameField.getText(), nicknameField.getText(),
                            streetAddressField.getText(), zipCodeField.getText(), cityField.getText(),
                            countryField.getText(), emailField.getText(),
                            gameField.getValue(), teamField.getValue()
                    );

                    boolean saved = PlayerActions.savePlayer(player);
                    if (saved) {
                        Label labelSaved = creatLabel(" Player saved and updated in the database! ");
                        resultBox.getChildren().add(labelSaved);
                    } else {
                        AlertBox.displayAlertBox("Error", "An error occurred while saving the player to the database. Player not Saved!");
                    }

                }
            });
            resultBox.getChildren().add(saveButton);
            anchorPane.getChildren().add(resultBox);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            AlertBox.displayAlertBox("Error", "An error occurred while saving the player to the database.");
        }
    }

    //GEFP-31-AA
    public static void addGameFieldListener(ComboBox<String> gameField, ComboBox<String> teamField) {
        gameField.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                try {
                    int gameId = Integer.parseInt(newValue.split(",")[0].trim());
                    List<Team> filteredTeams = PlayerActions.getTeamsByGame(gameId);

                    // Uppdatera teamField
                    teamField.getItems().clear();
                    filteredTeams.forEach(filteredTeam ->
                            teamField.getItems().add(filteredTeam.getTeamId() + ", " + filteredTeam.getTeamName()));
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void showUpdatePlayerForm(AnchorPane anchorPane) {
        initializeTextFieldsPlayerInfo();

        resultBox = createResultBox(230.0);

        TextField playerIdField = new TextField();
        Button getPlayerButton = creatButton("Get Player");
        getPlayerButton.setOnAction(event -> {
            try {
                int playerId = Integer.parseInt(playerIdField.getText());
                Player playerToUpdate = PlayerActions.getPlayerById(playerId);

                if (playerToUpdate == null) {
                    AlertBox.displayAlertBox("Error", "No player found with the given ID.");
                    return;
                }

                populateUpdatePlayerFormFields(playerToUpdate);
            } catch (NumberFormatException ex) {
                AlertBox.displayAlertBox("Error", "Please enter a valid Player ID.");
            }
        });
        getIdBox = createResultBox();
        getIdBox.getChildren().addAll(
                createResultBoxContentBox("Player ID", "Enter Player ID", playerIdField, false),
                getPlayerButton
        );
        anchorPane.getChildren().addAll(getIdBox, resultBox);
    }

    private static void populateUpdatePlayerFormFields(Player playerToUpdate) {
        // Fyll i formulärfälten med spelarens data
        firstNameField.setText(playerToUpdate.getFirstName());
        lastNameField.setText(playerToUpdate.getLastName());
        nicknameField.setText(playerToUpdate.getNickname());
        streetAddressField.setText(playerToUpdate.getStreetAddress());
        zipCodeField.setText(playerToUpdate.getZipCode());
        cityField.setText(playerToUpdate.getCity());
        countryField.setText(playerToUpdate.getCountry());
        emailField.setText(playerToUpdate.getEmail());

        List<Game> games = PlayerActions.getAllGames();
        Game game = playerToUpdate.getGameId();
        String selectedGameValue = game != null ? game.getGameId() + ", " + game.getGameName() : null;

        List<Team> teams = PlayerActions.getTeamsByGame(game.getGameId());
        Team team = playerToUpdate.getTeamId();
        String selectedTeamValue = team != null ? team.getTeamId() + ", " + team.getTeamName() : null;

        HBox gameBox = createResultBoxContentBoxComboBoxUpdate(
                "Game", gameField, games, g -> g.getGameId() + ", " + g.getGameName(), selectedGameValue
        );

        HBox teamBox = createResultBoxContentBoxComboBoxUpdate(
                "Team", teamField, teams, t -> t.getTeamId() + ", " + t.getTeamName(), selectedTeamValue
        );

        addGameFieldListener(gameField, teamField);

        Button updatePlayerButton = creatButton("Update Player");
        updatePlayerButton.setOnAction(event -> {
            try {
                if (!nicknameField.getText().equals(playerToUpdate.getNickname()) && !PlayerActions.isNicknameUnique(nicknameField.getText())) {
                    AlertBox.displayAlertBox("Error", "Nickname is already taken. Try a different one!");
                    return;
                }

                if (!emailField.getText().equals(playerToUpdate.getEmail()) && !PlayerActions.isEmailUnique(emailField.getText())) {
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

                if (gameField.getValue() != null) {
                    int gameId = Integer.parseInt(gameField.getValue().split(",")[0].trim());
                    playerToUpdate.setGameId(PlayerActions.getAllGames().stream()
                            .filter(g -> g.getGameId() == gameId).findFirst().orElse(null));
                }

                if (teamField.getValue() != null) {
                    int teamId = Integer.parseInt(teamField.getValue().split(",")[0].trim());
                    playerToUpdate.setTeamId(PlayerActions.getTeamsByGame(playerToUpdate.getGameId().getGameId()).stream()
                            .filter(t -> t.getTeamId() == teamId).findFirst().orElse(null));
                }

                boolean updated = PlayerActions.updatePlayer(playerToUpdate);
                if (updated) {
                    Label labelUpdated = creatLabel("Player updated in the database!");
                    resultBox.getChildren().add(labelUpdated);
                } else {
                    AlertBox.displayAlertBox("Error", "An error occurred while updating the player in the database. Update not saved!");
                }

            } catch (Exception ex) {
                AlertBox.displayAlertBox("Error", "An error occurred while updating the player.");
            }
        });

        resultBox.getChildren().clear();
        resultBox.getChildren().addAll(
                createResultBoxContentBox("First Name*", playerToUpdate.getFirstName(), firstNameField, true),
                createResultBoxContentBox("Last Name*", playerToUpdate.getLastName(), lastNameField, true),
                createResultBoxContentBox("Nickname*", playerToUpdate.getNickname(), nicknameField, true),
                createResultBoxContentBox("Street Address", playerToUpdate.getStreetAddress(), streetAddressField, true),
                createResultBoxContentBox("Zip Code", playerToUpdate.getZipCode(), zipCodeField, true),
                createResultBoxContentBox("City", playerToUpdate.getCity(), cityField, true),
                createResultBoxContentBox("Country", playerToUpdate.getCountry(), countryField, true),
                createResultBoxContentBox("E-mail*", playerToUpdate.getEmail(), emailField,true),
                gameBox,
                teamBox,
                updatePlayerButton
        );
    }

    public static boolean validateInputNewPlayer(String firstName, String lastName, String nickname, String email) {
        if (PlayerActions.areFieldsEmpty(firstName, lastName, nickname, email)) {
            AlertBox.displayAlertBox("Error", "Please fill in all mandatory fields. Fields marked with *.");
            return false;
        } else if (!PlayerActions.isNicknameUnique(nickname)) {
            AlertBox.displayAlertBox("Error", "Nickname is already taken. Try a different one!");
            return false;
        } else if (!PlayerActions.isEmailUnique(email)) {
            AlertBox.displayAlertBox("Error", "E-mail is already taken. Try a different one!");
            return false;
        }
        return true;
    }

    private static void showDeletePlayerForm(AnchorPane anchorPane){
        getIdBox = createResultBox();
        TextField playerIdField = new TextField();
        HBox playerIdBox = createResultBoxContentBox("Enter Player ID: ", "Player ID", playerIdField, false);

        resultBox = createResultBox(250.0);
        Label labelNoPlayerFound = new Label(" No player found! Enter a different ID (only numbers allowed). ");
        labelNoPlayerFound.getStyleClass().add("standardLabel");

        Button getButton = creatButton("Get player from database");
        getButton.setOnAction(event -> {
            try{
                int playerId = Integer.parseInt(playerIdField.getText());
                Player playerToDelete = PlayerActions.getPlayerById(playerId);

                if (playerToDelete == null) {
                    resultBox.getChildren().add(labelNoPlayerFound);
                    return;
                }

                showPlayerInfoForPlayerToDelete(playerToDelete);

            } catch (Exception e){
                System.out.println(e.getMessage());
                AlertBox.displayAlertBox("Error", "An error occurred while deleting the player from database.");
            }
        });

        getIdBox.getChildren().addAll(playerIdBox, getButton);
        anchorPane.getChildren().addAll(getIdBox,resultBox);
    }

    private static void showPlayerInfoForPlayerToDelete(Player playerToDelete) {
        clearResultBox(resultBox);
        Label playerInfo = creatLabel(
                        "\tName: " + playerToDelete.getFullName() + "\n" +
                        "\tNickname: " + playerToDelete.getNickname() + "\n" +
                        "\tAddress: " + playerToDelete.getStreetAddress() + "\n" +
                        "\t\t\t" + playerToDelete.getZipCode() + "\n" +
                        "\t\t\t" + playerToDelete.getCity() + "\n" +
                        "\tCountry: " + playerToDelete.getCountry() + "\n" +
                        "\tE-mail: " + playerToDelete.getEmail() + "\t\n" +
                        "\tGame: " + playerToDelete.getGameName() + "\n" +
                        "\tTeam: " + playerToDelete.getTeamName() + "\n" +
                        "\tMatch: " + playerToDelete.getMatchName()
                );
        resultBox.getChildren().add(playerInfo);

        Button deleteButton = creatButton("Delete Player");
        deleteButton.setOnAction(event -> {
            boolean deletePlayer = ConfirmBox.display("Delete Player", "Are you sure you want to delete " + playerToDelete.getFullName() + " (Nickname: " + playerToDelete.getNickname() +") from the database?");

            if (deletePlayer) {
                boolean deleted = PlayerActions.deletePlayerById(playerToDelete.getId());

                if (deleted) {
                    Label deletedLabel = creatLabel("Player deleted! Database up to date!");
                    resultBox.getChildren().add(deletedLabel);
                } else {
                    AlertBox.displayAlertBox("Error", "Delete player failed! Please try again.");
                }
            } else {
                AlertBox.displayAlertBox("Error", "Delete player failed! Please try again.");
            }
        });
        resultBox.getChildren().add(deleteButton);
    }

    private static void initializeTextFieldsPlayerInfo() {
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
    }

    private static Button creatButton (String text){
        Button button = new Button(text);
        button.getStyleClass().add("standardButton");
        button.setMinSize(160,30);
        return button;
    }

    private static Label creatLabel (String text){
        Label label = new Label(text);
        label.getStyleClass().add("standardLabel");
        return label;
    }

    private static VBox createResultBox() {
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(20));
        vBox.setSpacing(10);

        //vBox.getStyleClass().add("backgroundTeaGreen");
        AnchorPane.setTopAnchor(vBox, 140.0); // Y för toppen
        AnchorPane.setLeftAnchor(vBox, 210.0); // X för vänster
        AnchorPane.setRightAnchor(vBox, anchorPane.getWidth() - 210.0 - (HelloApplication.width - 235)); // Höger
        AnchorPane.setBottomAnchor(vBox, anchorPane.getHeight() - 140.0 - (HelloApplication.height - 160)); //
        return vBox;
    }

    private static VBox createResultBox(Double topAnchor) {
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(20));
        vBox.setSpacing(10);
        //vBox.getStyleClass().add("backgroundTeaGreen");
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

    private static <T> HBox createResultBoxContentBoxComboBox(String label, String prompt, ComboBox<String> comboBox, List<T> items, Function<T, String> itemMapper) {
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

    private static void clearResultBox(VBox a){
        if (a != null && !a.getChildren().isEmpty()) {
            a.getChildren().clear();
        }
    }

    private static void clearResultBox(VBox a, VBox b){
        if (a != null && !a.getChildren().isEmpty()) {
            a.getChildren().clear();
        }

        if (b != null && !b.getChildren().isEmpty()) {
            b.getChildren().clear();
        }
    }
}
