import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Graphics;

public class Waitscreen extends JPanel {

	private BufferedImage waitscreenImg0;
	private BufferedImage waitscreenImg1;
	private BufferedImage waitscreenImg2;
	private BufferedImage waitscreenImg3;
	private int count = 0;

	public Waitscreen(){
		try{
			waitscreenImg0 = ImageIO.read(new File("Images/Frames/WaitingForPlayers0.png"));
		}
		catch(Exception e){
			System.out.println("Exception in Waitscreen0 load " + e.toString());
		}

		try{
			waitscreenImg1 = ImageIO.read(new File("Images/Frames/WaitingForPlayers1.png"));
		}
		catch(Exception e){
			System.out.println("Exception in Waitscreen1 load " + e.toString());
		}

		try{
			waitscreenImg2 = ImageIO.read(new File("Images/Frames/WaitingForPlayers2.png"));
		}
		catch(Exception e){
			System.out.println("Exception in Waitscreen2 load " + e.toString());
		}

		try{
			waitscreenImg3 = ImageIO.read(new File("Images/Frames/WaitingForPlayers3.png"));
		}
		catch(Exception e){
			System.out.println("Exception in Waitscreen3 load " + e.toString());
		}

	}
	@Override
	public void paint(Graphics g){ //override paint method provided by JPanel
		super.paint(g);
		switch(count/500000%4){
			case 0: g.drawImage(waitscreenImg0, 0, 0, null);
					break;

			case 1: g.drawImage(waitscreenImg1, 0, 0, null);
					break;

			case 2: g.drawImage(waitscreenImg2, 0, 0, null);
					break;

			case 3: g.drawImage(waitscreenImg3, 0, 0, null);
					break;
		}
	}

	public void incrementCount(){
		count++;
	}



}