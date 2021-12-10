package com.example.clientapp.Football.Model;

import com.example.clientapp.BasketballEvent.Basketball;
import com.example.clientapp.VolleyballEvent.Volleyball;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Set;

public class AppUser implements Serializable {

    @SerializedName("id")
    private Long id;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("email")
    private String email;

    @SerializedName("footballEventsAuthor")
    @JsonIgnoreProperties("author")
    private Set<FootballEvent> footballEventsAuthor;

    @SerializedName("footballEventsParticipants")
    @JsonIgnoreProperties("participants")
    private Set<FootballEvent> footballEventsParticipants;

    @SerializedName("basketballEventsAuthor")
    @JsonIgnoreProperties("authorBasketball")
    private Set<Basketball> basketballEventsAuthor;

    @SerializedName("basketballEventsParticipants")
    @JsonIgnoreProperties("participantsBasketball")
    private Set<Basketball> basketballEventsParticipants;

    @SerializedName("volleyballEventsAuthor")
    @JsonIgnoreProperties("authorVolleyball")
    private Set<Volleyball> volleyballEventsAuthor;

    @SerializedName("volleyballEventsParticipants")
    @JsonIgnoreProperties("participantsVolleyball")
    private Set<Volleyball> volleyballEventsParticipants;

    @SerializedName("comments")
    @JsonIgnoreProperties("author")
    private Set<Comment> comments;

    public AppUser() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<FootballEvent> getFootballEventsAuthor() {
        return footballEventsAuthor;
    }

    public void setFootballEventsAuthor(Set<FootballEvent> footballEventsAuthor) {
        this.footballEventsAuthor = footballEventsAuthor;
    }

    public Set<FootballEvent> getFootballEventsParticipants() {
        return footballEventsParticipants;
    }

    public void setFootballEventsParticipants(Set<FootballEvent> footballEventsParticipants) {
        this.footballEventsParticipants = footballEventsParticipants;
    }

    public Set<Basketball> getBasketballEventsAuthor() {
        return basketballEventsAuthor;
    }

    public void setBasketballEventsAuthor(Set<Basketball> basketballEventsAuthor) {
        this.basketballEventsAuthor = basketballEventsAuthor;
    }

    public Set<Basketball> getBasketballEventsParticipants() {
        return basketballEventsParticipants;
    }

    public void setBasketballEventsParticipants(Set<Basketball> basketballEventsParticipants) {
        this.basketballEventsParticipants = basketballEventsParticipants;
    }

    public Set<Volleyball> getVolleyballEventsAuthor() {
        return volleyballEventsAuthor;
    }

    public void setVolleyballEventsAuthor(Set<Volleyball> volleyballEventsAuthor) {
        this.volleyballEventsAuthor = volleyballEventsAuthor;
    }

    public Set<Volleyball> getVolleyballEventsParticipants() {
        return volleyballEventsParticipants;
    }

    public void setVolleyballEventsParticipants(Set<Volleyball> volleyballEventsParticipants) {
        this.volleyballEventsParticipants = volleyballEventsParticipants;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
