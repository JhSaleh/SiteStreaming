package com.siteStreaming.SiteStreaming.Acceuil;

import com.siteStreaming.SiteStreaming.Access.ConnectedUserFilter;
import com.siteStreaming.SiteStreaming.DataBase.ClientDatabase;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Acceuil extends HttpServlet {
    private void doProcess(HttpServletRequest request, HttpServletResponse response){
        //Cas où le formulaire a été envoyé
        System.out.println("passageServletAcceuil");
        //Récupération des données du formulaire
        String mail = request.getParameter("mailAddress");
        String password = request.getParameter("password");

        /*
        System.out.println("mail : "+mail);
        System.out.println("password : "+password);
        System.out.println("fake :"+passwordWow);
        */

        if(mail != null){ //Si un formulaire a été envoyé
            System.out.println("Connection en cours. Preparation servlet.");
            ClientDatabase clientDatabase = new ClientDatabase();
            String infoClient[] = clientDatabase.getAllClientInformation(mail);
            CompteClient compteClient = clientDatabase.getCompteClient(mail);
            CompteClient lastStateCompteClient = clientDatabase.getCompteClient(mail);

            if(compteClient != null){
                System.out.println("Mdp entrée   : "+password);
                System.out.println("Mdp comparé  : "+compteClient.getPassword());
                if(compteClient.isPassWord(password)){ //Vérification du mdp

                    //Essaie
                    System.out.println("Coté serveur : Connection réussit.");
                    HttpSession session = request.getSession(); //Création d'une session utilisateur s'il n'a pas été créé avant
                    session.setAttribute(ConnectedUserFilter.sessionUtilisateur, compteClient); //Ajoute à la session la notion de client


                    request.setAttribute("infoClient", infoClient); //transmet l'info à d'autre servlet et la page html
                    request.setAttribute("signedInSent", true);
                } else {
                    System.out.println("Coté serveur : Echec de connection renvoit des données.");
                    request.setAttribute("mailAddressUsed", mail);
                    request.setAttribute("passwordUsed", password);
                }
            }
        }




        //Redirige vers la page d'acceuil
        String pageName = "/index.jsp";
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
