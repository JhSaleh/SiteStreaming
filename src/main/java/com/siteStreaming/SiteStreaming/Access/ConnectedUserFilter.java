package com.siteStreaming.SiteStreaming.Access;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ConnectedUserFilter implements Filter {
    /**
     * Page vers laquelle on redirige les utilisateurs qui n'ont pas de session ouverte.
     */
    public static final String ACCES_PUBLIC = "/Acceuil";

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

        System.out.println("On filtre la requete dans AccessFilter.");

        /*
         * Si l'objet utilisateur existe dans la session en cours, alors
         * l'utilisateur n'a pas le droit d'accéder à la page d'inscription
         */
        if ( session.getAttribute(sessionUtilisateur) != null ) {
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
