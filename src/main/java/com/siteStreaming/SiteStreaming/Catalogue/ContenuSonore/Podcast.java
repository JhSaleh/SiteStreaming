package com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore;

import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Enumérations.categorie;

public class Podcast extends ContenuSonore {
    /**
     * Titre du podcast
     */
    String titre;
    /**
     * Durée du podcast avec un entier en secondes
     */
    int duree;
    /**
     * Auteur du podcast
     */
    String auteur;
    /**
     * Catégorie du podcast
     */
    com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Enumérations.categorie categorie;

    /**
     * Constructeur d'un podcast
     * @param contenu
     * @param recommendationMoment
     * @param titre
     * @param duree
     * @param auteur
     * @param categorie
     */
    public Podcast(String contenu, Boolean recommendationMoment,String titre,
                   int duree, String auteur, categorie categorie) {
        super(contenu, recommendationMoment);
        this.titre = titre;
        this.duree = duree;
        this.auteur = auteur;
        this.categorie = categorie;
    }
}
