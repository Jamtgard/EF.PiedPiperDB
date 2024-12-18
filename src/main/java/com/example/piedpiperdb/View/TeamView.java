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
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import java.util.stream.Collectors;

//GEFP-21-SJ
public class TeamView extends AbstractScene{

    private static PlayerDAO playerDAO = new PlayerDAO();
    private static GameDAO gameDAO = new GameDAO();
    private static TeamDAO teamDAO = new TeamDAO();
    private static MatchDAO matchDAO = new MatchDAO();
    //private static TeamActions teamActions = new TeamActions(teamDAO);

    private static VBox idBox;
    private static VBox vBox;


    public static Scene startTeamScene(Stage window){

        Scene baseScene = AbstractScene.getScene(window);
        VBox vBox = AbstractScene.leftVbox;

        AbstractScene.back.setOnAction(e->{ ChangeSceneAction.toStartPage(window); });

        addCustomComponents(vBox);
        return baseScene;
    }

    private static void addCustomComponents(VBox vbox) {

        List<Game> listOfGames = gameDAO.getAllGames();
        List<CheckBox> listOfCheckboxes = new ArrayList<>();

        for (Game game : listOfGames) {
            String gameName = game.getGameName();
            int gameId = game.getGameId();
            CheckBox checkBox = new CheckBox(gameName + " , Game ID: " + gameId);
            listOfCheckboxes.add(checkBox);
        }

        Button getAllTeamsButton = new Button("Show all Teams");
        getAllTeamsButton.getStyleClass().add("standardButton");
        getAllTeamsButton.setMinSize(160, 30);
        getAllTeamsButton.setOnAction(event -> {
            List<Team> listOfAllTeams = teamDAO.getAllTeams();
            System.out.println(listOfAllTeams.size());
            showTable(anchorPane, listOfAllTeams);
        });

        Button teamsByGameSelectionButton = new Button("Show Teams By Game");
        teamsByGameSelectionButton.getStyleClass().add("standardButton");
        teamsByGameSelectionButton.setMinSize(160, 30);
        teamsByGameSelectionButton.setOnAction(event -> {

            clearResultBox(idBox, TeamView.vBox);

            List<String> selectedGameNames = ConfirmBox.displayCheckBoxOptions("Select game or games", listOfCheckboxes);
            List<Integer> selectedGamesIds = new ArrayList<>();

            for (String gameName : selectedGameNames) {
                try {

                    String[] parts = gameName.split(" ");
                    String lastPart  = parts[parts.length - 1];
                    int id = Integer.parseInt(lastPart);
                    selectedGamesIds.add(id);
                    //System.out.println("Accepted parse of ID: " + id);

                } catch (NumberFormatException e) {
                    System.out.println(e.getMessage());
                    System.out.println("Error while parse ID: " + selectedGamesIds);
                }
            }
            List<Team> teams = teamDAO.getTeamsByGame(selectedGamesIds);
            showTable(anchorPane, teams);
        });

        Button addNewTeamButton = new Button("Add New Team");
        addNewTeamButton.getStyleClass().add("standardButton");
        addNewTeamButton.setMinSize(160, 30);
        addNewTeamButton.setOnAction(event -> {
            showAddTeamForm(anchorPane);
        });

        Button updateTeamByIdButton = new Button("Update Team by ID");
        updateTeamByIdButton.getStyleClass().add("standardButton");
        updateTeamByIdButton.setMinSize(160, 30);
        updateTeamByIdButton.setOnAction(event -> {
            showUpdateTeamForm(anchorPane);
        });

        Button deleteTeamByIdButton = new Button("Delete Team by ID");
        deleteTeamByIdButton.getStyleClass().add("standardButton");
        deleteTeamByIdButton.setMinSize(160, 30);
        deleteTeamByIdButton.setOnAction(event -> {
            showDeleteTeamForm(anchorPane);
        });

        vbox.getChildren().addAll(getAllTeamsButton, teamsByGameSelectionButton,addNewTeamButton, updateTeamByIdButton, deleteTeamByIdButton);
    }

    public static void showTable(AnchorPane anchorPane, List<Team> players){

        vBox = createResultBox();
        vBox.getStyleClass().add("textFieldOne");

        TableView<Team> table = createTeamTable(players);

        table.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(table, Priority.ALWAYS);

        vBox.getChildren().add(table);
        anchorPane.getChildren().add(vBox);

    }

