//thin this down later
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.awt.image.*;

/*
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JPanel???
import java.awt.Color;
import java.awt.image.BufferedImage;???
import java.io.File;???
import javax.imageio.ImageIO???
import java.awt.Graphics;???

*/




public class PresidentGUI extends JFrame{

	private static final int WIDTH = 900;
	private static final int HEIGHT = WIDTH/12*9;
	private static final String NAME = "President";

	private Loadscreen jpanelLoad;
	private Namescreen jpanelName;
	private Waitscreen jpanelWait;
	private PresidentPanel jpanelGame;
	private String name = "";
	private boolean playersFound = false;
	private Server server;
	private int orderNum = 0;

	public PresidentGUI(){

		super(NAME);
		setSize(WIDTH, HEIGHT);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setIconImage(new ImageIcon("Images/Cards/cardBack_blue5.png").getImage());

		this.setResizable(false);
		this.setLocationRelativeTo(null);

		//create loadscreen
		jpanelLoad = new Loadscreen();
		this.add(jpanelLoad);
		this.setVisible(true);

		boolean lobbyJoined = false;
		server = new Server();

		while(lobbyJoined == false){
			lobbyJoined = jpanelLoad.getLobbyJoined();
		}

		this.remove(jpanelLoad);

		//create namescreen
		jpanelName = new Namescreen();
		jpanelName.setLayout(null);
		//jpanelName.addTextField();
		this.add(jpanelName);
		this.setVisible(true);

		while(name.equals("")){
			name = jpanelName.getName();
		}

		this.remove(jpanelName);

		//DEBUG
		
		//jpanel.animate();
		this.addWindowListener(new java.awt.event.WindowAdapter() {
	    	@Override
	    	public void windowClosing(java.awt.event.WindowEvent windowEvent) {
	    		//add SQL stuff here
	    		int count = 0;

	    		System.out.println(orderNum); //debug

	    		server.sendDataCell("ingame", 0, orderNum);

	    		System.out.println(server.readDataCell("ingame", orderNum)); //debug

	    		for(int i = 1; i < server.getRows()+1; i++){
	    			if(server.readDataCell("ingame", i) == 0)
	    				count++;
	    		}

	    		if(count == server.getRows())
	    			server.clearPresTable();

	            System.exit(0);
			}
		});

		
	}

	public String getName(){
		return name;
	}

	public void setPlayersFound(){
		this.playersFound = true;
	}
	
	public void createWaitScreen(){
		//create waitscreen

		jpanelWait = new Waitscreen();
		this.add(jpanelWait);
		this.setVisible(true);
	}

	public void updateWaitScreen(){
		jpanelWait.incrementCount();
		jpanelWait.repaint();
	}

	public void closeWaitScreen(){
		this.remove(jpanelWait);
	}

	public void createGameScreen(){
		jpanelGame = new PresidentPanel("Images/Spritesheets/playingCardBacks.png","Images/Spritesheets/playingCards.png");
	}

	//DEBUG
	public void renderHandOnScreen(Player currPlayer, int[] otherPlayers, ArrayList<Card> playedCards){

		int players[] = new int[3];
		players = otherPlayers.clone();

		//System.out.println(playedCards.size());
		ArrayList<Card> temp = new ArrayList<Card>();
		temp.clear();
		temp.addAll(playedCards);
		
		this.add(jpanelGame);
		this.setVisible(true);

		jpanelGame.renderAHand(currPlayer.getHand(), players, temp);
	}

	public boolean checkDone(){
		return jpanelGame.getNoCards();
	}

	public ArrayList<Card> getPileCards(){
		return jpanelGame.getPassedCards();
	}

	public boolean getTurnOver(){
		return jpanelGame.getTurnDone();
	}

	public void setOrderNum(int orderNum){
		int temp;
		temp = orderNum;
		this.orderNum =orderNum;
		System.out.println(temp);
		jpanelGame.setOrderNumPanel(temp);
	}

}