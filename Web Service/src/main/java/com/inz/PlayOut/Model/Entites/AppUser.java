package com.inz.PlayOut.Model.Entites;

import com.inz.PlayOut.Model.Entites.FootballEvent;
import com.inz.PlayOut.Model.SportEvent;

import javax.persistence.*;
import java.util.Set;

@Entity
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FootballEvent> footballEventsAuthor;
/*
    @OneToMany(mappedBy = "participants", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FootballEvent> footballEventsParticipants;
*/

    public AppUser(String name) {
        this.name = name;
    }

    public AppUser() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<FootballEvent> getFootballEventsAuthor() {
        return footballEventsAuthor;
    }

    public void setFootballEventsAuthor(Set<FootballEvent> footballEventsAuthor) {
        this.footballEventsAuthor = footballEventsAuthor;
    }
}
