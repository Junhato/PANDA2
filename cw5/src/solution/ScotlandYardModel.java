package solution;

import scotlandyard.*;

import java.io.IOException;
import java.util.*;

public class ScotlandYardModel extends ScotlandYard {
//need to be unspecified ie Players and Spectators??
    protected int numberOfDetectives, currentPlayer, currentRound;
    protected List<Boolean> rounds;
    private String graphFileName;
    protected Graph<Integer, Route> currentGraph;
    protected Map<Colour, SYPlayer> gameMembers;
    protected ArrayList<Colour> orderofPlayer;
    private boolean start = false;
    protected boolean isWinnerMrX = false;
    protected Move currentmove;
    protected ArrayList<Move> valid, MrXTrace;
    protected SYPlayer MrX;
    protected ArrayList<Spectator> spectators;

    public ScotlandYardModel(int numberOfDetectives, List<Boolean> rounds, String graphFileName) throws IOException {
        super(numberOfDetectives, rounds, graphFileName);
	if (numberOfDetectives < 0 || numberOfDetectives > 5) {
		System.out.println("Number of detectives entered is invalid");
		System.exit(-1);
	}
	//game initialization
	this.graphFileName = graphFileName;
	this.rounds = rounds;
	currentRound = 0;
	ScotlandYardGraphReader Reader = new ScotlandYardGraphReader();
	currentGraph = Reader.readGraph(graphFileName);
	this.numberOfDetectives = numberOfDetectives;
	gameMembers = new HashMap<Colour, SYPlayer>();
	orderofPlayer = new ArrayList<Colour>();
	spectators = new ArrayList<Spectator>();
	ArrayList<Move> MrXTrace = new ArrayList<Move>() ;
    }

    @Override
    protected Move getPlayerMove(Colour colour) {
	//Since I am not checking more here I assume that I give a list of only valid moves.
	if (!colour.equals(orderofPlayer.get(currentPlayer))) {
		System.out.println("Not this player's turn");
		return null;
	}
	if (valid.contains(gameMembers.get(colour).getMove())) {
		System.out.println("Invalid move");
		return null;
	}
	return currentmove;
    }

    @Override
    protected void nextPlayer() {
	currentPlayer = (currentPlayer + 1) % (numberOfDetectives+1);
    }

    @Override
    protected void play(MoveTicket move) {
	if (!getCurrentPlayer().equals(move.colour)) {
		System.out.println("Wrong player's move");
		System.exit(-1);
	}
	else {
		SYPlayer current = gameMembers.get(move.colour);
		current.setLocation(move.target, rounds.get(currentRound));
		current.removeTicket(move.ticket);
		if (!move.colour.equals(Colour.Black)) {
			MrX.addTicket(move.ticket);
		}
		else {
			MrXTrace.add(move);
			if(!move.ticket.equals(Ticket.SecretMove)) System.out.println(move.ticket.toString());
			currentRound = currentRound + 1;
		}			
	}
	for (Spectator s : spectators) {
		s.notify(move);
	}
	nextPlayer();
    }

    @Override
    protected void play(MoveDouble move) {
		MoveTicket first = (MoveTicket)move.moves.get(0);
		MoveTicket second = (MoveTicket)move.moves.get(1);
	if (!first.colour.equals(Colour.Black) || !first.colour.equals(second.colour) || !getCurrentPlayer().equals(first.colour)) {
		System.out.println("Wrong player's move");
		System.exit(-1);
	}
	else {
		MrX.setLocation(first.target, rounds.get(currentRound));
		MrX.removeTicket(first.ticket);
		currentRound = currentRound + 1;
		MrX.setLocation(second.target, rounds.get(currentRound));
		MrX.removeTicket(second.ticket);
		currentRound = currentRound + 1;
		MrXTrace.add(move);
		if(!first.ticket.equals(Ticket.SecretMove)) System.out.println(first.ticket.toString());
		if(!second.ticket.equals(Ticket.SecretMove)) System.out.println(second.ticket.toString());
	}
	for (Spectator s : spectators) {
		s.notify(move);
	}
	nextPlayer();
    }

    @Override
    protected void play(MovePass move) {
	if (getCurrentPlayer().equals(Colour.Black) && getCurrentPlayer().equals(move.colour)) {
		for (Spectator s : spectators) {
			s.notify(move);
		}
		nextPlayer();
	}
	else {
		System.out.println("Wrong player's move");
		System.exit(-1);
	}
    }

    protected boolean occupied(int location, Colour colour) {
	    for (Colour c : orderofPlayer) {
		if (!c.equals(Colour.Black) && !c.equals(colour)) {
			if (location == getPlayerLocation(c))
				return true;
			}
		}
    return false;	    
    }

