package com.inz.PlayOut.Model.Entites;

import com.inz.PlayOut.Model.SportEvent;

import javax.persistence.*;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SportEvent_id", nullable = false)
    private SportEvent sportEvent;
}
