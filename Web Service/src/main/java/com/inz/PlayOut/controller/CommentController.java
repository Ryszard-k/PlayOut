package com.inz.PlayOut.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.inz.PlayOut.firebase.FirebaseMessagingService;
import com.inz.PlayOut.model.entites.*;
import com.inz.PlayOut.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/comments")
public record CommentController (CommentService commentService, AppUserService appUserService, FootballEventService footballEventService,
                                 BasketballEventService basketballEventService, VolleyballEventService volleyballEventService,
                                 FirebaseMessagingService firebaseMessagingService){

    @Autowired
    public CommentController{}

    @GetMapping
    public ResponseEntity<Object> findAll() {
        List<Comment> found = commentService.findAll();
        if (found.isEmpty()){
            return new ResponseEntity<>("Repository is empty!", HttpStatus.NOT_FOUND);
        } else
            return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> add(@RequestBody Comment object) throws FirebaseMessagingException {
        Optional<AppUser> found = appUserService.findByUsername(object.getAuthor().getUsername());
        List<String> tokensOfParticipants = new ArrayList<>();

        if (found.isPresent()) {
            object.setAuthor(found.get());
            if (object.getFootballEvent() != null){
                Optional<FootballEvent> footballEvent = footballEventService.findById(object.getFootballEvent().getId());
                footballEvent.ifPresent(object::setFootballEvent);
                footballEvent.get().getParticipants().forEach(k -> tokensOfParticipants.add(k.getFirebaseToken()));
                tokensOfParticipants.add(footballEvent.get().getAuthor().getFirebaseToken());
                tokensOfParticipants.remove(object.getAuthor().getFirebaseToken());
                if (!tokensOfParticipants.isEmpty()) {
                    firebaseMessagingService.sendMulticast(tokensOfParticipants, footballEvent.get().getNote(), found.get().getUsername() +
                            " add comment: " + object.getText(), "Football");
                }
            } else if (object.getBasketballEvent() != null){
                Optional<BasketballEvent> basketballEvent = basketballEventService.findById(object.getBasketballEvent().getId());
                basketballEvent.ifPresent(object::setBasketballEvent);
                basketballEvent.get().getParticipantsBasketball().forEach(k -> tokensOfParticipants.add(k.getFirebaseToken()));
                tokensOfParticipants.add(basketballEvent.get().getAuthorBasketball().getFirebaseToken());
                tokensOfParticipants.remove(object.getAuthor().getFirebaseToken());
                if (!tokensOfParticipants.isEmpty()) {
                    firebaseMessagingService.sendMulticast(tokensOfParticipants, basketballEvent.get().getNote(), found.get().getUsername() +
                            " add comment: " + object.getText(), "Basketball");
                }
            } else if (object.getVolleyballEvent() != null){
                Optional<VolleyballEvent> volleyballEvent = volleyballEventService.findById(object.getVolleyballEvent().getId());
                volleyballEvent.ifPresent(object::setVolleyballEvent);
                volleyballEvent.get().getParticipantsVolleyball().forEach(k -> tokensOfParticipants.add(k.getFirebaseToken()));
                tokensOfParticipants.add(volleyballEvent.get().getAuthorVolleyball().getFirebaseToken());
                tokensOfParticipants.remove(object.getAuthor().getFirebaseToken());
                if (!tokensOfParticipants.isEmpty()) {
                    firebaseMessagingService.sendMulticast(tokensOfParticipants, volleyballEvent.get().getNote(), found.get().getUsername() +
                            " add comment: " + object.getText(), "Volleyball");
                }
            }

            commentService.save(object);
            found = appUserService.findByUsername(object.getAuthor().getUsername());
            Optional<Comment> optionalComment = found.get().getComments().stream().max(Comparator.comparing(Comment::getDate).thenComparing(Comment::getTime));
            return new ResponseEntity<>(optionalComment.get().getId(), HttpStatus.CREATED);
        } else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        Optional<Comment> found = commentService.findById(id);
        if (found.isPresent()) {
            commentService.delete(id);
            return new ResponseEntity<>(found,HttpStatus.OK);
        } else
            return new ResponseEntity<>("Not found object to delete!", HttpStatus.NOT_FOUND);
    }
}
