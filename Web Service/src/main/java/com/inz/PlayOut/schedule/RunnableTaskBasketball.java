package com.inz.PlayOut.schedule;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.inz.PlayOut.firebase.FirebaseMessagingService;
import com.inz.PlayOut.model.entites.BasketballEvent;
import com.inz.PlayOut.model.entites.FootballEvent;
import com.inz.PlayOut.service.BasketballEventService;
import com.inz.PlayOut.service.FootballEventService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RunnableTaskBasketball implements Runnable{

    private final BasketballEventService basketballEventService;
    private final Long id;
    private final FirebaseMessagingService firebaseMessagingService;

    public RunnableTaskBasketball(BasketballEventService basketballEventService, Long id, FirebaseMessagingService firebaseMessagingService) {
        this.basketballEventService = basketballEventService;
        this.id = id;
        this.firebaseMessagingService = firebaseMessagingService;
    }

    @Override
    public void run() {
        Optional<BasketballEvent> basketballEvent = basketballEventService.findById(id);
        if (basketballEvent.isPresent()){
            List<String> listOfTokens = new ArrayList<>();
            basketballEvent.get().getParticipantsBasketball().forEach(k -> listOfTokens.add(k.getFirebaseToken()));
            listOfTokens.add(basketballEvent.get().getAuthorBasketball().getFirebaseToken());
            if (!listOfTokens.isEmpty()) {
                try {
                    firebaseMessagingService.sendMulticast(listOfTokens, basketballEvent.get().getNote(), "You have one hour to start your event",
                            "Football");
                } catch (FirebaseMessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
