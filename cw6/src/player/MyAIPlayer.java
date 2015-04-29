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
 * This AI mimic almost random move when detectives are far away.
 * It calculates its moves better when detectives are near
 */

public class MyAIPlayer implements Player{

	protected ScotlandYardView view;
	protected String graphFilename;
	protected Map<Integer, List<Integer>> coordinateMap = new HashMap<Integer, List<Integer>>();
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
		Move themove = Minimax(location, moves, (8-nbofplayers)*nbofplayers);
		long elapsedTime = System.nanoTime() - startTime;
		double seconds = (double)elapsedTime / 1000000000.0;
		System.out.println(seconds);
		System.out.println(themove.toString());
		return themove;
	//return Minimax(location, moves, (8-nbofplayers)*nbofplayers);
    }

    public Move findbestMove(Map<Move, Integer> currentscore) {
    	Move bestmove = null;
	System.out.println(currentscore.isEmpty());
	if (currentscore.isEmpty()) return bestmove;

	int max = Integer.MIN_VALUE;
	for (Move move : currentscore.keySet()) {
		if (currentscore.get(move) >= max)  {
			max = currentscore.get(move);
			bestmove = move;
		}
	}
	System.out.println("score: " + currentscore.get(bestmove));
	return bestmove;
    }

    public Map<Move, Integer> onelookahead(ScotlandYardView view, int location, Set<Move> moves) {
	Colour myColour = view.getCurrentPlayer();
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

	Map<Move, Integer> boardscores = new HashMap<Move, Integer>();
	for (Move move : moves) {
		//recreate current game state
		ScotlandYardModel game = createGame(this.view, location);
		int choice = MaxMove(move, depth, game, Integer.MIN_VALUE, Integer.MAX_VALUE);

		//reduce the usage of Secret and Double move
		if (move instanceof MoveTicket) {
			MoveTicket thismove = (MoveTicket)move;
			if (thismove.ticket.equals(Ticket.Secret)) choice = choice - 100;
		}
		else if (move instanceof MoveDouble) choice = choice - 100;
		boardscores.put(move, choice);
    	}
	return average(boardscores, location, moves);
    }

    public Move average(Map<Move, Integer> minimax, int location, Set<Move> moves) {
	   Map<Move, Integer> one = onelookahead(this.view, location, moves);
	   for (Move m : one.keySet()) {
		one.put(m, minimax.get(m) + one.get(m));
	   }
	return findbestMove(one);
    }

    public int MaxMove(Move move, int depth, ScotlandYardModel currentGame, int alpha, int beta) {

	int currentDistance = distancetoMrX(currentGame.getPlayerLocation(currentGame.getCurrentPlayer()), currentGame.getPlayerLocation(Colour.Black));
	if (move instanceof MoveDouble) currentGame.play((MoveDouble)move);
	if (move instanceof MoveTicket) currentGame.play((MoveTicket)move);
	//assume detectives only get closer to MrX (else it is not an interesting move)
	if (currentGame.getCurrentPlayer() != Colour.Black) {
		int newDistance = distancetoMrX(currentGame.getPlayerLocation(currentGame.getCurrentPlayer()), currentGame.getPlayerLocation(Colour.Black));
		if ((newDistance - currentDistance) > 75) return (Integer.MIN_VALUE + 500);
		if ((newDistance - currentDistance) > 30) return (Integer.MIN_VALUE + 1000);
	}
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
		int depthbest = 0;
		for (Move m: currentGame.validMoves(player)) {

			ScotlandYardModel thislayer = createGame(currentGame, currentGame.getPlayerLocation(player));
			while (thislayer.getCurrentPlayer() != player) thislayer.nextPlayer();

			int currentbest = MaxMove(m, depth-1, thislayer, alpha, beta);

			if (player.equals(Colour.Black)) {
				if (depthbest == 0) depthbest = currentbest;
				else if (currentbest > depthbest) {
					depthbest = currentbest;
					alpha = currentbest;
				}
			if (beta > alpha) return depthbest;
			}
			else {
				if (depthbest == 0) depthbest = currentbest;
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
		File file = new File("resources/pos.txt");	
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
