package solution;

import scotlandyard.*;
import java.awt.event.*;
public class gameController {
	private gameView view;
	//private ScotlandYardModel model;
	public gameController(/*ScotlandYardModel model, */gameView view) {
		//this.model = model;
		this.view = view;
		//this.setFieldText();
}	
	/*public void setFieldText() {
		view.setStatus(model.isGameOver());
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
}
