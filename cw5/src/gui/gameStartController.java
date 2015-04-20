package solution;

import scotlandyard.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.lang.*;
import java.util.*;
import java.util.Random;
//import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class gameStartController {
	private gameStart sview;
	private ScotlandYardModel model;
	private int playerNumber = 0;
	private Player MrX;
	private List<Player> players;
	private Map<Ticket, Integer> mrxTickets = new HashMap<Ticket, Integer>();
	private Map<Ticket, Integer> detectiveTickets = new HashMap<Ticket, Integer>();
	private GameData currentGD;
	private List<Integer> locations = new LinkedList<Integer>(Arrays.asList(13,26,29,34,50,53,91,94,103,112,117,132,138,141,155,174,197,198));
	private int startCard = 18;
	private Random randomGenerator = new Random();
	private int randomInt = 0;
	private int mrxLocation;
	public gameStartController(ScotlandYardModel model, gameStart sview) {
		this.model = model;
		this.sview = sview;
		Player player1 = new SYPlayer();
		Player player2 = new SYPlayer();
		Player player3 = new SYPlayer();
		Player player4 = new SYPlayer();
		Player player5 = new SYPlayer();
		players = Arrays.asList(player1, player2, player3, player4, player5);
		
		mrxTickets.put(Ticket.Taxi, 11);
		mrxTickets.put(Ticket.Bus, 8);
		mrxTickets.put(Ticket.Underground, 4);
		mrxTickets.put(Ticket.DoubleMove, 2);
		mrxTickets.put(Ticket.SecretMove, 5);


		detectiveTickets.put(Ticket.Taxi, 11);
		detectiveTickets.put(Ticket.Bus, 8);
		detectiveTickets.put(Ticket.Underground, 4);

		this.sview.addListener(new AddListener());
		this.sview.addStartListener(new startListener());
		this.sview.addMapListener(new mapListener());
		this.sview.addSecretListener(new secretListener());


		randomInt = randomGenerator.nextInt(startCard);
		this.model.join(MrX, Colour.Black, locations.get(randomInt), mrxTickets);
		mrxLocation = locations.get(randomInt);
		locations.remove(randomInt);
		startCard -= 1;
	}
	public int getMrXLocation(){
		return mrxLocation;
	}

class secretListener implements ActionListener{
	public void actionPerformed(ActionEvent actionEvent){
		String show = "MrX's Starting position:" + mrxLocation;
		JTextField field = new JTextField();
		field.setText(show);
		field.setEditable(false);

		JFrame location = new JFrame();
		location.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 		location.setSize(200, 100);
		location.add(field);
		location.setVisible(true);
	}
}

class AddListener implements ActionListener {
	public void actionPerformed(ActionEvent actionEvent) { 
		Colour colour = Colour.Black;		
		try{
		Field field = Class.forName("scotlandyard.Colour").getField(sview.getColour()); 
		colour = (Colour)field.get(null);
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
		randomInt = randomGenerator.nextInt(startCard);

		boolean state = model.join(players.get(playerNumber), colour, locations.get(randomInt), detectiveTickets);

		if(state == true){
		String playercolour = sview.getColour();
		String playerlocation = String.valueOf(locations.get(randomInt));
		sview.addText(playercolour, playerlocation);
		}
		if(model.isReady() == true){
		currentGD = new GameData(model);
		sview.addStart();
		sview.removeAddButton();
		}
		locations.remove(randomInt);


	}

}
class startListener implements ActionListener {
	public void actionPerformed(ActionEvent actionEvent){
		sview.disapper();
		gameView view = new gameView();
 		gameController controller = new gameController(model, view, currentGD);
 		view.setVisible(true);
	} 
}
class mapListener implements ActionListener {
	public void actionPerformed(ActionEvent actionEvent) { 
		openMap Map = new openMap();
		Map.setVisible(true);
	}

}

}
