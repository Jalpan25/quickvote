FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

COPY src/ src/
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

RUN addgroup -S quickvote && adduser -S quickvote -G quickvote

COPY --from=build /app/target/*.jar app.jar

USER quickvote

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
