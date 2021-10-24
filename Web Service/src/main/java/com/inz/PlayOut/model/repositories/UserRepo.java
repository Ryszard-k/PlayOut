package com.inz.PlayOut.model.repositories;

import com.inz.PlayOut.model.entites.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<AppUser, Long> {
}
