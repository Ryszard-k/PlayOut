package com.inz.PlayOut.controller;

import com.inz.PlayOut.model.EventLevel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/levels")
public class EventLevelController {

    @GetMapping
    public ResponseEntity<Object> levels(){
        return new ResponseEntity<>(EventLevel.valueOfDescription(), HttpStatus.OK);
    }
}
