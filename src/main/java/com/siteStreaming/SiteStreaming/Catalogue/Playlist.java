package com.siteStreaming.SiteStreaming.Catalogue;

import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Musique;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    /**
     * Liste des musiques de la playlist
     */
    List<Musique> musique = new ArrayList<>();
    /**
     * Titre de la playlist
     */
    String titre;
    /**
     * Durée totale de la playlist
     */
    int dureeTotale;
    /**
     * Année de création de la playlist
     */
    String anneeCreation;

    /**
     * Constructeur d'une playlist : à sa construction, elle est vide
     * @param titre
     * @param dureeTotale
     * @param anneeCreation
     */
    public Playlist(String titre, int dureeTotale, String anneeCreation) {
        this.titre = titre;
        this.dureeTotale = dureeTotale;
        this.anneeCreation = anneeCreation;
    }

    /**
     * Verifie si une musique est deja dans la playlist
     * @param musique
     * @return
     */
    public boolean musiqueDejaPresent(Musique musique){
        if(this.musique.contains(musique)){
            return true;
        }else{
            return false;
        }
    }
    /**
     * Ajoute un élément du catalogue à la playlist
     * @param musique à voir si podcast autorisé ou que musique comme dans le diagramme de classe
     * @return true si l'element est ajouté si il n'est pas deja dans la playlist, false sinon
     */
    public boolean ajouterElement(Musique musique){
        boolean dejaContenu = musiqueDejaPresent(musique);
        if(dejaContenu) {
            this.musique.add(this.musique.size() - 1, musique);
        }
        return dejaContenu;
    }

    /**
     * Supprime un élément de la playlist si celui ci est présent dans la playlist
     * @param musique musique à supprimer
     * @return true si la musique était dans la playlist, false sinon
     */
    public boolean supprimerElement(Musique musique){
        boolean existe = musiqueDejaPresent(musique);
        if(existe){
            this.musique.remove(musique);
        }
        return existe;
    }

    /**
     * Déplace un élément dans la playlist
     * @param musique à déplacer
     * @param position où la musique doit aller
     * @return true si la musique existait bien dans la playlist, false sinon
     */
    public boolean changerOrdreElement(Musique musique, int position){
        boolean existe = musiqueDejaPresent(musique);
        if(existe){
            int anciennepos = this.musique.indexOf(musique);
            this.musique.add(position,musique);
            this.musique.remove(anciennepos);
        }
        return existe;
    }

    public void enregistrer(){

    }
}
