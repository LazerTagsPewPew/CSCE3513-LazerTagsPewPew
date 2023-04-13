package teamseven.lasertag;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

//Saves changes to database after program shuts down.
public class Database
{
   public Connection c = null;
   public Statement stmt = null;
   public ArrayList<Player> list = new ArrayList<>();
   public boolean openDatabase() 
   {
      try 
      {
         Class.forName("org.postgresql.Driver");
         c = DriverManager
            .getConnection("jdbc:postgresql://db.yfxjoxxaxhaqikyecrro.supabase.co:5432/postgres",
            "postgres", "LazerTagPewPew");
         c.setAutoCommit(true);
         System.out.println("Opened database successfully");
         return true;
      } 
      catch (Exception e) 
      {
         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
         System.exit(0);
         return false;
      }
   }

   public boolean createRecord(int id, String fname, String lname, String epicname)
   {
      try 
      {
         stmt = c.createStatement();

         String sql = "INSERT INTO PLAYER (id,first_name,last_name,codename) "
          + "VALUES (" + id + ", '" + fname + "', '" + lname + "', '" + epicname + "');";
         stmt.executeUpdate(sql);
         Player newPlayer = new Player(id, fname, lname, epicname);
         list.add(newPlayer);

         System.out.println("Record created successfully");
         //c.commit();
         return true;
      }
      catch (Exception e) 
      {
         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
         System.exit(0);
         return false;
      }  
   }

   public boolean readRecords()
   {
      try 
      {
         stmt = c.createStatement();
         ResultSet rs = stmt.executeQuery( "SELECT * FROM PLAYER;" );
         list.clear();
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
            Player tempplayer = new Player(id, first_name, last_name, codename);
            list.add(tempplayer);
         }
         rs.close();
         return true;
      }
      catch (Exception e) 
      {
         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
         System.exit(0);
         return false;
      } 
   }

   public boolean deleteRecord(int id, String fname, String lname, String epicname)
   {
      try 
      {
         stmt = c.createStatement();
         String sql = "DELETE from PLAYER where ID = " + id + ";";
         stmt.executeUpdate(sql);
         Player deletedPlayer = new Player(id, fname, lname, epicname);
         list.remove(deletedPlayer);

         System.out.println("Record deleted successfully");
         //c.commit();
         return true;
      }
      catch (Exception e) 
      {
         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
         System.exit(0);
         return false;
      }  
   }

   public String inTable(int id)
   {
      String returnString = null;
      for (int i = 0; i < list.size(); i++)
      {
         System.out.println(list.get(i).id_num);
         if (list.get(i).id_num == id)
         {
            System.out.println("Yes, " + id + " is in the table");
            returnString = list.get(i).codename;
            return returnString;
         }
      }
      System.out.println("No, " + id + " is not in the table");
      return returnString;
   }

   public boolean closeDatabase()
   {
      try 
      {
         list.clear();
         stmt.close();
         //c.commit();
         c.close();
         return true;
      }
      catch (Exception e) 
      {
         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
         System.exit(0);
         return false;
      }
      
   }

}
