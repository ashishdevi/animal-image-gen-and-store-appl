package org.camunda.assignment.util;

import org.camunda.assignment.dto.ImageData;
import org.camunda.assignment.error.CustomError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class ValidateRequestTest {

    @Test()
    void validateGenerationRequestWithNullrequest() throws CustomError {
        ImageData image = null;
        CustomError error = Assertions.assertThrows(CustomError.class, () -> ValidateRequest.validateGenerationRequest(image));
        assertEquals("NULL_REQUEST", error.getCode());
    }

    @Test()
    void validateGenerationRequestWithNullImageType() throws CustomError {
        ImageData image = new ImageData();
        CustomError error = Assertions.assertThrows(CustomError.class, () -> ValidateRequest.validateGenerationRequest(image));
        assertEquals("IMAGE_TYPE_MISSING", error.getCode());
    }

    @Test()
    void validateGenerationRequestWithInvalidImageType() throws CustomError {
        ImageData image = new ImageData();
        image.setImageType("AAA");
        CustomError error = Assertions.assertThrows(CustomError.class, () -> ValidateRequest.validateGenerationRequest(image));
        assertEquals("IMAGE_TYPE_INVALID", error.getCode());
    }

    @Test
    void validateGetRequestParamNullImageId() {
        String testParam = null;
        CustomError error = Assertions.assertThrows(CustomError.class, () -> ValidateRequest.validateGetRequestParam(testParam));
        assertEquals("IMAGE_ID_IS_NULL", error.getCode());
    }
    @Test
    void validateGetRequestParamInvalidImageId() {
        String testParam = "1233";
        CustomError error = Assertions.assertThrows(CustomError.class, () -> ValidateRequest.validateGetRequestParam(testParam));
        assertEquals("IMAGE_ID_FORMAT_INVALID", error.getCode());
    }
}