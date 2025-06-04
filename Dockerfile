# Etapa de build: usa Maven com JDK 17, nomeia etapa como 'builder'
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Define a pasta de trabalho dentro do container
WORKDIR /app

# Copia arquivos de dependência para otimizar cache
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Dá permissão de execução ao mvnw
RUN chmod +x ./mvnw

# Faz download das dependências (Roda o build do Maven, gera o .jar)
RUN ./mvnw dependency:go-offline

# Copia o restante do código
COPY src ./src

# Compila o projeto sem rodar testes
RUN ./mvnw clean package -DskipTests

# Etapa final: imagem pequena com JRE
FROM eclipse-temurin:17-jre-alpine

# Define a pasta de trabalho
WORKDIR /app

# Copia o JAR gerado na etapa de build
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Comando para rodar o app quando o container iniciar
ENTRYPOINT ["java", "-jar", "app.jar"]