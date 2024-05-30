package org.camunda.assignment.configuration;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.camunda.assignment.error.CustomError;

public class PlaceAPIErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        System.out.println("***************"+s);
        return new CustomError("INTERNAL_SERVER_ERROR","Internal server error occured.","500");
    }
}
