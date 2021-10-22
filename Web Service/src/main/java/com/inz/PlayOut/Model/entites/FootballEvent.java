package com.inz.PlayOut.Model.entites;

import com.inz.PlayOut.Model.SportEvent;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Entity
public class FootballEvent extends SportEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "User_id", nullable = false)
    private AppUser author;

    @ManyToMany(mappedBy = "footballEventsParticipants")
    private Set<AppUser> participants;

    @OneToMany(mappedBy = "footballEvent", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments;

    public FootballEvent(LocalDate date, LocalTime time, double latitude, double longitude, AppUser author) {
        super(date, time, latitude, longitude);
        this.author = author;
    }

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
}
