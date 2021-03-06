package com.siteStreaming.SiteStreaming.PageConnecté;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.ContenuSonore;
import com.siteStreaming.SiteStreaming.DataBase.CatalogueDatabase;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


    public class DataHandler extends HttpServlet {
        private static final long serialVersionUID = 1L;

        public DataHandler() {
            super();
        }

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        }

        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            CatalogueDatabase catalogueDatabase = new CatalogueDatabase();

            List<ContenuSonore> res = catalogueDatabase.searchAllByTitle(request.getParameter("searchtext"));
            res.addAll(catalogueDatabase.searchByAutor(request.getParameter("searchtext")));
            res.addAll(catalogueDatabase.searchByCategorie(request.getParameter("searchtext")));
            res.addAll(catalogueDatabase.searchByGenreMusical(request.getParameter("searchtext")));

            catalogueDatabase.close();

            ObjectMapper mapper = new ObjectMapper();
            String resJson = mapper.writeValueAsString(res);

            response.setContentType("application/json");
            response.getWriter().write(resJson);
        }
    }

