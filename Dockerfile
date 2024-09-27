FROM amazoncorretto:21-alpine-jdk

COPY GestionDeInscripciones-0.0.1.jar /app/app.jar

ENTRYPOINT ["java","-jar","app.jar"]
