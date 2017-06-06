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

		while(lobbyJoined == false){
			lobbyJoined = jpanelLoad.getLobbyJoined();
		}

		this.remove(jpanelLoad);

		//create namescreen
		jpanelName = new Namescreen();
		jpanelName.addTextField();
		this.add(jpanelName);
		this.setVisible(true);

		while(name.equals("")){
			name = jpanelName.getName();
		}

		this.remove(jpanelName);

		//create waitscreen

		jpanelWait = new Waitscreen();
		this.add(jpanelWait);
		this.setVisible(true);

		while(!playersFound){
			jpanelWait.incrementCount();
			jpanelWait.repaint();
		}

		this.remove(jpanelWait);






		


		//DEBUG
		
		//jpanel.animate();

		
	}

	public String getName(){
		return name;
	}

	public void setPlayersFound(){
		this.playersFound = true;
	}
	
	//DEBUG
	public void renderHandOnScreen(Player[] playerList){
		jpanelGame = new PresidentPanel("Images/Spritesheets/playingCardBacks.png","Images/Spritesheets/playingCards.png");
		this.add(jpanelGame);
		this.setVisible(true);
		jpanelGame.renderAHand(playerList[0].getHand());
	}

}