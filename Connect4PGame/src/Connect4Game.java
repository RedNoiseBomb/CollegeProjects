/* SELF ASSESSMENT
Connect4Game class (35 marks): 35
My class creates references to the Connect 4 Grid and two Connect 4 Players. It asks the user whether he/she would like to play/quit inside a loop. If the user decides to play then: 1. Connect4Grid2DArray is created using the Connect4Grid interface, 2. the two players are initialised - must specify the type to be ConnectPlayer, and 3. the game starts. In the game, I ask the user where he/she would like to drop the piece. I perform checks by calling methods in the Connect4Grid interface. Finally a check is performed to determine a win. 
Comment: All of the requirements are met.

Connect4Grid interface (10 marks): 10
I define all 7 methods within this interface.
Comment: They are all defined and used later

Connect4Grid2DArray class (25 marks): 25
My class implements the Connect4Grid interface. It creates a grid using a 2D array Implementation of the method to check whether the column to drop the piece is valid. It provides as implementation of the method to check whether the column to drop the piece is full. It provides as implementation of the method to drop the piece.  It provides as implementation of the method to check whether there is a win.
Comment: All of the methods from the interface are used and they perform the tasks as they are supposed to.
ConnectPlayer abstract class (10 marks): 10
My class provides at lest one non-abstract method and at least one abstract method. 
Comment: It does.
C4HumanPlayer class (10 marks): 10
My class extends the ConnectPlayer class and overrides the abstract method(s). It provides the Human player functionality.
Comment: It provides all the necessary functionality with input validity checks.
C4RandomAIPlayer class (10 marks): 10
My class extends the ConnectPlayer class and overrides the abstract method(s). It provides AI player functionality. 
Comment: AI player is given all the functionality it needs using ConnectPlayer abstract class.
Total Marks out of 100: 100
*/

import java.util.Scanner;

public class Connect4Game {

	public static void main(String[] args) {
		boolean quit = false;
		String gameStart = "";
		while(!quit) {
			System.out.print("The game is about to start. If you wish to quit, type /quit/. Otherwise, type anything: ");
			Scanner input = new Scanner(System.in);
			gameStart = input.next();
			if (gameStart.equalsIgnoreCase("quit")) {
				quit = true;
				break;
			}
			Connect4Grid2DArray grid = new Connect4Grid2DArray();
			System.out.println("Choose your opponent:\nAI(type '1')\nPlayer 2(type '2')\nSpectate AI vs. AI(type '3')");
			int opponentChoose = input.nextInt();
			ConnectPlayer player1 = null;
			ConnectPlayer player2 = null;
			grid.toString();
			if (opponentChoose == 1) {
				player1 = new C4HumanPlayer(grid, 1);
				player2 = new C4RandomAIPlayer(grid, 2);
			}
			if (opponentChoose == 2) {
				player1 = new C4HumanPlayer(grid, 1);
				player2 = new C4HumanPlayer(grid, 2);
			}
			if (opponentChoose == 3) {
				player1 = new C4RandomAIPlayer(grid, 1);
				player2 = new C4RandomAIPlayer(grid, 2);
			}
			int init = 1;
			while(init > 0) {
				grid.dropPiece(player1, player1.columnChoose());
				player1.turnChange();
				grid.toString();
				if (grid.didLastPieceConnect4()) {
					System.out.println("Player 1 won!");
					break;
				}
				if (grid.isGridFull()) {
					System.out.println("It's a tie!");
					break;
				}
				grid.dropPiece(player2, player2.columnChoose());
				player2.turnChange();
				grid.toString();
				if (grid.didLastPieceConnect4()) {
					System.out.println("Player 2 won!");
					break;
				}
				if (grid.isGridFull()) {
					System.out.println("It's a tie!");
					break;
				}
			}
			
		}
	}

}
