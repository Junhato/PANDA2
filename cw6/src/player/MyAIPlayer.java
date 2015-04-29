package player;

import scotlandyard.Move;
import scotlandyard.Player;
import scotlandyard.ScotlandYardView;
import scotlandyard.ScotlandYard;
import solution.ScotlandYardModel;

import scotlandyard.*;
import java.io.*;

import java.util.*;
import java.util.Set;

/**
 * An AI that plays Mr.X
 */

public class MyAIPlayer implements Player{

	protected ScotlandYardView view;
	protected String graphFilename;
	protected Map<Integer, List<Integer>> coordinateMap = new HashMap<Integer, List<Integer>>();
	protected Colour myColour; //always black?
	protected int nbofplayers;
	protected List<Boolean> truerounds;

    public MyAIPlayer(ScotlandYardView view, String graphFilename){
        this.view = view;
        this.graphFilename = graphFilename;
	truerounds = new ArrayList<Boolean>();
	for (int i = 0; i < 25; i++) { truerounds.add(true); }
	readPos();
    }

    @Override
    public Move notify(int location, Set<Move> moves){
	this.nbofplayers = view.getPlayers().size();
	myColour = view.getCurrentPlayer();
	//what happens if there is no move available??
	//is there (always) a pass move provided??
	if (moves.size() == 0) return null;
	if (moves.size() == 1) {
		for (Move move : moves) {
			if (move instanceof MovePass) return move;
			return move;
		}
	}

	//evaluate current situation (look-ahead one)
		//Map<Move, Integer> currentscore = onelookahead(this.view, location, moves);
		//return findbestMove(currentscore);

	//evaluate current situation (Min-Max)
		long startTime = System.nanoTime();    
		Move themove = Minimax(location, moves, (8-nbofplayers)*nbofplayers); //Minimax(location, moves, 2); 
		long elapsedTime = System.nanoTime() - startTime;
		double seconds = (double)elapsedTime / 1000000000.0;
		System.out.println(seconds);
		return themove;
	//return Minimax(location, moves, (8-nbofplayers)*nbofplayers);
    }

    public Move findbestMove(Map<Move, Integer> currentscore) {
    	Move bestmove = null;
	if (currentscore.isEmpty()) return bestmove;

	int max = 0;
	for (Move move : currentscore.keySet()) {
		if (currentscore.get(move) > max)  {
			max = currentscore.get(move);
			bestmove = move;
		}
	}
	return bestmove;
    }

    public Map<Move, Integer> onelookahead(ScotlandYardView view, int location, Set<Move> moves) {

	Map<Move, Integer> boardscores = new HashMap<Move, Integer>();
	for (Move move : moves) {

		//recreate current game state
		ScotlandYardModel game = createGame(view, location);

		if (move instanceof MoveDouble) game.play((MoveDouble)move);
		if (move instanceof MoveTicket) game.play((MoveTicket)move);
		boardscores.put(move, score(view, location, game.validMoves(myColour).size()));
        }
	return boardscores;
    }

    public Move Minimax(int location, Set<Move> moves, int depth) {
	//choose if use special move or not here like evaluate move option??
	Map<Move, Integer> boardscores = new HashMap<Move, Integer>();
	for (Move move : moves) {
		//recreate current game state
		ScotlandYardModel game = createGame(this.view, location);
		int choice = MaxMove(move, depth, game, Integer.MIN_VALUE, Integer.MAX_VALUE);
		boardscores.put(move, choice);
    	}
	return findbestMove(boardscores);
    }

    public int MaxMove(Move move, int depth, ScotlandYardModel currentGame, int alpha, int beta) {
	int currentDistance = distancetoMrX(currentGame.getPlayerLocation(currentGame.getCurrentPlayer()), currentGame.getPlayerLocation(Colour.Black));
	//detectives only get closer to MrX (else it is not an interesting move)
	if (move instanceof MoveDouble) currentGame.play((MoveDouble)move);
	if (move instanceof MoveTicket) currentGame.play((MoveTicket)move);
	if (currentGame.getCurrentPlayer() != Colour.Black) {
		int newDistance = distancetoMrX(currentGame.getPlayerLocation(currentGame.getCurrentPlayer()), currentGame.getPlayerLocation(Colour.Black));
		if (Math.abs(currentDistance - newDistance) > 50) return Integer.MIN_VALUE;
	}
	System.out.println(depth);
	currentGame.nextPlayer();
	Colour player = currentGame.getCurrentPlayer();

	if (player.equals(Colour.Black) && currentGame.validMoves(player).size() == 0) return Integer.MIN_VALUE;
			//assume that there is always a pass move
	//else if (currentGame.validMoves(player).size() == 1 || currentGame.validMoves(player).size() == 0)

	if (currentGame.isGameOver()) {
		if (player.equals(Colour.Black)) return Integer.MAX_VALUE;
		else return Integer.MIN_VALUE;		
	}
	if (depth == 0) {

		Map<Move, Integer> currentscores = onelookahead(currentGame, currentGame.getPlayerLocation(Colour.Black), currentGame.validMoves(Colour.Black));
		Move bestmove = findbestMove(currentscores);
		return currentscores.get(bestmove);		
	}
	else {	
		int depthbest = Integer.MIN_VALUE;
		for (Move m: currentGame.validMoves(player)) {

			ScotlandYardModel thislayer = createGame(currentGame, currentGame.getPlayerLocation(player));
			while (thislayer.getCurrentPlayer() != player) thislayer.nextPlayer();

			if (m instanceof MovePass) continue;

			int currentbest = MaxMove(m, depth-1, thislayer, alpha, beta);

			if (player.equals(Colour.Black)) {
				if (depthbest == Integer.MIN_VALUE) depthbest = currentbest;
				else if (currentbest > depthbest) {
					depthbest = currentbest;
					alpha = currentbest;
				}
			if (beta > alpha) return depthbest;
			}
			else {
				if (depthbest == Integer.MIN_VALUE) depthbest = currentbest;
				else if (currentbest > depthbest) {
					depthbest = currentbest;
					beta = currentbest;
				}
			if (beta < alpha) return depthbest;				
			}
		}
		return depthbest;
	}
    }

