package org.camunda.assignment.service;

import feign.Response;
import org.camunda.assignment.client.PlaceDogAPI;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenerateDogImageServiceTest {

    @Mock
    PlaceDogAPI placeDogAPI;

    @Mock
    Response response = mock(Response.class);

    @Mock
    Response.Body body = mock(Response.Body.class);


    @InjectMocks
    GenerateDogImageService generateDogImageService;
    @Test
    void generateImageNegativeScenario() throws IOException, CustomError {
        when(placeDogAPI.getDogImage()).thenReturn(response);
        when(response.body()).thenReturn(body);
        when(response.body().asInputStream()).thenReturn(new ByteArrayInputStream(Charset.forName("UTF-16").encode("this is test string").array()));
        CustomError error = Assertions.assertThrows(CustomError.class, () -> generateDogImageService.generateImage());
        assertEquals("INTERNAL_SERVER_ERROR",error.getCode());
    }
}