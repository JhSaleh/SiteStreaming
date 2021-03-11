package com.siteStreaming.SiteStreaming.DataBase;

import com.siteStreaming.SiteStreaming.LoggerSite;

import java.sql.*;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Classe qui lit les informations de connection du config.properties et crée les connection,
 * les autres classes database en héritent.
 */
public class DBManager {
	/**
	 * Instance de la classe que l'on peut récupérer.
	 */
	private static DBManager instance;
	/**
	 * propriétés de la lecture du fichier de config
	 */
	private ResourceBundle properties;
	/**
	 * nom du fichier de config
	 */
	private static String resourceBundle = "config";

	/**
	 * Constructeur de la classe.
	 */
	private DBManager() {
		properties = ResourceBundle.getBundle(resourceBundle);

		try {
			Class.forName(properties.getString("DB_DRIVER"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			LoggerSite.logger.error(e);
		}

	}

	/**
	 * Sert à récupérer une instance de la classe
	 * @return un instance si une existe déjà, une nouvelle crée sinon
	 */
	public static DBManager getInstance() {
		if (instance == null) {
			synchronized (DBManager.class) {
				instance = new DBManager();
			}
		}
		return instance;
	}

	/**
	 * Etablie une connection avec la base de donnée à l'aide des propriétés récupérées.
	 * @return une connection avec la base de donnée si réussi, null sinon
	 */
	public Connection getConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(properties.getString("JDBC_URL"), properties.getString("DB_LOGIN"),
					properties.getString("DB_PASSWORD"));

		} catch (SQLException sqle) {
			LoggerSite.logger.error(sqle);
		}
		return connection;
	}

	/**
	 * Ferme les connexions et les statements qui seraient encore ouverts
	 * @param connection
	 * @param stat
	 * @param rs
	 */
	public void cleanup(Connection connection, Statement stat, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				LoggerSite.logger.error(e);
			}
		}
		if (stat != null) {
			try {
				stat.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				LoggerSite.logger.error(e);
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				LoggerSite.logger.error(e);
			}
		}
	}

	/**
	 * permet de tester la connexion à la DB
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Connection c = DBManager.getInstance().getConnection();
		if (c != null) {
			try {
				System.out.println("Connection to db : " + c.getCatalog());
				Properties p = c.getClientInfo();
				Enumeration<Object> keys = p.keys();
				while (keys.hasMoreElements()) {
					String key = (String) keys.nextElement();
					System.out.println(key + ":" + p.getProperty(key));
				}
				Statement statement = c.createStatement();
				statement.executeUpdate("USE info_team07_schema;"); //selectionne la bonne bdd
				statement.close();

				Statement s2 = c.createStatement();
				//BookDAOImpl.createTableBooks(s2);
				//BookDAOImpl.fillBookTable(s2);

				ResultSet resultSet = s2.executeQuery("select * from `Book`");

				while (resultSet.next()) {
					System.out.println(resultSet.getInt("idBook")+ resultSet.getString("titre")+ resultSet.getString("author"));
				}


			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				DBManager.getInstance().cleanup(c, null, null);
			}
		}
	}
}
