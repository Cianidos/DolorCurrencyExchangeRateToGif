FROM gradle

WORKDIR /app

COPY . /app

RUN gradle bootJar

EXPOSE 8080

CMD ["gradle", "bootRun"]