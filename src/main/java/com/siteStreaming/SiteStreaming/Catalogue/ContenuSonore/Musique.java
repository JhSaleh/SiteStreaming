package com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Enumérations.genreMusical;

/**
 * Classe correspondant à une musique, contient des champs equivalents à ceux de la base de donnée,
 * hérite de contenu sonore.
 */
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
    static com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Enumérations.genreMusical genreMusical;
    /**
     * Duree de la musique
     */
    int duree;

    //Getter et Setter
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
     * @param contenu lien vers la musique renseignée
     * @param recommendationMoment booléen qui indique si le contenu fait partie des recommendations du moment
     * @param titre de la musique
     * @param interprete de la musique
     * @param anneeCreation de la musique
     * @param genreMusical de la musique
     * @param duree de la musique
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

    public String musToJson(){
        ObjectMapper mapper = new ObjectMapper();
        try {
                return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args){
        Musique m = new Musique("linktothemusic", false, "Melodie du soir", "joueur de piano"
                , "2 mars 4000", genreMusical.pop,100);
        System.out.println(m.musToJson());
    }
}
