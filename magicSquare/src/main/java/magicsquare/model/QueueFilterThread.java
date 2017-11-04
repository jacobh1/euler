package magicsquare.model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class QueueFilterThread implements Runnable {

	private final int sumIndex0;
	private final int sumIndex1;
	
	private final BlockingQueue<Board> inputQueue;
	private final BlockingQueue<Board> outputQueue;
	
	private final BoardSummer addingMachine;
	private final AtomicBoolean bigRedSwitch;
	
	public QueueFilterThread(int sumIndex0, int sumIndex1,
			BlockingQueue<Board> inputQueue, BlockingQueue<Board> outputQueue, BoardSummer addingMachine, AtomicBoolean bigRedSwitch) {
		super();
		this.sumIndex0 = sumIndex0;
		this.sumIndex1 = sumIndex1;
		this.inputQueue = inputQueue;
		this.outputQueue = outputQueue;
		this.addingMachine = addingMachine;
		this.bigRedSwitch = bigRedSwitch;
	}

	public void run() {
		while (bigRedSwitch.get()) {
			try {
				Board thisBoard = inputQueue.take();
				if (sumsMatch(thisBoard)) {
					outputQueue.put(thisBoard);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean sumsMatch(Board thisBoard) {
		return addingMachine.getSum(thisBoard, sumIndex0) == addingMachine.getSum(thisBoard, sumIndex1);
	}
	
}
