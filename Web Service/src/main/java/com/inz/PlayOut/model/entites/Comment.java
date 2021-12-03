package com.inz.PlayOut.model.entites;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Objects;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "BasketballEvent", nullable = false)
    private BasketballEvent basketballEvent;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "VolleyballEvent", nullable = false)
    private VolleyballEvent volleyballEvent;

    public Comment(String text) {
        this.text = text;
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
