FROM maven:3.9.4-eclipse-temurin-21 as  build
LABEL authors="Steve Tchoumi"
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests


FROM amazoncorretto:21
ARG PROFILE=dev
ARG APP_VERSION=1.0.0

WORKDIR /app
COPY --from=build /build/target/hotel_booking_app-*.jar /app/hotel-booking-api.jar

#ENV ACTIVE_PROFILE=${PROFILE}
#ENV SPRING_DATASOURCE_URL = jdbc:mysql://inventorydb:3306/inventorydb
EXPOSE 5150
#ENV ACTIVE_PROFILE=${PROFILE}
#ENV JAR_VERSION=${APP_VERSION}
CMD ["java", "-jar", "hotel-booking-api.jar"]
