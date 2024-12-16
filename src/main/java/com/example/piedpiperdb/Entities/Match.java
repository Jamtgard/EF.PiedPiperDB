package com.example.piedpiperdb.Entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//AWS GEFP-3
@Entity
@Table (name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")//GEFP-18-SA
    private int matchId;


    @Enumerated(EnumType.STRING) //Enum som sträng i db
    @Column (name = "match_type", nullable = false)
    private MatchType matchType;       //player vs player, team vs team

    //GEFP-18-SA
    @Column(name = "match_name",length = 70, nullable = false)
    private String matchName;

    //AWS GEFP-3
    @Column (name = "date", nullable = false)
    private LocalDate matchDate;

    @Column (name = "match_result")
    private String matchResult;

    // ref till spel
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game gameId;//GEFP-22-SA, bytte namn från "game" till "gameId"

    // ref till player
    @OneToMany (mappedBy = "matchId", cascade = CascadeType.ALL, orphanRemoval = false)
    //@JoinColumn(name = "player_id", nullable = true)
    private List <Player> players = new ArrayList<>();

    //GEFP-22-SA
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @OrderColumn
    private List<Team> teams = new ArrayList<>();

    public Match() {
    }

    public Match(MatchType matchType) {
        this.matchType = matchType;
    }

    public Match(String matchName, MatchType selectedMatchType, LocalDate matchDate, Game gameId) {
        this.matchName = matchName;
        this.gameId = gameId;
        this.matchType = selectedMatchType;
        this.matchDate = matchDate;

    }

    // ------------------------------- GETTERS AND SETTERS --------------------------------------

    //AWS GEFP-3
    public int getMatchId() {
        return matchId;
    }
    public void setMatchId(int id) {
        this.matchId = id;
    }
    public MatchType getMatchType() {
        return matchType;
    }
    public void setMatchType(MatchType matchType) {
        this.matchType = matchType;
    }
    public LocalDate getMatchDate() {
        return matchDate;
    }
    public void setMatchDate(LocalDate matchDate) {
        this.matchDate = matchDate;
    }
    public String getMatchResult() {
        return matchResult;
    }
    public void setMatchResult(String result) {
        this.matchResult = result;
    }
    public String getGameName() {
        return gameId !=null ? gameId.getGameName() : "No Game Registered";
    }
    public Game getGameId() {
        return gameId;
    }
    public void setGameId(Game game) {
        this.gameId = game;
    }
    public List<Player> getPlayers() {
        return players;
    }
    public void setPlayers(List<Player> players) {
        this.players = players;
    }
    public List<Team> getTeams() {
        return teams;
    }
    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
    public String getMatchName() {
        return matchName;
    }
    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

}
