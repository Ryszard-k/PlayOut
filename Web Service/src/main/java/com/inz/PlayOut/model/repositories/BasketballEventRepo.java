package com.inz.PlayOut.model.repositories;

import com.inz.PlayOut.model.entites.BasketballEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketballEventRepo extends JpaRepository<BasketballEvent, Long> {
}
