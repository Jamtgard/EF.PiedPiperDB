package com.example.piedpiperdb.Entities;

import jakarta.persistence.*;

//Anna R
@Entity
@Table(name = "staff")
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, name = "first_name")
    private String firstName;

    @Column(nullable = false, name = "last_name")
    private String lastName;

    @Column(nullable = false, name = "nickname")
    private String nickname;

    @Column(nullable = false, name = "street_adress")
    private String streetAdress;

    @Column(nullable = false, name = "postal_code")
    private String postalCode;

    @Column(nullable = false, name = "city")
    private String city;

    @Column(nullable = false, name = "country")
    private String country;

    @Column(nullable = false, name = "email")
    private String email;

    public Staff(String firstName, String lastName, String nickname, String streetAdress, String postalCode, String city, String country, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.streetAdress = streetAdress;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.email = email;
    }
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
    public String getStreetAdress() {
        return streetAdress;
    }
    public void setStreetAdress(String streetAdress) {
        this.streetAdress = streetAdress;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
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

    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nickname='" + nickname + '\'' +
                ", streetAddress='" + streetAdress + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}