package com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore;

/**
 * Classe dont héritent de Musique, Radio et Podcast
 */
public abstract class ContenuSonore {
    /**
     * fichier audio correspondant, lien vers le fichier audio
     * ou le fichier binaire (ici on ne gère pas cette partie là)
     */
    String contenu;
    /**
     * Si le contenu est une recommendation du moment
     */
    Boolean recommendationMoment;

    void ajoutPlaylist(){};
    void majStatistique(){};

    /**
     * Constructeur d'un contenu sonore
     * @param contenu
     * @param recommendationMoment
     */
    public ContenuSonore(String contenu, Boolean recommendationMoment) {
        this.contenu = contenu;
        this.recommendationMoment = recommendationMoment;
    }
}

