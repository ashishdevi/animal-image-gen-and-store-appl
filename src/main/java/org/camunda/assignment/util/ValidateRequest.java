package org.camunda.assignment.util;

import org.camunda.assignment.dto.ImageData;
import org.camunda.assignment.dto.ImageType;
import org.camunda.assignment.error.CustomError;

public class ValidateRequest {

    private static final String expression = "^[a-z\\d]{24}$";
    public static void validateGenerationRequest(ImageData image) throws CustomError {
        if(null!=image){
            if(null!=image.getImageType()){
            try {
                 ImageType type = ImageType.valueOf(image.getImageType());
            } catch (IllegalArgumentException e) {
                throw new CustomError("IMAGE_TYPE_INVALID","Image type is not valid","400");
            }}else throw new CustomError("IMAGE_TYPE_MISSING","Image type is mandatory","400");

        }else throw new CustomError("NULL_REQUEST","REQUEST_OBJECT_IS_NULL","400");

    }

    public static void validateGetRequestParam(String imageId) throws CustomError {


        if(null!=imageId){
            if(!imageId.matches(expression))
            {
                throw new CustomError("IMAGE_ID_FORMAT_INVALID","ImageId should have proper format","400");
            }
        }else throw new CustomError("IMAGE_ID_IS_NULL","IMAGE_ID_CANNOT_BE_NULL","400");
    }
}
