package com.siteStreaming.SiteStreaming.Acceuil;

import com.siteStreaming.SiteStreaming.DataBase.S;

public class CompteAdmin {
    private String nom;
    private String prenom;
    private String civilite;
    private String mail;
    private String password;
    private String birthDate;
    private String isProfilManagerClient;

    public CompteAdmin(String inNom, String inPrenom, String inCivilite, String inMail, String inPassword, String inBirthDate, String inIsProfilManagerClient){
        this.nom = inNom;
        this.prenom = inPrenom;
        this.civilite = inCivilite;
        this.mail = inMail;
        this.password = inPassword;
        this.birthDate = inBirthDate;
        this.isProfilManagerClient = inIsProfilManagerClient;
    }

    //Setter
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setCivilite(String civilite) {
        this.civilite = civilite;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setIsProfilManagerClient(String isProfilManagerClient) {
        this.isProfilManagerClient = isProfilManagerClient;
    }

    //Getter
    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getCivilite() {
        return civilite;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getIsProfilManagerClient() {
        return isProfilManagerClient;
    }

    //VersionDatabase
    public String getNomD() {
        return S.c(nom);
    }

    public String getPrenomD() {
        return S.c(prenom);
    }

    public String getCiviliteD() {
        return S.c(civilite);
    }

    public String getMailD() {
        return S.c(mail);
    }

    public String getPasswordD() {
        return S.c(password);
    }

    public String getBirthDateD() { return S.c(birthDate);}

    public String getIsProfilManagerClientD() {
        return S.c(isProfilManagerClient);
    }

    /**
     * Vérifie si un certain mot de passe est celui associé au compte admin
     * @param password
     * @return
     */
    public boolean isPassWord(String password){
        return this.getPassword().equals(password);
    }

    /**
     * Affiche les informations liées au compte client
     */
    public void displayInformation(){
        System.out.println("Nom :"+nom);
        System.out.println("Prenom :"+prenom);
        System.out.println("Civilite :"+civilite);
        System.out.println("AdresseMail :"+mail);
        System.out.println("MotDePasse :"+password);
        System.out.println("DateNaissance :"+birthDate);
        System.out.println("AdresseFacturation :"+isProfilManagerClient);
    }


}
