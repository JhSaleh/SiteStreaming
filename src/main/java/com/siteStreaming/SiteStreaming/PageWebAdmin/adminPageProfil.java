/**
 * @author: Jean-Hanna SALEH
 */
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
    public static String mailUtilisateurSelectionne = "mailUtilisateurSelectionne";
    public static String groupeUtilisateurEnvoye = "groupeUtilisateurEnvoye";


    public Boolean hasChangedInformation(CompteClient compteClient, String[] infosClient){

        System.out.println("Passage avant null");
        if(infosClient[0] != null) {
            System.out.println("Passage après null");
            if (compteClient.getNom() != infosClient[0]) {
                return true;
            } else if (compteClient.getPrenom() != infosClient[1]) {
                return true;
            } else if (compteClient.getCivilite() != infosClient[2]) {
                return true;
            } else if (compteClient.getPassword() != infosClient[4]) {
                return true;
            } else if (compteClient.getBirthDate() != infosClient[5]) {
                return true;
            } else if (compteClient.getAddressD() != infosClient[6]) {
                return true;
            } else if (compteClient.getStyleMusique() != infosClient[7]) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    private void doProcess(HttpServletRequest request, HttpServletResponse response){
        //Cas où le formulaire a été envoyé

        /*
        CompteClient compteClientChoosen = null;
        request.setAttribute(utilisateurEnvoye, compteClientChoosen);
        */
        ArrayList<CompteClient> matchingClients = new ArrayList<>();

        //Redirige vers la page d'acceuil
        String pageNameModification = "/WEB-INF/admin/administrationProfilClient.jsp";
        String pageNameResearch = "/WEB-INF/admin/adminResearchClient.jsp";
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
            HttpSession session = request.getSession();
            request.setAttribute("emailSelected", emailSelected); //Transfert de la valeur sur la seconde page
            String sentModification = request.getParameter("sentModification");
            if (sentModification != null){ //L'admin a appuyé sur le bouton
                processingData(emailSelected, request, response);
                request.setAttribute("successModification", true); //Déclenche le div
            }

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

    private void processingData(String mail, HttpServletRequest request, HttpServletResponse response){
        String infosClient[] = {request.getParameter("nomUser"),
                request.getParameter("prenomUser"),
                request.getParameter("civiliteUser"),
                request.getParameter("mailUser"),
                request.getParameter("passwordUser"),
                request.getParameter("birthDateUser"),
                request.getParameter("adresseFacturationUser"),
                request.getParameter("styleMusiqueUser")};
        System.out.println("nom :"+infosClient[0] +" prenom :"+ infosClient[1] + "mail :"+ infosClient[3]);

        //Redirige vers la page d'acceuil
        ClientDatabase clientDatabase = new ClientDatabase();
        CompteClient currentCompteClient = clientDatabase.getCompteClient(mail);
        System.out.println("Passage dans Profil");

        if(hasChangedInformation(currentCompteClient, infosClient)) {
            System.out.println("Succes");
            CompteClient compteToModify = new CompteClient(infosClient[0],
                    infosClient[1],
                    infosClient[2],
                    currentCompteClient.getMail(), //car un champs input avec le label disable renvoit null
                    infosClient[4],
                    infosClient[5],
                    infosClient[6],
                    infosClient[7]); //Provient du formulaire
            clientDatabase.modifyClientAccount(compteToModify);
            clientDatabase.closeConnection();
            request.setAttribute("successModification", true); //Déclenche le div
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
