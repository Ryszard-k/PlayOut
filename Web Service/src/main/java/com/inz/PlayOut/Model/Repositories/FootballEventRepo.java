package com.inz.PlayOut.Model.Repositories;

import com.inz.PlayOut.Model.Entites.FootballEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FootballEventRepo extends JpaRepository<FootballEvent, Long> {
}
