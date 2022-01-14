package com.inz.PlayOut;

import com.inz.PlayOut.model.EventLevel;
import com.inz.PlayOut.model.entites.AppUser;
import com.inz.PlayOut.model.entites.BasketballEvent;
import com.inz.PlayOut.model.entites.FootballEvent;
import com.inz.PlayOut.model.entites.VolleyballEvent;
import com.inz.PlayOut.service.*;
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
    private final VolleyballEventService volleyballEventService;

    @Autowired
    public test(FootballEventService footballEventService, AppUserService appUserService, CommentService commentService, PasswordEncoder passwordEncoder, BasketballEventService basketballEventService, VolleyballEventService volleyballEventService) {
        this.footballEventService = footballEventService;
        this.appUserService = appUserService;
        this.commentService = commentService;
        this.passwordEncoder = passwordEncoder;
        this.basketballEventService = basketballEventService;
        this.volleyballEventService = volleyballEventService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void fillDbWithExampleData(){
        appUserService.save(new AppUser("Piotr", "piotr123", "piotr@gmail.com"));
        appUserService.save(new AppUser("Kamil", "kamil123", "kamil@gmail.com"));

        footballEventService.save(new FootballEvent(LocalDate.now().plusDays(3), LocalTime.now(), 50.0641923, 19.9449845, EventLevel.E,
                3, "Wydarzenie piłka nożna 1", "Westerplatte" ,appUserService.findById(1L).get()));
        footballEventService.save(new FootballEvent(LocalDate.now().minusDays(3), LocalTime.now(), 50.0622247, 19.9511433, EventLevel.E,
                3, "Wydarzenie piłka nożna 2", "Odona Bujwida", appUserService.findById(1L).get()));
        footballEventService.save(new FootballEvent(LocalDate.now(), LocalTime.now().plusMinutes(3), 50.0589054, 19.9439291, EventLevel.E,
                3, "Wydarzenie piłka nożna 3", "6 Wielopole", appUserService.findById(1L).get()));
        footballEventService.save(new FootballEvent(LocalDate.now().plusDays(3), LocalTime.now().plusMinutes(3), 54.3577561, 18.6527646, EventLevel.A,
                3, "Wydarzenie piłka nożna 4","Aleja 3 Maja", appUserService.findById(2L).get()));

        basketballEventService.save(new BasketballEvent(LocalDate.now().plusDays(3), LocalTime.now(), 52.4066045, 16.9219379, EventLevel.C,
                3, "Wydarzenie koszykówka 1", "61 Święty Marcin" ,appUserService.findById(1L).get()));
        basketballEventService.save(new BasketballEvent(LocalDate.now().minusDays(3), LocalTime.now(), 52.3937437, 16.9352344, EventLevel.C,
                3, "Wydarzenie koszykówka 2", "21 Droga Dębińska" ,appUserService.findById(1L).get()));
        basketballEventService.save(new BasketballEvent(LocalDate.now(), LocalTime.now().plusMinutes(3), 52.39871, 16.896088, EventLevel.C,
                3, "Wydarzenie koszykówka 3", "2 Kazimierza Jarochowskiego" ,appUserService.findById(1L).get()));
        basketballEventService.save(new BasketballEvent(LocalDate.now().plusDays(3), LocalTime.now().plusMinutes(3), 52.4081949, 16.9049553, EventLevel.C,
                3, "Wydarzenie koszykówka 4", "Bukowska" ,appUserService.findById(2L).get()));

        volleyballEventService.save(new VolleyballEvent(LocalDate.now().plusDays(3), LocalTime.now(), 54.3657633, 18.6329095, EventLevel.C,
                3, "Wydarzenie siatkówka 1", "3 Juliana Tuwima" ,appUserService.findById(1L).get()));
        volleyballEventService.save(new VolleyballEvent(LocalDate.now().minusDays(3), LocalTime.now(), 54.3560096, 18.630234, EventLevel.C,
                3, "Wydarzenie siatkówka 2", "Powstańców Warszawskich" ,appUserService.findById(1L).get()));
        volleyballEventService.save(new VolleyballEvent(LocalDate.now(), LocalTime.now().plusMinutes(3), 54.3664794, 18.6150369, EventLevel.C,
                3, "Wydarzenie siatkówka 3", "55 Kręta" ,appUserService.findById(1L).get()));
        volleyballEventService.save(new VolleyballEvent(LocalDate.now().plusDays(3), LocalTime.now().plusMinutes(3), 50.0627144, 19.9160631, EventLevel.C,
                3, "Wydarzenie siatkówka 4", "Aksamitna" ,appUserService.findById(2L).get()));
    }
}
