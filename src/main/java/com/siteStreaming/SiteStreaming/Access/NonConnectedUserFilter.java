package com.siteStreaming.SiteStreaming.Access;


import com.siteStreaming.SiteStreaming.LoggerSite;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Classe qui redirige les utillisateurs non connectés.
 */
public class NonConnectedUserFilter implements Filter {
    /**
     * Page vers laquelle on redirige les utilisateurs qui n'ont pas de session ouverte.
     */
    public static final String ACCES_PUBLIC = "/Accueil";

    /**
     * Objet identifiant la session
     */
    public static final String sessionUtilisateur = "sessionUtilisateur";

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
         * Si l'objet utilisateur n'existe pas dans la session en cours, alors
         * l'utilisateur n'est pas connecté.
         */
        if ( session.getAttribute(sessionUtilisateur) == null) {
            /* Redirection vers la page publique */
            response.sendRedirect(request.getContextPath() + ACCES_PUBLIC);
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
