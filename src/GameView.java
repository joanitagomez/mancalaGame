import java.util.HashMap;
import java.util.Scanner;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GameView implements ChangeListener{

	HashMap<Integer, Integer> b;
	Model m;
	Scanner s = new Scanner(System.in);
	int selectedPit = 0;


	GameView(Model m) {
		this.m = m;
		this.b = m.getBoard();
		printBoard();


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