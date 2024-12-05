package com.example.piedpiperdb.Entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

// GEFP-10-SJ Commit #1
@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private int teamId;

    @Column(name = "team_name", length = 20, nullable = false, unique = true)
    private String teamName;


    @OneToMany(mappedBy = "teamId", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, orphanRemoval = false)
    private List<Player> listOfPlayersInTeam = new ArrayList<>();

    // -- Constructors --

    public Team (){}

    public Team (String teamName){
        this.teamName = teamName;
    }

    // -- Getters & Setters --

    //Local

    public int getId() {return teamId;}
    public void setId(int id) {this.teamId = teamId;}

    public String getTeamName() {return teamName;}
    public void setTeamName(String teamName) {this.teamName = teamName;}

    public List<Player> getListOfPlayersInTeam() {return listOfPlayersInTeam;}
    public void setListOfPlayersInTeam(List<Player> listOfPlayersInTeam) {this.listOfPlayersInTeam = listOfPlayersInTeam;}

    // Class wide

    /*
    public Game getGameId() {return gameId;}
    public void setGameId(Game gameId) {this.gameId = gameId;}

    public Match getMatchId() {return matchId;}
    public void setMatchId(Match matchId) {this.matchId = matchId;}
     */
}