    private static TableView<Team> createTeamTable(List<Team> teamMembers){
        ObservableList<Team> teamsObservableList = FXCollections.observableArrayList(teamMembers);

        TableView<Team> tableView = new TableView<>();

        TableColumn<Team, String> team_id = new TableColumn<>("Team Name");
        team_id.setCellValueFactory(new PropertyValueFactory<>("teamId"));

        TableColumn<Team, String> team_name = new TableColumn<>("Team Name");
        team_name.setCellValueFactory(new PropertyValueFactory<>("teamName"));

        TableColumn<Team, String> game_name = new TableColumn<>("Game");
        game_name.setCellValueFactory(new PropertyValueFactory<>("gameName"));

        TableColumn<Team, String> player_nickname = new TableColumn<>("Players");
        player_nickname.setCellValueFactory(cellData ->{
            Team team = cellData.getValue();
            String Nicknames = team.getListOfPlayersInTeam().stream()
                    .map(Player::getNickname)
                    .collect(Collectors.joining("\n"));
            return new SimpleStringProperty(Nicknames);
        });

        tableView.getColumns().addAll(team_id, team_name, game_name, player_nickname);
        tableView.setItems(teamsObservableList);
        return tableView;
    }

// CRUD TeamForms
//----------------------------------------------------------------------------------------------------------------------

