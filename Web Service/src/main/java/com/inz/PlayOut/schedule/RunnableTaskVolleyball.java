package com.inz.PlayOut.schedule;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.inz.PlayOut.firebase.FirebaseMessagingService;
import com.inz.PlayOut.model.entites.FootballEvent;
import com.inz.PlayOut.model.entites.VolleyballEvent;
import com.inz.PlayOut.service.VolleyballEventService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RunnableTaskVolleyball implements Runnable{

    private final VolleyballEventService volleyballEventService;
    private final Long id;
    private final FirebaseMessagingService firebaseMessagingService;

    public RunnableTaskVolleyball(VolleyballEventService volleyballEventService, Long id, FirebaseMessagingService firebaseMessagingService) {
        this.volleyballEventService = volleyballEventService;
        this.id = id;
        this.firebaseMessagingService = firebaseMessagingService;
    }

    @Override
    public void run() {
        Optional<VolleyballEvent> volleyballEvent = volleyballEventService.findById(id);
        if (volleyballEvent.isPresent()){
            List<String> listOfTokens = new ArrayList<>();
            volleyballEvent.get().getParticipantsVolleyball().forEach(k -> listOfTokens.add(k.getFirebaseToken()));
            listOfTokens.add(volleyballEvent.get().getAuthorVolleyball().getFirebaseToken());
            if (!listOfTokens.isEmpty()) {
                try {
                    firebaseMessagingService.sendMulticast(listOfTokens, volleyballEvent.get().getNote(), "You have one hour to start your event",
                            "Football");
                } catch (FirebaseMessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
