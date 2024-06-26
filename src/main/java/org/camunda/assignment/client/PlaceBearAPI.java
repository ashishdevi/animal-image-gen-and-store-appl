package org.camunda.assignment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "PlaceBearAPI", url = "${app.bearapi.url}")
public interface PlaceBearAPI {

    @GetMapping(value = "/200/300", name = "GetRandomImageOfBear")
    feign.Response getBearImage();
}
