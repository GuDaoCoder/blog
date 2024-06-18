FROM openjdk:17-jdk-alpine

# copy the jar
ADD blog-backend-0.0.1-SNAPSHOT.jar /app/app.jar

# expose port
EXPOSE 8173

# set the working directory
WORKDIR /app

ENTRYPOINT ["java","-jar","app.jar"]