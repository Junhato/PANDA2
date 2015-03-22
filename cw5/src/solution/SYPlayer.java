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
	this.player = player;
	this.location = location;
	this.tickets = tickets;	
	isMrX = false;
    }

    public SYPlayer(Player player, int location, Map<Ticket, Integer> tickets, boolean MrX) {
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
	if (player != null) {
	this.choicemove = player.notify(location, list);
	}
	else choicemove = null;
	return choicemove;
    }

}
