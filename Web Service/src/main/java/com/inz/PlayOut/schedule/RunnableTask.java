package com.inz.PlayOut.schedule;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.inz.PlayOut.firebase.FirebaseMessagingService;
import com.inz.PlayOut.model.entites.FootballEvent;
import com.inz.PlayOut.service.FootballEventService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RunnableTask implements Runnable{

    private final FootballEventService footballEventService;
    private final Long id;
    private final FirebaseMessagingService firebaseMessagingService;

    public RunnableTask(FootballEventService footballEventService, Long id, FirebaseMessagingService firebaseMessagingService) {
        this.footballEventService = footballEventService;
        this.id = id;
        this.firebaseMessagingService = firebaseMessagingService;
    }

    @Override
    public void run() {
        Optional<FootballEvent> footballEvent = footballEventService.findById(id);
        if (footballEvent.isPresent()){
            List<String> listOfTokens = new ArrayList<>();
            footballEvent.get().getParticipants().forEach(k -> listOfTokens.add(k.getFirebaseToken()));
            listOfTokens.add(footballEvent.get().getAuthor().getFirebaseToken());
            if (!listOfTokens.isEmpty()) {
                try {
                    firebaseMessagingService.sendMulticast(listOfTokens, footballEvent.get().getNote(), "You have one hour to start your event",
                            "Football");
                } catch (FirebaseMessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
