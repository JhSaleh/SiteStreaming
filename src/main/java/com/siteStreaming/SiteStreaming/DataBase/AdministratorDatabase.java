package com.siteStreaming.SiteStreaming.DataBase;
import com.siteStreaming.SiteStreaming.Accueil.CompteAdmin;
import com.siteStreaming.SiteStreaming.LoggerSite;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdministratorDatabase {
    public Connection connection;
    public Statement statement;


    /**
     * Crée une connection avec la base de données
     */
    public AdministratorDatabase(){
        try{
            this.connection = DBManager.getInstance().getConnection();
            this.statement = this.connection.createStatement();
        } catch (SQLException e){
            LoggerSite.logger.error(e);
        }
    }



    /**
     * Vérifie si un client est présent dans une bdd
     * @param mail
     * @return
     */
    public boolean isAdminInDatabase(String mail){
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
                LoggerSite.logger.debug("Tte les infos clients : "+ answer[0] +answer[1] + answer[3] + answer[4] + " etc.");
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
     * Renvoie le compte client s'il existe pour un mail donné
     * @param mail
     * @return
     */
    public CompteAdmin getCompteAdmin(String mail){
        String nom, prenom, civilite, email, mdp, dateNaissance, isProfilManagerClient;
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

                String query2 = "SELECT * FROM CompteAdmin where adresseMailAdmin ="+mail+";";
                ResultSet res2 = this.statement.executeQuery(query2);
                if (res2.next()){
                    isProfilManagerClient = res2.getString("isProfilManagerClient");
                    LoggerSite.logger.info("Succes finding the admin !");
                    return new CompteAdmin(nom, prenom, civilite, email, mdp, dateNaissance, isProfilManagerClient);
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
    public boolean addAdminAccount(CompteAdmin compte){
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

            this.statement.executeUpdate("insert into `CompteAdmin` values(NULL,"+compte.getMailD()+","+compte.getIsProfilManagerClientD()+");");
            LoggerSite.logger.info("Admin ajouté à la bdd!");
            return true;
        } catch (SQLException e){
            LoggerSite.logger.error("Admin non ajouté à la bdd!");
            LoggerSite.logger.error(e);
            return false;
        }
    }

    public static void main(String[] args){
        //Tests
        AdministratorDatabase administratorDatabase = new AdministratorDatabase();
        CompteAdmin compteAdmin = new CompteAdmin("Josh", "ManageClient", "Monsieur", "joshS@gmail.com", "fjYn7p6yBcwWaPp", "1988-12-02", "true");
        administratorDatabase.addAdminAccount(compteAdmin);
        CompteAdmin compteAdmin2 = new CompteAdmin("Tyler", "ManageMusicLibrary", "Monsieur", "joshS2@gmail.com", "fjYn7p6yBcwWaPp", "1988-12-02", "false");
        administratorDatabase.addAdminAccount(compteAdmin2);
    }

}
