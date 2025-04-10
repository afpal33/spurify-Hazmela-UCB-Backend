# Fase de construcción
FROM eclipse-temurin:17-jdk-alpine as builder
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# Fase final
FROM eclipse-temurin:17-jre-alpine
WORKDIR /application

# Crear usuario seguro
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copiar capas extraídas
COPY --from=builder /workspace/app/target/dependency/BOOT-INF/lib /app/lib
COPY --from=builder /workspace/app/target/dependency/META-INF /app/META-INF
COPY --from=builder /workspace/app/target/dependency/BOOT-INF/classes /app

EXPOSE 8081

ENTRYPOINT ["java","-cp","app:app/lib/*","com.hazmelaucb.ms_anuncios.MsAnunciosApplication"]
