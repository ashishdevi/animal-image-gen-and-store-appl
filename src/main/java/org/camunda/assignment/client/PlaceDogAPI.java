package org.camunda.assignment.client;

import org.camunda.assignment.configuration.PlaceAPIClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "PlaceDogAPI", url = "${app.dogapi.url}", configuration = PlaceAPIClientConfiguration.class)
public interface PlaceDogAPI {

    @GetMapping(value = "/300/200", name = "GetRandomImageOfDog")
    feign.Response getDogImage();
}
