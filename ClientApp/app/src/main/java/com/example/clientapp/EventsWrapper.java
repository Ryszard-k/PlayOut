package com.example.clientapp;

import com.example.clientapp.basketball.Basketball;
import com.example.clientapp.footballEvent.model.FootballEvent;
import com.example.clientapp.volleyball.Volleyball;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class EventsWrapper {

    private final HashMap<String, List<?>> map = new HashMap<>();

    @JsonDeserialize()
    public HashMap<String, List<?>> setEventsWrapperWithFootball(List<FootballEvent> objects){
        map.put("Football", objects);
        return map;
    }

    public HashMap<String, List<?>> setEventsWrapperWithBasketball(List<Basketball> objects){
        map.put("Basketball", objects);
        return map;
    }

    public HashMap<String, List<?>> setEventsWrapperWithVolleyball(List<Volleyball> objects){
        map.put("Volleyball", objects);
        return map;
    }

    public List<FootballEvent> getEventsWrapperWithFootball(){
        if (map.get("Football") != null) {
            return (List<FootballEvent>) map.get("Football");
        } else return Collections.emptyList();
    }

    public List<Basketball> getEventsWrapperWithBasketball(){
        if (map.get("Basketball") != null) {
            return (List<Basketball>) map.get("Basketball");
        } else return Collections.emptyList();
    }

    public List<Volleyball> getEventsWrapperWithVolleyball() {
        if (map.get("Volleyball") != null) {
            return (List<Volleyball>) map.get("Volleyball");
        } else return Collections.emptyList();
    }

}
