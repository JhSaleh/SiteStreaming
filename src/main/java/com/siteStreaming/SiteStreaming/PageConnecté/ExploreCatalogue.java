package com.siteStreaming.SiteStreaming.PageConnecté;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siteStreaming.SiteStreaming.Acceuil.CompteClient;
import com.siteStreaming.SiteStreaming.Catalogue.Playlist;
import com.siteStreaming.SiteStreaming.DataBase.ClientDatabase;
import com.siteStreaming.SiteStreaming.DataBase.DBManager;
import com.siteStreaming.SiteStreaming.DataBase.PlaylistDatabase;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import java.io.IOException;
import java.util.List;

public class ExploreCatalogue extends HttpServlet {
    private void doProcess(HttpServletRequest request, HttpServletResponse response){
        try {
        //Cas où le formulaire a été envoyé
        //request.getParameter("mailAddress")
        String mail = "aarobase@mail";
        if(mail != null){
            System.out.println("Connection en cours. Preparation servlet.");
            //ClientDatabase clientDatabase = new ClientDatabase();
            //String infoClient[] = clientDatabase.getAllClientInformation(mail);

            //request.setAttribute("infoClient", infoClient); //transmet l'info à d'autre servlet et la page html
            //request.setAttribute("signedInSent", true);
            PlaylistDatabase playlistDatabase = new PlaylistDatabase();
            List<Playlist> mesplaylists = playlistDatabase.getAllPlaylist(mail);

            String playlists;
            ObjectMapper mapper = new ObjectMapper();

            playlists = mapper.writeValueAsString(mesplaylists);
            System.out.println(playlists);
            request.setAttribute("mesplaylists",playlists);
            playlistDatabase.close();

        }

        //Redirige vers la page d'acceuil
        String pageName = "/restrictAdmin/exploreCat.jsp";
        RequestDispatcher rd = getServletContext().getRequestDispatcher(pageName);

        rd.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        doProcess(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        doProcess(req, resp);
    }
}


