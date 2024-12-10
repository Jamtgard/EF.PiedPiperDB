package com.example.piedpiperdb.View;

import com.example.piedpiperdb.DAO.GameDAO;
import com.example.piedpiperdb.DAO.TeamDAO;
import com.example.piedpiperdb.Entities.Game;
import com.example.piedpiperdb.Entities.Team;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

//GEFP-21-SJ
public class TeamView extends AbstractScene{

    private static TeamDAO teamDAO = new TeamDAO();
    private static GameDAO gameDAO = new GameDAO();

    public static Scene teamScene(Stage window){

        Scene baseScene = AbstractScene.getScene(window);

        AnchorPane anchorPane = AbstractScene.anchorPane;
        VBox vBox = AbstractScene.leftVbox;

        addCustomComponents(vBox);

        AnchorPane rootPane = (AnchorPane) baseScene.getRoot();

        return baseScene;

        /*
        AnchorPane anchorPane = AbstractScene.anchorPane;
        VBox leftVbox = AbstractScene.leftVbox;

        //addCustomComponents(anchorPane);


        AnchorPane teamRoot = (AnchorPane) firstScene.getRoot();

        return firstScene;
         */

    }

    private static void addCustomComponents(VBox vbox) {

        List<Game> listOfGames = gameDAO.getAllGames();
        List<CheckBox> listOfCheckboxes = new ArrayList<>();

        for (Game game : listOfGames) {
            String gameName = game.getGame_name();
            int gameId = game.getGame_id();
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

        Button selectedTeamButton = new Button("Show Teams from \nselected Game \\ Games");
        selectedTeamButton.getStyleClass().add("standardButton");
        selectedTeamButton.setMinSize(160, 30);
        selectedTeamButton.setOnAction(event -> {
            List<String> listSelection = ConfrimBox.displayCheckBoxOptions("Select Team \\ Teams", listOfCheckboxes);
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
        });

        Button addNewTeamButton = new Button("Add New Team");
        addNewTeamButton.getStyleClass().add("standardButton");
        addNewTeamButton.setMinSize(160, 30);
        addNewTeamButton.setOnAction(event -> {
            showAddTeamForm(anchorPane);
        });
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
        team_id.setCellValueFactory(new PropertyValueFactory<>("team_id"));

        tableView.getColumns().add(team_id);
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

        formContainer.getChildren().add(teamNameBox);

        Button saveTeamButton = new Button("Save Team");
        saveTeamButton.getStyleClass().add("standardButton");
        saveTeamButton.setMinSize(160, 30);

        // Version 1:
        /*
        saveTeamButton.setOnAction(event -> {

            Team team = null;

            if (teamNamefield.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Please fill in mandatory fields marked with \"*\".");
                alert.show();

            } else {
                try {
                    team = new Team(teamNamefield.getText());
                    Label savedLabel = new Label("Team has successfully been saved to the database.");
                    savedLabel.getStyleClass().add("standardLabel");
                    formContainer.getChildren().add(savedLabel);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
            TeamDAO newTeam = new TeamDAO();
            newTeam.createTeam(team);
            System.out.println(team.toString());
        });
        */

        // Version 2:
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
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Please fill in mandatory fields marked with \"*\".");
                alert.show();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

        formContainer.getChildren().add(saveTeamButton);
        anchorPane.getChildren().add(formContainer);

    }

    private static void showUpdateTeamForm(AnchorPane anchorPane){

    }

}
