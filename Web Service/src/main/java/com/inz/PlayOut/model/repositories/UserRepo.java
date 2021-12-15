package com.inz.PlayOut.model.repositories;

import com.inz.PlayOut.model.entites.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "update app_user e set e.firebase_token =:firebase_token where e.username =:username", nativeQuery = true)
    void updateToken(@Param(value = "username") String username, @Param(value = "firebase_token") String firebase_token);
}
