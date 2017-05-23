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
		//System.out.println("FLAG");
		selectionSort();
		/*for(int k = 0; k < hand.size(); k++)
		{
			System.out.println(hand.get(k).getValue());
		}*/
		ArrayList<Card> sortingHand = new ArrayList<Card>(hand);
		clearHand();
		sortingHand.add(0, new Card());
		sortingHand.add(0, new Card());
		sortingHand.add(0, new Card());
		sortingHand.add(0, new Card());

		//populate hand with 2's first
		for(int i = sortingHand.size()-1; i > 3; i--){
			if(sortingHand.get(i).getValue() == 2){
				hand.add(0,sortingHand.get(i));
				sortingHand.remove(i);
			}
			else{
				break;
			}
		}

		//check for singles
		for(int i = sortingHand.size() - 1; i > 3; i--){

			if(sortingHand.get(i).getValue() == sortingHand.get(i-3).getValue()){
				i = i-3;
			}
			else if(sortingHand.get(i).getValue() == sortingHand.get(i-2).getValue()){
				i = i-2;
			}
			else if(sortingHand.get(i).getValue() == sortingHand.get(i-1).getValue()){
				i = i-1;
			}
			else{
				hand.add(0,sortingHand.get(i));
				sortingHand.remove(i);
			}
		}

		//check for doubles
		for(int i = sortingHand.size() - 1; i > 3; i--){
			if(sortingHand.get(i).getValue() == sortingHand.get(i-3).getValue()){
				i = i-3;
			}
			else if(sortingHand.get(i).getValue() == sortingHand.get(i-2).getValue()){
				i = i-2;
			}
			else{	
				hand.add(0,sortingHand.get(i));		
				if(sortingHand.get(i).getSuit()<sortingHand.get(i-1).getSuit())
					hand.add(0, sortingHand.get(i-1));
				else
					hand.add(1,sortingHand.get(i-1));		

				sortingHand.remove(i);
				sortingHand.remove(i-1);
				i = i - 1;
			}
		}

		//check for triples
		for(int i = sortingHand.size() - 1; i > 3; i--){
			if(sortingHand.get(i).getValue() == sortingHand.get(i-3).getValue()){
				i = i-3;
			}
			else{

				hand.add(0,sortingHand.get(i));
				if(sortingHand.get(i).getSuit()<sortingHand.get(i-1).getSuit()){
					hand.add(0, sortingHand.get(i-1));
					if(sortingHand.get(i-1).getSuit()<sortingHand.get(i-2).getSuit())
						hand.add(0, sortingHand.get(i-2));
					else if(sortingHand.get(i).getSuit()>sortingHand.get(i-2).getSuit())
						hand.add(2, sortingHand.get(i-2));
					else
						hand.add(1, sortingHand.get(i-2));
				}
				else{
					hand.add(1, sortingHand.get(i-1));
					if(sortingHand.get(i).getSuit()<sortingHand.get(i-2).getSuit())
						hand.add(0, sortingHand.get(i-2));
					else if(sortingHand.get(i-1).getSuit()>sortingHand.get(i-2).getSuit())
						hand.add(2, sortingHand.get(i-2));
					else
						hand.add(1, sortingHand.get(i-2));
				}

				sortingHand.remove(i);
				sortingHand.remove(i-1);
				sortingHand.remove(i-2);
				i = i-2;
			}
		}

		//check for quadruples
		for(int i = sortingHand.size() - 1; i > 3; i-=4){
			hand.add(0, new Card());
			hand.add(0, new Card());
			hand.add(0, new Card());
			hand.add(0, new Card());

			hand.set(3-sortingHand.get(i).getSuit(),sortingHand.get(i));
			hand.set(3-sortingHand.get(i-1).getSuit(), sortingHand.get(i-1));
			hand.set(3-sortingHand.get(i-2).getSuit(),sortingHand.get(i-2));
			hand.set(3-sortingHand.get(i-3).getSuit(), sortingHand.get(i-3));

			sortingHand.remove(i);
			sortingHand.remove(i-1);
			sortingHand.remove(i-2);
			sortingHand.remove(i-3);
			i = i-3;
		}

		/*DEBUG*/
		System.out.println("Values in hand after all elements are parsed");

		for(int k = 0; k < hand.size(); k++){
			System.out.println(hand.get(k).getValue());
		}
		/****/
	}

	public void selectionSort()
	{

		for(int i = 0; i < hand.size(); i++){
			int max = i;
			for(int j = i + 1; j < hand.size(); j++){
				if(hand.get(j).getValue() > hand.get(max).getValue()){
					max = j;
				}
			}
			Card temp = hand.get(i);
			hand.set(i, hand.get(max));
			hand.set(max, temp);
		}
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