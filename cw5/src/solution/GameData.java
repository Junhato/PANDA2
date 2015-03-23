//the location before move is the first element
//if I click pass nothing happens...

package solution;

import scotlandyard.*;

import java.util.Scanner;
import java.io.*;
import java.util.*;

public class GameData {

     static class MovewLocation {
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

    int save;
    ScotlandYardModel CurrentGame;
    static ArrayList<String> SaveData;
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
	SaveData.add(initial);
    }

    public static GameData getInstance(ScotlandYardModel game) {
	if (GDInstance == null) {
		GDInstance = new GameData(game);
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

	    for (Integer i: game.initiallocation) {
		    start = start + " " + i;
	    }
	return start;
    }

   public static MovewLocation toMove(String str) {
	Move ticket = null;
       	String[] ticketparts = str.split(" ");
	if (ticketparts[2].equals("Pass")) ticket = new MovePass(Colour.valueOf(ticketparts[3]));
	
	else if (ticketparts[2].equals("Double")) {
		MoveTicket one = new MoveTicket(Colour.valueOf(ticketparts[3].substring(0, ticketparts[3].length()-1)), 
						Integer.parseInt(ticketparts[4]), Ticket.valueOf(ticketparts[5]));
		MoveTicket two = new MoveTicket(Colour.valueOf(ticketparts[3].substring(0, ticketparts[3].length()-1)), 
						Integer.parseInt(ticketparts[7]), Ticket.valueOf(ticketparts[8]));

		ticket = new MoveDouble(Colour.valueOf(ticketparts[3].substring(0, ticketparts[3].length()-1)), one, two);
	}
	else {
		ticket = new MoveTicket(Colour.valueOf(ticketparts[1]), Integer.parseInt(ticketparts[2]), Ticket.valueOf(ticketparts[3]));
	}
	int location = Integer.parseInt(ticketparts[0]);
	
	return new MovewLocation(ticket, location);
    }

    public void saveGame(String s) {
	SaveData.add(s);
    }

    public void saveGame() {
	//saves.put(name, save);
	String filename = "save" + save + ".txt";
	try {
	PrintWriter writer = new PrintWriter(filename, "UTF-8");
	writer.println(save);
	for (int i = 0; i < SaveData.size(); i++) {
	writer.println(SaveData.get(i));
	}
	writer.close();
	save++;
	} catch (IOException e) {e.printStackTrace();}
    }
    //void saveCatalogue() {}

//assume tickets are all fixed??
    public static ScotlandYardModel loadGame(int save) {
// read save catalogue first	    
// read file, start the game and play all moves
	ScotlandYardModel game = null;
	try {
		File file = new File("save" + save + ".txt");
		Scanner in = new Scanner(file);
		SaveData = new ArrayList<String>();
		if (in.hasNextLine()) in.nextLine();
		while (in.hasNextLine()) {SaveData.add(in.nextLine());}
      		in.close();
	} catch (Exception e) { System.err.println("file does not exist"); }

		int i;
		String[] initialparts = SaveData.get(0).split(" ");
		List<Boolean> rounds = new ArrayList<Boolean>();

		for (i = 0; i < Integer.parseInt(initialparts[2]); i++) {
			rounds.add(Boolean.valueOf(initialparts[3 + i]));
		}
		i = i + 3; //24	
		try { game = new ScotlandYardModel(Integer.parseInt(initialparts[0]), rounds, initialparts[1]);
		} catch (IOException e) {System.err.println("problem loading the game");}
		int k = Integer.parseInt(initialparts[i]);
		i = i + 1;
		for (int j = 0; j < k; j++) {

			SYPlayer player = null;
			Map<Ticket, Integer> Tickets = new HashMap<Ticket, Integer>();
			Tickets.put(Ticket.Taxi, 11);
			Tickets.put(Ticket.Bus, 8);
			Tickets.put(Ticket.Underground, 4);

			if (Colour.valueOf(initialparts[i + j]).equals(Colour.Black)) {

				Tickets.put(Ticket.DoubleMove, 2);
				Tickets.put(Ticket.SecretMove, 5);	
			}
			game.join(player, Colour.valueOf(initialparts[i + j]), Integer.parseInt(initialparts[i+k+j]), Tickets);
		}
	//check isready
	if (!game.isReady()) System.out.println("problem loading the game");
	else {
		for (int j = 1; j < SaveData.size(); j++) {

			//should be able to play these moves visually??

			MovewLocation amove = toMove(SaveData.get(j));
			if (amove.getM() instanceof MoveDouble) {
				MoveDouble m = (MoveDouble)amove.getM();
				game.play(m);
				game.nextPlayer();
			}
			else if (amove.getM() instanceof MoveTicket) {
			       MoveTicket m =(MoveTicket)amove.getM();
			       game.play(m);
			       game.nextPlayer();
			}
			else {
				MovePass m = (MovePass)amove.getM();
				game.play(m);
				game.nextPlayer();
			}
		}
	}
	return game;
    }

   public void replaylastmove() {
	if (SaveData.size() == 0) {
		System.out.println("no move has been played yet");
	}
	else {
		MovewLocation current = toMove(SaveData.get(SaveData.size() -1));
		if (current.getM() instanceof MovePass) return;
		else if (current.getM() instanceof MoveDouble) {
			MoveDouble m = (MoveDouble)current.getM();
			MoveTicket first = (MoveTicket)m.moves.get(0);
			MoveTicket second = (MoveTicket)m.moves.get(1);
			undolastmove(second, first.target);
			undolastmove(first, current.getL());
			SaveData.remove(SaveData.size() -1);
		}
		else {
			MoveTicket m = (MoveTicket)current.getM();
			undolastmove(m, current.getL());
			SaveData.remove(SaveData.size() -1);
		}
	}
    }
    public void undolastmove(Move move, int returnto) {
	if (move == null) return;
	else if (move instanceof MoveTicket) {
		MoveTicket m = (MoveTicket)move;
		CurrentGame.previousPlayer();
		SYPlayer current = CurrentGame.gameMembers.get(m.colour);
		current.setLocation(returnto, CurrentGame.getRounds().get(CurrentGame.getRound()- 1));
		current.addTicket(m.ticket);
		if (!m.colour.equals(Colour.Black))  {
			CurrentGame.MrX.removeTicket(m.ticket);
		}
		else {
			CurrentGame.setRound(CurrentGame.getRound() - 1);
		}
	}
    }
}    
