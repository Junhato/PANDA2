package solution;

import scotlandyard.*;

import java.util.List;
import java.util.Map;

public class MrX extends Player {
// do i need to remember MrX's moves detail or just how many?
    int location;
    Map<Ticket, Integer> tickets;

    private static MrX MrXInstance = null;

    private MrX (int location, Map<Ticket, Integer> tickets) {
	super(location, tickets);
	this.location = location;
	this.tickets = tickets;
    }
    public static MrX getInstance(int location, Map<Ticket, Integer> tickets) {
	(if MrXInstance == null) {
		MrXInstance = new MrX(location, tickets);
	}
	return MrXInstance;
    }

    public int getLocation() {
	return location;
    }

    public Map<Ticket, Integer> getTickets() {
	return tickets;
    }
    @Override
    Move notify(int location, List<Move> list) {
	//do something
	return null;
    }
}