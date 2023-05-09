FROM eclipse-temurin:17-jammy

WORKDIR /app

COPY resolv.conf /etc/resolv.conf
COPY target/rooms-app.jar /app/rooms-app.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/rooms-app.jar"]
