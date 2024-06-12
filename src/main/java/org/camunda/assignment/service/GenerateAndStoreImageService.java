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

    private static final String IMAGE_TYPE_PROCESS_VARIABLE_NAME="imageType";
    private static final String IMAGE_ID_PROCESS_VARIABLE_NAME="imageId";
    @Autowired
    ImageRepository imageRepository;

    @Autowired
    private ZeebeClient zeebeClient;


    public ImageData createAndStoreAnimalImage(String imageType) throws CustomError {
        final ProcessInstanceResult processInstanceResult =
                zeebeClient
                        .newCreateInstanceCommand()
                        .bpmnProcessId(bpmProcessId)
                        .latestVersion()
                        .variables(Map.of(IMAGE_TYPE_PROCESS_VARIABLE_NAME, new String(imageType)))
                        .withResult() // to await the completion of process execution and return result
                        .send()
//                        .exceptionally(t -> {throw new RuntimeException("Could not throw BPMN error: " + t.getMessage(), t);})
                        .join();

        ImageData imageData = new ImageData();

        if(processInstanceResult.getVariablesAsMap().containsKey("ErrorCode")){
            throw new CustomError("INTERNAL_SERVER_ERROR", (String) processInstanceResult.getVariable("ErrorCode"),"500");
        }
        imageData.setImageId((String) processInstanceResult.getVariable(IMAGE_ID_PROCESS_VARIABLE_NAME));
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
