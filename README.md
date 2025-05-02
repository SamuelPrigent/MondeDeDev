# Monde de Dev

## Front

`npm install` : installe les dépendances Node.js

### Lancer le serveur de développement

- `npm run start` : démarre le serveur Angular (`ng serve`)
- Accéder à l'application sur `http://localhost:4200/`

### Build

- `npm run build` : construit le projet Angular (`ng build`)
- Les fichiers seront générés dans le dossier `dist/`

---

## Back

### Installation des dépendances

- Exécutez `./mvnw clean install` à la racine du dossier `back` pour installer les dépendances Maven.

### Lancement du backend

- Pour démarrer le serveur Spring Boot :
  ```bash
  ./mvnw spring-boot:run
  ```

### Configuration de l'environnement (`.env`)

- Copiez le fichier `.env.example` en `.env` et remplissez les variables :
  ```env
  DB_PASSWORD=mot_de_passe_mysql (mot de passe pour l'utilisateur root)
  JWT_SECRET_KEY=clé_secrète_jwt
  ```
- Pour générer une clé secrète JWT forte dans le terminal :
  ```bash
  openssl rand -base64 32
  ```
  Copiez la valeur générée dans `JWT_SECRET_KEY`.

### Création de la base de données MySQL

1. Ouvrez **MySQL Workbench**.
2. Connectez-vous à votre instance MySQL.
3. Créez une base de données (par exemple `monde_de_dev`) :
   ```sql
   CREATE DATABASE monde_de_dev CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```
4. Vérifiez que le nom, l'utilisateur et le mot de passe dans `application.properties` correspondent à votre configuration MySQL.

5. Le schéma sera créé automatiquement au premier lancement si la configuration JPA est correcte.

---

## Résumé

- Front : Angular (`npm install`, `npm run start`)
- Back : Spring Boot (`./mvnw clean install`, `./mvnw spring-boot:run`)
- Configurer `.env` et la base de données avant de lancer le backend.
