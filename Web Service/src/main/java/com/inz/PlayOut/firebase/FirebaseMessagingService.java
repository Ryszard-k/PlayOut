package com.inz.PlayOut.firebase;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.inz.PlayOut.model.entites.AppUser;
import com.inz.PlayOut.model.repositories.UserRepo;
import com.inz.PlayOut.service.AppUserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public record FirebaseMessagingService(FirebaseMessaging firebaseMessaging, AppUserService appUserService, UserRepo userRepo) {

    public boolean saveToken(String username, String token){
        Optional<AppUser> appUser = appUserService.findByUsername(username);
        if (appUser.isPresent()){
            if (appUser.get().getFirebaseToken() == null){
                appUser.get().setFirebaseToken(token);
                userRepo.updateToken(username, token);
               // appUserService.save(appUser.get());
                return true;
            } else if (!appUser.get().getFirebaseToken().equals(token)){
                appUser.get().setFirebaseToken(token);
                userRepo.updateToken(username, token);
              //  appUserService.save(appUser.get());
                return true;
            }
            return true;
        } else return false;
    }

    public String sendNotification(String title, String note,  String token) throws FirebaseMessagingException {

        Notification notification = Notification
                .builder()
                .setTitle(title)
                .setBody(note)
                .build();

        Message message = Message
                .builder()
                .setToken(token)
                .setNotification(notification)
                .build();

        return firebaseMessaging.send(message);
    }

}
