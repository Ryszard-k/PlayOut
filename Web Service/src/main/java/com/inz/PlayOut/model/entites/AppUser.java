package com.inz.PlayOut.model.entites;

import com.fasterxml.jackson.annotation.*;
import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
public class AppUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String username;

    private String password;

    @NotNull
    @Email
    private String email;

    @OneToMany(mappedBy = "author",
            cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("author")
    private Set<FootballEvent> footballEventsAuthor;

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "Appuser_FootballEvent",
            joinColumns = { @JoinColumn(name = "Appuser_id") },
            inverseJoinColumns = { @JoinColumn(name = "FootballEvent_id") }
    )
    private Set<FootballEvent> footballEventsParticipants;

    public AppUser(String name, String password, String email) {
        this.username = name;
        this.password = password;
        this.email = email;
    }

    public AppUser() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<FootballEvent> getFootballEventsAuthor() {
        return footballEventsAuthor;
    }

    public void setFootballEventsAuthor(Set<FootballEvent> footballEventsAuthor) {
        this.footballEventsAuthor = footballEventsAuthor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<FootballEvent> getFootballEventsParticipants() {
        return footballEventsParticipants;
    }

    public void setFootballEventsParticipants(Set<FootballEvent> footballEventsParticipants) {
        this.footballEventsParticipants = footballEventsParticipants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppUser appUser)) return false;
        return id.equals(appUser.id) && username.equals(appUser.username) && Objects.equals(password, appUser.password) && email.equals(appUser.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, email);
    }
}
