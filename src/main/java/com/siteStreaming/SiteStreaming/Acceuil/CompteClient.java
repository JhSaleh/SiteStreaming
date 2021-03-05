package com.siteStreaming.SiteStreaming.Acceuil;

import com.siteStreaming.SiteStreaming.DataBase.ClientDatabase;
import com.siteStreaming.SiteStreaming.DataBase.S;

public class CompteClient {
    private String nom;
    private String prenom;
    private String civilite;
    private String mail;
    private String password;
    private String birthDate;
    private String address;

    public CompteClient(String inNom, String inPrenom, String inCivilite, String inMail, String inPassword, String inBirthDate, String inAddress){
        this.nom = inNom;
        this.prenom = inPrenom;
        this.civilite = inCivilite;
        this.mail = inMail;
        this.password = inPassword;
        this.birthDate = inBirthDate;
        this.address = inAddress;
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

    public void setAddress(String address) {
        this.address = address;
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

    public String getAddress() {
        return address;
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

    public String getAddressD() {
        return S.c(address);
    }


    /**
     * Ajoute un compte client à la base de donnée
     * @param compteClient
     */
    public boolean addToDatabase(CompteClient compteClient){
        ClientDatabase clientDatabase = new ClientDatabase(); //Créé une connexion avec la bdd
        return clientDatabase.addClientAccount(compteClient);
    }


}
