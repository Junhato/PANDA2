package solution;

//import scotlandyard.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.lang.*;
import java.awt.image.BufferedImage;
//make second frame to choose palyer

public class gameStart extends JFrame{
	private JButton startButton;
	private JButton addButton;
	private JTextArea colourField;
	private JTextArea locationField;
	private String[] colours = {"Black", "Blue", "Green", "Red", "White", "Yellow"};
	private JPanel comboPanel;
	private JPanel fieldPanel;
	private JPanel playerPanel;
	private JComboBox<String> colourCombo;
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
 		this.setSize(500, 400);
		this.add(fieldPanel);
	}
//add combobox for coosing colour
	public void addCombobox(){

		colourCombo = new JComboBox<String>(colours);
		colourCombo.setPreferredSize(new Dimension(80, 30));

	    
		comboPanel.add(new JLabel("Player:"));
		comboPanel.add(colourCombo);
	}
//add action listener to secretButton
	public void addSecretListener (ActionListener listenSecretButton){
		secretButton.addActionListener(listenSecretButton);
	}
//add addButton
	public void addListener(ActionListener listenAddButton) {

		addButton.addActionListener(listenAddButton);
	} 
//add startButton to panel	
	public void addStart(){
		fieldPanel.add(startButton);
	}
//add action listener to startButton	
	public void addStartListener(ActionListener listenStartButton){
		startButton.addActionListener(listenStartButton);
	}
//add action listener to mapButton
	public void addMapListener(ActionListener listenMapButton){
		mapButton.addActionListener(listenMapButton);
	}
//return string of selected colour in combobox
	public String getColour(){
		return (String)colourCombo.getSelectedItem();
	} 
//add text to colourField and locationField
	public void addText(String colour, String location){
		colourField.append("\n" + colour);
		colourField.setMaximumSize(new Dimension(Integer.MAX_VALUE, colourField.getPreferredSize().height) );
		
		locationField.append("\n" + location);
		locationField.setMaximumSize(new Dimension(Integer.MAX_VALUE, locationField.getPreferredSize().height) );
		playerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, playerPanel.getPreferredSize().height) );

	}
//make this frame invisible
	public void disapper(){
		this.setVisible(false);
	}
//remove button to add player
	public void removeAddButton(){
		fieldPanel.remove(addButton);
	}
		
}
