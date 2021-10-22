package com.inz.PlayOut.service;

import com.inz.PlayOut.Model.entites.AppUser;
import com.inz.PlayOut.Model.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public record AppUserService(UserRepo userRepo) {

    @Autowired
    public AppUserService {
    }

    public List<AppUser> findAll() {
        return userRepo.findAll();
    }

    public Optional<AppUser> findById(Long id) {
        return userRepo.findById(id);
    }

    public AppUser save(AppUser appUser) {
        return userRepo.save(appUser);
    }

    public Optional<AppUser> deleteById(Long id) {
        Optional<AppUser> deleted = userRepo.findById(id);
        userRepo.deleteById(id);

        return deleted;
    }
}
