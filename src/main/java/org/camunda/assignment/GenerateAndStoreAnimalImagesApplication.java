package org.camunda.assignment;

import io.camunda.zeebe.spring.client.EnableZeebeClient;
import io.camunda.zeebe.spring.client.annotation.Deployment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = "org.camunda.assignment")
@EnableMongoRepositories
@AutoConfiguration
@EnableFeignClients
@EnableZeebeClient
@Deployment(resources = {"classpath*:*.bpmn", "classpath*:*.dmn"})
public class GenerateAndStoreAnimalImagesApplication {

	public static void main(String[] args) {
		SpringApplication.run(GenerateAndStoreAnimalImagesApplication.class, args);
	}

}
