package com.inz.PlayOut.service;

import com.inz.PlayOut.model.entites.AppUser;
import com.inz.PlayOut.model.entites.FootballEvent;
import com.inz.PlayOut.model.repositories.FootballEventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public record FootballEventService(FootballEventRepo footballEventRepo, AppUserService appUserService)
        implements CRUDService<FootballEvent> {

    @Autowired
    public FootballEventService {
    }

    @Override
    public List<FootballEvent> findAll() {
        return footballEventRepo.findAll();
    }

    @Override
    public Optional<FootballEvent> findById(Long id) {
        return footballEventRepo.findById(id);
    }

    public List<FootballEvent> getMyActiveEvent(String username){
        Optional<AppUser> appUser = appUserService.findByUsername(username);
        if (appUser.isPresent()){
            List<FootballEvent> myActiveEvents = appUser.get().getFootballEventsParticipants().stream()
                    .filter(k -> k.getDate().isAfter(LocalDate.now()))
                    .collect(Collectors.toList());

            myActiveEvents.addAll(appUser.get().getFootballEventsParticipants().stream()
                    .filter(k -> (k.getDate().isEqual(LocalDate.now())) && (k.getTime().isAfter(LocalTime.now().plusSeconds(30))))
                    .collect(Collectors.toList()));

            myActiveEvents.addAll(appUser.get().getFootballEventsAuthor().stream()
                    .filter(k -> k.getDate().isAfter(LocalDate.now()))
                    .collect(Collectors.toList()));

            myActiveEvents.addAll(appUser.get().getFootballEventsAuthor().stream()
                    .filter(k -> (k.getDate().isEqual(LocalDate.now())) && (k.getTime().isAfter(LocalTime.now().plusSeconds(30))))
                    .collect(Collectors.toList()));
            return myActiveEvents;
        } else return List.of();
    }

    public List<FootballEvent> getMyHistoryEvent(String username){
        Optional<AppUser> appUser = appUserService.findByUsername(username);
        if (appUser.isPresent()){
            List<FootballEvent> myActiveEvents = appUser.get().getFootballEventsParticipants().stream()
                    .filter(k -> k.getDate().isBefore(LocalDate.now()))
                    .collect(Collectors.toList());

            myActiveEvents.addAll(appUser.get().getFootballEventsParticipants().stream()
                    .filter(k -> (k.getDate().isEqual(LocalDate.now())) && (k.getTime().isBefore(LocalTime.now().plusSeconds(30))))
                    .collect(Collectors.toList()));

            myActiveEvents.addAll(appUser.get().getFootballEventsAuthor().stream()
                    .filter(k -> k.getDate().isBefore(LocalDate.now()))
                    .collect(Collectors.toList()));

            myActiveEvents.addAll(appUser.get().getFootballEventsAuthor().stream()
                    .filter(k -> (k.getDate().isEqual(LocalDate.now())) && (k.getTime().isBefore(LocalTime.now().plusSeconds(30))))
                    .collect(Collectors.toList()));
            return myActiveEvents;
        } else return List.of();
    }

    @Override
    public FootballEvent save(FootballEvent object) {
        return footballEventRepo.save(object);
    }

    public Optional<FootballEvent> joinToEvent(Long idAppUser, Long idFootballEvent){
        Optional<FootballEvent> foundEvent = footballEventRepo.findById(idFootballEvent);
        Optional<AppUser> foundAppUser = appUserService.findById(idAppUser);
        if (foundEvent.isPresent() && foundAppUser.isPresent()){
            foundEvent.get().getParticipants().add(foundAppUser.get());
            foundAppUser.get().getFootballEventsParticipants().add(foundEvent.get());
            footballEventRepo.save(foundEvent.get());
            appUserService.save(foundAppUser.get());
            return foundEvent;
        } else throw new IllegalArgumentException("Not found event to join");
    }

    @Override
    public Optional<FootballEvent> delete(Long id) {
        Optional<FootballEvent> deleted = footballEventRepo.findById(id);
        if (deleted.isPresent()){
            footballEventRepo.deleteById(id);
            return deleted;
        } else throw new IllegalArgumentException("Not found User");
    }
}
