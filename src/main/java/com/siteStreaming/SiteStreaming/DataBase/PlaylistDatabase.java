package com.siteStreaming.SiteStreaming.DataBase;

import com.siteStreaming.SiteStreaming.Accueil.CompteClient;
import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Enumérations.genreMusical;
import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Musique;
import com.siteStreaming.SiteStreaming.Catalogue.Playlist;
import com.siteStreaming.SiteStreaming.LoggerSite;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui sert à la gestion des playlists dans la base de données, et à la récupération
 * d'informations qui en viennent.
 */
public class PlaylistDatabase {
    public Connection connection;
    public Statement statement;

    /**
     * Crée une connection avec la base de données
     */
    public PlaylistDatabase() {
        try {
            this.connection = DBManager.getInstance().getConnection();
            this.statement = this.connection.createStatement();
        } catch (SQLException e) {
            LoggerSite.logger.error(e);
        }
    }

    /**
     * A utiliser si on supprime toutes les playlists (vide aussi leur contenu)
     */
    public void resetPlaylist() {
        try {
            this.statement.executeUpdate("DELETE FROM Playlist;");
            //mettre dernière version
        } catch (SQLException e) {
            LoggerSite.logger.error(e);
        }

    }

    /**
     * A utiliser pour vider les contenus des playlists
     */
    public void resetContenuPlaylist() {
        try {
            this.statement.executeUpdate("DELETE FROM ContenuPlaylist;");
            //mettre dernière version
        } catch (SQLException e) {
            LoggerSite.logger.error(e);
        }
    }

