# Google Cloud Platform openjdk 8 Docker Image
FROM java:8-jdk-alpine

# Default to UTF-8 file.encoding
ENV LANG C.UTF-8

VOLUME /tmp

WORKDIR /app
ADD build/libs/*.jar /app.jar

# Ejecuta cualquier comando que se necesite
RUN sh -c 'touch /app.jar'

# Define variables de entorno
#ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
#JAVA_OPTS="-Dspring.profiles.active=local"

# Expone el puerto 8080 para acceder desde fuera del contenedor
EXPOSE 8080

# Accion que se ejecuta para lanzar el contenedor
ENTRYPOINT ["java","-jar","/app.jar"]