package solution;

import scotlandyard.*;

import java.util.List;
import java.util.Map;

public class Detective extends Player {
    int location;
    Map<Ticket, Integer> tickets;

    public Detective(int location, Map<Ticket, Integer> tickets) {
	this.location = location;
	this.tickets = tickets;	
    }

}
