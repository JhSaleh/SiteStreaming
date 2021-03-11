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


    //Getter et Setter
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Enumérations.genreMusical getGenreMusical() {
        return genreMusical;
    }

    public void setGenreMusical(com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Enumérations.genreMusical genreMusical) {
        this.genreMusical = genreMusical;
    }

    /**
     * Constructeur d'une radio
     * @param contenu lien vers la radio
     * @param recommendationMoment booléen qui indique si le contenu fait partie des recommendations du moment
     * @param nom de la radio
     * @param genreMusical de la radio
     */
    public Radio(String contenu, Boolean recommendationMoment,String nom, genreMusical genreMusical) {
        super(contenu, recommendationMoment);
        this.nom = nom;
        this.genreMusical = genreMusical;
    }
}
