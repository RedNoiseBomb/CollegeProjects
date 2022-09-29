
public class C4RandomAIPlayer extends ConnectPlayer {
	C4RandomAIPlayer (Connect4Grid2DArray grid, int playerNum) {
		this.grid = grid;
		this.playerNum = playerNum;
	}
	@Override
	public int columnChoose() {
		int aiInput = 1 + (int)(Math.random() * ((7 - 1) + 1));
		/*
		This particular line was taken from StackOverFlow to make the AI choose random number within set limits.
		https://stackoverflow.com/questions/363681/how-do-i-generate-random-integers-within-a-specific-range-in-java
		*/
		while (grid.isValidColumn(aiInput) != true || grid.isColumnFull(aiInput) == true) {
			aiInput = 1 + (int)(Math.random() * ((7 - 1) + 1));
		}
		return aiInput;
	}

}
