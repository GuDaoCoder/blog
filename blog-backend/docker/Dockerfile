FROM openjdk:17-jdk-alpine

# copy the jar
ADD blog-backend-0.0.1-SNAPSHOT.jar /app/app.jar

# set environment variables
ENV database_host=${database_host}
ENV database_username=${database_username}
ENV database_password=${database_password}
ENV redis_host=${redis_host}
ENV redis_port=${redis_port}
ENV redis_password=${redis_password}

# expose port
EXPOSE 8173

# set the working directory
WORKDIR /app

ENTRYPOINT ["java","-jar","app.jar"]