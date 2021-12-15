package com.inz.PlayOut.model.entites;

import com.fasterxml.jackson.annotation.*;
import com.sun.istack.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Entity
public class AppUser implements Serializable, UserDetails {

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
    @JsonIgnoreProperties({"author", "comments"})
    private Set<FootballEvent> footballEventsAuthor;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "Appuser_FootballEvent",
            joinColumns = { @JoinColumn(name = "Appuser_id") },
            inverseJoinColumns = { @JoinColumn(name = "FootballEvent_id") }
    )
    @JsonIgnoreProperties({"participants", "comments"})
    private Set<FootballEvent> footballEventsParticipants;

    @OneToMany(mappedBy = "authorBasketball",
            cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"authorBasketball", "comments"})
    private Set<BasketballEvent> basketballEventsAuthor;

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "Appuser_BasketballEvent",
            joinColumns = { @JoinColumn(name = "Appuser_id") },
            inverseJoinColumns = { @JoinColumn(name = "BasketballEvent_id") }
    )
    @JsonIgnoreProperties({"participants", "comments"})
    private Set<BasketballEvent> basketballEventsParticipants;

    @OneToMany(mappedBy = "authorVolleyball",
            cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"authorVolleyball", "comments"})
    private Set<VolleyballEvent> volleyballEventsAuthor;

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "Appuser_VolleyballEvent",
            joinColumns = { @JoinColumn(name = "Appuser_id") },
            inverseJoinColumns = { @JoinColumn(name = "VolleyballEvent_id") }
    )
    @JsonIgnoreProperties({"participants", "comments"})
    private Set<VolleyballEvent> volleyballEventsParticipants;

    @OneToMany(mappedBy = "author",
            cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"author", "comments"})
    private Set<Comment> comments;

    @JsonIgnore
    private String firebaseToken;

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

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
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

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("user"));
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

    public Set<BasketballEvent> getBasketballEventsAuthor() {
        return basketballEventsAuthor;
    }

    public void setBasketballEventsAuthor(Set<BasketballEvent> basketballEventsAuthor) {
        this.basketballEventsAuthor = basketballEventsAuthor;
    }

    public Set<BasketballEvent> getBasketballEventsParticipants() {
        return basketballEventsParticipants;
    }

    public void setBasketballEventsParticipants(Set<BasketballEvent> basketballEventsParticipants) {
        this.basketballEventsParticipants = basketballEventsParticipants;
    }

    public Set<VolleyballEvent> getVolleyballEventsAuthor() {
        return volleyballEventsAuthor;
    }

    public void setVolleyballEventsAuthor(Set<VolleyballEvent> volleyballEventsAuthor) {
        this.volleyballEventsAuthor = volleyballEventsAuthor;
    }

    public Set<VolleyballEvent> getVolleyballEventsParticipants() {
        return volleyballEventsParticipants;
    }

    public void setVolleyballEventsParticipants(Set<VolleyballEvent> volleyballEventsParticipants) {
        this.volleyballEventsParticipants = volleyballEventsParticipants;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public void removeFootballParticipants(FootballEvent footballEvent){
        footballEventsParticipants.remove(footballEvent);
        footballEvent.getParticipants().remove(this);
    }

    public void removeBasketballParticipants(BasketballEvent basketballEvent){
        basketballEventsParticipants.remove(basketballEvent);
        basketballEvent.getParticipantsBasketball().remove(this);
    }

    public void removeVolleyballParticipants(VolleyballEvent volleyballEvent){
        volleyballEventsParticipants.remove(volleyballEvent);
        volleyballEvent.getParticipantsVolleyball().remove(this);
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
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
