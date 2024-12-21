package com.example.piedpiperdb.View;

import com.example.piedpiperdb.DAO.GameDAO;
import com.example.piedpiperdb.DAO.JavaFXActions.ChangeSceneAction;
import com.example.piedpiperdb.DAO.JavaFXActions.TeamActions;
import com.example.piedpiperdb.DAO.MatchDAO;
import com.example.piedpiperdb.DAO.PlayerDAO;
import com.example.piedpiperdb.DAO.TeamDAO;
import com.example.piedpiperdb.Entities.Game;
import com.example.piedpiperdb.Entities.Player;
import com.example.piedpiperdb.Entities.Team;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

//GEFP-21-SJ
public class TeamView extends AbstractScene{

    private static VBox getIdBox;
    private static VBox resultBox;

    private static TextField teamNameField;

    private static ComboBox<String> gameField;
    private static ComboBox<String> playerField;


// Start TeamScene & CustomComponents
//----------------------------------------------------------------------------------------------------------------------

    public static Scene startTeamScene(Stage window){

        Scene baseScene = AbstractScene.getScene(window);
        VBox vBox = AbstractScene.leftVbox;

        AbstractScene.back.setOnAction(e->{ ChangeSceneAction.toStartPage(window); });

        addCustomComponents(vBox);
        return baseScene;
    }

    private static void addCustomComponents(VBox vbox) {

        Button getAllTeamsButton = createButton("Show All Teams");
        getAllTeamsButton.setOnAction(event -> {
            clearResultBox(resultBox, getIdBox);
            resultBox = createResultBox();
            Label title = createTitleLabel("All Teams");
            resultBox.getChildren().add(title);
            resultBox = TeamActions.getTableViewAllTeams(resultBox);
            anchorPane.getChildren().add(resultBox);
        });

        Button teamsByGameSelectionButton = createButton("Show players by Game");
        teamsByGameSelectionButton.setOnAction(actionEvent -> {
            clearResultBox(getIdBox, resultBox);
            resultBox = createResultBox();
            Label title = createTitleLabel("Teams from selected game or games");
            resultBox.getChildren().add(title);
            List<CheckBox> checkBoxes = TeamActions.gameCheckBoxes();
            List<String> selections = ConfirmBox.displayCheckBoxOptions("Select game or games", checkBoxes);
            resultBox = TeamActions.getTableViewSelectedTeams(resultBox, selections);
            anchorPane.getChildren().add(resultBox);
        });

        Button addNewTeamButton = createButton("Add New Team");
        addNewTeamButton.setOnAction(event -> {
            clearResultBox(getIdBox, resultBox);
            showAddTeamForm(anchorPane);
        });

        Button updateTeamByIdButton = createButton("Update Team by ID");
        updateTeamByIdButton.setOnAction(event -> {
            clearResultBox(getIdBox, resultBox);
            showUpdateTeamForm(anchorPane);
        });

        Button deleteTeamByIdButton = createButton("Delete Team by ID");
        deleteTeamByIdButton.setOnAction(event -> {
            clearResultBox(getIdBox, resultBox);
            showDeleteTeamForm(anchorPane);
        });

        vbox.getChildren().addAll(getAllTeamsButton, teamsByGameSelectionButton,addNewTeamButton, updateTeamByIdButton, deleteTeamByIdButton);
    }

// "CRUD" TeamForms
//----------------------------------------------------------------------------------------------------------------------

