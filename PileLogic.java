import java.util.ArrayList;

public class PileLogic{
	private int pileValue;
	private int passPlayCounter;
	private int pileCount;
	private boolean skip = false;
	private boolean repeat = false;

	
	public PileLogic(){
		this.pileValue = 1;
		this.passPlayCounter = 0;
		this.pileCount = 0;
	}
	
	public boolean checkClick (ArrayList<Card> inputCards, Card chosenCard){
		ArrayList<Card> queuedCards = new ArrayList<Card>();
		queuedCards.addAll(inputCards);

		System.out.println("pileValue: " + pileValue);
		System.out.println("pileCount: " + pileCount);	

		if(queuedCards.size()==1 && queuedCards.get(0).getValue()==2 && chosenCard.getValue()==2 && chosenCard.getSuit()!=queuedCards.get(0).getSuit()) //account for 2's
			return false;
			
		for(int i = 0; i< queuedCards.size(); i++){
			if(chosenCard.getValue() != queuedCards.get(i).getValue())
				return false;
		}
		return true;
	}
	
	public void checkRefresh(){ //called at the start of a turn
		if(this.passPlayCounter == 3){
			this.pileValue = 0;
			this.pileCount = 1;
		}
	}
	
	public boolean checkWin(ArrayList<Card> inputHand){
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.addAll(inputHand);

		if(hand.size()<5){
			for(int i = 0; i < hand.size(); i++){
				if(hand.get(i).getValue()!=2)
					return false;
			}
			//if(hand.size()!=0)
				//throwTwos();
			return true;
		}
		return false;
	}
	
	public boolean checkPlay (ArrayList<Card> inputCards){
		ArrayList<Card> queuedCards = new ArrayList<Card>();
		queuedCards.addAll(inputCards);

		if(((queuedCards.size() == 1 && queuedCards.get(0).getValue()==2) || queuedCards.size()==4)){
/*			this.pileValue = 0;
			this.passPlayCounter = 0;
			this.pileCount = 1;*/
			return true;
		}
			
		//can deal with finishing a set here 
		if(!(queuedCards.isEmpty())){
			if(pileValue == queuedCards.get(0).getValue() && pileCount + queuedCards.size() == 4){
				return true;
			}
		}

		//can deal with same skips here
		if(!(queuedCards.isEmpty())){
			if((pileValue == queuedCards.get(0).getValue()) && (pileCount == queuedCards.size())){
				return true;
			}
		}
		
		
		int powerValue = 0;
		if(!queuedCards.isEmpty())
			powerValue = queuedCards.get(0).getValue();
		if((powerValue > pileValue && queuedCards.size()==pileCount)||(queuedCards.size()>pileCount)){ //currently doesnt handle equal since the prev will
/*			this.pileValue = powerValue;
			this.passPlayCounter = 0;
			this.pileCount = queuedCards.size();*/
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean checkPass(ArrayList<Card> inputCards){
		ArrayList<Card> queuedCards = new ArrayList<Card>();
		queuedCards.addAll(inputCards);

		if (queuedCards.size() == 0){
			/*this.passPlayCounter++;*/
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean checkPlayButton (ArrayList<Card> inputCards){
		ArrayList<Card> queuedCards = new ArrayList<Card>();
		queuedCards.addAll(inputCards);

		if(((queuedCards.size() == 1 && queuedCards.get(0).getValue()==2) || queuedCards.size()==4)){
			repeat = true;
			return true;
		}
			
		//can deal with finishing a set here
		if(!(queuedCards.isEmpty())){
			if((pileValue == queuedCards.get(0).getValue()) && ((pileCount + queuedCards.size()) == 4)){
				repeat = true;
				return true;
			}
		}

		//can deal with same skips here
		if(!(queuedCards.isEmpty())){
			if((pileValue == queuedCards.get(0).getValue()) && (pileCount == queuedCards.size())){
				skip = true;
				return true;
			}
		}

		int powerValue = 0;
		if(!queuedCards.isEmpty())
			powerValue = queuedCards.get(0).getValue();
		if((powerValue > pileValue && queuedCards.size()==pileCount)||(queuedCards.size()>pileCount)) //currently doesnt handle equal since the prev will
			return true;
		else
			return false;
	}
	
	public boolean checkPassButton(ArrayList<Card> inputCards){
		ArrayList<Card> queuedCards = new ArrayList<Card>();
		queuedCards.addAll(inputCards);

		if (queuedCards.size() == 0)
			return true;
		else 
			return false;
	}

	public void setPileValue(ArrayList<Card> inputCards){
		ArrayList<Card> oldCards = new ArrayList<Card>();
		oldCards.addAll(inputCards);

		this.pileCount = oldCards.size();
		
		if(oldCards.isEmpty())
			this.pileValue = 1;	
		else
			this.pileValue = oldCards.get(0).getValue();
	}

	public void resetPileCount(){
			this.passPlayCounter = 0;
	}

	public boolean getSkip(){
		return skip;
	}

	public boolean getRepeat(){
		return repeat;
	}

	public void resetSkip(){
		skip = false;
	}

	public void resetRepeat(){
		repeat = false;
	}
}