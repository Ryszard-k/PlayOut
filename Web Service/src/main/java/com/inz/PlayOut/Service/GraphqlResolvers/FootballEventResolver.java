package com.inz.PlayOut.Service.GraphqlResolvers;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.inz.PlayOut.Model.Entites.FootballEvent;
import com.inz.PlayOut.Service.FootballEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FootballEventResolver implements GraphQLQueryResolver {

    private final FootballEventService footballEventService;

    @Autowired
    public FootballEventResolver(FootballEventService footballEventService) {
        this.footballEventService = footballEventService;
    }

    public List<FootballEvent> getAllFootballEvent(){
        return footballEventService.findAll();
    }
}
