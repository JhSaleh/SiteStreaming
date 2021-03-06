package com.siteStreaming.SiteStreaming.DataBase;

import com.siteStreaming.SiteStreaming.Acceuil.CompteClient;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
                    System.out.println("Succes finding the client !");
                    return new CompteClient(nom, prenom, civilite, email, mdp, dateNaissance, addresseFacturation, styleMusique);
                }

                return null;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            System.out.println(addToCompte);
            this.statement.executeUpdate(addToCompte);

            this.statement.executeUpdate("insert into `CompteClient` values(NULL,"+compte.getMailD()+","+compte.getAddressD()+","+compte.getStyleMusiqueD()+");");
            System.out.println("Client ajouté à la bdd!");
            return true;
        } catch (SQLException e){
            System.out.println("Client non ajouté à la bdd!");
            e.printStackTrace();
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
        System.out.println(modifyToCompte);
        this.statement.executeUpdate(modifyToCompte);

        this.statement.executeUpdate("update `CompteClient` set adresseFacturation="+compte.getAddressD()+",  styleMusique="+compte.getStyleMusiqueD()+" where adresseMailClient="+compte.getMailD()+";");
        System.out.println("Informations client modifié dans la bdd!");
        return true;
        } catch (SQLException e){
            System.out.println("Informations client modifié dans la bdd!");
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args){
        //Tests
        ClientDatabase clientDatabase = new ClientDatabase();
        CompteClient compteClient = clientDatabase.getCompteClient("harryJ@gmail.com");
        compteClient.setNom("Instagram");
        clientDatabase.modifyClientAccount(compteClient);

        compteClient.displayInformation();

    }

}
