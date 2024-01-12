package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class ConnectionProvider {
	private static final String URL = "jdbc:sqlserver://localhost;databasename=PATE_DOR;trustservercertificate=true";
	
	public static Connection getConnection() throws DALException {
		try {
			return DriverManager.getConnection(URL, System.getenv("USER_SQLSERVER"), System.getenv("PASSWORD_SQLSERVER"));
		} catch (SQLException e) {
			throw new DALException("Echec de la connexion à la BDD :\n" + e.getMessage(), e);
		}
	}
}
