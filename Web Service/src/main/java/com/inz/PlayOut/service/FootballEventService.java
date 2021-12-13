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

    public List<FootballEvent> findAllActiveEvent(){
        List<FootballEvent> allEvents = footballEventRepo.findAll();
        List<FootballEvent> list = allEvents.stream()
                .filter(k -> k.getDate().isAfter(LocalDate.now()))
                .collect(Collectors.toList());

        list.addAll(allEvents.stream()
                .filter(k -> (k.getDate().isEqual(LocalDate.now())) && (k.getTime().isAfter(LocalTime.now().plusSeconds(30))))
                .collect(Collectors.toList()));

        return list;
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

    public boolean joinToEvent(String username, Long idFootballEvent){
        Optional<FootballEvent> foundEvent = footballEventRepo.findById(idFootballEvent);
        Optional<AppUser> foundAppUser = appUserService.findByUsername(username);
        if (foundEvent.isPresent() && foundAppUser.isPresent()){
            if (foundEvent.get().getAuthor().equals(foundAppUser.get()) || foundEvent.get().getParticipants().contains(foundAppUser.get()) ||
             foundEvent.get().getVacancies() < 1)
            {
                return false;
            } else {
                foundEvent.get().getParticipants().add(foundAppUser.get());
                foundEvent.get().setVacancies(foundEvent.get().getVacancies() - 1);
                foundAppUser.get().getFootballEventsParticipants().add(foundEvent.get());
                footballEventRepo.save(foundEvent.get());
                return true;
            }
        } else throw new IllegalArgumentException("Not found event to join");
    }

    @Override
    public Optional<FootballEvent> delete(Long id) {
        Optional<FootballEvent> deleted = footballEventRepo.findById(id);
        if (deleted.isPresent()){
            deleted.get().getParticipants().forEach(k -> {
                AppUser appUser = appUserService.findById(k.getId()).get();
                appUser.removeFootballParticipants(deleted.get());
                appUserService.save(appUser);
            });
            footballEventRepo.deleteById(id);
            return deleted;
        } else throw new IllegalArgumentException("Not found event");
    }

    public boolean resignForEvent(Long id, String username){
        Optional<FootballEvent> deleted = footballEventRepo.findById(id);
        Optional<AppUser> appUser = appUserService.findByUsername(username);
        if (deleted.isPresent() && appUser.isPresent()){
            appUser.get().removeFootballParticipants(deleted.get());
            appUserService.save(appUser.get());
            deleted.get().getParticipants().remove(appUser);
            footballEventRepo.save(deleted.get());
            return true;
        } else return false;
    }
}
