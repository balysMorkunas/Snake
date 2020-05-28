package board;

public abstract class BoardFactory {

    /**
     * Given a level it this factory method will return a new board for
     * that level.
     */
    public abstract Board createBoard(int level);


}
