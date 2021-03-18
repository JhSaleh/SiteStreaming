package com.siteStreaming.SiteStreaming.Access;

import com.siteStreaming.SiteStreaming.Accueil.CompteAdmin;
import com.siteStreaming.SiteStreaming.LoggerSite;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Cette classe empêche des administrateurs d'accéder aux partie du site qui leur est interdit.
 */
public class NonAdminFilter implements Filter {
    /**
     * Objet identifiant la session
     */
    public static final String sessionAdmin = "sessionAdministrateur";


    /**
     * Page vers laquelle on redirige l'administrateur de gestion de profil client
     */
    public static final String ACCES_ADMIN_CLIENT = "/Administration/AdminProfilClient";

    /**
     * Page vers laquelle on redirige l'administrateur de gestion de contenu de bibliotheque
     */
    public static final String ACCES_ADMIN_CONTENU = "/Administration/AdminGestionnaireMusical";

    /**
     * Initialisation du filtre
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        /* Cast des objets request et response */
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        /* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();

        LoggerSite.logger.info("On filtre la requete dans AccessFilter.");

        /*
         * Si l'objet administrateur existe dans la session en cours, alors
         * l'administrateur n'a pas le droit d'accéder à la page d'inscription
         */
        if ( session.getAttribute(sessionAdmin) != null ) {
            CompteAdmin admin = (CompteAdmin) session.getAttribute(sessionAdmin);
            System.out.println("Is admin client manager : "+admin.getIsProfilManagerClient());
            if(admin.getIsProfilManagerClient().equals("true")){
                /* Redirection vers la page publique */
                response.sendRedirect(request.getContextPath() + ACCES_ADMIN_CLIENT);
            } else {
                response.sendRedirect(request.getContextPath() + ACCES_ADMIN_CONTENU);
            }
        } else {
            /* Affichage de la page restreinte */
            filterChain.doFilter( request, response );
        }
    }

    /**
     * Destruction du filtre
     */
    @Override
    public void destroy() {

    }

}
