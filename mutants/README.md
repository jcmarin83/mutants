# mutants

### Introduction
This project contains the professor X requirements

### Libraries and Versions
* SpringBoot, version: 2.4.3
* Swagger, version: 2.8.0
* Lombok, version: latest
* JUnit, version: 5.7.1
* Mockito, version: 3.6.28

### Compile and Run
To compile this project we need:

* Java 1.8 or greater [link](https://openjdk.java.net/install/)
* Maven 3.6.3 [link](https://maven.apache.org/install.html)
* Lombok latest version [link](https://projectlombok.org/download)

After the above, go to the mutants folder and compile the code with `mvnw clean install -P dev` and runs with `mvnw spring-boot:run -P dev`.

To create the docker image you can execute the command `mvnw clean package`

### Testing in Local Environment
To test the application in a local environment, you can access to this URL: [link](http://localhost:8090/swagger-ui.html) and try the service

### Using Application in Production Environment
In order to use the application in production environment, you must access to the URL: [link](http://ec2-3-138-154-248.us-east-2.compute.amazonaws.com/swagger-ui.html)

### Mutant Service 
in Swagger select the service Mutant, push the `Try Out` Button, in the request put the DNA sequence (e.g. "ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG") between the square brackets 
and push the `Execute` button

### Stats Service 
in Swagger select the service Stats, push the `Try Out` Button, and push the `Execute` button