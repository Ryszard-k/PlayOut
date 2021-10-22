package com.inz.PlayOut.service.resolvers;

import com.inz.PlayOut.Model.entites.AppUser;
import com.inz.PlayOut.service.AppUserService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public record AppUserResolver(
        AppUserService appUserService) implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    public AppUserResolver {
    }

    public List<AppUser> getAllAppUser() {
        return appUserService.findAll();
    }

    public Optional<AppUser> findByIdAppUser(Long id) {
        return appUserService.findById(id);
    }

    public AppUser createAppUser(AppUser appUser) {
        return appUserService.save(appUser);
    }

    public Optional<AppUser> deleteAppUser(Long id) {
        return appUserService.deleteById(id);
    }
}
