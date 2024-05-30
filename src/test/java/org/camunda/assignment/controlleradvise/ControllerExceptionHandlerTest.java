package org.camunda.assignment.controlleradvise;

import org.camunda.assignment.dto.Error;
import org.camunda.assignment.error.CustomError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ControllerExceptionHandlerTest {

    MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);

    @Test
    void handleCustomException() {

        ControllerExceptionHandler controllerExceptionHandler = new ControllerExceptionHandler();
        ResponseEntity<Error> entity=controllerExceptionHandler.handleCustomException(new CustomError("IMAGE_TYPE_INVALID","Image type is not valid","400"));
        assertEquals(400, entity.getStatusCodeValue());
    }

    @Test
    void handleMethodArgumentNotValidExceptionException() {

        ControllerExceptionHandler controllerExceptionHandler = new ControllerExceptionHandler();
        ResponseEntity<Error> entity=controllerExceptionHandler.handleMethodArgumentNotValidExceptionException(exception);
        assertEquals(400, entity.getStatusCodeValue());
    }
}