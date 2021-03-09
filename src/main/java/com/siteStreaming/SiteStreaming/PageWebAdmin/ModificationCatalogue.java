package com.siteStreaming.SiteStreaming.PageWebAdmin;

import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.ContenuSonore;
import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Musique;
import com.siteStreaming.SiteStreaming.DataBase.CatalogueDatabase;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ModificationCatalogue extends HttpServlet {
    public static String choixModif = "choixModif";
    public static String actionModif = "actionModif";
    public static String firstStep = "firstStep";

    public static String Ajouter = "Ajouter";
    public static String Modifier = "Modifier";
    public static String Supprimer = "Supprimer";

    public static String secondStep = "secondStep";


    private void doProcess(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        /*
        String byType = request.getParameter("byType");
        String byName = request.getParameter("byName");
        System.out.println("le type recherché est :"+byType+" , le nom recherché est : "+byName);
        CatalogueDatabase catalogue = new CatalogueDatabase();

        List<ContenuSonore> listRes = catalogue.searchAllByTitle(byName);
        request.setAttribute("listRes",listRes);

        String byID = request.getParameter("ADM_modmusSelectedElem");
        System.out.println("By ID ="+byID);

        ResultSet res = catalogue.getAllBy("musique","",byID);
        List<ContenuSonore> listMusique = catalogue.readResultset("musique",res);
        request.setAttribute("ModMus",listMusique);
        */

        String action = request.getParameter("action");
        String choixContenu = request.getParameter("choixContenu");

        String rechercherModifierSupprimer = request.getParameter("rechercherModifierSupprimer");
        String fieldModifierSupprimer = request.getParameter("fieldModifierSupprimer");

        if(action != null){
            System.out.println("Premier traitement.");
            request.setAttribute(choixModif, choixContenu);
            request.setAttribute(actionModif, action);
            request.setAttribute(firstStep, true);

            //Modifier/Supprimer
            if(fieldModifierSupprimer != null){
                request.setAttribute(secondStep, true);

            }


        } else {
            request.setAttribute("firstStep", false);
        }


        String pageName = "/WEB-INF/admin/adminModifCatalogue.jsp";
        this.getServletContext().getRequestDispatcher(pageName);
        RequestDispatcher rd = getServletContext().getRequestDispatcher(pageName);

        try {
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
        try {
            doProcess(req, resp);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        try {
            doProcess(req, resp);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
