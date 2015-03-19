package solution;

import scotlandyard.*;
import java.awt.event.*;
import java.util.*;
public class gameController {
	private gameView view;
	private ScotlandYardModel model;
	private List<Colour> players;
	private Map<Colour, SYPlayer> members;
	private List<Move> moves;

	public gameController(ScotlandYardModel model, gameView view) {
		this.model = model;
		this.view = view;
		this.view.addMoveListener(new moveListener());
		this.players = model.getPlayers();
		for(Colour player:players){
			view.setImage(player.toString(), model.getPlayerLocation(player));
		}
		view.setStatus(model.isGameOver());
		view.setTurn(model.getCurrentPlayer().toString());
		view.setRound(1);
		this.members = model.getMembers();

		for(Colour p:players){
			String tickets = "";
			
			SYPlayer c = members.get(p);
			tickets += p.toString() + ": \n location =" + String.valueOf(model.getPlayerLocation(p)) + "\ntickets:\n";
			Map <Ticket, Integer> playerTickets = c.getTickets();
			tickets += "Taxi ticket:" + playerTickets.get(Ticket.Taxi)+ "\n";
			tickets += "Underground ticket:" + playerTickets.get(Ticket.Underground)+ "\n";
			tickets += "Bus ticket:" + playerTickets.get(Ticket.Bus)+ "\n";
			tickets += "DoubleMove ticket:" + playerTickets.get(Ticket.DoubleMove)+ "\n";
			tickets += "SecretMove ticket:" + playerTickets.get(Ticket.SecretMove)+ "\n";
			view.addTickets(p.toString(), tickets);
		
		}
		
		this.moves = model.validMoves(model.getCurrentPlayer());
		String moveString = "";
		int i = 1;
	/*	for (Move move:moves){
			moveString += i + ": " + move.toString() +"\n";
			i ++;
		}*/
		view.setMoveArea(moveString);



	}	
class moveListener implements ActionListener {
	public void actionPerformed(ActionEvent actionEvent) {
		int index = view.getMove(); 
		Move chosenMove = moves.get(index-1);
		if (chosenMove instanceof MoveTicket) model.play((MoveTicket) chosenMove);
		if (chosenMove instanceof MoveDouble) model.play((MoveDouble) chosenMove);
		if (chosenMove instanceof MovePass) model.play((MovePass) chosenMove);

		model.nextPlayer();
		view.resetImage();
		for(Colour player:players){
			view.setImage(player.toString(), model.getPlayerLocation(player));
		}
		view.setStatus(model.isGameOver());
		view.setTurn(model.getCurrentPlayer().toString());
		view.setRound(1);
		view.setTickets();
		for(Colour p:players){
			String tickets = "";
			
			SYPlayer c = members.get(p);
			tickets += p.toString() + ": \n location =" + String.valueOf(model.getPlayerLocation(p)) + "\ntickets:\n";
			Map <Ticket, Integer> playerTickets = c.getTickets();
			tickets += "Taxi ticket:" + playerTickets.get(Ticket.Taxi)+ "\n";
			tickets += "Underground ticket:" + playerTickets.get(Ticket.Underground)+ "\n";
			tickets += "Bus ticket:" + playerTickets.get(Ticket.Bus)+ "\n";
			tickets += "DoubleMove ticket:" + playerTickets.get(Ticket.DoubleMove)+ "\n";
			tickets += "SecretMove ticket:" + playerTickets.get(Ticket.SecretMove)+ "\n";
			view.addTickets(p.toString(), tickets);
		
		}
		
		moves = model.validMoves(model.getCurrentPlayer());
		String moveString = "";
		int i = 1;
		for (Move move:moves){
			moveString += i + ": " + move.toString() +"\n";
			i ++;
		}
		view.setMoveArea(moveString);

	}
} 
}
