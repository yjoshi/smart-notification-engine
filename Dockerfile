FROM hub.docker.prod.walmart.com/library/openjdk:8

ADD target/sne-*.jar /app.jar

EXPOSE 8080
CMD ["java", "-jar", "/app.jar"]
