import java.util.Arrays;
import java.util.ArrayList;


public class President{

	private static Player playerList[];

	public static void main(String args[]){
		
		PresidentGUI newGame = new PresidentGUI();
		//Get names into an ArrayList
		ArrayList<String> names = new ArrayList<String>();
/*		names.add("Joe");
		names.add("Bill");*/
		Server sqlServer = new Server();

		names.add("Sam");
		names.add("Linda");
		sqlServer.createPlayer(newGame.getName());
		int orderNum = sqlServer.getRows();

		System.out.println(orderNum);

		if(sqlServer.getRows() == 1){
			sqlServer.dealCards();
		}

		if(sqlServer.getRows()<4){
			newGame.createWaitScreen();
			while(sqlServer.getRows()<4){
				newGame.updateWaitScreen();
			}
			newGame.closeWaitScreen();
		}

		Player currPlayer = new Player(newGame.getName());

		for(int i = 1; i < 14; i++){
			currPlayer.getHand().addCard(new Card(sqlServer.getInitCards(orderNum, "Num", i), sqlServer.getInitCards(orderNum, "Suit", i)));
		}

		System.out.println("FLAG1");

		currPlayer.sortMyHand();
		sqlServer.sortTableById();

		System.out.println("FLAG2");

		int otherPlayers[] = new int[3];
		String otherPlayersString[] = new String[3];

		System.out.println("FLAG3");

		if(orderNum ==1){
			for(int i = 0; i < 3; i++){
				otherPlayers[i] = sqlServer.readDataCell("numCards", orderNum + 1 + i);
				otherPlayersString[i] = sqlServer.readStringCell("Player", orderNum + 1 + i);

			}
		}

		else{
			int mod = orderNum + 1;

			for(int i = 1; i < 4; i++){
				if(orderNum + i >= 5){
					otherPlayers[(orderNum + i) % mod] = sqlServer.readDataCell("numCards", orderNum + i - 4);
					otherPlayersString[(orderNum + i) % mod] = sqlServer.readStringCell("Player", orderNum + i - 4);
				}
				else{
					otherPlayers[(orderNum + i) % mod] = sqlServer.readDataCell("numCards", orderNum+i);
					otherPlayersString[(orderNum + i) % mod] = sqlServer.readStringCell("Player", orderNum+i);
				}
			}	
		}

		System.out.println("FLAG4");

		System.out.println("OtherPlayers size: " + otherPlayersString.length);

		for(int i = 0; i<otherPlayersString.length; i++){
				System.out.println(otherPlayersString[i]);
		}

		ArrayList<Card> prevCards = new ArrayList<Card>(); //last passed cards

		for(int i = 0; i < otherPlayersString.length; i++)
			System.out.println(otherPlayersString[i]);

		if(orderNum == 4) //clear initial hands once each local machine has them
			sqlServer.clearHandTable();

		if(orderNum == 1)
			sqlServer.sendDataCell("Turn", 1, 1);

/*		if(sqlServer.readDataCell("Turn", orderNum) == 1)
 			turn = true; 	
 		else
 			turn = false;*/
 		newGame.createGameScreen();
 		newGame.setOrderNum(orderNum);
		newGame.renderHandOnScreen(currPlayer, otherPlayers, prevCards);

		//gameLoop
		boolean gameRunning = true;
		
		while(gameRunning){

			for(int j = 1; j < 5; j++){
		 			if(sqlServer.readDataCell("Place", j) == 0){
			 			if(sqlServer.readDataCell("Turn", j) == 1){
						 	//check for last played cards, needed for if a person is already done
							int val, suit;
							boolean flag = false;
							prevCards.clear();

				 			while(sqlServer.readDataCell("Turn", j) == 1){
				 				if(orderNum == j){

				 					//now execute the turn
				 					while(newGame.getTurnOver() == false);

				 					System.out.println("TURNOVER FLAG");

				 					if(newGame.checkDone() == true){
								 		int count = 1;
								 		for(int i = 1; i < 5; i++){
								 			if(sqlServer.readDataCell("Place", i) != 0)
								 				count++;
								 		}
								 		sqlServer.sendDataCell("Place", count, j);
								 		newGame.getWinscreen(sqlServer.readDataCell("Place", j));
								 		if(sqlServer.readDataCell("Place", j) == 3){
								 			for(int i = 1; i < 5; i ++){
								 				if(sqlServer.readDataCell("Place", i) == 0){
								 					sqlServer.sendDataCell("Place", 4, i);
								 					break;
								 				}
								 			}
								 		}

								 	}

								
								 	for(int i = 0; i < newGame.getPileCards().size(); i++){
								 		if(newGame.isClear()){
								 			sqlServer.sendDataCell("Card" + (i+1) + "Value", -1, j);
								 			sqlServer.sendDataCell("Card" + (i+1) + "Suit", -1, j);					 			
								 		}
								 		else{
								 			sqlServer.sendDataCell("Card" + (i+1) + "Value", newGame.getPileCards().get(i).getValue(), j);
								 			sqlServer.sendDataCell("Card" + (i+1) + "Suit", newGame.getPileCards().get(i).getSuit(), j);
								 		}
							 		}

							 		for(int i = newGame.getPileCards().size(); i < 4; i++){
							 			sqlServer.sendDataCell("Card" + (i+1) + "Value", -1, j);
							 			sqlServer.sendDataCell("Card" + (i+1) + "Suit", -1, j);
							 		}

							 		//change turn
							 		sqlServer.sendDataCell("Turn", -1, j);

							 		int k = j + 1;
							 		if(k > 4)
							 			k -= 4;

							 		int l = j + 2;
							 		if(l > 4)
							 			l -= 4;

							 		if(newGame.isRepeat()){
			 							flag = true; 
							 		}
							 		else if(newGame.isSkip()){
			 							sqlServer.sendDataCell("Turn", 1, l);
							 		}
							 		else{
			 							sqlServer.sendDataCell("Turn", 1, k);
			 						}

			 						//change numCards in sql
			 						if(!(newGame.getPanelPassed()))
			 							sqlServer.sendDataCell("numCards", (sqlServer.readDataCell("numCards", j) - newGame.getPileCards().size()), j);
			 					
				 				}
				 			}


				 			if((orderNum == j) && flag){
				 				sqlServer.sendDataCell("Turn", 1, j);
				 				flag = false;
				 			}


							//populate prev cards
							for(int i = 1; i < 5; i++){
								val = sqlServer.readDataCell("Card" + i + "Value", j);
								suit = sqlServer.readDataCell("Card" + i + "Suit", j);
							
								if(val != -1 && suit != -1)
									prevCards.add(new Card(val, suit));
							}

							//populate otherPlayers
							if(orderNum == 1){
								for(int i = 0; i < 3; i++){
									otherPlayers[i] = sqlServer.readDataCell("numCards", orderNum + 1 + i);
									otherPlayersString[i] = sqlServer.readStringCell("Player", orderNum + 1 + i);

								}
							}

							else{
								int mod = orderNum + 1;

								for(int i = 1; i < 4; i++){
									if(orderNum + i >= 5){
										otherPlayers[(orderNum + i) % mod] = sqlServer.readDataCell("numCards", orderNum + i - 4);
										otherPlayersString[(orderNum + i) % mod] = sqlServer.readStringCell("Player", orderNum + i - 4);
									}
									else{
										otherPlayers[(orderNum + i) % mod] = sqlServer.readDataCell("numCards", orderNum+i);
										otherPlayersString[(orderNum + i) % mod] = sqlServer.readStringCell("Player", orderNum+i);
									}
								}
							}
				 		}
				 	}

				 	else{
				 		if(orderNum == j){
				 			newGame.getWinscreen(sqlServer.readDataCell("Place", j));

					 		sqlServer.sendDataCell("Turn", -1, j);
					 		int k = j + 1;
					 		if(k > 4)
					 			k -= 4;		
					 		sqlServer.sendDataCell("Turn", 1, k);	
					 	}	 		
				 	}
			 		
		 			newGame.renderHandOnScreen(currPlayer, otherPlayers, prevCards);	
			}
		}
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
		//setup(names);
		//printCardsToOutput();

	



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
	public static void printCardsToOutput(Player currPlayer){ //tests if deal is working and outputs the hands

			for(int j = 0; j < 13; j++){
				switch(currPlayer.getHand().getCardFromLoc(j).getValue()){
					case 11:	System.out.print("Jack");
								break;
					case 12:	System.out.print("Queen");
								break;
					case 13:	System.out.print("King");
								break;
					case 14:	System.out.print("Ace");
								break;
					default:	System.out.print(currPlayer.getHand().getCardFromLoc(j).getValue());
								break;
				}

				System.out.print(" of ");

				switch(currPlayer.getHand().getCardFromLoc(j).getSuit()){
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