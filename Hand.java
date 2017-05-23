import java.util.ArrayList;
import java.util.Iterator;

public class Hand{

	private ArrayList<Card> hand;

	public Hand(){ //create an empty hand
		hand = new ArrayList<Card>();
	}

	public void clearHand(){ //discard all cards in a hand to make it empty
		hand.clear();
	}

	public void addCard(Card card){ //puts a certain card into a hand
		hand.add(card);
	}

	public void removeCard(Card card){ //remove a given card and return it
		//using iterator since cant loop and remove from an arraylist at the same time
		Iterator<Card> iter = hand.iterator();
		while(iter.hasNext()){
			Card parsedCard = iter.next();
			if(parsedCard == card){
				iter.remove();
				break;
			}
		}
	}

	public int getHandSize(){
		//return # of cards in hand
		return hand.size();
	}

	public Card getCardFromLoc(int location){
		//get card at given location (indexed from 0)
		return hand.get(location);
	}

	public Card getCardFromIdentifier(int value, int suit){
		for(Card card: hand){ //loop through each card in hand
			if(card.getValue()==value && card.getSuit()==suit) //return card when it is found
				return card;
		}
		return null;
	}

	public void sortHand(){
		//TODO:
		//sort had by value first, then by frequency(i.e higher frequency on left, lowest single on right)
		//also order of suit from left to right is Clubs, Diamonds, Hearts, Spades
		//example sorted hand: (Suit|Value) 7D JH 5C 4H 9S JC 8C KC 10C 10S 8S 8D 4D
		//8C 8D 8S JC JH 10C 10S 4D 4H KC 9S 7D 5C
	}
}

/*
	FOR REFERENCE: 

	public final static int SPADES = 0;
	public final static int HEARTS = 1;
	public final static int DIAMONDS = 2;
	public final static int CLUBS = 3;

	public final static int ACE = 14;
	public final static int KING = 13;
	public final static int QUEEN = 12;
	public final static int JACK = 11;
*/