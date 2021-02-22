package com.siteStreaming.SiteStreaming.Acceuil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Inscription extends HttpServlet {
    private void doProcess(HttpServletRequest request, HttpServletResponse response) {

        if(request.getParameter("nom") != null){
            CompteClient compteToAdd = new CompteClient(request.getParameter("nom"),
                                                        request.getParameter("prenom"),
                                                        request.getParameter("civilite"),
                                                        request.getParameter("mail"),
                                                        request.getParameter("password"),
                                                        request.getParameter("birthDate"),
                                                        request.getParameter("adresseFacturation"));

            compteToAdd.addToDatabase(compteToAdd);

        }


        //Redirige vers la page d'acceuil
        String pageName = "/inscription.jsp";
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
        doProcess(req, resp);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        doProcess(req, resp);
    }
}
