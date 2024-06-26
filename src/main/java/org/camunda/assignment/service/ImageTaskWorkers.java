package org.camunda.assignment.service;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import io.camunda.zeebe.spring.client.exception.ZeebeBpmnError;
import lombok.extern.slf4j.Slf4j;
import org.camunda.assignment.error.CustomError;
import org.camunda.assignment.model.Image;
import org.camunda.assignment.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Slf4j
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

    private static final String PROCESS_VARIABLE_NAME="imageId";

    @JobWorker(type = "generateAndSaveDogImage")
    public Map<String, Object> generateAndSaveDogImage(final JobClient client, final ActivatedJob job, @Variable String imageType) throws CustomError {
        Image image;
        try {
        Image newImage = new Image();
        newImage.setImageType(imageType);
        newImage.setImage(generateDogImageService.generateImage());
        image =  imageRepository.save(newImage);

        } catch (CustomError e) {
            throw new ZeebeBpmnError("INTERNAL_SERVER_ERROR","Internal server error has occured");
        }
        if(null!= image){
            return Collections.singletonMap(PROCESS_VARIABLE_NAME, image.getImageId());
        }
        else return Collections.singletonMap(PROCESS_VARIABLE_NAME, null);
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
            throw new ZeebeBpmnError("INTERNAL_SERVER_ERROR","Internal server error has occured");
        }
        if(null!= image) return Collections.singletonMap(PROCESS_VARIABLE_NAME, image.getImageId());
        else return Collections.singletonMap(PROCESS_VARIABLE_NAME, null);
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
            throw new ZeebeBpmnError("INTERNAL_SERVER_ERROR","Internal server error has occured");
        }
        if(null!= image) return Collections.singletonMap(PROCESS_VARIABLE_NAME, image.getImageId());
        else return Collections.singletonMap(PROCESS_VARIABLE_NAME, null);
    }

    @JobWorker(type = "commonErrorLogger")
    public void commonErrorLogger(final JobClient client, final ActivatedJob job) {
        log.error("Process failed for process id: {} process versiob: {} and ",job.getBpmnProcessId(),job.getProcessDefinitionVersion());
    }
}
