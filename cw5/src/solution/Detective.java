package solution;

import scotlandyard.*;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Detective extends Player {
    private int location;
    private Map<Ticket, Integer> tickets;
    protected Move choicemove;

    public Detective(int location, Map<Ticket, Integer> tickets) {
	super(location, tickets);
	this.location = location;
	this.tickets = tickets;	
    }

    public int getLocation() {
	return location;
    }

    public Map<Ticket, Integer> getTickets() {
	return tickets;
    }

    public Move getMove() {
	return choicemove;
    }
    @Override
    Move notify(int location, List<Move> list) {
	//for human player?
	System.out.println("Your current location is " + location);
	System.out.println("Posible moves are: ");
	int i = 1;
	boolean valid = false;
	for (Move m : list) {
		System.out.println("(" + i + ") " + m.toString());
		i++;	
	}
	System.out.println("Please enter your choice (integer number): ");
	while (!valid) {
		Scanner KeyboardIn = new Scanner(System.in);
		int selection = KeyboardIn.nextInt();
		if (selection < 0 || selection > i) System.out.println("Invalid choice, please try again");
		else valid = true;
	}
	choicemove = list.get(selection-1);
	return choicemove;
    }

}
