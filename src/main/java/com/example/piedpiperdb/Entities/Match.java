package com.example.piedpiperdb.Entities;

import jakarta.persistence.*;
import java.time.LocalDate;

//AWS GEFP-3
@Entity
@Table (name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    // ref till player
    @ManyToOne
    @JoinColumn(name = "player_id", nullable = true)
    private Player player;

    // ref till lag
    @ManyToOne
    @JoinColumn(name = "team_id", nullable = true)
    private Team team;

    public Match() {
    }

    public Match(MatchType matchType) {
        this.matchType = matchType;
    }

    //AWS GEFP-3
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
/*    public Game getGame() {
        return game;
    }
    public void setGame(Game game) {
        this.game = game;
    }
    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public Team getTeam() {
        return team;
    }
    public void setTeam(Team team) {
        this.team = team;
    }*/
}
