// Import the SQL Server JDBC Driver classes 
import java.sql.*;

class Server
{  
    public Server() {  
       try { 
            // Load the SQLServerDriver class, build the 
            // connection string, and get a connection 
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
            String connectionUrl = "jdbc:sqlserver://YOGIPC:1433;instance=SQLEXPRESS;" + 
                                    "databaseName=President;" + 
                                    "user=player1;" + 
                                    "password=abc123"; 
            Connection con = DriverManager.getConnection(connectionUrl);
            System.out.println("Connected.");

            // Create and execute an SQL statement that returns some data.  
            String SQL = "SELECT * FROM PresidentTable";  
            Statement stmt = con.createStatement();  
            ResultSet rs = stmt.executeQuery(SQL);

            // Iterate through the data in the result set and display it.  
            while (rs.next())  
            {  
               System.out.println(rs.getString(1) + " " + rs.getString(2)
            		   + " " + rs.getString(3) + " " + rs.getString(4)
            		   + " " + rs.getString(5) + " " + rs.getString(6)
            		   + " " + rs.getString(7) + " " + rs.getString(8)
            		   + " " + rs.getString(9) + " " + rs.getString(10));  
            }

       }  
       catch(Exception e)  
       { 
            System.out.println(e.getMessage()); 
            System.exit(0);  
       } 
    } 

    public void sendDataCol(String column){
    	
    }

    public void readDataCol(String column){

    }

    public void sendDataCell(int cell){

    }

    public void readDataCell(int cell){
    	String SQL = "SELECT "
    }
}