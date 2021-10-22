package com.inz.PlayOut.configuration;

import graphql.scalars.ExtendedScalars;
import graphql.schema.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScalarConf {

    @Bean
    public GraphQLScalarType date(){
        return ExtendedScalars.Date;
        }
}
