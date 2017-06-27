import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Graphics;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.Arrays;
import java.util.ArrayList;

public class PresidentPanel extends JPanel{

	private BufferedImage cardBackSS, cardsSS, playButton, playButtonGrey, passButton, passButtonGrey, background;
	private BufferedImage[][] cardBackImages, cardImages;
	private static String cardBackPath, cardsPath;
	private ArrayList<Integer> xMax, xMin;
	private ArrayList<Card> cardQueue, playedCards, passedCards;
	private PileLogic logicChecker;
	private ArrayList<Boolean> raisedCards;
	private int[] otherHands;
	private JLabel p1Name, p2Name, p3Name, p4Name;
	private Server server;

	private int orderNum = 0;


	private Hand currentHand;
	private boolean noCards = false;
	private boolean turnDone = false;
	
	private static final int cbROWS = 5;
	private static final int cbCOLS = 3;
	private static final int cROWS = 4;
	private static final int cCOLS = 13;

	private static final int WIDTH = 140;
	private static final int HEIGHT = 190;

	//p1
	private static final int INITY = 525;
	private static final int DELTAY = -20;

	//p2
	private static final int INITX2 = 30;

	//p3
	private static final int INITY3 = 25;//50-75

	//p4
	private static final int INITX4 = 770;

	private static final int PASSBUTTONX = 740;
	private static final int PASSBUTTONY = 525+50;
	private static final int PLAYBUTTONX = 740;
	private static final int PLAYBUTTONY = 525+0;
	private static final int BUTTONWIDTH = 607*4/20;
	private static final int BUTTONHEIGHT = 235*4/20;
	private static final int PILEX = 415;
	private static final int PILEY = 245;//215

	//DEBUG------/
	private int x,  y, z;
	//-----------/

