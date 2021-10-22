package com.inz.PlayOut.Model.repositories;

import com.inz.PlayOut.Model.entites.FootballEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FootballEventRepo extends JpaRepository<FootballEvent, Long> {
}
