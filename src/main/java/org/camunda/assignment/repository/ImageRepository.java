package org.camunda.assignment.repository;

import org.camunda.assignment.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ImageRepository extends MongoRepository<Image, String>  {

    @Query("{imageId:'?0'}")
    Image findItemByimageId(String imageId);

}
