package com.siteStreaming.SiteStreaming.DataBase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.ContenuSonore;
import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Enumérations.categorie;
import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Enumérations.genreMusical;
import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Musique;
import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Podcast;
import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Radio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CatalogueDatabase {
    public Connection connection;
    public Statement statement;


    /**
     * Créé une connection avec la base de données
     */
    public CatalogueDatabase() {
        try {
            this.connection = DBManager.getInstance().getConnection();
            this.statement = this.connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Vide la base de donner (ContenuSonore vide les autres qui ont on delete cascade)
     */
    public void resetCatalogue() {
        try {
            this.statement.executeUpdate("DELETE FROM ContenuSonore;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * petite fonction pour convertir le booleen en entier pour la base de donnée
     *
     * @param b a convertir
     * @return la valeur entière
     */
    public static int boolToInt(Boolean b) {
        return b.compareTo(false);
    }

    /**
     * Crée un nouveau contenu sonore dans la base de donnée
     *
     * @param contenu contenu à ajouter dans la base de donnée
     * @return true si réussi false sinon
     */
    public boolean createContenuSonore(ContenuSonore contenu) {
        PreparedStatement preparedStatement = null;
        try {
            String name = "com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.";
            //Assume a valid connection object conn
            this.connection.setAutoCommit(false);
            preparedStatement = this.connection.prepareStatement("INSERT INTO `info_team07_schema`.`ContenuSonore` " +
                    "(`fichierAudio`, `recommendationMoment`, `morceauxPopulaire`,`nbLectureMois`, `nbLectureTotal`) VALUES (?,?,?,?,?);");
            preparedStatement.setString(1, contenu.getContenu());
            preparedStatement.setInt(2, boolToInt(contenu.getRecommendationMoment()));
            preparedStatement.setInt(3, boolToInt(contenu.getMorceauPopulaire()));
            preparedStatement.setInt(4, contenu.getNbLectureMois());
            preparedStatement.setInt(5, contenu.getNbLectureTotal());
            preparedStatement.executeUpdate();
            if (contenu.getClass().getName().equals(name + "Musique")) {
                Musique musique = (Musique) contenu;
                preparedStatement = this.connection.prepareStatement("INSERT INTO `info_team07_schema`.`Musique` " +
                        "(`idMusique`, `titre`, `interprete`, `anneeCreation`, `genreMusical`, `duree`)" +
                        " VALUES (last_insert_id(),?,?,?,?,?);");
                preparedStatement.setString(1, musique.getTitre());
                preparedStatement.setString(2, musique.getInterprete());
                preparedStatement.setString(3, musique.getAnneeCreation());
                preparedStatement.setString(4, String.valueOf(musique.getGenreMusical()));
                preparedStatement.setInt(5, musique.getDuree());
            } else if (contenu.getClass().getName().equals(name + "Radio")) {
                Radio radio = (Radio) contenu;
                preparedStatement = this.connection.prepareStatement("INSERT INTO `info_team07_schema`.`Radio` (`idRadio`, `nom`, `genreMusical`) VALUES " +
                        "(last_insert_id(),?,?);");
                preparedStatement.setString(1, radio.getNom());
                preparedStatement.setString(2, String.valueOf(radio.getGenreMusical()));
            } else if (contenu.getClass().getName().equals(name + "Podcast")) {
                Podcast podcast = (Podcast) contenu;
                preparedStatement = this.connection.prepareStatement("INSERT INTO `info_team07_schema`.`Podcast` (`idPodcast`, `titre`, `duree`, `auteur`, `categorie`) VALUES " +
                        "(last_insert_id(),?,?,?,?);");
                preparedStatement.setString(1, podcast.getTitre());
                preparedStatement.setInt(2, podcast.getDuree());
                preparedStatement.setString(3, podcast.getAuteur());
                preparedStatement.setString(4, String.valueOf(podcast.getCategorie()));
            }
            preparedStatement.executeUpdate();
            // If there is no error.
            this.connection.commit();
            this.connection.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                // If there is any error.
                this.connection.rollback();
                this.connection.setAutoCommit(true);
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            return false;
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * Mets à jour dans la base de donnée toutes les informations d'un contenu sonore
     *
     * @param contenu objet avec les infos mises à jour
     * @return true si reussi, false sinon
     */
    public boolean updateContenuSonore(ContenuSonore contenu) {
        PreparedStatement preparedStatement = null;
        try {
            int id = contenu.getId();
            if (id == -1) {
                System.out.println("ce contenu n'a pas d'id !");
                return false;
            } else {
                //Assume a valid connection object conn
                this.connection.setAutoCommit(false);
                String name = "com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.";

                preparedStatement = this.connection.prepareStatement("UPDATE `info_team07_schema`.`ContenuSonore` SET `fichierAudio`=?," +
                        "`recommendationMoment` =?,`morceauxPopulaire`=?,`nbLectureMois`=?,`nbLectureTotal`=?  WHERE `idContenuSonore`=" + id + ";");
                preparedStatement.setString(1, contenu.getContenu());
                preparedStatement.setInt(2, boolToInt(contenu.getRecommendationMoment()));
                preparedStatement.setInt(3, boolToInt(contenu.getMorceauPopulaire()));
                preparedStatement.setInt(4, contenu.getNbLectureMois());
                preparedStatement.setInt(5, contenu.getNbLectureTotal());


                preparedStatement.executeUpdate();
                if (contenu.getClass().getName().equals(name + "Musique")) {
                    Musique musique = (Musique) contenu;
                    preparedStatement = this.connection.prepareStatement("UPDATE `info_team07_schema`.`Musique` " +
                            "SET `titre`=?, `interprete`=?, `anneeCreation`=?, `genreMusical`=?, `duree` = ? WHERE idMusique ='" + id + "';");
                    preparedStatement.setString(1, musique.getTitre());
                    preparedStatement.setString(2, musique.getInterprete());
                    preparedStatement.setString(3, musique.getAnneeCreation());
                    preparedStatement.setString(4, String.valueOf(musique.getGenreMusical()));
                    preparedStatement.setInt(5, musique.getDuree());

                } else if (contenu.getClass().getName().equals(name + "Radio")) {
                    Radio radio = (Radio) contenu;
                    preparedStatement = this.connection.prepareStatement("UPDATE `info_team07_schema`.`Radio` SET `nom` =?,`genreMusical`=?" +
                            "' WHERE idRadio = '" + id + "';");
                    preparedStatement.setString(1, radio.getNom());
                    preparedStatement.setString(2, String.valueOf(radio.getGenreMusical()));

                } else if (contenu.getClass().getName().equals(name + "Podcast")) {
                    Podcast podcast = (Podcast) contenu;
                    preparedStatement = this.connection.prepareStatement("UPDATE `info_team07_schema`.`Podcast` SET `titre` =? '" +
                            "', `duree` =?, `auteur` =?, `categorie` =?  WHERE idPodcast = '" + id + "';");
                    preparedStatement.setString(1, podcast.getTitre());
                    preparedStatement.setInt(2, podcast.getDuree());
                    preparedStatement.setString(3, podcast.getAuteur());
                    preparedStatement.setString(4, String.valueOf(podcast.getCategorie()));

                }
                preparedStatement.executeUpdate();
                // If there is no error.
                this.connection.commit();
                this.connection.setAutoCommit(true);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                // If there is any error.
                this.connection.rollback();
                this.connection.setAutoCommit(true);
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            return false;
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * Supprime un contenu sonore de la base de donnée
     *
     * @param contenu à suprimer
     * @return true si réussi false sinon
     */
    public boolean deleteContenuSonore(ContenuSonore contenu) {
        try {
            int id = contenu.getId();
            if (id == -1) {
                System.out.println("ce contenu n'a pas d'id !");
                return false;
            } else {
                // Suffisant car on a ON DELETE CASCADE sur tous les autres ids
                String query = "DELETE FROM `info_team07_schema`.`ContenuSonore` " +
                        "WHERE idContenuSonore='" + id + "';";
                this.statement.executeUpdate(query);

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                // If there is any error.
                this.connection.rollback();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            return false;
        }

    }


    /**
     * Mets à jour dans la base de donnée les infos de lectures d'un contenu sonore
     * (que les infos du contenu sonore, pas celles plus precise de la musique, la radio ou le podcast)
     *
     * @param contenu obet avec les infos mises à jour
     * @return true si réussi, false sinon
     */
    public boolean infoStatMAJContenuSonore(ContenuSonore contenu) {
        PreparedStatement preparedStatement = null;
        try {
            int id = contenu.getId();
            if (id == -1) {
                System.out.println("ce contenu n'a pas d'id !");
                return false;
            } else {
                preparedStatement = this.connection.prepareStatement("UPDATE `info_team07_schema`.`ContenuSonore` SET `fichierAudio`=?," +
                        "`recommendationMoment` =?,`morceauxPopulaire`=?,`nbLectureMois`=?,`nbLectureTotal`=?  WHERE `idContenuSonore`=" + id + ";");
                preparedStatement.setString(1, contenu.getContenu());
                preparedStatement.setInt(2, boolToInt(contenu.getRecommendationMoment()));
                preparedStatement.setInt(3, boolToInt(contenu.getMorceauPopulaire()));
                preparedStatement.setInt(4, contenu.getNbLectureMois());
                preparedStatement.setInt(5, contenu.getNbLectureTotal());
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * Renvoie un resultset de tous les résultat de la base de donnée correspondant à un type
     * de contenu sonore ou tous les contenus sonore
     *
     * @param choix  "musique", "radio" ou "podcast"
     * @param filtre filtre à rajouter si voulu, sinon mettre une chaine vide ""
     * @param param  entrée d'une recherche d'un utilisateur, null si non utilisé dans le filtre (pas de  ?)
     * @return le resultset de la query si réussi, null sinon
     */
    public ResultSet getAllBy(String choix, String filtre, String param) {
        PreparedStatement preparedStatement = null;
        try {
            if (choix.equals("musique")) {
                preparedStatement = this.connection.prepareStatement("SELECT * FROM ContenuSonore,Musique WHERE idContenuSonore=idMusique" +
                        "" + filtre + ";");
            } else if (choix.equals("radio")) {
                preparedStatement = this.connection.prepareStatement("SELECT * FROM ContenuSonore,Radio WHERE idContenuSonore=idRadio" +
                        filtre + ";");
            } else if (choix.equals("podcast")) {
                preparedStatement = this.connection.prepareStatement("SELECT * FROM ContenuSonore,Podcast WHERE idContenuSonore=idPodcast" + filtre + ";");
            } else {
                System.out.println("L'entrée choix n'est pas valide");
                return null;
            }
            if (param != null) {
                preparedStatement.setString(1, param);
            }
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        /*finally {
            try {
                preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }*/
    }


    /**
     * Lire un resultset donné selon s'il s'agit de musiques, de radio, ou de podcast
     *
     * @param choix "musique", "radio", "podcast"
     * @param res   resultset à lire
     * @return la liste des contenus sonore du type choisi du resultset donné
     */
    public List<ContenuSonore> readResultset(String choix, ResultSet res) {
        try {
            ContenuSonore temp;
            List<ContenuSonore> catalogue = new ArrayList<>();

            if (choix.equals("musique")) {
                while (res.next()) { //S'il y a un élément dans le résultat
                    temp = new Musique(res.getString("fichierAudio"), res.getBoolean("recommendationMoment"),
                            res.getString("titre"), res.getString("interprete"), res.getString("anneeCreation"),
                            genreMusical.valueOf(res.getString("genreMusical")), res.getInt("duree"));
                    temp.setId(res.getInt("idContenuSonore"));
                    catalogue.add(temp);
                }
            } else if (choix.equals("radio")) {
                while (res.next()) { //S'il y a un élément dans le résultat
                    temp = new Radio(res.getString("fichierAudio"), res.getBoolean("recommendationMoment"),
                            res.getString("nom"), genreMusical.valueOf(res.getString("genreMusical")));
                    temp.setId(res.getInt("idContenuSonore"));
                    catalogue.add(temp);
                }
            } else if (choix.equals("podcast")) {
                while (res.next()) { //S'il y a un élément dans le résultat
                    temp = new Podcast(res.getString("fichierAudio"), res.getBoolean("recommendationMoment"),
                            res.getString("titre"), res.getInt("duree"), res.getString("auteur"),
                            categorie.valueOf(res.getString("categorie")));
                    temp.setId(res.getInt("idContenuSonore"));
                    catalogue.add(temp);
                }
            } else {
                System.out.println("L'entrée choix n'est pas valide");
            }
            return catalogue;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Donne le catalogue complet en demandant toutes les musiques, radio et podcast sans aucun filtre
     *
     * @return la liste des contenus sonores du catalogue
     */
    public List<ContenuSonore> getAllCatalogue() {
        List<ContenuSonore> resultat = this.readResultset("musique", this.getAllBy("musique", "", null));
        resultat.addAll(this.readResultset("radio", this.getAllBy("radio", "", null)));
        resultat.addAll(this.readResultset("podcast", this.getAllBy("podcast", "", null)));
        return resultat;

    }

    /**
     * Donne le catalogue complet d'un type : musique ou radio ou podcast
     *
     * @return la liste des contenus sonores du catalogue du type choisi
     */
    public List<ContenuSonore> getTypeCatalogue(String choix) {
        if (choix.equals("musique")) {
            return this.readResultset("musique", this.getAllBy("musique", "", null));
        } else if (choix.equals("radio")) {
            return this.readResultset("radio", this.getAllBy("radio", "", null));
        } else if (choix.equals("podcast")) {
            return this.readResultset("podcast", this.getAllBy("podcast", "", null));
        } else {
            System.out.println("Choix du type de contenu sonore incorrect");
            return null;
        }
    }


    /**
     * Renvoie tous les contenus sonore dont le titre ou le nom contient la chaine rentrée
     *
     * @param title chaine qui doit être dans le titre ou le nom
     * @return liste de tous les contenus sonores trouvés
     */
    public List<ContenuSonore> searchAllByTitle(String title) {
        List<ContenuSonore> resultat = this.readResultset("musique", this.getAllBy("musique",
                " and titre like concat('%',?,'%')", title));
        resultat.addAll(this.readResultset("radio", this.getAllBy("radio",
                " and nom like concat('%',?,'%')", title)));
        resultat.addAll(this.readResultset("podcast", this.getAllBy("podcast",
                " and titre like concat('%',?,'%')", title)));
        return resultat;
    }

    /**
     * Renvoie tous les contenus sonore dont l'auteur ou l'interprete contient les caractères recherchés
     *
     * @param auteur chaine qui doit être dansl'auteur ou l'interprete
     * @return liste de tous les contenus sonores trouvés
     */
    public List<ContenuSonore> searchByAutor(String auteur) {
        List<ContenuSonore> resultat = this.readResultset("musique", this.getAllBy("musique",
                " and interprete like concat('%',?,'%')", auteur));
        resultat.addAll(this.readResultset("podcast", this.getAllBy("podcast",
                " and auteur like concat('%',?,'%')", auteur)));
        return resultat;
    }

    /**
     * Renvoie tous les contenus sonore dont le genre musical correspond à celui recherché :
     * ne peuvent être que des musiques ou des radios
     *
     * @param genre chaine qui doit être dans genreMusical
     * @return liste de tous les contenus sonores trouvés
     */
    public List<ContenuSonore> searchByGenreMusical(String genre) {
        List<ContenuSonore> resultat = this.readResultset("musique", this.getAllBy("musique",
                " and genreMusical like concat('%',?,'%')", genre));
        resultat.addAll(this.readResultset("radio", this.getAllBy("radio",
                " and genreMusical like concat('%',?,'%')", genre)));
        return resultat;
    }

    /**
     * Renvoie tous les contenus sonore dont le genre musical correspond à celui recherché :
     * ne peuvent être que des musiques ou des radios
     *
     * @param categorie chaine qui doit être dans genreMusical
     * @return liste de tous les contenus sonores trouvés
     */
    public List<ContenuSonore> searchByCategorie(String categorie) {
        return this.readResultset("podcast", this.getAllBy("podcast",
                " and categorie like concat('%',?,'%')", categorie));
    }

    /**
     * Renvoie les éléments du catalogue qui sont les recommendations du moment
     *
     * @return liste des éléments du catalogue qui sont les recommendations du moment
     */
    public List<ContenuSonore> getRecommendationMoment() {
        List<ContenuSonore> resultat = this.readResultset("musique", this.getAllBy("musique",
                " and ContenuSonore.recommendationMoment=1", null));
        resultat.addAll(this.readResultset("radio", this.getAllBy("radio",
                " and ContenuSonore.recommendationMoment=1", null)));
        resultat.addAll(this.readResultset("podcast", this.getAllBy("podcast",
                " and ContenuSonore.recommendationMoment=1", null)));
        return resultat;
    }

    /**
     * Renvoie les éléments du catalogue qui sont les recommendations du moment
     *
     * @return liste des éléments du catalogue qui sont les recommendations du moment
     */
    public List<ContenuSonore> getMorceauxPopulaires() {
        List<ContenuSonore> resultat = this.readResultset("musique", this.getAllBy("musique",
                " and ContenuSonore.morceauxPopulaire=1", null));
        resultat.addAll(this.readResultset("radio", this.getAllBy("radio",
                " and ContenuSonore.morceauxPopulaire=1", null)));
        resultat.addAll(this.readResultset("podcast", this.getAllBy("podcast",
                " and ContenuSonore.morceauxPopulaire=1", null)));
        return resultat;
    }


    public void close() {
        try {
            this.statement.close();
            this.connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //Tests
        CatalogueDatabase catDatabase = new CatalogueDatabase();

/*
       Musique m = new Musique("linknewlink", false, "les orguilleuse", "William Sheller"
       , "1988", genreMusical.pop,190);
        Musique m1 = new Musique("likliklink", false, "l'internationale", "inconnu"
                , "1900", genreMusical.disco,340);
        Radio r = new Radio("radio france",false,"radio france",genreMusical.salsa);

        Podcast p = new Podcast("tscadop",true,"tscedop",30,"ednom el",categorie.histoire);
*/

        //catDatabase.resetCatalogue();
/*
       catDatabase.createContenuSonore(m);
        catDatabase.createContenuSonore(m1);
        catDatabase.createContenuSonore(r);
        catDatabase.createContenuSonore(p);
*/


     List<ContenuSonore> liste = catDatabase.getAllCatalogue();

        liste.get(1).setContenu("new content");
        catDatabase.updateContenuSonore(liste.get(1));

        catDatabase.deleteContenuSonore(liste.get(0));


        for (ContenuSonore contenuSonore : liste) {
            System.out.print(contenuSonore.getContenu() + "--");
        }
        System.out.println("recommendation");

        liste = catDatabase.getRecommendationMoment();
        for (ContenuSonore contenuSonore : liste) {
            System.out.print(contenuSonore.getContenu() + "--");
        }
        System.out.println("morc pop");
        liste = catDatabase.getMorceauxPopulaires();
        for (ContenuSonore contenuSonore : liste) {
            System.out.print(contenuSonore.getContenu() + "--");
        }
        System.out.println("title");
        liste = catDatabase.searchAllByTitle("les");
        for(int i=0;i<liste.size();i++){
            System.out.print(liste.get(i).getContenu() + "--");
        }
        System.out.println("autor");

        liste = catDatabase.searchByAutor("wi");
        for(int i=0;i<liste.size();i++){
            System.out.print(liste.get(i).getContenu() + "--");
        }
        System.out.println("genremus");

        liste = catDatabase.searchByGenreMusical("po");
        for(int i=0;i<liste.size();i++){
            System.out.print(liste.get(i).getContenu() + "--");
        }
        System.out.println("categorie");

        liste = catDatabase.searchByCategorie("histoi");

        for(int i=0;i<liste.size();i++){
            System.out.print(liste.get(i).getContenu() + "--");
        }
        System.out.println("");



    }
}
