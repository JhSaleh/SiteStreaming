package com.siteStreaming.SiteStreaming.Catalogue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Musique;
import com.siteStreaming.SiteStreaming.DataBase.PlaylistDatabase;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    /**
     * id de la playlist si est lu depuis la base de donnée
     */
    int idPlaylist = -1;
    /**
     * id du compte client associé à la playlist
     */
    int idCompteClient;
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

    public int getIdPlaylist() {
        return idPlaylist;
    }

    public void setIdPlaylist(int idPlaylist) {
        this.idPlaylist = idPlaylist;
    }

    public int getIdCompteClient() {
        return idCompteClient;
    }

    public void setIdCompteClient(int idCompteClient) {
        this.idCompteClient = idCompteClient;
    }

    public List<Musique> getMusique() {
        return musique;
    }

    public void setMusique(List<Musique> musique) {
        this.musique = musique;
        majDuree();
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getDureeTotale() {
        return dureeTotale;
    }

    public void setDureeTotale(int dureeTotale) {
        this.dureeTotale = dureeTotale;
    }

    public String getAnneeCreation() {
        return anneeCreation;
    }

    public void setAnneeCreation(String anneeCreation) {
        this.anneeCreation = anneeCreation;
    }

    /**
     * Constructeur d'une playlist : à sa construction, elle est vide
     * @param mail
     * @param titre
     * @param dureeTotale
     * @param anneeCreation
     */
    public Playlist(String mail, String titre, int dureeTotale, String anneeCreation) {
        PlaylistDatabase playlistDatabase = new PlaylistDatabase();
        this.idCompteClient = playlistDatabase.getIdClient(mail);
        this.titre = titre;
        this.dureeTotale = dureeTotale;
        this.anneeCreation = anneeCreation;
    }

    /**
     * Recalcule la duree totale de la plylist de la base, en cas de problèmes (normalement
     * les durées se mettent à jour toutes seules lorsqu'on ajoute ou supprime des musiques à la main mais
     * on en a besoin si on ajoute une liste directement avec setMusique)
     */
    public void majDuree(){
        if(this.musique.size()==0){
            this.dureeTotale=0;
        }else{
            this.dureeTotale = 0;
            for(int i=0;i<this.musique.size();i++){
                this.dureeTotale = this.dureeTotale + this.musique.get(i).getDuree();
            }
        }
    }
    /**
     * Verifie si une musique est deja dans la playlist
     * @param musique
     * @return
     */
    public boolean musiqueDejaPresent(Musique musique){
        if(musique.getId()!=-1){
            for(int i=0;i<this.musique.size();i++){
                if(this.musique.get(i).getId()!=-1 && this.musique.get(i).getId()==musique.getId()){
                    return true;
                }
            }
        }
        if(this.musique.contains(musique)){

            return true;
        }else{
            return false;
        }
    }
    /**
     * Ajoute un élément du catalogue à la playlist
     * !! pour que le changement soit fait dans la base de donnée, il faut appeler enregistrerContenuPlaylist
     * de PlaylistDatabase ensuite!!
     * @param musique à voir si podcast autorisé ou que musique comme dans le diagramme de classe
     * @return true si l'element est ajouté si il n'est pas deja dans la playlist, false sinon
     */
    public boolean ajouterElement(Musique musique){
        boolean dejaContenu = musiqueDejaPresent(musique);
        if(!dejaContenu) {
            this.musique.add(this.musique.size(), musique);
            this.dureeTotale = this.dureeTotale + musique.getDuree();
        }
        return dejaContenu;
    }

    /**
     * Supprime un élément de la playlist si celui ci est présent dans la playlist
     * !! pour que le changement soit fait dans la base de donnée, il faut appeler enregistrerContenuPlaylist
     * de PlaylistDatabase ensuite!!
     * @param musique musique à supprimer
     * @return true si la musique était dans la playlist, false sinon
     */
    public boolean supprimerElement(Musique musique){
        boolean existe = musiqueDejaPresent(musique);
        if(existe){
            this.musique.remove(musique);
            this.dureeTotale = this.dureeTotale - musique.getDuree();
        }
        return existe;
    }

    /**
     * Déplace un élément dans la playlist
     * !! pour que le changement soit fait dans la bes de donnée, il faut appeler enregistrerContenuPlaylist
     * de PlaylistDatabase ensuite!!
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

    public String toJson(){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void enregistrer(){

    }

}
