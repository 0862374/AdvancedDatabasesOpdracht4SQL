package opdrachtvier;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSecure {

    final String DB_NAME = "opdrachtviersql";
    final String DB_URL = "jdbc:postgresql://localhost:5432/" + DB_NAME;

    // Database accountinstellingen
    String USER = "";
    String PASS = "1234";
    public Connection conn = null;
    public Statement stmt = null;

    public DatabaseSecure(String usr) {
        try {
            System.out.println("Connecting to database...");
            USER = usr;
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            System.out.println("You are now connected to the database!");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("Connecting to database failed...");
            e.printStackTrace();

        }
    }

}
