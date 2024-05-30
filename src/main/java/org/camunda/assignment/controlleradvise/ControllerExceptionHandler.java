package org.camunda.assignment.controlleradvise;

import lombok.extern.slf4j.Slf4j;
import org.camunda.assignment.dto.Error;
import org.camunda.assignment.error.CustomError;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomError.class)
    public final ResponseEntity<Error> handleCustomException(CustomError error){
        Error e = new Error(error.getCode(), error.getDescription());
        log.error("Error occured, errorcode: {}, errorDescription: {}", e.getErrorCode(),e.getErrorDescription());
        return new ResponseEntity<>(e, HttpStatusCode.valueOf(Integer.parseInt(error.getResponseCode())));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Error> handleMethodArgumentNotValidExceptionException(MethodArgumentNotValidException exception){
        Error e = new Error(exception.getDetailMessageCode(), exception.getMessage());
        log.error("Error occured, errorcode: {}, errorDescription: {}", e.getErrorCode(),e.getErrorDescription());
        return new ResponseEntity<>(e, HttpStatusCode.valueOf(400));
    }

//    @ExceptionHandler(Exception.class)
//    public final ResponseEntity<Error> handleOtherExceptionException(Exception t){
//        Error e = new Error("INTERNAL_SERVER_ERROR", "Internal server error occured.");
//        return new ResponseEntity<>(e, HttpStatusCode.valueOf(500));
//    }
}
