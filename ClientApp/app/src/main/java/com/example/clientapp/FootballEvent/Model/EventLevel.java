package com.example.clientapp.FootballEvent.Model;

import java.util.HashMap;

public enum EventLevel {

    F("Beginner, never played before"),
    E("Recreational, with fundamentals of rules"),
    D("Amateur, players with minimum basic technic and fundamentals of tactic"),
    C("Intermediate, mostly ex-players in district leagues"),
    B("Advanced, active players in district leagues or amateur leagues"),
    A("Pro, ex-players and oldboys from professional league");

    public final String description;

    EventLevel(String description) {
        this.description = description;
    }

    public static HashMap<EventLevel, String> valueOfDescription(){
        HashMap<EventLevel, String> map = new HashMap<>();
        map.put(F, F.description);
        map.put(E, E.description);
        map.put(D, D.description);
        map.put(C, C.description);
        map.put(B, B.description);
        map.put(A, A.description);

        return map;
    }
}
