package com.siteStreaming.SiteStreaming.Acceuil;

import com.siteStreaming.SiteStreaming.DataBase.ClientDatabase;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Acceuil extends HttpServlet {
    private void doProcess(HttpServletRequest request, HttpServletResponse response){
        //Cas où le formulaire a été envoyé
        System.out.println("passageServletAcceuil");
        String mail = request.getParameter("mailAddress");
        System.out.println("mail : "+ mail);
        if(mail != null){
            System.out.println("Connection en cours. Preparation servlet.");
            ClientDatabase clientDatabase = new ClientDatabase();
            String infoClient[] = clientDatabase.getAllClientInformation(mail);

            if(true){
                request.setAttribute("infoClient", infoClient); //transmet l'info à d'autre servlet et la page html
                request.setAttribute("signedInSent", true);
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
