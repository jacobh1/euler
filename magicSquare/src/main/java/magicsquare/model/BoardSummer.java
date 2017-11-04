package magicsquare.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class BoardSummer {

	private final int side;
	
//	private final int[] board; // flattened to 1d for simpler indexing

	private final Map<Integer, int[]> indexMap = new HashMap<Integer, int[]>();
	
	public BoardSummer(int side) {
		this.side = side;
//		this.board = new int[size];
		this.init();
	}
	
	public int totalSums() {
		// number of sums to verify:
		// 1 per row, 1 per col, plus 2 diagonals 
		return 2*side + 2;
	}
	
	public int getSum(Board board, int index) {
		int result=0;
		for(int i : indexMap.get(index)) {
			result+= board.get(i);
		}
		return result;
	}
	
	public void init() {
		buildIndexMap();
	}
	
	private void buildIndexMap() {
		addRowIndices();
		addColIndices();
		addDiagIndices();
	}

	private void addRowIndices() {
//		eg. for side == 4: 
//		0 => {0, 1, 2,  3}
//		1 => {4, 5, 6,  7}
//		2 => {8, 9, 10, 11}
//		3 => {12, 13, 14, 15}
		
		for(int i=0; i<side; i++) {
			int[] list = new int[side];
			for(int j=0; j<side; j++) {
				list[j] = i*side+j;
			}
			indexMap.put(i, list);
		}
	}
	
	private void addColIndices() {
//		eg. for side == 4: 
//		4 => {0, 4, 8, 12}
//		5 => {1, 5, 9, 13}
//		6 => {2, 6, 10, 14}
//		7 => {3, 7, 11, 15
		
		for(int i=0; i<side; i++) {
			int[] list = new int[side];
			for(int j=0; j<side; j++) {
				list[j] = j*side+i;
			}
			indexMap.put(side+i, list);
		}
	}

	private void addDiagIndices() {
//		eg. for side == 4: 
//		8 => {0, 5, 10, 15}
//		9 => {3, 6, 9, 12}
		int[] diag1 = new int[side];
		int[] diag2 = new int[side];
		
		for (int i=0; i<side; i++) {
			diag1[i] = i*(side+1);
			diag2[i] = (i+1) * (side-1);
		}
		indexMap.put(side*2, diag1);
		indexMap.put(side*2+1, diag2);
	}
	
	public void printIndexMap() {
		for(Entry<Integer, int[]> kv : indexMap.entrySet()) {
			System.out.printf("%s\t%s\n", kv.getKey(), Arrays.toString(kv.getValue()));
		}
	}
	
	public void showSumPairs() {
		for (int i=0; i<totalSums()-1; i++) {
			System.out.printf("%d\t%d\n",i, i+1);
		}
	}

	public static void main(String[] args) {
		BoardSummer myBoard = new BoardSummer(3);
		myBoard.init();
		
//		System.out.println(myBoard.indexMap);
		myBoard.printIndexMap();
		myBoard.showSumPairs();
	}
	
}
