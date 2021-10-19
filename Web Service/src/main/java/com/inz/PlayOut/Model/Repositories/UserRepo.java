package com.inz.PlayOut.Model.Repositories;

import com.inz.PlayOut.Model.Entites.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<AppUser, Long> {
}
