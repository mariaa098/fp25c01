public class AIPlayerTableLookup extends AIPlayer {

    private final int[][] preferredMoves = {
            {1, 1}, {0, 0}, {0, 2}, {2, 0}, {2, 2},
            {0, 1}, {1, 0}, {1, 2}, {2, 1}
    };

    public AIPlayerTableLookup(Board board) {
        super(board);
    }


    @Override
    public int[] move() {
        for (int[] move : preferredMoves) {
            if (cells[move[0]][move[1]].content == Seed.NO_SEED) {
                return move;
            }
        }
        throw new IllegalStateException("No moves left!");
    }
}