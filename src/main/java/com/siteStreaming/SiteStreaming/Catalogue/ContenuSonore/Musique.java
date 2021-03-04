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

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getInterprete() {
        return interprete;
    }

    public void setInterprete(String interprete) {
        this.interprete = interprete;
    }

    public String getAnneeCreation() {
        return anneeCreation;
    }

    public void setAnneeCreation(String anneeCreation) {
        this.anneeCreation = anneeCreation;
    }

    public com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Enumérations.genreMusical getGenreMusical() {
        return genreMusical;
    }

    public void setGenreMusical(com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Enumérations.genreMusical genreMusical) {
        this.genreMusical = genreMusical;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

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
