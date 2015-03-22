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
	private JPanel playerPanel1;
	private JPanel playerPanel2;
	private Image underground;
	private Image londonbus;
	private Image taxi;
	private JPanel imagePanel;
	private JButton replayButton;
	private JButton saveButton;
	private JButton finishButton;
	private JPanel movePanel;
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
		setMrX("a");
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

		playerPanel1 = new JPanel();
		playerPanel1.setMaximumSize(new Dimension(Integer.MAX_VALUE, playerPanel1.getPreferredSize().height) );
		
		playerPanel2 = new JPanel();
		playerPanel2.setMaximumSize(new Dimension(Integer.MAX_VALUE, playerPanel2.getPreferredSize().height) );

		JPanel topPanel = new JPanel();
		movePanel = new JPanel();
		imagePanel = new JPanel();
		ticketsPanel = new JPanel();
		ticketsPanel.setLayout(new BoxLayout(ticketsPanel, BoxLayout.PAGE_AXIS));


		//playerPanel.setBackground(Color.WHITE);
		playerPanel1.setOpaque(false);
		playerPanel2.setOpaque(false);
		topPanel.setOpaque(false);
		movePanel.setOpaque(false);
		imagePanel.setOpaque(false);

		//topPanel.setBackground(Color.WHITE);
		//movePanel.setBackground(Color.WHITE);
		//imagePanel.setBackground(Color.WHITE);
		
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
		//this.setBackground(Color.WHITE);
		this.setContentPane(back);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 		this.setSize(1200, 900);
		//this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		back.setLayout(new BorderLayout());
		playerPanel1.setLayout(new FlowLayout());
		movePanel.setLayout(new BoxLayout(movePanel, BoxLayout.PAGE_AXIS));

		//displayPanel.setLayout(BoxLayout.Y_AXIS);
		imageLabel.add(statusField);
		roundField.setBackground(new Color(250, 250, 250, 200));
		turnField.setBackground(new Color(250, 250, 250, 200));
		playerField.setBackground(new Color(250, 250, 250, 200));
		MrXField.setBackground(new Color(250, 250, 250, 200));

		movePanel.add(roundField);
		movePanel.add(turnField);
		setTickets();
		movePanel.add(ticketsPanel);
		movePanel.add(playerPanel1);
		movePanel.add(playerPanel2);
		movePanel.add(MrXField);

		replayButton = new JButton("Replay");
		replayButton.setBackground(Color.WHITE);
		saveButton = new JButton("Save Game");
		saveButton.setBackground(Color.WHITE);

		finishButton = new JButton("New Game");
		finishButton.setBackground(Color.WHITE);

		topPanel.add(replayButton);
		topPanel.add(saveButton);
		topPanel.add(finishButton);
		//playerPanel.add(MrXField);
		//playerPanel.add(playerField);
		
		//movePanel.add(moveArea);
		//movePanel.add(moveField);
		//movePanel.add(moveButton);

		readFile();
		resetImage();
		createImage();
		//setImage("Bule", 2);
		imagePanel.add(imageLabel);
		this.add(movePanel, BorderLayout.EAST);
		this.add(topPanel, BorderLayout.NORTH);
		//this.add(playerPanel, BorderLayout.WEST);
		this.add(imagePanel, BorderLayout.CENTER);
		//this.add(back);


		
	}
public void addReplayListener(ActionListener listenReplayButton) {
	replayButton.addActionListener(listenReplayButton);
}
public void addSaveListener(ActionListener listenSaveButton) {
	saveButton.addActionListener(listenSaveButton);
}
public void addFinishListener(ActionListener listenFinishButton) {
	finishButton.addActionListener(listenFinishButton);
}

public void addMoveListener(ActionListener listenMoveButton) {

	moveButton.addActionListener(listenMoveButton);
} 

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

public JButton drawUnderground(int location, int index){
	List<Integer> p = coordinateMap.get(location);
	JButton button = new JButton(String.valueOf(location), new ImageIcon(underground));

	button.setBounds(p.get(0)-10, p.get(1)-30, 100, 20);
	//g2d.drawImage(londonbus, p.get(0)+10, p.get(1)-40, null);
	//g2d.drawImage(taxi, p.get(0)-70, p.get(1)-40, null);
	button.setActionCommand(String.valueOf(index));
	//button.setContentAreaFilled(false);
	button.setBackground(Color.WHITE);

	imageLabel.add(button);


	//icon = new ImageIcon(img);
	//imageLabel.setIcon(icon);
	return button;

}
public JButton drawLondonbus(int location, int index){
	List<Integer> p = coordinateMap.get(location);
	JButton button = new JButton(String.valueOf(location), new ImageIcon(londonbus));

	button.setBounds(p.get(0), p.get(1)-40, 100, 20);
	button.setActionCommand(String.valueOf(index));
	//button.setContentAreaFilled(false);
	button.setBackground(Color.WHITE);

	imageLabel.add(button);
	return button;
}
public JButton drawTaxi(int location, int index){
	List<Integer> p = coordinateMap.get(location);
	//JLabel label = new JLabel(String.valueOf(location));
	JButton button = new JButton(String.valueOf(location), new ImageIcon(taxi));
	button.setBounds(p.get(0)-30, p.get(1)-30, 100, 20);
	button.setActionCommand(String.valueOf(index));
	//button.setContentAreaFilled(false);
	button.setBackground(Color.WHITE);
	imageLabel.add(button);
	return button;
}

public JButton drawSecret(int location, int index){
	List<Integer> p = coordinateMap.get(location);
	JButton button = new JButton("S " + String.valueOf(location));

	button.setBounds(p.get(0)+10, p.get(1)-40, 80, 20);
	button.setActionCommand(String.valueOf(index));
	//button.setContentAreaFilled(false);
	button.setBackground(Color.LIGHT_GRAY);

	imageLabel.add(button);
	return button;
}

public JButton drawDouble(int location, int index){
	List<Integer> p = coordinateMap.get(location);
	JButton button = new JButton("D " + String.valueOf(location));

	button.setBounds(p.get(0)-20, p.get(1)-30, 80, 20);
	button.setActionCommand(String.valueOf(index));
	//button.setContentAreaFilled(false);
	button.setBackground(Color.GRAY);

	imageLabel.add(button);
	return button;
}
public JButton drawPass(int location, int index){
	List<Integer> p = coordinateMap.get(location);
	JButton button = new JButton("Pass" );

	button.setBounds(p.get(0), p.get(1)-20, 80, 20);
	button.setActionCommand(String.valueOf(index));
	//button.setContentAreaFilled(false);
	button.setBackground(Color.YELLOW);

	imageLabel.add(button);
	return button;
}

public void setStatus(boolean status){
	if(status == false) statusField.setText("");
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
	roundField.setText("Round" + round +"              ");
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
	ticketsField.setBackground(new Color(250, 250, 250, 200));

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
public void resetMrX(){
	movePanel.remove(MrXField);
	JTextArea MrXField = new JTextArea();
	MrXField.setText("MrX move\n");
	movePanel.add(MrXField);

}
public void setMrX(String MrXTickets){
	MrXField.append(MrXTickets);
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
