FROM gradle:8.7-jdk21

WORKDIR /app

COPY . .

RUN gradle build