    private static void showAddTeamForm(AnchorPane anchorPane){

        try {
            initializeTextFields();

            resultBox = createResultBox();
            Label title = createTitleLabel("Add New Team");
            resultBox.getChildren().add(title);

            resultBox.getChildren().add(createResultBoxContentBox("Team Name*", "Enter Team Name", teamNameField, false));

            List<Game> games = TeamActions.getAllGames();
            resultBox.getChildren().add(createResultBoxContentBoxComboBox("Game", "Select Game", gameField, games, game -> game.getGameId() + ", " + game.getGameName()));

            Label addPlayerLabel = createLabelWithoutBorder("Optional: Choose a Player to join the Team. (Shift/Ctrl to select multiple Players)");
            resultBox.getChildren().add(addPlayerLabel);

            ListView<String> availablePlayers = TeamActions.createPlayerListView(TeamActions.getAllAvailablePlayers());
            resultBox.getChildren().add(availablePlayers);

            Button saveButton = createButton("Save Team");
            saveButton.setOnAction(event -> {
                if (TeamActions.validateNewTeamName(teamNameField.getText())) {

                    try {
                        Team team = new Team(teamNameField.getText());

                        if (gameField.getValue() != null && !gameField.getValue().isEmpty()) {
                            int gameID = Integer.parseInt(gameField.getValue().split(",")[0].trim());
                            Game selectedGame = TeamActions.getGameById(gameID);
                            team.setGameId(selectedGame);
                        }

                        ObservableList<String> selectedPlayers = availablePlayers.getSelectionModel().getSelectedItems();
                        for (String player : selectedPlayers) {
                            String[] parts = player.split(", ");
                            int playerId = Integer.parseInt(parts[0].trim());
                            Player selectedPlayer = TeamActions.getPlayerById(playerId);

                            if (selectedPlayer != null) {
                                team.getListOfPlayersInTeam().add(selectedPlayer);
                            }
                        }

                        boolean saved = TeamActions.createTeam(team);

                        if (saved) {
                            Label savedLabel = createLabel("Team saved successfully.");
                            resultBox.getChildren().add(savedLabel);
                        } else {
                            AlertBox.displayAlertBox("Error", "An error occurred while saving the team.");
                        }
                    } catch (Exception e) {
                        AlertBox.displayAlertBox("Error", "An error occurred while saving the team.");
                        System.out.println(e.getMessage());
                    }
                }
            });

            resultBox.getChildren().add(saveButton);
            anchorPane.getChildren().add(resultBox);

        } catch (Exception e){
            e.printStackTrace();
            AlertBox.displayAlertBox("Error", "Error while saving Team to database");
        }
    }


    private static void showUpdateTeamForm(AnchorPane anchorPane) {
        initializeTextFields();

        resultBox = createResultBox(260.0);
        TextField teamIdField = new TextField();

        Button getTeamButton = createButton("Get Team");
        getTeamButton.setOnAction(event -> {
            try {
                int teamId = Integer.parseInt(teamIdField.getText());
                Team teamToUpdate = TeamActions.getTeamById(teamId);

                if (teamToUpdate == null) {
                    AlertBox.displayAlertBox("Error", "Team does not exist");
                    return;
                }

                populateUpdateTeamFormFields(teamToUpdate, teamId);
            } catch (NumberFormatException e) {
                AlertBox.displayAlertBox("Error", "Invalid Team ID");
            }
        });

        getIdBox = createResultBox();
        getIdBox.getChildren().addAll(createResultBoxContentBox("Team ID", "Enter Team ID", teamIdField, false), getTeamButton);

        anchorPane.getChildren().addAll(getIdBox, resultBox);
    }

