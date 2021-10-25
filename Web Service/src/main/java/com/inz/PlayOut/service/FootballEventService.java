package com.inz.PlayOut.service;

import com.inz.PlayOut.model.entites.AppUser;
import com.inz.PlayOut.model.entites.FootballEvent;
import com.inz.PlayOut.model.repositories.FootballEventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public FootballEvent save(FootballEvent object) {
        return footballEventRepo.save(object);
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
