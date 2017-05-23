import java.util.ArrayList;
import java.util.Random;

public class Deck{

	private ArrayList<Card> deck;

	public Deck(){
		//create an unshuffled deck
		deck = new ArrayList<Card>();

		for(int suit = 0; suit < 4; suit++){
			for(int number = 2; number < 15; number++){
				deck.add(new Card(number, suit));
			}
		}
	}
/*
	public void shuffle(){
		//reset deck and shuffle
	}
*/
	public Card dealCard(){ 
		//deals and returns a card from the deck
		Random randGen = new Random();
		int randIndex = randGen.nextInt(deck.size()); //generates a random value from 0 to decksize-1
		Card tempCard = deck.get(randIndex); //card temp saved, since it is deleted then returned
		deck.remove(randIndex);
		return tempCard; 

	}

	public boolean cardsDealt(int players){
		//returns true when deck is dealt or only leftover cards are left (only necc with players%4!=0)
		//currently only for 4 players
		if(deck.size()==0)
			return true;
		else
			return false;

		//TODO:
		//Implement for a variable # of players
	}
}