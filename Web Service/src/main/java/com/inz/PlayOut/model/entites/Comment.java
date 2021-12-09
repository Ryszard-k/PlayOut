package com.inz.PlayOut.model.entites;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalTime time;

    @NotNull
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "footballEvent")
    private FootballEvent footballEvent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basketballEvent")
    private BasketballEvent basketballEvent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volleyballEvent")
    private VolleyballEvent volleyballEvent;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author", nullable = false)
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

    public BasketballEvent getBasketballEvent() {
        return basketballEvent;
    }

    public void setBasketballEvent(BasketballEvent basketballEvent) {
        this.basketballEvent = basketballEvent;
    }

    public VolleyballEvent getVolleyballEvent() {
        return volleyballEvent;
    }

    public void setVolleyballEvent(VolleyballEvent volleyballEvent) {
        this.volleyballEvent = volleyballEvent;
    }

    public AppUser getAuthor() {
        return author;
    }

    public void setAuthor(AppUser author) {
        this.author = author;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        Comment that = (Comment) o;
        return id.equals(that.id) && text.equals(that.text) && footballEvent.equals(that.footballEvent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, footballEvent);
    }
}
