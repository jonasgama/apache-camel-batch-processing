FROM openjdk:8 AS BUILD_IMAGE

RUN mkdir -p /root/dev/myapp && mkdir -p /resources
COPY . /root/dev/myapp
WORKDIR /root/dev/myapp 


RUN ./gradlew build 

FROM openjdk:8-jre
WORKDIR /root/

COPY --from=BUILD_IMAGE /root/dev/myapp/build/libs/vin-truck-batch-service-0.1.0-SNAPSHOT.jar ./myapp.jar
EXPOSE 8080
CMD ["java","-jar","myapp.jar"]