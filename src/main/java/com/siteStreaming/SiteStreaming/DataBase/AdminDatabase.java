package com.siteStreaming.SiteStreaming.DataBase;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminDatabase {

    public Connection connection;
    public Statement statement;

    /**
     * Accès à la base de données
     */
    public AdminDatabase(){
        try{
            this.connection = DBManager.getInstance().getConnection();
            this.statement = this.connection.createStatement();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Afficher les n titres les plus écoutés
     */
    public void consulterTitresPlusEcoute(int n) throws SQLException {

        java.sql.ResultSet rs = statement.executeQuery("select * from ContenuSonore limit "+n);

    }
}
