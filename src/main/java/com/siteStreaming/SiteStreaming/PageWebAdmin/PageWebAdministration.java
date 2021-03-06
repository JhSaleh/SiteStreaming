package com.siteStreaming.SiteStreaming.PageWebAdmin;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PageWebAdministration extends HttpServlet {
    private void doProcess(HttpServletRequest request, HttpServletResponse response){

        String paramNom = request.getParameter( "nom" );
        String paramAction = request.getParameter( "actionEf" );
        String paramNbTitres = request.getParameter("nbtitres");
        String actionLine = "Vous voulez : "+paramAction+" le client :"+paramNom;
        int formAction = 0;

        request.setAttribute("actionLine",actionLine);
        String pageName = "/administration.jsp";
        this.getServletContext().getRequestDispatcher(pageName);

        RequestDispatcher rd = getServletContext().getRequestDispatcher(pageName);

        try {
            rd.forward(request, response);
        } catch (ServletException | IOException e) {
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
