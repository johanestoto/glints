package com.glints.toto.model;

import java.util.List;

 
/**
 * Main class for accessing game board, game state, game selection and player state
 * @author Johanes Toto Indarto
 */
public class Game {
	
	private Board board;
	private PlayerState playerState;
	private boolean playerGoFirst;
	private boolean nextMoveX;
	private String existingOpponent;
	private GameSelection gameSelection;
	private boolean yourTurn;
	
	/**
	 * Default Constructor to instantiating game object 
	 */
	public Game() {
	}
 
	/**
	 * Method to prepare the new game with selected board dimension
	 * Initialize player state, movement state and board
	 */
	public void startNew(int dimension) {
		System.out.print("startNew");
		playerGoFirst = true;
		nextMoveX = true;
		playerState = PlayerState.IN_PROGRESS;
		board = new Board(dimension);
		System.out.print(" startNew dimension:"+dimension+" board: "+board );
	}

	/**
	 * Method to access Board object 
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * Method to mark the square when user click the square on playing with COMPUTER
	 */
	public void markTile( String tileId ) {
		System.out.print("markTile tileId"+tileId+", board: "+board);
		setTileValue( board.get( tileId ) );
	}
	
	/**
	 * Method to mark the square when user click the square on playing with HUMAN
	 */
	public PlayerState markTileHuman( String tileId,	boolean goFirst) {
		 
		System.out.print("markTileHuman tileId"+tileId);
		return setTileValueHuman( board.get( tileId ) ,goFirst);
	}
	
	public void markTileRandom() {
		setTileValue( board.getRandomAvailable() );
	}

	/**
	 * Method to set the square value to X or O and evaluating the WIN/LOSS/DRAW condition on playing with COMPUTER
	 */
	private void setTileValue( Kotak kotak ) {
		
		//if game state is game over or the square is occupied, no need to set value and evaluate condition, just return
		if ( isGameOver() || !kotak.isEmpty() ) {
			return;
		}

		//set the square value to X on player move (if go first) and O for COMPUTER, we set nextMoveX to true on game initialization to set value X for current player
		kotak.setValue( nextMoveX ? Kotak.Value.X : Kotak.Value.O );
		
		//inverse nextMoveX value , so next mark will always changing from X to O and the other way arround
		nextMoveX = !nextMoveX;
		
		//evaluate WIN value
		Kotak.Value winValue = evaluateWinValue();

		if ( winValue != null ) {
			//winValue is not null means the game state finished with WIN or LOSS condition
			//winValue is X or O, this winValue should be associated with playerValue to know for this who belong to. Belong to player or belong to opponent
			//to check player value is X or O we need to see whether the player on first turn or not, if player on first turn it should have the value of X otherwise is O
			//to know that player has the first turn, we can check it on 'Play First' checkbox on play.html
			Kotak.Value playerValue = playerGoFirst ? Kotak.Value.X : Kotak.Value.O;
			//after we know the player value is X or O, then we compare with winValue, if it the same, then classify the player to WIN, otherwise LOSS
			playerState = winValue == playerValue ? PlayerState.WIN : PlayerState.LOSS;
		} else {
			//winValue is not null means the game can be still in progress with available empty board square or finished with DRAW condtion if board square already full
			playerState = board.isFull() ? PlayerState.DRAW : PlayerState.IN_PROGRESS;
		}
	}
 
	/**
	 * Method to set the square value and evaluating the WIN/LOSS/DRAW condition on playing with HUMAN
	 */
	private 	PlayerState   setTileValueHuman( Kotak kotak,boolean goFirst) {
		
		//if game state is game over or the square is occupied, no need to set value and evaluate condition, just return
		if ( isGameOver() || !kotak.isEmpty() ) {
			return null;
		}
		
		//First player is the player then create game for the first time , Second Player is the Player that play with existing game
		//Set the square value to X on First Player move and O for Second Player, we set nextMoveX to true on game initialization to set value X for First Player
		kotak.setValue( nextMoveX ? Kotak.Value.X : Kotak.Value.O );
		
		//inverse nextMoveX value , so next mark will always changing from X to O and the other way arround
		nextMoveX = !nextMoveX;
		PlayerState pState;
		Kotak.Value winValue = evaluateWinValue();
		if ( winValue != null ) {
			//winValue is not null means the game state finished with WIN or LOSS condition
			//winValue is X or O, this winValue should be associated with playerValue to know for this who belong to. Belong to player or belong to opponent
			//to check player value is X or O we need to see whether it is First Player or Second Player, if First Player it should have the value of X otherwise is O
			//to know it's First Player , we need to compare the player that has the first turn or not with goFirst variable. If goFirst true then it is First Player.
			//goFirst variable is assigned true if the player create new HUMAN game, goFirst variable is false if player accepting existing game to play
			Kotak.Value playerValue = goFirst ? Kotak.Value.X : Kotak.Value.O;
			//after we know the player value is X or O, then we compare with winValue, if it the same, then classify the player to WIN, otherwise LOSS
			  pState = winValue == playerValue ? PlayerState.WIN : PlayerState.LOSS;
		} else {
			//winValue is not null means the game can be still in progress with available empty board square or finished with DRAW condtion if board square already full
			  pState = board.isFull() ? PlayerState.DRAW : PlayerState.IN_PROGRESS;
			
		}
 
		return pState;
	}
	
	/**
	 * Method to evaluate WIN for both COMPUTER and HUMAN game opponent
	 */
	private Kotak.Value evaluateWinValue() {

		//get all eligible lines to be the candidate of WIN lines, with the pattern of horizontal, vertical and diagonal 
		List<List<Kotak>> allLines = board.getAllEligibleWinCandidateLines();

		//iterates all eligible lines 
		for ( List<Kotak> line : allLines ) {
			
			//pick the last square of each eligible lines
			Kotak last = line.get(board.getDimension()-1 );
			
			//check if last square of eligible line already marked or not
			if ( last.isEmpty() ) {
				//if last square are not marked/clicked by player means still empty and not eligible for evaluation
				//we simply skip this line for evaluation
				//this is simple hack to check at last square other than first square to save the loop process shorter
				continue;
			}

			//at this point means we have last square is marked, then we try to compare against all of squares in this same lines
			//if all squares in this line has the same mark value , means this lines is WIN, immediately return the square value
			if ( line.stream().allMatch( t -> t.getValue() == last.getValue() ) ) {
				return last.getValue();
			}
		}

		return null;
	}
	
	public void setPlayerGoFirst( boolean flag ) {
		this.playerGoFirst = flag;
	}

	public boolean isPlayerGoFirst() {
		return playerGoFirst;
	}
	
	public void setYourTurn( boolean yourTurn ) {
		this.yourTurn = yourTurn;
	}

	public boolean isYourTurn() {
		return yourTurn;
	}
	
	public boolean isGameOver() {
		return playerState.isGameOver();
	}

	
	public PlayerState getPlayerState() {
		return playerState;
	}
	
	public GameSelection getGameSelection() {
		return gameSelection;
	}
	
	public void setGameSelection(GameSelection gameSelection) {
		this.gameSelection= gameSelection;
	}
	
	public String getExistingOpponent() {
		return existingOpponent;
	}
	
	public void setExistingOpponent(String existingOpponent) {
		this.existingOpponent= existingOpponent;
	}
	
}
