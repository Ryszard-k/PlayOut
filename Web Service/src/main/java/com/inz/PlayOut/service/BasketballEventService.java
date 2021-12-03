package com.inz.PlayOut.service;

import com.inz.PlayOut.model.entites.AppUser;
import com.inz.PlayOut.model.entites.BasketballEvent;
import com.inz.PlayOut.model.entites.FootballEvent;
import com.inz.PlayOut.model.repositories.BasketballEventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public record BasketballEventService(BasketballEventRepo basketballEventRepo, AppUserService appUserService) implements CRUDService<BasketballEvent>{

    @Autowired
    public BasketballEventService {
    }

    @Override
    public List<BasketballEvent> findAll() {
        return basketballEventRepo.findAll();
    }

    @Override
    public Optional<BasketballEvent> findById(Long id) {
        return basketballEventRepo.findById(id);
    }

    public List<BasketballEvent> findAllActiveEvent(){
        List<BasketballEvent> allEvents = basketballEventRepo.findAll();
        List<BasketballEvent> list = allEvents.stream()
                .filter(k -> k.getDate().isAfter(LocalDate.now()))
                .collect(Collectors.toList());

        list.addAll(allEvents.stream()
                .filter(k -> (k.getDate().isEqual(LocalDate.now())) && (k.getTime().isAfter(LocalTime.now().plusSeconds(30))))
                .collect(Collectors.toList()));

        return list;
    }

    public List<BasketballEvent> getMyActiveEvent(String username){
        Optional<AppUser> appUser = appUserService.findByUsername(username);
        if (appUser.isPresent()){
            List<BasketballEvent> myActiveEvents = appUser.get().getBasketballEventsParticipants().stream()
                    .filter(k -> k.getDate().isAfter(LocalDate.now()))
                    .collect(Collectors.toList());

            myActiveEvents.addAll(appUser.get().getBasketballEventsParticipants().stream()
                    .filter(k -> (k.getDate().isEqual(LocalDate.now())) && (k.getTime().isAfter(LocalTime.now().plusSeconds(30))))
                    .collect(Collectors.toList()));

            myActiveEvents.addAll(appUser.get().getBasketballEventsAuthor().stream()
                    .filter(k -> k.getDate().isAfter(LocalDate.now()))
                    .collect(Collectors.toList()));

            myActiveEvents.addAll(appUser.get().getBasketballEventsAuthor().stream()
                    .filter(k -> (k.getDate().isEqual(LocalDate.now())) && (k.getTime().isAfter(LocalTime.now().plusSeconds(30))))
                    .collect(Collectors.toList()));
            return myActiveEvents;
        } else return List.of();
    }

    public List<BasketballEvent> getMyHistoryEvent(String username){
        Optional<AppUser> appUser = appUserService.findByUsername(username);
        if (appUser.isPresent()){
            List<BasketballEvent> myActiveEvents = appUser.get().getBasketballEventsParticipants().stream()
                    .filter(k -> k.getDate().isBefore(LocalDate.now()))
                    .collect(Collectors.toList());

            myActiveEvents.addAll(appUser.get().getBasketballEventsParticipants().stream()
                    .filter(k -> (k.getDate().isEqual(LocalDate.now())) && (k.getTime().isBefore(LocalTime.now().plusSeconds(30))))
                    .collect(Collectors.toList()));

            myActiveEvents.addAll(appUser.get().getBasketballEventsAuthor().stream()
                    .filter(k -> k.getDate().isBefore(LocalDate.now()))
                    .collect(Collectors.toList()));

            myActiveEvents.addAll(appUser.get().getBasketballEventsAuthor().stream()
                    .filter(k -> (k.getDate().isEqual(LocalDate.now())) && (k.getTime().isBefore(LocalTime.now().plusSeconds(30))))
                    .collect(Collectors.toList()));
            return myActiveEvents;
        } else return List.of();
    }

    @Override
    public BasketballEvent save(BasketballEvent object) {
        return basketballEventRepo.save(object);
    }

    public boolean joinToEvent(String username, Long idBasketball){
        Optional<BasketballEvent> foundEvent = basketballEventRepo.findById(idBasketball);
        Optional<AppUser> foundAppUser = appUserService.findByUsername(username);
        if (foundEvent.isPresent() && foundAppUser.isPresent()){
            if (foundEvent.get().getAuthorBasketball().equals(foundAppUser.get()) || foundEvent.get().getParticipantsBasketball().contains(foundAppUser.get()) ||
                    foundEvent.get().getVacancies() < 1)
            {
                return false;
            } else {
                foundEvent.get().getParticipantsBasketball().add(foundAppUser.get());
                foundEvent.get().setVacancies(foundEvent.get().getVacancies() - 1);
                foundAppUser.get().getBasketballEventsParticipants().add(foundEvent.get());
                basketballEventRepo.save(foundEvent.get());
                appUserService.save(foundAppUser.get());
                return true;
            }
        } else throw new IllegalArgumentException("Not found event to join");
    }

    @Override
    public Optional<BasketballEvent> delete(Long id) {
        Optional<BasketballEvent> deleted = basketballEventRepo.findById(id);
        if (deleted.isPresent()){
            basketballEventRepo.deleteById(id);
            return deleted;
        } else throw new IllegalArgumentException("Not found event");
    }
}
