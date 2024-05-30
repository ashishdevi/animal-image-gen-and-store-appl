package org.camunda.assignment.service;

import org.bson.types.Binary;
import org.camunda.assignment.error.CustomError;

public interface GenerateAnimalImage {

    public Binary generateImage() throws CustomError;
}
