package com.inz.PlayOut;

import com.inz.PlayOut.model.EventLevel;
import com.inz.PlayOut.model.entites.AppUser;
import com.inz.PlayOut.model.entites.FootballEvent;
import com.inz.PlayOut.service.AppUserService;
import com.inz.PlayOut.service.CommentService;
import com.inz.PlayOut.service.FootballEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class test {

    private final FootballEventService footballEventService;
    private final AppUserService appUserService;
    private final CommentService commentService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public test(FootballEventService footballEventService, AppUserService appUserService, CommentService commentService, PasswordEncoder passwordEncoder) {
        this.footballEventService = footballEventService;
        this.appUserService = appUserService;
        this.commentService = commentService;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void fillDbWithExampleData(){
        appUserService.save(new AppUser("Piotr", "piotr123", "piotr@gmail.com"));
        appUserService.save(new AppUser("Kamil", "kamil123", "kamil@gmail.com"));

        footballEventService.save(new FootballEvent(LocalDate.now(), LocalTime.now(), 12.343234, 45.345665, EventLevel.E,
                3, appUserService.findById(1L).get()));

    }
}
