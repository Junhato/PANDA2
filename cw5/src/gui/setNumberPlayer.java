import java.io.*;
import java.lang.*;
import java.awt.image.BufferedImage;

public class setNumberPlayer extends JFrame{
	private JComboBox<Integer> numberField;
	private Integer[] numbers = {2, 3, 4, 5};
	private JButton numberButton;

	public setNumberPlayer(){
		numberField = new JComboBox<Integer>(numbers);
		//numberField.setMaximumSize(new Dimension(100, 20) );
		
		numberButton = new JButton("next");
		
		JPanel numberPanel = new JPanel();
		numberPanel.setLayout(new FlowLayout());
		numberPanel.setMinimumSize(new Dimension(500, 200));
		numberPanel.add(new JLabel("How many are playing?"));
		numberPanel.add(numberField);
		numberPanel.add(numberButton);

		this.setBackground(Color.WHITE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 		//this.setSize(1200, 900);
		this.setSize(800, 500);

		//this.setLayout(new FlowLayout());
		//this.add(new JLabel("How many are playing?"));
		
		this.add(numberPanel);
		//this.add(numberButton);
		

	}

	public int getNumber(){
		return (int) numberField.getSelectedItem();
	}

	public void addNumberListener(ActionListener listenNumberButton) {

		numberButton.addActionListener(listenNumberButton);
	} 
}
