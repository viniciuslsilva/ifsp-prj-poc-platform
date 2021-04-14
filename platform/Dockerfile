FROM maven:3.6-jdk-11 AS builder

COPY pom.xml pom.xml

RUN mvn dependency:go-offline -B

COPY ./src ./src
RUN mvn clean package -DskipTests -DskipITs

FROM azul/zulu-openjdk-alpine:11-jre

COPY --from=builder /target/platform-*-SNAPSHOT.jar platform.jar

ADD entrypoint.sh /

RUN chmod +x /entrypoint.sh

CMD ["/entrypoint.sh"]