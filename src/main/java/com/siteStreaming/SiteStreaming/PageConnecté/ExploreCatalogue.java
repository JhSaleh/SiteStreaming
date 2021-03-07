package com.siteStreaming.SiteStreaming.PageConnecté;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siteStreaming.SiteStreaming.Acceuil.CompteClient;
import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.ContenuSonore;
import com.siteStreaming.SiteStreaming.Catalogue.Playlist;
import com.siteStreaming.SiteStreaming.DataBase.CatalogueDatabase;
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
            if(mail != null) {
                PlaylistDatabase playlistDatabase = new PlaylistDatabase();
                List<Playlist> mesplaylists = playlistDatabase.getAllPlaylist(mail);

                String playlists;
                ObjectMapper mapper = new ObjectMapper();

                playlists = mapper.writeValueAsString(mesplaylists);
                request.setAttribute("mesplaylists", playlists);
                playlistDatabase.close();

                CatalogueDatabase catalogueDatabase = new CatalogueDatabase();
                List<ContenuSonore> res;
                Boolean recherche;
                //Si une recherche pour un contenu sonore a été faite
                if(request.getParameter("searchText")!=null) {

                    String search = request.getParameter("searchText");
                    recherche=true;
                    System.out.println("recherche : " + search);
                    request.setAttribute("search",search);

                    res = catalogueDatabase.searchAllByTitle(search);
                    res.addAll(catalogueDatabase.searchByAutor(search));
                    res.addAll(catalogueDatabase.searchByCategorie(search));
                    res.addAll(catalogueDatabase.searchByGenreMusical(search));

                }else{
                    recherche=false;
                    System.out.println("pas de recherche, affiche les par défaut");
                    res = catalogueDatabase.getRecommendationMoment();
                    res.addAll(catalogueDatabase.getMorceauxPopulaires());
                }
                catalogueDatabase.close();

                String resJson = mapper.writeValueAsString(res);
                System.out.println(resJson);
                if(recherche){
                    request.setAttribute("musiques", resJson);
                }else {
                    request.setAttribute("musiquesDefault", resJson);
                }
        }

        //Redirige vers la page d'acceuil
        String pageName = "/WEB-INF/restrictAdmin/exploreCat.jsp";
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



