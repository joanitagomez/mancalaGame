import java.awt.Container;
import java.util.Scanner;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MancalaTester {

	public static void main1(String[] args) {
		Scanner s = new Scanner(System.in);
		Model model = new Model(3);
		// GameView view = new GameView(model);
		// model.addChangeListener(view);

		BoardFrame view = new BoardFrame(model);
		while (!model.isGameOver()) {
			System.out.println(model.getGameState() + "'s turn.");
			System.out.println("Select pit");
			int pit = s.nextInt();

			// int pit = view.getSelectedPit();
			if (model.isValidPit(pit))
				model.updatePits(pit);
			else
				System.out.println("Choose valid pit.");
		}
		System.out.println("*Game Over*");
		System.out.println("Winner: " + model.declareWinner());
	}

	
	
	public static void main(String[] args) {		

		
		String strStones = JOptionPane.showInputDialog("How many stones do you want to play with?");
		int stones  = Integer.parseInt(strStones);
		
		Model model = new Model(stones);
		BoardFrame view = new BoardFrame(model);
		
		while (!model.isGameOver()) {			
			int pit = view.getPit();
			if(pit == -1)
				continue;
			if(model.isValidPit(pit))
				model.updatePits(pit);
	
		}
		System.out.println("*Game Over*");
		System.out.println("Winner: " + model.declareWinner());
	}

}
