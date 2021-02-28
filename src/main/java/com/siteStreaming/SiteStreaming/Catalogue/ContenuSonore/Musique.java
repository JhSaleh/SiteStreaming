package com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore;

import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Enumérations.genreMusical;

public class Musique extends ContenuSonore {
    /**
     * Titre de la musique
     */
    String titre;
    /**
     * Interprete qui joue la musique
     */
    String interprete;
    /**
     * Date de la musique
     */
    String anneeCreation;
    /**
     * genre musical
     */
     com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Enumérations.genreMusical genreMusical;
    /**
     * Duree de la musique
     */
    int duree;

    /**
     * Constructeur d'une musique
     * @param contenu
     * @param recommendationMoment
     * @param titre
     * @param interprete
     * @param anneeCreation
     * @param genreMusical
     * @param duree
     */
    public Musique(String contenu, Boolean recommendationMoment, String titre,
                   String interprete, String anneeCreation, genreMusical genreMusical,
                   int duree) {
        super(contenu, recommendationMoment);
        this.titre = titre;
        this.interprete = interprete;
        this.anneeCreation = anneeCreation;
        this.genreMusical = genreMusical;
        this.duree=duree;
    }
}
