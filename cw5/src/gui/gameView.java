package solution;

import java.awt.event.ActionListener;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.image.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Image;

import java.io.*;
import java.text.*;
import java.util.*;

import javax.imageio.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JTextArea;
import java.awt.event.*;

import java.lang.reflect.Field;
import java.lang.*;

//frame for palying game

public class gameView extends JFrame {
	private JTextArea statusField;
	private JTextArea turnField;
	private JTextArea roundField;
	private JPanel ticketsPanel;
	private JTextArea MrXField;
	private JTextArea playerField;
	private JLabel imageLabel;
	private BufferedImage img = null;
	private Map<Integer, List<Integer>> coordinateMap = new HashMap<Integer, List<Integer>>();
	private Graphics2D g2d;
	private ImageIcon icon;
	private JButton moveButton;
	private JTextField moveField;
	private JTextArea moveArea;
	private JPanel playerPanel1;
	private JPanel playerPanel2;
	private Image underground;
	private Image londonbus;
	private Image taxi;
	private JPanel imagePanel;

	private JButton saveButton;
	private JButton finishButton;
	private JButton newButton;
	private JPanel movePanel;
	private Font font2;
	private JFrame statusFrame;

 	public gameView(){
		font2 = new Font("Verdana", Font.BOLD, 15);
		Font font3 = new Font("Verdana", Font.BOLD, 20);
		Font font4 = new Font("Verdana", Font.BOLD, 30);

		statusField  = new JTextArea();
		statusField.setMaximumSize(new Dimension(Integer.MAX_VALUE, statusField.getPreferredSize().height) );
		statusField.setFont(font3);

		
		
		turnField  = new JTextArea();
		turnField.setMaximumSize(new Dimension(Integer.MAX_VALUE, turnField.getPreferredSize().height) );
		turnField.setFont(font3);

		roundField  = new JTextArea();
		roundField.setMaximumSize(new Dimension(Integer.MAX_VALUE, roundField.getPreferredSize().height) );
		roundField.setFont(font4);

		
		MrXField  = new JTextArea();
		MrXField.setMaximumSize(new Dimension(Integer.MAX_VALUE, MrXField.getPreferredSize().height) );
		MrXField.setFont(font2);

		imageLabel = new JLabel();
		imageLabel.setBackground(Color.WHITE);
		moveButton = new JButton("Make Move");

		moveField = new JTextField();
		moveField.setMaximumSize(new Dimension(100, 20));
		moveField.setFont(font2);

		moveArea = new JTextArea();
		moveArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, moveArea.getPreferredSize().height) );
		moveArea.setFont(font2);

		playerPanel1 = new JPanel();
		playerPanel1.setMaximumSize(new Dimension(Integer.MAX_VALUE, playerPanel1.getPreferredSize().height) );
		
		playerPanel2 = new JPanel();
		playerPanel2.setMaximumSize(new Dimension(Integer.MAX_VALUE, playerPanel2.getPreferredSize().height) );

		JPanel topPanel = new JPanel();
		movePanel = new JPanel();
		imagePanel = new JPanel();
		ticketsPanel = new JPanel();
		ticketsPanel.setLayout(new BoxLayout(ticketsPanel, BoxLayout.PAGE_AXIS));


		playerPanel1.setOpaque(false);
		playerPanel2.setOpaque(false);
		topPanel.setOpaque(false);
		movePanel.setOpaque(false);
		imagePanel.setOpaque(false);

		//make background
		Image image = null;
		try
		{
			image = ImageIO.read(new File("./solution/london.jpg"));
			//image = im.getScaledInstance(im.getWidth(), im.getHeight(), Image.SCALE_SMOOTH);
			

		}
		catch( IOException e )
		{
			System.out.println(e);
		}

		JLabel back = new JLabel(new ImageIcon(image));
		this.setContentPane(back);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 		this.setSize(1400, 1000);

		back.setLayout(new BorderLayout());
		playerPanel1.setLayout(new FlowLayout());
		movePanel.setLayout(new BoxLayout(movePanel, BoxLayout.PAGE_AXIS));

		imageLabel.add(statusField);
		roundField.setBackground(new Color(255, 175, 175, 200));
		turnField.setBackground(new Color(255, 175, 175, 200));
		MrXField.setBackground(new Color(250, 250, 250, 200));

		movePanel.add(roundField);
		movePanel.add(turnField);
		setTickets();
		movePanel.add(ticketsPanel);
		movePanel.add(playerPanel1);
		movePanel.add(playerPanel2);
		movePanel.add(MrXField);

		
		saveButton = new JButton("Save Game");
		saveButton.setBackground(Color.WHITE);

		finishButton = new JButton("New Game");
		finishButton.setBackground(Color.WHITE);

		
		
		topPanel.add(saveButton);
		topPanel.add(finishButton);
		
		readFile();
		resetImage();
		createImage();
		imagePanel.add(imageLabel);
		this.add(movePanel, BorderLayout.EAST);
		this.add(topPanel, BorderLayout.NORTH);
		this.add(imagePanel, BorderLayout.CENTER);


		
	}

