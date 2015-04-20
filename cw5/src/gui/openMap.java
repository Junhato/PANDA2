package solution;

//import scotlandyard.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.lang.*;
import java.awt.image.BufferedImage;
//make frame to show map
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
		this.setMinimumSize(new Dimension(width, height));

		this.add(imagePanel);



	}
//load image of the map
	public void setImage(){
		try
		{
			img = ImageIO.read(new File("../resources/map.jpg"));

		}
		catch( IOException e )
		{
			System.out.println(e);
		}
	}
}

