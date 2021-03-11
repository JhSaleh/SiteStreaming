package com.siteStreaming.SiteStreaming.PageWebAdmin;

import com.siteStreaming.SiteStreaming.DataBase.AdminDatabase;
import com.siteStreaming.SiteStreaming.LoggerSite;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * A enlever, CLASSE INUTILE
 */
public class PageWebAdministration extends HttpServlet {
    private void doProcess(HttpServletRequest request, HttpServletResponse response) throws SQLException {


        String defaultActionString = "action";
        String defaultNameString = "nom";
        String actionLine;
        boolean ActionLineStatus = false;

        String paramAction = request.getParameter( "actionEf" );
        String paramNom = request.getParameter( "nom" );
        LoggerSite.logger.info("paramAction is : "+paramAction);

        if (paramAction==null|| paramNom==null) {
            actionLine = "Vous voulez : " + defaultActionString + " le client :" + defaultNameString;
            LoggerSite.logger.debug("null null"+actionLine);

        }else{
            actionLine = "Vous voulez : " + paramAction + " le client : " + paramNom;
            ActionLineStatus = true;
            LoggerSite.logger.debug("non null"+actionLine);
        }

        request.setAttribute("showAction",ActionLineStatus);
        request.setAttribute("actionLine",actionLine);

        String defaultNbTitresString = "0";
        String byPeriode = request.getParameter("byPeriode");
        String paramNbTitres = request.getParameter("nbtitres");
        String byType = request.getParameter("byType");

        if (paramNbTitres==null){
            paramNbTitres = defaultNbTitresString;
        }
        if (paramNbTitres.equals("")){
            paramNbTitres=defaultNbTitresString;
        }

        LoggerSite.logger.info("byPeriode checkbox status = "+byPeriode);
        LoggerSite.logger.info("byType checkbox status = "+byType);



        if (!paramNbTitres.equals("0")) {
            System.out.println("paramNbTitres = " + paramNbTitres);
            AdminDatabase admDBImpl = new AdminDatabase();
            if ((byPeriode.equals("byAll"))) {
                request.setAttribute("topNmusique",admDBImpl.consulterTopN(Integer.parseInt(paramNbTitres), "all",byType));
            } else {
                request.setAttribute("topNmusique",admDBImpl.consulterTopN(Integer.parseInt(paramNbTitres), "mois",byType));
            }
        }

        String pageName = "/WEB-INF/admin/administrationProfilClient.jsp";
        this.getServletContext().getRequestDispatcher(pageName);
        RequestDispatcher rd = getServletContext().getRequestDispatcher(pageName);

        try {
            rd.forward(request, response);
        } catch (ServletException | IOException e) {
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
