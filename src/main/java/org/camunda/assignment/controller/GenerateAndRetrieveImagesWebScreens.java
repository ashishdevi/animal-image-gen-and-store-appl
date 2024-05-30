package org.camunda.assignment.controller;


import org.camunda.assignment.dto.ImageData;
import org.camunda.assignment.error.CustomError;
import org.camunda.assignment.service.GenerateAndStoreImageService;
import org.camunda.assignment.util.ValidateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@Controller
@RequestMapping(value = "/fe/animalimages")
public class GenerateAndRetrieveImagesWebScreens {


    @RequestMapping("/")
    public String loadIndexPage() {
        return "index";

    }


}
