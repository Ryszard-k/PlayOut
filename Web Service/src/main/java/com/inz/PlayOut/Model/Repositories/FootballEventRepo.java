package com.inz.PlayOut.Model.Repositories;

import com.inz.PlayOut.Model.Entites.FootballEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FootballEventRepo extends CrudRepository<FootballEvent, Long> {
}
