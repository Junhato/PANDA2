package solution;

import scotlandyard.*;
import java.awt.event.*;
public class gameStartController {
	private gameStart sview;
	//private ScotlandYardModel model;
	public gameStartController(/*ScotlandYardModel model, */gameStart sview) {
		//this.model = model;
		this.sview = sview;
		this.sview.addListener(new AddListener());
		this.sview.addStartListener(new startListener());
	}

class AddListener implements ActionListener {
	public void actionPerformed(ActionEvent actionEvent) { 
		String colour = sview.getColour();
		String location = sview.getStartLocation();
		sview.addText(colour, location);
		sview.addStart();
	}

}
class startListener implements ActionListener {
	public void actionPerformed(ActionEvent actionEvent){
		sview.disapper();
		gameView view = new gameView();
 		gameController controller = new gameController(view);
 		view.setVisible(true);
	} 
}
}
