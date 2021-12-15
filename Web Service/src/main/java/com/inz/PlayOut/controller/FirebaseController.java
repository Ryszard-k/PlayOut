package com.inz.PlayOut.controller;

import com.inz.PlayOut.firebase.FirebaseMessagingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
public record FirebaseController(FirebaseMessagingService firebaseMessagingService) {

    @PatchMapping(value = "/{username}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> add(@RequestBody String token, @PathVariable String username) {
        boolean found = firebaseMessagingService.saveToken(username, token);
        if (found) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
