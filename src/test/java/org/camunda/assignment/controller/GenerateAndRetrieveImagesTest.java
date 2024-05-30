package org.camunda.assignment.controller;

import org.camunda.assignment.dto.ImageData;
import org.camunda.assignment.error.CustomError;
import org.camunda.assignment.service.GenerateAndStoreImageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.configuration.IMockitoConfiguration;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.isA;

@ExtendWith(MockitoExtension.class)
class GenerateAndRetrieveImagesTest {

    @Mock
    GenerateAndStoreImageService generateAndStoreService;

    @InjectMocks
    GenerateAndRetrieveImages generateAndRetrieveImages;
    @Test
    void generateAndSaveImage() throws CustomError {
        when(generateAndStoreService.createAndStoreAnimalImage(isA(String.class))).thenReturn(new ImageData("66581b153b121d498cb0d09b", "DOG", null));
        ImageData imageData = generateAndRetrieveImages.generateAndSaveImage(new ImageData(null,"DOG", null)).getBody();
        assertEquals(imageData.getImageType(), "DOG");
    }

    @Test
    void getEmployeesByEmployeeId() throws CustomError {
        when(generateAndStoreService.retrieveImageByImageId(isA(String.class))).thenReturn(new ImageData("66581b153b121d498cb0d09b", "DOG", null));
        ImageData imageData = generateAndRetrieveImages.getImagesByImageId("66581b153b121d498cb0d09b").getBody();
        assertEquals(imageData.getImageId(), "66581b153b121d498cb0d09b");
    }
}