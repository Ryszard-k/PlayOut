package com.inz.PlayOut.controller;

import com.inz.PlayOut.model.entites.AppUser;
import com.inz.PlayOut.model.entites.FootballEvent;
import com.inz.PlayOut.model.entites.VolleyballEvent;
import com.inz.PlayOut.service.AppUserService;
import com.inz.PlayOut.service.VolleyballEventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/volleyball")
public record VolleyballEventController(AppUserService appUserService, VolleyballEventService volleyballEventService) {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> add(@RequestBody VolleyballEvent object) {
        Optional<AppUser> found = appUserService.findByUsername(object.getAuthorVolleyball().getUsername());
        if (found.isPresent()) {
            object.setAuthorVolleyball(found.get());
            volleyballEventService.save(object);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/join/{eventId}/{username}")
    public ResponseEntity<Object> jointToEvent(@PathVariable String username, @PathVariable Long eventId) {
        boolean updatedEvent = volleyballEventService.joinToEvent(username, eventId);
        if (updatedEvent) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        Optional<VolleyballEvent> found = volleyballEventService.findById(id);
        if (found.isPresent()) {
            volleyballEventService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/resign/{eventId}/{username}")
        public ResponseEntity<Object> resignForEvent(@PathVariable String username, @PathVariable Long eventId){
        boolean updatedEvent = volleyballEventService.resignForEvent(eventId, username);
        if (updatedEvent) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
