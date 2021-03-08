package com.siteStreaming.SiteStreaming.Acceuil;

import com.siteStreaming.SiteStreaming.Access.AdminFilter;
import com.siteStreaming.SiteStreaming.Access.ConnectedUserFilter;
import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.ContenuSonore;
import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Musique;
import com.siteStreaming.SiteStreaming.DataBase.AdministratorDatabase;
import com.siteStreaming.SiteStreaming.DataBase.CatalogueDatabase;
import com.siteStreaming.SiteStreaming.DataBase.ClientDatabase;
import com.siteStreaming.SiteStreaming.DataBase.PlaylistDatabase;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class Acceuil extends HttpServlet {
    private void doProcess(HttpServletRequest request, HttpServletResponse response){
        Boolean notRedirected = true;
        String pageName = "";

        //Cas où le formulaire a été envoyé
        System.out.println("passageServletAcceuil");
        //Récupération des données du formulaire
        String mail = request.getParameter("mailAddress");
        String password = request.getParameter("password");

        if(mail != null) { //Si un formulaire a été envoyé
            System.out.println("Connection en cours. Preparation servlet.");
            ClientDatabase clientDatabase = new ClientDatabase();
            String infoClient[] = clientDatabase.getAllClientInformation(mail);
            CompteClient compteClient = clientDatabase.getCompteClient(mail);

            AdministratorDatabase administratorDatabase = new AdministratorDatabase();
            CompteAdmin compteAdmin = administratorDatabase.getCompteAdmin(mail);

            if (compteClient != null) {
                System.out.println("Mdp entrée   : " + password);
                System.out.println("Mdp comparé  : " + compteClient.getPassword());
                if (compteClient.isPassWord(password)) { //Vérification du mdp

                    //Essaie
                    System.out.println("Coté serveur : Connection réussit.");
                    HttpSession session = request.getSession(); //Création d'une session utilisateur s'il n'a pas été créé avant
                    session.setAttribute(ConnectedUserFilter.sessionUtilisateur, compteClient); //Ajoute à la session la notion de client

                    /*
                    request.setAttribute("infoClient", infoClient); //transmet l'info à d'autre servlet et la page html
                    request.setAttribute("signedInSent", true);
                     */
                } else {
                    System.out.println("Coté serveur : Echec de connection renvoit des données.");
                    request.setAttribute("mailAddressUsed", mail);
                    request.setAttribute("passwordUsed", password);
                }
                pageName = "/WEB-INF/index.jsp";

            }else if(compteAdmin != null){
                if(compteAdmin.isPassWord(password)){
                    System.out.println("Administrateur trouvé !");
                    HttpSession session = request.getSession(); //Création d'une session utilisateur s'il n'a pas été créé avant
                    session.setAttribute(AdminFilter.sessionAdmin, compteAdmin); //Création de la session administrateur
                    RequestDispatcher rdAdmin;
                    if(compteAdmin.getIsProfilManagerClient().equals("true")){
                        pageName = "Administration/AdminProfilClient";
                        System.out.println("ManageProfil");
                        try {
                            response.sendRedirect(pageName);
                            notRedirected = false;
                        } catch (IOException  e){
                            e.printStackTrace();
                        }
                    } else {
                        rdAdmin = request.getRequestDispatcher("..."); //METTRE LE LIEN VERS LE PROFIL GESTION MUSICAL
                        System.out.println("ManageLibrary");
                    }


                    //Ajouté la redirection vers la bonne page admin
                } else {
                    System.out.println("Coté serveur : Echec de connection renvoit des données.");
                    request.setAttribute("mailAddressUsed", mail);
                    request.setAttribute("passwordUsed", password);
                    pageName = "/WEB-INF/index.jsp";
                }
            }else {
                System.out.println("Coté serveur : Echec de connection renvoit des données.");
                request.setAttribute("mailAddressUsed", mail);
                request.setAttribute("passwordUsed", password);
                //pageName = "/WEB-INF/index.jsp";
                pageName = "/WEB-INF/index.jsp";
            }
        } else {
            pageName = "/WEB-INF/index.jsp";
        }


        //Va chercher les musiques
        CatalogueDatabase cataloqueDatabase = new CatalogueDatabase();
        List<ContenuSonore> listMus = cataloqueDatabase.getRecommendationMoment();
        listMus.addAll(cataloqueDatabase.getMorceauxPopulaires());
        request.setAttribute("listMus",listMus);
        System.out.println("mise des musiques en attribut");

        //Regarde si une musique est écoutée
        PlaylistDatabase playlistDatabase = new PlaylistDatabase();
        if(request.getParameter("hiddenChamp")!=null) {
            int idMusique = Integer.parseInt(request.getParameter("hiddenChamp"));
            Musique m = playlistDatabase.getMusique(idMusique);
            m.setNbLectureTotal(m.getNbLectureTotal() + 1);
            m.setNbLectureMois(m.getNbLectureMois() + 1);
            cataloqueDatabase.infoStatMAJContenuSonore(m);
            //renvoie la musique à écouter
            request.setAttribute("musique", m.musToJson());
        }
        playlistDatabase.close();
        cataloqueDatabase.close();
        //Redirige vers la page d'acceuil
        pageName = "/WEB-INF/index.jsp";
        RequestDispatcher rd = getServletContext().getRequestDispatcher(pageName);
        if(notRedirected) { //parce qu'on peut pas forward et redirect en même temps
            rd = getServletContext().getRequestDispatcher(pageName);

            try {
                rd.forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
