package org.camunda.assignment.service;

import feign.Response;
import org.bson.types.Binary;
import org.camunda.assignment.client.PlaceKittenAPI;
import org.camunda.assignment.error.CustomError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class GenerateKittenImageService implements GenerateAnimalImage{

    @Autowired
    PlaceKittenAPI placeKittenAPI;

    public Binary generateImage() throws CustomError {
        try (Response response = placeKittenAPI.getKittenImage()) {
            InputStream is = response.body().asInputStream();
            BufferedImage image=ImageIO.read(is);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpeg", baos);

            byte[] imageBytes = baos.toByteArray();

        return new Binary(imageBytes);
        } catch (IOException e) {
            throw new CustomError("INTERNAL_SERVER_ERROR","Kitten image generation API is down.","500");
            //log
        } catch (Exception e) {
            throw new CustomError("INTERNAL_SERVER_ERROR","Kitten image generation API is down.","500");
            //log
        }
    }

}
