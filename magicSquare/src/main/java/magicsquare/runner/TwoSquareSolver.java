package magicsquare.runner;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import magicsquare.model.Board;
import magicsquare.model.BoardSummer;
import magicsquare.model.QueueFilterThread;

public class TwoSquareSolver {
	
	BlockingQueue<Board> allBoardsQ = new LinkedBlockingQueue<Board>(10_000);
	BlockingQueue<Board> sums0_1matchQ = new LinkedBlockingQueue<Board>(1_000);
	BlockingQueue<Board> sums1_2matchQ = new LinkedBlockingQueue<Board>(1_000);
	BlockingQueue<Board> sums2_3matchQ = new LinkedBlockingQueue<Board>(1_000);
	BlockingQueue<Board> sums3_4matchQ = new LinkedBlockingQueue<Board>(1_000);
	BlockingQueue<Board> sums4_5matchQ = new LinkedBlockingQueue<Board>(1_000);
	
//	private void init() {

	BoardSummer adder = new BoardSummer(2);
//	QueueFilterThread step0 = new QueueFilterThread(0, 1, allBoardsQ, sums0_1matchQ, adder);
//	QueueFilterThread step1 = new QueueFilterThread(1, 2, sums0_1matchQ, sums1_2matchQ, adder);
//	QueueFilterThread step2 = new QueueFilterThread(2, 3, sums1_2matchQ, sums2_3matchQ, adder);
//	QueueFilterThread step3 = new QueueFilterThread(3, 4, sums2_3matchQ, sums3_4matchQ, adder);
//	QueueFilterThread step4 = new QueueFilterThread(4, 5, sums3_4matchQ, sums4_5matchQ, adder);
	
//	}
	
	ExecutorService threadPool = Executors.newFixedThreadPool(10);
	
	private void start() {
		
//		threadPool.execute(step0);
//		threadPool.execute(step1);
//		threadPool.execute(step2);
//		threadPool.execute(step3);
//		threadPool.execute(step4);
		
	}
	
	private void generateAllBoards() {
		threadPool.execute(new Runnable() {
			
			@Override
			public void run() {
				for (int i=0; i<10; i++) {
					for (int j=0; j<10; j++) {
						for (int k=0; k<10; k++) {
							for (int l=0; l<10; l++) {
								int[] b = new int[] {i,j,k,l};
								Board bd = new Board(b);
								try {
//									System.out.print(bd);
									allBoardsQ.put(bd);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
		});

	}
		
	private void showAnswers() throws InterruptedException {
		while(true) {
			System.out.println(sums4_5matchQ.take());
		}
	}
	public static void main(String[] args) throws InterruptedException {
		
//		TwoSquareSolver solver = new TwoSquareSolver();
//		solver.start();
//		solver.generateAllBoards();
//		solver.showAnswers();
//		solver.threadPool.awaitTermination(1, TimeUnit.HOURS);
		
		for (int i=0; i<10; i=i+1) {
			System.out.println("i: " + i);
			for (int j=0; j<10; j++) {
				System.out.println("\ti: " + i + " j: " + j);
				for (int k=0; k<10; k++) {
					System.out.println("\t\ti: " + i + " j: " + j + " k: " + k);
					for (int m=0; m<10; m++) {
						System.out.println("\t\t\ti: " + i + " j: " + j + " k: " + k + " m: "+m);
					}
					
					
				}	
			}
		}

	
		// Make board generator thread, add all boards to initial queue
		
		// Make answer reporter thread, pulling results off final queue
		
		// Instantiate ExecutorService or other thread pool to start all threads running
		
	}

}
