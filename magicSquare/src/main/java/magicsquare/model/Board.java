package magicsquare.model;

import java.util.Arrays;

public class Board {

//	private final int size;
	private final int side;
	private final int[] board;
	
	public Board(int[] board) {
		this.side = (int) Math.sqrt(board.length);
		this.board = board;
	}
	
//	public Board(int size) {
//		this.size = size;
//		this.board = new int[size];
//	}
	
	public int get(int index) {
		return board[index];
	}
	
	public void set(int index, int value) {
		board[index] = value;
	}

	@Override
	public String toString() {
		StringBuffer retVal = new StringBuffer();
//		retVal.append(Arrays.toString(board));
//		retVal.append("\n");
//		return Arrays.toString(board);
		for(int i=0; i<side; i++) {
			retVal.append("[");
			for(int j=0; j<side; j++) {
				retVal.append("\t"+board[i*side+j]);
			}
			retVal.append("\t]\n");
		}
		
		return retVal.toString();
		
//		return "Board::board=\t[" + board[0] + ", "+board[1] + "]\n\t\t[" 
//	+ board[2] + ", "+board[3] + "]\n";
	}
	
	
}
