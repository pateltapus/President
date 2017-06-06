import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Graphics;

public class Waitscreen extends JPanel {

	private BufferedImage waitscreenImg;

	public Waitscreen(){
		try{
			waitscreenImg = ImageIO.read(new File("Images/Frames/WaitingForPlayers.png"));
		}
		catch(Exception e){
			System.out.println("Exception in Waitscreen load " + e.toString());
		}

	}
	@Override
	public void paint(Graphics g){ //override paint method provided by JPanel
		super.paint(g);
		g.drawImage(waitscreenImg, 0, 0, null);
	}


}