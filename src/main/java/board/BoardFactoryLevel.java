package board;

public class BoardFactoryLevel extends BoardFactory {

    @Override
    @SuppressWarnings("PMD")
    public Board createBoard(int level) {

        Board board = null;
        if (level == 1) {
            board = new BoardLevel1();
        } else if (level == 2) {
            board = new BoardLevel2();
        } else if (level == 3) {
            board = new BoardLevel3();
        }

        return board;
    }
}
