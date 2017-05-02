import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Model {

	HashMap<Integer, Integer> board;
	ArrayList<ChangeListener> listeners;

	private int gameState = (new Random()).nextInt(1 - 0 + 1);
	static final int GAME_STATE_PLAYER_A = 0;
	static final int GAME_STATE_PLAYER_B = 1;
	private int mancala_A_Key = 6;
	private int mancala_B_Key = 13;
	int lastStonePit = 0;
	int pit = 0;
	boolean freeTurn = false;
	boolean undoA = false;
	boolean undoB = false;
	int maxUndoA = 3;
	int maxUndoB = 3;
	HashMap<Integer, Integer> prevBoard;

	Model(int stones) {
		board = new HashMap<>();
		listeners = new ArrayList<>();
		// setting up the board
		for (int i = 0; i < 13; i++)
			board.put(i, stones);
		board.put(6, 0); // A's mancala
		board.put(13, 0); // b's mancala
	}

	public HashMap<Integer, Integer> getBoard() {
		return (HashMap<Integer, Integer>) board;
	}

	 @SuppressWarnings("unchecked")
	public void setPrevBoard() {
	  prevBoard = (HashMap<Integer, Integer>) getBoard().clone();
	 }

	public HashMap<Integer, Integer> getPrevBoard() {
		return prevBoard;
	}

	public void addChangeListener(ChangeListener c) {
		listeners.add(c);
	}

	/**
	 * @return current game state
	 */
	public int getGameState() {
		return this.gameState;
	}

	public void setGameState(int gameState) {
		this.gameState = gameState;
	}

	public void changeGameState() {
		switch (this.gameState) {
		case GAME_STATE_PLAYER_A:
			setGameState(GAME_STATE_PLAYER_B);
			break;
		case GAME_STATE_PLAYER_B:
			setGameState(GAME_STATE_PLAYER_A);
			break;

		default:
			System.out.println("Invalid gameState");
			break;
		}
	}

	public void undo() {
		this.board = prevBoard;	
		if(freeTurn)
			changeGameState();
		
			changeGameState();
			if(getGameState() == GAME_STATE_PLAYER_A){
				undoA = true;
				this.maxUndoA--;
			}
			else{
				undoB = true;
				this.maxUndoB--;
			}

			for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}

	@SuppressWarnings("unchecked")
	public void makeMove(int pit) {
		setPrevBoard();
		freeTurn = false;
		undoA = false; undoB = false;
		moveStones(pit);

		// number of stones in last pit before stones were moved. Once stones
		// are moved, empty pit will have 1 stone.
		int lastPit_Stones = board.get(lastStonePit);

		if (getGameState() == GAME_STATE_PLAYER_A && (lastStonePit >= 0 && lastStonePit <= 6)) {
			applyRules(mancala_A_Key, lastPit_Stones);
		} else if (getGameState() == GAME_STATE_PLAYER_B && (lastStonePit >= 7 && lastStonePit <= 13)) {
			applyRules(mancala_B_Key, lastPit_Stones);
		}

		if (!freeTurn)
			changeGameState();
	
	}

	public void applyRules(int mancalaKey, int lastPit_Stones) {
		if (lastStonePit == mancalaKey)
			freeTurn = true;
		else if (lastPit_Stones - 1 == 0) {
			// capture from opposite pit
			capture(mancalaKey, 12 - lastStonePit);
		}
	}

	public void capture(int mancalaKey, int capturePit) {
		board.put(mancalaKey, board.get(mancalaKey) + board.get(capturePit));
		board.put(capturePit, 0);
	}

	public void moveStones(int pit) {
		int stones = board.get(pit);
		board.put(pit, 0);
		int nextPit = pit + 1;

		for (int i = 0; i < stones; i++) {
			if ((pit >= 0 && pit < 6) && nextPit == mancala_B_Key)
				nextPit = 0;
			else if ((pit >= 7 && pit < 13) && nextPit == mancala_A_Key)
				nextPit++;
			else if (nextPit > 13)
				nextPit = 0;

			board.put(nextPit, (board.get(nextPit)) + 1);
			lastStonePit = nextPit;
			nextPit++;
		}
	}

	/**
	 * Change the data in the model
	 * @param pitIndex key of the map
	 */

	public void updatePits(int pitIndex) {
		makeMove(pitIndex);
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}

	public boolean isValidPit(int pit) {
		if (getGameState() == GAME_STATE_PLAYER_A && (pit >= 0 && pit < 6))
			return true;
		if (getGameState() == GAME_STATE_PLAYER_B && (pit >= 7 && pit < 13))
			return true;

		if (board.get(pit) == 0)
			return false;

		return false;
	}

	public int declareWinner() {
		int stonesMancalaA = board.get(mancala_A_Key);
		int stonesMancalaB = board.get(mancala_B_Key);
		if (stonesMancalaA == stonesMancalaB)
			return -1;
		else if (Math.max(stonesMancalaA, stonesMancalaB) == stonesMancalaA)
			return GAME_STATE_PLAYER_A;
		else
			return GAME_STATE_PLAYER_B;
	}

	public void gameOver(int mancalaKey, int pit) {
		for (int i = pit; i < pit + 6; i++)
			capture(mancalaKey, pit);
	}

	public boolean isGameOver() {
		boolean playerAflag = true;
		boolean playerBflag = true;
		for (int i = 12; i >= board.size() / 2; i--) {
			if (!(board.get(i) == 0))
				playerBflag = false;
		}
		for (int i = 0; i < 6; i++) {
			if (!(board.get(i) == 0))
				playerAflag = false;
		}

		if (playerAflag) {
			for (int i = 7; i < 13; i++)
				capture(mancala_B_Key, i);
		}
		if (playerBflag) {
			for (int i = 0; i < 6; i++)
				capture(mancala_A_Key, i);
		}
		return playerAflag || playerBflag;
	}

	public int getUndoA() {
		return maxUndoA;
	}
	
	public int getUndoB() {
		return maxUndoB;
	}
}
