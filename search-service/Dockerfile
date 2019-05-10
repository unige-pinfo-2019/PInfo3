### thorntail PACKAGEs
# search-service
FROM openjdk:11-jre-slim
WORKDIR /search-service
# copy build dir to curr dir.
COPY ./target ./target
# expose 8080
EXPOSE 8080
# define env. var.
ENV Name World
ENTRYPOINT ["java", "-Djava.net.preferIPv4Stack=true","-jar","/search-service/target/search-service-1.0.0-SNAPSHOT-thorntail.jar"]
