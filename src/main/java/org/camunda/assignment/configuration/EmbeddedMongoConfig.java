package org.camunda.assignment.configuration;

import com.mongodb.client.MongoClient;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.distribution.Version;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;


import java.io.IOException;

@Configuration
public class EmbeddedMongoConfig {

//    private static final String MONGO_DB_URL = "localhost";
//    private static final String MONGO_DB_NAME = "embeded_db";
//    @Bean
//    public MongoTemplate mongoTemplate() throws IOException {
//        EmbeddedMongoFactoryBean mongo = new EmbeddedMongoFactoryBean();
//        mongo.setBindIp(MONGO_DB_URL);
//        MongoClient mongoClient = mongo.getObject();
//        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, MONGO_DB_NAME);
//        return mongoTemplate;
//    }


}