import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Graphics;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Loadscreen extends JPanel {

	private BufferedImage loadscreenImg;
	private boolean lobbyJoined;

	public Loadscreen(){

		this.lobbyJoined = false;

		try{
			loadscreenImg = ImageIO.read(new File("Images/Frames/Loadscreen.png"));
		}
		catch(Exception e){
			System.out.println("Exception in Loadscreen load" + e.toString());
		}

		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent me){
				//if hits join lobby then execute
				if(me.getX() >= 385 && me.getX() <= 510 && me.getY() >= 319 && me.getY() <= 494 )
					lobbyJoined = true;
			}
		});
	}

	public boolean getLobbyJoined(){
		return lobbyJoined;
	}

	@Override
	public void paint(Graphics g){ //override paint method provided by JPanel
		super.paint(g);
		g.drawImage(loadscreenImg, 0, 0, null);
	}



}