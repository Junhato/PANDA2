package solution;

import scotlandyard.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.lang.*;
import java.util.*;

public class gameStartController {
	private gameStart sview;
	private ScotlandYardModel model;
	private int playerNumber = 0;
	private Player MrX;
	private List<Player> players;
	private Map<Ticket, Integer> mrxTickets = new HashMap<Ticket, Integer>();
	private Map<Ticket, Integer> detectiveTickets = new HashMap<Ticket, Integer>();

	public gameStartController(ScotlandYardModel model, gameStart sview) {
		this.model = model;
		this.sview = sview;
		Player player1 = new SYPlayer();
		Player player2 = new SYPlayer();
		Player player3 = new SYPlayer();
		Player player4 = new SYPlayer();
		Player player5 = new SYPlayer();
		players = Arrays.asList(player1, player2, player3, player4, player5);
		
		mrxTickets.put(Ticket.Taxi, 3);
		mrxTickets.put(Ticket.Bus, 3);
		mrxTickets.put(Ticket.Underground, 3);
		mrxTickets.put(Ticket.DoubleMove, 3);
		mrxTickets.put(Ticket.SecretMove, 3);


		detectiveTickets.put(Ticket.Taxi, 3);
		detectiveTickets.put(Ticket.Bus, 3);
		detectiveTickets.put(Ticket.Underground, 3);

		this.sview.addListener(new AddListener());
		this.sview.addStartListener(new startListener());
		this.sview.addMapListener(new mapListener());
		this.model.join(MrX, Colour.Black, 67, mrxTickets);
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

		boolean state = model.join(players.get(playerNumber), colour, sview.getStartLocation(), detectiveTickets);
		if(state == true){
		String playercolour = sview.getColour();
		String playerlocation = String.valueOf(sview.getStartLocation());
		sview.addText(playercolour, playerlocation);
		}
		if(model.isReady() == true){
		sview.addStart();
		sview.removeAddButton();
		}

	}

}
class startListener implements ActionListener {
	public void actionPerformed(ActionEvent actionEvent){
		sview.disapper();
		gameView view = new gameView();
 		gameController controller = new gameController(model, view);
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
