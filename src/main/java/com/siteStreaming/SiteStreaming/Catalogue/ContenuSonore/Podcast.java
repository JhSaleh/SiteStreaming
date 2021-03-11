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


    //Getter et Setter
    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Enumérations.categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Enumérations.categorie categorie) {
        this.categorie = categorie;
    }

    /**
     * Constructeur d'un podcast
     * @param contenu lien vers le podcast renseignée
     * @param recommendationMoment booléen qui indique si le contenu fait partie des recommendations du moment
     * @param titre du podcast
     * @param duree du podcast
     * @param auteur du podcast
     * @param categorie du podcast
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
