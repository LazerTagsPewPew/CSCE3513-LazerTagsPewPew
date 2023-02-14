package teamseven.lasertag;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private final String url = "jdbc:postgresql://db.yfxjoxxaxhaqikyecrro.supabase.co";
    private final String userName = "postgres";
    private final String passWord = "LazerTagPewPew";

    public Connection connect() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url, userName, passWord);
            System.out.println("Connected to the database...");
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return connection;
    }
}