    //calculate board score for a new location
    public int score(ScotlandYardView view, int location, int numberofmoves){

		List<Colour> players = view.getPlayers();
		int score = 0;
		score += distance(view, location, players);
		score += numberofmoves;
		score += overall(location);
        	return score;
	}

    //distance to detectives
    public int distance(ScotlandYardView view, int currentLocation, List<Colour> players){
		List<Integer> pos = coordinateMap.get(currentLocation);
		int score = 0;
			for(Colour player:players){
				int location = view.getPlayerLocation(player);
				List<Integer> co = coordinateMap.get(location);
				if(co != null && pos != null){
					double value = Math.sqrt((pos.get(0)-co.get(0))*(pos.get(0)-co.get(0))+(pos.get(1)-co.get(1))*(pos.get(1)-co.get(1)));
					score += (int) value;
				}
			}
		return score;
	}

     //overall position on the board
     public int overall(int location){
			List<Integer> pos = coordinateMap.get(location);
			int score = 0;
			if(pos != null){
				int upLeft = pos.get(0) + pos.get(1);
				int downLeft = pos.get(0) + 800 - pos.get(1);
				int upRight = 1000 - pos.get(0) + pos.get(1);
				int downRight = 1000 - pos.get(0) + 800 - pos.get(1);
			
				score = upLeft;
				if(score > downLeft) score = downLeft;
				if(score > upRight) score = upRight;
				if(score > downRight) score = downRight;
			}
			return score;
	}
     public int distancetoMrX(int locationd, int locationx) {
	int distance = Integer.MAX_VALUE;
	List<Integer> pos = coordinateMap.get(locationx);
	List<Integer> co = coordinateMap.get(locationd);

		if(co != null && pos != null){
			double value = Math.sqrt((pos.get(0)-co.get(0))*(pos.get(0)-co.get(0))+(pos.get(1)-co.get(1))*(pos.get(1)-co.get(1)));
			distance = (int) value;
		}
	return distance;
     }

     public ScotlandYardModel createGame(ScotlandYardView view, int location) {
	    ScotlandYardModel initialGame = new ScotlandYardModel(view.getPlayers().size()-1, truerounds, this.graphFilename);

	    for (Colour colour : view.getPlayers()) {
		Map<Ticket, Integer> tickets = new HashMap<Ticket, Integer>();

		//can I join with null player?? or I need to create players
		tickets.put(Ticket.Bus, view.getPlayerTickets(colour, Ticket.Bus));
		tickets.put(Ticket.Taxi, view.getPlayerTickets(colour, Ticket.Taxi));
		tickets.put(Ticket.Underground, view.getPlayerTickets(colour, Ticket.Underground));

		if (!colour.equals(Colour.Black)) {
			initialGame.join(null, colour, view.getPlayerLocation(colour), tickets);
		
		}
		else {
			tickets.put(Ticket.Secret, view.getPlayerTickets(colour, Ticket.Secret));
			tickets.put(Ticket.Underground, view.getPlayerTickets(colour, Ticket.Underground));
			initialGame.join(null, colour, location, tickets);

		}
	    }
	    //if (initialGame.isReady()) 
	    //else { return null; }
	    //initialGame.start();
	    return initialGame;	
     }

     //read all possible positions
     public void readPos(){
		File file = new File("../resources/pos.txt");	
			Scanner in = null;
			try 
			{
				in = new Scanner(file);
			} 
			catch (FileNotFoundException e) 
			{
				System.out.println(e);
			}
			
			// get the number of nodes
			String topLine = in.nextLine();
		
			int numberOfNodes = Integer.parseInt(topLine);
			
			
			for(int i = 0; i < numberOfNodes; i++)
			{
				String line = in.nextLine();
		   
				String[] parts = line.split(" ");
				List<Integer> pos = new ArrayList<Integer>();
				pos.add(Integer.parseInt(parts[1]));
				pos.add(Integer.parseInt(parts[2]));
			//System.out.println(pos);
				
				int key = Integer.parseInt(parts[0]);
			//System.out.println(key);

				coordinateMap.put(key, pos);
			}
	}
}