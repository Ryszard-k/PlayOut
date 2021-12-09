package com.inz.PlayOut.controller;

import com.inz.PlayOut.model.entites.*;
import com.inz.PlayOut.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public record CommentController (CommentService commentService, AppUserService appUserService, FootballEventService footballEventService,
                                 BasketballEventService basketballEventService, VolleyballEventService volleyballEventService){

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
    public ResponseEntity<Object> add(@RequestBody Comment object) {
        Optional<AppUser> found = appUserService.findByUsername(object.getAuthor().getUsername());

        if (found.isPresent()) {
            object.setAuthor(found.get());
            if (object.getFootballEvent() != null){
                Optional<FootballEvent> footballEvent = footballEventService.findById(object.getFootballEvent().getId());
                footballEvent.ifPresent(object::setFootballEvent);
            } else if (object.getBasketballEvent() != null){
                Optional<BasketballEvent> basketballEvent = basketballEventService.findById(object.getBasketballEvent().getId());
                basketballEvent.ifPresent(object::setBasketballEvent);
            } else if (object.getVolleyballEvent() != null){
                Optional<VolleyballEvent> volleyballEvent = volleyballEventService.findById(object.getVolleyballEvent().getId());
                volleyballEvent.ifPresent(object::setVolleyballEvent);
            }
            commentService.save(object);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
