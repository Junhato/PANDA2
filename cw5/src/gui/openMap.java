package solution;

//import scotlandyard.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.lang.*;
import java.awt.image.BufferedImage;

public class openMap extends JFrame{
	private ImageIcon icon;
	private BufferedImage img = null;

	public openMap(){
		JLabel imageLabel = new JLabel();
		setImage();
		icon = new ImageIcon(img);
		imageLabel.setIcon(icon);
		int width  = img.getWidth();
		int height = img.getHeight();
		JPanel imagePanel = new JPanel();
		imagePanel.add(imageLabel);
		imagePanel.setMinimumSize(new Dimension(width, height));

		this.setBackground(Color.WHITE);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 		//this.setSize(1200, 900);
		this.setMinimumSize(new Dimension(width, height));

		//this.setLayout(new BorderLayout());
		this.add(imagePanel);



	}
	public void setImage(){
		try
		{
			img = ImageIO.read(new File("../resources/map.jpg"));
			//g2d = img.createGraphics();

		}
		catch( IOException e )
		{
			System.out.println(e);
		}
	}
}


