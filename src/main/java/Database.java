import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;


public class Database{
   public static void main(String args[]) {
      Connection c = null;
      Statement stmt = null;
      try {
         Class.forName("org.postgresql.Driver");
         c = DriverManager
            .getConnection("jdbc:postgresql://db.yfxjoxxaxhaqikyecrro.supabase.co:5432/postgres",
            "postgres", "LazerTagPewPew");
         c.setAutoCommit(false);
         System.out.println("Opened database successfully");

         stmt = c.createStatement();
         String sql = "INSERT INTO PLAYER (id,first_name,last_name,codename) "
          + "VALUES (9, 'Paul', 'Smith', 'brug');";
         //String sql = "DELETE from PLAYER where ID = 9;";
         stmt.executeUpdate(sql);

         ResultSet rs = stmt.executeQuery( "SELECT * FROM PLAYER;" );
         while ( rs.next() ) 
         {
            int id = rs.getInt("id");
            String  first_name = rs.getString("first_name");
            String last_name  = rs.getString("last_name");
            String codename = rs.getString("codename");
            System.out.println( "ID = " + id );
            System.out.println( "FIRST_NAME = " + first_name );
            System.out.println( "LAST_NAME = " + last_name);
            System.out.println( "CODENAME = " + codename);
            System.out.println();
         }
         rs.close();
         stmt.close();
         c.commit();
         c.close();
      } catch (Exception e) {
         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
         System.exit(0);
      }
      System.out.println("Records created successfully");
   }
}