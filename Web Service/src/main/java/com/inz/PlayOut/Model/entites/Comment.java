package com.inz.PlayOut.Model.entites;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String text;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FootballEvent_id", nullable = false)
    private FootballEvent footballEvent;
}
