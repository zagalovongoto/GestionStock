# Utilisez l'image OpenJDK officielle en tant qu'image de base
FROM openjdk:17-alpine

# Définissez le répertoire de travail
WORKDIR /app

# Copiez le JAR de l'application (assurez-vous que le JAR est construit avant de construire l'image Docker)
COPY target/gestiondestock-0.0.1-SNAPSHOT.jar gestiondestock.jar

# Exposez le port sur lequel l'application s'exécute
EXPOSE 8881

# Commande pour démarrer l'application Spring Boot lorsque le conteneur démarre
CMD ["java", "-jar", "gestiondestock.jar"]

