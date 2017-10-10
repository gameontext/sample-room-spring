FROM ibmjava:8-sdk
MAINTAINER IBM Java engineering at IBM Cloud

COPY target/sampleroomspring-1.0-SNAPSHOT.jar /app.jar

ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]