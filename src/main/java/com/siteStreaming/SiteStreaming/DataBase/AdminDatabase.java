package com.siteStreaming.SiteStreaming.DataBase;

import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.ContenuSonore;
import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Enumérations.genreMusical;
import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Musique;

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
            e.printStackTrace();
        }
    }

    /**
     * Afficher les n titres les plus écoutés
     */
    public List<ContenuSonore> consulterTopNMusiques(int n, String periode) throws SQLException {

        List<ContenuSonore> topNmusique = new ArrayList<>();

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

        java.sql.ResultSet rs = statement.executeQuery("select * from ContenuSonore order by nbLectureMois desc");
        System.out.println("passed query = select * from ContenuSonore order by nbLectureMois desc");
        while(rs.next() && counter<n){
            idContenuSonore = rs.getInt("idContenuSonore");

            java.sql.Connection connexion = DBManager.getInstance().getConnection();
            java.sql.Statement statement2 = connexion.createStatement();
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
                System.out.println("idContenuSonore = "+idContenuSonore+", nbLectureMois = "+nbLectureMois+", nbLectureTotal = "+nbLectureTotal);
                Musique music = new Musique(contenu,false,titre,interprete,annee,genreMusical,duree);

                topNmusique.add(music);
                counter+=1;
            }

        }

        return topNmusique;
    }
}
