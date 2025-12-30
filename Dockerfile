FROM maven:3.9-eclipse-temurin-25 AS build-authify

WORKDIR /build/authify

COPY pom.xml ./pom.xml
COPY authify-core/pom.xml ./authify-core/pom.xml
COPY authify-customer-portal/pom.xml ./authify-customer-portal/pom.xml
COPY authify-services/pom.xml ./authify-services/pom.xml
COPY authify-repository/pom.xml ./authify-repository/pom.xml
COPY authify-models/pom.xml ./authify-models/pom.xml
COPY authify-utils/pom.xml ./authify-utils/pom.xml

RUN mvn -ntp dependency:go-offline

COPY authify-core/src ./authify-core/src
COPY authify-customer-portal/src ./authify-customer-portal/src
COPY authify-services/src ./authify-services/src
COPY authify-repository/src ./authify-repository/src
COPY authify-models/src ./authify-models/src
COPY authify-utils/src ./authify-utils/src

RUN mvn clean package -DskipTests=true

FROM eclipse-temurin:25-jre-alpine
WORKDIR /authify
ARG JAR_FILE=/build/authify/authify-core/target/authify.jar
COPY --from=build-authify ${JAR_FILE} /authify
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8888", "-jar", "authify.jar"]