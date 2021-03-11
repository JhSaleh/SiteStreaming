package com.siteStreaming.SiteStreaming.PageWebAdmin;

import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Enumérations.genreMusical;
import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Musique;
import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Radio;
import com.siteStreaming.SiteStreaming.DataBase.AdminDatabase;
import com.siteStreaming.SiteStreaming.DataBase.CatalogueDatabase;
import com.siteStreaming.SiteStreaming.LoggerSite;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class TraitementModificationCatalogue extends HttpServlet {
    public static String addSuccess = "addSuccess";
    public static String modifySuccess = "addSuccess";
    public static String deleteSuccess = "addSuccess";


    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        LoggerSite.logger.info("Passage servlet traitement catalogue.");
        //Premier passage dans le servlet
        String action = request.getParameter("actionChoisit");
        String choixContenu = request.getParameter("choixContenuChoisit");
        String idMusique = request.getParameter("idMusique");
        String idRadio = request.getParameter("idRadio");
        String idPodcast = request.getParameter("idPodcast");

        request.setAttribute("actionChoisit", action);
        request.setAttribute("choixContenuChoisit", choixContenu);

        //Les ids
        request.setAttribute("idMusique", idMusique);
        request.setAttribute("idRadio", idRadio);
        request.setAttribute("idPodcast", idPodcast);

        LoggerSite.logger.debug("-------Dans le servlet\n Action : "+action+"\nchoixContenu : "+choixContenu+"\nidMusique : "+idMusique);

        //Envoit du form de la page
        String idSent = request.getParameter("idSent"); //Valable pour un podcast, une radio et une musique
        //Récupération des boutons
        String AjouterMusiqueButton = request.getParameter("AjouterMusiqueButton");
        String ModifierMusiqueButton = request.getParameter("ModifierMusiqueButton");
        String SupprimerMusiqueButton = request.getParameter("SupprimerMusiqueButton");

        String AjouterRadioButton = request.getParameter("AjouterRadioButton");
        String ModifierRadioButton = request.getParameter("ModifierRadioButton");
        String SupprimerRadioButton = request.getParameter("SupprimerRadioButton");

        String AjouterPodcastButton = request.getParameter("AjouterPodcastButton");
        String ModifierPodcastButton = request.getParameter("ModifierPodcastButton");
        String SupprimerPodcastButton = request.getParameter("SupprimerPodcastButton");


        //Musique
        String recommendationMomentMusic = request.getParameter("recommendationMomentMusic");
        String titreMusic = request.getParameter("titreMusic");
        String interMusic = request.getParameter("interMusic");
        String yearMusic = request.getParameter("yearMusic");
        String genreMusic = request.getParameter("genreMusic");
        String lengthMusic = request.getParameter("lengthMusic");
        Musique musique = null;

        if(genreMusic != null) {
            musique = new Musique("Du contenu audio.", Boolean.parseBoolean(recommendationMomentMusic), titreMusic, interMusic, yearMusic, genreMusical.valueOf(genreMusic), Integer.parseInt(lengthMusic));
            if(AjouterMusiqueButton == null) {
                musique.setId(Integer.parseInt(idSent));
            }
        }
        if(AjouterMusiqueButton != null){
            CatalogueDatabase catalogueDatabase = new CatalogueDatabase();
            catalogueDatabase.createContenuSonore(musique);
            LoggerSite.logger.info("Musique créée !");

        } else if(ModifierMusiqueButton != null){
            CatalogueDatabase catalogueDatabase = new CatalogueDatabase();
            catalogueDatabase.updateContenuSonore(musique);
            LoggerSite.logger.info("Musique modifiée !");

        } else if(SupprimerMusiqueButton != null){
            CatalogueDatabase catalogueDatabase = new CatalogueDatabase();
            catalogueDatabase.deleteContenuSonore(musique);
            LoggerSite.logger.info("Musique supprimée !");
        }

        //Radio
        String recommendationMomentRadio = request.getParameter("recommendationMomentRadio");
        String nomRadio = request.getParameter("nomRadio");
        String genreRadio = request.getParameter("genreRadio");
        Radio radio = null;


        if(genreRadio != null) {
            radio = new Radio("Du contenu audio.", Boolean.parseBoolean(recommendationMomentRadio), nomRadio, genreMusical.valueOf(genreRadio));
            if(AjouterMusiqueButton == null) {
                radio.setId(Integer.parseInt(idSent));
            }
        }
        if(AjouterRadioButton != null){
            CatalogueDatabase catalogueDatabase = new CatalogueDatabase();
            catalogueDatabase.createContenuSonore(radio);
            LoggerSite.logger.info("Radio créée !");

        } else if(ModifierRadioButton != null){
            CatalogueDatabase catalogueDatabase = new CatalogueDatabase();
            catalogueDatabase.updateContenuSonore(radio);
            LoggerSite.logger.info("Radio modifiée !");

        } else if(SupprimerRadioButton != null){
            CatalogueDatabase catalogueDatabase = new CatalogueDatabase();
            catalogueDatabase.deleteContenuSonore(radio);
            LoggerSite.logger.info("Radio supprimée !");
        }

        //Podcast



        String pageName = "/WEB-INF/admin/adminModifCatalogueTraitement.jsp";
        this.getServletContext().getRequestDispatcher(pageName);
        RequestDispatcher rd = getServletContext().getRequestDispatcher(pageName);

        try {
            rd.forward(request, response);
        } catch (ServletException e) {
            LoggerSite.logger.error(e);
        } catch (IOException e) {
            LoggerSite.logger.error(e);
        }

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        try {
            doProcess(req, resp);
        } catch (SQLException e) {
            LoggerSite.logger.error(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        try {
            doProcess(req, resp);
        } catch (SQLException e) {
            LoggerSite.logger.error(e);
        }
    }
}
