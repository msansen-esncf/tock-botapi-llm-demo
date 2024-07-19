FROM eclipse-temurin:17-jre-alpine

USER root
RUN apk --no-cache add curl jq bash ncdu tzdata

# CHOOSE THE WAY TO ADD THE JAR FILE TO THE IMAGE
ENV TZ=Europe/Paris
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ENTRYPOINT ["java","-jar","/opt/app.jar"]

HEALTHCHECK --start-period=15s --interval=1m --timeout=10s --retries=5 \
            CMD curl --silent --fail --request GET http://localhost:8080/actuator/health \
                | jq --exit-status '.status == "UP"' || exit 1