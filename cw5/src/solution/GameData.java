//call this when game is ready
//set a boolean in isready to just initialize gamedata once??
//so future isready call can still be made??
//String current = "" + game.gameMembers.(move.colour).getLocation();
//saveGame(current + move.toString());
//the location before move is the first element

package solution;

import scotlandyard.*;

import java.util.Scanner;
import java.io.*;
import java.util.List;

public class MovewLocation {
     Move move;
     int location;

     MovewLocation(Move move, int location) {
	this.move = move;
	this.location = location;
     }

     Move getM() {
	return move;
     }
     int getL() {
	return location;
     }
}

public class GameData {

    int save;
    ScotlandYardModel CurrentGame;
    ArrayList<String> SavedData;
    //Map<String, Integer> Saves;

    private static GameData GDInstance = null;

    private GameData(ScotlandYardModel game) {
        CurrentGame = game;
	try {
	File file = new File("save0.txt");
	Scanner in = new Scanner(file);
	String savecount = in.nextLine();
	save = Integer.parseInt(savecount);
	in.close();

	} catch (Exception e) { save = 0; }
//if there is no save file then save = 0;
	SaveData = new ArrayList<String>();
	String initial = initialization(game);
	SavaData.add(initial);
    }

    public static GameData getInstance(ScotlandYardModel game) {
	if (GDInstance == null) {
		GDInstance = new GDInstance(game);
	}
	return GDInstance;
    }

    String initialization(ScotlandYardModel game) {
	    if (game == null) return "";

	    String start = "" + game.numberOfDetectives + " " + game.graphFileName + " " + game.getRounds().size();
	    for (Boolean b : game.getRounds()) {
		    start = start + " " + String.valueOf(b);	
	    }

	    start = start + " " + game.getPlayers().size();

	    for (Colour c : game.getPlayers()) {
		    start = start + " " + c.name();
	    }
	    start = start + " ";

	    for (Integer i: game.initiallocation) {
		    start = start + " " + i;
	    }
	return start;
    }

    MovewLocation ToMove(String str) {
	Move ticket = null;
       	String[] ticketparts = str.split(" ");
	if (ticketparts[2].equals("Pass")) ticket = new MovePass(Colour.valueOf(ticketparts[3]));
	
	else if (ticketparts[2].equals("Double")) {
		MoveTicket one = new MoveTicket(Colour.valueOf(ticketparts[3].substring(0, str.length()-1)), 
						Integer.parseInt(ticketparts[4]), Ticket.valueOf(ticketparts[5]));
		MoveTicket two = new MoveTicket(Colour.valueOf(ticketparts[3].substring(0, str.length()-1)), 
						Integer.parseInt(ticketparts[8]), Ticket.valueOf(ticketparts[9]));

		ticket = new MoveDouble(Colour.valueOf(ticketparts[3]), one, two);
	}
	else {
		MoveTicket one = new MoveTicket(Colour.valueOf(ticketparts[1]), Integer.parseInt(ticketparts[2]), Ticket.valueOf(ticketparts[3]));
	}
	int location = ticketparts[0];
	
	return new MovewLocation(ticket, location);
    }

    public static void saveGame(String s) {
	SavaData.add(s);
    }

    public void saveGame() {
	//saves.put(name, save);
	String filename = "save" + save + ".txt";
	PrintWriter writer = new PrintWriter(filename, "UTF-8");
	writer.println(save);
	for (int i = 0; i < SaveData.size(); i++) {
	writer.println(SaveData.get(i));
	}
	writer.close();
	save++;
    }
    //void saveCatalogue() {}

//assume tickets are all fixed??
    public static ScotlandYardModel loadGame(int save) {
// read save catalogue first	    
// read file, start the game and play all moves
	try {
		File file = new File("save" + save + ".txt");
		Scanner in = new Scanner(file);
		SaveData = new ArrayList<String>();
		if (in.hasNextLine()) in.nextLine();
		while (in.hasNextLine()) {SaveData.add(in.nextLine());}
      		in.close();
		int i;
		String[] initialparts = SaveData.get(0).split(" ");
		List<Boolean> rounds = new ArrayList<Boolean>();

		for (i = 0; i < Integer.parseInt(initialparts[2]); i++) {
			rounds.add(Boolean.valueOf(initialparts[3 + i]));
		}
		i = i + 3;	
		ScotlandYardModel game = new ScotlandYardModel(Integer.parseInt(initialparts[0]), rounds, initialparts[1]);

		for (int j = 0; j < Integer.parseInt(initialparts[i]); j++) {
			game.join(Colour.valueOf(initialparts[i + j]), ;
		}
	//check isready??
		for (int k = 1; k < SaveData.size(); k++) {
			MovewLocation = toMove(SaveData.get(k));
			game.play(MovewLocation.getM);
		}

	} catch (Exception e) { System.err.println("file does not exist"); }
    }

   /*public void undolastmove() {
	if (SaveData.size() == 0) {
		System.out.println("no move has been played yet");
	}
	else {
		MovewLocation current = ToMove(SavedData.get(SavedData.size() -1));
		if (current.getM instanceof MovePass) return;
		else if (current.getM instanceof MoveDouble) {
			MoveTicket first = (MoveTicket)current.getM.moves.get(0);
			MoveTicket second = (MoveTicket)current.getM.moves.get(1);
			undolastmove(second, first.target);
			undolastmove(first, current.getL);
			SavedData.remove(SavedData.size() -1);
		}
		else {
			undolastmove(current.getM, current.getL);
			SavedData.remove(SavedData.size() -1);
		}
	}
    }
    public void replaylastmove(Move move, int returnto) {
	if (move == null) return;
	else {
		game.previousPlayer();
		SYPlayer current = game.gameMembers.get(move.colour);
		current.setLocation(returnto, rounds.get(currentRound - 1));
		current.addTicket(move.ticket);
		if (!move.colour.equals(Colour.Black))  {
			MrX.removeTicket(move.ticket);
		}
		else {
			currentRound = currentRound - 1;
		}
	}
    }*/	
}    
