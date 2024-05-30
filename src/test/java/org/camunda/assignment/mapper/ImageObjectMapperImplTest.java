package org.camunda.assignment.mapper;

import org.camunda.assignment.dto.ImageData;
import org.camunda.assignment.model.Image;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImageObjectMapperImplTest {

    @Test
    void toDTO() {
        ImageObjectMapper objectMapper = new ImageObjectMapperImpl();
        Image image = new Image();
        image.setImage(null);
        image.setImageId("123456");
        image.setImageType("DOG");
        ImageData imageData = objectMapper.toDTO(image);
        assertEquals(image.getImageId(),imageData.getImageId());
        assertEquals(image.getImageType(),imageData.getImageType());

    }

    @Test
    void toEntity() {
        ImageObjectMapper objectMapper = new ImageObjectMapperImpl();
        ImageData imageData = new ImageData();
        imageData.setImage(null);
        imageData.setImageId("123456");
        imageData.setImageType("DOG");
        Image image = objectMapper.toEntity(imageData);
        assertEquals(imageData.getImageId(),image.getImageId());
        assertEquals(imageData.getImageType(),image.getImageType());

    }
}