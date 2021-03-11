package com.siteStreaming.SiteStreaming.DataBase;

import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.ContenuSonore;
import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Enumérations.categorie;
import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Enumérations.genreMusical;
import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Musique;
import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Podcast;
import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Radio;
import com.siteStreaming.SiteStreaming.LoggerSite;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AdminDatabase {

    public Connection connection;
    public Statement statement;

    /**
     * Accès à la base de données
     */
    public AdminDatabase(){
        try{
            this.connection = DBManager.getInstance().getConnection();
            this.statement = this.connection.createStatement();
        } catch (SQLException e){
            LoggerSite.logger.error(e);
        }
    }

    /**
     * Afficher les n musiques les plus écoutés en fonction de la période
     */
    public List<Musique> consulterTopNMusiques(int n, String periode) throws SQLException {

        List<Musique> topNmusique = new ArrayList<>();

        int idContenuSonore;
        int nbLectureMois;
        int nbLectureTotal;
        String titre;
        String interprete;
        genreMusical genreMusical;
        int duree;
        String contenu;
        String annee;
        int counter=0;

        String qPeriode;
        if(periode.equals("mois")){
            qPeriode = "nbLectureMois";
        }else{
            qPeriode = "nbLectureTotal";
        }

        java.sql.ResultSet rs = statement.executeQuery("select * from ContenuSonore order by "+qPeriode+" desc");
        LoggerSite.logger.debug("select * from ContenuSonore order by "+qPeriode+" desc");
        while(rs.next() && counter<n){
            idContenuSonore = rs.getInt("idContenuSonore");

            Connection connexion = DBManager.getInstance().getConnection();
            Statement statement2 = connexion.createStatement();
            java.sql.ResultSet CategorieTest = statement2.executeQuery("select * from Musique where idMusique = '"+idContenuSonore+"'");

            if (CategorieTest.next()){
                nbLectureMois = rs.getInt("nbLectureMois");
                nbLectureTotal = rs.getInt("nbLectureTotal");
                contenu = rs.getString("fichierAudio");
                titre = CategorieTest.getString("titre");
                interprete = CategorieTest.getString("interprete");
                genreMusical = com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Enumérations.genreMusical.valueOf(CategorieTest.getString("genreMusical"));
                duree = CategorieTest.getInt("duree");
                annee = CategorieTest.getString("anneeCreation");
                LoggerSite.logger.debug("idContenuSonore = "+idContenuSonore+", nbLectureMois = "+nbLectureMois+", nbLectureTotal = "+nbLectureTotal);
                Musique music = new Musique(contenu,false,titre,interprete,annee,genreMusical,duree);

                topNmusique.add(music);
                counter+=1;
            }

        }

        return topNmusique;
    }

    /**
     * Afficher les n titres les plus écoutés en fonction de la période et du type
     */
    public List<ContenuSonore> consulterTopN(int n, String periode, String type) throws SQLException {

        List<ContenuSonore> topN = new ArrayList<>();

        int idContenuSonore;
        int nbLectureMois;
        int nbLectureTotal;
        String contenu;
        int counter=0;

        String qPeriode;
        if(periode.equals("mois")){
            qPeriode = "nbLectureMois";
        }else{
            qPeriode = "nbLectureTotal";
        }

        String qType;
        if(type.equals("music")){
            qType = "Musique";
        }else if (type.equals("radio")){
            qType = "Radio";
        }else{
            qType = "Podcast";
        }

        java.sql.ResultSet rs = statement.executeQuery("select * from ContenuSonore order by "+qPeriode+" desc");
        LoggerSite.logger.debug("select * from ContenuSonore order by "+qPeriode+" desc");
        while(rs.next() && counter<n){
            idContenuSonore = rs.getInt("idContenuSonore");

            Connection connexion = DBManager.getInstance().getConnection();
            Statement statement2 = connexion.createStatement();
            java.sql.ResultSet CategorieTest = statement2.executeQuery("select * from "+qType+" where id"+qType+" = '"+idContenuSonore+"'");

            switch (type) {
                case "music": {

                    String titre;
                    String interprete;
                    genreMusical genreMusical;
                    int duree;
                    String annee;

                    if (CategorieTest.next()) {
                        nbLectureMois = rs.getInt("nbLectureMois");
                        nbLectureTotal = rs.getInt("nbLectureTotal");
                        contenu = rs.getString("fichierAudio");
                        titre = CategorieTest.getString("titre");
                        interprete = CategorieTest.getString("interprete");
                        genreMusical = com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Enumérations.genreMusical.valueOf(CategorieTest.getString("genreMusical"));
                        duree = CategorieTest.getInt("duree");
                        annee = CategorieTest.getString("anneeCreation");
                        LoggerSite.logger.debug("idContenuSonore = " + idContenuSonore + ", nbLectureMois = " + nbLectureMois + ", nbLectureTotal = " + nbLectureTotal);


                        ContenuSonore cont = new Musique(contenu, false, titre, interprete, annee, genreMusical, duree);

                        topN.add(cont);
                        counter += 1;
                    }
                    break;
                }
                case "radio": {

                    String nom;
                    genreMusical genreMusical;


                    if (CategorieTest.next()) {
                        nbLectureMois = rs.getInt("nbLectureMois");
                        nbLectureTotal = rs.getInt("nbLectureTotal");
                        contenu = rs.getString("fichierAudio");
                        nom = CategorieTest.getString("nom");
                        genreMusical = com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Enumérations.genreMusical.valueOf(CategorieTest.getString("genreMusical"));
                        LoggerSite.logger.debug("idContenuSonore = " + idContenuSonore + ", nbLectureMois = " + nbLectureMois + ", nbLectureTotal = " + nbLectureTotal);
                        ContenuSonore cont = new Radio(contenu, false, nom, genreMusical);
                        topN.add(cont);
                        counter += 1;
                    }
                    break;
                }
                case "podcast": {

                    String titre;
                    String auteur;
                    int duree;
                    categorie categorie;

                    if (CategorieTest.next()) {
                        nbLectureMois = rs.getInt("nbLectureMois");
                        nbLectureTotal = rs.getInt("nbLectureTotal");
                        contenu = rs.getString("fichierAudio");
                        titre = CategorieTest.getString("titre");
                        duree = CategorieTest.getInt("duree");
                        auteur = CategorieTest.getString("auteur");
                        categorie = com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Enumérations.categorie.valueOf(CategorieTest.getString("categorie"));
                        LoggerSite.logger.debug("idContenuSonore = " + idContenuSonore + ", nbLectureMois = " + nbLectureMois + ", nbLectureTotal = " + nbLectureTotal);
                        ContenuSonore cont = new Podcast(contenu, false, titre, duree, auteur, categorie);
                        topN.add(cont);
                        counter += 1;
                    }
                    break;
                }
            }
        }

        return topN;
    }

    /**
     * Renvoie une liste d'éléments Musique en fonction du nom ou de l'ID
     * ID = -1 si recherche par nom, sinon la rehcerche se fait par ID
     * @param nom
     * @param ID
     */
    public List<Musique> getMusic(int ID, String nom) throws SQLException {

        List<Musique> music = new ArrayList<>();

        int idMusique;
        String titre;
        String interprete;
        genreMusical genreMusical;
        int duree;
        String contenu;
        String annee;

        Connection connexion = DBManager.getInstance().getConnection();
        Statement statement2 = connexion.createStatement();

        if (ID == -1) {

            java.sql.ResultSet CategorieTest = statement2.executeQuery("select * from Musique where titre LIKE '%" + nom + "%'");
            while (CategorieTest.next()) {

                idMusique = CategorieTest.getInt("idMusique");
                LoggerSite.logger.debug("id de la musique cherchée "+idMusique);
                java.sql.ResultSet rs = statement.executeQuery("select * from ContenuSonore where idContenuSonore = '" + idMusique + "'");
                rs.next();
                contenu = rs.getString("fichierAudio");
                titre = CategorieTest.getString("titre");
                interprete = CategorieTest.getString("interprete");
                genreMusical = com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Enumérations.genreMusical.valueOf(CategorieTest.getString("genreMusical"));
                duree = CategorieTest.getInt("duree");
                annee = CategorieTest.getString("anneeCreation");
                Musique musicObj = new Musique(contenu, false, titre, interprete, annee, genreMusical, duree);
                musicObj.setId(idMusique);

                music.add(musicObj);
            }
        } else {

            idMusique = ID;
            java.sql.ResultSet CategorieTest = statement2.executeQuery("select * from Musique where idMusique = '" + idMusique + "'");
            java.sql.ResultSet rs = statement.executeQuery("select * from ContenuSonore where idContenuSonore = '" + idMusique + "'");
            if (CategorieTest.next()) {
                rs.next();
                contenu = rs.getString("fichierAudio");
                titre = CategorieTest.getString("titre");
                interprete = CategorieTest.getString("interprete");
                genreMusical = com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Enumérations.genreMusical.valueOf(CategorieTest.getString("genreMusical"));
                duree = CategorieTest.getInt("duree");
                annee = CategorieTest.getString("anneeCreation");
                Musique musicObj = new Musique(contenu, false, titre, interprete, annee, genreMusical, duree);
                musicObj.setId(idMusique);
                music.add(musicObj);
            }
        }
        return music;
    }

    /**
     * Renvoie une liste d'éléments Podcast en fonction du nom ou de l'ID
     * ID = -1 si recherche par nom, sinon la rehcerche se fait par ID
     * @param nom
     * @param ID
     */
    public List<Podcast> getPodcast(int ID, String nom) throws SQLException {

        List<Podcast> podcast = new ArrayList<>();

        int idPodcast;
        String titre;
        String auteur;
        int duree;
        categorie categorie;
        String contenu;
        int counter = 0;

        Connection connexion = DBManager.getInstance().getConnection();
        Statement statement2 = connexion.createStatement();

        if (ID == -1) {

            java.sql.ResultSet CategorieTest = statement2.executeQuery("select * from Podcast where titre LIKE '%" + nom + "%'");
            while (CategorieTest.next()) {

                idPodcast = CategorieTest.getInt("idPodcast");
                java.sql.ResultSet rs = statement.executeQuery("select * from ContenuSonore where idContenuSonore = '" + idPodcast + "'");
                rs.next();
                contenu = rs.getString("fichierAudio");
                titre = CategorieTest.getString("titre");
                duree = CategorieTest.getInt("duree");
                auteur = CategorieTest.getString("auteur");
                categorie = com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Enumérations.categorie.valueOf(CategorieTest.getString("categorie"));
                Podcast pod = new Podcast(contenu, false, titre, duree, auteur, categorie);
                pod.setId(idPodcast);
                podcast.add(pod);
            }
        } else {

            idPodcast = ID;
            java.sql.ResultSet CategorieTest = statement2.executeQuery("select * from Podcast where idPodcast = '" + ID + "'");
            java.sql.ResultSet rs = statement.executeQuery("select * from ContenuSonore where idContenuSonore = '" + ID + "'");
            if (CategorieTest.next()) {
                rs.next();
                contenu = rs.getString("fichierAudio");
                titre = CategorieTest.getString("titre");
                duree = CategorieTest.getInt("duree");
                auteur = CategorieTest.getString("auteur");
                categorie = com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Enumérations.categorie.valueOf(CategorieTest.getString("categorie"));
                Podcast pod = new Podcast(contenu, false, titre, duree, auteur, categorie);
                pod.setId(idPodcast);
                podcast.add(pod);
            }
        }
        return podcast;
    }


    /**
     * Renvoie une liste d'éléments Radio en fonction du nom ou de l'ID
     * ID = -1 si recherche par nom, sinon la rehcerche se fait par ID
     * @param nom
     * @param ID
     */
    public List<Radio> getRadio(int ID, String nom) throws SQLException {

        List<Radio> radio = new ArrayList<>();

        int idRadio;
        genreMusical genreMusical;
        String contenu;

        Connection connexion = DBManager.getInstance().getConnection();
        Statement statement2 = connexion.createStatement();

        if (ID == -1) {


            java.sql.ResultSet CategorieTest = statement2.executeQuery("select * from Radio where nom LIKE '%" + nom + "%'");
            while (CategorieTest.next()) {

                idRadio = CategorieTest.getInt("idRadio");
                java.sql.ResultSet rs = statement.executeQuery("select * from ContenuSonore where idContenuSonore = '" + idRadio + "'");
                rs.next();
                contenu = rs.getString("fichierAudio");
                genreMusical = com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Enumérations.genreMusical.valueOf(CategorieTest.getString("genreMusical"));
                Radio rad = new Radio(contenu, false, nom, genreMusical);
                rad.setId(idRadio);
                radio.add(rad);
            }
        } else {

            idRadio = ID;
            java.sql.ResultSet CategorieTest = statement2.executeQuery("select * from Radio where idPodcast = '" + ID + "'");
            java.sql.ResultSet rs = statement.executeQuery("select * from ContenuSonore where idContenuSonore = '" + ID + "'");
            if (CategorieTest.next()) {
                rs.next();
                contenu = rs.getString("fichierAudio");
                genreMusical = com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Enumérations.genreMusical.valueOf(CategorieTest.getString("genreMusical"));
                Radio rad = new Radio(contenu, false, nom, genreMusical);
                rad.setId(idRadio);
                radio.add(rad);
            }
        }
        return radio;
    }

}
