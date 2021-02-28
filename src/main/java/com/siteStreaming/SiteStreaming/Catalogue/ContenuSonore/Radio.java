package com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore;

import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Enumérations.genreMusical;

/**
 * Pour les contenus sonore correspondants à une radio
 */
public class Radio extends ContenuSonore {
    /**
     * Nom de la radio
     */
    String nom;
    /**
     * Genre musical de la radio
     */
    com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Enumérations.genreMusical genreMusical;


    /**
     * Constructeur d'une radio
     * @param contenu
     * @param recommendationMoment
     * @param nom
     * @param genreMusical
     */
    public Radio(String contenu, Boolean recommendationMoment,String nom, genreMusical genreMusical) {
        super(contenu, recommendationMoment);
        this.nom = nom;
        this.genreMusical = genreMusical;
    }
}
