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
