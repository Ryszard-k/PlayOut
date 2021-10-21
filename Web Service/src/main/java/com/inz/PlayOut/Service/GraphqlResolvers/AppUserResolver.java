package com.inz.PlayOut.Service.GraphqlResolvers;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.inz.PlayOut.Model.Entites.AppUser;
import com.inz.PlayOut.Service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AppUserResolver implements GraphQLMutationResolver, GraphQLQueryResolver {

    private final AppUserService appUserService;

    @Autowired
    public AppUserResolver(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    public List<AppUser> getAllAppUser(){
        return appUserService.findAll();
    }

    public Optional<AppUser> findByIdAppUser(Long id){
        return appUserService.findById(id);
    }

    public AppUser createAppUser(AppUser appUser){
        return appUserService.save(appUser);
    }

    public Optional<AppUser> deleteAppUser(Long id){
        return appUserService.deleteById(id);
    }
}
