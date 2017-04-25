import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Model {

	HashMap<Integer, Integer> board;
	ArrayList<ChangeListener> listeners;

	private int gameState = GAME_STATE_PLAYER_A;
	static final int GAME_STATE_PLAYER_A = 0;
	static final int GAME_STATE_PLAYER_B = 1;
	static final int GAME_STATE_FREETURN = 2;
	private int mancala_A_Key = 6;
	private int mancala_B_Key = 13;
	int lastStonePit = 0;
	int pit = 0;
	boolean freeTurn = false;
	Model(int stones) {
		board = new HashMap<>();
		listeners = new ArrayList<>();
		// setting up the board for testing purposes
		for (int i = 0; i < 13; i++)
			board.put(i, stones);
		board.put(6, 0); // A's mancala
		board.put(13, 0); // b's mancala
		
	}

	@SuppressWarnings("unchecked")
	public HashMap<Integer, Integer> getBoard() {
		return (HashMap<Integer, Integer>) board;
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

	public void makeMove(int pit){
		freeTurn = false;
		moveStones(pit);
		
		//number of stones in last pit before stones were moved. Once stones are moved, empty pit will have 1 stone.
		int lastPit_Stones = board.get(lastStonePit);
//		if(lastStonePit == mancala_A_Key || lastStonePit == mancala_B_Key){
//			freeTurn = true;
//		}
		
		if(getGameState() == GAME_STATE_PLAYER_A && (lastStonePit >= 0 && lastStonePit <= 6)){
			applyRules(mancala_A_Key, lastPit_Stones);
		}
		else if(getGameState() == GAME_STATE_PLAYER_B && (lastStonePit >= 7 && lastStonePit <=13)){
			applyRules(mancala_B_Key, lastPit_Stones);
		}
					
		
		if(!freeTurn)
			changeGameState();	
	}
	
	public void applyRules(int mancalaKey, int lastPit_Stones){
		
		if(lastStonePit == mancalaKey)
			freeTurn = true;
		else if(lastPit_Stones - 1 == 0){
		//capture from opposite pit
		capture(mancalaKey, 12 - lastStonePit);
		}
	
	}

	

	public void capture(int mancalaKey, int oppositePit){
		board.put(mancalaKey, board.get(mancalaKey) + board.get(oppositePit));
		board.put(oppositePit, 0);
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
	      Change the data in the model at a particular location
	      @param pitIndex key of the map
	      @param stones the new stones
	   */	
	
	public void updatePits(int pitIndex){
		
		makeMove(pitIndex);
//		board.put(pitIndex, stones);
//		setPitIndex(pitIndex);
//		if(isValidPit(pitIndex)){
//			rules(pitIndex);
//		}
		 for (ChangeListener l : listeners)
	      {
	         l.stateChanged(new ChangeEvent(this));
	      }
		
	}
	
	private void setPitIndex(int pitIndex) {
		this.pit = pitIndex;	
	}

	public boolean isValidPit(int pit) {
		if (getGameState() == GAME_STATE_PLAYER_A && (pit >= 0 && pit < 6))
			return true;
		if (getGameState() == GAME_STATE_PLAYER_B && (pit >= 7 && pit < 13))
			return true;
		
		if(board.get(pit) == 0)
			return false;
		return false;
	}

	
	public int declareWinner(){
		int stonesMancalaA = board.get(mancala_A_Key);
		int stonesMancalaB =  board.get(mancala_B_Key);
		if(stonesMancalaA == stonesMancalaB)
			return -1;
		else if(Math.max(stonesMancalaA, stonesMancalaB) == stonesMancalaA)
			return GAME_STATE_PLAYER_A;
		else
			return GAME_STATE_PLAYER_B;
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
		return playerAflag || playerBflag;
	}
}
