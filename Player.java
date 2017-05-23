

public class Player{
	private boolean isPresident;
	private Hand hand;
	private String name;

	public Player(String name){
		hand = new Hand();
		this.name = name;
	}

	public boolean getIsPresident(){
		return this.isPresident;
	}

	public void setPresident(boolean isPres){
		//potentially change it to a rank system instead of a boolean president system for variable # of players
		this.isPresident = isPres;
	}

	public Hand getHand(){
		return this.hand;
	}

	public String getName(){
		return this.name;
	}

	public void debugHand()
	{
		hand.sortHand();
	}
}