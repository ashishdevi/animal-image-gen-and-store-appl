FROM azul/zulu-openjdk-alpine:17-latest

RUN mkdir -p /usr/scripts
WORKDIR /usr/scripts
RUN adduser --disabled-password appuser

COPY *.jar /usr/scripts/app.jar
COPY *.jar /app.jar

RUN chmod -R 755 /usr/*
RUN chown -R appuser /usr/*
USER appuser
EXPOSE 8088
ENTRYPOINT ["java","-jar","/app.jar"]