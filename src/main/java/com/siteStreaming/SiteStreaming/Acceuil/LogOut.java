package com.siteStreaming.SiteStreaming.Acceuil;

import com.siteStreaming.SiteStreaming.DataBase.ClientDatabase;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogOut extends HttpServlet {
    private void doProcess(HttpServletRequest request, HttpServletResponse response){
        System.out.println("Deconnection.");
        HttpSession session = request.getSession(); //Création d'une session utilisateur s'il n'a pas été créé avant
        session.invalidate(); //fermeture de la session

        //Redirection vers la page d'acceuil
        try{
            response.sendRedirect("./Acceuil");
        }catch(IOException e) {
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

