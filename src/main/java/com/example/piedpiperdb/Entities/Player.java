package com.example.piedpiperdb.Entities;

import jakarta.persistence.*;

//GEFP-8-AA
@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private int id;

    @Column(name = "first_name", length = 40, nullable = false)
    private String firstName;

    @Column(name = "last_name",length = 50, nullable = false)
    private String lastName;

    @Column(name = "nickname", length = 20, nullable = false, unique = true)
    private String nickname;

    @Column(name = "street_address")
    private String streetAddress;

    @Column(name = "zip_code")
    private String zipCode; //String för det brukar vara ett mellanrum i postnummer

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "email", length = 50, unique = true)
    private String email;

/*    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game gameId;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team teamId;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match matchId;*/

    //-----------------------------------------------------
    //Constructors

    public Player() {

    }

    public Player(String firstName, String lastName, String nickname) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
    }

    public Player(String firstName, String lastName, String nickname, String streetAddress, String zipCode, String city, String country, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.streetAddress = streetAddress;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
        this.email = email;
    }

/*    public Player(String firstName, String lastName, String nickname, String streetAddress, String zipCode, String city, String country, String email, Game gameId, Team teamId, Match matchId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.streetAddress = streetAddress;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
        this.email = email;
        this.gameId = gameId;
        this.teamId = teamId;
        this.matchId = matchId;
    }*/

//------------------------------------------------------
    //Getters & Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

/*    public Game getGameId() {
        return gameId;
    }

    public void setGameId(Game gameId) {
        this.gameId = gameId;
    }

    public Team getTeamId() {
        return teamId;
    }

    public void setTeamId(Team teamId) {
        this.teamId = teamId;
    }

    public Match getMatchId() {
        return matchId;
    }

    public void setMatchId(Match matchId) {
        this.matchId = matchId;
    }*/
}
