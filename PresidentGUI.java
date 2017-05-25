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

	private PresidentPanel jpanel;

	public PresidentGUI(){

		super(NAME);
		setSize(WIDTH, HEIGHT);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setIconImage(new ImageIcon("Images/Cards/cardBack_blue5.png").getImage());

		this.setResizable(false);
		this.setLocationRelativeTo(null);

		jpanel = new PresidentPanel("Images/Spritesheets/playingCardBacks.png","Images/Spritesheets/playingCards.png");
		jpanel.setBackground(Color.GREEN);
		this.add(jpanel);

		this.setVisible(true);


		//DEBUG
		
		//jpanel.animate();
		
	}
	
	//DEBUG
	public void renderHandOnScreen(Player[] playerList){
		jpanel.renderAHand(playerList[0].getHand());
	}

}