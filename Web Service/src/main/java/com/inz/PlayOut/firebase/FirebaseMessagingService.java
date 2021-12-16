package com.inz.PlayOut.firebase;

import com.google.firebase.messaging.*;
import com.inz.PlayOut.model.entites.AppUser;
import com.inz.PlayOut.model.repositories.UserRepo;
import com.inz.PlayOut.service.AppUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public record FirebaseMessagingService(FirebaseMessaging firebaseMessaging, AppUserService appUserService, UserRepo userRepo) {

    public boolean saveToken(String username, String token){
        Optional<AppUser> appUser = appUserService.findByUsername(username);
        token = token.substring(1, token.length() - 1);
        if (appUser.isPresent()){
            if (appUser.get().getFirebaseToken() == null){
                appUser.get().setFirebaseToken(token);
                userRepo.updateToken(username, token);
                return true;
            } else if (!appUser.get().getFirebaseToken().equals(token)){
                appUser.get().setFirebaseToken(token);
                userRepo.updateToken(username, token);
                return true;
            }
            return true;
        } else return false;
    }

    public BatchResponse sendMulticast(List<String> tokens, String title, String note, String icon) throws FirebaseMessagingException {

        Notification notification = Notification
                .builder()
                .setTitle(title)
                .setBody(note)
                .build();

        MulticastMessage message = MulticastMessage.builder()
                .setNotification(notification)
                .addAllTokens(tokens)
                .putData("icon", icon)
                .build();

        return firebaseMessaging.sendMulticast(message);
    }

}
