# Build Stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Skip tests to speed up build and avoid environment errors
RUN mvn clean package -DskipTests

# Run Stage
FROM tomcat:10.1-jdk17
# Remove default apps
RUN rm -rf /usr/local/tomcat/webapps/*
# Copy built WAR file to Tomcat webapps as ROOT.war
COPY --from=build /app/target/webapp-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
