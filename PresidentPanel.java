import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Graphics;

public class PresidentPanel extends JPanel{

	private BufferedImage cardBackSS, cardsSS;
	private BufferedImage[][] cardBackImages, cardImages;
	private static String cardBackPath, cardsPath;

	private static final int cbROWS = 5;
	private static final int cbCOLS = 3;
	private static final cROWS = 4;
	private static final cCOLS = 13;

	private static final int WIDTH = 140;
	private static final int HEIGHT = 190;

	//DEBUG------/
	private int x,  y;
	//-----------/

	public PresidentPanel(String cardBackPath, String cardsPath){

		//DEBUG-------/
		x = 0;
		y = 0;
		//------------/

		this.cardBackPath = cardBackPath;
		this.cardsPath = cardsPath;

		cardBackImages = new BufferedImage[cbROWS][cbCOLS]; //5 rows 3 columns,  ****ROWS PARSE DESIGN, COLS PARSE COLOR****
		cardsPath = new BufferedImage[cROWS][cCOLS] ;//4 rows 13 columns   ****ROWS PARSE SUIT, COLS PARSE NUMBER****

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

	}

	//DEBUG-----/

	public void animate(){
		y=(y+1)%3;
		repaint();
	}
	//---------/

	public void parseCards(){
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

			4 rows 13 columns   ****ROWS PARSE SUIT, COLS PARSE NUMBER****
		*/
		//cardImages[i][j] = cardBackSS.getSubimage(j*WIDTH, i*HEIGHT, WIDTH, HEIGHT);
		
		//2,3,4 of Spades
		for(int i = 2; i > -1; i--)
			cardImages[0][2-i] = cardBackSS.getSubimage(WIDTH, i*HEIGHT, WIDTH, HEIGHT);

		//5-10 of spades
		for(int i = 9; i > 3; i--)
			cardImages[0][12-i] = cardBackSS.getSubimage(0, i*HEIGHT, WIDTH, HEIGHT);

		//J of spades
		cardImages[0][9] = cardBackSS.getSubimage(0, 2*HEIGHT, WIDTH, HEIGHT);
		//Q of spades
		cardImages[0][10] = cardBackSS.getSubimage(0, 0, WIDTH, HEIGHT);
		//K of spades
		cardImages[0][11] = cardBackSS.getSubimage(0, 1*HEIGHT, WIDTH, HEIGHT);
		//A of spades
		cardImages[0][12] = cardBackSS.getSubimage(0, 3*HEIGHT, WIDTH, HEIGHT);




	}














	@Override
	public void paint(Graphics g){ //override paint method provided by JPanel
		super.paint(g);
		g.drawImage(cardBackImages[4][2], 0, 0, null); //140 190
		
	}


	
}