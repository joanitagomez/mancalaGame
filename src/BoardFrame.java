import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class BoardFrame extends JFrame  {
	public static final int DEFAULT_WIDTH = 600;
	public static final int DEFAULT_HEIGHT = 400;
	Component p;
	

	public BoardFrame(Model model) {
		
		p = new PitComponent(model);
		add(p,BorderLayout.NORTH);
		
		JLabel label = new JLabel(model.getGameState() + "'s turn.");
		add(label,BorderLayout.SOUTH);
		setTitle("Mancala");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
	}

	public int getPit(){
		return ((PitComponent) p).getSelectedPit();
	}



}