package org.camunda.assignment.service;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceResult;
import io.camunda.zeebe.process.test.api.ZeebeTestEngine;
import io.camunda.zeebe.process.test.assertions.BpmnAssert;
import io.camunda.zeebe.process.test.assertions.MessageAssert;
import io.camunda.zeebe.process.test.assertions.ProcessInstanceAssert;
import io.camunda.zeebe.process.test.extension.testcontainer.ZeebeProcessTest;
import io.camunda.zeebe.process.test.filters.RecordStream;
import org.camunda.assignment.dto.ImageData;
import org.camunda.assignment.error.CustomError;
import org.camunda.assignment.mapper.ImageObjectMapperImpl;
import org.camunda.assignment.model.Image;
import org.camunda.assignment.repository.ImageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ZeebeProcessTest
class GenerateAndStoreImageServiceTest {

    @Autowired
    private ZeebeTestEngine engine;
    private ZeebeClient client;


    @Mock
    ImageRepository imageRepository;

    @Mock
    ImageObjectMapperImpl imageObjectMapper;

    @InjectMocks
    GenerateAndStoreImageService generateAndStoreImageService;
    @Value("${app.bpnm.process.id}")
    private String bpmProcessId;

    private static final String PROCESS_VARIABLE_NAME="imageType";
    @Test
    void createAndStoreAnimalImage() {
//        final ProcessInstanceResult processInstanceResult = client
//                .newCreateInstanceCommand()
//                .bpmnProcessId(bpmProcessId)
//                .latestVersion()
//                .variables(Map.of(PROCESS_VARIABLE_NAME, new String("DOG")))
//                .withResult() // to await the completion of process execution and return result
//                .send()
////                        .exceptionally(t -> {throw new RuntimeException("Could not throw BPMN error: " + t.getMessage(), t);})
//                .join();
//        ProcessInstanceAssert assertions = BpmnAssert.assertThat(processInstanceResult).isActive();

    }

    @Test
    void retrieveImageByImageId() throws CustomError {
        Image image = new Image();
        image.setImage(null);
        image.setImageId("123456");
        image.setImageType("DOG");
        when(imageRepository.findItemByimageId(isA(String.class))).thenReturn(image);
        ImageData retrievedImage = generateAndStoreImageService.retrieveImageByImageId("123455");
        assertEquals("123456",image.getImageId());
    }

    @Test
    void retrieveImageByImageIdNegative() throws CustomError {
        Image image = new Image();
        image.setImage(null);
        image.setImageId("123456");
        image.setImageType("DOG");
        when(imageRepository.findItemByimageId(isA(String.class))).thenReturn(null);
        CustomError error = assertThrows(CustomError.class, () -> generateAndStoreImageService.retrieveImageByImageId("123455"));
        assertEquals("IMAGE_NOT_FOUND", error.getCode());
    }
}