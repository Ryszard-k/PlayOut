package com.inz.PlayOut.controller;

import com.inz.PlayOut.model.entites.AppUser;
import com.inz.PlayOut.model.entites.FootballEvent;
import com.inz.PlayOut.service.AppUserService;
import com.inz.PlayOut.service.FootballEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/footballEvent")
public record FootballEventController(FootballEventService footballEventService,
                                      AppUserService appUserService) implements CRUDController<FootballEvent> {

    @Autowired
    public FootballEventController {
    }

    @Override
    @GetMapping()
    public ResponseEntity<Object> findAll() {
        List<FootballEvent> found = footballEventService.findAll();
        if (found.isEmpty()) {
            return new ResponseEntity<>("Repository is empty!", HttpStatus.NOT_FOUND);
        } else
            return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        Optional<FootballEvent> found = footballEventService.findById(id);
        if (found.isEmpty()) {
            return new ResponseEntity<>("Bad id", HttpStatus.NOT_FOUND);
        } else
            return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @GetMapping(path = "/activeEvent/all",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> getAllActiveEvent() {
        List<FootballEvent> list = footballEventService.findAllActiveEvent();
        if (list.isEmpty()) {
            return new ResponseEntity<>(List.of(), HttpStatus.NO_CONTENT);
        } else
            return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(path = "/activeEvent/{username}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> getMyActiveEvent(@PathVariable String username) {
        List<FootballEvent> list = footballEventService.getMyActiveEvent(username);
        if (list.isEmpty()) {
            return new ResponseEntity<>(List.of(), HttpStatus.NO_CONTENT);
        } else
            return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(path = "/historyEvent/{username}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> getMyHistoryEvent(@PathVariable String username) {
        List<FootballEvent> list = footballEventService.getMyHistoryEvent(username);
        if (list.isEmpty()) {
            return new ResponseEntity<>(List.of(), HttpStatus.NO_CONTENT);
        } else
            return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Override
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> add(@RequestBody FootballEvent object) {
        Optional<AppUser> found = appUserService.findByUsername(object.getAuthor().getUsername());
        if (found.isPresent()) {
            object.setAuthor(found.get());
            footballEventService.save(object);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{idAppUser}/{idFootballEvent}")
    public ResponseEntity<Object> jointToEvent(@PathVariable Long idAppUser, @PathVariable Long idFootballEvent) {
        Optional<FootballEvent> updatedEvent = footballEventService.joinToEvent(idAppUser, idFootballEvent);
        if (updatedEvent.isPresent()) {
            return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
        } else
            return new ResponseEntity<>("Not found event to join", HttpStatus.NOT_FOUND);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        Optional<FootballEvent> found = footballEventService.findById(id);
        if (found.isPresent()) {
            footballEventService.delete(id);
            return new ResponseEntity<>(found, HttpStatus.OK);
        } else
            return new ResponseEntity<>("Not found object to delete!", HttpStatus.NOT_FOUND);
    }
}