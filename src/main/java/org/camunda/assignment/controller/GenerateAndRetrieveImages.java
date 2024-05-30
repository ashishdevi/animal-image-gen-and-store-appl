package org.camunda.assignment.controller;


import org.camunda.assignment.dto.ImageData;
import org.camunda.assignment.error.CustomError;
import org.camunda.assignment.service.GenerateAndStoreImageService;
import org.camunda.assignment.util.ValidateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping(value = "/api/animalimages")
public class GenerateAndRetrieveImages {

    @Autowired
    GenerateAndStoreImageService generateAndStoreService;
    @PostMapping(value = "/generateandsave", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ImageData> generateAndSaveImage(ImageData imageData) throws CustomError, IOException {
        ValidateRequest.validateGenerationRequest(imageData);
        return ResponseEntity.ok(generateAndStoreService.createAndStoreAnimalImage(imageData.getImageType()));
    }

    @GetMapping(value = "/{imageId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ImageData> getEmployeesByEmployeeId(@PathVariable String imageId) throws CustomError {
        ValidateRequest.validateGetRequestParam(imageId);
        return ResponseEntity.ok(generateAndStoreService.retrieveImageByImageId(imageId));

    }

}
