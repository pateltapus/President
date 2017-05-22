public class Hand{
	public Hand(){
		//create an empty hand
	}

	public void clearHand(){
		//discard all cards in a hand to make it empty
	}

	public void addCard(Card card){
		//puts a certain card into a hand
	}

	public void removeCard(Card card){
		//remove a given card and return it
	}

	public int getHandSize(){
		//return # of cards in hand
	}

	public Card getCard(int location){
		//get card at given location (indexed from 0)
	}

	public void sort(){
		//sort had by value first, then by frequency(i.e higher frequency on left, lowest single on right)
	}
}