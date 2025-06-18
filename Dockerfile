# Étape 1 : build avec Gradle wrapper
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

# 🧱 Étape 1 : copier seulement les fichiers liés à la configuration Gradle
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# ⚙️ Donne les droits d'exécution au wrapper
RUN chmod +x gradlew

# 📦 Télécharge les dépendances (ça crée un cache)
RUN ./gradlew dependencies

# 🧱 Étape 2 : copier le code source (après le cache Gradle)
COPY src src

# 🔨 Build du jar (rejoue seulement si le code change)
RUN ./gradlew bootJar

# Étape 2 : image de runtime
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]


