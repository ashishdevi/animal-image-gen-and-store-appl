package org.camunda.assignment.service;

import feign.Response;
import org.bson.types.Binary;
import org.camunda.assignment.client.PlaceBearAPI;
import org.camunda.assignment.error.CustomError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class GenerateBearImageService implements GenerateAnimalImage{

    @Autowired
    PlaceBearAPI placeBearAPI;

    private static final String IMAGE_TYPE="jpeg";

    public Binary generateImage() throws CustomError {
        try (Response response = placeBearAPI.getBearImage()) {
            InputStream is = response.body().asInputStream();
            BufferedImage image=ImageIO.read(is);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, IMAGE_TYPE, baos);

        byte[] imageBytes = baos.toByteArray();
        return new Binary(imageBytes);
        } catch (IOException e) {
            throw new CustomError("INTERNAL_SERVER_ERROR","Bear image generation API is down.","500");

        } catch (Exception e) {
            throw new CustomError("INTERNAL_SERVER_ERROR","Bear image generation API is down.","500");
            //log
        }
    }

}
