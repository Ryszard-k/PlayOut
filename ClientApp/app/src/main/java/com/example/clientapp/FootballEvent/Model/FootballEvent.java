package com.example.clientapp.FootballEvent.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public class FootballEvent {

    @SerializedName("date")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate date;

    @SerializedName("time")
    @JsonDeserialize(using = LocalTimeDeserializer.class)
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

    @SerializedName("author")
    @JsonIgnoreProperties("footballEventsAuthor")
    private AppUser author;

    @SerializedName("participants")
    @JsonIgnoreProperties("footballEventsParticipants")
    private Set<AppUser> participants;

    @SerializedName("comments")
    private Set<Comment> comments;

    public FootballEvent() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public AppUser getAuthor() {
        return author;
    }

    public void setAuthor(AppUser author) {
        this.author = author;
    }

    public Set<AppUser> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<AppUser> participants) {
        this.participants = participants;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
