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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GenerateAndStoreImageService {

    @Autowired
    ImageObjectMapperImpl imageObjectMapper;

    @Value("${app.bpnm.process.id}")
    private String bpmProcessId;

    private static final String PROCESS_VARIABLE_NAME="imageType";
    @Autowired
    ImageRepository imageRepository;

    @Autowired
    private ZeebeClient zeebeClient;

    private static ProcessInstanceResult apply(Throwable t) throws CustomError{
        if (t.getMessage().equals("INVALID_IMAGE_TYPE")) {
            throw new CustomError("IMAGE_TYPE_INVALID", "Image type is not valid", "400");
        } else {
            throw new CustomError("IMAGE_TYPE_INVALID", "Image type is not valid", "400");
        }
    }

    public ImageData createAndStoreAnimalImage(String imageType) throws CustomError {
        final ProcessInstanceResult processInstanceResult =
                zeebeClient
                        .newCreateInstanceCommand()
                        .bpmnProcessId(bpmProcessId)
                        .latestVersion()
                        .variables(Map.of(PROCESS_VARIABLE_NAME, new String(imageType)))
                        .withResult() // to await the completion of process execution and return result
                        .send()
//                        .exceptionally(t -> {throw new RuntimeException("Could not throw BPMN error: " + t.getMessage(), t);})
                        .join();

        ImageData imageData = new ImageData();

        if(processInstanceResult.getVariablesAsMap().containsKey("ErrorCode")){
            throw new CustomError("INTERNAL_SERVER_ERROR", (String) processInstanceResult.getVariable("ErrorCode"),"500");
        }
        imageData.setImageId((String) processInstanceResult.getVariable(PROCESS_VARIABLE_NAME));
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
