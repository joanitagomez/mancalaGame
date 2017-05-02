import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Joanitha Christle Gomez 
 * Model class holds all the data and logic for the mancala game
 */
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

	/**
	 * Model constructor initializes the data in the game
	 */
	Model(int stones) {
		board = new HashMap<>();
		listeners = new ArrayList<>();
		// setting up the board
		for (int i = 0; i < 13; i++)
			board.put(i, stones);
		board.put(6, 0); // A's mancala
		board.put(13, 0); // b's mancala
	}

	/**
	 * getBoard returns the Hashmap of the board 
	 * @return Hashmap of data values of the pits
	 */
	public HashMap<Integer, Integer> getBoard() {
		return (HashMap<Integer, Integer>) board;
	}

	/**
	 * setPrevBoard sets previous board
	 */
	@SuppressWarnings("unchecked")
	public void setPrevBoard() {
		prevBoard = (HashMap<Integer, Integer>) getBoard().clone();
	}

	/**
	 * getPrevBoard returns the state of previous board
	 * @return Hashmap of data values of the pits
	 */
	public HashMap<Integer, Integer> getPrevBoard() {
		return prevBoard;
	}

	/**
	 * addChangeListener method attaches the changelisteners
	 * @param cl  ChangeListeners
	 */
	public void addChangeListener(ChangeListener c) {
		listeners.add(c);
	}

	/**
	 * getGameState method returns current game state
	 */
	public int getGameState() {
		return this.gameState;
	}

	/**
	 * setGameState method sets the state of game
	 * 
	 * @param gameState:
	 *            state to be set
	 */
	public void setGameState(int gameState) {
		this.gameState = gameState;
	}

	/**
	 * changeGameState method alternates turns
	 */
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

	/**
	 * undo method goes back to the previous move
	 */
	public void undo() {
		this.board = prevBoard;
		if (freeTurn)
			changeGameState();

		changeGameState();
		if (getGameState() == GAME_STATE_PLAYER_A) {
			undoA = true;
			this.maxUndoA--;
		} else {
			undoB = true;
			this.maxUndoB--;
		}

		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}

	/**
	 * makeMove is the main method for mancala game
	 * @param pit- pit player chooses
	 */
	@SuppressWarnings("unchecked")
	public void makeMove(int pit) {
		setPrevBoard();
		freeTurn = false;
		undoA = false;
		undoB = false;
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

	/**
	 * applyRules method decides if there is a free turn or capture to be
	 * performed
	 * @param pit
	 */
	public void applyRules(int mancalaKey, int lastPit_Stones) {
		if (lastStonePit == mancalaKey)
			freeTurn = true;
		else if (lastPit_Stones - 1 == 0) {
			// capture from opposite pit
			capture(mancalaKey, 12 - lastStonePit);
		}
	}

	/**
	 * capture method captures stones from given pit to player's mancala
	 * 
	 * @param capturePit
	 *            : pit from which stones need to be captured
	 * @param mancalaKey:
	 *            mancala where the captured stones should go
	 */
	public void capture(int mancalaKey, int capturePit) {
		board.put(mancalaKey, board.get(mancalaKey) + board.get(capturePit));
		board.put(capturePit, 0);
	}

	/**
	 * moveStones method implements distribution of stones
	 * 
	 * @param pit:
	 *            chosen pit
	 */
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
	 * updatePits method changes the data in model when player intends to move
	 * stones
	 * 
	 * @param pitIndex:
	 *            int value of pit position
	 */

	public void updatePits(int pitIndex) {
		makeMove(pitIndex);
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}

	/**
	 * isValidPit method checks if the chosen pit is valid
	 * 
	 * @param pit
	 *            : chosen pit
	 */

	public boolean isValidPit(int pit) {
		if (getGameState() == GAME_STATE_PLAYER_A && (pit >= 0 && pit < 6))
			return true;
		if (getGameState() == GAME_STATE_PLAYER_B && (pit >= 7 && pit < 13))
			return true;

		if (board.get(pit) == 0)
			return false;

		return false;
	}

	/**
	 * declareWinner method returns winner of the game
	 */
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

	/**
	 * gameOver method is a helper method that captures remaining stones when
	 * game is over
	 * 
	 * @param mancalaKey
	 * @param pit
	 */
	public void gameOver(int mancalaKey, int pit) {
		for (int i = pit; i < pit + 6; i++)
			capture(mancalaKey, pit);
	}

	/**
	 * isGameOver method checks if the game is over
	 */
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

	/**
	 * getUndoA returns playerA's total number of undos
	 * 
	 * @return int value of undo
	 */
	public int getUndoA() {
		return maxUndoA;
	}

	/**
	 * getUndoB returns playerB's total number of undos
	 * 
	 * @return int value of undo
	 */
	public int getUndoB() {
		return maxUndoB;
	}
}
