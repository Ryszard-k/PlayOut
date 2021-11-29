package com.example.clientapp;

import com.example.clientapp.Basketball.Basketball;
import com.example.clientapp.FootballEvent.Model.FootballEvent;
import com.example.clientapp.Volleyball.Volleyball;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.gson.annotations.SerializedName;

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
       return (List<FootballEvent>) map.get("Football");
    }

    public List<Basketball> getEventsWrapperWithBasketball(){
        return (List<Basketball>) map.get("Basketball");
    }

    public List<Volleyball> getEventsWrapperWithVolleyball(){
        return (List<Volleyball>) map.get("Volleyball");
    }

}
