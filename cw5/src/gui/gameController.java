package solution;

import scotlandyard.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Dimension;

import java.util.*;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
//control the game with gameView
public class gameController {
	private gameView view;
	private ScotlandYardModel model;
	private List<Colour> players;
	private Map<Colour, SYPlayer> members;
	private List<Move> moves;
	private int mrxLocation = 0;
	private int index = 0;
	private JFrame frame;
	private JCheckBox check;
	private ArrayList<String> MrXmoves = new ArrayList<String>();
	private GameData currentGD;

	public gameController(ScotlandYardModel model, gameView view, GameData currentGD) {
		this.model = model;
		this.view = view;
		this.currentGD = currentGD;
		this.players = model.getPlayers();
		//draw circle for position of each player
		for(Colour player:players){
		System.out.println(model.getPlayerLocation(player));
			int location = (int) model.getPlayerLocation(player);
			if(location == 0 && mrxLocation == 0) continue;
			else if(location == 0) location = mrxLocation;

			view.setImage(player.toString(), location);
		}

		view.setTurn(model.getCurrentPlayer().toString());
		view.setRound(1);
		this.members = model.getMembers();
		//write tickets players have in ticketsField
			String tickets = "";
			
			SYPlayer c = members.get(model.getCurrentPlayer());
			tickets += model.getCurrentPlayer().toString() + ": \n location =" + String.valueOf(model.getPlayerLocation(model.getCurrentPlayer())) + "\ntickets:\n";
			Map <Ticket, Integer> playerTickets = c.getTickets();
			tickets += "Taxi ticket:" + playerTickets.get(Ticket.Taxi)+ "\n";
			tickets += "Underground ticket:" + playerTickets.get(Ticket.Underground)+ "\n";
			tickets += "Bus ticket:" + playerTickets.get(Ticket.Bus)+ "\n";
			tickets += "DoubleMove ticket:" + playerTickets.get(Ticket.DoubleMove)+ "\n";
			tickets += "SecretMove ticket:" + playerTickets.get(Ticket.SecretMove)+ "\n";
			view.addTickets(model.getCurrentPlayer().toString(), tickets);
		//make button for each player
		int numPlayer = 0;
		for(Colour p:players){
			JButton playerButton = view.addPlayerButton(p.toString(), numPlayer);
			playerButton.addActionListener(new playerListener());


			numPlayer++;
		}
		//make button for each valid moves and draw line for it
		this.moves = model.validMoves(model.getCurrentPlayer());
		String moveString = "";
		int i = 0;
		for (Move move:moves){
			//moveString += i + ": " + move.toString() +"\n";
			i ++;
			int target = 0;
			Ticket ticket = Ticket.Taxi;
			if (move instanceof MoveTicket) {
				int a = ((MoveTicket) move).target;
				ticket = ((MoveTicket) move).ticket;
				//if(ticket.equals(Ticket.SecretMove) && t == target) continue;
				target = a;
			}
			if (move instanceof MoveDouble) {
				List<Move> list = ((MoveDouble) move).moves;
				int a = ((MoveTicket) list.get(1)).target;
				//if(a == target) continue;
				target = a;
				ticket = Ticket.DoubleMove;
			}
			if (move instanceof MovePass) continue;
			
			
			view.drawMove(model.getCurrentPlayer().toString(), target, (int) members.get(model.getCurrentPlayer()).getLocation(true));
			JButton button = new JButton();
			if(ticket.equals(Ticket.DoubleMove)) {
				button = view.drawDouble(target, i-1);
				button.addActionListener(new moveDoubleListener());
				continue;
			}
			else if(ticket.equals(Ticket.Underground)) button = view.drawUnderground(target, i-1);
			else if(ticket.equals(Ticket.Bus)) button = view.drawLondonbus(target, i-1);
			else if(ticket.equals(Ticket.Taxi)) button = view.drawTaxi(target, i-1);
			else if(ticket.equals(Ticket.SecretMove)) button = view.drawSecret(target, i-1);
			button.addActionListener(new moveListener());
			
			
		}
		this.view.addFinishListener(new finishListener());
		this.view.addSaveListener(new saveListener());
	}	

//action listener for saveButton
class saveListener implements ActionListener{
	public void actionPerformed(ActionEvent e){
		currentGD.saveGame();
	}
}
//action listener for new game button
class finishListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			view.setVisible(false);
			setNumberPlayer newGame = new setNumberPlayer();
			setNumberController newController = new setNumberController(newGame);
			newGame.setVisible(true);
		}
}
//action listener for button for each player
class playerListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			view.repaint();

			view.setTickets();
					int in = Integer.parseInt(e.getActionCommand());
					String tickets = "";
			
					SYPlayer c = members.get(players.get(in));
					tickets += players.get(in).toString() + ": \n location =" + String.valueOf(model.getPlayerLocation(players.get(in))) + "\ntickets:\n";
					Map <Ticket, Integer> playerTickets = c.getTickets();
					tickets += "Taxi ticket:" + playerTickets.get(Ticket.Taxi)+ "\n";
					tickets += "Underground ticket:" + playerTickets.get(Ticket.Underground)+ "\n";
					tickets += "Bus ticket:" + playerTickets.get(Ticket.Bus)+ "\n";
					tickets += "DoubleMove ticket:" + playerTickets.get(Ticket.DoubleMove)+ "\n";
					tickets += "SecretMove ticket:" + playerTickets.get(Ticket.SecretMove)+ "\n";
					view.addTickets(players.get(in).toString(), tickets);
				}
}
//action listener for making moves
class okListener implements ActionListener {
	public void actionPerformed(ActionEvent actionEvent) {
		frame.setVisible(false);
		Move chosenMove = moves.get(index);
		currentGD.saveGame("" + model.gameMembers.get(model.getCurrentPlayer()).getLocation(true) + " " + chosenMove.toString());
		//make move
		if (chosenMove instanceof MoveTicket) model.play((MoveTicket) chosenMove);
		if (chosenMove instanceof MoveDouble) model.play((MoveDouble) chosenMove);
		if (chosenMove instanceof MovePass) model.play((MovePass) chosenMove);
		//check if the game is over
		if(model.isGameOver()){

			if(model.getWinningPlayers().contains(Colour.Black)) view.setStatus("MrX WIN !!");
			else view.setStatus("Detectives WIN !!");
		}
		else if(!model.isGameOver()){
		model.nextPlayer();
		
		
		if(model.isGameOver()){
			if(model.getWinningPlayers().contains(Colour.Black)) view.setStatus("MrX WIN !!");
			else view.setStatus("Detectives WIN !!");
		}
	
		else if(!model.isGameOver()){
		view.repaint();

		view.resetImage();
		//draw circle for position of each player
		for(Colour player:players){
			int location = (int) model.getPlayerLocation(player);
			if(location == 0 && mrxLocation == 0) continue;
			else if(location == 0) location = mrxLocation;

			view.setImage(player.toString(), location);

		}
		view.setTurn(model.getCurrentPlayer().toString());
		view.setRound(model.getRound());
		view.setTickets();
		//write tickets for current player
			String tickets = "";
			
			SYPlayer c = members.get(model.getCurrentPlayer());
			tickets += model.getCurrentPlayer().toString() + ": \n location =" + String.valueOf(model.getPlayerLocation(model.getCurrentPlayer())) + "\ntickets:\n";
			Map <Ticket, Integer> playerTickets = c.getTickets();
			tickets += "Taxi ticket:" + playerTickets.get(Ticket.Taxi)+ "\n";
			tickets += "Underground ticket:" + playerTickets.get(Ticket.Underground)+ "\n";
			tickets += "Bus ticket:" + playerTickets.get(Ticket.Bus)+ "\n";
			tickets += "DoubleMove ticket:" + playerTickets.get(Ticket.DoubleMove)+ "\n";
			tickets += "SecretMove ticket:" + playerTickets.get(Ticket.SecretMove)+ "\n";
			view.addTickets(model.getCurrentPlayer().toString(), tickets);
		
		//make button for each valid moves and draw line for it
		moves = model.validMoves(model.getCurrentPlayer());
		String moveString = "";
		int i = 0;
		if(!model.getCurrentPlayer().equals(Colour.Black)) moves.add(new MovePass(model.getCurrentPlayer()));
		for (Move move:moves){
			moveString += i + ": " + move.toString() +"\n";
			i ++;
			int target = 0;
			Ticket ticket = Ticket.Taxi;
			JButton button = new JButton();

			if (move instanceof MoveTicket) {
				System.out.print(move.toString());
				int a = ((MoveTicket) move).target;
				ticket = ((MoveTicket) move).ticket;
				target = a;
			}
			else if (move instanceof MoveDouble) {
				List<Move> list = ((MoveDouble) move).moves;
				int a = ((MoveTicket) list.get(1)).target;
				target = a;
				ticket = Ticket.DoubleMove;
			}
			else if (move instanceof MovePass){
				button = view.drawPass((int) members.get(model.getCurrentPlayer()).getLocation(true), i-1);
				button.addActionListener(new moveListener());

				continue;
			}

			else continue;

			view.drawMove(model.getCurrentPlayer().toString(), target, (int) members.get(model.getCurrentPlayer()).getLocation(true));
			if(ticket.equals(Ticket.DoubleMove)) {
				button = view.drawDouble(target, i-1);
				button.addActionListener(new moveDoubleListener());
				continue;
			}
			else if(ticket.equals(Ticket.Underground)) button = view.drawUnderground(target, i-1);
			else if(ticket.equals(Ticket.Bus)) button = view.drawLondonbus(target, i-1);
			else if(ticket.equals(Ticket.Taxi)) button = view.drawTaxi(target, i-1);
			else if(ticket.equals(Ticket.SecretMove)) button = view.drawSecret(target, i-1);
			button.addActionListener(new moveListener());

		}
		view.setMoveArea(moveString);
		view.resetMrX();
		int iMrX = 1;
		//write tickets MrX used in MrXField
		for (String s:model.MrXTrace){
			
			view.setMrX("Round" + iMrX + ": " + s + "\n");
			iMrX++;
		}
		}
		}
	}
}
//action listener for when click the button on the map 
//make new frame to show selected move
//make ok button to make move
class moveListener implements ActionListener {
	public void actionPerformed(ActionEvent actionEvent) {
		JTextField field = new JTextField();
		field.setEditable(false);

		index = Integer.parseInt(actionEvent.getActionCommand());
		String s = moves.get(index).toString();
		field.setText(s);
		field.setMaximumSize(new Dimension(Integer.MAX_VALUE, field.getPreferredSize().height) );

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setMinimumSize(new Dimension(200, 100));
		frame.setLayout(new BorderLayout());
		frame.add(field, BorderLayout.CENTER);	
		JButton okButton = new JButton("ok");
		okButton.addActionListener(new okListener());
		okButton.setSize(100, 30);
	
		frame.add(okButton, BorderLayout.SOUTH);
		frame.setVisible(true);
	}
}
//action listener for when click the button for double move
//make new frame
//combobox to choose right double move
//make ok button to make move
class moveDoubleListener implements ActionListener {
	public void actionPerformed(ActionEvent actionEvent) {
		index = Integer.parseInt(actionEvent.getActionCommand());
		Move chosenMove = moves.get(index);
		List<Move> list = ((MoveDouble) chosenMove).moves;
		int target = ((MoveTicket) list.get(1)).target;
		String s = "";
		int num = 0;
		ArrayList<Integer> ms = new ArrayList<Integer>();
		int n = 0;
		//show possible moves with double moves
		for(Move m:moves){
			n ++;
			if (m instanceof MoveTicket) continue;
			if (m instanceof MoveDouble) {
				list = ((MoveDouble) m).moves;
				int t = ((MoveTicket) list.get(1)).target;
				if(t == target){
					s += (n-1) + ":" + m.toString() + "\n";
					ms.add(n - 1);
					num ++;

				}
				continue;
			}

			if (m instanceof MovePass) continue;
		}
		Integer[] m = new Integer[ms.size()];
		m = ms.toArray(m);
		//make combobox
		JComboBox<Integer> moveChoice = new JComboBox<Integer>(m);
        	moveChoice.addActionListener(new ActionListener(){
                    	public void actionPerformed(ActionEvent e){
				JComboBox combo = (JComboBox)e.getSource();
                        	index = (int)combo.getSelectedItem();
                   	}
               	});
		JTextArea area = new JTextArea();
		area.setText(s);
		area.setEditable(false);
		area.setMaximumSize(new Dimension(Integer.MAX_VALUE, area.getPreferredSize().height) );

		JButton okButton = new JButton("ok");
		okButton.addActionListener(new okListener());
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setMinimumSize(new Dimension(400, area.getPreferredSize().height + 100));

		frame.setLayout(new BorderLayout());
		frame.add(area, BorderLayout.NORTH);
		frame.add(moveChoice, BorderLayout.CENTER);
		frame.add(okButton, BorderLayout.SOUTH);
		frame.setVisible(true);
	}
}

}
