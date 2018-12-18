FROM docker.sz-shuwei.com/centos7-jre8
VOLUME /tmp
COPY target/gs-spring-boot-0.1.0.jar /app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
