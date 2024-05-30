package org.camunda.assignment.service;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceResult;
import org.camunda.assignment.client.PlaceDogAPI;
import org.camunda.assignment.dto.ImageData;
import org.camunda.assignment.error.CustomError;
import org.camunda.assignment.mapper.ImageObjectMapperImpl;
import org.camunda.assignment.model.Image;
import org.camunda.assignment.repository.ImageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GenerateAndStoreImageService {

    @Autowired
    PlaceDogAPI placeDogAPI;

@Autowired
ImageObjectMapperImpl imageObjectMapper;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    GenerateDogImageService generateDogImageService;

    @Autowired
    private ZeebeClient zeebeClient;
    public ImageData createAndStoreAnimalImage(String imageType) throws CustomError {
        final ProcessInstanceResult processInstanceResult =
                zeebeClient
                        .newCreateInstanceCommand()
                        .bpmnProcessId("Process_0rle9ea")
                        .latestVersion()
                        .variables(Map.of("imageType", new String(imageType)))
                        .withResult() // to await the completion of process execution and return result
                        .send()
                        .join();

        ImageData imageData = new ImageData();
        imageData.setImageId((String) processInstanceResult.getVariable("imageId"));
        imageData.setImageType(imageType);
        return imageData;

    }

    public ImageData retrieveImageByImageId(String imageId) throws CustomError {
        Image savedImage = imageRepository.findItemByimageId(imageId);
        if(null==savedImage){
            throw new CustomError("IMAGE_NOT_FOUND","Image does not exists against the imageId","404");
        }
        return imageObjectMapper.toDTO(savedImage);

    }
}