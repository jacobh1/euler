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

public class FourSquareSolver extends MagicSquareSolver {

	BlockingQueue<Board> allBoardsQ = new LinkedBlockingQueue<Board>(10_000_000);
	BlockingQueue<Board> sums0_1matchQ = new LinkedBlockingQueue<Board>(10_000);
	BlockingQueue<Board> sums1_2matchQ = new LinkedBlockingQueue<Board>(10_000);
	BlockingQueue<Board> sums2_3matchQ = new LinkedBlockingQueue<Board>(10_000);
	BlockingQueue<Board> sums3_4matchQ = new LinkedBlockingQueue<Board>(10_000);
	BlockingQueue<Board> sums4_5matchQ = new LinkedBlockingQueue<Board>(10_000);
	BlockingQueue<Board> sums5_6matchQ = new LinkedBlockingQueue<Board>(10_000);
	BlockingQueue<Board> sums6_7matchQ = new LinkedBlockingQueue<Board>(10_000);
	BlockingQueue<Board> sums7_8matchQ = new LinkedBlockingQueue<Board>(10_000);
	BlockingQueue<Board> sums8_9matchQ_final = new LinkedBlockingQueue<Board>(10_000);
	
//	private void init() {

	BoardSummer adder = new BoardSummer(4);
	AtomicBoolean bigRedSwitch = new AtomicBoolean(true);
	ExecutorService threadPool = Executors.newFixedThreadPool(10);
	
	QueueFilterThread step0 = new QueueFilterThread(0, 1, allBoardsQ, sums0_1matchQ, adder, bigRedSwitch);
//	QueueFilterThread step0a = new QueueFilterThread(0, 1, allBoardsQ, sums0_1matchQ, adder, bigRedSwitch);
	QueueFilterThread step1 = new QueueFilterThread(1, 2, sums0_1matchQ, sums1_2matchQ, adder, bigRedSwitch);
	QueueFilterThread step2 = new QueueFilterThread(2, 3, sums1_2matchQ, sums2_3matchQ, adder, bigRedSwitch);
	QueueFilterThread step3 = new QueueFilterThread(3, 4, sums2_3matchQ, sums3_4matchQ, adder, bigRedSwitch);
	QueueFilterThread step4 = new QueueFilterThread(4, 5, sums3_4matchQ, sums4_5matchQ, adder, bigRedSwitch);
	QueueFilterThread step5 = new QueueFilterThread(5, 6, sums4_5matchQ, sums5_6matchQ, adder, bigRedSwitch);
	QueueFilterThread step6 = new QueueFilterThread(6, 7, sums5_6matchQ, sums6_7matchQ, adder, bigRedSwitch);
	QueueFilterThread step7 = new QueueFilterThread(7, 8, sums6_7matchQ, sums7_8matchQ, adder, bigRedSwitch);
	QueueFilterThread step8 = new QueueFilterThread(8, 9, sums7_8matchQ, sums8_9matchQ_final, adder, bigRedSwitch);
		
//	}

	@Override
	public void start() {
//		for (QueueFilterThread qft : new QueueFilterThread[]{step0}) {
//			
//		}
		threadPool.execute(step0);
//		threadPool.execute(step0a);
		threadPool.execute(step1);
		threadPool.execute(step2);
		threadPool.execute(step3);
		threadPool.execute(step4);
		threadPool.execute(step5);
		threadPool.execute(step6);
		threadPool.execute(step7);
		threadPool.execute(step8);
	}

	@Override
	public void generateAllBoards() {
		threadPool.execute(new Runnable() {
			
			@Override
			public void run() {
				int boardCount=0;
				for (int i=0; i<10; i++) {
					for (int j=0; j<10; j++) {
						for (int k=0; k<10; k++) {
							for (int m=0; m<10; m++) {
								for (int n=0; n<10; n++) {
									for (int p=0; p<10; p++) {
										for (int q=0; q<10; q++) {
											for (int r=0; r<10; r++) {
												for (int s=0; s<10; s++) {
													for (int t=0; s<10; s++) {
														for (int u=0; u<10; u++) {
															for (int v=0; v<10; v++) {
																for (int w=0; w<10; w++) {
																	for (int x=0; x<10; x++) {
																		for (int y=0; y<10; y++) {
																			for (int z=0; z<10; z++) {
																				int[] b = new int[] {i,j,k,m,n,p,q,r,s,t,u,v,w,x,y,z};
																				Board bd = new Board(b);
																				try {
																					allBoardsQ.put(bd);
																					boardCount++;
																					if(boardCount % 10_000_000 == 0) {
																						System.out.println(boardCount + " boards generated");
																						System.out.println(bd);
																						System.out.printf("q1: %d q2:%d q3: %d q4: %d\n", allBoardsQ.size(),
																								sums0_1matchQ.size(), sums1_2matchQ.size(), 
																								sums2_3matchQ.size());

																					}
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
		int maxSum = 36;
		while(!stopWorking) {
			Board nextSolution = sums8_9matchQ_final.take();
			solutionCount++;
			System.out.println(nextSolution);
			stopWorking = (maxSum == adder.getSum(nextSolution, 0));
		}
		bigRedSwitch.set(false);
		System.out.println("total solutions found: "+ solutionCount);
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		FourSquareSolver solver = new FourSquareSolver();
		solver.solve();
		solver.threadPool.awaitTermination(1, TimeUnit.HOURS);
		
		// Make board generator thread, add all boards to initial queue
		
		// Make answer reporter thread, pulling results off final queue
		
		// Instantiate ExecutorService or other thread pool to start all threads running
		
	}
	

}
