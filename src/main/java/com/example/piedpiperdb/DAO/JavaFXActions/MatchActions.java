package com.example.piedpiperdb.DAO.JavaFXActions;

import com.example.piedpiperdb.DAO.MatchDAO;
import com.example.piedpiperdb.DAO.GameDAO;
import com.example.piedpiperdb.Entities.*;


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

    public static boolean updateMatch(Match matchToUpdate, String newName, MatchType newType, LocalDate newDate, Game newGame, String selectedWinner) {
        matchToUpdate.setMatchName(newName);
        matchToUpdate.setMatchType(newType);
        matchToUpdate.setMatchDate(newDate);
        matchToUpdate.setGameId(newGame);

        matchToUpdate.setMatchResult(selectedWinner);

        matchDAO.updateMatch(matchToUpdate);
        return true;
    }

    public static boolean deleteMatchById(int matchId) {
        return matchDAO.deleteMatchById(matchId);
    }

    public static List<Match> getAllMatches() {
        return matchDAO.getAllMatches();
    }

    public static List<Match> getDecidedMatches() {
        return matchDAO.getAllMatches().stream()
                .filter(Match::isMatchDecided)
                .toList();
    }

    public static List<Match> getUpcomingMatches() {
        return matchDAO.getAllMatches().stream()
                .filter(match -> !match.isMatchDecided())
                .toList();
    }
}
