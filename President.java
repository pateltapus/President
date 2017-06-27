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

		currPlayer.sortMyHand();
		sqlServer.sortTableById();

		int otherPlayers[] = new int[3];
		String otherPlayersString[] = new String[3];

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
 			
 			//wait for turn

/* PLAYER 1 TURN ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

 	
 			if(sqlServer.readDataCell("Place", 1) == 0){
			 	//check for last played cards, needed for if a person is already done
				int val, suit;
				prevCards.clear();

	 			while(sqlServer.readDataCell("Turn", 1) == 1){
	 				if(orderNum == 1){

	 					//now execute the turn
	 					while(newGame.getTurnOver() == false);

	 					System.out.println("TURNOVER FLAG");

	 					if(newGame.checkDone() == true){
					 		int count = 1;
					 		for(int i = 1; i < 5; i++){
					 			if(sqlServer.readDataCell("Place", i) != 0)
					 				count++;
					 		}
					 		sqlServer.sendDataCell("Place", count, orderNum);

					 	}

					 	for(int i = 0; i < newGame.getPileCards().size(); i++){
				 			sqlServer.sendDataCell("Card" + (i+1) + "Value", newGame.getPileCards().get(i).getValue(), 1);
				 			sqlServer.sendDataCell("Card" + (i+1) + "Suit", newGame.getPileCards().get(i).getSuit(), 1);
				 		}

				 		for(int i = newGame.getPileCards().size(); i < 4; i++){
				 			sqlServer.sendDataCell("Card" + (i+1) + "Value", -1, 1);
				 			sqlServer.sendDataCell("Card" + (i+1) + "Suit", -1, 1);
				 		}

				 		sqlServer.sendDataCell("Turn", -1, 1);
 						sqlServer.sendDataCell("Turn", 1, 2);
	 				}
	 			}
/*				if(sqlServer.readDataCell("Place", 4) == 0){

					for(int i = 1; i < 5; i++){
						val = sqlServer.readDataCell("Card" + i + "Value", 4);
						suit = sqlServer.readDataCell("Card" + i + "Suit", 4);
					
						if(val != -1 && suit != -1)
							prevCards.add(new Card(val, suit));
					}

				}
				else if(sqlServer.readDataCell("Place", 3) == 0){

					for(int i = 1; i < 5; i++){
						val = sqlServer.readDataCell("Card" + i + "Value", 3);
						suit = sqlServer.readDataCell("Card" + i + "Suit", 3);
					
						if(val != -1 && suit != -1)
							prevCards.add(new Card(val, suit));
					}

				}

				else if(sqlServer.readDataCell("Place", 2) == 0){

					for(int i = 1; i < 5; i++){
						val = sqlServer.readDataCell("Card" + i + "Value", 2);
						suit = sqlServer.readDataCell("Card" + i + "Suit", 2);
					
						if(val != -1 && suit != -1)
							prevCards.add(new Card(val, suit));
					}
					
				}

				else {
					//game is over
					sqlServer.sendDataCell("Place", 4, orderNum);
					gameRunning = false;
				}*/
				//populate prev cards
				for(int i = 1; i < 5; i++){
					val = sqlServer.readDataCell("Card" + i + "Value", 1);
					suit = sqlServer.readDataCell("Card" + i + "Suit", 1);
				
					if(val != -1 && suit != -1)
						prevCards.add(new Card(val, suit));
				}

				//populate otherPlayers
					//update numCards
	 		}



	 		System.out.println("Pl1 Turn over");//debug
