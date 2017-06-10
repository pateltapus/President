import java.util.Arrays;
import java.util.ArrayList;


public class President{

	private static Player playerList[];

	public static void main(String args[]){
		
		
		PresidentGUI newGame = new PresidentGUI();
		//Get names into an ArrayList
		ArrayList<String> names = new ArrayList<String>();
/*		names.add("Joe");
		names.add("Sam");
		names.add("Linda");
		names.add("Bill");*/
		Server sqlServer = new Server();
		//System.out.println("FLAG1");
		sqlServer.createPlayer(newGame.getName());
		int orderNum = sqlServer.getRows();
		System.out.println("FLAG2");

		if(sqlServer.getRows() == 1){
			sqlServer.dealCards();
		}
		System.out.println("FLAG3");

		if(sqlServer.getRows()<4){
			newGame.createWaitScreen();
			while(sqlServer.getRows()<4){
				newGame.updateWaitScreen();
			}
			newGame.closeWaitScreen();
		}

		Player currPlayer = new Player();
		for(int i = 0; i < 13; i++){
			currPlayer.getHand().addCard(new Card())
		}


		
		
		
		
/*		
		PSEUDOCODE FOR SQL COMMUNICATION AND GAMESTATE: 

		Server sqlServer = new Server();
		send newGame.getName() to sql

		
		if(the four people aren't in sql server){
			newGame.createWaitScreen();
			while(keep checking server for four people){
				newGame.updateWaitScreen();
			}
			newGame.closeWaitScreen();
		}

		for(int i = 0; i < 4; i++){
			add each name from SQL to arrayList name
		}
		rearrange the array list so that the current player is at the top,but the order is kept proper
		assign the first player to enter SQL lobby as turn 1 and go in order from there.

		NEXT STEP:
		after this is implemented, begin working on code for game player

*/
		//DEBUG
		setup(names);
		printCardsToOutput();

	}

/*	public static void gameLoop(){
		//gameloop
	}*/

	public static void deal(){//pass static array of Players
		Deck deck = new Deck();

		while(deck.cardsDealt(playerList.length)==false){ //loop until cards are evenly dealt
			for(int i = 0; i < playerList.length; i++){
				//populate each hand in bunches of # of players
				playerList[i].getHand().addCard(deck.dealCard());
				playerList[i].sortMyHand();
			}
		}
	}


	public static void setup(ArrayList<String> names){
		playerList = new Player[names.size()];
		for(int i = 0; i < names.size(); i++){
			playerList[i] = new Player(names.get(i));
		}
		deal();
	}












/***********************************************
	DEBUG
***********************************************/
	public static void printCardsToOutput(){ //tests if deal is working and outputs the hands

		for(int i = 0; i < 4; i++){
			System.out.print(playerList[i].getName() + "\n");

			for(int j = 0; j < 13; j++){
				switch(playerList[i].getHand().getCardFromLoc(j).getValue()){
					case 11:	System.out.print("Jack");
								break;
					case 12:	System.out.print("Queen");
								break;
					case 13:	System.out.print("King");
								break;
					case 14:	System.out.print("Ace");
								break;
					default:	System.out.print(playerList[i].getHand().getCardFromLoc(j).getValue());
								break;
				}

				System.out.print(" of ");

				switch(playerList[i].getHand().getCardFromLoc(j).getSuit()){
					case 0: System.out.print("Spades");
							break;
					case 1: System.out.print("Hearts");
							break;
					case 2: System.out.print("Diamonds");
							break;
					case 3: System.out.print("Clubs");
							break;
				}
				System.out.print("\n");
			}
			System.out.print("\n");
		}
	}
}