    /**
     * Creer une playlist et met la date de création quelque soit celle de l'objet playlist
     * à l'année en cours
     *
     * @param playlist à créer dans la base de données
     * @return true si réussi, false sinon
     */
    public boolean createPlaylist(Playlist playlist) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = this.connection.prepareStatement(
                    "INSERT INTO Playlist (idCompteClient, titre, dureeTotale, anneeCreation) VALUES (?,?,?,CURRENT_DATE)");
            preparedStatement.setInt(1, playlist.getIdCompteClient());
            preparedStatement.setString(2, playlist.getTitre());
            preparedStatement.setInt(3, playlist.getDureeTotale());
            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            LoggerSite.logger.error(e);
            return false;
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                LoggerSite.logger.error(e);
            }
        }
    }

    /**
     * Renommer une playlist
     *
     * @param playlist avec le nouveau nom
     * @return true si réussi, false sinon
     */
    public boolean renamePlaylist(Playlist playlist) {
        PreparedStatement preparedStatement = null;
        try {
            if (playlist.getIdPlaylist() < 0) {
                LoggerSite.logger.error("pas d'id pour cette playlist !");
                return false;
            } else {
                preparedStatement = this.connection.prepareStatement("UPDATE Playlist SET titre=? " +
                        "where idPlaylist = ?;");
                preparedStatement.setString(1, playlist.getTitre());
                preparedStatement.setInt(2, playlist.getIdPlaylist());
                preparedStatement.executeUpdate();

                return true;
            }
        } catch (SQLException e) {
            LoggerSite.logger.error(e);
            return false;
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                LoggerSite.logger.error(e);
            }
        }
    }

    /**
     * Mettre à jour la durée d'une playlist
     *
     * @param playlist avec la nouvelle durée
     * @return true si réussi, false sinon
     */
    public boolean majDureePlaylist(Playlist playlist) {
        PreparedStatement preparedStatement = null;
        try {
            if (playlist.getIdPlaylist() < 0) {
                LoggerSite.logger.error("pas d'id pour cette playlist !");
                return false;
            } else {
                preparedStatement = this.connection.prepareStatement("UPDATE Playlist SET dureeTotale=? " +
                        "where idPlaylist=?;");
                preparedStatement.setInt(1, playlist.getDureeTotale());
                preparedStatement.setInt(2, playlist.getIdPlaylist());
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            LoggerSite.logger.error(e);
            return false;
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                LoggerSite.logger.error(e);
            }
        }
    }

    /**
     * Supprimer une playlist de la base de données -- le supprime aussi de ContenuPlaylist avec ON DELETE CASCADE
     *
     * @param playlist avec le nouveau nom
     * @return true si réussi, false sinon
     */
    public boolean deletePlaylist(Playlist playlist) {
        try {
            if (playlist.getIdPlaylist() < 0) {
                LoggerSite.logger.error("pas d'id pour cette playlist !");
                return false;
            } else {
                String query = "DELETE FROM Playlist" +
                        " WHERE idPlaylist=" + playlist.getIdPlaylist() + ";";

                this.statement.executeUpdate(query);
                return true;
            }
        } catch (SQLException e) {
            LoggerSite.logger.error(e);
            return false;
        }
    }

    /**
     * Ajoute une musique à la playlist
     * par défaut les musiques sont ajoutées à la fin de la playlist
     *
     * @param playlist à laquelle on ajoute une musique
     * @param musique  à ajouter
     * @return true si réussi, false sinon
     */
    public boolean addMusiquetoPlaylist(Playlist playlist, Musique musique) {
        PreparedStatement preparedStatement = null;
        try {
            if (playlist.getIdPlaylist() < 0 || musique.getId()<0) {
                LoggerSite.logger.error("pas d'id pour cette playlist ou cette musique!");
                return false;
            } else {
                if (playlist.ajouterElement(musique)) {
                    int position = 1;

                    /* On récupère la position où l'on doit insérer la musique */
                    String query = "select max(position) from ContenuPlaylist where idPlaylist=" + playlist.getIdPlaylist() + ";";
                    ResultSet res = this.statement.executeQuery(query);
                    if (res.next()) {
                        position = res.getInt("max(position)") + 1;
                    }
                    LoggerSite.logger.debug("ajout de mus "+musique.getId()+" dans :"+playlist.getIdPlaylist()+" à la position:"+position);

                    res.close();

                    preparedStatement = this.connection.prepareStatement("INSERT INTO ContenuPlaylist " +
                            "(idMusique, idPlaylist, position) VALUES (?,?,?);");
                    preparedStatement.setInt(1, musique.getId());
                    preparedStatement.setInt(2, playlist.getIdPlaylist());
                    preparedStatement.setInt(3, position);

                    preparedStatement.executeUpdate();
                    //la duree de la playlist a été augmenter dans ajouter element, il faut la changer dans la base de donnée aussi
                    return this.majDureePlaylist(playlist);
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            LoggerSite.logger.error(e);
            return false;
        }
    }

    /**
     * Restocke toute la playlist (en cas de déplacement ou de suppression d'une musique, ce qui se fait avec la classe playlist)
     * à appeler après déplacement ou suppression de contenu !!
     *
     * @param playlist à laquelle on ajoute une musique
     * @return true si réussi, false sinon
     */
    public boolean enregistrerContenuPlaylist(Playlist playlist) {
        try {
            if (playlist.getIdPlaylist() < 0) {
                LoggerSite.logger.error("pas d'id pour cette playlist !");
                return false;
            } else {
                /* On supprime la playlist de la liste pour la réenregistrer totalement */
                String query = "DELETE FROM ContenuPlaylist  WHERE idPlaylist = " + playlist.getIdPlaylist() + ";";
                this.statement.executeUpdate(query);


                boolean res = true;
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
            LoggerSite.logger.error(e);
            return false;
        }
    }

    /**
     * Récupère une musique depuis la base de données à partir de son id (sert dans la lecture des playlist depuis la base de données)
     *
     * @param idMusique de la musique à récupérer
     * @return la musique dans la base de données correspondante
     */
    public Musique getMusique(int idMusique) {
        try {
            String query = "SELECT * FROM ContenuSonore,Musique where idContenuSonore=idMusique and " +
                    "idMusique =" + idMusique + ";";
            ResultSet res = this.statement.executeQuery(query);
            if (res.next()) {
                Musique m = new Musique(res.getString("fichierAudio"), res.getBoolean("recommendationMoment"),
                        res.getString("titre"), res.getString("interprete"), res.getString("anneeCreation"),
                        genreMusical.valueOf(res.getString("genreMusical")), res.getInt("duree"));
                m.setId(res.getInt("idMusique"));
                m.setNbLectureTotal(res.getInt("nbLectureTotal"));
                m.setNbLectureMois(res.getInt("nbLectureMois"));
                m.setRecommendationMoment(res.getBoolean("recommendationMoment"));
                res.close();
                return m;
            }
            return null;
        } catch (SQLException e) {
            LoggerSite.logger.error(e);
            return null;
        }
    }

    /**
     * Récupère l'id du client à partir de son mail
     *
     * @param mail du client dont on veut l'id
     * @return l'id du client
     */
    public int getIdClient(String mail) {
        PreparedStatement preparedStatement = null;
        ResultSet res = null;
        try {
            preparedStatement = this.connection.prepareStatement("SELECT idCompteClient FROM CompteClient where adresseMailClient =?;");
            preparedStatement.setString(1, mail);
            res = preparedStatement.executeQuery();
            if (res.next()) {
                return res.getInt("idCompteClient");
            }
            res.close();
            return -1;
        } catch (SQLException e) {
            LoggerSite.logger.error(e);
            return -1;
        } finally {
            try {
                preparedStatement.close();
                res.close();
            } catch (SQLException e) {
                LoggerSite.logger.error(e);
            }
        }
    }

    /**
     * Récupère la liste des playlist d'un client
     *
     * @param mail du client dont on récupère les playlists
     * @return la liste des playlists si réussi, null sinon
     */
    public List<Playlist> getAllPlaylist(String mail) {
        try {
            int idClient = -1;
            /* On récupère l'id du client */
            idClient = this.getIdClient(mail);

            if (idClient == -1) {
                System.out.println("ce client n'a pas été trouvé dans la base de donnée");
                return null;
            } else {
                /* On récupère les infos des playlists du client */
                String query = "SELECT * FROM Playlist where idCompteClient=" + idClient + ";";
                ResultSet res = this.statement.executeQuery(query);

                Playlist temp;
                List<Playlist> playlists = new ArrayList<>();
                while (res.next()) {
                    temp = new Playlist(mail, res.getString("titre"), res.getInt("dureeTotale"),
                            res.getString("anneeCreation"));
                    temp.setIdPlaylist(res.getInt("idPlaylist"));
                    playlists.add(temp);

                }
                res.close();

                /* On récupère les musiques des playlists récupérée */
                List<Musique> tempMus = new ArrayList<>();
                LoggerSite.logger.debug("nombre de playlist : " + playlists.size());
                LoggerSite.logger.debug(playlists);
                for (int i = 0; i < playlists.size(); i++) {
                    playlists.get(i).setMusique(getListMusique(playlists.get(i).getIdPlaylist()));
                }

                /* On renvoie le résultat */
                return playlists;
            }
        } catch (SQLException e) {
            LoggerSite.logger.error(e);
            return null;
        }
    }

    /**
     * Récupère la liste des musiques de la playlist
     *
     * @param idPlaylist dont on récupère les musiques
     * @return la liste de musique
     */
    public List<Musique> getListMusique(int idPlaylist) {
        PreparedStatement preparedStatement = null;
        try {
            if (idPlaylist == -1) {
                LoggerSite.logger.error("la playlist n'a pas d'identifiant");
                return null;
            } else {
                /* On récupère les musiques de la playlist */
                List<Musique> tempMus = new ArrayList<>();
                List<Integer> positions = new ArrayList<>();
                preparedStatement = this.connection.prepareStatement("SELECT * FROM ContenuPlaylist where idPlaylist=" +
                        idPlaylist + ";");
                int pos;
                ResultSet res2 = preparedStatement.executeQuery();
                while (res2.next()) {
                    pos = res2.getInt("position");
                    positions.add(pos - 1);
                    tempMus.add(this.getMusique(res2.getInt("idMusique")));
                }
                res2.close();
                return trieMusiques(tempMus,positions);
            }
        } catch (SQLException e) {
            LoggerSite.logger.error(e);
            return null;
        }
    }

    /**
     * Trie la liste des musiques avec une liste d'entiers qui correpondent aux positions souhaitées
     * de chaque musique, la liste de musiques et de positions correspondant index par index.
     * Utile dans le cas où une des musiques de la playlist a été supprimée et qu'il y a un trou
     * dans les numéros des positions de la base de données.
     *
     * @param musiques  liste des musiques à trier
     * @param positions liste des positions des musiques
     * @return la liste des musiques dans le bon ordre
     */
    public List<Musique> trieMusiques(List<Musique> musiques, List<Integer> positions) {
        List<Musique> res = new ArrayList<>();
        List<Integer> copie = new ArrayList<>();
        copie.addAll(positions);
        int parcours;
        int min;
        for (parcours = 0; parcours < musiques.size(); parcours++) {
            if (copie.size() > 0) {
                min = copie.get(0);
                for (int j = 0; j < copie.size(); j++) {
                    if (copie.get(j) < min) {
                        min = copie.get(j);
                    }
                }
                res.add(musiques.get(positions.indexOf(min)));
                copie.remove(Integer.valueOf(min));
            }
        }
        return res;
    }

    /**
     * Récupère la playlist associée à l'identifiant donné
     *
     * @param idPlaylist de la playlist recherchée
     * @return la playlist voulue
     */
    public Playlist getPlaylistById(int idPlaylist, String mail) {
        try {
            if (idPlaylist == -1) {
                LoggerSite.logger.error("la playlist n'a pas d'identifiant");
                return null;
            } else {
                /* On récupère les infos des playlists du client */
                String query = "SELECT * FROM Playlist where idPlaylist=" + idPlaylist + " LIMIT 1;";
                ResultSet res = this.statement.executeQuery(query);

                Playlist temp = null;
                if (res.next()) {
                    temp = new Playlist(mail, res.getString("titre"), res.getInt("dureeTotale"),
                            res.getString("anneeCreation"));
                    temp.setIdPlaylist(res.getInt("idPlaylist"));
                }
                res.close();

                /* On récupère les musiques de la playlist */
                temp.setMusique(getListMusique(idPlaylist));

                /* On renvoie le résultat */
                return temp;
            }
        } catch (SQLException e) {
            LoggerSite.logger.error(e);
            return null;
        }
    }

    public void close() {
        try {
            this.statement.close();
            this.connection.close();
        } catch (SQLException e) {
            LoggerSite.logger.error(e);
        }
    }

    public static void main(String[] args) {

        //Tests

        PlaylistDatabase playlistDatabase = new PlaylistDatabase();

        //playlistDatabase.resetContenuPlaylist();

         CompteClient c = new CompteClient("Moulinex","lopmiur","M","aarobase@mail2","vgtbhynju","12/12/12","12 prepre LPOP 70345", "Boys");
        // c.addToDatabase(c);

       //  Playlist p = new Playlist(c.getMail(),"mesmusiquesperso",0,"9090");
        // playlistDatabase.createPlaylist(p);

      /*  Playlist p1 = new Playlist(c,"copie",234,"1252");

        playlistDatabase.createPlaylist(p1);



        CatalogueDatabase catDatabase = new CatalogueDatabase();
        catDatabase.createContenuSonore(m);
        catDatabase.createContenuSonore(m1);

        CatalogueDatabase catDatabase = new CatalogueDatabase();
        List<ContenuSonore> resultat = catDatabase.getTypeCatalogue("musique");

*/
        List<Playlist> mesplaylists = playlistDatabase.getAllPlaylist(c.getMail());

        System.out.println(mesplaylists.get(0).toJson());

     /*  Musique m = new Musique("newMus",false,"Musique douce",
        "elizabeth","02/09/1000",genreMusical.pop,40);
        CatalogueDatabase catalogueDatabase = new CatalogueDatabase();
        catalogueDatabase.createContenuSonore(m);
        catalogueDatabase.close();*/

        System.out.println(playlistDatabase.addMusiquetoPlaylist(mesplaylists.get(0), playlistDatabase.getMusique(88)));
        //mesplaylists.get(0).ajouterElement(m);
        playlistDatabase.deletePlaylist(mesplaylists.get(1));

        mesplaylists = playlistDatabase.getAllPlaylist(c.getMail());
        System.out.println(mesplaylists.get(0).toJson());

playlistDatabase.close();
/*
        mesplaylists.get(0).setTitre("je me change");
        playlistDatabase.renamePlaylist(mesplaylists.get(1));

        Playlist modifié = playlistDatabase.getPlaylistById(mesplaylists.get(1),c.getMail());
        System.out.println(modifié.getTitre()+modifié.getDureeTotale());
        for(int i = 0; i<mesplaylists.size(); i++){
            System.out.println(mesplaylists.get(i).getTitre()+mesplaylists.get(i).getDureeTotale());
        }*/

    }
}
