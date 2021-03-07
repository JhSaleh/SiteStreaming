package com.siteStreaming.SiteStreaming.DataBase;

import com.siteStreaming.SiteStreaming.Acceuil.CompteClient;
import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.ContenuSonore;
import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Enumérations.genreMusical;
import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Musique;
import com.siteStreaming.SiteStreaming.Catalogue.Playlist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDatabase {
    public Connection connection;
    public Statement statement;

    /**
     * Crée une connection avec la base de données
     */
    public PlaylistDatabase(){
        try{
            this.connection = DBManager.getInstance().getConnection();
            this.statement = this.connection.createStatement();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * A utilisé si on supprime toutes les playlists (vide aussi leur contenu)
     */
    public void resetPlaylist(){
        try {
            this.statement.executeUpdate("DELETE FROM Playlist;");
            //mettre dernière version
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    /**
     * A utiliser pour vider les contenus des playlists
     */
    public void resetContenuPlaylist(){
        try {
            this.statement.executeUpdate("DELETE FROM ContenuPlaylist;");
            //mettre dernière version
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Creer une playlist et mets la date de création quelque soit celle de l'objet playlist
     * à l'année en cours
     * @param playlist à créer dans la base de donnée
     * @return true si réussi, false sinon
     */
    public boolean createPlaylist(Playlist playlist) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = this.connection.prepareStatement(
                    "INSERT INTO `info_team07_schema`.`Playlist` (`idCompteClient`, `titre`, `dureeTotale`, `anneeCreation`) VALUES (?,?,?,EXTRACT(YEAR FROM CURRENT_DATE))");
            preparedStatement.setInt(1, playlist.getIdCompteClient());
            preparedStatement.setString(2, playlist.getTitre());
            preparedStatement.setInt(3, playlist.getDureeTotale());
            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
     * Renommer une playlist
     * @param playlist avec le nouveau nom
     * @return true si réussi, false sinon
     */
    public boolean renamePlaylist(Playlist playlist){
        PreparedStatement preparedStatement = null;
        try{
            if(playlist.getIdPlaylist()<0){
                System.out.print("pas d'id pour cette playlist !");
                return false;
            }else {
                preparedStatement = this.connection.prepareStatement("UPDATE `info_team07_schema`.`Playlist` SET `titre`=? " +
                                "where idPlaylist = ?;");
                preparedStatement.setString(1,playlist.getTitre());
                preparedStatement.setInt(2,playlist.getIdPlaylist());
                preparedStatement.executeUpdate();

                return true;
            }
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        }
      finally {
        try {
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    }

    /**
     * Mettre à jour la durée d'une playlist
     * @param playlist avec la nouvelle durée
     * @return true si réussi, false sinon
     */
    public boolean majDureePlaylist(Playlist playlist) {
        PreparedStatement preparedStatement = null;
        try {
            if(playlist.getIdPlaylist()<0){
                System.out.print("pas d'id pour cette playlist !");
                return false;
            }else {
                preparedStatement = this.connection.prepareStatement("UPDATE `info_team07_schema`.`Playlist` SET `dureeTotale`=? " +
                                "where idPlaylist=?;");
                preparedStatement.setInt(1,playlist.getDureeTotale());
                preparedStatement.setInt(2,playlist.getIdPlaylist());
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            try {
                preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * Supprimer une playlist de la base de donnée -- le supprime aussi de ContenuPlaylist avec ON DELETE CASCADE
     * @param playlist avec le nouveau nom
     * @return true si réussi, false sinon
     */
    public boolean deletePlaylist(Playlist playlist) {
        try {
            if(playlist.getIdPlaylist()<0){
                System.out.print("pas d'id pour cette playlist !");
                return false;
            }else {
                String query = "DELETE FROM `info_team07_schema`.`Playlist`" +
                        "WHERE idPlaylist='" + playlist.getIdPlaylist() + "';";

                this.statement.executeUpdate(query);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Ajoute une musique à la playlist
     * par défaut les musiques sont ajoutées à la fin de la playlist
     * !! dans la base de donné la liste commence à 1 pour ne pas confondre avec le 0 de retourné
     * s'il n'y a pas encore de musiquue dans la playlist !!
     * @param playlist à laquelle on ajoute une musique
     * @param musique à ajouter
     * @return true si réussi, false sinon
     */
    public boolean addMusiquetoPlaylist(Playlist playlist, Musique musique){
        PreparedStatement preparedStatement = null;
        try {
            if(playlist.getIdPlaylist()<0){
                System.out.print("pas d'id pour cette playlist !");
                return false;
            }else {
                if(playlist.ajouterElement(musique)) {
                    int position = 0;

                    /* On récupère la position où l'on doit insérer la musique */
                    String query = "select max(position) from ContenuPlaylist where idPlaylist='"+playlist.getIdPlaylist()+"';";
                    ResultSet res = this.statement.executeQuery(query);
                    if(res.next()){
                        position=res.getInt("max(position)")+1;
                        //System.out.println("here-- "+position+"--"+musique.getId()+"--"+playlist.getIdPlaylist());
                    }
                    res.close();

                   preparedStatement = this.connection.prepareStatement("INSERT INTO `info_team07_schema`.`ContenuPlaylist` " +
                           "(`idMusique`, `idPlaylist`, `position`) VALUES (?,?,?);");
                   preparedStatement.setInt(1,musique.getId());
                    preparedStatement.setInt(2,playlist.getIdPlaylist());
                    preparedStatement.setInt(3,position);

                    preparedStatement.executeUpdate();
                    //la duree de la playlist a été augmenter dans ajouter element, il faut la changer dans la base de donnée aussi
                    return  this.majDureePlaylist(playlist);
                }else{
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            try {
                preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * Restocke toute la playlist (en cas de déplacement ou de suppression d'une musique, ce qui se fait avec la classe playlist)
     * à appeler après déplacement ou suppression de contenu !!
     * @param playlist à laquelle on ajoute une musique
     * @return true si réussi, false sinon
     */
    public boolean enregistrerContenuPlaylist(Playlist playlist){
        try {
            if(playlist.getIdPlaylist()<0){
                System.out.print("pas d'id pour cette playlist !");
                return false;
            }else {
                /* On supprime la playlist de la liste pour la réenregistrer totalement */
                String query = "DELETE FROM `info_team07_schema`.`ContenuPlaylist`  WHERE idPlaylist = '" + playlist.getIdPlaylist() + "';";
                this.statement.executeUpdate(query);



               boolean res = true;
               // System.out.println("lignes supprimées : "+nb);
                /* On réajoute une à une toutes les musiques de la playlist */
                for (int i = 0; i < playlist.getMusique().size(); i++) {
                    res = this.addMusiquetoPlaylist(playlist, playlist.getMusique().get(i));
                    /* On échout si une musique échout à être enregistrée */
                    if (res == false) {
                        return false;
                    }
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Récupère une musique depuis la base de donnée à partir de son id (sert dans la lecture des playlist depuis la base de donnée)
     * @param idMusique de la musique à récupérer
     * @return la musique dans la base de donnée correspondante
     */
    public Musique getMusique(int idMusique){
        try{
            String query= "SELECT * FROM ContenuSonore,Musique where idContenuSonore=idMusique and " +
                    "idMusique ='"+idMusique+"';";
            ResultSet res = this.statement.executeQuery(query);
            if(res.next()){
                Musique m = new Musique(res.getString("fichierAudio"), res.getBoolean("recommendationMoment"),
                        res.getString("titre"), res.getString("interprete"), res.getString("anneeCreation"),
                        genreMusical.valueOf(res.getString("genreMusical")), res.getInt("duree"));
                m.setId(res.getInt("idMusique"));
                res.close();
                return m;
            }
            return null;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Récupère l'id du client à partir de son compte
     * @param client compte du client dont on veut l'id
     * @return l'id du client
     */
    public int getIdClient(CompteClient client){
        PreparedStatement preparedStatement = null;
        ResultSet res= null;
        try {
          preparedStatement = this.connection.prepareStatement("SELECT idCompteClient FROM CompteClient where adresseMailClient =?;");
           preparedStatement.setString(1,client.getMail() );
           res = preparedStatement.executeQuery();
            if (res.next()) {
                return res.getInt("idCompteClient");
            }
            res.close();
            return -1;
        }catch(SQLException e){
            return -1;
        }
        finally {
            try {
                preparedStatement.close();
                res.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    /**
     * Récupère la liste des playlist d'un client
     * @param client dont on récupère les playlist
     * @return la liste des playlists si réussi, null sinon
     */
    public List<Playlist> getAllPlaylist(CompteClient client){
        try {
            int idClient=-1;
            /* On récupère l'id du client */
            idClient = this.getIdClient(client);

            if(idClient==-1){
                System.out.println("ce client n'a pas été trouvé dans la base de donnée");
                return null;
            }else {
                /* On récupère les infos des playlists du client */
                String query = "SELECT * FROM Playlist where idCompteClient='" + idClient + "';";
                ResultSet res = this.statement.executeQuery(query);

                Playlist temp;
                List<Playlist> playlists = new ArrayList<>();
                while (res.next()) {
                    temp = new Playlist(client, res.getString("titre"), res.getInt("dureeTotale"),
                            res.getString("anneeCreation"));
                    temp.setIdPlaylist(res.getInt("idPlaylist"));
                    playlists.add(temp);

                }
                res.close();

                /* On récupère les musiques des playlists récupérée */
                List<Musique> tempMus = new ArrayList<>();
                for (int i = 0; i < playlists.size(); i++) {
                    playlists.get(i).setMusique(getListMusique(playlists.get(i)));
                }

                /* On renvoie le résultat */
                return playlists;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * Récupère la liste des musiques de la playlist
     * @param playlist dont on récupère les musiques
     * @return la liste de musique
     */
    public List<Musique> getListMusique(Playlist playlist) {
        PreparedStatement preparedStatement =null;
        try {
            if (playlist.getIdPlaylist()== -1) {
                System.out.println("la playlist n'a pas d'identifiant");
                return null;
            } else {
               /* On récupère les musiques de la playlist */
                List<Musique> tempMus = new ArrayList<>();
                preparedStatement =this.connection.prepareStatement("SELECT * FROM info_team07_schema.ContenuPlaylist where idPlaylist='" +
                        playlist.getIdPlaylist() + "';");
                int pos;
                ResultSet res2 = preparedStatement.executeQuery();
                while (res2.next()) {
                    pos = res2.getInt("position");
                    tempMus.add(pos - 1, this.getMusique(res2.getInt("idMusique")));
                }
                res2.close();
                return  tempMus;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * Récupère la playlist associée à l'identifiant donné
     * @param playlist dont on récupère l'identifiant
     * @return la playlist voulue
     */
    public Playlist getPlaylistById(Playlist playlist, CompteClient client) {
        try {
            if (playlist.getIdPlaylist()== -1) {
                System.out.println("la playlist n'a pas d'identifiant");
                return null;
            } else {
                /* On récupère les infos des playlists du client */
                String query = "SELECT * FROM Playlist where idPlaylist='" + playlist.getIdPlaylist() + "' LIMIT 1;";
                ResultSet res = this.statement.executeQuery(query);

                Playlist temp = null;
                if (res.next()) {
                    temp = new Playlist(client, res.getString("titre"), res.getInt("dureeTotale"),
                            res.getString("anneeCreation"));
                    temp.setIdPlaylist(res.getInt("idPlaylist"));
                }
                res.close();

                /* On récupère les musiques de la playlist */
                temp.setMusique(getListMusique(playlist));

                /* On renvoie le résultat */
                return temp;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



    public static void main(String[] args){
        //Tests

        PlaylistDatabase playlistDatabase = new PlaylistDatabase();

        //playlistDatabase.resetContenuPlaylist();

        CompteClient c = new CompteClient("Moulinex","lopmiur","M","aarobase@mail","vgtbhynju","12/12/12","12 prepre LPOP 70345");
    /*    c.addToDatabase(c);

        Playlist p = new Playlist(c,"mesmusiquesperso",0,"9090");
        Playlist p1 = new Playlist(c,"copie",234,"1252");

        playlistDatabase.createPlaylist(p);
        playlistDatabase.createPlaylist(p1);


        Musique m = new Musique("linktothemusic", false, "Melodie du soir", "joueur de piano"
                , "2 mars 4000", genreMusical.romantique,100);
        Musique m1 = new Musique("freemusic.com", false, "Lullaby", "orchestre le grand"
                , "09/12/21", genreMusical.classique,9000);

        CatalogueDatabase catDatabase = new CatalogueDatabase();
        catDatabase.createContenuSonore(m);
        catDatabase.createContenuSonore(m1);
*/
       CatalogueDatabase catDatabase = new CatalogueDatabase();
        List<ContenuSonore> resultat = catDatabase.getTypeCatalogue("musique");


        List<Playlist> mesplaylists = playlistDatabase.getAllPlaylist(c);

Musique tp= null;
        for(int i=0;i<resultat.size();i++) {
            tp = (Musique) resultat.get(i);

            mesplaylists.get(1).ajouterElement(tp);
        }
        playlistDatabase.enregistrerContenuPlaylist(mesplaylists.get(1));

        mesplaylists.get(0).setTitre("je me change");
        playlistDatabase.renamePlaylist(mesplaylists.get(1));

        Playlist modifié = playlistDatabase.getPlaylistById(mesplaylists.get(1),c);
        System.out.println(modifié.getTitre()+modifié.getDureeTotale());
        for(int i = 0; i<mesplaylists.size(); i++){
            System.out.println(mesplaylists.get(i).getTitre()+mesplaylists.get(i).getDureeTotale());
        }

    }
}
