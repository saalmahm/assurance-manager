# 🏢 Assurance Manager

## 📋 Description

**Assurance Manager** est une application Java de gestion complète d'un système d'assurance. Elle permet de gérer les conseillers, clients, contrats d'assurance et sinistres à travers une interface console intuitive et une base de données PostgreSQL.

## ✨ Fonctionnalités

### 👥 Gestion des Conseillers
- ➕ Ajouter un nouveau conseiller
- 📋 Lister tous les conseillers
- 🔍 Rechercher un conseiller par ID
- ✏️ Modifier les informations d'un conseiller
- 🗑️ Supprimer un conseiller

### 👤 Gestion des Clients
- ➕ Ajouter un nouveau client
- 📋 Lister tous les clients
- 🔍 Rechercher un client par ID
- ✏️ Modifier les informations d'un client
- 🗑️ Supprimer un client

### 📄 Gestion des Contrats
- ➕ Créer un nouveau contrat (Automobile, Maison, Maladie)
- 📋 Afficher tous les contrats
- 🔍 Rechercher un contrat par ID
- 👤 Afficher les contrats d'un client spécifique
- 🗑️ Supprimer un contrat

### 🚨 Gestion des Sinistres
- ➕ Déclarer un nouveau sinistre
- 📋 Lister tous les sinistres
- 🔍 Rechercher un sinistre par ID
- ✏️ Modifier le statut d'un sinistre
- 🗑️ Supprimer un sinistre

## 🛠️ Technologies Utilisées

- **Java 8+** - Langage de programmation principal
- **PostgreSQL** - Base de données relationnelle
- **JDBC** - Connecteur base de données
- **Maven** - Gestionnaire de dépendances (optionnel)
- **Architecture MVC** - Modèle, Vue, Contrôleur

## 📁 Structure du Projet

```
assurance-manager/
│
├── src/
│   ├── dao/                    # Data Access Objects
│   │   ├── DatabaseConnection.java
│   │   ├── ConseillerDAO.java
│   │   ├── ClientDAO.java
│   │   ├── ContratDAO.java
│   │   └── SinistreDAO.java
│   │
│   ├── model/                  # Modèles de données
│   │   ├── Conseiller.java
│   │   ├── Client.java
│   │   ├── Contrat.java
│   │   ├── Sinistre.java
│   │   ├── TypeContrat.java
│   │   └── StatutSinistre.java
│   │
│   ├── service/                # Couche métier
│   │   ├── ConseillerService.java
│   │   ├── ClientService.java
│   │   ├── ContratService.java
│   │   └── SinistreService.java
│   │
│   ├── view/                   # Interface utilisateur
│   │   ├── ConseillerView.java
│   │   ├── ClientView.java
│   │   ├── ContratView.java
│   │   └── SinistreView.java
│   │
│   └── Main.java              # Point d'entrée de l'application
│
├── sql/
│   └── database_setup.sql     # Scripts de création de la BDD
│
├── lib/                       # Bibliothèques (si sans Maven)
│   └── postgresql-xx.x.x.jar
│
└── README.md
```

## ⚙️ Installation et Configuration

### Prérequis

- ☕ **Java JDK 8** ou supérieur
- 🐘 **PostgreSQL 12** ou supérieur
- 📝 Un IDE Java (IntelliJ IDEA, Eclipse, VS Code)

### 1. Cloner le Projet

```bash
git clone https://github.com/saalmahm/assurance-manager.git
cd assurance-manager
```

### 2. Configuration de la Base de Données

#### Créer la base de données PostgreSQL :

```sql
-- Se connecter à PostgreSQL et créer la base
CREATE DATABASE assurance_db;
\c assurance_db;
```

#### Exécuter le script de création des tables :

```sql
-- Créer le type énuméré pour les contrats
CREATE TYPE type_contrat AS ENUM ('AUTOMOBILE', 'MAISON', 'MALADIE');

-- Créer le type énuméré pour les statuts de sinistre
CREATE TYPE statut_sinistre AS ENUM ('EN_ATTENTE', 'EN_COURS', 'ACCEPTE', 'REFUSE');

-- Table des conseillers
CREATE TABLE conseillers (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    telephone VARCHAR(20)
);

-- Table des clients
CREATE TABLE clients (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    telephone VARCHAR(20),
    adresse VARCHAR(255),
    conseiller_id INTEGER,
    FOREIGN KEY (conseiller_id) REFERENCES conseillers(id)
);

-- Table des contrats
CREATE TABLE contrats (
    id SERIAL PRIMARY KEY,
    type_contrat type_contrat NOT NULL,
    date_debut DATE NOT NULL,
    date_fin DATE NOT NULL,
    client_id INTEGER NOT NULL,
    FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE
);

-- Table des sinistres
CREATE TABLE sinistres (
    id SERIAL PRIMARY KEY,
    description TEXT NOT NULL,
    date_sinistre DATE NOT NULL,
    statut statut_sinistre DEFAULT 'EN_ATTENTE',
    contrat_id INTEGER NOT NULL,
    FOREIGN KEY (contrat_id) REFERENCES contrats(id) ON DELETE CASCADE
);
```

