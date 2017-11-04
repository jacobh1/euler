package magicsquare.runner;

public abstract class MagicSquareSolver {

	public abstract void start();

	public abstract void generateAllBoards();

	public abstract void showAnswers() throws InterruptedException;

	public void solve() throws InterruptedException {
		start();
		generateAllBoards();
		showAnswers();
	}
	


}