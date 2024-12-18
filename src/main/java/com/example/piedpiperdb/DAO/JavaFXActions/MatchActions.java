package com.example.piedpiperdb.DAO.JavaFXActions;

import com.example.piedpiperdb.DAO.MatchDAO;
import com.example.piedpiperdb.DAO.PlayerDAO;
import com.example.piedpiperdb.DAO.TeamDAO;
import com.example.piedpiperdb.DAO.GameDAO;
import com.example.piedpiperdb.Entities.*;
import com.example.piedpiperdb.View.AbstractScene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.List;


//GEFP-32-AWS
public class MatchActions {
    private static MatchDAO matchDAO = new MatchDAO();
    private static GameDAO gameDAO = new GameDAO();

    public static void addMatch(String matchName, MatchType selectedMatchType, LocalDate matchDate, Game selectedGame) {
        Match newMatch = new Match(matchName, selectedMatchType, matchDate, selectedGame);
        newMatch.setMatchDate(matchDate);
        matchDAO.saveMatch(newMatch);
    }

    public static boolean updateMatch(Match matchToUpdate, String newName, MatchType newType, LocalDate newDate, Game newGame, String result) {
        matchToUpdate.setMatchName(newName);
        matchToUpdate.setMatchType(newType);
        matchToUpdate.setMatchDate(newDate);
        matchToUpdate.setGameId(newGame);
        matchToUpdate.setMatchResult(result);
        matchDAO.updateMatch(matchToUpdate);
        return true;
    }

    public static boolean deleteMatchById(int matchId) {
        return matchDAO.deleteMatchById(matchId);
    }

    public static List<Match> getAllMatches() {
        return matchDAO.getAllMatches();
    }

}
