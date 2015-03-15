package solution;

import scotlandyard.*;
import java.awt.event.*;
public class gameController {
	private gameView view;
	//private ScotlandYardModel model;
	public gameController(/*ScotlandYardModel model, */gameView view) {
		//this.model = model;
		this.view = view;
		this.view.addMoveListener(new moveListener());
		//this.setFieldText();
}	
	/*public void setFieldText() {
		view.setTurn(model.getCurrentPlayer());
		view.setRound(model.getRound());
		String tickets = "";
		for(Colour c:model.getPlayers())Û
			tickets += c.toString() + "has\n";
			playerTickets = c.getTickets();
			tickets += "Taxi ticket:" + playerTickets.get(Ticket Taxi)+ "\n";
			tickets += "Underground ticket:" + playerTickets.get(Ticket Underground)+ "\n";
			tickets += "Bus ticket:" + playerTickets.get(Ticket Bus)+ "\n";
			tickets += "DoubleMove ticket:" + playerTickets.get(Ticket DoubleMove)+ "\n";
			tickets += "SecretMove ticket:" + playerTickets.get(Ticket SecretMove)+ "\n";
		}

		view.setTicket(tickets):
		view.setMrXField();//ArrayList of tickets MrX used
	} */

class moveListener implements ActionListener {
	public void actionPerformed(ActionEvent actionEvent) { 
		/*view.setStatus(model.isGameOver());
		String player = model.getCurrentPlayer().toString();
		view.setTurn(player);
		view.setRound(model.getRound());
		String tickets = "";
		for(Colour c:model.getPlayers()){
			view.setPlayer();

			tickets += c.toString() + "has\n";
			playerTickets = c.getTickets();
			tickets += "Taxi ticket:" + playerTickets.get(Ticket Taxi)+ "\n";
			tickets += "Underground ticket:" + playerTickets.get(Ticket Underground)+ "\n";
			tickets += "Bus ticket:" + playerTickets.get(Ticket Bus)+ "\n";
			tickets += "DoubleMove ticket:" + playerTickets.get(Ticket DoubleMove)+ "\n";
			tickets += "SecretMove ticket:" + playerTickets.get(Ticket SecretMove)+ "\n";
			view.setTickets(tickets);
		
		}*/
		view.setStatus(true);
		view.setTurn("Blue");
		view.setRound(2);
	//	view.setPlayer("3");
		view.setTickets();
		view.addTickets("Blue", "blue has underground");
		view.setTickets();
		view.addTickets("Green", "green");


		view.setMrX("mrx used boat");
		view.resetImage();
		view.setImage("Bule", 5);
	}
} 
}
