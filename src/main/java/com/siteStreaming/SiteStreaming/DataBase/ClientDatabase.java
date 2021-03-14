package com.siteStreaming.SiteStreaming.DataBase;

import com.siteStreaming.SiteStreaming.Accueil.CompteClient;
import com.siteStreaming.SiteStreaming.LoggerSite;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Classe qui sert la gestion des comptes clients dans la base de donnée.
 */
public class ClientDatabase {
    public Connection connection;
    public Statement statement;


    /**
     * Crée une connection avec la base de données
     */
    public ClientDatabase(){
        try{
            this.connection = DBManager.getInstance().getConnection();
            this.statement = this.connection.createStatement();
        } catch (SQLException e){
            LoggerSite.logger.error(e);
        }
    }

    /**
     * A utilisé si la table Compte est supprimée
     */
    public void createAccountTable(){
        try {
            this.statement.executeUpdate("DROP TABLE IF EXISTS `Compte`;");
            //mettre dernière version
        } catch (SQLException e){
            LoggerSite.logger.error(e);
        }

    }

    /**
     * A utiliser seulement si la table CompteClient est supprimée
     */
    public void createClientTable(){
        try {
            this.statement.executeUpdate("DROP TABLE IF EXISTS `CompteClient`;");
            //mettre dernière version
        } catch (SQLException e){
            LoggerSite.logger.error(e);
        }
    }

    /**
     * Vérifie si un client est présent dans une bdd
     * @param mail
     * @return
     */
    public boolean isClientInDatabase(String mail){
        try{
            mail = S.cd(mail);
            String query = "SELECT * FROM CompteClient where adresseMailClient="+mail+";";
            ResultSet res = this.statement.executeQuery(query);
            if(res.next()){ //S'il y a un élément dans le résultat, c'est que le client est présent dans la bdd
                return true;
            } else {
                return false;
            }
        } catch(SQLException e){
            LoggerSite.logger.error(e);
        }
        return false;
    }

    /**
     * Renvoit le nom et prénom d'un client
     * @param mail
     * @return
     */
    public String[] getFirstNameLastName(String mail){
        try {
            mail = "'"+mail+"'";

            String query = "SELECT * FROM Compte where adresseMail ="+mail+";";
            ResultSet res = this.statement.executeQuery(query);
            if (res.next()) { //S'il y a un élément dans le résultat, c'est que le client est présent dans la bdd
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                String[] answer = {nom, prenom};
                return answer;
            } else {
                return null;
            }
        } catch (SQLException e){
            LoggerSite.logger.error(e);
        }
        return null;
    }

    public String[] getAllClientInformation(String mail){
        try {
            mail = "'"+mail+"'";

            String query = "SELECT * FROM Compte where adresseMail ="+mail+";";
            ResultSet res = this.statement.executeQuery(query);
            if (res.next()) { //S'il y a un élément dans le résultat, c'est que le client est présent dans la bdd
                String[] answer = {res.getString("adresseMail"), res.getString("civilite"), res.getString("nom"), res.getString("prenom"), res.getString("motDePasse"), res.getString("dateNaissance")};
                //System.out.println("Tte les infos clients : "+ answer[0] +answer[1] + answer[3] + answer[4] + " etc.");
                return answer;
            } else {
                return null;
            }
        } catch (SQLException e) {
            LoggerSite.logger.error(e);
        }
        return null;
    }

    /**
     * Renvoit le compte client s'il existe pour un mail donné
     * @param mail
     * @return
     */
    public CompteClient getCompteClient(String mail){
        String nom, prenom, civilite, email, mdp, dateNaissance, addresseFacturation, styleMusique;
        try{
            mail = "'"+mail+"'";

            String query = "SELECT * FROM Compte where adresseMail ="+mail+";";
            ResultSet res = this.statement.executeQuery(query);
            if (res.next()) { //S'il y a un élément dans le résultat, c'est que le client est présent dans la bdd

                //Récupération des élèments de compte client
                nom = res.getString("nom");
                prenom = res.getString("prenom");
                civilite = res.getString("civilite");
                email = res.getString("adresseMail");
                mdp = res.getString("motDePasse");
                dateNaissance = res.getString("dateNaissance");

                String query2 = "SELECT * FROM CompteClient where adresseMailClient ="+mail+";";
                ResultSet res2 = this.statement.executeQuery(query2);
                if (res2.next()){
                    addresseFacturation = res2.getString("adresseFacturation");
                    styleMusique = res2.getString("styleMusique");
                    LoggerSite.logger.debug("Succes finding the client !");
                    return new CompteClient(nom, prenom, civilite, email, mdp, dateNaissance, addresseFacturation, styleMusique);
                }

                return null;
            } else {
                return null;
            }
        } catch (SQLException e) {
            LoggerSite.logger.error(e);
        }
        return null;
    }

