FROM maven:3.6.3-jdk-8-alpine AS MAVEN_BUILD
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn package -DskipTests
FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/msensores-0.0.1-SNAPSHOT.jar /app/
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "msensores-0.0.1-SNAPSHOT.jar"]