package com.example.piedpiperdb.Entities;

import jakarta.persistence.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

//GEFP-7-SA
@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private int gameId;//GEFP-22-SA, bytte namn på variablerna till gameId och gameName

    //GEFP-18-SA, satte längre längd på namnet
    @Column(name = "game_name",length = 70,nullable = false,unique = true)//GEFP-25-SA, la till unique så inte samma spelnamn längs in två gånger
    private String gameName;


    //GEFP-18-SA, ändrade från ManyToOne till OneToMany
    //GEFP-22-SA, ändra mapped by fån id -> game, teamId -> game
        //Ändra orphanRemoval till false
    //GEFP-22-SA, ändra från mappedBy game till gameId
    @OneToMany(mappedBy = "gameId", orphanRemoval = false,fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    private List<Match> matches = new ArrayList<>();

    @OneToMany(mappedBy = "gameId", orphanRemoval = false,fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    private List<Player> players = new ArrayList<>();


    @OneToMany(mappedBy = "gameId", orphanRemoval = false,fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    private List<Team> teams = new ArrayList<>();

    public Game() {

    }

    public Game(String gameName) {
        this.gameName = gameName;
    }

    public List<Match> getMatches() {return matches;}

    public void setMatches(List<Match> matches) {this.matches = matches;}

    public List<Player> getPlayers() {return players;}

    public void setPlayers(List<Player> players) {this.players = players;}

    public List<Team> getTeams() {return teams;}

    public void setTeams(List<Team> teams) {this.teams = teams;}

    public String getGameName() {return gameName;}

    public void setGameName(String game_name) {this.gameName = game_name;}

    public int getGameId() {return gameId;}

    public void setGameId(int game_id) {this.gameId = game_id;}

    //GEFP-26-SA
    public ObservableList<String> getPlayersNickNames(){
       ObservableList<String> nicknames = FXCollections.observableArrayList();
       for (Player player : players) {
           nicknames.add(player.getNickname());
       }
       return nicknames;
    }
    public ObservableList<String> getMatchNames(){
        ObservableList<String> matchesList = FXCollections.observableArrayList();
        for (Match match : matches) {
            matchesList.add(match.getMatchName());
        }
        return matchesList;
    }
    public ObservableList<String> getMatchDates(){
        ObservableList<String> matchesDateList = FXCollections.observableArrayList();
        for (Match match : matches) {
            matchesDateList.add(match.getMatchDate().toString());
        }
        return matchesDateList;
    }
}
