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
        if(null!=image.getImage()) {
            imageData.setImage(new String(Base64.encode(image.getImage().getData()), StandardCharsets.UTF_8));
        }else{
            imageData.setImage(null);
        }
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
        if(null!=imageDto.getImage()) {
            image.setImage(new Binary(imageDto.getImage().getBytes(StandardCharsets.UTF_8)));
        }else{
            image.setImage(null);
        }
        image.setImageId(imageDto.getImageId());
        return image;
    }
}
