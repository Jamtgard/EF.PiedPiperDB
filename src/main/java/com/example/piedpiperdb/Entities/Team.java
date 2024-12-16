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

    //GEFP-22-SA
    @ManyToOne
    @JoinColumn(name = "game_id",nullable = true)//GEFP-22-SA, ändra från gameId till game_id
    private Game gameId;//GEFP-22-SA, ändra från game till gameId

    @ManyToOne
    @JoinColumn (name = "match_id")
    private Match matchId;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Match>matchesInTeam = new ArrayList<>();


    //GEFP-10-SJ
    // Constructors

    public Team (){}

    public Team (String teamName){
        this.teamName = teamName;
    }

    // Getters & Setters

    public int getTeamId() {return teamId;}
    public void setTeamId(int teamId) {this.teamId = teamId;}

    public String getTeamName() {return teamName;}
    public void setTeamName(String teamName) {this.teamName = teamName;}

    public List<Player> getListOfPlayersInTeam() {return listOfPlayersInTeam;}
    public void setListOfPlayersInTeam(List<Player> listOfPlayersInTeam) {this.listOfPlayersInTeam = listOfPlayersInTeam;}

    // Player

    //public Player getPlayer (int playerId) {}

    // Game

    public Game getGameId() {return gameId;}
    public void setGameId(Game game) {this.gameId = game;}

    public String getGameName() { return gameId != null ? gameId.getGameName() : "-n-"; }

    // Match

    /* - SJ: Behövs en List?
    public List<Match> getMatchesInTeam() {return matchesInTeam;}
    public void setMatchesInTeam(List<Match> matchesInTeam) {this.matchesInTeam = matchesInTeam;}
     */

    public Match getMatchId() { return matchId; }

    public String getMatchInfo (){
        if (matchId != null) {
            return "-n-";
        }
        String result = (matchId.getResult() != null ? matchId.getResult() : "Upcoming Match");
        return matchId.getMatchName() + "\n" + matchId.getMatchDate() + "\nResult: " + result;
    }

    // ToString


    @Override
    public String toString() {
        return "Team{" +
                "teamId=" + teamId +
                ", teamName='" + teamName + '\'' +
                ", listOfPlayersInTeam=" + listOfPlayersInTeam +
                ", gameId=" + gameId +
                ", matchId=" + matchId +
                ", matchesInTeam=" + matchesInTeam +
                '}';
    }
}

