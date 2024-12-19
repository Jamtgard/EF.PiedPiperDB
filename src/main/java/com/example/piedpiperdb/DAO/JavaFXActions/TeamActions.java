package com.example.piedpiperdb.DAO.JavaFXActions;

import com.example.piedpiperdb.DAO.GameDAO;
import com.example.piedpiperdb.DAO.MatchDAO;
import com.example.piedpiperdb.DAO.PlayerDAO;
import com.example.piedpiperdb.DAO.TeamDAO;
import com.example.piedpiperdb.Entities.Game;
import com.example.piedpiperdb.Entities.Player;
import com.example.piedpiperdb.Entities.Team;
import com.example.piedpiperdb.View.AlertBox;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//GEFP-27-SJ
public class TeamActions {

    private static TeamDAO teamDAO = new TeamDAO();
    private static PlayerDAO playerDAO = new PlayerDAO();
    private static GameDAO gameDAO = new GameDAO();
    private static MatchDAO matchDAO = new MatchDAO();

    //public TeamActions(TeamDAO teamDAO) {this.teamDAO = teamDAO;}


//TableView
//----------------------------------------------------------------------------------------------------------------------

    public static VBox getTableViewAllTeams(VBox vBox) {
        List<Team> teams = teamDAO.getAllTeams();
        return showTable(vBox, teams);
    }

    public static VBox getTableViewSelectedTeams (VBox vBox, List<String> selections){

        List<Integer> ids = new ArrayList<>();

        for (String selection : selections) {
            try {
                String[] parts = selection.split(",");
                String lastPart = parts[parts.length - 1];
                int id = Integer.parseInt(lastPart);
                ids.add(id);
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
                System.out.println("Could not parse id: " + selection);
            }
            System.out.println("IDs to query: " + ids);
        }
        List<Team> teams = teamDAO.getTeamsByGame(ids);
        //System.out.println(teams.size());
        return showTable(vBox, teams);
    }

    public static List<CheckBox> gameCheckBoxes (){
        List<Game> games = gameDAO.getAllGames();
        List<CheckBox> checkBoxes = new ArrayList<>();

        for (Game game : games) {
            String gameName = game.getGameName();
            int gameId = game.getGameId();

            CheckBox checkBox = new CheckBox(gameName + ", Game ID: " + gameId);
            checkBoxes.add(checkBox);
        }
        return checkBoxes;
    }

// Show Table & Create Team Table
//----------------------------------------------------------------------------------------------------------------------

    public static VBox showTable(VBox vBox, List<Team> teams){

        vBox.getStyleClass().add("textFieldOne");
        TableView<Team> table = createTeamTable(teams);
        table.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        VBox.setVgrow(table, Priority.ALWAYS);
        vBox.getChildren().add(table);
        return vBox;

    }

    private static TableView<Team> createTeamTable(List<Team> teams){
        ObservableList<Team> teamsObservableList = FXCollections.observableArrayList(teams);

        TableView<Team> tableView = new TableView<>();

        TableColumn<Team, String> team_id = new TableColumn<>("Team ID");
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

// CRUDs (Team Actions for FX)
//----------------------------------------------------------------------------------------------------------------------

    //Create
    public  static void createTeam(Team team){teamDAO.createTeam(team);}
    public static Team createTeamFromFieldsInput (String teamName){
        // WIP - add funtion to add game and players
        Team team = new Team(teamName);
        return team;
    }

    //Read
    public static Team getTeamById(int teamId){return teamDAO.getTeamById(teamId);}
    public static List<Team> getTeamsByGame (int gameId){
        return teamDAO.getTeamsByGame(List.of(gameId));
    }

    //Update
    public static void updateTeam(Team team){teamDAO.updateTeam(team);}

    //Delete
    public static void deleteTeam(Team team){teamDAO.deleteTeam(team);}
    public static boolean deleteTeamById(int teamId){return teamDAO.deleteTeamById(teamId);}

    // Checkers - Booleans
    public static boolean isTeamNameUnique(String teamName){return teamDAO.isTeamNameUnique(teamName);}
    public static boolean isFieldEmpty(String teamName){return teamName.isEmpty();}

// Get Actions
//----------------------------------------------------------------------------------------------------------------------

    public static List<Game> getAllGames(){return gameDAO.getAllGames();}






}
