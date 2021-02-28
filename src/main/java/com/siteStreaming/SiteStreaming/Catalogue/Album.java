package com.siteStreaming.SiteStreaming.Catalogue;

import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Musique;

import java.util.List;

/**
 * Classe qui représente un album de musique, c'est-à-dire un ensemble de musique avec des informetions
 * dessus
 */
public class Album {
    /**
     * Liste des musiques de l'album
     */
    List<Musique> musiques;
    /**
     * Nom de l'album
     */
    String nom;
    /**
     * Interprete qui joue la musique de l'album
     */
    String interprete;
    /**
     * Date de l'album
     */
    String anneeCreation;
    /**
     * Duree totale de l'album
     */
    int duree;

    /**
     * Constructeur d'un album
     * @param musiques
     * @param nom
     * @param interprete
     * @param anneeCreation
     * @param duree
     */
    public Album(List<Musique> musiques, String nom, String interprete, String anneeCreation, int duree) {
        this.musiques = musiques;
        this.nom = nom;
        this.interprete = interprete;
        this.anneeCreation = anneeCreation;
        this.duree = duree;
    }
}
