package com.inz.PlayOut.Service;

import com.inz.PlayOut.Model.Entites.FootballEvent;
import com.inz.PlayOut.Model.Repositories.FootballEventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FootballEventService {

    private final FootballEventRepo footballEventRepo;

    @Autowired
    public FootballEventService(FootballEventRepo footballEventRepo) {
        this.footballEventRepo = footballEventRepo;
    }

    public List<FootballEvent> findAll(){
        return footballEventRepo.findAll();
    }

    public FootballEvent save(FootballEvent footballEvent){
        return footballEventRepo.save(footballEvent);
    }
}
