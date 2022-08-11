package hexAPawn;

import java.util.Random;
import java.util.Scanner;

// Any useful imports to pick random choice

public class CompPlayer implements Player {

	// Any instance variables
	private char color;


	public CompPlayer(char color) {
		this.color = color;
	}

	/**
	 * makes moves from opponent and player until a winner
	 * 
	 * @param node
	 * @param opponent
	 * @return Player who won the game
	 */
	public Player play(GameTree node, Player opponent) {    
		// trim tree thing
		
        // Print current board
        System.out.println(node.toString());

        // if the state is a winning state for the opponent or there aren't any moves:
        if (node.board.win(HexBoard.opponent(color)) || node.numChildren() <= 1) {
        	// find parent
        	GameTree parentG = node.getParent();
        	
			// if parent is not null
        	if (parentG != null) {
				// find grandparent
        		GameTree grandparent = parentG.getParent();
				// remove grandparent's child which corresponds to parent
        		grandparent.children.remove(parentG);
				// print what you removed
        		System.out.println(parentG);
        	} 
        	else {
				// print that the parent is null and that was nothing there for you to remove
        		System.out.println("parent is null");
           	}
        	// return the opponent
        	return opponent;
        } 
        else {
            // Pick a random GameTree child
        	Random rand = new Random();
        	int randChild = rand.nextInt(node.numChildren()); 
        	
        	// return opponent.play on the chosen random child with "this" player as the opponent
           	return opponent.play(node.children.get(randChild), this); 
        }


	}

	public String toString() {
		return color == HexBoard.BLACK ? "black" : "white";
	}

	public static void main(String s[]) {
		Scanner r = new Scanner(System.in);
		GameTree gt = new GameTree(new HexBoard(), HexBoard.WHITE);
		String ans = "yes";
		while (ans.equals( "yes")) {
			Player h1 = new CompPlayer(HexBoard.BLACK);
			Player h2 = new HumanPlayer(HexBoard.WHITE);
			System.out.println(h2 + " plays first.");
			System.out.println(h2.play(gt, h1) + " wins!");
			System.out.println("The tree now has size " + gt.size());
			System.out.println("Play again?  answer yes or no");
			ans = r.next().toLowerCase();
			System.out.println("Your answer was " + ans);
		}
		System.out.println("Thanks for playing!");
		r.close();
	}
}
