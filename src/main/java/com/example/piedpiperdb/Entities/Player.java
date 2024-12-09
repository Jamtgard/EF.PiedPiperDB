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


    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "street_address",nullable = false)
    private String streetAddress;

    @Column(name = "zip_code", nullable = false)
    private String zipCode; //String för det brukar vara ett mellanrum i postnummer

    @Column(name = "city",nullable = false)
    private String city;

    @Column(name = "country",nullable = false)
    private String country;

    @Column(name = "email", unique = true)
    private String email;


    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game gameId;

    //GEFP-12-AA
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team teamId;

    //GEFP-12-AA

   /* @ManyToOne
    @JoinColumn(name = "match_id")
    private Match matchId;
*/
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

    //GEFP-12-AA
    public Player(String firstName, String lastName, String nickname, String streetAddress, String zipCode, String city, String country, String email, Game gameId, Team teamId, Match matchId) {
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
        /*this.matchId = matchId;*/
    }

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

    //GEFP-19-AA
    public String getFullName(){
        return firstName + " " + lastName;
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

    //GEFP-19-AA
    public String getFullAddress(){
        return streetAddress + "\n" + zipCode + "\n" + city;
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

    public Game getGameId() {
        return gameId;
    }

    public String getGameName(){
        return gameId !=null ? gameId.getGame_name() : "No game registered";
    }

    public void setGameId(Game gameId) {
        this.gameId = gameId;
    }

    public Team getTeamId() {
        return teamId;
    }

    public String getTeamName(){
        return teamId !=null ? teamId.getTeamName() : "No team registered";
    }

    public void setTeamId(Team teamId) {
        this.teamId = teamId;
    }

    /*public Match getMatchId() {
        return matchId;
    }

    public String getMatchName(){
        return matchId !=null ? matchId.getMatchName() : "No match registered";
    }

    public void setMatchId(Match matchId) {
        this.matchId = matchId;
    }*/
}
