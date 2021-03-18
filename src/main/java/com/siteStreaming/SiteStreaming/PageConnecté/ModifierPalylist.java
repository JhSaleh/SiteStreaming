package com.siteStreaming.SiteStreaming.PageConnecté;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siteStreaming.SiteStreaming.Accueil.CompteClient;
import com.siteStreaming.SiteStreaming.Catalogue.Playlist;
import com.siteStreaming.SiteStreaming.DataBase.PlaylistDatabase;
import com.siteStreaming.SiteStreaming.LoggerSite;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Servlet pour la modification des playlist pour le client
 */
public class ModifierPalylist  extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // on recupere les infos du client
            HttpSession session = request.getSession();
            CompteClient client = (CompteClient) session.getAttribute("sessionUtilisateur");
            String mail = client.getMail();
            String genre = client.getStyleMusique();
            if (mail != null) {
                PlaylistDatabase playlistDatabase = new PlaylistDatabase();
                int idPlay;
                String titre;
                //lecture de contenu sonore
                if (request.getParameter("NomPlaylist") != null
                        && !request.getParameter("NomPlaylist").equals("")) {
                    //on récupère les paramètres
                    titre = request.getParameter("NomPlaylist");
                    Playlist playlist = new Playlist(mail, titre, 0, "");
                    playlistDatabase.createPlaylist(playlist);
                }

                //Supprimer une playlist
                if (request.getParameter("numPlaylistSupp") != null) {
                    idPlay = Integer.parseInt(request.getParameter("numPlaylistSupp"));
                    playlistDatabase.deletePlaylist(playlistDatabase.getPlaylistById(idPlay, mail));
                }
                //Renommer une playlist
                if (request.getParameter("numPlaylistRenom") != null &&
                        request.getParameter("nouvNom") != null && !request.getParameter("numPlaylistRenom").equals("") &&
                        !request.getParameter("nouvNom").equals("")) {
                    idPlay = Integer.parseInt(request.getParameter("numPlaylistRenom"));
                    titre = request.getParameter("nouvNom");
                    Playlist playlist = playlistDatabase.getPlaylistById(idPlay, mail);
                    playlist.setTitre(titre);
                    playlistDatabase.renamePlaylist(playlist);
                }

                //donne les playlists à la page
                List<Playlist> mesplaylists = playlistDatabase.getAllPlaylist(mail);
                String playlists;
                ObjectMapper mapper = new ObjectMapper();
                playlists = mapper.writeValueAsString(mesplaylists);
                request.setAttribute("mesplaylists", playlists);
                playlistDatabase.close();
            }

            //Redirige vers la page d'acceuil
            String pageName = "/WEB-INF/client/modifierPlaylist.jsp";
            RequestDispatcher rd = getServletContext().getRequestDispatcher(pageName);

            rd.forward(request, response);
        } catch (ServletException e) {
            LoggerSite.logger.error(e);
        } catch (IOException e) {
            LoggerSite.logger.error(e);
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        doPost(request, response);
    }
}