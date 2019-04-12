### thorntail PACKAGEs
# classads-service
FROM openjdk:11-jre-slim
WORKDIR /classads-service
# copy build dir to curr dir.
COPY /classads-service/target /classads-service/target
# expose 8080
EXPOSE 8080
# define env. var.
ENV Name World
ENTRYPOINT ["java", "-Djava.net.preferIPv4Stack=true","-jar","/classads-service/target/demo-thorntail.jar"]
