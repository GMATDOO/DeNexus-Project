# Utiliza la imagen oficial de OpenJDK como base
FROM openjdk:17-jdk-alpine

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR generado por tu aplicación en el directorio de trabajo del contenedor
COPY target/tu-aplicacion.jar /app/tu-aplicacion.jar

# Comando que se ejecutará al iniciar el contenedor
ENTRYPOINT ["java", "-jar", "/app/tu-aplicacion.jar"]
