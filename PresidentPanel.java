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
	private static final int cROWS = 4;
	private static final int cCOLS = 13;

	private static final int WIDTH = 140;
	private static final int HEIGHT = 190;

	//DEBUG------/
	private int x,  y, z;
	//-----------/

	public PresidentPanel(String cardBackPath, String cardsPath){

		//DEBUG-------/
		x = 0;
		y = 0;
		z = 0;
		//------------/

		this.cardBackPath = cardBackPath;
		this.cardsPath = cardsPath;

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

	}

	//DEBUG-----/

	public void animate(){
		for(this.x = 0; x<cROWS; x++){
			for(this.y = 0; y<cCOLS; y++){
				repaint();
				z+=20;
				try{
					Thread.sleep(500); //slows down animations
				}
				catch(Exception e){
					System.out.println("Exception in thread sleep " + e.toString());
				}
			}
		}
	}
	//---------/

	public void rerender(){
		repaint();
	}
	
	private void parseCards(){
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


	@Override
	public void paint(Graphics g){ //override paint method provided by JPanel
		super.paint(g);
		g.drawImage(cardImages[x][y], z, 0, WIDTH/2, HEIGHT/2, null); //140 190

	}

}