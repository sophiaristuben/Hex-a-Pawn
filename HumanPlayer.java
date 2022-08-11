package hexAPawn;

import java.util.Scanner;

public class HumanPlayer implements Player {

	// Any instance variables
	private char color;


	public HumanPlayer(char color) {
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
        
        // Print current board
        System.out.println(node.toString());

        //if the state is a winning state for the opponent or there aren't any moves:
        if (node.board.win(HexBoard.opponent(color)) || node.numChildren() <= 1) {
            //    return the opponent
        	return opponent;
        }
        // else:   
        else {
        	
            //Print all available moves in the form of "1: Move from [1,1] to [2,1]." etc.
        	for (int i=0; i <node.numChildren(); i++) {
        		System.out.println(i + ": "+ (node.children.get(i).m.toString()));        
        		}
        	
        	try {
                // Ask the user for which move they have chosen
               	Scanner input = new Scanner(System.in);
               	System.out.println("Choose move: ");
               	int chosenMove = Integer.parseInt(input.nextLine()); 
               	
                // Find the GameTree child that corresponds to the chosen move (be careful if you have printed the moves starting at 1)
                // return opponent.play on the chosen child with "this" player as the opponent
               	return opponent.play(node.children.get(chosenMove), this); 
        	}
        	catch (Exception e) {
           		//if (chosenMove < 0 || chosenMove > node.numChildren()) {
    	       		System.out.println("Illegal move, select another.");
    	       		Scanner inputReDo = new Scanner(System.in);
    	       		System.out.println("Choose move: ");
    	       		int chosenMoveTwo = Integer.parseInt(inputReDo.nextLine());
    	       			       		
    	       		return opponent.play(node.children.get(chosenMoveTwo), this);               
               	}

        }
        
	}

	public String toString() {
		return color == HexBoard.BLACK ? "black" : "white";
	}

	public static void main(String s[]) {
		HumanPlayer h1 = new HumanPlayer(HexBoard.WHITE);
		HumanPlayer h2 = new HumanPlayer(HexBoard.BLACK);
		System.out.println(
			h1.play(new GameTree(new HexBoard(), HexBoard.WHITE), h2)+" wins");
	}
}
