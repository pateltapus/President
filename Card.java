public class Card{

	public final static int ACE = 14;
	public final static int KING = 13;
	public final static int QUEEN = 12;
	public final static int JACK = 11;

	public final static int SPADES = 0;
	public final static int HEARTS = 1;
	public final static int DIAMONDS = 2;
	public final static int CLUBS = 3;

	public final int value, suit;//value 2-14  suit 0-3

	public Card(){ //base constructer for dummy cards
		this.value = -1;
		this.suit = -1;
	}

	public Card(int value, int suit){ //no base constructer
		this.value = value;
		this.suit = suit;
	}

	public int getSuit(){
		return this.suit;
	}

	public int getValue(){
		return this.value;
	}


	//might be nessecary:
	//public String getSuitString() i.e returns "Spades" instead of 0
	//public String getValueString() i.e returns "Ace" instead of 14
	//public String getCardString() i.e returns "Jack of Hearts"
}