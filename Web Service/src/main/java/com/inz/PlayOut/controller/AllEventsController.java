package com.inz.PlayOut.controller;

import com.inz.PlayOut.EventWrapper;
import com.inz.PlayOut.model.entites.BasketballEvent;
import com.inz.PlayOut.model.entites.FootballEvent;
import com.inz.PlayOut.model.entites.VolleyballEvent;
import com.inz.PlayOut.service.BasketballEventService;
import com.inz.PlayOut.service.FootballEventService;
import com.inz.PlayOut.service.VolleyballEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/events")
public record AllEventsController(FootballEventService footballEventService, BasketballEventService basketballEventService, VolleyballEventService volleyballEventService) {

    @Autowired
    public AllEventsController {
    }

    @GetMapping(path = "/active/all",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> getAllActiveEvents(){
        EventWrapper eventWrapper = new EventWrapper();

        List<FootballEvent> footballEvents = footballEventService.findAllActiveEvent();
        eventWrapper.setEventsWrapperWithFootball(footballEvents);

        List<BasketballEvent> basketballEvents = basketballEventService.findAllActiveEvent();
        eventWrapper.setEventsWrapperWithBasketball(basketballEvents);

        List<VolleyballEvent> volleyballEvents = volleyballEventService.findAllActiveEvent();
        eventWrapper.setEventsWrapperWithVolleyball(volleyballEvents);

        if (eventWrapper.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else
            return new ResponseEntity<>(eventWrapper, HttpStatus.OK);
    }

    @GetMapping(path = "/activeEvent/{username}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> getMyActiveEvent(@PathVariable String username) {
        EventWrapper eventWrapper = new EventWrapper();

        List<FootballEvent> list = footballEventService.getMyActiveEvent(username);
        eventWrapper.setEventsWrapperWithFootball(list);

        List<BasketballEvent> basketballEvents = basketballEventService.getMyActiveEvent(username);
        eventWrapper.setEventsWrapperWithBasketball(basketballEvents);

        List<VolleyballEvent> volleyballEvents = volleyballEventService.getMyActiveEvent(username);
        eventWrapper.setEventsWrapperWithVolleyball(volleyballEvents);

        if (eventWrapper.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else
            return new ResponseEntity<>(eventWrapper, HttpStatus.OK);
    }

    @GetMapping(path = "/historyEvent/{username}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> getMyHistoryEvent(@PathVariable String username) {
        EventWrapper eventWrapper = new EventWrapper();

        List<FootballEvent> list = footballEventService.getMyHistoryEvent(username);
        eventWrapper.setEventsWrapperWithFootball(list);

        List<BasketballEvent> basketballEvents = basketballEventService.getMyHistoryEvent(username);
        eventWrapper.setEventsWrapperWithBasketball(basketballEvents);

        List<VolleyballEvent> volleyballEvents = volleyballEventService.getMyHistoryEvent(username);
        eventWrapper.setEventsWrapperWithVolleyball(volleyballEvents);

        if (eventWrapper.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else
            return new ResponseEntity<>(eventWrapper, HttpStatus.OK);
    }
}
