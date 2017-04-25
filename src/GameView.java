import java.util.HashMap;
import java.util.Scanner;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GameView implements ChangeListener{

	HashMap<Integer, Integer> b;
	DraftModel m;
	Scanner s = new Scanner(System.in);
	int selectedPit = 0;


	GameView(DraftModel m) {
		this.m = m;
		this.b = m.getBoard();
		printBoard();
//		System.out.println(m.getGameState() + "'s turn.");
//		System.out.println("Select pit");
//		int pit = s.nextInt();
//		if (m.isValidPit(pit)) {
//			m.makeMove(pit);
//			printBoard();
//			System.out.println("Last stone dropped in pit #" + m.lastStonePit);
//		} else
//			System.out.println(m.getGameState() + "'s turn. Choose valid pit");

	}
	public int getSelectedPit() {
		return selectedPit;
	}

	public void setSelectedPit(int selectedPit) {
		this.selectedPit = selectedPit;
	}
	/*
	 * choose pit move stones print new map
	 */
	public void printBoard() {

		for (int i = 12; i >= b.size() / 2; i--) {
			System.out.printf("\t%s \t", b.get(i));
		}
		System.out.println("\n" + b.get(13));
		for (int i = 0; i < b.size() / 2; i++) {
			System.out.printf("\t%s \t", b.get(i));
		}
		System.out.println("\n");

	
	}


	@Override
	public void stateChanged(ChangeEvent e) {
		b = m.getBoard();
		printBoard();
	}
}