    @Override
    protected List<Move> validMoves(Colour player) {
	valid = new ArrayList<Move>();
	int currentPosition = getPlayerLocation(player);
	Map<Ticket, Integer> currenttickets = gameMembers.get(player).getTickets();
	List<Edge<Integer, Route>> availableRoute = currentGraph.getEdges(currentPosition);
	for (Edge e: availableRoute) {
		int nbofticket = currenttickets.get(Ticket.fromRoute((Route)e.data()));
		if (currenttickets.get(Ticket.fromRoute((Route)e.data())) != null && nbofticket != 0) {
			int destination = (currentPosition == (int)e.source()) ? (int)e.target() : (int)e.source();
			//check other player's location except Mr.X
			if (occupied(destination, player)) continue;

			MoveTicket single = new MoveTicket(player, destination, Ticket.fromRoute((Route)e.data()));
			valid.add(single);
			MoveTicket secret = null;
			if (player.equals(Colour.Black) && currenttickets.get(Ticket.SecretMove) != 0) {
				secret = new MoveTicket(player, destination, Ticket.SecretMove);
				valid.add(secret);
			}
		//make double moves	
		if (player.equals(Colour.Black) && currenttickets.get(Ticket.DoubleMove) != 0) {
			for (Edge edge : currentGraph.getEdges((Integer)e.target())) {
				destination = (destination == (int)edge.source()) ? (int)edge.target() : (int)edge.source();
			//can Mr.X go somewhere and come back as two moves?
				if (currenttickets.get(Ticket.fromRoute((Route)edge.data())) != 0) {
					if (e.data().equals(edge.data()) && nbofticket ==1) continue;
					if (occupied(destination, player)) continue;

					MoveTicket firstoption = new MoveTicket(player, (Integer)edge.target(), Ticket.fromRoute((Route)edge.data()));
					valid.add(new MoveDouble(player, single, firstoption));

					if (secret != null && currenttickets.get(Ticket.SecretMove) != 0) {
						MoveTicket secondoption = new MoveTicket(player, (Integer)edge.target(), Ticket.SecretMove);
						valid.add(new MoveDouble(player, secret, secondoption));
					}
				}
			}
		}
		}
	}
	//add a pass move
	if (!player.equals(Colour.Black)) valid.add(new MovePass(player));
	currentmove = gameMembers.get(player).notify(gameMembers.get(player).getLocation(true), valid);
        return valid;
    }

    @Override
    public void spectate(Spectator spectator) {
	spectators.add(spectator);
    }

    @Override
    public boolean join(Player player, Colour colour, int location, Map<Ticket, Integer> tickets) {
        if (gameMembers.size() >= numberOfDetectives + 1) {
		System.out.println("The game is already full");
		return false;
	}
	if (numberOfDetectives ==0 && !colour.equals(Colour.Black)) {
		System.out.println("The only player is Mr.X");
		return false;
	}
	if (orderofPlayer.isEmpty() && gameMembers.isEmpty() && !colour.equals(Colour.Black)) {
		System.out.println("The first player should be Mr.X");
		return false;
	}
	for (Colour c : gameMembers.keySet()) {
		if (c.equals(colour)) {
			System.out.println("This colour already belong to a detective");
			return false;
		}
	// testing for two person starting at the same location don't know if it is necessary
		if (gameMembers.get(c).getLocation(true) == location) {
			System.out.println("There is already a detective at this location");
			return false;
		}
	}
	if (colour.equals(Colour.Black)) {
		MrX = new SYPlayer(location, tickets, true);
		gameMembers.put(colour, MrX);
	}
	else {
		gameMembers.put(colour, new SYPlayer(location, tickets));
	}
	orderofPlayer.add(colour);
	return true;
    }

    @Override
    public List<Colour> getPlayers() {
	return orderofPlayer;
    }

    @Override
    public Set<Colour> getWinningPlayers() {
	Set<Colour> winner = new HashSet<Colour>();
        if (!isGameOver()) return winner;
	if (isWinnerMrX) {
		winner.add(Colour.Black);
	}
	else {
		orderofPlayer.remove(Colour.Black);
		winner = new HashSet<>(orderofPlayer);
	}
	return winner;
    }

    @Override
    public int getPlayerLocation(Colour colour) {
	if (gameMembers.get(colour) != null) {
		return gameMembers.get(colour).getLocation(rounds.get(currentRound));
	} 
        return -1;
    }

    @Override
    public int getPlayerTickets(Colour colour, Ticket ticket) {
	if (gameMembers.get(colour) != null) {
		return gameMembers.get(colour).getTickets().get(ticket);
	} 
        return -1;
    }

    @Override
    public boolean isGameOver() {
	if(!isReady()) return false;
	if (orderofPlayer.get(currentPlayer).equals(Colour.Black) && validMoves(Colour.Black).isEmpty()) return true;
	if (getPlayerLocation(orderofPlayer.get(currentPlayer)) == getPlayerLocation(Colour.Black)) return true;
	if (((currentRound + 1) == rounds.size()) && currentPlayer == 0) {
		isWinnerMrX = true;
		return true;
	}
	for (Colour c : gameMembers.keySet()) {
		if (!c.equals(Colour.Black)) {
			 Map<Ticket, Integer> currentTickets = gameMembers.get(c).getTickets();
			for (Ticket t : currentTickets.keySet()) {
				if (currentTickets.get(t) != 0) return false;
			}
			
		}
	}
	isWinnerMrX = true;
	return true;
    }

    @Override
    public boolean isReady() {
	if (gameMembers.size() != numberOfDetectives + 1) return false;
	start = true;
	return true;
    }

    @Override
    public Colour getCurrentPlayer() {
	if (getRound() == 0 && start) {
		currentPlayer = orderofPlayer.indexOf(Colour.Black);
		start = false;
	}
	return orderofPlayer.get(currentPlayer);
    }

    @Override
    public int getRound() {	
        return currentRound;
    }

    @Override
    public List<Boolean> getRounds() {
        return rounds;
    }
}
