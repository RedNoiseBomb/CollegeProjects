import java.util.Scanner;

public class C4HumanPlayer extends ConnectPlayer {
	C4HumanPlayer(Connect4Grid2DArray grid, int playerNum) {
		this.grid = grid;
		this.playerNum = playerNum;
	}
	@Override
	public int columnChoose() {
		Scanner input2 = new Scanner(System.in);
		System.out.print("Please, enter a number of the column: ");
		int p2Input = input2.nextInt();
		while (grid.isValidColumn(p2Input) != true) {
			System.out.print("Please, enter a VALID number of the column: ");
			p2Input = input2.nextInt();
		}
		while (grid.isColumnFull(p2Input) == true) {
			System.out.print("Please, enter another number of the column as this one is full: ");
			p2Input = input2.nextInt();
		}
		return p2Input;
	}
	
} 
