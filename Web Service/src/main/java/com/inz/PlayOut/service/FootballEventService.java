package com.inz.PlayOut.service;

import com.inz.PlayOut.model.entites.AppUser;
import com.inz.PlayOut.model.entites.FootballEvent;
import com.inz.PlayOut.model.repositories.FootballEventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public record FootballEventService(FootballEventRepo footballEventRepo, AppUserService appUserService) {

    @Autowired
    public FootballEventService {
    }

    public List<FootballEvent> findAll() {
        return footballEventRepo.findAll();
    }

    public Optional<FootballEvent> findById(Long id) {
        return footballEventRepo.findById(id);
    }

    public FootballEvent save(FootballEvent footballEvent, Long id) throws IllegalArgumentException {
        Optional<AppUser> appUser = appUserService.findById(id);
        if (appUser.isPresent()) {
            footballEvent.setAuthor(appUser.get());
            return footballEventRepo.save(footballEvent);
        } else throw new IllegalArgumentException("Not found User");
    }

    public Optional<FootballEvent> deleteById(Long id) {
        Optional<FootballEvent> deleted = footballEventRepo.findById(id);
        if (deleted.isPresent()){
            footballEventRepo.deleteById(id);
            return deleted;
        } else throw new IllegalArgumentException("Not found User");
    }
}
