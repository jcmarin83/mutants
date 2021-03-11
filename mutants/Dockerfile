FROM openjdk:8
ENV DB_PASSWD=melimutant
ENV DB_URL=jdbc:mysql://mutants.cbpqic7aaxo6.us-east-2.rds.amazonaws.com:3306/mutants?createDatabaseIfNotExist=true
ENV DB_USER=root
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} mutants.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "mutants.jar"]