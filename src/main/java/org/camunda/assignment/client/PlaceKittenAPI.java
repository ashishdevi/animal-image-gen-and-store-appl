package org.camunda.assignment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "PlaceKittenAPI", url = "${app.kittenapi.url}")
public interface PlaceKittenAPI {

    @GetMapping(value = "/200/300", name = "GetRandomImageOfKitten")
    feign.Response getKittenImage();
}
