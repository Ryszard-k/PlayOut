package com.inz.PlayOut.controller;

import com.inz.PlayOut.model.entites.AppUser;
import com.inz.PlayOut.model.entites.BasketballEvent;
import com.inz.PlayOut.service.AppUserService;
import com.inz.PlayOut.service.BasketballEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/basketball")
public record BasketballEventController(BasketballEventService basketballEventService, AppUserService appUserService) {

    @Autowired
    public BasketballEventController {
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> add(@RequestBody BasketballEvent object) {
        Optional<AppUser> found = appUserService.findByUsername(object.getAuthorBasketball().getUsername());
        if (found.isPresent()) {
            object.setAuthorBasketball(found.get());
            basketballEventService.save(object);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/join/{eventId}/{username}")
    public ResponseEntity<Object> jointToEvent(@PathVariable String username, @PathVariable Long eventId) {
        boolean updatedEvent = basketballEventService.joinToEvent(username, eventId);
        if (updatedEvent) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        Optional<BasketballEvent> found = basketballEventService.findById(id);
        if (found.isPresent()) {
            basketballEventService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
