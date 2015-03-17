package solution;

import scotlandyard.*;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SYPlayer implements Player {
    private Player player;
    private int location;
    private int knownlocation = 0;
    private Map<Ticket, Integer> tickets;
    protected Move choicemove;
    private boolean isMrX;

    public SYPlayer(Player player, int location, Map<Ticket, Integer> tickets) {
//where did this first line come from @_@
	super();
	this.player = player;
	this.location = location;
	this.tickets = tickets;	
	isMrX = false;
    }

    public SYPlayer(Player player, int location, Map<Ticket, Integer> tickets, boolean MrX) {
//where did this first line come from @_@
	super();
	this.player = player;
	this.location = location;
	this.tickets = tickets;	
	isMrX = true;
    }
    public int getLocation(boolean surface) {
	if (isMrX && !surface) {
		return knownlocation;
	}
	return location;
    }

    public void setLocation(int location, boolean surface) {
	if (isMrX && surface) this.knownlocation = location;
	this.location = location;
    }

    public Map<Ticket, Integer> getTickets() {
	return tickets;
    }

    public void removeTicket(Ticket ticket) {
	tickets.put(ticket, tickets.get(ticket) - 1);
    }

    public void addTicket(Ticket ticket) {
	tickets.put(ticket, tickets.get(ticket) + 1);
    }

    public Move getMove() {
	return choicemove;
    }

    @Override
    public Move notify(int location, List<Move> list) {
	//for human player?
	this.choicemove = player.notify(location, list);
	if (choicemove != null) return choicemove;
	else {/*
	int selection = 0;
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
		selection = KeyboardIn.nextInt();
		if (selection < 0 || selection > i) System.out.println("Invalid choice, please try again");
		else valid = true;
	}
	choicemove = list.get(selection-1);*/
	return null;
	}
//	return choicemove;
    }

}
