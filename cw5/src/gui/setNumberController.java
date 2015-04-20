package solution;

import scotlandyard.*;
import java.awt.event.*;
import java.util.*;
import java.io.IOException;

//initialize ScotlandYardModel
public class setNumberController {
	private setNumberPlayer setNumber;
	private ScotlandYardModel model;
	private GameData currentGD;
	public setNumberController(setNumberPlayer setNumber) {
		this.setNumber = setNumber;
		this.setNumber.addNumberListener(new numberListener());
		this.setNumber.addLoadListener(new loadListener());
	}
//action listener for numberButton
//get number of player from combobox
//initialize ScotlandYardModel
class numberListener implements ActionListener {
	public void actionPerformed(ActionEvent actionEvent) { 
		setNumber.setVisible(false);
		List<Boolean> rounds = Arrays.asList(false, false, true, false, false, false, false, true, false, false, false, false, true, false, false, false, false, true, false, false, false);
		try{
			model = new ScotlandYardModel(setNumber.getNumber() - 1, rounds, "../resources/graph.txt");
		}
		catch(IOException e){
			System.err.println(e);
		}
		gameStart start = new gameStart();
		gameStartController scontroller = new gameStartController(model, start);
		start.setVisible(true);

	}

}
//action listener for loadButton
//get string for file name from loadField 
//load the game
class loadListener implements ActionListener {
	public void actionPerformed(ActionEvent actionEvent) { 
<<<<<<< HEAD
		String textFieldValue = loadField.getText();
		//what if invalid
		int save = Integer.parseInt(textFieldValue);
		if (save < 0) System.out.println("invalid savefile number");
		else {
		ScotlandYardModel model = GameData.loadGame(save);
=======
		//what if invalid
		int save = Integer.parseInt(setNumber.getSaveNumber());
		if (save < 0) System.out.println("invalid savefile number");
		else {
		ScotlandYardModel model = GameData.loadGame(save);
		currentGD = new GameData(model);
>>>>>>> b2a3f6f04e53b3f850d048aa3a53ab234d3cce35
		setNumber.setVisible(false);
		
		gameView view = new gameView();
		gameController controller = new gameController(model, view, currentGD);
		view.setVisible(true);
		}
	}
}

}
