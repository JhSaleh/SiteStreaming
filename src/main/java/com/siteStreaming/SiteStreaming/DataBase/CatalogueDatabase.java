package com.siteStreaming.SiteStreaming.DataBase;

import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.ContenuSonore;
import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Enumérations.categorie;
import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Enumérations.genreMusical;
import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Musique;
import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Podcast;
import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Radio;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CatalogueDatabase {
    public Connection connection;
    public Statement statement;


    /**
     * Créé une connection avec la base de données
     */
    public CatalogueDatabase(){
        try{
            this.connection = DBManager.getInstance().getConnection();
            this.statement = this.connection.createStatement();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Vide la base de donner (ContenuSonore vide les autres qui ont on delete cascade)
     */
    public void resetCatalogue(){
        try {
            this.statement.executeUpdate("DELETE FROM ContenuSonore;");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * petite fonction pour convertir le booleen en entier pour la base de donnée
     * @param b a convertir
     * @return la valeur entière
     */
    public static int boolToInt(Boolean b){
        return b.compareTo(false);
    }

    /**
     * Crée un nouveau contenu sonore dans la base de donnée
     * !!! aux caractères spéciaux : exemple pour ' il faudra remplacer par ''  !!!
     * (Je n'ai pas testé les autres caractères spéciaux)
     * @param contenu contenu à ajouter dans la base de donnée
     * @return true si réussi false sinon
     */
    public boolean createContenuSonore(ContenuSonore contenu){
        try{
            //Assume a valid connection object conn
            this.connection.setAutoCommit(false);
            String name = "com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.";

            String query = "INSERT INTO `info_team07_schema`.`ContenuSonore` " +
                    "(`fichierAudio`, `recommendationMoment`, `morceauxPopulaire`, " +
                    "`nbLectureMois`, `nbLectureTotal`) VALUES " +
                    "('"+contenu.getContenu()+"','"+boolToInt(contenu.getRecommendationMoment())+
                    "' ,'"+boolToInt(contenu.getMorceauPopulaire()) + "','" +contenu.getNbLectureMois()+
                    "','"+ contenu.getNbLectureTotal()+"');";
            this.statement.executeUpdate(query);
            if(contenu.getClass().getName().equals(name+"Musique")){
                Musique musique = (Musique) contenu;
                query = "INSERT INTO `info_team07_schema`.`Musique` "+
                        "(`idMusique`, `titre`, `interprete`, `anneeCreation`, `genreMusical`, `duree`)"+
                        " VALUES (last_insert_id(), '"+musique.getTitre()+"','"+musique.getInterprete()+
                    "','"+musique.getAnneeCreation()+"','"+musique.getGenreMusical()+"','" +
                            musique.getDuree()+"');";
            }else if(contenu.getClass().getName().equals(name+"Radio")){
                Radio radio = (Radio) contenu;
                query = "INSERT INTO `info_team07_schema`.`Radio` (`idRadio`, `nom`, `genreMusical`) VALUES " +
                        "(last_insert_id(),'"+radio.getNom()+"','"+radio.getGenreMusical()+"');";
            }else if(contenu.getClass().getName().equals(name+"Podcast")){
                Podcast podcast = (Podcast) contenu;
                query = "INSERT INTO `info_team07_schema`.`Podcast` (`idPodcast`, `titre`, `duree`, `auteur`, `categorie`) VALUES " +
                        "(last_insert_id(),'"+podcast.getTitre()+"','"+podcast.getDuree()+"','"+
                        podcast.getAuteur()+"','"+podcast.getCategorie()+"');";
            }
            this.statement.executeUpdate(query);
            // If there is no error.
            this.connection.commit();
            this.connection.setAutoCommit(true);
            return true;
        } catch(SQLException e){
            e.printStackTrace();
            try {
                // If there is any error.
                this.connection.rollback();
                this.connection.setAutoCommit(true);
            }catch(SQLException e2) {
                e2.printStackTrace();
            }
            return false;
        }

    }

    /**
     * Mets à jour dans la base de donnée toutes les informations d'un contenu sonore
     * @param contenu objet avec les infos mises à jour
     * @param id id de l'objet à mettre à jour
     * @return true si reussi, false sinon
     */
    public boolean updateContenuSonore(ContenuSonore contenu, int id){
        try{
            //Assume a valid connection object conn
            this.connection.setAutoCommit(false);
            String name = "com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.";


            String query = "UPDATE `info_team07_schema`.`ContenuSonore` SET `fichierAudio`='"+contenu.getContenu()+"',"+
                    "`recommendationMoment` ='"+boolToInt(contenu.getRecommendationMoment())+"', `morceauxPopulaire`='"
                    +boolToInt(contenu.getMorceauPopulaire())+"',`nbLectureMois`='"+contenu.getNbLectureMois()+
                    "',`nbLectureTotal`='"+contenu.getNbLectureTotal()+"' WHERE (`idContenuSonore` = '"+id+"');";

            this.statement.executeUpdate(query);

            if(contenu.getClass().getName().equals(name+"Musique")){
                Musique musique = (Musique) contenu;
                query = "UPDATE `info_team07_schema`.`Musique` "+
                        "SET `titre`='"+musique.getTitre()+"', `interprete`='"+ musique.getInterprete()+
                        "', `anneeCreation`='"+musique.getAnneeCreation()+"', `genreMusical`='"+musique.getGenreMusical()+
                        "', `duree` = '"+musique.getDuree()+"' WHERE idMusique = '"+id+"';";
            }else if(contenu.getClass().getName().equals(name+"Radio")){
                Radio radio = (Radio) contenu;
                query = "UPDATE `info_team07_schema`.`Radio` SET `nom` = '"+radio.getNom()+
                        "', `genreMusical`= '" +radio.getGenreMusical()+"' WHERE idRadio = '"+id+"';";
            }else if(contenu.getClass().getName().equals(name+"Podcast")){
                Podcast podcast = (Podcast) contenu;
                query = "UPDATE `info_team07_schema`.`Podcast` SET `titre` = '"+podcast.getTitre()+
                        "', `duree` = '"+podcast.getDuree()+"', `auteur` = '"+podcast.getAuteur()+
                        "', `categorie` = '"+podcast.getCategorie()+"' WHERE idPodcast = '"+id+"';";
            }
            this.statement.executeUpdate(query);
            // If there is no error.
            this.connection.commit();
            this.connection.setAutoCommit(true);
            return true;
        } catch(SQLException e){
            e.printStackTrace();
            try {
                // If there is any error.
                this.connection.rollback();
                this.connection.setAutoCommit(true);
            }catch(SQLException e2) {
                e2.printStackTrace();
            }
            return false;
        }

    }
    /**
     * Supprime un contenu sonore de la base de donnée
     * @param id du contenu à suprimer
     * @return true si réussi false sinon
     */
    public boolean deleteContenuSonore(int id){
        try{
            // Suffisant car on a ON DELETE CASCADE sur tous les autres ids
            String query = "DELETE FROM `info_team07_schema`.`ContenuSonore` " +
                    "WHERE idContenuSonore='"+id+"';";
            this.statement.executeUpdate(query);

            return true;
        } catch(SQLException e){
            e.printStackTrace();
            try {
                // If there is any error.
                this.connection.rollback();
            }catch(SQLException e2) {
                e2.printStackTrace();
            }
            return false;
        }

    }


    /**
     * Mets à jour dans la base de donnée les infos de lectures d'un contenu sonore
     * (que les infos du contenu sonore, pas celles plus precise de la musique, la radio ou le podcast)
     * @param contenu obet avec les infos mises à jour
     * @param id du contenu sonore à mettre à jour
     * @return true si réussi, false sinon
     */
    public boolean infoStatMAJContenuSonore(ContenuSonore contenu, int id){
        try{
            String query = "UPDATE `info_team07_schema`.`ContenuSonore` SET `fichierAudio`='"+contenu.getContenu()+"',"+
                    "`recommendationMoment` ='"+boolToInt(contenu.getRecommendationMoment())+"', `morceauxPopulaire`='"
                    +boolToInt(contenu.getMorceauPopulaire())+"',`nbLectureMois`='"+contenu.getNbLectureMois()+
                    "',`nbLectureTotal`='"+contenu.getNbLectureTotal()+"' WHERE (`idContenuSonore` = '"+id+"');";
            this.statement.executeQuery(query);
            return true;
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Renvoie un resultset de tous les résultat de la base de donnée correspondant à un type
     * de contenu sonore ou tous les contenus sonore
     * @param choix "musique", "radio" ou "podcast"
     * @param filtre filtre à rajouter si voulu, sinon mettre une chaine vide ""
     * @return le resultset de la query si réussi, null sinon
     */
    public ResultSet getAllBy(String choix, String filtre){
        try{
            String query;
            if(choix.equals("musique")) {
                query = "SELECT * FROM ContenuSonore,Musique WHERE idContenuSonore=idMusique"+filtre+";";
            }else if(choix.equals("radio")) {
                query = "SELECT * FROM ContenuSonore,Radio WHERE idContenuSonore=idRadio"+filtre+";";
            }else if(choix.equals("podcast")) {
                query = "SELECT * FROM ContenuSonore,Podcast WHERE idContenuSonore=idPodcast"+filtre+";";
            }else{
                System.out.println("L'entrée choix n'est pas valide");
                return null;
            }
            return this.statement.executeQuery(query);
        } catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Lire un resultset donné selon s'il s'agit de musiques, de radio, ou de podcast
     * @param choix "musique", "radio", "podcast"
     * @param res resultset à lire
     * @return la liste des contenus sonore du type choisi du resultset donné
     */
    public List<ContenuSonore> readResultset(String choix,ResultSet res){
        try {
            ContenuSonore temp;
            List<ContenuSonore> catalogue = new ArrayList<>();
            if (res != null) {
                if (choix.equals("musique")) {
                    while (res.next()) { //S'il y a un élément dans le résultat
                        temp = new Musique(res.getString("fichierAudio"), res.getBoolean("recommendationMoment"),
                                res.getString("titre"), res.getString("interprete"), res.getString("anneeCreation"),
                                genreMusical.valueOf(res.getString("genreMusical")), res.getInt("duree"));
                        temp.setId(res.getInt("idContenuSonore"));
                        catalogue.add(temp);
                    }
                }else if(choix.equals("radio")){
                    while (res.next()) { //S'il y a un élément dans le résultat
                        temp = new Radio(res.getString("fichierAudio"), res.getBoolean("recommendationMoment"),
                                res.getString("nom"),genreMusical.valueOf(res.getString("genreMusical")));
                        temp.setId(res.getInt("idContenuSonore"));
                        catalogue.add(temp);
                    }
                }else if(choix.equals("podcast")){
                    while (res.next()) { //S'il y a un élément dans le résultat
                        temp = new Podcast(res.getString("fichierAudio"),res.getBoolean("recommendationMoment"),
                                res.getString("titre"),res.getInt("duree"),res.getString("auteur"),
                                categorie.valueOf(res.getString("categorie")));
                        temp.setId(res.getInt("idContenuSonore"));
                        catalogue.add(temp);
                    }
                }else{
                    System.out.println("L'entrée choix n'est pas valide");
                }
            }
            return catalogue;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
}

    /**
     * Donne le catalogue complet en demandant toutes les musiques, radio et podcast sans aucun filtre
     * @return la liste des contenus sonores du catalogue
     */
    public List<ContenuSonore> getAllCatalogue(){
        List<ContenuSonore> resultat = this.readResultset("musique",this.getAllBy("musique",""));
        resultat.addAll(this.readResultset("radio",this.getAllBy("radio","")));
        resultat.addAll(this.readResultset("podcast",this.getAllBy("podcast","")));
        return resultat;

    }


    /**
     * Renvoie tous les contenus sonore dont le titre ou le nom contient la chaine rentrée
     * @param title chaine qui doit être dans le titre ou le nom
     * @return liste de tous les contenus sonores trouvés
     */
    public List<ContenuSonore> searchAllByTitle(String title){
        List<ContenuSonore> resultat = this.readResultset("musique",this.getAllBy("musique",
                " and titre like '%"+title+"%'"));
        resultat.addAll(this.readResultset("radio",this.getAllBy("radio",
                " and nom like '%"+title+"%'")));
        resultat.addAll(this.readResultset("podcast",this.getAllBy("podcast",
                " and titre like '%"+title+"%'")));
        return resultat;
    }

    /**
     * Renvoie tous les contenus sonore dont l'auteur ou l'interprete contient les caractères recherchés
     * @param auteur chaine qui doit être dansl'auteur ou l'interprete
     * @return liste de tous les contenus sonores trouvés
     */
    public List<ContenuSonore> searchByAutor(String auteur){
        List<ContenuSonore> resultat = this.readResultset("musique",this.getAllBy("musique",
                " and interprete like '%"+auteur+"%'"));
        resultat.addAll(this.readResultset("podcast",this.getAllBy("podcast",
                " and auteur like '%"+auteur+"%'")));
        return resultat;
    }
    /**
     * Renvoie tous les contenus sonore dont le genre musical correspond à celui recherché :
     * ne peuvent être que des musiques ou des radios
     * @param genre chaine qui doit être dans genreMusical
     * @return liste de tous les contenus sonores trouvés
     */
    public List<ContenuSonore> searchByGenreMusical(String genre){
        List<ContenuSonore> resultat = this.readResultset("musique",this.getAllBy("musique",
                " and genreMusical like '%"+genre+"%'"));
        resultat.addAll(this.readResultset("radio",this.getAllBy("radio",
                " and genreMusical like '%"+genre+"%'")));
        return resultat;
    }
    /**
     * Renvoie tous les contenus sonore dont le genre musical correspond à celui recherché :
     * ne peuvent être que des musiques ou des radios
     * @param categorie chaine qui doit être dans genreMusical
     * @return liste de tous les contenus sonores trouvés
     */
    public List<ContenuSonore> searchByCategorie(String categorie){
        return this.readResultset("podcast",this.getAllBy("podcast",
                " and categorie like '%"+categorie+"%'"));
    }
    /**
     * Renvoie les éléments du catalogue qui sont les recommendations du moment
     * @return liste des éléments du catalogue qui sont les recommendations du moment
     */
    public List<ContenuSonore> getRecommendationMoment(){
        List<ContenuSonore> resultat = this.readResultset("musique",this.getAllBy("musique",
                " and ContenuSonore.recommendationMoment=1"));
        resultat.addAll(this.readResultset("radio",this.getAllBy("radio",
                " and ContenuSonore.recommendationMoment=1")));
        resultat.addAll(this.readResultset("podcast",this.getAllBy("podcast",
                " and ContenuSonore.recommendationMoment=1")));
        return resultat;
    }

    /**
     * Renvoie les éléments du catalogue qui sont les recommendations du moment
     * @return liste des éléments du catalogue qui sont les recommendations du moment
     */
    public List<ContenuSonore> getMorceauxPopulaires(){
        List<ContenuSonore> resultat = this.readResultset("musique",this.getAllBy("musique",
                " and ContenuSonore.morceauxPopulaire=1"));
        resultat.addAll(this.readResultset("radio",this.getAllBy("radio",
                " and ContenuSonore.morceauxPopulaire=1")));
        resultat.addAll(this.readResultset("podcast",this.getAllBy("podcast",
                " and ContenuSonore.morceauxPopulaire=1")));
        return resultat;
    }

    public static void main(String[] args){
        //Tests
        CatalogueDatabase catDatabase = new CatalogueDatabase();

        /*
       Musique m = new Musique("nouveau", false, "qui suis je", "ou vais je"
       , "quand", genreMusical.baroque,3);
        Musique m1 = new Musique("pour le titre", false, "ravioli", "e"
                , "quand", genreMusical.baroque,10);
        Radio r = new Radio("ici lien",true,"Poiuyt",genreMusical.blues);
        Radio r1 = new Radio("lien",true,"hasard",genreMusical.classique);

        Podcast p = new Podcast("beaucuop",true,"rolala",30,"ergotique",categorie.histoire);
        Podcast p1 = new Podcast("pourl''auteur",false,"pasici",21,"oublie",categorie.histoire);
        Podcast p2 = new Podcast("pour cat",false,"pasici",21,"oublie",categorie.divertissement);


      catDatabase.resetCatalogue();

       catDatabase.createContenuSonore(m);
        catDatabase.createContenuSonore(m1);
        catDatabase.createContenuSonore(r);
        catDatabase.createContenuSonore(r1);
        catDatabase.createContenuSonore(p);
       catDatabase.createContenuSonore(p1);
        catDatabase.createContenuSonore(p2);*/


       //liste.get(1).setContenu("");
       //catDatabase.updateContenuSonore(liste.get(1),liste.get(1).getId());

      // catDatabase.deleteContenuSonore(liste.get(0), liste.get(0).getId());

     List<ContenuSonore> liste = catDatabase.getAllCatalogue();
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
        liste = catDatabase.searchAllByTitle("ra");
        for(int i=0;i<liste.size();i++){
            System.out.print(liste.get(i).getContenu() + "--");
        }
        System.out.println("autor");

        liste = catDatabase.searchByAutor("ou");
        for(int i=0;i<liste.size();i++){
            System.out.print(liste.get(i).getContenu() + "--");
        }
        System.out.println("genremus");

        liste = catDatabase.searchByGenreMusical("las");
        for(int i=0;i<liste.size();i++){
            System.out.print(liste.get(i).getContenu() + "--");
        }
        System.out.println("categorie");

        liste = catDatabase.searchByCategorie("diver");

        for(int i=0;i<liste.size();i++){
            System.out.print(liste.get(i).getContenu() + "--");
        }
        System.out.println("");

    }

}