    private static void showAddTeamForm(AnchorPane anchorPane){

        TextField gameField = new TextField();
        TextField playerField = new TextField();

        VBox formContainer = new VBox();
        formContainer.setPadding(new Insets(20));
        formContainer.setSpacing(10);
        formContainer.getStyleClass().add("backgroundTeaGreen");
        AnchorPane.setTopAnchor(formContainer, 150.0);
        AnchorPane.setLeftAnchor(formContainer, 220.0);
        AnchorPane.setRightAnchor(formContainer, 30.0);
        AnchorPane.setBottomAnchor(formContainer, 30.0);

        HBox teamNameBox = new HBox(5);
        Label teamName = new Label("Team Name* ");
        teamName.getStyleClass().add("standardLabel");
        TextField teamNamefield = new TextField();
        teamNamefield.getStyleClass().add("textFieldOne");
        teamNamefield.setPromptText("First name");
        teamNameBox.getChildren().addAll(teamName, teamNamefield);

        // FIXA !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        /*
        List<Game> games = gameDAO.getAllGames();
        HBox gameBox = createResultBoxContentBox(
                " Game ", "Select Game",gameField, games, game -> game.getGameId() + ", " + game.getGameName()
        );

         */



        //Korrigera nedan:
        formContainer.getChildren().add(teamNameBox);

        Button saveTeamButton = new Button("Save Team");
        saveTeamButton.getStyleClass().add("standardButton");
        saveTeamButton.setMinSize(160, 30);

        saveTeamButton.setOnAction(event -> {
            Team team = null;

            try {
                if (teamNamefield.getText().isEmpty() || teamNamefield.getText().trim().isEmpty()) {
                    AlertBox.displayAlertBox("Error", "Please fill in all mandatory fields. Fields marked with \"*\".");
                    return;
                } else if (!teamDAO.isTeamNameUnique(teamNamefield.getText())) {
                    AlertBox.displayAlertBox("Error", "Team name already taken. \nPlease try again.");
                    return;
                } else {
                    try {
                        team = new Team(teamNamefield.getText());

                        Label savedLabel = new Label("Team has been saved to the database.");
                        savedLabel.getStyleClass().add("standardLabel");
                        vBox.getChildren().add(savedLabel);
                        //anchorPane.getChildren().add(savedLabel);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }

                teamDAO.createTeam(team);

            } catch (Exception e) {
                AlertBox.displayAlertBox("Error", "Error while saving team.");
            }
        });

        /*
        saveTeamButton.setOnAction(event -> {
            try {
                // Lägg in kontroll för annat än isEmpty.. t.ex (contains " ". elr möjligen fula ord.)
                if (teamNamefield.getText().isEmpty()) {
                    throw new IllegalArgumentException("Mandatory fields are empty.");
                }

                Team team = new Team(teamNamefield.getText());
                Label savedLabel = new Label("Team has successfully been saved to the database.");
                savedLabel.getStyleClass().add("standardLabel");
                //formContainer.getChildren().add(savedLabel);
                leftVbox.getChildren().add(savedLabel);

            } catch (IllegalArgumentException e) {
                AlertBox.displayAlertBox("Error", "Please fill in mandatory fields marked with \"*\".");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

         */



        formContainer.getChildren().add(saveTeamButton);
        anchorPane.getChildren().add(formContainer);

    }

    private static void showUpdateTeamForm(AnchorPane anchorPane){
        VBox getIdBox = new VBox();
        getIdBox.setPadding(new Insets(20));
        getIdBox.setSpacing(10);
        getIdBox.getStyleClass().add("backgroundTeaGreen");
        AnchorPane.setTopAnchor(getIdBox, 150.0);
        AnchorPane.setLeftAnchor(getIdBox, 220.0);
        AnchorPane.setRightAnchor(getIdBox, 30.0);
        AnchorPane.setBottomAnchor(getIdBox, 30.0);

        HBox teamIdBox = new HBox(5);
        Label teamId = new Label("Enter Team ID: ");
        teamId.getStyleClass().add("standardLabel");
        TextField teamIdfield = new TextField();
        teamIdfield.getStyleClass().add("textFieldOne");
        teamIdfield.setPromptText("Team ID");
        teamIdfield.setAlignment(Pos.BASELINE_CENTER); // Test för att se placeringen av Textfield - Önskar att det är oberoende av Label-width.
        teamIdBox.getChildren().addAll(teamId, teamIdfield);

        VBox formContainer = new VBox(5);
        formContainer.setPadding(new Insets(20));
        formContainer.getStyleClass().add("backgroundTeaGreen");
        AnchorPane.setTopAnchor(formContainer, 250.0);
        AnchorPane.setLeftAnchor(formContainer, 220.0);
        AnchorPane.setRightAnchor(formContainer, 30.0);
        AnchorPane.setBottomAnchor(formContainer, 30.0);

        Button getButton = new Button("Get Team  to Update from database");
        getButton.getStyleClass().add("standardButton");
        //getButton.set

        getButton.setOnAction(event -> {
            try {
                Team teamToUpdate = teamDAO.getTeamById(Integer.parseInt(teamIdfield.getText()));

                if (teamToUpdate == null) {
                    AlertBox.displayAlertBox("Error", "Team ID does not exist.");
                    return;
                }
                HBox teamNameBox = new HBox(5);
                Label teamName = new Label(" Team Name* ");
                teamName.getStyleClass().add("standardLabel");
                TextField teamNamefield = new TextField();
                teamNamefield.getStyleClass().add("textFieldOne");
                teamNamefield.setText(teamToUpdate.getTeamName());
                teamNameBox.getChildren().addAll(teamName, teamNamefield);

                Button updateButton = new Button("Update Team");
                updateButton.getStyleClass().add("standardButton");
                updateButton.setOnAction(actionEvent -> {

                    teamToUpdate.setTeamName(teamNamefield.getText());

                    teamDAO.updateTeam(teamToUpdate);
                    Label updatedLabel = new Label("Team has successfully been updated in the database.");
                    updatedLabel.getStyleClass().add("standardLabel");
                    formContainer.getChildren().addAll(updatedLabel);

                });

                formContainer.getChildren().addAll(teamNameBox, updateButton);

            } catch (Exception e) {
                AlertBox.displayAlertBox("Error", "Could not update team.");
            }
        });

        getIdBox.getChildren().addAll(getButton, teamIdBox);
        anchorPane.getChildren().addAll(getIdBox, formContainer);

    }

    private static void showDeleteTeamForm(AnchorPane anchorPane){
        VBox getIdBox = new VBox();
        getIdBox.setPadding(new Insets(20));
        getIdBox.setSpacing(10);
        getIdBox.getStyleClass().add("backgroundTeaGreen");
        AnchorPane.setTopAnchor(getIdBox, 150.0);
        AnchorPane.setLeftAnchor(getIdBox, 220.0);
        AnchorPane.setRightAnchor(getIdBox, 30.0);
        AnchorPane.setBottomAnchor(getIdBox, 30.0);

        HBox teamIdBox = new HBox(5);
        Label teamId = new Label("Enter Team ID: ");
        teamId.getStyleClass().add("standardLabel");
        TextField teamIdfield = new TextField();
        teamIdfield.getStyleClass().add("textFieldOne");
        teamIdfield.setPromptText("Team ID");
        teamIdfield.setAlignment(Pos.BASELINE_CENTER); // Test för att se placeringen av Textfield - Önskar att det är oberoende av Label-width.
        teamIdBox.getChildren().addAll(teamId, teamIdfield);

        VBox formContainer = new VBox(5);
        formContainer.setPadding(new Insets(20));
        formContainer.getStyleClass().add("backgroundTeaGreen");
        AnchorPane.setTopAnchor(formContainer, 250.0);
        AnchorPane.setLeftAnchor(formContainer, 220.0);
        AnchorPane.setRightAnchor(formContainer, 30.0);
        AnchorPane.setBottomAnchor(formContainer, 30.0);

        Button getButton = new Button("Get Team to Delete from database");
        getButton.getStyleClass().add("standardButton");
        getButton.setOnAction(event -> {
            try {
                Team teamToDelete = teamDAO.getTeamById(Integer.parseInt(teamIdfield.getText()));

                if (teamToDelete == null) {
                    AlertBox.displayAlertBox("Error", "Team ID does not exist.");
                    return;
                }

                Button deleteButton = new Button("Delete Team");
                deleteButton.getStyleClass().add("standardButton");
                deleteButton.setOnAction(actionEvent -> {

                    boolean deleteTeamConfirm = ConfirmBox.display("Delete Team", "Are you sure you want to delete this team from the database?");
                    if (deleteTeamConfirm) {
                        boolean deleted = teamDAO.deleteTeamById(teamToDelete.getTeamId());
                        if (deleted) {
                            Label deletedLabel = new Label("Team has successfully been deleted in the database.");
                            deletedLabel.getStyleClass().add("standardLabel");
                            formContainer.getChildren().addAll(deletedLabel);
                        }
                    } else {
                        Label cancDeleteLabel = new Label("Cancelled deletion of Team");
                        cancDeleteLabel.getStyleClass().add("standardLabel");
                        formContainer.getChildren().add(cancDeleteLabel);
                    }
                });

                formContainer.getChildren().addAll(deleteButton);

            } catch (Exception e) {
                AlertBox.displayAlertBox("Error", "Could not delete team.");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        });

        getIdBox.getChildren().addAll(getButton, teamIdBox);
        anchorPane.getChildren().addAll(getIdBox, formContainer);
    }

// CRUD ResultBox
//----------------------------------------------------------------------------------------------------------------------

    private static VBox createResultBox(){
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(20));
        vBox.setSpacing(10);
        vBox.getStyleClass().add("backgroundTeaGreen");
        return vBox;
    }

    private static <T> HBox createResultBoxContentBox (String label, String prompt, ComboBox<String> comboBox, List<T> items, Function<T, String> itemMapper){

        HBox hBox = new HBox(5);
        Label localLabel = new Label(label);
        localLabel.getStyleClass().add("standardLabel");
        comboBox.setPromptText(prompt);
        comboBox.getStyleClass().add("textFieldOne");

        for (T item : items) {
            comboBox.getItems().add(itemMapper.apply(item));
        }

        hBox.getChildren().addAll(localLabel, comboBox);
        return hBox;
    }

    private static HBox createContentBox(String label, String prompt, TextField field, boolean update){

        HBox hBox = new HBox(5);
        Label localLabel = new Label(label);
        field.getStyleClass().add("textFieldOne");

        if(update){
            field.setText(prompt);
        } else {
            field.setPromptText(prompt);
        }
        hBox.getChildren().addAll(localLabel, field);
        return hBox;
    }

    private static void clearResultBox(VBox vBoxOne, VBox vBoxTwo){
        if (vBoxOne != null && !vBoxOne.getChildren().isEmpty()) {
            vBoxOne.getChildren().clear();
        }
        if (vBoxTwo != null && !vBoxTwo.getChildren().isEmpty()) {
            vBoxTwo.getChildren().clear();
        }
    }



}
