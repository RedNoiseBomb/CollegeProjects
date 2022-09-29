
public class Connect4Grid2DArray implements Connect4Grid {
	int[][] playGrid;
	int playerInput;
	
	Connect4Grid2DArray() {
		playGrid = new int[6][7];
	}

	@Override
	public void emptyGrid() {
		for (int i = 0; i < 6; i++) {
			for (int k = 0; k < 7; k++) {
				playGrid[i][k] = 0;
			}
		}
		
	}

	public String toString() {
		for (int i = 0; i < 6; i++) {
			for (int k = 0; k < 7; k++) {
				System.out.print(playGrid[i][k] + " ");
			}
			System.out.print("\n");
		}
		return null;
		
	}
	@Override
	public boolean isValidColumn(int column) {
		if (column - 1 >= 0 && column - 1 <= 6) {
			return true;
		}
		else return false;
	}

	@Override
	public boolean isColumnFull(int column) {
		int i = 0;
		for (; i < 6; i++) {
			if (playGrid[i][column-1] == 0) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void dropPiece(ConnectPlayer player, int column) {
		for (int i = 5; i >= 0; i--) {
			if (player.grid.playGrid[i][column-1] == 0) {
				if(player.playerNum == 1) {
					playerInput = 1;
					player.grid.playGrid[i][column-1] = playerInput;
					break;
				}
				if(player.playerNum == 2) {
					playerInput = 2;
					player.grid.playGrid[i][column-1] = playerInput;
					break;
				}
			}
		}
		
	}

	@Override
	/* This function (lines 102 - 145) was taken from this website:
	 * https://gist.github.com/jonathan-irvin/97537512eb65a8bbcb9a
	 * I couldn't come up with a suitable solution, so I found this function and it perfectly fits.
	 */
	public boolean didLastPieceConnect4() {
		for(int row = 0; row< playGrid.length; row++){
			for (int col = 0;col < playGrid[0].length - 3;col++){
				if (playGrid[row][col] == playerInput   && 
					playGrid[row][col+1] == playerInput &&
					playGrid[row][col+2] == playerInput &&
					playGrid[row][col+3] == playerInput){
					return true;
				}
			}			
		}
		//check for 4 up and down
		for(int row = 0; row < playGrid.length - 3; row++){
			for(int col = 0; col < playGrid[0].length; col++){
				if (playGrid[row][col] == playerInput   && 
					playGrid[row+1][col] == playerInput &&
					playGrid[row+2][col] == playerInput &&
					playGrid[row+3][col] == playerInput){
					return true;
				}
			}
		}
		//check upward diagonal
		for(int row = 3; row < playGrid.length; row++){
			for(int col = 0; col < playGrid[0].length - 3; col++){
				if (playGrid[row][col] == playerInput   && 
					playGrid[row-1][col+1] == playerInput &&
					playGrid[row-2][col+2] == playerInput &&
					playGrid[row-3][col+3] == playerInput){
					return true;
				}
			}
		}
		//check downward diagonal
		for(int row = 0; row < playGrid.length - 3; row++){
			for(int col = 0; col < playGrid[0].length - 3; col++){
				if (playGrid[row][col] == playerInput   && 
					playGrid[row+1][col+1] == playerInput &&
					playGrid[row+2][col+2] == playerInput &&
					playGrid[row+3][col+3] == playerInput){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean isGridFull() {
		for (int i = 0; i < 6; i++) {
			for (int k = 0; k < 7; k++) {
				if (playGrid[i][k] == 0) {
					return false;
				}
			}
		}
		return true;
	}

}
