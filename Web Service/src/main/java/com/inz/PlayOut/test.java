package com.inz.PlayOut;

import com.inz.PlayOut.model.entites.AppUser;
import com.inz.PlayOut.model.entites.FootballEvent;
import com.inz.PlayOut.service.AppUserService;
import com.inz.PlayOut.service.CommentService;
import com.inz.PlayOut.service.FootballEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class test {

    private final FootballEventService footballEventService;
    private final AppUserService appUserService;
    private final CommentService commentService;

    @Autowired
    public test(FootballEventService footballEventService, AppUserService appUserService, CommentService commentService) {
        this.footballEventService = footballEventService;
        this.appUserService = appUserService;
        this.commentService = commentService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void fillDbWithExampleData(){
        appUserService.save(new AppUser("Piotr", "password", "piotr@gmail.com"));

        footballEventService.save(new FootballEvent(LocalDate.now(), LocalTime.now(), 12.343234, 45.345665,
                appUserService.findById(1L).get()), 1L);

    }
}
