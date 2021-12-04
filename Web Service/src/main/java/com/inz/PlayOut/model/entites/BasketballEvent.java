package com.inz.PlayOut.model.entites;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.inz.PlayOut.model.EventLevel;
import com.inz.PlayOut.model.SportEvent;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

@Entity
public class BasketballEvent extends SportEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "User_id", nullable = false)
    @JsonIgnoreProperties({"footballEventsAuthor", "basketballEventsAuthor", "volleyballEventsAuthor"})
    private AppUser authorBasketball;

    @ManyToMany(mappedBy = "basketballEventsParticipants", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"footballEventsParticipants", "basketballEventsParticipants", "volleyballEventsParticipants"})
    private Set<AppUser> participantsBasketball;

    @OneToMany(mappedBy = "basketballEvent", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments;

    public BasketballEvent(LocalDate date, LocalTime time, double latitude, double longitude, EventLevel eventLevel, int vacancies, String note, String location, AppUser authorBasketball) {
        super(date, time, latitude, longitude, eventLevel, vacancies, note, location);
        this.authorBasketball = authorBasketball;
    }

    public BasketballEvent() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(o instanceof BasketballEvent)) return false;
        BasketballEvent that = (BasketballEvent) o;
        return id.equals(that.id) && authorBasketball.equals(that.authorBasketball);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authorBasketball);
    }
}
