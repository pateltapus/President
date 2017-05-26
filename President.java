import java.util.Arrays;
import java.util.ArrayList;


public class President{

	private static Player playerList[];

	public static void main(String args[]){
		
		
		PresidentGUI newGame = new PresidentGUI();
		//Get names into an ArrayList
		ArrayList<String> names = new ArrayList<String>();
		names.add("Joe");
		names.add("Sam");
		names.add("Linda");
		names.add("Bill");
		
		//1.)	//Create player list for number of players and name them
		
		
		//2.)	//deal cards to them and sort them
		setup(names);
		printCardsToOutput();

		
		//3.)Communicate data to and from PresidentGUI
		newGame.renderHandOnScreen(playerList);
		


	}

	public static void gameLoop(){
		//gameloop
	}

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