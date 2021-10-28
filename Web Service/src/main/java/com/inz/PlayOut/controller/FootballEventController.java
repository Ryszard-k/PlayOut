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
public class FootballEventController implements CRUDController<FootballEvent> {

    private final FootballEventService footballEventService;
    private final AppUserService appUserService;

    @Autowired
    public FootballEventController(FootballEventService footballEventService, AppUserService appUserService) {
        this.footballEventService = footballEventService;
        this.appUserService = appUserService;
    }

    @Override
    @GetMapping()
    public ResponseEntity<Object> findAll() {
        List<FootballEvent> found = footballEventService.findAll();
        if (found.isEmpty()){
            return new ResponseEntity<>("Repository is empty!", HttpStatus.NOT_FOUND);
        } else
            return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        Optional<FootballEvent> found = footballEventService.findById(id);
        if(found.isEmpty()){
            return new ResponseEntity<>("Bad id", HttpStatus.NOT_FOUND);
        } else
            return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @Override
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> add(@RequestBody FootballEvent object) {
        Optional<AppUser> found = appUserService.findById(object.getAuthor().getId());
        if (found.isPresent()) {
            object.setAuthor(found.get());
            footballEventService.save(object);
            return new ResponseEntity<>(object, HttpStatus.CREATED);
        } else
            return new ResponseEntity<>("Empty input data", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{idAppUser}/{idFootballEvent}")
    public ResponseEntity<Object> jointToEvent(@PathVariable Long idAppUser, @PathVariable Long idFootballEvent){
        Optional<FootballEvent> updatedEvent = footballEventService.joinToEvent(idAppUser, idFootballEvent);
        if (updatedEvent.isPresent()){
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
            return new ResponseEntity<>(found,HttpStatus.OK);
        } else
            return new ResponseEntity<>("Not found object to delete!", HttpStatus.NOT_FOUND);
    }
}