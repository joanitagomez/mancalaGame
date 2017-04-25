import java.util.Scanner;

import javax.swing.*;
import javax.swing.event.ChangeEvent;

public class MancalaTester {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		DraftModel model = new DraftModel(3);
		GameView view = new GameView(model);

		model.addChangeListener(view);

		while (!model.isGameOver()) {

			System.out.println(model.getGameState() + "'s turn.");
			System.out.println("Select pit");
			int pit = s.nextInt();

			// int pit = view.getSelectedPit();
			if(model.isValidPit(pit))
				model.updatePits(pit);
			else
				System.out.println("Choose valid pit.");
		}
		System.out.println("*Game Over*");
		System.out.println("Winner: " + model.declareWinner());
	}

	public static void main11(String[] args) {
		Scanner s = new Scanner(System.in);
		//
		DraftModel model = new DraftModel(3);
		// GameView view = new GameView(model);
		//
		// model.addChangeListener(view);
		//
		// while (!model.isGameOver()) {
		// int pit = view.getSelectedPit();
		// model.update(pit);
		// }
		// }

		while (!model.isGameOver()) {
			System.out.println(model.getGameState() + "'s turn.");
			System.out.println("Select pit");
			int pit = s.nextInt();

			if (model.isValidPit(pit)) {
				model.makeMove(pit);
				model.printBoard();
				System.out.println("Last stone dropped in pit #" + model.lastStonePit);
			} else
				System.out.println(model.getGameState() + "'s turn. Choose valid pit");
		}
		System.out.println("*Game Over*");
		System.out.println("Winner: " + model.declareWinner());
	}

	public static void main1(String[] args) {
		Model m = new Model(9);
		JFrame frame = new BoardFrameView(m);

	}

}
