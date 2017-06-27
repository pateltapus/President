import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import javax.swing.JTextField;
import java.awt.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;


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
			@Override
			public void mousePressed(MouseEvent me){
				//if hits join lobby then execute
				if(me.getX() >= 602 && me.getX() <= 639 && me.getY() >= 466 && me.getY() <= 502) //&& if name is entered
					if(!(textField.getText().equals(""))){
						name = textField.getText();
					}
					
			}
		});

		addTextField();

		textField.addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent ke){
				if(ke.getKeyCode() == KeyEvent.VK_ENTER){
					if(!(textField.getText().equals(""))){
						name = textField.getText();
					}
				}

			}
		});
	}

	public void addTextField(){	
		textField = new JTextField(30);
		Font font = new Font("SansSerif", Font.BOLD, 18);
		textField.setBorder(null);
		textField.setBounds(279,472,315,25);
		textField.setFont(font);
		
		this.add(textField);
	
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