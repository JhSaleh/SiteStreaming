package com.siteStreaming.SiteStreaming.PageWebAdmin;

import com.siteStreaming.SiteStreaming.Access.AdminFilter;
import com.siteStreaming.SiteStreaming.Access.ConnectedUserFilter;
import com.siteStreaming.SiteStreaming.Acceuil.CompteAdmin;
import com.siteStreaming.SiteStreaming.Acceuil.CompteClient;
import com.siteStreaming.SiteStreaming.DataBase.AdministratorDatabase;
import com.siteStreaming.SiteStreaming.DataBase.ClientDatabase;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

public class adminPageProfil extends HttpServlet {
    public static String utilisateurSelectionne = "utilisateurSelectionne";
    public static String groupeUtilisateurEnvoye = "groupeUtilisateurEnvoye";

    private void doProcess(HttpServletRequest request, HttpServletResponse response){
        //Cas où le formulaire a été envoyé

        /*
        CompteClient compteClientChoosen = null;
        request.setAttribute(utilisateurEnvoye, compteClientChoosen);
        */
        ArrayList<CompteClient> matchingClients = new ArrayList<>();

        //Redirige vers la page d'acceuil
        String pageNameModification = "/WEB-INF/administrationProfilClient.jsp";
        String pageNameResearch = "/WEB-INF/adminResearchClient.jsp";
        String page = "";

        String mail = request.getParameter("email");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String emailSelected = request.getParameter("emailSelected");

        if(emailSelected == null) {
            if (mail != null || nom != null || prenom != null) {
                System.out.println(mail + " " + nom + " " + prenom);
                System.out.println("Passage dans la research");
                ClientDatabase clientDatabase = new ClientDatabase();
                ArrayList<CompteClient> result = clientDatabase.searchClient(nom, prenom, mail);
                request.setAttribute(groupeUtilisateurEnvoye, result);
                page = pageNameResearch;
            } else {
                page = pageNameResearch;
            }
        } else {
            request.setAttribute("emailSelected", emailSelected); //Transfert de la valeur sur la seconde page
            page = pageNameModification;
        }



        RequestDispatcher rd = getServletContext().getRequestDispatcher(page);

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
