package com.siteStreaming.SiteStreaming.Accueil;

import com.siteStreaming.SiteStreaming.LoggerSite;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet redirigeant vers la page d'inscription et traitant les inscriptions reçues.
 */
public class Inscription extends HttpServlet {
    private void doProcess(HttpServletRequest request, HttpServletResponse response) {

        if(request.getParameter("nom") != null){
            CompteClient compteToAdd = new CompteClient(request.getParameter("nom"),
                                                        request.getParameter("prenom"),
                                                        request.getParameter("civilite"),
                                                        request.getParameter("mail"),
                                                        request.getParameter("password"),
                                                        request.getParameter("birthDate"),
                                                        request.getParameter("adresseFacturation"),
                                                        request.getParameter("styleMusique"));

            //En cas d'échec de l'ajout, on envoit les champs déjà remplis remplis aux clients
            boolean successAdd = compteToAdd.addToDatabase(compteToAdd);
            if(!successAdd){
                LoggerSite.logger.info("Echec de l'insciption");
                request.setAttribute("compteInscription", compteToAdd);
                request.setAttribute("successSignUp", false);
            } else {
                LoggerSite.logger.debug("Inscription réussie");
                request.setAttribute("successSignUp", true);
            }
        }




        //Redirige vers la page d'inscription
        String pageName = "/WEB-INF/inscription.jsp";
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
