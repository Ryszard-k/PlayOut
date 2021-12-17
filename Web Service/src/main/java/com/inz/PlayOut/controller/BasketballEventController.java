package com.inz.PlayOut.controller;

import com.inz.PlayOut.firebase.FirebaseMessagingService;
import com.inz.PlayOut.model.entites.AppUser;
import com.inz.PlayOut.model.entites.BasketballEvent;
import com.inz.PlayOut.schedule.RunnableTaskBasketball;
import com.inz.PlayOut.schedule.RunnableTaskFootball;
import com.inz.PlayOut.schedule.ScheduleTaskConfig;
import com.inz.PlayOut.service.AppUserService;
import com.inz.PlayOut.service.BasketballEventService;
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
import java.util.Optional;

@RestController
@RequestMapping("/basketball")
public record BasketballEventController(BasketballEventService basketballEventService, AppUserService appUserService,
                                        FirebaseMessagingService firebaseMessagingService) {

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
            BasketballEvent basketballEvent = basketballEventService.save(object);

            LocalDateTime localDateTime = LocalDateTime.of(object.getDate(), object.getTime()).minusHours(1);
            ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
            Instant result = Instant.from(zonedDateTime);

            ScheduleTaskConfig scheduleTaskConfig = new ScheduleTaskConfig();
            scheduleTaskConfig.threadPoolTaskScheduler().schedule(
                    new RunnableTaskBasketball(basketballEventService, basketballEvent.getId(), firebaseMessagingService),
                    Date.from(result));
            System.out.println(Date.from(result));
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

    @PutMapping("/resign/{eventId}/{username}")
    public ResponseEntity<Object> resignForEvent(@PathVariable String username, @PathVariable Long eventId){
        boolean updatedEvent = basketballEventService.resignForEvent(eventId, username);
        if (updatedEvent) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
