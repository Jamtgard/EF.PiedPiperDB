package com.example.piedpiperdb.DAO.JavaFXActions;

import com.example.piedpiperdb.DAO.TeamDAO;
import com.example.piedpiperdb.Entities.Team;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

//GEFP-27-SJ
public class TeamActions {

    private static TeamDAO teamDAO = new TeamDAO();

    public static ObservableList<Team> getAllTeams() {
        ObservableList<Team> teams = FXCollections.observableArrayList(teamDAO.getAllTeams());
        return teams;
    }

    public static ObservableList<String> getAllTeamNames() {
        ObservableList<Team> teams = getAllTeams();
        ObservableList<String> teamNames = FXCollections.observableArrayList();

        for (Team team : teams) {
            teamNames.add(team.getTeamName());
        }

        return teamNames;
    }

    public static ListView teamListView (ListView teamListView){

        return teamListView;
    }

}
