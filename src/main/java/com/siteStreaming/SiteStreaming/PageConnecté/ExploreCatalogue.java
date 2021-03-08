package com.siteStreaming.SiteStreaming.PageConnecté;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.ContenuSonore;
import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Musique;
import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Podcast;
import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Radio;
import com.siteStreaming.SiteStreaming.Catalogue.Playlist;
import com.siteStreaming.SiteStreaming.DataBase.CatalogueDatabase;

import com.siteStreaming.SiteStreaming.DataBase.PlaylistDatabase;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExploreCatalogue extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //Cas où le formulaire a été envoyé
            //request.getParameter("mailAddress")
            String mail = "aarobase@mail";
            String genre = "pop";
            System.out.println("dopost");
            if (mail != null) {
                CatalogueDatabase catalogueDatabase = new CatalogueDatabase();
                PlaylistDatabase playlistDatabase = new PlaylistDatabase();
                int idMus;
                int idPlay;
                //lecture de contenu sonore
                if(request.getParameter("hiddenChamp")!=null &&
                        request.getParameter("hiddenChamp2")!=null
                && !request.getParameter("hiddenChamp").equals("") &&
                        !request.getParameter("hiddenChamp2").equals("")) {
                    System.out.println("ecouter");
                    //on récupère les paramètres
                    idMus = Integer.parseInt(request.getParameter("hiddenChamp"));
                    idPlay = Integer.parseInt(request.getParameter("hiddenChamp2"));

                    ObjectMapper mapper = new ObjectMapper();
                    String tpres = "";

                    //s'il s'agit d'une lecture de playlist
                    if (idPlay != -1) {
                        //on met toutes les musiques de la playlist à lu
                        Musique temp;
                        Playlist playlistLu = playlistDatabase.getPlaylistById(idPlay, mail);
                        for (int i = 0; i < playlistLu.getMusique().toArray().length; i++) {
                            temp = playlistLu.getMusique().get(i);
                            temp.setNbLectureMois(temp.getNbLectureMois() + 1);
                            temp.setNbLectureTotal(temp.getNbLectureTotal() + 1);
                            catalogueDatabase.infoStatMAJContenuSonore(temp);
                        }
                        temp = playlistDatabase.getMusique(idMus);
                        //on renvoie la musique sélectionnée en Json
                        tpres = mapper.writeValueAsString(temp);
                        System.out.println(idMus+" - "+idPlay+" - "+tpres);
                        request.setAttribute("Musique", tpres);
                        request.setAttribute("idPlaylist", idPlay);
                    } else {
                        //si on n'est pas dans une playlist, on cherche le type de contenu
                        List<ContenuSonore> contenutemp =null;
                        Podcast p =null;
                        Radio r = null;
                        Musique m = playlistDatabase.getMusique(idMus);
                        if(m==null) {
                            contenutemp = (catalogueDatabase.readResultset("podcast",
                                    catalogueDatabase.getAllBy("podcast", " and idPodcast=? limit 1", String.valueOf(idMus))));

                            if (contenutemp == null || contenutemp.toArray().length == 0) {
                                System.out.println("podcast  null");
                                contenutemp = catalogueDatabase.readResultset("radio",
                                        catalogueDatabase.getAllBy("radio", " and idRadio=? limit 1", String.valueOf(idMus)));

                                if (contenutemp == null || contenutemp.toArray().length == 0) {
                                    System.out.println("radio is null");
                                } else {
                                    System.out.println("radio not null");
                                    System.out.println(contenutemp);
                                    r = (Radio) contenutemp.get(0);
                                }
                            } else {
                                System.out.println("podcast not null");
                                System.out.println(contenutemp);
                                p = (Podcast) contenutemp.get(0);
                            }
                        }

                        //On met à jour le nombre de lecture, l'enregistre et le
                        // converti en Json
                        if (m != null) {
                            m.setNbLectureTotal(m.getNbLectureTotal() + 1);
                            m.setNbLectureMois(m.getNbLectureMois() + 1);
                            tpres = mapper.writeValueAsString(m);
                            catalogueDatabase.infoStatMAJContenuSonore(m);
                        } else if (p != null) {
                            p.setNbLectureTotal(p.getNbLectureTotal() + 1);
                            p.setNbLectureMois(p.getNbLectureMois() + 1);
                            tpres = mapper.writeValueAsString(p);
                            catalogueDatabase.infoStatMAJContenuSonore(p);
                        } else if (r != null) {
                            r.setNbLectureTotal(r.getNbLectureTotal() + 1);
                            r.setNbLectureMois(r.getNbLectureMois() + 1);
                            tpres = mapper.writeValueAsString(r);
                            catalogueDatabase.infoStatMAJContenuSonore(r);
                        }
                        request.setAttribute("Musique", tpres);
                    }
                }

                //Ajouter une musique à une playlist
                if(request.getParameter("hiddenChampBis")!=null &&request.getParameter("playList")!=null
               && !request.getParameter("hiddenChampBis").equals("") &&
                !request.getParameter("playList").equals("")) {
                    String sidMus = request.getParameter("hiddenChampBis");
                    String sidPlay = request.getParameter("playList");

                    idMus = Integer.parseInt(sidMus);
                    idPlay = Integer.parseInt(sidPlay);
                    playlistDatabase.addMusiquetoPlaylist(playlistDatabase.getPlaylistById(idPlay, mail),
                           playlistDatabase.getMusique(idMus));
                }
                //donne les playlists à la page
                List<Playlist> mesplaylists = playlistDatabase.getAllPlaylist(mail);
                String playlists;
                ObjectMapper mapper = new ObjectMapper();
                playlists = mapper.writeValueAsString(mesplaylists);
                request.setAttribute("mesplaylists", playlists);
                playlistDatabase.close();

                //Si une recherche pour un contenu sonore a été faite
                List<ContenuSonore> res;
                Boolean recherche;
                if (request.getParameter("searchText") != null) {

                    String search = request.getParameter("searchText");
                    recherche = true;
                    System.out.println("recherche : " + search);
                    request.setAttribute("search", search);

                    res = catalogueDatabase.searchAllByTitle(search);
                    res.addAll(catalogueDatabase.searchByAutor(search));
                    res.addAll(catalogueDatabase.searchByCategorie(search));
                    res.addAll(catalogueDatabase.searchByGenreMusical(search));

                } else {
                    recherche = false;
                    res = catalogueDatabase.searchByGenreMusical(genre);
                }
                catalogueDatabase.close();

                String resJson = mapper.writeValueAsString(res);
                System.out.println(resJson);

                if (recherche) {
                    request.setAttribute("musiques", resJson);
                } else {
                    request.setAttribute("musiquesDefault", resJson);
                }
            }

            //Redirige vers la page d'acceuil
            String pageName = "/WEB-INF/exploreCat.jsp";
            RequestDispatcher rd = getServletContext().getRequestDispatcher(pageName);

            rd.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        doPost(request, response);
    }
}


