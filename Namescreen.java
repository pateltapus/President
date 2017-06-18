import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import javax.swing.JTextField;
import java.awt.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Namescreen extends JPanel {

	private BufferedImage namescreenImg;
	private JTextField textField;
	private String name = "";


	public Namescreen(){



		try{
			namescreenImg = ImageIO.read(new File("Images/Frames/EnterNameScreen.png"));
		}
		catch(Exception e){
			System.out.println("Exception in Namescreen load" + e.toString());
		}

		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent me){
				//if hits join lobby then execute
				if(me.getX() >= 602 && me.getX() <= 639 && me.getY() >= 466 && me.getY() <= 502) //&& if name is entered
					if(!(textField.getText().equals(""))){
						name = textField.getText();
					}

			}
		});
	}

	public void addTextField(){
		//this.textField.setVisible(false);
		/*this.textField = new JTextField(30);
		this.textField.setBounds(273,476,328,36);

		this.add(this.textField);
		*/
		JTextField textInput = new JTextField(30);
		Font font = new Font("SansSerif", Font.BOLD, 18);
		textInput.setBorder(null);
		textInput.setBounds(279,470,315,25);
		textInput.setFont(font);
		//textInput.setHorizontalAlgnment();
		this.add(textInput);
		//repaint();
	}

	public String getName(){
		return this.name;
	}


	@Override
	public void paint(Graphics g){ //override paint method provided by JPanel
		System.out.println("FLAGx");
		super.paint(g);
		g.drawImage(namescreenImg, 0, 0, null);
	}



}