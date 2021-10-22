package com.inz.PlayOut.service.resolvers;

import com.inz.PlayOut.Model.entites.FootballEvent;
import com.inz.PlayOut.service.FootballEventService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public record FootballEventResolver(
        FootballEventService footballEventService) implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    public FootballEventResolver {
    }

    public List<FootballEvent> getAllFootballEvent() {
        return footballEventService.findAll();
    }

    public Optional<FootballEvent> findByIdFootballEvent(Long id) {
        return footballEventService.findById(id);
    }

    public FootballEvent createFootballEvent(FootballEvent footballEvent, Long id) {
        return footballEventService.save(footballEvent, id);
    }

    public Optional<FootballEvent> deleteFootballEvent(Long id) {
        return footballEventService.deleteById(id);
    }
}
