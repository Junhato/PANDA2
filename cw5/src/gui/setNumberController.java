package solution;

import scotlandyard.*;
import java.awt.event.*;
import java.util.*;
import java.io.IOException;
public class setNumberController {
	private setNumberPlayer setNumber;
	private ScotlandYardModel model;
	public setNumberController(setNumberPlayer setNumber) {
		this.setNumber = setNumber;
		this.setNumber.addNumberListener(new numberListener());
		this.setNumber.addLoadListener(new loadListener());
	}

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
class loadListener implements ActionListener {
	public void actionPerformed(ActionEvent actionEvent) { 
		String textFieldValue = loadField.getText();
		//what if invalid
		int save = Integer.parseInt(textFieldValue);
		if (save < 0) System.out.println("invalid savefile number");
		else {
		ScotlandYardModel model = GameData.loadGame(save);
		setNumber.setVisible(false);
		
		gameView view = new gameView();
		gameController controller = new gameController(model, view);
		view.setVisible(true);
		}
	}
}

}
