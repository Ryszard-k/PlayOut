package com.inz.PlayOut.Service.GraphqlResolvers;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.inz.PlayOut.Model.Entites.AppUser;
import com.inz.PlayOut.Service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppUserResolver implements GraphQLMutationResolver {

    private final AppUserService appUserService;

    @Autowired
    public AppUserResolver(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    public AppUser createAppUser(AppUser appUser){
        return appUserService.save(appUser);
    }
}
