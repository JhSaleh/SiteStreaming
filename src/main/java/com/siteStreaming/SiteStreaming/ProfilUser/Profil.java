/**
 * @author: Jean-Hanna SALEH
 */
package com.siteStreaming.SiteStreaming.ProfilUser;

import com.siteStreaming.SiteStreaming.Access.ConnectedUserFilter;
import com.siteStreaming.SiteStreaming.Acceuil.CompteClient;
import com.siteStreaming.SiteStreaming.DataBase.ClientDatabase;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Profil extends HttpServlet {

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
        HttpSession session = request.getSession(); //Création d'une session utilisateur s'il n'a pas été créé avant
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
        CompteClient currentCompteClient = (CompteClient) session.getAttribute(ConnectedUserFilter.sessionUtilisateur);
        //CompteClient lastStateCompteClient = (CompteClient) session.getAttribute(ConnectedUserFilter.lastStateSessionUtilisateur);
        System.out.println("Passage dans Profil");

       if(hasChangedInformation(currentCompteClient, infosClient)) {
           System.out.println("Succes");
            ClientDatabase clientDatabase = new ClientDatabase();
            CompteClient compteToModify = new CompteClient(infosClient[0],
                    infosClient[1],
                    infosClient[2],
                    currentCompteClient.getMail(), //car un champs input avec le label disable renvoit null
                    infosClient[4],
                    infosClient[5],
                    infosClient[6],
                    infosClient[7]); //Provient du formulaire
            clientDatabase.modifyClientAccount(compteToModify);

            session.setAttribute(ConnectedUserFilter.sessionUtilisateur, compteToModify); //MAJ de l'objet de session
            request.setAttribute("successModification", true); //Déclenche le div
        }

        //Redirige vers la page
        String pageName = "/WEB-INF/profil.jsp";
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
