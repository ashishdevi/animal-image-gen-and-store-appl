package org.camunda.assignment.service;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import org.camunda.assignment.error.CustomError;
import org.camunda.assignment.model.Image;
import org.camunda.assignment.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
public class ImageTaskWorkers {

    @Autowired
    GenerateDogImageService generateDogImageService;

    @Autowired
    GenerateKittenImageService generateKittenImageService;

    @Autowired
    GenerateBearImageService generateBearImageService;
    @Autowired
    ImageRepository imageRepository;

    @JobWorker(type = "generateAndSaveDogImage")
    public Map<String, Object> generateAndSaveDogImage(final JobClient client, final ActivatedJob job, @Variable String imageType) {
        Image image;
        try {
        Image newImage = new Image();
        newImage.setImageType(imageType);
        newImage.setImage(generateDogImageService.generateImage());
        image =  imageRepository.save(newImage);

        } catch (CustomError e) {
            throw new RuntimeException(e);
        }
        if(null!= image) return Collections.singletonMap("imageId", image.getImageId());
        else return Collections.singletonMap("imageId", null);
    }

    @JobWorker(type = "generateAndSaveBearImage")
    public Map<String, Object> generateAndSaveBearImage(final JobClient client, final ActivatedJob job, @Variable String imageType) {
        Image image;
        try {
            Image newImage = new Image();
            newImage.setImageType(imageType);
            newImage.setImage(generateBearImageService.generateImage());
            image =  imageRepository.save(newImage);

        } catch (CustomError e) {
            throw new RuntimeException(e);
        }
        if(null!= image) return Collections.singletonMap("imageId", image.getImageId());
        else return Collections.singletonMap("imageId", null);
    }


    @JobWorker(type = "generateAndSaveKittenImage")
    public Map<String, Object> generateAndSaveKittenImage(final JobClient client, final ActivatedJob job, @Variable String imageType) {
        Image image;
        try {
            Image newImage = new Image();
            newImage.setImageType(imageType);
            newImage.setImage(generateKittenImageService.generateImage());
            image =  imageRepository.save(newImage);

        } catch (CustomError e) {
            throw new RuntimeException(e);
        }
        if(null!= image) return Collections.singletonMap("imageId", image.getImageId());
        else return Collections.singletonMap("imageId", null);
    }
}
