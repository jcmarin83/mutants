FROM maven:3.6.3-jdk-8 AS MAVEN_BUILD
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn package -DskipTests
FROM openjdk:8-jre-alpine
ENV DB_PASSWD=melimutant
ENV DB_URL=jdbc:mysql://mutants.cbpqic7aaxo6.us-east-2.rds.amazonaws.com:3306/mutants?createDatabaseIfNotExist=true
ENV DB_USER=root
WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/mutants-0.0.1-SNAPSHOT.jar /app/
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "mutants-0.0.1-SNAPSHOT.jar"]