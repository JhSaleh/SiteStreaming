# Projet JEE site de streaming musical : UsTube

## Equipe 7

- Jean-Hanna SALEH
- Juliette DECLERCK
- Erwan BOISARD
- Romain BARDINET

## Informations

Le dernier code se trouve sur la branche master.

Utilisation de Maven pour les dépendances.

L'accès à la base de donnée peut être configuré dans le fichier config.properties, qui se trouve dans le dossier resources.

Le fichier logger sans modification du fichier log4j.properties du dossier resource écrit en mode DEBUG dans ../logs/info.log donc dans un fichier nommé info qui se trouve dans les logs de Tomcat une fois déployé.

Certaines classes comme CatalogueDatabase, PlaylistDatabase, etc ont une procédure main qui sert uniquement aux tests, et peut contenir des lignes de codes obsolètes.

## Fonctionnalités à tester
### Partie Utilisateur
- Afficher les morceaux les plus populaires sur la page d'accueil
- Inscription et création d'un compte utilisateur
- Accéder à l'ensemble du catalogue musical une fois connecté
- Connection à un commpte utilisateur
- Accéder à sa page de profil
- Modifier ses informations personnelles
- Ajouter, modifier ou supprimer une playlist
- Rechercher dans le catalogue
- Écouter un contenu sonore depuis le catalogue ou l'accueil
- Impossibilité d'accéder aux fonctionnalités administrateur
- Déconnection de son compte utilisateur


### Partie Administrateur
- Connection à un compte administrateur
- Rechercher un profil utilisateur
- Modifier les informations personnelles d'un utilisateur
- Rechercher un contenu sonore
- Ajouter, modifier ou supprimer un élément dans le catalogue
- Déconnection de son compte administrateur




## Compilation du projet

``
mvn compile war:war
``

## Demarrer TOMCAT sous Ubuntu (Unix)

``
sudo /opt/tomcat/latest/bin/startup.sh
``

## Arreter TOMCAT sous Ubuntu (Unix)

``
sudo /opt/tomcat/latest/bin/shutdown.sh
``
