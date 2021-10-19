package com.inz.PlayOut.Service;

import com.inz.PlayOut.Model.Entites.AppUser;
import com.inz.PlayOut.Model.Entites.FootballEvent;
import com.inz.PlayOut.Model.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {

    private UserRepo userRepo;

    @Autowired
    public AppUserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public AppUser save(AppUser appUser){
        return userRepo.save(appUser);
    }
}
