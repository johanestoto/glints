package com.glints.toto.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Class for setting up Tic Tac Toe Board
 * @author Johanes Toto Indarto
 * 
 */
public class Board {

	private    int DIMENSION ;
 
	private Kotak[][] kotaks;
	
	public Board(int dimension) {
		//configurable board dimension supplied from index.html
		DIMENSION=dimension;
 
		kotaks = new Kotak[ DIMENSION ][ DIMENSION ];

		for ( int rowIndex = 0; rowIndex < DIMENSION; rowIndex++ ) {
			for ( int columnIndex = 0; columnIndex < DIMENSION; columnIndex++ ) {
				kotaks[ rowIndex ][ columnIndex ] = new Kotak( rowIndex, columnIndex );
			}
		}
	}
	
	/**
	 * Method to get board dimension
	 * 
	 */
	public int getDimension() {
		return DIMENSION;
	}
 

	/**
	 * Method to get square object in the form of <row>-<column>
	 * 
	 */
	public Kotak get( String tileId ) {
		String[] indices = tileId.split( "-" );
		// in case of incorrect format, return null
		if ( indices.length != 2 ) {
			return null;
		}
		// index of row is first array of indices
		int rowIndex = Integer.valueOf( indices[ 0 ] );
		// index of column is second array of indices
		int columnIndex = Integer.valueOf( indices[ 1 ] );
		return get( rowIndex, columnIndex );
	}


	/**
	 * Method to get square object with rowIndex and columnIndex parameter
	 */
	public Kotak get( int rowIndex, int columnIndex ) {
		return kotaks[ rowIndex ][ columnIndex ];
	}

	/**
	 * Method to get available square for Computer turn 
	 */
	public Kotak getRandomAvailable() {
		List<Kotak> available = new ArrayList<>();

		// populate available list if still found the empty square
		for ( Kotak[] row : kotaks ) {
			for ( Kotak kotak : row ) {
				if ( kotak.isEmpty() ) {
					available.add( kotak );
				}
			}
		}

		// if not available at all, retun null for square
		if ( available.isEmpty() ) {
			return null;
		}

		// generate random available empty square index, to give which square location should run by computer
		int randomNum = new Random().nextInt( available.size() );
		
		// finally return the square for computer turn
		return available.get( randomNum );
	}

	/**
	 * Method to check whether the board square is fully occupied or not
	 */
	public boolean isFull() {
		for ( Kotak[] row : kotaks ) {
			for ( Kotak kotak : row ) {
				if( kotak.isEmpty() ) {
					// found one of empty square, return the board is not full
					return false;
				}
			}
		}
		
		// all square is not empty, return the board is full
		return true;
	}

	/**
	 * Method to get all total square 
	 */
	public Kotak[][] getTiles() {
		return kotaks;
	}

	/**
	 * Method to get list of square on specific row index
	 */
	public List<Kotak> getBaris( int rowIndex ) {
		return Arrays.asList( kotaks[ rowIndex ] );
	}

	/**
	 * Method to get list of square on specific column index
	 */
	public List<Kotak> getColumn( int columnIndex ) {
		List<Kotak> column = new ArrayList<>();

		for ( Kotak[] row : kotaks ) {
			column.add( row[ columnIndex ] );
		}

		return column;
	}


	/**
	 * Method to get list of square on diagonal axis from decreasing left to right
	 */
	public List<Kotak> getDiagonalLeftTopBottomRight() {
		List<Kotak> diagonalList =new ArrayList<>();
		for ( int i = 0; i < DIMENSION; i++ ) {
			diagonalList.add( get( i,i ) );
		}
		return diagonalList;
	}

	/**
	 * Method to get list of square on diagonal axis from increasing left to right
	 */
	public List<Kotak> getDiagonalRightTopBottomLeft() {
		List<Kotak> diagonalList =new ArrayList<>();
		int row= DIMENSION-1;
		for ( int i = 0; i < DIMENSION; i++ ) {
			diagonalList.add( get( i,row) );
			row--;
		}
		return diagonalList;
	}

	/**
	 * Method to prepare square lines to be the based possibility pattern of WIN evaluation
	 * Tic tac toe WIN pattern should evaluate the horizontal, vertical and diagonal lines
	 * For example, if you set dimension to 3, which is 3 x 3 total square we need to prepare 3 horizontal lines possibilities, 3 vertical lines possibilities and 2 diagonal lines possibilities. Total possibilities for WIN pattern would 8 lines for 3 x 3 dimension
	 * 
	 */
	public List<List<Kotak>> getAllEligibleWinCandidateLines() {
		List<List<Kotak>> lines = new ArrayList<>();

		//prepare the  horizontal pattern
		for ( int i = 0; i < DIMENSION; i++ ) {
			lines.add( getBaris( i ) );
		}

		//prepare the  vertical pattern
		for ( int j = 0; j < DIMENSION; j++ ) {
			lines.add( getColumn( j ) );
		}

		//prepare all 2 different diagonal pattern
		lines.add( getDiagonalLeftTopBottomRight() );
		lines.add( getDiagonalRightTopBottomLeft() );

		return lines;
	}

	/**
	 * Method to reset all square to be empty and ready for a new game
	 */
	public void reset() {
		for ( Kotak[] row : kotaks ) {
			for ( Kotak kotak : row ) {
				kotak.setValue( Kotak.Value.EMPTY );
			}
		}
	}
}
