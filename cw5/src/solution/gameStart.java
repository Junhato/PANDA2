package solution;

//import scotlandyard.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.lang.*;
import java.awt.image.BufferedImage;
public class gameStart extends JFrame{
	private JButton startButton;
	private JButton addButton;
	private JTextArea colourField;
	private JTextArea locationField;
	private ImageIcon icon;
	private BufferedImage img = null;
	private String[] colours = {"Black", "Blue", "Green", "Red", "White", "Yellow"};
	private String[] locations = {"67", "13", "46", "89", "111", "140"};
	private JPanel comboPanel;
	private JPanel fieldPanel;
	private JPanel playerPanel;
	private JComboBox<String> colourCombo;
	private JComboBox<String> locationCombo;
	
	public gameStart(){
		Font font2 = new Font("Verdana", Font.PLAIN, 15);

		colourField = new JTextArea();
		locationField = new JTextArea();
		
		colourField.setText("Black\n");
		colourField.setMaximumSize(new Dimension(Integer.MAX_VALUE, colourField.getPreferredSize().height) );
		colourField.setFont(font2);
		colourField.setEditable(false);
		
		locationField.setText("67\n");
		locationField.setMaximumSize(new Dimension(Integer.MAX_VALUE, locationField.getPreferredSize().height) );
		locationField.setFont(font2);
		locationField.setEditable(false);

		addButton = new JButton("add");
		startButton = new JButton("start");
		
		JLabel imageLabel = new JLabel();
		setImage();
		icon = new ImageIcon(img);
		imageLabel.setIcon(icon);
		
		JPanel imagePanel = new JPanel();
		imagePanel.add(imageLabel);
		
		comboPanel = new JPanel();
		addCombobox();
		comboPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, comboPanel.getPreferredSize().height) );

		playerPanel = new JPanel();
		playerPanel.setLayout(new FlowLayout());
		playerPanel.add(new JLabel("Player:"));

		playerPanel.add(colourField);
		playerPanel.add(new JLabel("Location:"));

		playerPanel.add(locationField);
		playerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, playerPanel.getPreferredSize().height) );

		fieldPanel = new JPanel();
		fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.PAGE_AXIS));
		fieldPanel.add(playerPanel);
		fieldPanel.add(comboPanel);
		fieldPanel.add(addButton);
		
		this.setBackground(Color.WHITE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 		this.setSize(1200, 900);
		this.setLayout(new BorderLayout());
		this.add(imagePanel, BorderLayout.CENTER);
		this.add(fieldPanel, BorderLayout.EAST);
	}
	public void setImage(){
	try
		{
			img = ImageIO.read(new File("map.jpg"));
			//g2d = img.createGraphics();

		}
		catch( IOException e )
		{
			System.out.println(e);
		}
	}

	public void addCombobox(){
	//comboPanel.removeAll();

    colourCombo = new JComboBox<String>(colours);
    colourCombo.setPreferredSize(new Dimension(80, 30));

    locationCombo = new JComboBox<String>(locations);
    locationCombo.setPreferredSize(new Dimension(80, 30));


    //JPanel comboPanel = new JPanel();
    comboPanel.add(new JLabel("Player:"));
	comboPanel.add(colourCombo);
    comboPanel.add(new JLabel("  Location:"));
    comboPanel.add(locationCombo);

    /*label = new JLabel();
    JPanel labelPanel = new JPanel();
    labelPanel.add(label);

    getContentPane().add(p, BorderLayout.CENTER);
    getContentPane().add(labelPanel, BorderLayout.PAGE_END);*/
  }

	public void addListener(ActionListener listenAddButton) {

		addButton.addActionListener(listenAddButton);
	} 
	
	public void addStart(){
		fieldPanel.add(startButton);
	}
	
	public void addStartListener(ActionListener listenStartButton){
		startButton.addActionListener(listenStartButton);
	}
	public String getColour(){
		return (String)colourCombo.getSelectedItem();
	} 
	public String getStartLocation(){
		return(String)locationCombo.getSelectedItem();
	}
	public void addText(String colour, String location){
		colourField.append(colour +"\n");
		colourField.setMaximumSize(new Dimension(Integer.MAX_VALUE, colourField.getPreferredSize().height) );
		
		locationField.append(location + "\n");
		locationField.setMaximumSize(new Dimension(Integer.MAX_VALUE, locationField.getPreferredSize().height) );
		playerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, playerPanel.getPreferredSize().height) );

		//colours.remove(colour);
		//locations.remove(location);
	}
	public void disapper(){
		this.setVisible(false);
	}


}
