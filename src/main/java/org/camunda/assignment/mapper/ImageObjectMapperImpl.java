package org.camunda.assignment.mapper;

import org.bouncycastle.util.encoders.Base64;
import org.bson.types.Binary;
import org.camunda.assignment.dto.ImageData;
import org.camunda.assignment.model.Image;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;


@Component
public class ImageObjectMapperImpl implements  ImageObjectMapper {

    @Override
    public ImageData toDTO(Image image) {
        if (image == null) {
            return null;
        }
        ImageData imageData = new ImageData();
        imageData.setImageType(image.getImageType());
        imageData.setImage(new String(Base64.encode(image.getImage().getData()), StandardCharsets.UTF_8));
        imageData.setImageId(image.getImageId());
        return imageData;
    }

    @Override
    public Image toEntity(ImageData imageDto) {
        if (imageDto == null) {
            return null;
        }
        Image image = new Image();
        image.setImageType(imageDto.getImageType());
        image.setImage(new Binary(imageDto.getImage().getBytes(StandardCharsets.UTF_8)));
        image.setImageId(imageDto.getImageId());
        return image;
    }
}