    private static void populateUpdateTeamFormFields(Team teamToUpdate, int teamId) {
        teamNameField.setText(teamToUpdate.getTeamName());

        List<Game> games = TeamActions.getAllGames();
        String selectedGameValue =
                teamToUpdate.getGameId() != null
                        ? teamToUpdate.getGameId().getGameId() + ", " + teamToUpdate.getGameId().getGameName() : null;

        HBox gameBox = createResultBoxContentBoxComboBoxUpdate("Game", gameField, games, game -> game.getGameId() + ", " + game.getGameName(), selectedGameValue);


        ObservableList<String> teamPlayersObservableList = FXCollections.observableArrayList(
                teamToUpdate.getListOfPlayersInTeam().stream()
                        .map(player -> player.getId() + ", " + player.getNickname())
                        .toList()
        );

        ObservableList<String> availablePlayersObservableList = FXCollections.observableArrayList(
                TeamActions.getAllAvailablePlayers().stream()
                        .filter(player -> !teamToUpdate.getListOfPlayersInTeam().contains(player))
                        .map(player -> player.getId() + ", " + player.getNickname())
                        .toList()
        );

        ListView<String> teamPlayersListView = new ListView<>(teamPlayersObservableList);
        teamPlayersListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ListView<String> availablePlayersListView = new ListView<>(availablePlayersObservableList);
        availablePlayersListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        Button removeButton = createButton("Remove Player");
        removeButton.setOnAction(event -> {

            ObservableList<String> selectedPlayers = teamPlayersListView.getSelectionModel().getSelectedItems();

            List<String> playersToRemove = new ArrayList<>(selectedPlayers);

            teamPlayersObservableList.removeAll(playersToRemove);
            availablePlayersObservableList.addAll(playersToRemove);

            for (String playerInfo : playersToRemove) {
                int playerId = Integer.parseInt(playerInfo.split(", ")[0].trim());
                Player player = TeamActions.getPlayerById(playerId);
                if (player != null) {
                    player.setTeamId(null);
                    teamToUpdate.getListOfPlayersInTeam().remove(player);
                    TeamActions.updatePlayerTeamId(player, null);
                }
            }
        });

        Button addButton = createButton("Add Player");
        addButton.setOnAction(event -> {

            ObservableList<String> selectedPlayers = availablePlayersListView.getSelectionModel().getSelectedItems();

            List<String> playersToAdd = new ArrayList<>(selectedPlayers);

            availablePlayersObservableList.removeAll(playersToAdd);
            teamPlayersObservableList.addAll(playersToAdd);

            for (String playerInfo : playersToAdd) {
                int playerId = Integer.parseInt(playerInfo.split(", ")[0].trim());
                Player player = TeamActions.getPlayerById(playerId);
                if (player != null) {
                    player.setTeamId(teamToUpdate);
                    teamToUpdate.getListOfPlayersInTeam().add(player);
                    TeamActions.updatePlayerTeamId(player, teamToUpdate);
                }
            }
        });

        Button updateTeamButton = createButton("Update Team");
        updateTeamButton.setOnAction(event -> {
            try {
                if (TeamActions.validateNewTeamName(teamNameField.getText())) {
                    teamToUpdate.setTeamName(teamNameField.getText());

                    if (gameField.getValue() != null) {
                        int gameId = Integer.parseInt(gameField.getValue().split(",")[0].trim());
                        teamToUpdate.setGameId(TeamActions.getGameById(gameId));
                    }

                    List<Player> updatedPlayers = teamPlayersObservableList.stream()
                            .map(item -> {
                                int playerId = Integer.parseInt(item.split(", ")[0].trim());
                                return TeamActions.getPlayerById(playerId);
                            })
                            .toList();
                    teamToUpdate.setListOfPlayersInTeam(updatedPlayers);

                    boolean updated = TeamActions.updateTeam(teamToUpdate);
                    if (updated) {
                        Label updatedLabel = createLabel("Team updated successfully.");
                        resultBox.getChildren().add(updatedLabel);
                    } else {
                        AlertBox.displayAlertBox("Error", "An error occurred while updating the team.");
                    }
                }
            } catch (Exception e) {
                AlertBox.displayAlertBox("Error", "An error occurred while updating the team.");
                System.out.println(e.getMessage());
            }
        });

        VBox playersVBox = new VBox(10, createLabel("Team Players"), teamPlayersListView, removeButton);
        VBox availablePlayersVBox = new VBox(10, createLabel("Available Players"), availablePlayersListView, addButton);

        HBox playersBox = new HBox(20, playersVBox, availablePlayersVBox);

        resultBox.getChildren().clear();
        resultBox.getChildren().addAll(
                createResultBoxContentBox("Team Name", teamToUpdate.getTeamName(), teamNameField, true),
                gameBox,
                playersBox,
                updateTeamButton
        );
    }


    private static void showDeleteTeamForm(AnchorPane anchorPane) {

        Label title = createTitleLabel("Delete Team");

        TextField teamIdField = new TextField();
        HBox teamIdBox = createResultBoxContentBox("Enter Team ID: ", "Team ID", teamIdField, false);

        resultBox = createResultBox();
        resultBox.getChildren().addAll(title,teamIdBox);

        Button getButton = createButton("Get Team");
        getButton.setOnAction(event -> {
            try {
                int teamId = Integer.parseInt(teamIdField.getText());
                Team teamToDelete = TeamActions.getTeamById(teamId);


                if (teamToDelete == null) {
                    AlertBox.displayAlertBox("Error", "Team does not exist");
                    return;
                }

                showTeamInfoForDeletion(teamToDelete);

            } catch (NumberFormatException e) {
                AlertBox.displayAlertBox("Error", "Invalid Team ID format.");
            } catch (Exception e) {
                AlertBox.displayAlertBox("Error", "An error occurred while fetching the team.");
            }
        });
        resultBox.getChildren().add(getButton);
        anchorPane.getChildren().add(resultBox);
    }

    private static void showTeamInfoForDeletion(Team team) {
        clearResultBox(resultBox);

        Label title = createTitleLabel("Team info:");
        resultBox.getChildren().add(title);

        Label teamInfo = createLabel(
                "\tTeam Name: " + team.getTeamName() + "\n" +
                        "\tGame: " + team.getGameName() + "\n" +
                        "\tPlayers: " + TeamActions.getPlayerNicknames(TeamActions.getPlayersInTeam(team))
        );
        resultBox.getChildren().add(teamInfo);

        Button deleteTeamButton = createButton("Delete Team");
        deleteTeamButton.setOnAction(event -> {
            boolean confirmDelete = ConfirmBox.display("Delete Team", "Are you sure you want to delete " + team.getTeamName() + "?");

            if (confirmDelete) {
                boolean deleted = TeamActions.deleteTeamById(team.getTeamId());
                clearResultBox(resultBox);

                if (deleted) {
                    Label deletedLabel = createLabel("Team successfully deleted.");
                    resultBox.getChildren().add(deletedLabel);
                } else {
                    AlertBox.displayAlertBox("Error", "Error while deleting the team.");
                }
            }
        });
        resultBox.getChildren().add(deleteTeamButton);
    }

// ResultBox
//----------------------------------------------------------------------------------------------------------------------

