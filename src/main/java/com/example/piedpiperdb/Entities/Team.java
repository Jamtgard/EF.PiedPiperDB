package com.example.piedpiperdb.Entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

// GEFP-10-SJ Commit #1
@Entity
@Table(name = "Teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private int id;

    @Column(name = "team_name", length = 20, nullable = false, unique = true)
    private String teamName;

    /*
    @OneToMany(mappedBy = "playerId", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn (name = "players", nullable = false)
    private List<Player> listOfPlayersInTeam = new ArrayList<>();
     */

    // Constructors

    public Team (){}

    public Team (String teamName){
        this.teamName = teamName;
    }

    // Getters & Setters

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getTeamName() {return teamName;}
    public void setTeamName(String teamName) {this.teamName = teamName;}

    /*
    public List<Player> getListOfPlayersInTeam() {return listOfPlayersInTeam;}
    public void setListOfPlayersInTeam(List<Player> listOfPlayersInTeam) {this.listOfPlayersInTeam = listOfPlayersInTeam;}

    public Game getGameId() {return gameId;}
    public void setGameId(Game gameId) {this.gameId = gameId;}

    public Team getTeamId() {return teamId;}
    public void setTeamId(Team teamId) {this.teamId = teamId;}

    public Match getMatchId() {return matchId;}
    public void setMatchId(Match matchId) {this.matchId = matchId;}
     */
}

