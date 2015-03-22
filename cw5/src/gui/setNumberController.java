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
	}

class numberListener implements ActionListener {
	public void actionPerformed(ActionEvent actionEvent) { 
		setNumber.setVisible(false);
		List<Boolean> rounds = Arrays.asList(true, false);
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

}