/*
	 		if(sqlServer.readDataCell("Turn", orderNum) == 1)
 				turn = true;
 			else
 				turn = false;*/

 			System.out.println(prevCards.size());
 			for(int i = 0; i < prevCards.size(); i++){
 				System.out.println(prevCards.get(i).getSuit());
 			}

 	
 			newGame.renderHandOnScreen(currPlayer, otherPlayers, prevCards);	





 /* PLAYER 2 TURN ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~`*/

 			if(sqlServer.readDataCell("Place", 2) == 0){

 				System.out.println("P2 Turn start"); 

			 	//check for last played cards, needed for if a person is already done
				int val, suit;
				prevCards.clear();

	 			while(sqlServer.readDataCell("Turn", 2) == 1){
	 				if(orderNum == 2){
						while(newGame.getTurnOver() == false);

	 					System.out.println("TURNOVER FLAG");

	 					if(newGame.checkDone() == true){
					 		int count = 1;
					 		for(int i = 1; i < 5; i++){
					 			if(sqlServer.readDataCell("Place", i) != 0)
					 				count++;
					 		}
					 		sqlServer.sendDataCell("Place", count, orderNum);
					 	}

					 	for(int i = 0; i < newGame.getPileCards().size(); i++){
				 			sqlServer.sendDataCell("Card" + (i+1) + "Value", newGame.getPileCards().get(i).getValue(), 2);
				 			sqlServer.sendDataCell("Card" + (i+1) + "Suit", newGame.getPileCards().get(i).getSuit(), 2);
				 		}

				 		for(int i = newGame.getPileCards().size(); i < 4; i++){
				 			sqlServer.sendDataCell("Card" + (i+1) + "Value", -1, 2);
				 			sqlServer.sendDataCell("Card" + (i+1) + "Suit", -1, 2);
				 		}
				 		//change turn
				 		sqlServer.sendDataCell("Turn", -1, 2);
 						sqlServer.sendDataCell("Turn", 1, 3);


	 				}
	 			}

	 			System.out.println("Pl2 Turn over");//debug
/*				if(sqlServer.readDataCell("Place", 1) == 0){

					for(int i = 1; i < 5; i++){
						val = sqlServer.readDataCell("Card" + i + "Value", 1);
						suit = sqlServer.readDataCell("Card" + i + "Suit", 1);
					
						if(val != -1 && suit != -1)
							prevCards.add(new Card(val, suit));
					}

				}
				else if(sqlServer.readDataCell("Place", 4) == 0){

					for(int i = 1; i < 5; i++){
						val = sqlServer.readDataCell("Card" + i + "Value", 4);
						suit = sqlServer.readDataCell("Card" + i + "Suit", 4);
					
						if(val != -1 && suit != -1)
							prevCards.add(new Card(val, suit));
					}

				}

				else if(sqlServer.readDataCell("Place", 3) == 0){

					for(int i = 1; i < 5; i++){
						val = sqlServer.readDataCell("Card" + i + "Value", 3);
						suit = sqlServer.readDataCell("Card" + i + "Suit", 3);
					
						if(val != -1 && suit != -1)
							prevCards.add(new Card(val, suit));
					}
					
				}

				else {
					//game is over
					sqlServer.sendDataCell("Place", 4, orderNum);
					gameRunning = false;
				}*/

				//populate prev cards
				for(int i = 1; i < 5; i++){
					val = sqlServer.readDataCell("Card" + i + "Value", 2);
					suit = sqlServer.readDataCell("Card" + i + "Suit", 2);
				
					if(val != -1 && suit != -1)
						prevCards.add(new Card(val, suit));
				}
				
				//populate otherPlayers
					//update numCards
	 		}

	 		

/* 			if(sqlServer.readDataCell("Turn", orderNum) == 1)
 				turn = true;
 			else
 				turn = false;*/

 			newGame.renderHandOnScreen(currPlayer, otherPlayers, prevCards);	


 /* PLAYER 3 TURN ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


 			if(sqlServer.readDataCell("Place", 3) == 0){
				int val, suit;
				prevCards.clear();

	 			while(sqlServer.readDataCell("Turn", 3) == 1){
	 				if(orderNum == 3){
						while(newGame.getTurnOver() == false);

	 					System.out.println("TURNOVER FLAG");

	 					if(newGame.checkDone() == true){
					 		int count = 1;
					 		for(int i = 1; i < 5; i++){
					 			if(sqlServer.readDataCell("Place", i) != 0)
					 				count++;
					 		}
					 		sqlServer.sendDataCell("Place", count, orderNum);
					 	}

					 	for(int i = 0; i < newGame.getPileCards().size(); i++){
				 			sqlServer.sendDataCell("Card" + (i+1) + "Value", newGame.getPileCards().get(i).getValue(), 3);
				 			sqlServer.sendDataCell("Card" + (i+1) + "Suit", newGame.getPileCards().get(i).getSuit(), 3);
				 		}

				 		for(int i = newGame.getPileCards().size(); i < 4; i++){
				 			sqlServer.sendDataCell("Card" + (i+1) + "Value", -1, 3);
				 			sqlServer.sendDataCell("Card" + (i+1) + "Suit", -1, 3);
				 		}
				 		//change turn
				 		sqlServer.sendDataCell("Turn", -1, 3);
 						sqlServer.sendDataCell("Turn", 1, 4);


	 				}

	 			}
/*
	 			if(sqlServer.readDataCell("Place", 2) == 0){

					for(int i = 1; i < 5; i++){
						val = sqlServer.readDataCell("Card" + i + "Value", 2);
						suit = sqlServer.readDataCell("Card" + i + "Suit", 2);
					
						if(val != -1 && suit != -1)
							prevCards.add(new Card(val, suit));
					}

				}
				else if(sqlServer.readDataCell("Place", 1) == 0){

					for(int i = 1; i < 5; i++){
						val = sqlServer.readDataCell("Card" + i + "Value", 1);
						suit = sqlServer.readDataCell("Card" + i + "Suit", 1);
					
						if(val != -1 && suit != -1)
							prevCards.add(new Card(val, suit));
					}

				}

				else if(sqlServer.readDataCell("Place", 4) == 0){

					for(int i = 1; i < 5; i++){
						val = sqlServer.readDataCell("Card" + i + "Value", 4);
						suit = sqlServer.readDataCell("Card" + i + "Suit", 4);
					
						if(val != -1 && suit != -1)
							prevCards.add(new Card(val, suit));
					}
					
				}

				else {
					//game is over
					sqlServer.sendDataCell("Place", 4, orderNum);
					gameRunning = false;
				}*/

				//populate prev cards
				for(int i = 1; i < 5; i++){
					val = sqlServer.readDataCell("Card" + i + "Value", 2);
					suit = sqlServer.readDataCell("Card" + i + "Suit", 2);
				
					if(val != -1 && suit != -1)
						prevCards.add(new Card(val, suit));
				}

				//populate otherPlayers
					//update numCards
	 		}

	 		System.out.println("Pl3 Turn over");//debug

