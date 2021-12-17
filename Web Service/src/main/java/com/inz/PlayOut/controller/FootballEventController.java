package com.inz.PlayOut.controller;

import com.inz.PlayOut.firebase.FirebaseMessagingService;
import com.inz.PlayOut.model.entites.AppUser;
import com.inz.PlayOut.model.entites.FootballEvent;
import com.inz.PlayOut.schedule.RunnableTaskFootball;
import com.inz.PlayOut.schedule.ScheduleTaskConfig;
import com.inz.PlayOut.service.AppUserService;
import com.inz.PlayOut.service.FootballEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/footballEvent")
public record FootballEventController(FootballEventService footballEventService, AppUserService appUserService,
                                      FirebaseMessagingService firebaseMessagingService) implements CRUDController<FootballEvent> {

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

    @Override
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> add(@RequestBody FootballEvent object) {
        Optional<AppUser> found = appUserService.findByUsername(object.getAuthor().getUsername());
        if (found.isPresent()) {
            object.setAuthor(found.get());
            FootballEvent footballEvent = footballEventService.save(object);

            LocalDateTime localDateTime = LocalDateTime.of(object.getDate(), object.getTime()).minusHours(1);
            ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
            Instant result = Instant.from(zonedDateTime);

            ScheduleTaskConfig scheduleTaskConfig = new ScheduleTaskConfig();
            scheduleTaskConfig.threadPoolTaskScheduler().schedule(
                    new RunnableTaskFootball(footballEventService, footballEvent.getId(), firebaseMessagingService),
                    Date.from(result));
            System.out.println(Date.from(result));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/join/{eventId}/{username}")
    public ResponseEntity<Object> jointToEvent(@PathVariable String username, @PathVariable Long eventId) {
        boolean updatedEvent = footballEventService.joinToEvent(username, eventId);
        if (updatedEvent) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        Optional<FootballEvent> found = footballEventService.findById(id);
        if (found.isPresent()) {
            footballEventService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/resign/{eventId}/{username}")
    public ResponseEntity<Object> resignForEvent(@PathVariable String username, @PathVariable Long eventId){
        boolean updatedEvent = footballEventService.resignForEvent(eventId, username);
        if (updatedEvent) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}