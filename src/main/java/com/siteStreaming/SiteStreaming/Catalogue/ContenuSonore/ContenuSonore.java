package com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore;

/**
 * Classe dont héritent de Musique, Radio et Podcast
 */
public abstract class ContenuSonore {
    /**
     * Si le contenu vient de la base de donnée
     */
    int id = -1;
    /**
     * fichier audio correspondant, lien vers le fichier audio
     * ou le fichier binaire (ici on ne gère pas cette partie là)
     */
    String contenu;
    /**
     * Si le contenu est une recommendation du moment
     */
    Boolean recommendationMoment;
    /**
     * Si le contenu est un morceau populaire
     */
    Boolean morceauPopulaire = false;
    /**
     * nombre de lecture du contenu durant le mois en cours
     */
    int nbLectureMois = 0;
    /**
     * nombre de lecture du contenu
     */
    int nbLectureTotal = 0;


    void ajoutPlaylist(){};
    void majStatistique(){};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Boolean getRecommendationMoment() {
        return recommendationMoment;
    }

    public void setRecommendationMoment(Boolean recommendationMoment) {
        this.recommendationMoment = recommendationMoment;
    }

    public Boolean getMorceauPopulaire() {
        return morceauPopulaire;
    }

    public void setMorceauPopulaire(Boolean morceauPopulaire) {
        this.morceauPopulaire = morceauPopulaire;
    }

    public int getNbLectureMois() {
        return nbLectureMois;
    }

    public void setNbLectureMois(int nbLectureMois) {
        this.nbLectureMois = nbLectureMois;
    }

    public int getNbLectureTotal() {
        return nbLectureTotal;
    }

    public void setNbLectureTotal(int nbLectureTotal) {
        this.nbLectureTotal = nbLectureTotal;
    }

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

