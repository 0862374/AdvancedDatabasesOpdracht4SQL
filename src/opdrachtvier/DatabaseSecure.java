package opdrachtvier;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSecure {
	final String DB_NAME = "OpdrachtDrie";
	final String DB_URL = "jdbc:postgresql://localhost:5432/" + DB_NAME;

	// Database accountinstellingen
	final String USER = "";
	final String PASS = "";
	public Connection conn = null;
	public Statement stmt = null;

	public DatabaseSecure() {
		try {
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Connecting to database failed...");
			e.printStackTrace();

		}
	}

}
