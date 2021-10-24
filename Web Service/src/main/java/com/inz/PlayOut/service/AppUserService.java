package com.inz.PlayOut.service;

import com.inz.PlayOut.model.entites.AppUser;
import com.inz.PlayOut.model.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public record AppUserService(UserRepo userRepo) implements CRUDService<AppUser>{

    @Autowired
    public AppUserService {
    }

    @Override
    public List<AppUser> findAll() {
        return userRepo.findAll();
    }

    @Override
    public Optional<AppUser> findById(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public AppUser save(AppUser object) {
        return userRepo.save(object);
    }

    @Override
    public Optional<AppUser> delete(Long id) {
        Optional<AppUser> deleted = userRepo.findById(id);
        userRepo.deleteById(id);

        return deleted;
    }
}
