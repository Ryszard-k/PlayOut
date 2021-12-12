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
public class VolleyballEvent extends SportEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "User_id", nullable = false)
    @JsonIgnoreProperties({"footballEventsAuthor", "basketballEventsAuthor", "volleyballEventsAuthor", "comments",
            "footballEventsParticipants", "basketballEventsParticipants", "volleyballEventsParticipants"})
    private AppUser authorVolleyball;

    @ManyToMany(mappedBy = "volleyballEventsParticipants", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"footballEventsAuthor", "basketballEventsAuthor", "volleyballEventsAuthor", "comments",
            "footballEventsParticipants", "basketballEventsParticipants", "volleyballEventsParticipants"})
    private Set<AppUser> participantsVolleyball;

    @OneToMany(mappedBy = "volleyballEvent", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"volleyballEvent"})
    private Set<Comment> comments;

    public VolleyballEvent(LocalDate date, LocalTime time, double latitude, double longitude, EventLevel eventLevel, int vacancies, String note, String location, AppUser authorVolleyball) {
        super(date, time, latitude, longitude, eventLevel, vacancies, note, location);
        this.authorVolleyball = authorVolleyball;
    }

    public VolleyballEvent() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(o instanceof VolleyballEvent)) return false;
        VolleyballEvent that = (VolleyballEvent) o;
        return id.equals(that.id) && authorVolleyball.equals(that.authorVolleyball);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authorVolleyball);
    }
}
