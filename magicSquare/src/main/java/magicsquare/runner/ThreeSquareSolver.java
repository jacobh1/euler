package magicsquare.runner;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import magicsquare.model.Board;
import magicsquare.model.BoardSummer;
import magicsquare.model.QueueFilterThread;

public class ThreeSquareSolver extends MagicSquareSolver {

	BlockingQueue<Board> allBoardsQ = new LinkedBlockingQueue<Board>(1_000_000);
	BlockingQueue<Board> sums0_1matchQ = new LinkedBlockingQueue<Board>(10_000);
	BlockingQueue<Board> sums1_2matchQ = new LinkedBlockingQueue<Board>(10_000);
	BlockingQueue<Board> sums2_3matchQ = new LinkedBlockingQueue<Board>(10_000);
	BlockingQueue<Board> sums3_4matchQ = new LinkedBlockingQueue<Board>(10_000);
	BlockingQueue<Board> sums4_5matchQ = new LinkedBlockingQueue<Board>(10_000);
	BlockingQueue<Board> sums5_6matchQ = new LinkedBlockingQueue<Board>(10_000);
	BlockingQueue<Board> sums6_7matchQ = new LinkedBlockingQueue<Board>(10_000);
	
//	private void init() {

	BoardSummer adder = new BoardSummer(3);
	AtomicBoolean bigRedSwitch = new AtomicBoolean(true);
	QueueFilterThread step0 = new QueueFilterThread(0, 1, allBoardsQ, sums0_1matchQ, adder, bigRedSwitch);
	QueueFilterThread step1 = new QueueFilterThread(1, 2, sums0_1matchQ, sums1_2matchQ, adder, bigRedSwitch);
	QueueFilterThread step2 = new QueueFilterThread(2, 3, sums1_2matchQ, sums2_3matchQ, adder, bigRedSwitch);
	QueueFilterThread step3 = new QueueFilterThread(3, 4, sums2_3matchQ, sums3_4matchQ, adder, bigRedSwitch);
	QueueFilterThread step4 = new QueueFilterThread(4, 5, sums3_4matchQ, sums4_5matchQ, adder, bigRedSwitch);
	QueueFilterThread step5 = new QueueFilterThread(5, 6, sums4_5matchQ, sums5_6matchQ, adder, bigRedSwitch);
	QueueFilterThread step6 = new QueueFilterThread(6, 7, sums5_6matchQ, sums6_7matchQ, adder, bigRedSwitch);
		
//	}
	
	ExecutorService threadPool = Executors.newFixedThreadPool(10);
	
	/* (non-Javadoc)
	 * @see magicsquare.runner.MagicSquareSolver#start()
	 */
//	@Override
	public void start() {
		
		threadPool.execute(step0);
		threadPool.execute(step1);
		threadPool.execute(step2);
		threadPool.execute(step3);
		threadPool.execute(step4);
		threadPool.execute(step5);
		threadPool.execute(step6);
		
	}
	
	@Override
	public void generateAllBoards() {
		threadPool.execute(new Runnable() {
			
			@Override
			public void run() {
				for (int i=0; i<10; i++) {
					for (int j=0; j<10; j++) {
						for (int k=0; k<10; k++) {
							for (int m=0; m<10; m++) {
								for (int n=0; n<10; n++) {
									for (int p=0; p<10; p++) {
										for (int q=0; q<10; q++) {
											for (int r=0; r<10; r++) {
												for (int s=0; s<10; s++) {
													int[] b = new int[] {i,j,k,m,n,p,q,r,s};
													Board bd = new Board(b);
													try {
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
							}
						}
					}
				}
			}
		});

	}
	
	@Override
	public void showAnswers() throws InterruptedException {
		boolean stopWorking = false;
		int solutionCount = 0;
		int maxSum = 27;
		while(!stopWorking) {
			Board nextSolution = sums6_7matchQ.take();
			solutionCount++;
			System.out.println(nextSolution);
			stopWorking = (maxSum == adder.getSum(nextSolution, 0));
		}
		bigRedSwitch.set(false);
		System.out.println("total solutions found: "+ solutionCount);
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		ThreeSquareSolver solver = new ThreeSquareSolver();
		solver.solve();
		solver.threadPool.awaitTermination(1, TimeUnit.HOURS);
		
		// Make board generator thread, add all boards to initial queue
		
		// Make answer reporter thread, pulling results off final queue
		
		// Instantiate ExecutorService or other thread pool to start all threads running
		
	}

}
