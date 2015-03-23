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
	private String[] colours = {"Black", "Blue", "Green", "Red", "White", "Yellow"};
	//private List<Integer> locations = Arrays.asList(13,26,29,34,50,53,91,94,103,112,117,132,138,141,155,174,197,198);
	private JPanel comboPanel;
	private JPanel fieldPanel;
	private JPanel playerPanel;
	private JComboBox<String> colourCombo;
	//private JComboBox<Integer> locationCombo;
	private JButton mapButton;
	private JButton secretButton;

	public gameStart(){
		Font font2 = new Font("Verdana", Font.PLAIN, 15);
		
		secretButton = new JButton("Check Location");
		
		JPanel blackPanel = new JPanel();
		blackPanel.setLayout(new FlowLayout());
		blackPanel.add(new JLabel("Black: "));
		blackPanel.add(secretButton);
		blackPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, blackPanel.getPreferredSize().height) );
		

		colourField = new JTextArea();
		locationField = new JTextArea();
		
		colourField.setText("");
		colourField.setMaximumSize(new Dimension(Integer.MAX_VALUE, colourField.getPreferredSize().height) );
		colourField.setFont(font2);
		colourField.setEditable(false);
		
		locationField.setText("");
		locationField.setMaximumSize(new Dimension(Integer.MAX_VALUE, locationField.getPreferredSize().height) );
		locationField.setFont(font2);
		locationField.setEditable(false);

		addButton = new JButton("add");
		startButton = new JButton("start");
		mapButton = new JButton("map");

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
		fieldPanel.add(blackPanel);
		fieldPanel.add(playerPanel);
		fieldPanel.add(comboPanel);
		fieldPanel.add(addButton);
		fieldPanel.add(mapButton);

		this.setBackground(Color.WHITE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 		this.setSize(1200, 900);
		//this.setLayout(new BorderLayout());
		this.add(fieldPanel);
	}
	public void addCombobox(){
	//comboPanel.removeAll();

    colourCombo = new JComboBox<String>(colours);
    colourCombo.setPreferredSize(new Dimension(80, 30));

    /*locationCombo = new JComboBox<Integer>(locations);
    locationCombo.setPreferredSize(new Dimension(80, 30));*/


    //JPanel comboPanel = new JPanel();
    comboPanel.add(new JLabel("Player:"));
	comboPanel.add(colourCombo);
    //comboPanel.add(new JLabel("  Location:"));
    //comboPanel.add(locationCombo);

    /*label = new JLabel();
    JPanel labelPanel = new JPanel();
    labelPanel.add(label);

    getContentPane().add(p, BorderLayout.CENTER);
    getContentPane().add(labelPanel, BorderLayout.PAGE_END);*/
  }
	public void addSecretListener (ActionListener listenSecretButton){
		secretButton.addActionListener(listenSecretButton);
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

	public void addMapListener(ActionListener listenMapButton){
		mapButton.addActionListener(listenMapButton);
	}

	public String getColour(){
		return (String)colourCombo.getSelectedItem();
	} 
	/*public int getStartLocation(){
		return(int)locationCombo.getSelectedItem();
	}*/
	public void addText(String colour, String location){
		colourField.append("\n" + colour);
		colourField.setMaximumSize(new Dimension(Integer.MAX_VALUE, colourField.getPreferredSize().height) );
		
		locationField.append("\n" + location);
		locationField.setMaximumSize(new Dimension(Integer.MAX_VALUE, locationField.getPreferredSize().height) );
		playerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, playerPanel.getPreferredSize().height) );

		//colours.remove(colour);
		//locations.remove(location);
	}
	public void disapper(){
		this.setVisible(false);
	}
	public void removeAddButton(){
		fieldPanel.remove(addButton);
	}
		

