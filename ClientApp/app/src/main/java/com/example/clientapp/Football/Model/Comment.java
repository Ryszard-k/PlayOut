package com.example.clientapp.Football.Model;

import com.example.clientapp.BasketballEvent.Basketball;
import com.example.clientapp.VolleyballEvent.Volleyball;
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

public class Comment implements Serializable {

    @SerializedName("id")
    private Long id;

    @SerializedName("date")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;

    @SerializedName("time")
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonSerialize(using = LocalTimeSerializer.class)
    private LocalTime time;

    @SerializedName("time")
    private String text;

    @SerializedName("footballEvent")
    private FootballEvent footballEvent;

    @SerializedName("basketballEvent")
    private Basketball basketballEvent;

    @SerializedName("volleyballEvent")
    private Volleyball volleyballEvent;

    @SerializedName("author")
    private AppUser author;

    public Comment(LocalDate date, LocalTime time, String text, AppUser author) {
        this.date = date;
        this.time = time;
        this.text = text;
        this.author = author;
    }

    public Comment() {
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public FootballEvent getFootballEvent() {
        return footballEvent;
    }

    public void setFootballEvent(FootballEvent footballEvent) {
        this.footballEvent = footballEvent;
    }

    public AppUser getAuthor() {
        return author;
    }

    public void setAuthor(AppUser author) {
        this.author = author;
    }

    public Basketball getBasketballEvent() {
        return basketballEvent;
    }

    public void setBasketballEvent(Basketball basketballEvent) {
        this.basketballEvent = basketballEvent;
    }

    public Volleyball getVolleyballEvent() {
        return volleyballEvent;
    }

    public void setVolleyballEvent(Volleyball volleyballEvent) {
        this.volleyballEvent = volleyballEvent;
    }
}
