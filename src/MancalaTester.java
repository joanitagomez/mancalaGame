import java.util.Scanner;

public class MancalaTester {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		Model m = new Model(3);
		while (!m.isGameOver()) {
			System.out.println(m.getGameState() + "'s turn.");
			System.out.println("Select pit");
			int pit = s.nextInt();
			if (m.isValidPit(pit)) {
				m.rules(pit);
				m.printBoard();
				System.out.println("Last stone dropped in pit #" + m.lastStonePit);
			} else
				System.out.println(m.getGameState() + "'s turn. Choose valid pit");
		}
		System.out.println("*Game Over*");
		System.out.println("Winner: " + m.declareWinner());
	}
	
//	public static void main(String[] args) {
//		System.out.println(Math.max(3, 3));
//	}
}
