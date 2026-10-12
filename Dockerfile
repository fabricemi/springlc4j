FROM eclipse-temurin:17-jre

WORKDIR /app

COPY target/lcj-0.0.1-SNAPSHOT.war app.war
COPY mcp103.jar mcp103.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.war"]