package com.inz.PlayOut;

import com.inz.PlayOut.Model.Entites.AppUser;
import com.inz.PlayOut.Model.Entites.Comment;
import com.inz.PlayOut.Model.Entites.FootballEvent;
import com.inz.PlayOut.Service.AppUserService;
import com.inz.PlayOut.Service.CommentService;
import com.inz.PlayOut.Service.FootballEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.time.LocalDateTime;

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
        footballEventService.save(FootballEvent.builder()
                .dateTime(LocalDateTime.parse("2021-10-15 12:00"))
                .location("Cracow")
                .author(new AppUser())
                .build());

        appUserService.save(new AppUser("Piotr"));

    }
}
