package com.example.clientapp.VolleyballEvent;

import com.example.clientapp.Football.Model.AppUser;
import com.example.clientapp.Football.Model.Comment;
import com.example.clientapp.Football.Model.EventLevel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

public class Volleyball implements Serializable {

    @SerializedName("date")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;

    @SerializedName("time")
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonSerialize(using = LocalTimeSerializer.class)
    private LocalTime time;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("eventLevel")
    private EventLevel eventLevel;

    @SerializedName("vacancies")
    private int vacancies;

    @SerializedName("note")
    private String note;

    @SerializedName("id")
    private Long id;

    @SerializedName("location")
    private String location;

    @SerializedName("authorVolleyball")
    @JsonIgnoreProperties({"footballEventsParticipants", "basketballEventsParticipants", "volleyballEventsParticipants"})
    private AppUser authorVolleyball;

    @SerializedName("participantsVolleyball")
    @JsonIgnoreProperties({"footballEventsAuthor", "basketballEventsAuthor", "volleyballEventsAuthor"})
    private Set<AppUser> participantsVolleyball;

    @SerializedName("comments")
    private Set<Comment> comments;

    public Volleyball() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public EventLevel getEventLevel() {
        return eventLevel;
    }

    public void setEventLevel(EventLevel eventLevel) {
        this.eventLevel = eventLevel;
    }

    public int getVacancies() {
        return vacancies;
    }

    public void setVacancies(int vacancies) {
        this.vacancies = vacancies;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public AppUser getAuthorVolleyball() {
        return authorVolleyball;
    }

    public void setAuthorVolleyball(AppUser authorVolleyball) {
        this.authorVolleyball = authorVolleyball;
    }

    public Set<AppUser> getParticipantsVolleyball() {
        return participantsVolleyball;
    }

    public void setParticipantsVolleyball(Set<AppUser> participantsVolleyball) {
        this.participantsVolleyball = participantsVolleyball;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Volleyball)) return false;
        Volleyball that = (Volleyball) o;
        return id.equals(that.id) && authorVolleyball.equals(that.authorVolleyball);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authorVolleyball);
    }
}
