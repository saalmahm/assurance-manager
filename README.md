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

- **Java 8+** – Langage de programmation principal
- **PostgreSQL** – Base de données relationnelle
- **JDBC** – Connecteur base de données
- **Bibliothèques locales** – Les dépendances sont dans `src/lib/` (par ex. driver PostgreSQL)
- **Architecture Modèle-Service-Vue** – Séparation entre modèles, services métier et interface utilisateur


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
│   │   └── TypeSinistre.java
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
│   ├── lib/                    # Bibliothèques (si sans Maven)
│   │   └── postgresql-43.7.8.jar
│   │
│   └── Main.java               # Point d'entrée de l'application
│
├── SchemaSQL/                  # Scripts SQL
│   └── assurance-manager.sql
│
├── Assurance.jar               # Fichier JAR exécutable
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
CREATE DATABASE assurance-manager;
\c assurance-manager;
```

#### Exécuter le script de création des tables :

    1-Ouvre pgAdmin
    
    2-Clique droit sur ta base assurance-manager → Restore / Import
    
    3-Sélectionne le fichier assurance-manager.sql
    
    4-Exécute → les tables et types seront créés automatiquement.

### 3. Configuration de la Connexion

Modifiez le fichier `DatabaseConnection.java` avec vos paramètres :

```java
private static final String URL = "jdbc:postgresql://localhost:5432/assurance-manager";
private static final String USERNAME = "votre_nom_utilisateur";
private static final String PASSWORD = "votre_mot_de_passe";
```

### 4. Ajouter le Driver PostgreSQL

1. Téléchargez le driver JDBC PostgreSQL depuis [ici](https://jdbc.postgresql.org/download/)
2. Placez le fichier `.jar` dans le dossier `lib/`
3. Ajoutez-le au classpath de votre IDE


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

## 📊 Modèle de Données

```
Conseiller (1) ←→ (N) Client (1) ←→ (N) Contrat (1) ←→ (N) Sinistre
```

- Un **conseiller** peut avoir plusieurs **clients**
- Un **client** peut avoir plusieurs **contrats**
- Un **contrat** peut avoir plusieurs **sinistres**



## 👨‍💻 Auteur

**Saalma hm** - [GitHub](https://github.com/saalmahm)


⭐ N'oubliez pas de donner une étoile si ce projet vous a été utile !