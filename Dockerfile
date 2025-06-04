# Etapa de build: usa Maven com JDK 17, nomeia etapa como 'builder'
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Define a pasta de trabalho dentro do container
WORKDIR /app

# Copia arquivos de dependência para otimizar cache
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Roda o build do Maven, gera o .jar
RUN mvn clean package -DskipTests

# Imagem final menor só com Java Runtime
FROM eclipse-temurin:17-jre-alpine

# Define a pasta de trabalho
WORKDIR /app

# copia o .jar gerado na etapa builder
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# comando para rodar o app quando o container iniciar
ENTRYPOINT ["java", "-jar", "app.jar"]