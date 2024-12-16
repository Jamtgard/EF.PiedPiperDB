package com.example.piedpiperdb.DAO.JavaFXActions;

import com.example.piedpiperdb.DAO.MatchDAO;
import com.example.piedpiperdb.Entities.Game;
import com.example.piedpiperdb.Entities.Match;
import com.example.piedpiperdb.Entities.MatchType;

import java.time.LocalDate;

public class MatchActions {
    private static MatchDAO matchDAO = new MatchDAO();

    public static void addMatch(String matchName, MatchType selectedMatchType, LocalDate matchDate, Game selectedGame) {
        Match newMatch = new Match(matchName, selectedMatchType, matchDate, selectedGame);
        newMatch.setMatchDate(matchDate);
        matchDAO.saveMatch(newMatch);
    }

}
