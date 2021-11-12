package com.inz.PlayOut.security;

import com.inz.PlayOut.model.entites.AppUser;
import com.inz.PlayOut.model.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AppUserDetailService implements UserDetailsService {

    private final UserRepo userRepo;

    @Autowired
    public AppUserDetailService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<AppUser> appUser = userRepo.findByUsername(s);
        if (appUser.isPresent()){
            return new User(appUser.get().getUsername(), appUser.get().getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority("user")));
        } else throw new UsernameNotFoundException(s);
    }
}
