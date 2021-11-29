package com.inz.PlayOut;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inz.PlayOut.model.entites.Basketball;
import com.inz.PlayOut.model.entites.FootballEvent;
import com.inz.PlayOut.model.entites.Volleyball;

import java.util.HashMap;
import java.util.List;

public class EventWrapper {

    private HashMap<String, List<?>> map = new HashMap<>();

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

    @JsonIgnore
    public boolean isEmpty(){
        return map.isEmpty();
    }
}