    /**
     * Ajoute un client à la base de données
     * Attention, cela ne vérifie pas s'il y a respect de la clé primaire, i.e. un compte = un mail
     * @param compte
     * @return boolean indiquant le succès de l'opération
     */
    public boolean addClientAccount(CompteClient compte){
        try {
            String addToCompte = "insert into `Compte` values("+compte.getMailD()+","+
                            compte.getCiviliteD()+","+
                            compte.getNomD()+","+
                            compte.getPrenomD()+","+
                            compte.getPasswordD()+","+
                            compte.getBirthDateD()+
                            ");";
            LoggerSite.logger.debug(addToCompte);
            this.statement.executeUpdate(addToCompte);

            this.statement.executeUpdate("insert into `CompteClient` values(NULL,"+compte.getMailD()+","+compte.getAddressD()+","+compte.getStyleMusiqueD()+");");
            LoggerSite.logger.info("Client ajouté à la bdd!");
            return true;
        } catch (SQLException e){
            LoggerSite.logger.error("Client non ajouté à la bdd!");
            LoggerSite.logger.error(e);
            return false;
        }
    }

    /**
     *Modifie le compte d'un client pour un mail donné
     * @param compte
     * @return
     */
    public boolean modifyClientAccount(CompteClient compte){
        try {
            String modifyToCompte = "update `Compte` "+
                    "set civilite="+compte.getCiviliteD()+
                    ", nom="+compte.getNomD()+
                    ", prenom="+compte.getPrenomD()+
                    ", motDePasse="+compte.getPasswordD()+
                    ", dateNaissance="+compte.getBirthDateD()+
                    " where adresseMail="+compte.getMailD()+
                    ";";
            LoggerSite.logger.debug(modifyToCompte);
            this.statement.executeUpdate(modifyToCompte);

            this.statement.executeUpdate("update `CompteClient` set adresseFacturation="+compte.getAddressD()+",  styleMusique="+compte.getStyleMusiqueD()+" where adresseMailClient="+compte.getMailD()+";");
            LoggerSite.logger.info("Informations client modifié dans la bdd!");
            return true;
        } catch (SQLException e){
            LoggerSite.logger.error("Informations client modifié dans la bdd!");
            LoggerSite.logger.error(e);
            return false;
        }
    }

    public String checkIfNull(String string){
        if(string == null || string == ""){
            return "null";
        } else {
            return  string;
        }
    }

    /**
     * Recherche des clients dans la base de données basé sur les informations rentrées
     * @param nomSearch
     * @param prenomSearch
     * @param emailSearch
     * @return
     */
    public ArrayList<CompteClient> searchClient(String nomSearch, String prenomSearch, String emailSearch){
        nomSearch = checkIfNull(nomSearch);
        prenomSearch = checkIfNull(prenomSearch);
        emailSearch = checkIfNull(emailSearch);

        nomSearch = S.c("%"+nomSearch+"%");
        prenomSearch = S.c("%"+prenomSearch+"%");
        emailSearch = S.c("%"+emailSearch+"%");
        String nom, prenom, civilite, email, mdp, dateNaissance, addresseFacturation, styleMusique;
        ArrayList<CompteClient> resultList;

        try{
            String query = "SELECT * FROM Compte JOIN CompteClient ON Compte.adresseMail=adresseMailClient" +
                           " WHERE Compte.nom LIKE "+nomSearch+"OR Compte.prenom LIKE"+prenomSearch+"OR Compte.adresseMail LIKE"+emailSearch+";";


          LoggerSite.logger.debug(query);
          ResultSet res = this.statement.executeQuery(query);
          CompteClient compte;
          resultList = new ArrayList<>();
          while(res.next()){
              nom = res.getString("nom");
              prenom = res.getString("prenom");
              civilite = res.getString("civilite");
              email = res.getString("adresseMail");
              mdp = res.getString("motDePasse");
              dateNaissance = res.getString("dateNaissance");
              addresseFacturation = res.getString("adresseFacturation");
              styleMusique = res.getString("styleMusique");
              compte = new CompteClient(nom, prenom, civilite, email, mdp, dateNaissance, addresseFacturation, styleMusique);
              LoggerSite.logger.debug("-------------");
              compte.displayInformation();
              resultList.add(compte);
          }
          return resultList;

        } catch (SQLException e){
            LoggerSite.logger.error(e);
        }
        return null;
    }

    /**
     * Ferme la connection active avec la base de données.
     */
    public void closeConnection(){
        try {
            this.connection.close();
        } catch(SQLException e){
            LoggerSite.logger.error(e);
        }
    }

    public static void main(String[] args){
        //Tests
        ClientDatabase clientDatabase = new ClientDatabase();
        clientDatabase.searchClient(null, null, "yahoo");

    }

}
