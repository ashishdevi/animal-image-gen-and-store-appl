# Camunda assignment to generate random animal images
This application generates an image of a chosen animal (currently dog, kitten and bear are only supported), saves that image and returns the image. Application is exposed using REST API and can also be consumed via an embedded interactive webpage.

This is primary a java based application containing following components/technologies
1. Java spring boot (REST API and to host static web page)
2. Camunda engine (saas platform running business process and decision engine)
3. Zeebe camunda client (Client used to connect to Camunda engine)
4. MongoDB database (To store images and metadata)
5. HTML5 to create simple web page. (User interaction)

**Design:**
![Alt text](design-documents/ApplicationBlockDiagram.png?raw=true "Application components")
**Explanation:**
1. Current application is packaged in single jar file, so that it can be run easily on other machines.
2. In my opinion, desired state should contain following different component for maintainability and scalling point of view
   1. Static web screens hosted in static web container or kubernetes pod hosting webcontainer like TomCat, JBoss or NGinx
   2. Separate spring boot application handling REST calls, this will also be responsible to interact with Camunda Saas engine and database.
   3. Separate work package, which will interact with Camunda Saas application and MongoDB database
      1. Depending upon complexity and size of workers they can further be split into different components
      2. e.g. instead of having one component containing 3 different workers, we can split them into multiple components based on required size.
3. **Interactions:**
   1. Web will interact with REST API only to generate and get the images.APIs are
      1. POST: /api/animalimages/generateandsave - To generate and save images of an animal of choice
      2. GET: /api/animalimages/{imageId} - Retrieves image against a generated imageId
   3. REST API/component interacts with two components based on request,
      1. Camunda engine: To initiate the process if generate Image (POST) call is received. Upon copletion, an iageId returned from process is returned back to the calling application as POST call response.
      2. MongoDB: To retrieve a stored image against imageId in case GET call is received.
   3. Camunda SaaS (Business process):
      1. Interacts with workers to generate and store the images. ImageId returned from workers is sent back to the calling application.
   4. Zeebe client(workers): 
      1. Interacts with Camunda engine queue, accepts the work from the camunda tasks and returns response(imageId.
      2. Calls third party APIs to generate images
      3. Saves images to database
      4. Returns image id to camunda process engine.

More details can be seen in below **sequence diagram:**

![Alt text](design-documents/SequenceDiagram.png?raw=true "SequenceDiagram")

**API Specifications:**
*********************************************
### Random image generator and store process
This API geberates random photos of a selected animal and stores and returns it in the response. Currently API supports random pictures of Kitten, Bear and Dog only. For any other animals in the request, it will rreturn invalid request error.

### Version: 1.0.0

**Contact information:**  
ashishdevi@gmail.com

### /generateandsave

#### POST
##### Summary:

Generates a new image and saves it to the database

##### Description:

OPeration generates a new image and saves it, returns imageId and an animal image(optionally) based on input request.

##### Responses

| Code | Description |
| ---- | ----------- |
| 201 | Successful operation |
| 400 | Invalid input |
| 500 | Internal server error |

### /{imageId}

#### GET
##### Summary:

Returns image details by image id

##### Description:

Multiple status values can be provided with comma separated strings

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| imageId | path | Image id of a stored image | Yes | string |

##### Responses

| Code | Description |
| ---- | ----------- |
| 201 | Successful operation |
| 400 | Invalid input |
| 404 | Image not found |

*********************************************

**Design decisions:** For this assignment
1. Package all the components in one deployable jar
   1. Reason: To make is simpler to run application on most of the machines.
2. MongoDb as database:
   1. Reason: Since image is unstructured data, in current scope searching is purely based on primary key, it is easier to configure MongoDB
      1. At the time of decisioning I decided to use mondo DB because it's embedded version is also available.
3. Not to load image data in camunda engine so that load will not be exerted on the proces engine.
4. By default trust server SSL certificates
5. Because of time limitations, not to implement authentication and authorization on API
*********************************************

**How to set up project:**
1. Required tooling on local computer:
   1. Git client
   2. Java 17
   3. Maven
   4. MongoDB with connection parameters
   5. Locally running camunda engine or connection parameters from Camunda Saas.
2. How to set up the project, once all of above tooling is available:
   1. ```shell
      git clone https://github.com/ashishdevi/animal-image-gen-and-store-appl.git
      ```
   2. There are multiple ways through which a code can be run/tested on local,
      1. Using locally installed java:
         1. Build the jar using
         ```shell
         mvn clean install
         ```
         2. GO to the jar location, run below command to start code with the default configuration,
            ```shell
            java -jar <<jar name>>.jar
            ```
         3. If external config file is needed to be provided then,
            ```shell
            java -jar <<jar name>>.jar --spring.config.location="file://<<File path>>>>"
            ```
         3. In case of specific configurations are needed to be changed e.g. Camunda engine values, or mongoDB values only
            ```shell
            java -jar -Dzeebe.client.cloud.clusterId=<<clusterId>> -Dzeebe.client.cloud.region=<<region>> -Dzeebe.client.cloud.clientId=<<clientId>> -Dzeebe.client.cloud.clientSecret=<<clientSecret>> -Dspring.data.mongodb.uri=<<mongoDBURI>> app.jar
            ```
      2. Using containers:
         1. Dockerfile is provided in docker-context folder
         2. In ideal scenario, image of this application would have been pushed on container registry.
         3. If that is not a case, then build the image locally using dockerfile provided using following command,make sure jar is already built for the application and is present in docker-context package.
            ```shell
            docker build -t camundaengine/animalimagegenerator <<folder location of dockerfile>>
            ```
         4. Run the image generated from local registry, using below command,
            ```shell
            docker run -p 8088:8088  camundaengine/animalimagegenerator:latest app --network=host
            ```
         5. If needed then add environment properties for the spring boot properties which are needed to be changed like mongoDB connection string, camunda engine or port number.
      3. Using maven (to test)


**Other artifacts:**
1. Dockerfile: Present in docker-context root folder
2. helmcharts: Present in helm-context folder.
   1. Multiple values-<<env>>.yaml files can be added in the helm configuration to enable application deployment for multiple environment.
   2. To Generate kubernetes template files, execute following command after traversing to helm-context folder
      ```shell
      helm template animal-image-gen-and-store-appl
      ```
3. API specification: AnimalImageGeneratorAndStore.yaml
4. Draw io design file: CamundaAssignment_BlockOverview.drawio
5. Plant UML sequence diagram: AnimalImageeStoreSequenceDIagram.puml
6. Already built jar file to start testing already.(not part of attachment))

**Limitations of current code:**
1. Considering the nature of the project as an assignment
   1. It does not have production level stability.
   2. Error handling and codes can be improved further.
   3. At some locations timeouts and other parameters are not configured.
   4. Authorization and authentication is not setup.
   5. Server certificate verification for external API is not done.
2. Unit test cases using camunda library are pending. Unit test cases can be enhanced further.
3. Image and json sent in response should be multipart form data rather than sending it as part of json. If needed this can be improved.
4. During design consideration, it was decided to include MongoDb because embedded version of MongoDB was possible along with other major design considerations. But currently because of some reason not able to boot internal MongoDB instance along with the app. App needs external MongoDB same as camunda engine.
5. Image returned from external source is not scanned against vulnerabilities.
6. NetworkPolicy is not added in helm chart to enable ingress and egress traffic