    private static VBox createResultBox(){
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(20));
        vBox.setSpacing(10);
        AnchorPane.setTopAnchor(vBox, 140.0);
        AnchorPane.setLeftAnchor(vBox, 210.0);
        AnchorPane.setRightAnchor(vBox, anchorPane.getWidth() - 210.0 - (HelloApplication.width - 235));
        AnchorPane.setBottomAnchor(vBox, anchorPane.getHeight() - 140 - (HelloApplication.height - 160));
        return vBox;
    }

    private static VBox createResultBox(Double topAnchor) {
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(20));
        vBox.setSpacing(10);

        AnchorPane.setTopAnchor(vBox, topAnchor);
        AnchorPane.setLeftAnchor(vBox, 220.0);
        AnchorPane.setRightAnchor(vBox, 30.0);
        AnchorPane.setBottomAnchor(vBox, 30.0);
        return vBox;
    }

    private static HBox createResultBoxContentBox(String label, String prompt, TextField field, boolean update){
        HBox box = new HBox(5);
        Label localLabel = new Label(label);
        localLabel.getStyleClass().add("standardLabel");
        field.getStyleClass().add("textFieldOne");
        if (update) {
            field.setText(prompt);
        } else {
            field.setPromptText(prompt);
        }
        box.getChildren().addAll(localLabel, field);
        return box;
    }

    private static <T> HBox createResultBoxContentBoxComboBox (String label, String prompt, ComboBox<String> comboBox, List<T> items, Function<T, String> itemMapper){
        HBox box = new HBox(5);
        Label localLabel = new Label(label);
        localLabel.getStyleClass().add("standardLabel");
        comboBox.setPromptText(prompt);
        comboBox.getStyleClass().add("textFieldOne");

        for (T item : items) {
            comboBox.getItems().add(itemMapper.apply(item));
        }

        box.getChildren().addAll(localLabel, comboBox);
        return box;
    }

    private static <T> HBox createResultBoxContentBoxComboBoxUpdate (String label, ComboBox<String> comboBox, List<T> items, Function<T, String> itemMapper, String selectedValue){
        HBox box = new HBox(5);
        Label LocalLabel = new Label(label);

        if (selectedValue == null || selectedValue.isEmpty()) {
            comboBox.setPromptText("Select an option");
        }

        comboBox.getStyleClass().add("textFieldOne");

        for (T item : items) {
            String mappedValue = itemMapper.apply(item);
            comboBox.getItems().add(mappedValue);
            if (selectedValue != null && mappedValue.equals(selectedValue)) {
                comboBox.setValue(mappedValue);
            }
        }

        box.getChildren().addAll(LocalLabel, comboBox);
        return box;
    }

    private static void clearResultBox(VBox vBoxOne){
        if (vBoxOne != null && !vBoxOne.getChildren().isEmpty()) {
            vBoxOne.getChildren().clear();
        }
    }

    private static void clearResultBox(VBox vBoxOne, VBox vBoxTwo){
        if (vBoxOne != null && !vBoxOne.getChildren().isEmpty()) {
            vBoxOne.getChildren().clear();
        }
        if (vBoxTwo != null && !vBoxTwo.getChildren().isEmpty()) {
            vBoxTwo.getChildren().clear();
        }
    }

// Multiple usage methods
//----------------------------------------------------------------------------------------------------------------------

    private static Button createButton (String text){
        Button button = new Button(text);
        button.getStyleClass().add("standardButton");
        button.setMinSize(160, 30);
        return button;
    }

    private static Label createTitleLabel (String text){
        Label label = new Label(text);
        label.getStyleClass().add("titel");
        return label;
    }

    private static Label createLabel (String text){
        Label label = new Label(text);
        label.getStyleClass().add("standardLabel");
        return label;
    }

    private static Label createLabelWithoutBorder (String text){
        Label label = new Label(text);
        label.getStyleClass().add("standardLabelWithoutBorder");
        return label;
    }

    private static void initializeTextFields (){
        teamNameField = new TextField();
        gameField = new ComboBox<>();
        playerField = new ComboBox<>();
    }

}
