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

//GEFP-21-SJ - Start:
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game gameId;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match matchId;
//GEFP-21-SJ - Slut:

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

    public Game getGameId() {return gameId;}
    public void setGameId(Game gameId) {this.gameId = gameId;}

    public Match getMatchId() {return matchId;}
    public void setMatchId(Match matchId) {this.matchId = matchId;}

    public List<Player> getListOfPlayersInTeam() {return listOfPlayersInTeam;}
    public void setListOfPlayersInTeam(List<Player> listOfPlayersInTeam) {this.listOfPlayersInTeam = listOfPlayersInTeam;}

    @Override
    public String toString() {
        return "Team{" +
                "teamId=" + teamId +
                ", teamName='" + teamName + '\'' +
                ", gameId=" + gameId +
                ", matchId=" + matchId +
                ", listOfPlayersInTeam=" + listOfPlayersInTeam +
                '}';
    }
}

