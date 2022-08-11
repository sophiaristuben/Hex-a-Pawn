package hexAPawn;

import java.util.ArrayList;

/**
 * A GameTree is a tree of HexBoard positions.
 * 
 * The children of a position are all of the positions that could
 * result from possible moves from that position.  Knowing what
 * positions are possible can enable us to choose the best.
 * 
 * @author Sophia Ristuben, Liz Johnson
 */
public class GameTree {	
	public GameTree parent;
	public HexBoard board;
	public char color;
	public ArrayList<GameTree> children;
	public HexMove m;
	
	/**
	 * primary constructor (for new GameTree)
	 * 
	 * @param board with which this tree starts
	 * @param color of the player who gets next move
	 */
	public GameTree(HexBoard board, char color) {
		this(board, color, null, null);
	}
	
	/**
	 * alternative constructor (for moves and positions)
	 * 
	 * Generate a GameTree that generates all possible moves originating from this position,
	 * given whose turn it is.
	 * 
	 * @param board	for this position
	 * @param color	of the player who gets next move
	 * @param move HexMove that gets us from our parent to this position
	 * @param parent GameTree for our parent
	 */
	public GameTree(HexBoard board, char color, HexMove m, GameTree parent) {
		// instantiate appropriate instance and local variables
		this.board = board;
		this.color = color;
		this.m = m;
		this.parent = parent;
		this.children = new ArrayList<GameTree>();
		
		// If the opponent has not won
		if (!board.win(HexBoard.opponent(color))) {
			
			// Go through the possible moves on the board
			for (int i = 0; i < board.moves(color).size(); i++) {
				// Create a new board for each of the moves
				HexBoard newBoard = new HexBoard(board, board.moves(color).get(i));
				
				// Add tree to GameTrees a new GameTree list of children 
				GameTree newTree = new GameTree(newBoard, HexBoard.opponent(color), board.moves(color).get(i), this);
				children.add(newTree);
			}
		}
	}

	/**
	 * @return number of possible moves from this position
	 */
	public int numChildren() {
		// implement numChildren by reusing the size method 
		return children.size();
	}
	
	/**
	 * @param i index number
	 * @return GameTree for our i'th child
	 */
	public GameTree getChild(int i) {
		// implement getChild so that it returns the i-th child from the 0-index list
		return children.get(i);
	}
	
	/**
	 * @return total number of positions in this (sub) GameTree
	 */
	public int size() {
		// implement size so that it cumulatively adds up the current GameTree and the nodes of all its children
		int count = 1;
		for (int i = 0; i < numChildren() ;i++) {
			count = count + getChild(i).size();	
		}	
		return count;
	}
	
	/**
	 * Game tree for the parent
	 */
	public GameTree getParent() {
		return parent;
	}
	
	/**
	 * string representation of the board (for this position)
	 */
	public String toString() {
		// implement toString so that it returns the current's board representation
		return board.toString();
	}
	
	/**
	 * Simple test program for GameTree class.
	 */
	public static void main(String args[]) {
		// 1. Instantiate a new (initial positions) board
		HexBoard b = new HexBoard(3,3);
		
		// 2. generate GameTree of all possible games
		GameTree t = new GameTree(b, HexBoard.WHITE);
		
		// 3. count the number of possible moves/positions
		int nodes = t.size();
		
		// print out the result
		System.out.println("Initial position:");
		System.out.println(t);
		System.out.println("Size of Tree: " + nodes + 
					((nodes == 252) ? " (Correct)" : " (incorrect)"));
	}
}