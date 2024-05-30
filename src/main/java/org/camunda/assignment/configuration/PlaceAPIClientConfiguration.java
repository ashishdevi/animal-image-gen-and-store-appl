package org.camunda.assignment.configuration;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlaceAPIClientConfiguration {

    @Bean
    public ErrorDecoder errorDecoder()
    {
        return new PlaceAPIErrorDecoder();
    }
}
