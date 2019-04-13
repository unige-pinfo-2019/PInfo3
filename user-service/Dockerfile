### thorntail PACKAGEs
# user-service
FROM openjdk:11-jre-slim
WORKDIR /user-service
# copy build dir to curr dir.
COPY ./target ./target
# expose 8080
EXPOSE 8082
# define env. var.
ENV Name World
ENTRYPOINT ["java", "-Djava.net.preferIPv4Stack=true","-jar","/user-service/target/demo-thorntail.jar"]
