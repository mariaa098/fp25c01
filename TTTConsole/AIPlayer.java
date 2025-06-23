public abstract class AIPlayer {
    protected int ROWS = Board.ROWS;
    protected int COLS = Board.COLS;

    protected Cell[][] cells; // papan permainan
    protected Seed mySeed;    // simbol komputer (X atau O)
    protected Seed oppSeed;   // simbol lawan

    public AIPlayer(Board board) {
        this.cells = board.cells;
    }

    public void setSeed(Seed seed) {
        this.mySeed = seed;
        this.oppSeed = (seed == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
    }

    public abstract int[] move();
}
