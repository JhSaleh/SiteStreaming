package com.siteStreaming.SiteStreaming.PageWebAdmin;

import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.ContenuSonore;
import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Musique;
import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Podcast;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModificationCatalogue extends HttpServlet {
    public static String choixModif = "choixModif";
    public static String actionModif = "actionModif";
    public static String firstStep = "firstStep";

    public static String Ajouter = "Ajouter";
    public static String Modifier = "Modifier";
    public static String Supprimer = "Supprimer";

    public static String secondStep = "secondStep";

    public static String fieldResearchCompleted = "fieldResearchCompleted";
    public static String dropDownListSupprimerModifier = "dropDownListModifierSupprimer";

    public boolean redirect;

    private void doProcess(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        redirect = false;
        LoggerSite.logger.debug("Passage servlet.");

        //Champs envoyés
        String action = request.getParameter("action");
        String choixContenu = request.getParameter("choixContenu");

        String dropDownListModifierSupprimer = request.getParameter("dropDownListModifierSupprimer");
        String fieldModifierSupprimer = request.getParameter("fieldModifierSupprimer");

        //1ere étape
        if(action != null){
            System.out.println("Premier traitement.");
            request.setAttribute(choixModif, choixContenu);
            request.setAttribute(actionModif, action);
            request.setAttribute(firstStep, true);
            if(action.equals("Ajouter")){

                String page = request.getContextPath() + "/Administration/AdminGestionnaireMusicalTraitement?actionChoisit="+action+"&choixContenuChoisit="+choixContenu;
                this.getServletContext().getRequestDispatcher(page);
                redirect = true;
                try {
                    response.sendRedirect(page);
                } catch (IOException e) {
                    LoggerSite.logger.error(e);
                }
            }

        } else {
            request.setAttribute(firstStep, false);
        }

        //2ème étape : Modifier/Supprimer
        if(fieldModifierSupprimer != null){
            LoggerSite.logger.debug("Second traitement.");
            //Capture des paramètres du lien, transmission dans sur la seconde réponse
            action = request.getParameter("actionChoisit");
            choixContenu = request.getParameter("choixContenuChoisit");
            request.setAttribute(actionModif, action);
            request.setAttribute(choixModif, choixContenu);

            request.setAttribute(dropDownListSupprimerModifier, dropDownListModifierSupprimer);
            request.setAttribute(fieldResearchCompleted, fieldModifierSupprimer);
            System.out.println(dropDownListModifierSupprimer);

            request.setAttribute(firstStep, true);
            request.setAttribute(secondStep, true);

            if(choixContenu.equals("Musique")){
                LoggerSite.logger.debug("Passage musique");

                AdminDatabase adminDatabase = new AdminDatabase();
                ArrayList<Musique> resultatMusique = new ArrayList<>();
                if(dropDownListModifierSupprimer.equals("Id")){
                    resultatMusique = (ArrayList<Musique>) adminDatabase.getMusic(Integer.parseInt(fieldModifierSupprimer), null);
                } else {
                    resultatMusique = (ArrayList<Musique>) adminDatabase.getMusic(-1, fieldModifierSupprimer);
                }

                request.setAttribute("resultatListeMusique", resultatMusique);
            } else if(choixContenu.equals("Radio")){
                LoggerSite.logger.debug("Passage radio");

                AdminDatabase adminDatabase = new AdminDatabase();
                ArrayList<Radio> resultatRadio = new ArrayList<>();
                if(dropDownListModifierSupprimer.equals("Id")){
                    resultatRadio = (ArrayList<Radio>) adminDatabase.getRadio(Integer.parseInt(fieldModifierSupprimer), null);
                } else {
                    resultatRadio = (ArrayList<Radio>) adminDatabase.getRadio(-1, fieldModifierSupprimer);
                }

                request.setAttribute("resultatListeRadio", resultatRadio);

            } else if(choixContenu.equals("Podcast")){
                LoggerSite.logger.debug("Passage podcast");

                AdminDatabase adminDatabase = new AdminDatabase();
                ArrayList<Podcast> resultatPodcast = new ArrayList<>();
                if(dropDownListModifierSupprimer.equals("Id")){
                    resultatPodcast = (ArrayList<Podcast>) adminDatabase.getPodcast(Integer.parseInt(fieldModifierSupprimer), null);
                } else {
                    resultatPodcast = (ArrayList<Podcast>) adminDatabase.getPodcast(-1, fieldModifierSupprimer);
                }
                request.setAttribute("resultatListePodcast", resultatPodcast);

            }
        }

        if(!redirect) {
            String pageName = "/WEB-INF/admin/adminModifCatalogue.jsp";
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
