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

    @Column(name = "team_name", length = 70, nullable = false, unique = true)
    private String teamName;

//GEFP-15-SJ - Start:
                    //GEFPP-18-SA: ändrade player_id till teamId
    @OneToMany(mappedBy = "teamId", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, orphanRemoval = false)
    private List<Player> listOfPlayersInTeam = new ArrayList<>();

//GEFP-15-SJ - Slut.

    // Constructors

    public Team (){}

    public Team (String teamName){
        this.teamName = teamName;
    }

    // Getters & Setters

    public int getId() {return teamId;}
    public void setId(int id) {this.teamId = teamId;}

    public String getTeamName() {return teamName;}
    public void setTeamName(String teamName) {this.teamName = teamName;}

    public List<Player> getListOfPlayersInTeam() {return listOfPlayersInTeam;}
    public void setListOfPlayersInTeam(List<Player> listOfPlayersInTeam) {this.listOfPlayersInTeam = listOfPlayersInTeam;}

    /*
    public int getGame_id() {return game_id;}
    public void setGame_id(int game_id) {this.game_id = game_id;}

    public Match getMatch_id() {return match_id;}
    public void setMatch_id(Match match_id) {this.match_id = match_id;}
     */

}

