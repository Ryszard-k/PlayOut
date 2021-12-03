package com.inz.PlayOut;

import com.inz.PlayOut.model.EventLevel;
import com.inz.PlayOut.model.entites.AppUser;
import com.inz.PlayOut.model.entites.BasketballEvent;
import com.inz.PlayOut.model.entites.FootballEvent;
import com.inz.PlayOut.service.AppUserService;
import com.inz.PlayOut.service.BasketballEventService;
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
    private final BasketballEventService basketballEventService;

    @Autowired
    public test(FootballEventService footballEventService, AppUserService appUserService, CommentService commentService, PasswordEncoder passwordEncoder, BasketballEventService basketballEventService) {
        this.footballEventService = footballEventService;
        this.appUserService = appUserService;
        this.commentService = commentService;
        this.passwordEncoder = passwordEncoder;
        this.basketballEventService = basketballEventService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void fillDbWithExampleData(){
        appUserService.save(new AppUser("Piotr", "piotr123", "piotr@gmail.com"));
        appUserService.save(new AppUser("Kamil", "kamil123", "kamil@gmail.com"));

        footballEventService.save(new FootballEvent(LocalDate.now().plusDays(3), LocalTime.now(), 12.343234, 45.345665, EventLevel.E,
                3, "Piłeczka raz", "Cystersów 1" ,appUserService.findById(1L).get()));
        footballEventService.save(new FootballEvent(LocalDate.now().minusDays(3), LocalTime.now(), 12.343234, 45.345665, EventLevel.E,
                3, "Piłeczka dwa", "Cystersów 2", appUserService.findById(1L).get()));
        footballEventService.save(new FootballEvent(LocalDate.now(), LocalTime.now().plusMinutes(3), 10.343234, 35.345665, EventLevel.E,
                3, "Piłeczka trzy", "Cystersów 3", appUserService.findById(1L).get()));
        footballEventService.save(new FootballEvent(LocalDate.now().plusDays(3), LocalTime.now().plusMinutes(3), 11.343234, 12.76, EventLevel.A,
                3, "Piłeczka cztery","Cystersów 4", appUserService.findById(2L).get()));

        basketballEventService.save(new BasketballEvent(LocalDate.now().plusDays(3), LocalTime.now(), 2.343234, 5.345665, EventLevel.C,
                3, "kosz raz", "Mogilska 1" ,appUserService.findById(1L).get()));
        basketballEventService.save(new BasketballEvent(LocalDate.now().minusDays(3), LocalTime.now(), 2.343234, 5.345665, EventLevel.C,
                3, "kosz dwa", "Mogilska 2" ,appUserService.findById(1L).get()));
        basketballEventService.save(new BasketballEvent(LocalDate.now(), LocalTime.now().plusMinutes(3), 2.343234, 5.345665, EventLevel.C,
                3, "kosz trzy", "Mogilska 3" ,appUserService.findById(1L).get()));
        basketballEventService.save(new BasketballEvent(LocalDate.now().plusDays(3), LocalTime.now().plusMinutes(3), 2.343234, 5.345665, EventLevel.C,
                3, "kosz cztery", "Mogilska 4" ,appUserService.findById(2L).get()));
    }
}