/*	 		if(sqlServer.readDataCell("Turn", orderNum) == 1)
 				turn = true;
 			else
 				turn = false;*/

 			newGame.renderHandOnScreen(currPlayer, otherPlayers, prevCards);	


 /* PLAYER 4 TURN ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

 			if(sqlServer.readDataCell("Place", 4) == 0){
				int val, suit;
				prevCards.clear();

	  			while(sqlServer.readDataCell("Turn", 4) == 1){
	 				if(orderNum == 4){
						while(newGame.getTurnOver() == false);

	 					System.out.println("TURNOVER FLAG");

	 					if(newGame.checkDone() == true){
					 		int count = 1;
					 		for(int i = 1; i < 5; i++){
					 			if(sqlServer.readDataCell("Place", i) != 0)
					 				count++;
					 		}
					 		sqlServer.sendDataCell("Place", count, orderNum);
					 	}

					 	for(int i = 0; i < newGame.getPileCards().size(); i++){
				 			sqlServer.sendDataCell("Card" + (i+1) + "Value", newGame.getPileCards().get(i).getValue(), 4);
				 			sqlServer.sendDataCell("Card" + (i+1) + "Suit", newGame.getPileCards().get(i).getSuit(), 4);
				 		}

				 		for(int i = newGame.getPileCards().size(); i < 4; i++){
				 			sqlServer.sendDataCell("Card" + i + "Value", -1, 4);
				 			sqlServer.sendDataCell("Card" + i + "Suit", -1, 4);
				 		}
				 		//change turn
				 		sqlServer.sendDataCell("Turn", -1, 4);
 						sqlServer.sendDataCell("Turn", 1, 1);

	 				}
	 			}

/*	 			if(sqlServer.readDataCell("Place", 3) == 0){

					for(int i = 1; i < 5; i++){
						val = sqlServer.readDataCell("Card" + i + "Value", 3);
						suit = sqlServer.readDataCell("Card" + i + "Suit", 3);
					
						if(val != -1 && suit != -1)	
							prevCards.add(new Card(val, suit));
					}

				}
				else if(sqlServer.readDataCell("Place", 2) == 0){

					for(int i = 1; i < 5; i++){
						val = sqlServer.readDataCell("Card" + i + "Value", 2);
						suit = sqlServer.readDataCell("Card" + i + "Suit", 2);
					
						if(val != -1 && suit != -1)
							prevCards.add(new Card(val, suit));
					}

				}

				else if(sqlServer.readDataCell("Place", 1) == 0){

					for(int i = 1; i < 5; i++){
						val = sqlServer.readDataCell("Card" + i + "Value", 1);
						suit = sqlServer.readDataCell("Card" + i + "Suit", 1);
					
						if(val != -1 && suit != -1)
							prevCards.add(new Card(val, suit));
					}
					
				}

				else {
					//game is over
					sqlServer.sendDataCell("Place", 4, orderNum);
					gameRunning = false;
				}*/

				//populate prev cards
				for(int i = 1; i < 5; i++){
					val = sqlServer.readDataCell("Card" + i + "Value", 2);
					suit = sqlServer.readDataCell("Card" + i + "Suit", 2);
				
					if(val != -1 && suit != -1)
						prevCards.add(new Card(val, suit));
				}

				//populate otherPlayers
					//update numCards

	 		}
	 		System.out.println("Pl4 Turn over");//debug

	/* 		if(sqlServer.readDataCell("Turn", orderNum) == 1)
	 			turn = true;
	 		else
	 			turn = false;*/
	 			
	  		newGame.renderHandOnScreen(currPlayer, otherPlayers, prevCards);	
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