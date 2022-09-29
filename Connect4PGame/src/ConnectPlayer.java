
public abstract class ConnectPlayer {
	int playerNum;
	Connect4Grid2DArray grid;
	public abstract int columnChoose();
	public void turnChange() {
		System.out.println("\nOpponent's turn now!\n");
	}
}


