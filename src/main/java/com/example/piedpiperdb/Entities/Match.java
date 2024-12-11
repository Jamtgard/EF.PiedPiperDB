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

    @Column (nullable = false)
    @Enumerated(EnumType.STRING) //Enum som sträng i db
    private MatchType matchType;       //player vs player, team vs team

    //GEFP-18-SA
    @Column(name = "match_name",length = 70, nullable = false)
    private String matchName;

    //AWS GEFP-3

    @Column (name = "date", nullable = false)
    private LocalDate matchDate;

    private String result;

    // ref till spel
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game gameId;//GEFP-22-SA, bytte namn från "game" till "gameId"

    // ref till player
    @ManyToOne
    @JoinColumn(name = "player_id", nullable = true)
    private Player player;

    // ref till lag
    /* //GEFP-22-SA, kommentera ut och la till ManyToOne istället
    @ManyToOne
    @JoinColumn(name = "team_id", nullable = true)
    private Team team;*/

    //GEFP-22-SA
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @OrderColumn
    private List<Team> teams = new ArrayList<>();


    public Match() {
    }

    public Match(MatchType matchType) {
        this.matchType = matchType;
    }

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
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public Game getGameId() {
        return gameId;
    }
    public void setGameId(Game game) {
        this.gameId = game;
    }
    //GEFP-22-SA, var utkommenterat
    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    /*
    public Team getTeam() {
        return team;
    }
    public void setTeam(Team team) {
        this.team = team;
    }*/

    //GEFP-22-SA, var utkommenterat
    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    //GEFP-22-SA, la till getter och setter för MatchName
    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }
}
