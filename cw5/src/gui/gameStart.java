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
		locati