//add action listener to saveButton
public void addSaveListener(ActionListener listenSaveButton) {
	saveButton.addActionListener(listenSaveButton);
}
//add action listener to finishButton
public void addFinishListener(ActionListener listenFinishButton) {
	finishButton.addActionListener(listenFinishButton);
}
//add action listener to moveButton
public void addMoveListener(ActionListener listenMoveButton) {

	moveButton.addActionListener(listenMoveButton);
} 

//read pos.txt to get coordinate
public void readFile(){
	File file = new File("../resources/pos.txt");	
		Scanner in = null;
        try 
        {
			in = new Scanner(file);
		} 
        catch (FileNotFoundException e) 
        {
			System.out.println(e);
		}

        
        // get the number of nodes
        String topLine = in.nextLine();
	
        int numberOfNodes = Integer.parseInt(topLine);
        
        
        for(int i = 0; i < numberOfNodes; i++)
        {
        	String line = in.nextLine();
       
        	String[] parts = line.split(" ");
        	List<Integer> pos = new ArrayList<Integer>();
        	pos.add(Integer.parseInt(parts[1]));
        	pos.add(Integer.parseInt(parts[2]));
		System.out.println(pos);
        	
        	int key = Integer.parseInt(parts[0]);
		System.out.println(key);

        	coordinateMap.put(key, pos);
        }
}
//set map image
public void resetImage(){
	try
		{
			img = ImageIO.read(new File("../resources/map.jpg"));
			g2d = img.createGraphics();

		}
		catch( IOException e )
		{
			System.out.println(e);
		}


	g2d = img.createGraphics();
        BasicStroke bs = new BasicStroke(4);
	g2d.setStroke(bs);
	imageLabel.removeAll();

}
//create imaage for underground, bus and taxi
public void createImage(){
	
	try{
		BufferedImage under = ImageIO.read(new File("./solution/Undergroundimg.png"));
		underground = under.getScaledInstance(under.getWidth()/5, under.getHeight()/5, Image.SCALE_SMOOTH);
		BufferedImage london = ImageIO.read(new File("./solution/londonbus.PNG"));
		londonbus = london.getScaledInstance(london.getWidth()/10, london.getHeight()/10, Image.SCALE_SMOOTH);
		BufferedImage ta = ImageIO.read(new File("./solution/taxi.PNG"));
		taxi = ta.getScaledInstance(ta.getWidth()/10, ta.getHeight()/10, Image.SCALE_SMOOTH);



	}
	catch(IOException e){
		System.err.println(e);
	}

}
//draw circle at the position of player
public void setImage(String colour, int location){
	List<Integer> pos = coordinateMap.get(location);
	try{
	Color color;
	Field field = Class.forName("java.awt.Color").getField(colour.toLowerCase()); 
	color = (Color)field.get(null);
        g2d.setColor(color);

	}
	catch(ClassNotFoundException e){
		System.err.println(e);
	}
	catch(NoSuchFieldException e){
		System.err.println(e);
	}
	catch(IllegalAccessException e){
		System.err.println(e);
	}

	System.out.println(g2d);

	g2d.drawOval(pos.get(0)-15, pos.get(1)-15, 30, 30);
	icon = new ImageIcon(img);
	imageLabel.setIcon(icon);

	
}
//draw line for valid moves
public void drawMove(String colour, int target, int location){
	List<Integer> p = coordinateMap.get(location);
	List<Integer> t = coordinateMap.get(target);

	try{
	Color color;
	Field field = Class.forName("java.awt.Color").getField(colour.toLowerCase()); 
	color = (Color)field.get(null);
        g2d.setColor(color);

	}
	catch(ClassNotFoundException e){
		System.err.println(e);
	}
	catch(NoSuchFieldException e){
		System.err.println(e);
	}
	catch(IllegalAccessException e){
		System.err.println(e);
	}
	
	BasicStroke dashed =new BasicStroke(5.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{9}, 0.0f);
	g2d.setStroke(dashed);

	g2d.drawLine(p.get(0), p.get(1), t.get(0), t.get(1));
	icon = new ImageIcon(img);
	imageLabel.setIcon(icon);
}
//draw button for move using underground
public JButton drawUnderground(int location, int index){
	List<Integer> p = coordinateMap.get(location);
	JButton button = new JButton(String.valueOf(location), new ImageIcon(underground));

	button.setBounds(p.get(0)-10, p.get(1)-30, 100, 20);
	button.setActionCommand(String.valueOf(index));
	button.setBackground(Color.WHITE);

	imageLabel.add(button);

	return button;

}
//draw button for move using bus
public JButton drawLondonbus(int location, int index){
	List<Integer> p = coordinateMap.get(location);
	JButton button = new JButton(String.valueOf(location), new ImageIcon(londonbus));

	button.setBounds(p.get(0), p.get(1)-40, 100, 20);
	button.setActionCommand(String.valueOf(index));
	button.setBackground(Color.WHITE);

	imageLabel.add(button);
	return button;
}
// draw button for move using taxi
public JButton drawTaxi(int location, int index){
	List<Integer> p = coordinateMap.get(location);
	JButton button = new JButton(String.valueOf(location), new ImageIcon(taxi));
	button.setBounds(p.get(0)-30, p.get(1)-30, 100, 20);
	button.setActionCommand(String.valueOf(index));
	button.setBackground(Color.WHITE);
	imageLabel.add(button);
	return button;
}
//draw button for secret move
public JButton drawSecret(int location, int index){
	List<Integer> p = coordinateMap.get(location);
	JButton button = new JButton("S " + String.valueOf(location));

	button.setBounds(p.get(0)+10, p.get(1)-40, 80, 20);
	button.setActionCommand(String.valueOf(index));
	button.setBackground(Color.LIGHT_GRAY);

	imageLabel.add(button);
	return button;
}
//draw button for double move
public JButton drawDouble(int location, int index){
	List<Integer> p = coordinateMap.get(location);
	JButton button = new JButton("D " + String.valueOf(location));

	button.setBounds(p.get(0)-20, p.get(1)-30, 80, 20);
	button.setActionCommand(String.valueOf(index));
	button.setBackground(Color.GRAY);

	imageLabel.add(button);
	return button;
}
//draw button to pass
public JButton drawPass(int location, int index){
	List<Integer> p = coordinateMap.get(location);
	JButton button = new JButton("Pass" );

	button.setBounds(p.get(0), p.get(1)-20, 80, 20);
	button.setActionCommand(String.valueOf(index));
	button.setBackground(Color.YELLOW);

	imageLabel.add(button);
	return button;
}
//make frame to tell the winner when the game is over
public JFrame setStatus(String s){
	statusFrame = new JFrame();
	statusFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 	statusFrame.setSize(600, 200);
	JTextArea status = new JTextArea();
	status.setFont(new Font("Verdana", Font.BOLD, 50));

	status.setText(s);
	status.setEditable(false);
	status.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60) );
	JPanel panel = new JPanel();
	panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, panel.getPreferredSize().height) );
	
	panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
	
	addFinishListener(new newListener());
	panel.add(status);
	panel.add(finishButton);
	statusFrame.add(panel);
	statusFrame.setVisible(true);
	return statusFrame;
		
}
//action listener to close status frame
class newListener implements ActionListener {
	public void actionPerformed(ActionEvent actionEvent) {
		statusFrame.setVisible(false);
	}
}
//set text of whose turn in turnField
public void setTurn(String player){
	turnField.setText(player + "'s turn");
	turnField.setEditable(false);
	turnField.setMaximumSize(new Dimension(Integer.MAX_VALUE, turnField.getPreferredSize().height) );
	try{
	Color color;
	Field field = Class.forName("java.awt.Color").getField(player.toLowerCase()); 
	color = (Color)field.get(null);
	turnField.setForeground(color);
	}
	catch(ClassNotFoundException e){
		System.err.println(e);
	}
	catch(NoSuchFieldException e){
		System.err.println(e);
	}
	catch(IllegalAccessException e){
		System.err.println(e);
	}



}
//set text of round in roundField
public void setRound(int round){
	roundField.setText("Round" + round +"              ");
	roundField.setEditable(false);
	roundField.setMaximumSize(new Dimension(Integer.MAX_VALUE, roundField.getPreferredSize().height) );


}
//reset the tickets panel
public void setTickets(){
	ticketsPanel.removeAll();

}
//set text of tickets player hava in ticketsField
public void addTickets(String player, String inf){
	JTextArea ticketsField = new JTextArea();
	ticketsField.repaint();

	ticketsField.setBackground(new Color(255, 175, 175, 200));
	ticketsField.setFont(font2);

	ticketsField.setText(inf);
	try{
	Color color;
	Field field = Class.forName("java.awt.Color").getField(player.toLowerCase()); 
	color = (Color)field.get(null);
	ticketsField.setForeground(color);
	}
	catch(ClassNotFoundException e){
		System.err.println(e);
	}
	catch(NoSuchFieldException e){
		System.err.println(e);
	}
	catch(IllegalAccessException e){
		System.err.println(e);
	}
	ticketsPanel.add(ticketsField);

	ticketsField.setEditable(false);
	ticketsField.setMaximumSize(new Dimension(Integer.MAX_VALUE, ticketsField.getPreferredSize().height+10) );
}
//add button for each palyer to show the tickets they hava
public JButton addPlayerButton(String colour, int i){
	JButton playerButton = new JButton(colour);
	playerButton.setActionCommand(String.valueOf(i));
	playerButton.setBackground(Color.WHITE);
	if(i < 3) playerPanel1.add(playerButton);
		
	else playerPanel2.add(playerButton);
		playerPanel1.setMaximumSize(new Dimension(Integer.MAX_VALUE, playerPanel1.getPreferredSize().height) );
		playerPanel2.setMaximumSize(new Dimension(Integer.MAX_VALUE, playerPanel2.getPreferredSize().height) );
	
	return playerButton;
}
//reset MrXField
public void resetMrX(){
	movePanel.remove(MrXField);
	MrXField = new JTextArea();
	MrXField.setBackground(new Color(250, 250, 250, 200));

	MrXField.setText("MrX move\n");
	movePanel.add(MrXField);

}
//set text for tickets MrX used in MrXField
public void setMrX(String MrXTickets){
	MrXField.append(MrXTickets);
	MrXField.setEditable(false);
	MrXField.setMaximumSize(new Dimension(Integer.MAX_VALUE, MrXField.getPreferredSize().height+10) );


}

}
