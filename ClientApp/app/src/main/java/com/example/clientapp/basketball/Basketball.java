package com.example.clientapp.basketball;

import com.example.clientapp.footballEvent.model.AppUser;
import com.example.clientapp.footballEvent.model.Comment;
import com.example.clientapp.footballEvent.model.EventLevel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

public class Basketball {

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

    @SerializedName("authorBasketball")
    @JsonIgnoreProperties({"footballEventsParticipants", "basketballEventsParticipants", "volleyballEventsParticipants"})
    private AppUser authorBasketball;

    @SerializedName("participantsBasketball")
    @JsonIgnoreProperties({"footballEventsAuthor", "basketballEventsAuthor", "volleyballEventsAuthor"})
    private Set<AppUser> participantsBasketball;

    @SerializedName("comments")
    private Set<Comment> comments;

    public Basketball() {
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

    public AppUser getAuthorBasketball() {
        return authorBasketball;
    }

    public void setAuthorBasketball(AppUser authorBasketball) {
        this.authorBasketball = authorBasketball;
    }

    public Set<AppUser> getParticipantsBasketball() {
        return participantsBasketball;
    }

    public void setParticipantsBasketball(Set<AppUser> participantsBasketball) {
        this.participantsBasketball = participantsBasketball;
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
        if (!(o instanceof Basketball)) return false;
        Basketball that = (Basketball) o;
        return id.equals(that.id) && authorBasketball.equals(that.authorBasketball);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authorBasketball);
    }
}
