package com.inz.PlayOut.model.repositories;

import com.inz.PlayOut.model.entites.FootballEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FootballEventRepo extends JpaRepository<FootballEvent, Long> {
}
