package com.inz.PlayOut.model.repositories;

import com.inz.PlayOut.model.entites.VolleyballEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolleyballEventRepo extends JpaRepository<VolleyballEvent, Long> {
}
