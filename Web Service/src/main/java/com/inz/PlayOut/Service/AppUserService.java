package com.inz.PlayOut.Service;

import com.inz.PlayOut.Model.Entites.AppUser;
import com.inz.PlayOut.Model.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserService {

    private final UserRepo userRepo;

    @Autowired
    public AppUserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<AppUser> findAll(){
        return userRepo.findAll();
    }

    public Optional<AppUser> findById(Long id){
        Optional<AppUser> found = userRepo.findById(id);
        return found;
    }

    public AppUser save(AppUser appUser){
        return userRepo.save(appUser);
    }

    public Optional<AppUser> deleteById(Long id){
        Optional<AppUser> deleted = userRepo.findById(id);
        userRepo.deleteById(id);

        return deleted;
    }
}
