public abstract class AIPlayer {
    protected int ROWS = Board.ROWS;
    protected int COLS = Board.COLS;

    protected Cell[][] cells;
    protected Seed mySeed;
    protected Seed oppSeed;

    public AIPlayer(Board board) {
        this.cells = board.cells;
    }

    public void setSeed(Seed seed) {
        this.mySeed = seed;
        this.oppSeed = (seed == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
    }

    public abstract int[] move();
}
