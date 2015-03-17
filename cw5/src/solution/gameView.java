package solution;

import scotlandyard.*;
import java.awt.event.ActionListener;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.image.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
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

import java.lang.reflect.Field;
import java.lang.*;

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
	private JPanel playerPanel;
 	public gameView() {
		Font font1 = new Font("Verdana", Font.BOLD, 30);
		Font font2 = new Font("Verdana", Font.PLAIN, 15);
		Font font3 = new Font("Verdana", Font.BOLD, 20);


		statusField = new JTextArea();
		statusField.setMaximumSize(new Dimension(Integer.MAX_VALUE, statusField.getPreferredSize().height) );
		statusField.setFont(font1);

		turnField  = new JTextArea();
		turnField.setMaximumSize(new Dimension(Integer.MAX_VALUE, turnField.getPreferredSize().height) );
		turnField.setFont(font3);

		roundField  = new JTextArea();
		roundField.setMaximumSize(new Dimension(Integer.MAX_VALUE, roundField.getPreferredSize().height) );
		roundField.setFont(font1);

		/*ticketsField  = new JTextArea();
		ticketsField.setMaximumSize(new Dimension(Integer.MAX_VALUE, ticketsField.getPreferredSize().height) );
		ticketsField.setFont(font2);*/

		MrXField  = new JTextArea();
		MrXField.setMaximumSize(new Dimension(Integer.MAX_VALUE, MrXField.getPreferredSize().height) );
		MrXField.setFont(font2);

		playerField = new JTextArea();
		playerField.setMaximumSize(new Dimension(Integer.MAX_VALUE, playerField.getPreferredSize().height) );
		playerField.setFont(font2);

		imageLabel = new JLabel();
		imageLabel.setBackground(Color.WHITE);
		moveButton = new JButton("Make Move");

		moveField = new JTextField();
		moveField.setMaximumSize(new Dimension(100, 20));
		moveField.setFont(font2);

		moveArea = new JTextArea();
		moveArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, moveArea.getPreferredSize().height) );
		moveArea.setFont(font2);

		playerPanel = new JPanel();
		JPanel topPanel = new JPanel();
		JPanel movePanel = new JPanel();
		JPanel imagePanel = new JPanel();
		ticketsPanel = new JPanel();
		ticketsPanel.setLayout(new BoxLayout(ticketsPanel, BoxLayout.PAGE_AXIS));


		playerPanel.setBackground(Color.WHITE);
		topPanel.setBackground(Color.WHITE);
		movePanel.setBackground(Color.WHITE);
		imagePanel.setBackground(Color.WHITE);

		this.setBackground(Color.WHITE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 		this.setSize(1200, 900);
		//this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.setLayout(new BorderLayout());
		playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.PAGE_AXIS));
		movePanel.setLayout(new BoxLayout(movePanel, BoxLayout.PAGE_AXIS));

		//displayPanel.setLayout(BoxLayout.Y_AXIS);
		topPanel.add(statusField);
		movePanel.add(turnField);
		topPanel.add(roundField);
		setTickets();
		playerPanel.add(ticketsPanel);
		playerPanel.add(MrXField);
		playerPanel.add(playerField);
		
		movePanel.add(moveArea);
		movePanel.add(moveField);
		movePanel.add(moveButton);

		readFile();
		resetImage();
		setImage("Bule", 2);
		imagePanel.add(imageLabel);
		this.add(movePanel, BorderLayout.EAST);
		this.add(topPanel, BorderLayout.NORTH);
		this.add(playerPanel, BorderLayout.WEST);
		this.add(imagePanel, BorderLayout.CENTER);


		
	}

public void addMoveListener(ActionListener listenMoveButton) {

	moveButton.addActionListener(listenMoveButton);
} 

public void readFile(){
	File file = new File("pos.txt");	
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

public void resetImage(){
	try
		{
			img = ImageIO.read(new File("map.jpg"));
			g2d = img.createGraphics();

		}
		catch( IOException e )
		{
			System.out.println(e);
		}


	g2d = img.createGraphics();
        BasicStroke bs = new BasicStroke(4);
	g2d.setStroke(bs);

}
public void setImage(String colour, int location){
	List<Integer> pos = coordinateMap.get(location);
        g2d.setColor(Color.BLUE);
	System.out.println(g2d);

	g2d.drawOval(pos.get(0)-15, pos.get(1)-15, 30, 30);
	icon = new ImageIcon(img);
	imageLabel.setIcon(icon);

	
}
public void setStatus(boolean status){
	if(status) statusField.setText("Playing     ");
	else statusField.setText("The Grame is over");
	statusField.setEditable(false);
	statusField.setMaximumSize(new Dimension(Integer.MAX_VALUE, statusField.getPreferredSize().height) );
	
}
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
public void setRound(int round){
	roundField.setText("Round" + round);
	roundField.setEditable(false);
	roundField.setMaximumSize(new Dimension(Integer.MAX_VALUE, roundField.getPreferredSize().height) );


}
public void setTickets(){
	ticketsPanel.removeAll();
	//ticketsPanel = new JPanel();
	//ticketsPanel.setLayout(new BoxLayout(ticketsPanel, BoxLayout.PAGE_AXIS));
	System.out.println("inf");


}
public void addTickets(String player, String inf){
	JTextArea ticketsField = new JTextArea();
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
	//playerPanel.add(ticketsPanel);

}
public void setMrX(/*ArrayList<Ticket> tickets*/ String MrXTickets){
	/*String MrXTickets = "MrX used\n";
	for(int i = 0; i<tickets.size(); i++){
		MrXTickets += tickets[i] + "at Round " + (i + 1);
	}*/
	MrXField.setText(MrXTickets);
	MrXField.setEditable(false);
	MrXField.setMaximumSize(new Dimension(Integer.MAX_VALUE, MrXField.getPreferredSize().height+10) );


}
/*public void setPlayer(String location){
	playerField.setText(location);
	playerField.setEditable(false);
	playerField.setMaximumSize(new Dimension(Integer.MAX_VALUE, playerField.getPreferredSize().height+10) );

}*/
public void setMoveArea(String validMove){
	moveArea.setText(validMove);
	moveArea.setEditable(false);
	moveArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, moveArea.getPreferredSize().height+10) );
}
public int getMove(){
	return Integer.parseInt(moveField.getText());
} 
/*public void displayErrorMessage(String errorMessage) {
	JOptionPane.showMessageDialog(this, errorMessage);

}*/


}

