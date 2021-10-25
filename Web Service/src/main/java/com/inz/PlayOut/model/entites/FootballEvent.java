package com.inz.PlayOut.model.entites;

import com.fasterxml.jackson.annotation.*;
import com.inz.PlayOut.model.EventLevel;
import com.inz.PlayOut.model.SportEvent;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

@Entity
public class FootballEvent extends SportEvent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "User_id", nullable = false)
    @JsonIgnoreProperties("footballEventsAuthor")
    private AppUser author;

    @ManyToMany(mappedBy = "footballEventsParticipants", fetch = FetchType.EAGER)
    private Set<AppUser> participants;

    @OneToMany(mappedBy = "footballEvent", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments;

    public FootballEvent(LocalDate date, LocalTime time, double latitude, double longitude, EventLevel eventLevel, int vacancies, AppUser author) {
        super(date, time, latitude, longitude, eventLevel, vacancies);
        this.author = author;
    }

    /*  public FootballEvent(LocalDate date, LocalTime time, double latitude, double longitude, AppUser author) {
        super(date, time, latitude, longitude);
        this.author = author;
    }*/

    public FootballEvent() {
    }

    public Long getId() {
        return id;
    }

    public AppUser getAuthor() {
        return author;
    }

    public void setAuthor(AppUser author) {
        this.author = author;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<AppUser> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<AppUser> participants) {
        this.participants = participants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FootballEvent)) return false;
        FootballEvent that = (FootballEvent) o;
        return id.equals(that.id) && Objects.equals(author, that.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author);
    }
}
