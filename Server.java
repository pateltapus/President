// Import the SQL Server JDBC Driver classes 
import java.sql.*;

class Server
{  
	
	Connection con; 
	
    public Server() {  
       try { 
            // Load the SQLServerDriver class, build the 
            // connection string, and get a connection 
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
            String connectionUrl = "jdbc:sqlserver://YOGIPC:1433;instance=SQLEXPRESS;" + 
                                    "databaseName=President;" + 
                                    "user=player1;" + 
                                    "password=abc123"; 
            con = DriverManager.getConnection(connectionUrl);
            System.out.println("Connected.");

       }  
       catch(Exception e)  
       { 
            System.out.println(e.getMessage()); 
            System.exit(0);  
       } 

    } 

	public void createPlayer(String name){
		try{
			String SQL = "INSERT INTO PresidentTable (Player, Card1Value, Card1Suit, Card2Value, Card2Suit, Card3Value, Card3Suit, Card4Value, Card4Suit, Turn, numCards, PlayerID) VALUES ('" + name + "', -1, -1, -1 ,-1, -1, -1, -1, -1, -1, 13," + (getRows()+1) + ")";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(SQL);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}

    public void sendDataCell(String column, int data, int id){
    	try{
	    	String SQL = "UPDATE PresidentTable SET" + column + "=" + data + "WHERE playerID = " + id;
	    	Statement stmt = con.createStatement();
			stmt.executeUpdate(SQL);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			System.exit(0);
		}
    }

    public int readDataCell(String column, int id){
    	int value = -1;
    	try{
	    	String SQL = "SELECT " + column + " FROM PresidentTable WHERE playerID = " + id;
	    	Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			rs.next();
			value = rs.getInt(1);
			rs.close();
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			System.exit(0);
		}
		return value;
    }
    
    public void clearPresTable(){
    	try{
	    	String SQL = "DELETE FROM PresidentTable";
	    	Statement stmt = con.createStatement();
			stmt.executeUpdate(SQL);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			System.exit(0);
		}
    }

    public void clearHandTable(){
    	try{
	    	String SQL = "DELETE FROM InitialHands";
	    	Statement stmt = con.createStatement();
			stmt.executeUpdate(SQL);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			System.exit(0);
		}
    }
    
    public int getRows(){
    	int value = -1;
    	try{
	    	String SQL = "SELECT COUNT (*) FROM PresidentTable";
	    	Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			rs.next();
			value = rs.getInt(1);
			rs.close();
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			System.exit(0);
		}
		return value;
    	
    }
    
    public void dealCards(){
    	try{
	    	Deck deck = new Deck();
	    	int count = 1;
	    	while(deck.cardsDealt(4) == false){
		    	Card card1 = deck.dealCard();
		    	Card card2 = deck.dealCard();
		    	Card card3 = deck.dealCard();
		    	Card card4 = deck.dealCard();
		    	String SQL = "INSERT INTO InitialHands (Player1CardsNum, Player1CardsSuit, Player2CardsNum, Player2CardsSuit, Player3CardsNum, Player3CardsSuit,Player4CardsNum, Player4CardsSuit, cardNum) VALUES (" + 
		    				  card1.getValue() + "," + card1.getSuit() + "," + card2.getValue() + "," + card2.getSuit() + "," +
		    				  card3.getValue() + "," + card3.getSuit() + "," + card4.getValue() + "," + card4.getSuit() + "," + count + ")";
		    	Statement stmt = con.createStatement();
				stmt.executeUpdate(SQL);
				count++;
			}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			System.exit(0);
		}
    }

    public int getInitCards(int player, String type, int card){
    	int value = -1;
    	try{
	    	String SQL = "SELECT Player" + player + "Cards" + type + " FROM InitialHands WHERE cardNum = " + card;	
	    	Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			rs.next();
			value = rs.getInt(1);
			rs.close();		
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			System.exit(0);
		}

		return value;

    }

}