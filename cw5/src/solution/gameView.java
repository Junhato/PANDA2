package solution;

import scotlandyard.*;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.util.*;
public class gameView extends JFrame {
	private JTextField statusField;
	private JTextField turnField;
	private JTextField roundField;
	private JTextField ticketsField;
	private JTextField MrXField;
 	gameView() {
		statusField = new JTextField(10);
		turnField  = new JTextField(10);
		roundField  = new JTextField(10);
		ticketsField  = new JTextField(10);
		MrXField  = new JTextField(10);

		JPanel displayPanel = new JPanel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 		this.setSize(400, 300);
		displayPanel.add(statusField);
		displayPanel.add(turnField);
		displayPanel.add(roundField);
		displayPanel.add(ticketsField);
		displayPanel.add(MrXField);
		this.add(displayPanel);
	}
public void setStatus(boolean status){
	if(status) statusField.setText("The Game is on");
	else status.setText("The Grame is over");
}
public void setTurn(Colour player){
	turnField.setText(player.toString() + "'s turn");
}
public void setRound(int round){
	roundField.setText("Round" + round);
}
public void setTickets(String playerTickets){
	ticketsField.setText(playerTickets);
}
public void setMrX(ArrayList<Ticket> tickets){
	String MrXTickets = "MrX used\n";
	for(int i = 0; i<tickets.size(); i++){
		MrXTickets += tickets[i] + "at Round " + (i + 1);
	}
	MrXField.setText(MrXTickets);
}
public void displayErrorMessage(String errorMessage) {
	JOptionPane.showMessageDialog(this, errorMessage);
}


}
