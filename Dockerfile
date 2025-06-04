# 1) Etapa de build: usa Maven com JDK 17, nomeia etapa como 'builder'
FROM maven:3.9.6-eclipse-temurin-17 AS build

# 2) Define a pasta de trabalho dentro do container
WORKDIR /app

# 3) Copia arquivos de dependência para otimizar cache
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# 4) Faz download das dependências (Roda o build do Maven, gera o .jar)
RUN ./mvnw dependency:go-offline

# 5) Copia o restante do código
COPY src ./src

# 6) Compila o projeto sem rodar testes
RUN ./mvnw clean package -DskipTests

# 7) Etapa final: imagem pequena com JRE
FROM eclipse-temurin:17-jre-alpine

# 8) Define a pasta de trabalho
WORKDIR /app

# 9) Copia o JAR gerado na etapa de build
COPY --from=build /app/target/*.jar app.jar

# 10) Expõe a porta padrão do Spring Boot
EXPOSE 8080

# 11) Comando para rodar o app quando o container iniciar
ENTRYPOINT ["java", "-jar", "app.jar"]