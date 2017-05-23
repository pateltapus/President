import java.util.Arrays;

public class President{
	public static void main(String args[]){

		PresidentGUI newGame = new PresidentGUI();
		printCards();
	
	}
	
	public static void gameLoop(){
		//gameloop
	}

	public static void deal(int players, Player[] playerList){//pass static array of Players
		Deck deck = new Deck();

		while(deck.cardsDealt(players)==false){ //loop until cards are evenly dealt
			for(int i = 0; i < players; i++){
				//populate each hand in bunches of # of players
				playerList[i].getHand().addCard(deck.dealCard());
			}
		}
	}














/***********************************************
	DEBUG
***********************************************/
	public static void printCards(){ //tests if deal is working and outputs the hands
		Player playerList[] = new Player[4];
		playerList[0] = new Player("Joe");
		playerList[1] = new Player("Sam");
		playerList[2] = new Player("Linda");
		playerList[3] = new Player("Bill");
		deal(4, playerList);

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