### thorntail PACKAGEs
# classads
FROM openjdk:11-jre-slim
WORKDIR /classads
# copy build dir to curr dir.
COPY /classads/target /classads/target
# expose 8080
EXPOSE 8080
# define env. var.
ENV Name World
ENTRYPOINT ["java", "-Djava.net.preferIPv4Stack=true","-jar","/classads/target/demo-thorntail.jar"]
