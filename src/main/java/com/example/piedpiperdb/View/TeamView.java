package com.example.piedpiperdb.View;

import com.example.piedpiperdb.DAO.GameDAO;
import com.example.piedpiperdb.DAO.JavaFXActions.ChangeSceneAction;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//GEFP-21-SJ
public class TeamView extends AbstractScene{

    private static PlayerDAO playerDAO = new PlayerDAO();
    private static GameDAO gameDAO = new GameDAO();
    private static TeamDAO teamDAO = new TeamDAO();
    private static MatchDAO matchDAO = new MatchDAO();

    public static Scene startTeamScene(Stage window){

        Scene baseScene = AbstractScene.getScene(window);
        VBox vBox = AbstractScene.leftVbox;

        AbstractScene.back.setOnAction(e->{
            ChangeSceneAction.toStartPage(window);
        });

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


/*        Button selectedTeamButton = new Button("Show Teams from \nselected Game \\ Games");
        selectedTeamButton.getStyleClass().add("standardButton");
        selectedTeamButton.setMinSize(160, 30);
        selectedTeamButton.setOnAction(event -> {
            List<String> listSelection = ConfirmBox.displayCheckBoxOptions("Select Team \\ Teams", listOfCheckboxes);
            List<Integer> ids = new ArrayList<>();
            for (String selection : listSelection) {
                try {
                    String[] parts = selection.split(" ");
                    String lastPart = parts[parts.length - 1];
                    int SelectionId = Integer.parseInt(lastPart);
                    ids.add(SelectionId);
                } catch (NumberFormatException e) {
                    System.out.println(e.getMessage());
                    System.out.println("Error parsing selection: " + selection);
                }
            }
            List<Team> teams = teamDAO.getTeamsByGame(ids);
            showTable(anchorPane, teams);
        });*/


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

        vbox.getChildren().addAll(getAllTeamsButton, addNewTeamButton, updateTeamByIdButton, deleteTeamByIdButton);
    }

    public static void showTable(AnchorPane anchorPane, List<Team> players){
        TableView<Team> table = createTeamTable(players);

        AnchorPane.setTopAnchor(table, 150.0);
        AnchorPane.setLeftAnchor(table, 220.0);
        AnchorPane.setRightAnchor(table, 30.0);
        AnchorPane.setBottomAnchor(table, 30.0);
        anchorPane.getStyleClass().add("backgroundTeaGreen");
        anchorPane.getStyleClass().add("standardLabel");

        anchorPane.getStyleClass().add("columnV");
        anchorPane.getChildren().addAll(table);
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

    private static void showAddTeamForm(AnchorPane anchorPane){
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



        //Korrigera nedan:
        formContainer.getChildren().add(teamNameBox);

        Button saveTeamButton = new Button("Save Team");
        saveTeamButton.getStyleClass().add("standardButton");
        saveTeamButton.setMinSize(160, 30);


        saveTeamButton.setOnAction(event -> {
            try {
                // Lägg in kontroll för annat än isEmpty.. t.ex (contains " ". elr möjligen fula ord.)
                if (teamNamefield.getText().isEmpty()) {
                    throw new IllegalArgumentException("Mandatory fields are empty.");
                }

                Team team = new Team(teamNamefield.getText());
                Label savedLabel = new Label("Team has successfully been saved to the database.");
                savedLabel.getStyleClass().add("standardLabel");
                formContainer.getChildren().add(savedLabel);

            } catch (IllegalArgumentException e) {
                AlertBox.displayAlertBox("Error", "Please fill in mandatory fields marked with \"*\".");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

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



}