### 3. Configuration de la Connexion

Modifiez le fichier `DatabaseConnection.java` avec vos paramètres :

```java
private static final String URL = "jdbc:postgresql://localhost:5432/assurance_db";
private static final String USERNAME = "votre_nom_utilisateur";
private static final String PASSWORD = "votre_mot_de_passe";
```

### 4. Ajouter le Driver PostgreSQL

#### Option A : Avec Maven
Ajoutez dans votre `pom.xml` :

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.7.0</version>
</dependency>
```

#### Option B : Sans Maven
1. Téléchargez le driver JDBC PostgreSQL depuis [ici](https://jdbc.postgresql.org/download/)
2. Placez le fichier `.jar` dans le dossier `lib/`
3. Ajoutez-le au classpath de votre IDE

## 🚀 Exécution de l'Application

### Méthode 1 : Depuis l'IDE
1. Ouvrez le projet dans votre IDE
2. Configurez le classpath avec le driver PostgreSQL
3. Exécutez la classe `Main.java`

### Méthode 2 : En ligne de commande

```bash
# Compilation
javac -cp "lib/postgresql-42.7.0.jar:." src/**/*.java

# Exécution
java -cp "lib/postgresql-42.7.0.jar:src" Main
```

### Méthode 3 : Avec Maven

```bash
mvn clean compile exec:java -Dexec.mainClass="Main"
```

## 📱 Utilisation de l'Application

Au lancement, vous verrez le menu principal :

```
=== SYSTÈME DE GESTION D'ASSURANCE ===
1. Gérer les conseillers
2. Gérer les clients
3. Gérer les contrats
4. Gérer les sinistres
0. Quitter
Votre choix: 
```

### Exemple d'Utilisation

1. **Créer un conseiller** → Menu 1 → Option 1
2. **Créer un client** → Menu 2 → Option 1 (associer au conseiller)
3. **Créer un contrat** → Menu 3 → Option 1 (associer au client)
4. **Déclarer un sinistre** → Menu 4 → Option 1 (associer au contrat)

## 🔧 Dépannage

### Erreurs Communes

#### 1. "ClassNotFoundException: org.postgresql.Driver"
- ✅ Vérifiez que le driver PostgreSQL est dans le classpath
- ✅ Téléchargez la dernière version du driver

#### 2. "Connection refused"
- ✅ Vérifiez que PostgreSQL est démarré
- ✅ Vérifiez l'URL, le port (5432 par défaut)
- ✅ Vérifiez les identifiants de connexion

#### 3. "Relation does not exist"
- ✅ Exécutez les scripts SQL de création des tables
- ✅ Vérifiez que vous êtes connecté à la bonne base

#### 4. "Type does not exist"
- ✅ Créez les types énumérés (`type_contrat`, `statut_sinistre`)

## 📊 Modèle de Données

```
Conseiller (1) ←→ (N) Client (1) ←→ (N) Contrat (1) ←→ (N) Sinistre
```

- Un **conseiller** peut avoir plusieurs **clients**
- Un **client** peut avoir plusieurs **contrats**
- Un **contrat** peut avoir plusieurs **sinistres**

## 🤝 Contribution

1. Forkez le projet
2. Créez une branche pour votre fonctionnalité
3. Committez vos changements
4. Poussez vers la branche
5. Ouvrez une Pull Request

## 📝 Licence

Ce projet est sous licence MIT. Voir le fichier `LICENSE` pour plus de détails.

## 👨‍💻 Auteur

**Saal Mahm** - [GitHub](https://github.com/saalmahm)

## 📞 Support

Si vous rencontrez des problèmes :
1. Vérifiez la section **Dépannage**
2. Consultez les **Issues** du projet
3. Créez une nouvelle **Issue** avec une description détaillée

---

⭐ N'oubliez pas de donner une étoile si ce projet vous a été utile !