package com.inz.PlayOut.Model.Entites;

import com.inz.PlayOut.Model.SportEvent;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class FootballEvent extends SportEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "User_id", nullable = false)
    private AppUser author;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Participants_id")
    private AppUser participants;

    @OneToMany(mappedBy = "sportEvent", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments;

    protected FootballEvent(final Builder builder) {
        super(builder);
        this.author = builder.author;
        this.participants = builder.participants;
        this.comments = builder.comments;
    }

    public Long getId() {
        return id;
    }

    public AppUser getAuthor() {
        return author;
    }

    public AppUser getParticipants() {
        return participants;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends SportEvent.Builder<Builder> {
        private AppUser author;
        private AppUser participants;
        private Set<Comment> comments;

        public Builder author(final AppUser author) {
            this.author = author;
            return this;
        }

        public Builder participants(final AppUser participants) {
            this.participants = participants;
            return this;
        }

        public Builder comments(final Set<Comment> comments) {
            this.comments = comments;
            return this;
        }

        public FootballEvent build() {
            return new FootballEvent(this);
        }
    }
}
