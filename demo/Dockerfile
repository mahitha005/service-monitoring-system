# ---------- BUILD STAGE ----------
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy pom first (better caching)
COPY pom.xml .

# Download dependencies
RUN mvn -B -q -e -DskipTests dependency:go-offline

# Copy project files
COPY . .

# Give permission to mvnw
RUN chmod +x mvnw

# Build jar
RUN ./mvnw clean package -DskipTests


# ---------- RUN STAGE ----------
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy built jar
COPY --from=build /app/target/*.jar app.jar

# Render uses port 8080
EXPOSE 8080

# Start application
ENTRYPOINT ["java","-jar","/app/app.jar"]
