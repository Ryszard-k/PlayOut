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

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FootballEvent> footballEventsAuthor;
/*
    @OneToMany(mappedBy = "participants", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FootballEvent> footballEventsParticipants;
*/
}
