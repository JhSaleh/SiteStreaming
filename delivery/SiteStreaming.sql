-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema info_team07_schema
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `info_team07_schema` ;

-- -----------------------------------------------------
-- Schema info_team07_schema
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `info_team07_schema` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `info_team07_schema` ;

-- -----------------------------------------------------
-- Table `info_team07_schema`.`Compte`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `info_team07_schema`.`Compte` ;

CREATE TABLE IF NOT EXISTS `info_team07_schema`.`Compte` (
  `adresseMail` VARCHAR(45) NOT NULL,
  `civilite` VARCHAR(45) NOT NULL,
  `nom` VARCHAR(45) NOT NULL,
  `prenom` VARCHAR(45) NOT NULL,
  `motDePasse` VARCHAR(45) NOT NULL,
  `dateNaissance` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`adresseMail`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `info_team07_schema`.`CompteAdmin`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `info_team07_schema`.`CompteAdmin` ;

CREATE TABLE IF NOT EXISTS `info_team07_schema`.`CompteAdmin` (
  `idCompteAdmin` INT NOT NULL AUTO_INCREMENT,
  `adresseMailAdmin` VARCHAR(45) NOT NULL,
  `isProfilManagerClient` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idCompteAdmin`),
  INDEX `CompteAdmin_FK` (`adresseMailAdmin` ASC),
  CONSTRAINT `CompteAdmin_FK`
    FOREIGN KEY (`adresseMailAdmin`)
    REFERENCES `info_team07_schema`.`Compte` (`adresseMail`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `info_team07_schema`.`CompteClient`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `info_team07_schema`.`CompteClient` ;

CREATE TABLE IF NOT EXISTS `info_team07_schema`.`CompteClient` (
  `idCompteClient` INT NOT NULL AUTO_INCREMENT,
  `adresseMailClient` VARCHAR(45) NOT NULL,
  `adresseFacturation` VARCHAR(45) NOT NULL,
  `styleMusique` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idCompteClient`),
  INDEX `CompteClient_FK` (`adresseMailClient` ASC),
  CONSTRAINT `CompteClient_FK`
    FOREIGN KEY (`adresseMailClient`)
    REFERENCES `info_team07_schema`.`Compte` (`adresseMail`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 38
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `info_team07_schema`.`ContenuSonore`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `info_team07_schema`.`ContenuSonore` ;

CREATE TABLE IF NOT EXISTS `info_team07_schema`.`ContenuSonore` (
  `idContenuSonore` INT NOT NULL AUTO_INCREMENT,
  `fichierAudio` VARCHAR(45) NOT NULL,
  `recommendationMoment` TINYINT NOT NULL DEFAULT '0',
  `morceauxPopulaire` TINYINT NOT NULL DEFAULT '0',
  `nbLectureMois` INT NOT NULL DEFAULT '0',
  `nbLectureTotal` INT NOT NULL DEFAULT '0',
  PRIMARY KEY (`idContenuSonore`))
ENGINE = InnoDB
AUTO_INCREMENT = 90
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `info_team07_schema`.`Musique`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `info_team07_schema`.`Musique` ;

CREATE TABLE IF NOT EXISTS `info_team07_schema`.`Musique` (
  `idMusique` INT NOT NULL,
  `titre` VARCHAR(45) NULL DEFAULT NULL,
  `interprete` VARCHAR(45) NULL DEFAULT NULL,
  `anneeCreation` VARCHAR(45) NULL DEFAULT NULL,
  `genreMusical` VARCHAR(45) NULL DEFAULT NULL,
  `duree` INT NULL DEFAULT NULL,
  PRIMARY KEY (`idMusique`),
  CONSTRAINT `fk_Musique_1`
    FOREIGN KEY (`idMusique`)
    REFERENCES `info_team07_schema`.`ContenuSonore` (`idContenuSonore`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `info_team07_schema`.`Playlist`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `info_team07_schema`.`Playlist` ;

CREATE TABLE IF NOT EXISTS `info_team07_schema`.`Playlist` (
  `idPlaylist` INT NOT NULL AUTO_INCREMENT,
  `idCompteClient` INT NOT NULL,
  `titre` VARCHAR(45) NOT NULL,
  `dureeTotale` INT NOT NULL,
  `anneeCreation` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idPlaylist`),
  INDEX `Playlist_FK` (`idCompteClient` ASC),
  CONSTRAINT `Playlist_FK`
    FOREIGN KEY (`idCompteClient`)
    REFERENCES `info_team07_schema`.`CompteClient` (`idCompteClient`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 32
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `info_team07_schema`.`ContenuPlaylist`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `info_team07_schema`.`ContenuPlaylist` ;

CREATE TABLE IF NOT EXISTS `info_team07_schema`.`ContenuPlaylist` (
  `idMusique` INT NOT NULL,
  `idPlaylist` INT NOT NULL,
  `position` INT NOT NULL,
  PRIMARY KEY (`idMusique`, `idPlaylist`),
  INDEX `fk_ContenuPlaylist_2` (`idPlaylist` ASC),
  CONSTRAINT `fk_ContenuPlaylist_1`
    FOREIGN KEY (`idMusique`)
    REFERENCES `info_team07_schema`.`Musique` (`idMusique`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_ContenuPlaylist_2`
    FOREIGN KEY (`idPlaylist`)
    REFERENCES `info_team07_schema`.`Playlist` (`idPlaylist`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `info_team07_schema`.`Podcast`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `info_team07_schema`.`Podcast` ;

CREATE TABLE IF NOT EXISTS `info_team07_schema`.`Podcast` (
  `idPodcast` INT NOT NULL,
  `titre` VARCHAR(45) NULL DEFAULT NULL,
  `duree` INT NULL DEFAULT NULL,
  `auteur` VARCHAR(45) NULL DEFAULT NULL,
  `categorie` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idPodcast`),
  CONSTRAINT `fk_Podcast_1`
    FOREIGN KEY (`idPodcast`)
    REFERENCES `info_team07_schema`.`ContenuSonore` (`idContenuSonore`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `info_team07_schema`.`Radio`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `info_team07_schema`.`Radio` ;

CREATE TABLE IF NOT EXISTS `info_team07_schema`.`Radio` (
  `idRadio` INT NOT NULL,
  `nom` VARCHAR(45) NULL DEFAULT NULL,
  `genreMusical` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idRadio`),
  CONSTRAINT `fk_Radio_1`
    FOREIGN KEY (`idRadio`)
    REFERENCES `info_team07_schema`.`ContenuSonore` (`idContenuSonore`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

--
-- Data for table `Compte`
--
INSERT INTO `Compte` VALUES ('aarobase@mail','M','Moulinex','lopmiur','vgtbhynju','12/12/12'),('aarobase@mail2','M','Moulinex','lopmiur','vgtbhynju','12/12/12'),('dora78@exploratrice.com','Monsieur','Harry','Gonzales','fjYn7p6yBcwWaPp','1994-01-09'),('dragon@gmail.com','Monsieur','Rachel','Bob','fjYn7p6yBcwWaPp','1992-02-02'),('gandalf2@hotmail.com','Monsieur','Rachel','Lee','fjYn7p6yBcwWaPp','1993-02-02'),('gandH@gmail.com','Monsieur','Gand','Hunter','fjYn7p6yBcwWaPp','2004-02-03'),('gunth@hotmail.fr','Monsieur','Gunther','Gunts','fjYn7p6yBcwWaPp','2007-02-06'),('gunth2@hotmail.fr','Monsieur','Gunther','Gunts','fjYn7p6yBcwWaPp','2007-02-06'),('gunth3@hotmail.fr','Monsieur','Gunther','Gunts','fjYn7p6yBcwWaPp','2007-02-06'),('gunth4@hotmail.fr','Monsieur','Gunther','Gunts','fjYn7p6yBcwWaPp','2007-02-06'),('harryJ@gmail.com','Monsieur','Instagram','James','fjYn7p6yBcwWaPp','2001-09-09'),('john@gmail.com','Monsieur','John','Tellor','72b6gqNEvHfDeki','1988-12-02'),('joshS@gmail.com','Monsieur','Josh','ManageClient','fjYn7p6yBcwWaPp','1988-12-02'),('joshS2@gmail.com','Monsieur','Tyler','ManageMusicLibrary','fjYn7p6yBcwWaPp','1988-12-02'),('loop@loop.loop','Monsieur','Loop','Loop','mGa9tJq9iqwsBNk','1999-12-20'),('marley@yahoo.com','Monsieur','Marley','Bob','B22PP5qfs3f3i33','1993-03-11'),('mm@gmail.com','Monsieur','Michel','Michel','fjYn7p6yBcwWaPp','1950-08-06');

--
-- Data for table `CompteAdmin`
--
INSERT INTO `CompteAdmin` VALUES (1,'joshS@gmail.com','true'),(2,'joshS2@gmail.com','false');

--
-- Data for table `CompteClient`
--
INSERT INTO `CompteClient` VALUES (22,'gandalf2@hotmail.com','oio','House'),(23,'dragon@gmail.com','Paris, Boys','Metal'),(24,'harryJ@gmail.com','Paris','Classique'),(25,'gandH@gmail.com','Mexique','House'),(26,'gunth@hotmail.fr','Paris','House'),(27,'gunth2@hotmail.fr','Paris','House'),(28,'gunth3@hotmail.fr','Paris','House'),(29,'gunth4@hotmail.fr','Paris','House'),(30,'dora78@exploratrice.com','Poudlard','Pop'),(31,'mm@gmail.com','Russie','Jazz'),(32,'aarobase@mail','12 prepre LPOP 70345','Boys'),(33,'marley@yahoo.com','UK','Pop'),(35,'john@gmail.com','USA','House'),(36,'loop@loop.loop','9 passage du chemin','House'),(37,'aarobase@mail2','12 prepre LPOP 70345','Boys');


--
-- Data for table `ContenuSonore`
--
INSERT INTO `ContenuSonore` VALUES (54,'ici lien',1,0,0,0),(55,'lien',1,0,0,0),(56,'Du contenu audio.',0,0,0,0),(57,'pourl\'auteur',0,0,0,0),(58,'pour cat',0,0,0,0),(61,'new content',0,1,0,8),(62,'freemusic.com',0,1,0,8),(63,'linktothemusic',1,1,0,5),(64,'freemusic.com',1,1,0,2),(66,'likliklink',0,0,0,3),(67,'Du contenu audio.',0,0,0,4),(69,'linknewlink',0,0,0,0),(70,'likliklink',1,0,0,7),(71,'radio france',0,0,0,0),(72,'tscadop',1,0,0,0),(73,'linknewlink',0,0,0,0),(74,'likliklink',0,0,0,0),(75,'radio france',0,0,0,0),(76,'tscadop',1,0,0,0),(77,'linknewlink',0,0,0,0),(78,'likliklink',0,0,0,0),(80,'tscadop',1,0,0,0),(81,'linktothemusic',0,1,0,0),(82,'freemusic.com',1,0,0,7),(83,'Du contenu audio.',0,0,0,1),(84,'Du contenu audio.',0,0,0,0),(85,'Du contenu audio.',0,0,0,0),(86,'Du contenu audio.',0,0,0,0),(87,'Du contenu audio.',0,0,0,1),(88,'newMus',1,0,0,2),(89,'newMus',1,0,0,0);

--
-- Data for table `Musique`
--
INSERT INTO `Musique` VALUES (61,'Melodie du soir','joueur de piano','2007','house',100),(62,'Lullaby','orchestre le grand','1900','pop',9000),(63,'Melodie du matin','Greedy Snow','1790','classique',100),(64,'Concerto pour trois violons','orchestre le grand','1990','classique',900),(66,'L\'internationale','inconnu','1999','métal',340),(69,'Les orgueuilleuse','Peaceful Delightful','1988','pop',190),(70,'Because For The Memories','inconnu','1900','house',340),(73,'Wild For A Rainy Day','Warm Optimistic','1988','pop',190),(74,'Wild Secrets','inconnu','1900','pop',340),(77,'Evening For The Good Mood','Dark Spot','1988','house',190),(78,'I Said He Has A Way','inconnu','1900','métal',340),(81,'Road Flower','joueur de piano','1940','classique',100),(82,'Midnight Moon','orchestre le grand','1900','classique',9000),(83,'Une balade dans la prairie','B James','1996','pop',120),(88,'Promise For The Morning','elizabeth','1001','pop',40),(89,'Evening Miracle','Luis Victor','1990','jazz',40);

--
-- Data for table `Podcast`
--
INSERT INTO `Podcast` VALUES (56,'Joe Rogan',200,'ergotique','politique'),(57,'pasici',21,'oublie','histoire'),(58,'pasici',21,'Trois jours','divertissement'),(72,'tscedop',30,'ednom el','histoire'),(76,'tscedop',30,'Nián Qīng','divers'),(80,'tscedop',30,'La guerre des mondes','histoire');

--
-- Data for table `Radio`
--
INSERT INTO `Radio` VALUES (54,'Poiuyt','jazz'),(55,'hasard','classique'),(67,'radio france 2','jazz'),(84,'NRJ','house'),(85,'Nostalgie','pop'),(86,'Skyrock','jazz'),(87,'ChÃ©rie FM','pop');

--
-- Data for table `Playlist`
--
INSERT INTO `Playlist` VALUES (20,32,'mesmusiquesperso',29420,'2021'),(21,32,'mesmusiquespe',0,'2021-03-11'),(22,32,'mesmusiques',0,'2021-03-11'),(23,32,'mes',0,'2021-03-11'),(24,32,'test',0,'2021-03-11'),(25,32,'test',0,'2021-03-11'),(26,37,'mesmusiquesperso',40,'2021-03-11'),(30,23,'Fun',9100,'2021-03-11'),(31,36,'Playlist',100,'2021-03-11');

--
-- Data for table `ContenuPlaylist`
--
INSERT INTO `ContenuPlaylist` VALUES (61,20,1),(61,30,1),(61,31,1),(62,20,2),(62,30,2),(63,20,3),(64,20,4),(66,20,6),(69,20,7),(70,20,8),(73,20,9),(74,20,10),(77,20,11),(78,20,12),(81,20,13),(82,20,14),(88,26,1);


--
-- Event pour le nombre de vue par mois
--
CREATE EVENT reset_vues_mois
ON SCHEDULE every 1 month
DO UPDATE ContenuSonore set nbLectureMois=0 where idContenuSonore!=-1;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
