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


    @Column(name = "first_name", length = 30, nullable = false)
    private String firstName;

    @Column(name = "last_name",length = 40, nullable = false)
    private String lastName;

    @Column(name = "nickname", length = 20, nullable = false, unique = true)
    private String nickname;

    @Column(name = "street_address", length = 100)
    private String streetAddress;

    @Column(name = "zip_code", length = 6)
    private String zipCode; //String för det brukar vara ett mellanrum i postnummer

    @Column(name = "city", length = 30)
    private String city;

    @Column(name = "country", length = 30)
    private String country;

    @Column(name = "email", length = 50,unique = true)
    private String email;


    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private Game gameId;

    //GEFP-12-AA
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id")
    private Team teamId;

    //GEFP-12-AA

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "match_id")
    private Match matchId;

    //-----------------------------------------------------
    //Constructors

    public Player() {

    }

    //GEFP-36-AA Tog bort flera konstruktorer och la till endast den med obligatoriska fält.
    public Player(String firstName, String lastName, String nickname, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.email = email;
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

    //GEFP-19-AA //GEFP-36-AA uppdaterade metoden StringBuilder
    @SuppressWarnings("unused") //Används i TableView genom reflektion och ger därför varning
    public String getFullAddress(){
        StringBuilder fullAddress = new StringBuilder();

        if (streetAddress != null && !streetAddress.isEmpty()){
            fullAddress.append(streetAddress).append("\n");
        }

        if (zipCode != null && !zipCode.isEmpty()){
            fullAddress.append(zipCode).append("\n");
        }

        if (city != null && !city.isEmpty()){
            fullAddress.append(city);
        }

        if (fullAddress.length() > 0){
            return fullAddress.toString();
        } else {
            return "No address registered";
        }
    }


    //GEFP-36-AA la till if-satsen för tydlighet i tabellen i view
    public String getCountry() {
        if (country == null || country.isEmpty()){
            return "No country registered";
        }
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
        return gameId !=null ? gameId.getGameName() : "No game registered";
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

    //GEFP-22-SA, var utkommenterat
    public Match getMatchId() {
        return matchId;
    }

    //GEFP-35-AA - Omskriven för att inkludera spelarens registrerade lags matcher.
    public String getMatchInfo(){
        String playerMatch = null;
        String teamMatch = null;
        StringBuilder matches = new StringBuilder();

        if (matchId != null) {
            String r = (matchId.getMatchResult() != null ? matchId.getMatchResult() : "Upcoming game");
            playerMatch = matchId.getMatchName() + ", " + matchId.getMatchDate() + "\nResult: " + r + "\n---\n";
            matches.append(playerMatch);
        }

        if (teamId != null && teamId.getMatchesInTeam() != null && !teamId.getMatchesInTeam().isEmpty()) {
            for (Match match : teamId.getMatchesInTeam()) {
                String r = (match.getMatchResult() != null ? match.getMatchResult() : "Upcoming game");
                teamMatch = match.getMatchName() + ", " + match.getMatchDate() + "\nResult: " + r + "\n---\n";
                matches.append(teamMatch);
            }
        }

        if (matches.length() > 0) {
            return matches.toString();
        } else {
            return "No matches registered";
        }
    }

    public void setMatchId(Match matchId) {
        this.matchId = matchId;
    }

    public String getMatchName(){
        return matchId !=null ? matchId.getMatchName() : "No match registered";
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nickname='" + nickname + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", email='" + email + '\'' +
                ", gameId=" + gameId +
                ", teamId=" + teamId +
                '}';
    }

}
