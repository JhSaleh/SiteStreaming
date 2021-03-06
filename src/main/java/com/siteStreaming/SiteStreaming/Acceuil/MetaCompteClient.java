package com.siteStreaming.SiteStreaming.Acceuil;

import com.siteStreaming.SiteStreaming.DataBase.S;

/**
 * Classe utilisé exclusivement dans les jsp
 */
public class MetaCompteClient {
    private CompteClient compte;
    private String defaultStringAnswer = "\"\""; //Correspond à la valeur par default des champs input

    public MetaCompteClient(CompteClient inCompte){
        this.compte = inCompte;
    }

    //Getter version jsp

    /**
     * Renvoit le champs nom si l'objet existe, sinon un string vide
     * Version a utilisé dans une page jsp
     * @return
     */
    public String getNom(){
        if(compte == null){
            return defaultStringAnswer;
        }
        return S.cd(compte.getNom());
    }

    /**
     * Renvoit le champs prenom si l'objet existe, sinon un string vide
     * Version a utilisé dans une page jsp
     * @return
     */
    public String getPrenom() {
        if(this.compte == null){
            return defaultStringAnswer;
        }
        return S.cd(compte.getPrenom());
    }

    /**
     * Renvoit le champs civilite si l'objet existe, sinon un string vide
     * Version a utilisé dans une page jsp
     * @return
     */
    public String getCivilite() {
        if(this.compte == null){
            return defaultStringAnswer;
        }
        return S.cd(compte.getCivilite());
    }

    /**
     * Renvoit le champs mail si l'objet existe, sinon un string vide
     * Version a utilisé dans une page jsp
     * @return
     */
    public String getMail() {
        if(this.compte == null){
            return defaultStringAnswer;
        }
        return S.cd(compte.getMail());
    }

    /**
     * Renvoit le champs password si l'objet existe, sinon un string vide
     * Version a utilisé dans une page jsp
     * @return
     */
    public String getPassword() {
        if(this.compte == null){
            return defaultStringAnswer;
        }
        return S.cd(compte.getPassword());
    }

    /**
     * Renvoit le champs birthDate si l'objet existe, sinon un string vide
     * Version a utilisé dans une page jsp
     * @return
     */
    public String getBirthDate() {
        if(this.compte == null){
            return defaultStringAnswer;
        }
        return S.cd(compte.getBirthDate());
    }

    /**
     * Renvoit le champs address si l'objet existe, sinon un string vide
     * Version a utilisé dans une page jsp
     * @return
     */
    public String getAddress() {
        if(this.compte == null){
            return defaultStringAnswer;
        }
        return S.cd(compte.getAddress());
    }

    /**
     * Renvoit le champs styleMusique si l'objet existe, sinon un string vide
     * Version a utilisé dans une page jsp
     * @return
     */
    public String getStyleMusique(){
        if(this.compte == null){
            return defaultStringAnswer;
        }
        return  S.cd(compte.getStyleMusique());
    }


}