	public PresidentPanel(String cardBackPath, String cardsPath){

		//DEBUG-------/
		x = 0;
		y = 0;
		z = 0;
		//------------/

		//this.updatePile = false;
		this.cardBackPath = cardBackPath;
		this.cardsPath = cardsPath;
		this.xMax = new ArrayList<Integer>();
		this.xMin = new ArrayList<Integer>();
		this.cardQueue = new ArrayList<Card>();
		this.logicChecker = new PileLogic();
		this.raisedCards = new ArrayList<Boolean>(); 
		this.playedCards = new ArrayList<Card>();
		this.passedCards = new ArrayList<Card>();

		cardBackImages = new BufferedImage[cbROWS][cbCOLS]; //5 rows 3 columns,  ****ROWS PARSE DESIGN, COLS PARSE COLOR****
		cardImages = new BufferedImage[cROWS][cCOLS] ;//4 rows 13 columns   ****ROWS PARSE SUIT, COLS PARSE NUMBER****

		try{
			cardBackSS = ImageIO.read(new File(cardBackPath));
			for(int i = 0; i < cbROWS; i++)
				for(int j = 0; j < cbCOLS; j++)
					cardBackImages[i][j] = cardBackSS.getSubimage(j*WIDTH, i*HEIGHT, WIDTH, HEIGHT);

			//swap to make array more organized
			BufferedImage temp = cardBackImages[2][1];
			cardBackImages[2][1] = cardBackImages[4][2];
			cardBackImages[4][2] = temp;
		}
		catch(Exception e){
			System.out.println("Exception in Cardback Spritesheet load" + e.toString());
		}

		try{
			cardsSS = ImageIO.read(new File(cardsPath));
			parseCards();
		}
		catch(Exception e){
			System.out.println("Exception in Cards Spritesheet load" + e.toString());
		}
		
		try{
			playButton = ImageIO.read(new File("Images/Buttons/playButton.png"));
		}
		catch(Exception e){
			System.out.println("Exception in playButton" + e.toString());
		}
		try{
			passButton = ImageIO.read(new File("Images/Buttons/passButton.png"));
		}
		catch(Exception e){
			System.out.println("Exception in passButton" + e.toString());
		}
		try{
			playButtonGrey = ImageIO.read(new File("Images/Buttons/playButtonGrey.png"));
		}
		catch(Exception e){
			System.out.println("Exception in playButtonGrey" + e.toString());
		}
		try{
			passButtonGrey = ImageIO.read(new File("Images/Buttons/passButtonGrey.png"));
		}
		catch(Exception e){
			System.out.println("Exception in passButtonGrey" + e.toString());
		}
		try{
			background = ImageIO.read(new File("Images/Frames/Background.png"));
		}
		catch(Exception e){
			System.out.println("Exception in background" + e.toString());
		}

		server = new Server();

		addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent me){
				//System.out.println("click turn: " + getIsTurn());
				if(server.readDataCell("Turn", orderNum)==1){ //turn determines whether a click can be made, for now hard coded to 1, will be changed to a method from logic class
				//bound farthest right x and lowest and highest y
					cardClicker(me);
					buttonClicker(me);
					repaint();
				}
			}
		});

	}


	@Override
	public void paint(Graphics g){ //override paint method provided by JPanel
		super.paint(g);
		//TODO: FIX LOW GRAPHICS QUALITY ISSUE

		//TODO: Find and implement a nice felt background
		g.drawImage(background, 0, 0, null);

		//print other hands

			//left
			BufferedImage temp = rotate90ToRight(cardBackImages[0][0]);
			for(int i = otherHands[0]-1; i > -1; i--){
				int j = 20; //change later for scaling
				int k = 400 - otherHands[0]*20; 
					g.drawImage(temp, INITX2, i*j+k, HEIGHT/2, WIDTH/2, null); 
			}

			//top
			for(int i = 0; i < otherHands[1]; i++){
				int j = 20; //change later for scaling
				int k = 545 - otherHands[1]*20; 
					g.drawImage(rotate180(cardBackImages[0][0]), i*j+k, INITY3, WIDTH/2, HEIGHT/2, null);
			}

			//right
			for(int i = 0; i < otherHands[2]; i++){
				int j = 20; //change later for scaling
				int k = 400 - otherHands[2]*20; 
					g.drawImage(rotate90ToLeft(cardBackImages[0][0]), INITX4, i*j+k, HEIGHT/2, WIDTH/2, null); 
			}


		//print our hand
		if(currentHand != null){
			if(!playedCards.isEmpty()){
				if(playedCards.get(0).getValue() !=2 && !(playedCards.size()==4)){
					if(!playedCards.isEmpty()){
						int k = 30;
						for(int i = 0; i < playedCards.size(); i++)
							g.drawImage(cardImages[3-playedCards.get(i).getSuit()][playedCards.get(i).getValue()-2], PILEX + k*i - playedCards.size()*8 , PILEY, WIDTH/2, HEIGHT/2, null);
					}
				}
			}	
			if (logicChecker.checkPlayButton(cardQueue)&&(server.readDataCell("Turn", orderNum)==1)) //has to check turn ***
				g.drawImage(playButton,PLAYBUTTONX, PLAYBUTTONY, BUTTONWIDTH, BUTTONHEIGHT, null);
			else
				g.drawImage(playButtonGrey,PLAYBUTTONX, PLAYBUTTONY, BUTTONWIDTH, BUTTONHEIGHT, null);
			
			if (logicChecker.checkPassButton(cardQueue)&&(server.readDataCell("Turn", orderNum)==1)) //has to check turn ***
				g.drawImage(passButton,PASSBUTTONX, PASSBUTTONY, BUTTONWIDTH, BUTTONHEIGHT, null);
			else
				g.drawImage(passButtonGrey,PASSBUTTONX, PASSBUTTONY, BUTTONWIDTH, BUTTONHEIGHT, null);

			if(currentHand.getHandSize() != 0){
				xMin.clear();
				for(int i = 0; i < currentHand.getHandSize(); i++){
					int j = 40; //change later for scaling
					int k = 410 - currentHand.getHandSize()*20; //change later for scaling, 120 for 13 cards
					if(raisedCards.get(i))
						g.drawImage(cardImages[3-currentHand.getCardFromLoc(i).getSuit()][currentHand.getCardFromLoc(i).getValue()-2], i*j+k, INITY+DELTAY, WIDTH/2, HEIGHT/2, null); //140 190 
					else
						g.drawImage(cardImages[3-currentHand.getCardFromLoc(i).getSuit()][currentHand.getCardFromLoc(i).getValue()-2], i*j+k, INITY, WIDTH/2, HEIGHT/2, null); //140 190 
					xMin.add(i*j+k);
				}
				createXMax();
			}
		}
	}

	public void renderAHand(Hand handPrint, int[] otherPlayers, ArrayList<Card> newCards){
		//clear values for new turn

		//render last passed cards; set playedCards to last passed cards
		turnDone = false;
		playedCards.clear();
		playedCards.addAll(newCards);

		//set pilevalue
		logicChecker.setPileValue(playedCards);

		//set passPlayCounter
		boolean reset = true; 
		for(int i = 1; i < 5; i++){ //all players
			for(int j = 1; i < 5; i++){ //all cards
				if(server.readDataCell("Card" + j + "Value", i) != -1 && server.readDataCell("Card" + j + "Suit", i) != -1)
					reset = false;
			}
		}

		if(reset)
			server.resetPileCount();


		//DEBUG
		//System.out.println(playedCards.size());
		//for(int i = 0; i < playedCards.size(); i++){
			//System.out.println(playedCards.get(i).getSuit());
		//}
		//
		passedCards.clear();
		currentHand = handPrint;
		otherHands = otherPlayers.clone();
		for(int i = 0; i < currentHand.getHandSize(); i++)
			raisedCards.add(false);
		repaint();	
	}

	//Helper functions
	public void createXMax(){
		xMax.clear();
		for(int i = 0; i < xMin.size()-1; i++)
			xMax.add(xMin.get(i+1));
		xMax.add(xMin.get(xMin.size()-1)+WIDTH/2);
	}

	private void cardClicker(MouseEvent me){
	
		for(int i = 0; i< xMin.size()-1; i++){					
			//bound each card			
			if(raisedCards.get(i)){ //card is raised
				if(me.getY()>(INITY+DELTAY) && me.getY()<(INITY+HEIGHT/2+DELTAY)){ //50 pixels raise atm
					if(raisedCards.get(i+1)){ //if next card is raised
						if(me.getX()>xMin.get(i) && me.getX()<xMax.get(i)){
							System.out.println(currentHand.getCardFromLoc(i).getValue() + " " + currentHand.getCardFromLoc(i).getSuit());
								cardQueue.remove(currentHand.getCardFromLoc(i)); //remove card from queue
								raisedCards.set(i, false);
							break;
						}
					}
					else{ //if next card isnt raised
						if((me.getX()>xMin.get(i) && me.getX()<xMax.get(i))||(me.getX()>xMin.get(i) && me.getX()<(xMin.get(i)+WIDTH/2) && me.getY()<INITY)){
							System.out.println(currentHand.getCardFromLoc(i).getValue() + " " + currentHand.getCardFromLoc(i).getSuit());
								cardQueue.remove(currentHand.getCardFromLoc(i)); //remove card from queue
								raisedCards.set(i, false);
							break;
						}
					}
				}
			}

			else{ //card is not raised
				if(me.getY()>INITY && me.getY()<(INITY+HEIGHT/2)){
					if(me.getX()>xMin.get(i) && me.getX()<xMax.get(i)){
						System.out.println(currentHand.getCardFromLoc(i).getValue() + " " + currentHand.getCardFromLoc(i).getSuit());
						if(logicChecker.checkClick(cardQueue, currentHand.getCardFromLoc(i))){
							cardQueue.add(currentHand.getCardFromLoc(i));
							raisedCards.set(i, true);
						}
						else{ //replacement for convenience of user
							cardQueue.clear();
							cardQueue.add(currentHand.getCardFromLoc(i));
							for(int j = 0; j < raisedCards.size(); j++)
								raisedCards.set(j, false);
							raisedCards.set(i, true);
						}
						break;
					}									
				}

			}	
		}
	

		//hard code last card
		if(me.getX()>xMin.get(xMin.size()-1) && me.getX()<xMax.get(xMax.size()-1)){
			if(raisedCards.get(xMin.size()-1)){ //card is raised
				if(me.getY()>(INITY+DELTAY) && me.getY()<(INITY+HEIGHT/2+DELTAY)){
					System.out.println(currentHand.getCardFromLoc(xMin.size()-1).getValue() + " " + currentHand.getCardFromLoc(xMin.size()-1).getSuit());
					if(logicChecker.checkClick(cardQueue, currentHand.getCardFromLoc(xMin.size()-1))){
						cardQueue.remove(currentHand.getCardFromLoc(xMin.size()-1));
						raisedCards.set(xMin.size()-1, false);
					}
				}

			}
			else{
				if(me.getY()>INITY && me.getY()<(INITY+HEIGHT/2)){
					System.out.println(currentHand.getCardFromLoc(xMin.size()-1).getValue() + " " + currentHand.getCardFromLoc(xMin.size()-1).getSuit());
					if(logicChecker.checkClick(cardQueue, currentHand.getCardFromLoc(xMin.size()-1))){
						cardQueue.add(currentHand.getCardFromLoc(xMin.size()-1));
						raisedCards.set(xMin.size()-1, true);
					}
					else{
						cardQueue.clear();
						cardQueue.add(currentHand.getCardFromLoc(xMin.size()-1));
						for(int j = 0; j < raisedCards.size(); j++)
							raisedCards.set(j, false);
						raisedCards.set(xMin.size()-1, true);
					}
				}
			}

		}
	
	}

	//TODO: Implement button clicking
	private void buttonClicker(MouseEvent me){

		if(me.getX()>PLAYBUTTONX && me.getX()<PLAYBUTTONX+BUTTONWIDTH && me.getY()>PLAYBUTTONY && me.getY()<PLAYBUTTONY+BUTTONHEIGHT && logicChecker.checkPlayButton(cardQueue)){
			if(logicChecker.checkPlay(cardQueue)){
				playedCards.clear();
				for(int i = 0; i < cardQueue.size(); i++){
					playedCards.add(cardQueue.get(i));
					raisedCards.remove(currentHand.getCardIndex(cardQueue.get(i)));
					currentHand.removeCard(cardQueue.get(i));

				}
				currentHand.sortHand();
				passedCards.addAll(cardQueue);
				cardQueue.clear();
				if(currentHand.getHandSize() == 0){
					noCards = true;
				}

				turnDone = true;

				//TODO:
				//change turn
			}
		}
		else if(me.getX()>PASSBUTTONX && me.getX()<PASSBUTTONX+BUTTONWIDTH  && me.getY()>PASSBUTTONY   && me.getY()<PASSBUTTONY+BUTTONHEIGHT  && logicChecker.checkPassButton(cardQueue)){
			if(logicChecker.checkPass(cardQueue)){
				//TODO:
				//change turn
				passedCards.clear();
				turnDone = true;
			}

			turnDone = true;
		}
	
	}

	private void parseCards(){
		//4 rows 13 columns   ****ROWS PARSE SUIT, COLS PARSE NUMBER****

		//2,3,4 of spades
		for(int i = 2; i > -1; i--)
			cardImages[0][2-i] = cardsSS.getSubimage(WIDTH, i*HEIGHT, WIDTH, HEIGHT);

		//5-10 of spades
		for(int i = 9; i > 3; i--)
			cardImages[0][12-i] = cardsSS.getSubimage(0, i*HEIGHT, WIDTH, HEIGHT);

		//J of spades
		cardImages[0][9] = cardsSS.getSubimage(0, 2*HEIGHT, WIDTH, HEIGHT);
		//Q of spades
		cardImages[0][10] = cardsSS.getSubimage(0, 0, WIDTH, HEIGHT); 
		//K of spades
		cardImages[0][11] = cardsSS.getSubimage(0, 1*HEIGHT, WIDTH, HEIGHT);
		//A of spades
		cardImages[0][12] = cardsSS.getSubimage(0, 3*HEIGHT, WIDTH, HEIGHT);

		//2 of hearts
		cardImages[1][0] = cardsSS.getSubimage(5*WIDTH, 2*HEIGHT, WIDTH, HEIGHT);
		//3-8 of hearts
		for(int i = 5; i > -1; i--)
			cardImages[1][6-i] = cardsSS.getSubimage(2*WIDTH, i*HEIGHT, WIDTH, HEIGHT);
		//9, 10 of hearts
		cardImages[1][7] = cardsSS.getSubimage(WIDTH, 9*HEIGHT, WIDTH, HEIGHT);
		cardImages[1][8] = cardsSS.getSubimage(WIDTH, 8*HEIGHT, WIDTH, HEIGHT);
		//J of hearts
		cardImages[1][9] = cardsSS.getSubimage(WIDTH, 6*HEIGHT, WIDTH, HEIGHT);
		//Q of hearts
		cardImages[1][10] = cardsSS.getSubimage(WIDTH, 4*HEIGHT, WIDTH, HEIGHT);
		//K of hearts
		cardImages[1][11] = cardsSS.getSubimage(WIDTH, 5*HEIGHT, WIDTH, HEIGHT);
		//A of hearts
		cardImages[1][12] = cardsSS.getSubimage(WIDTH, 7*HEIGHT, WIDTH, HEIGHT);
		//2-10 of diamonds
		for(int i = 9; i > 0; i--)
			cardImages[2][9-i] = cardsSS.getSubimage(3*WIDTH, i*HEIGHT, WIDTH, HEIGHT);
		//J of diamonds
		cardImages[2][9] = cardsSS.getSubimage(2*WIDTH, 9*HEIGHT, WIDTH, HEIGHT);
		//Q of diamonds
		cardImages[2][10] = cardsSS.getSubimage(2*WIDTH, 7*HEIGHT, WIDTH, HEIGHT);
		//K of diamonds
		cardImages[2][11] = cardsSS.getSubimage(2*WIDTH, 8*HEIGHT, WIDTH, HEIGHT);
		//A of diamonds
		cardImages[2][12] = cardsSS.getSubimage(3*WIDTH, 0, WIDTH, HEIGHT);
		//2 of clubs
		cardImages[3][0] = cardsSS.getSubimage(2*WIDTH, 6*HEIGHT, WIDTH, HEIGHT);
		//3,4 of clubs
		cardImages[3][1] = cardsSS.getSubimage(5*WIDTH, HEIGHT, WIDTH, HEIGHT);
		cardImages[3][2] = cardsSS.getSubimage(5*WIDTH, 0, WIDTH, HEIGHT);
		//5-10 of clubs
		for(int i = 9; i > 3; i--)
			cardImages[3][12-i] = cardsSS.getSubimage(4*WIDTH, i*HEIGHT, WIDTH, HEIGHT);
		//J of clubs
		cardImages[3][9] = cardsSS.getSubimage(4*WIDTH, 2*HEIGHT, WIDTH, HEIGHT);
		//Q of clubs
		cardImages[3][10] = cardsSS.getSubimage(4*WIDTH, 0, WIDTH, HEIGHT);
		//K of clubs
		cardImages[3][11] = cardsSS.getSubimage(4*WIDTH, HEIGHT, WIDTH, HEIGHT);
		//A of clubs
		cardImages[3][12] = cardsSS.getSubimage(4*WIDTH, 3*HEIGHT, WIDTH, HEIGHT);
	}

	public BufferedImage rotate90ToRight(BufferedImage inputImage){
		BufferedImage returnImage = new BufferedImage(HEIGHT, WIDTH, inputImage.getType());

		for(int x = 0; x < WIDTH; x++) {
			for( int y = 0; y < HEIGHT; y++ ) {
				returnImage.setRGB(HEIGHT-y-1, x, inputImage.getRGB(x, y));
			}
		}
		return returnImage;
	}

	public BufferedImage rotate90ToLeft(BufferedImage inputImage){
		BufferedImage returnImage = new BufferedImage(HEIGHT, WIDTH, inputImage.getType());

		for(int x = 0; x < WIDTH; x++) {
			for( int y = 0; y < HEIGHT; y++ ) {
				returnImage.setRGB(y, WIDTH-x-1, inputImage.getRGB(x, y));
			}
		}
		return returnImage;
	}

	public BufferedImage rotate180(BufferedImage inputImage) {
		BufferedImage returnImage = new BufferedImage(WIDTH, HEIGHT, inputImage.getType());

		for(int x = 0; x < WIDTH; x++){
			for(int y = 0; y < HEIGHT; y++){
				returnImage.setRGB(WIDTH-x-1, HEIGHT-y-1, inputImage.getRGB(x, y));
			}
		}
		return returnImage;
	}


	//DEBUG-----/

	public void animate(){
		for(this.x = 0; x<cROWS; x++){
			for(this.y = 0; y<cCOLS; y++){
				repaint();
				z+=20;
				try{
					Thread.sleep(25); //slows down animations
				}
				catch(Exception e){
					System.out.println("Exception in thread sleep " + e.toString());
				}
			}
		}
	}
	//---------/

	public boolean getNoCards(){
		return noCards;
	}

	public ArrayList<Card> getPassedCards(){
		return passedCards;
	}

	public boolean getTurnDone(){
		return turnDone;
	}

	public void setOrderNumPanel(int val){
		this.orderNum = val;
	}

}