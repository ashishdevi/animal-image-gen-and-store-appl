package org.camunda.assignment.mapper;


import org.camunda.assignment.dto.ImageData;
import org.camunda.assignment.model.Image;

//@Mapper
public interface ImageObjectMapper {
//    ImageObjectMapper INSTANCE = Mappers.getMapper( ImageObjectMapper.class );
        ImageData toDTO(Image image);
        Image toEntity(ImageData imageDto);


}

