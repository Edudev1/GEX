# Etapa 1: Build de los jar con el código. Se usa una imagen de desarrollo, con Maven
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copiar el POM raíz y los de los módulos, para luego descargar dependencias
# COPY recibe el origen en el equipo local, y el destino en el remoto
# El remoto, si es relativo, será respecto al valor establecido en WORKDIR
# Aunque el dockerfile sea para uno de los dos módulos (api/web) hacen falta
# todos los pom.xml, porque en el módulo padre se referencian
COPY pom.xml .
COPY shop-eah-jdr-common/pom.xml shop-eah-jdr-common/
COPY shop-eah-jdr-security/pom.xml shop-eah-jdr-security/
COPY shop-eah-jdr-api/pom.xml shop-eah-jdr-api/
COPY shop-eah-jdr-web/pom.xml shop-eah-jdr-web/

# Descargar dependencias para compilar sin conexión
# El -B indica modo “batch”. Básicamente, no preguntar a usuario cosas, porque
# no hay un usuario que pueda responder.
RUN mvn dependency:go-offline -B

# Copiar el código fuente necesario
# Aquí no hace falta copiar el módulo que no se vaya a empaquetar
COPY shop-eah-jdr-common/src shop-eah-jdr-common/src
COPY shop-eah-jdr-security/src shop-eah-jdr-security/src
COPY shop-eah-jdr-web/src shop-eah-jdr-web/src
# COPY shop-eah-jdr-api/src shop-eah-jdr-api/src # No hace falta para construir la WEB

# Compilar y empaquetar solo el módulo que interesa y sus dependencias internas
# Se invocan dos goals de Maven:
# clean – Limpiar resultado de anteriores builds, para no usar código antiguo.
# package – Empaquetar la aplicación en un jar.
# Con -DskipTests se evita que se ejecuten test, si los hubiera.
# Con -pl <módulo> se indica el módulo que interesa construir.
# Con -am (--also-make) se indica que se construyan también dependencias necesarias.
RUN mvn clean package -DskipTests -pl shop-eah-jdr-web -am

# Etapa 2: Construcción de la imagen. Imagen más ligera que la anterior, y sin Maven
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiar el JAR ejecutable generado desde la etapa de build.
COPY --from=build /app/shop-eah-jdr-web/target/web-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto configurado en application.properties para la WEB
EXPOSE 8080

# Comando para arrancar la aplicación usando el perfil 'docker'
# -Dspring.profiles.active=docker sobrescribe el perfil activo en application.properties.
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "app.jar"]