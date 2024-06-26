package org.camunda.assignment.service;

import feign.Response;
import org.camunda.assignment.client.PlaceKittenAPI;
import org.camunda.assignment.error.CustomError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GenerateKittenImageServiceTest {

    @Mock
    PlaceKittenAPI placeKittenAPI;

    @Mock
    feign.Response response = mock(feign.Response.class);

    @Mock
    feign.Response.Body body = mock(feign.Response.Body.class);


    @InjectMocks
    GenerateKittenImageService generateKittenImageService;
    @Test
    void generateImageNegativeScenario() throws IOException, CustomError {
        when(placeKittenAPI.getKittenImage()).thenReturn(response);
        when(response.body()).thenReturn(body);
        when(response.body().asInputStream()).thenReturn(new ByteArrayInputStream(Charset.forName("UTF-16").encode("this is test string").array()));
        CustomError error = Assertions.assertThrows(CustomError.class, () -> generateKittenImageService.generateImage());
        assertEquals("INTERNAL_SERVER_ERROR",error.getCode());
    }
}