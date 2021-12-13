package com.inz.PlayOut.service;

import com.inz.PlayOut.model.entites.AppUser;
import com.inz.PlayOut.model.entites.VolleyballEvent;
import com.inz.PlayOut.model.repositories.VolleyballEventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public record VolleyballEventService(VolleyballEventRepo volleyballEventRepo,
                                     AppUserService appUserService) implements CRUDService<VolleyballEvent> {

    @Autowired
    public VolleyballEventService {
    }

    @Override
    public List<VolleyballEvent> findAll() {
        return volleyballEventRepo.findAll();
    }

    @Override
    public Optional<VolleyballEvent> findById(Long id) {
        return volleyballEventRepo.findById(id);
    }

    public List<VolleyballEvent> findAllActiveEvent(){
        List<VolleyballEvent> allEvents = volleyballEventRepo.findAll();
        List<VolleyballEvent> list = allEvents.stream()
                .filter(k -> k.getDate().isAfter(LocalDate.now()))
                .collect(Collectors.toList());

        list.addAll(allEvents.stream()
                .filter(k -> (k.getDate().isEqual(LocalDate.now())) && (k.getTime().isAfter(LocalTime.now().plusSeconds(30))))
                .collect(Collectors.toList()));

        return list;
    }

    public List<VolleyballEvent> getMyActiveEvent(String username){
        Optional<AppUser> appUser = appUserService.findByUsername(username);
        if (appUser.isPresent()){
            List<VolleyballEvent> myActiveEvents = appUser.get().getVolleyballEventsParticipants().stream()
                    .filter(k -> k.getDate().isAfter(LocalDate.now()))
                    .collect(Collectors.toList());

            myActiveEvents.addAll(appUser.get().getVolleyballEventsParticipants().stream()
                    .filter(k -> (k.getDate().isEqual(LocalDate.now())) && (k.getTime().isAfter(LocalTime.now().plusSeconds(30))))
                    .collect(Collectors.toList()));

            myActiveEvents.addAll(appUser.get().getVolleyballEventsAuthor().stream()
                    .filter(k -> k.getDate().isAfter(LocalDate.now()))
                    .collect(Collectors.toList()));

            myActiveEvents.addAll(appUser.get().getVolleyballEventsAuthor().stream()
                    .filter(k -> (k.getDate().isEqual(LocalDate.now())) && (k.getTime().isAfter(LocalTime.now().plusSeconds(30))))
                    .collect(Collectors.toList()));
            return myActiveEvents;
        } else return List.of();
    }

    public List<VolleyballEvent> getMyHistoryEvent(String username){
        Optional<AppUser> appUser = appUserService.findByUsername(username);
        if (appUser.isPresent()){
            List<VolleyballEvent> myActiveEvents = appUser.get().getVolleyballEventsParticipants().stream()
                    .filter(k -> k.getDate().isBefore(LocalDate.now()))
                    .collect(Collectors.toList());

            myActiveEvents.addAll(appUser.get().getVolleyballEventsParticipants().stream()
                    .filter(k -> (k.getDate().isEqual(LocalDate.now())) && (k.getTime().isBefore(LocalTime.now().plusSeconds(30))))
                    .collect(Collectors.toList()));

            myActiveEvents.addAll(appUser.get().getVolleyballEventsAuthor().stream()
                    .filter(k -> k.getDate().isBefore(LocalDate.now()))
                    .collect(Collectors.toList()));

            myActiveEvents.addAll(appUser.get().getVolleyballEventsAuthor().stream()
                    .filter(k -> (k.getDate().isEqual(LocalDate.now())) && (k.getTime().isBefore(LocalTime.now().plusSeconds(30))))
                    .collect(Collectors.toList()));
            return myActiveEvents;
        } else return List.of();
    }

    @Override
    public VolleyballEvent save(VolleyballEvent object) {
        return volleyballEventRepo.save(object);
    }

    public boolean joinToEvent(String username, Long idBasketball){
        Optional<VolleyballEvent> foundEvent = volleyballEventRepo.findById(idBasketball);
        Optional<AppUser> foundAppUser = appUserService.findByUsername(username);
        if (foundEvent.isPresent() && foundAppUser.isPresent()){
            if (foundEvent.get().getAuthorVolleyball().equals(foundAppUser.get()) || foundEvent.get().getParticipantsVolleyball().contains(foundAppUser.get()) ||
                    foundEvent.get().getVacancies() < 1)
            {
                return false;
            } else {
                foundEvent.get().getParticipantsVolleyball().add(foundAppUser.get());
                foundEvent.get().setVacancies(foundEvent.get().getVacancies() - 1);
                foundAppUser.get().getVolleyballEventsParticipants().add(foundEvent.get());
                volleyballEventRepo.save(foundEvent.get());
                appUserService.save(foundAppUser.get());
                return true;
            }
        } else throw new IllegalArgumentException("Not found event to join");
    }

    @Override
    public Optional<VolleyballEvent> delete(Long id) {
        Optional<VolleyballEvent> deleted = volleyballEventRepo.findById(id);
        if (deleted.isPresent()){
            deleted.get().getParticipantsVolleyball().forEach(k -> {
                AppUser appUser = appUserService.findById(k.getId()).get();
                appUser.removeVolleyballParticipants(deleted.get());
                appUserService.save(appUser);
            });
            volleyballEventRepo.deleteById(id);
            return deleted;
        } else throw new IllegalArgumentException("Not found event");
    }
}
