package board;

import static game.GameConstants.BOARD_CELL_SIZE;
import static game.GameConstants.HEIGHT_OF_SCREEN;
import static game.GameConstants.WIDTH_OF_SCREEN;
import static game.GameConstants.boardBrick;
import static game.GameConstants.boardStone;
import static game.GameConstants.boardWater;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;


public abstract class Board {

    transient Square[][] grid;
    transient LinkedList<Square> wallMap = new LinkedList<>();

    /**
     * This method will render the board given the SpriteBatch for the board.
     *
     * @param batch is the required sprite batch
     */
    @SuppressWarnings("PMD")
    public void render(SpriteBatch batch) {
        for (Square[] squares : grid) {
            for (Square square : squares) {
                batch.draw(square.getSprite(), square.getXcoordinate(), square.getYcoordinate());
            }
        }
    }

    /**
     * This method creates a board for the game given.
     * @param texture this is the texture for the board tiles.
     * @param wallMapPath this is the file used to parse the walls on the map.
     * @param calledByTest this is a boolean value used to make this method testable.
     */
    public void createBoard(Texture texture, String wallMapPath, boolean calledByTest) {
        grid = new Square[HEIGHT_OF_SCREEN / BOARD_CELL_SIZE][WIDTH_OF_SCREEN / BOARD_CELL_SIZE];
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                Square square = new Square(x * BOARD_CELL_SIZE, y * BOARD_CELL_SIZE,
                        new Sprite(texture));
                grid[x][y] = square;
            }
        }
        drawWalls(this.grid, new File(wallMapPath), calledByTest);
    }

    /**
     * Getter method for the wallMap attribute.
     */
    public LinkedList<Square> getWallMap() {
        return wallMap;
    }

    /**
     * Method used for drawing walls on the board.
     *
     * @param grid is the grid of the board
     * @param file is the file with the wall map
     */
    @SuppressWarnings("PMD")
    public void drawWalls(Square[][] grid, File file, boolean calledByTest) {
        try {
            Scanner scanner = new Scanner(file);
            for (int x = 0; x < grid.length; x++) {
                for (int y = 0; y < grid[x].length; y++) {
                    String texturePath = null;

                    if (scanner.hasNext()) {
                        String next = scanner.next();
                        if (next.equals("S")) {
                            texturePath = boardStone;
                        } else if (next.equals("W")) {
                            texturePath = boardWater;
                        } else if (next.equals("B")) {
                            texturePath = boardBrick;
                        }
                    }
                    if (texturePath != null) {
                        Square square = null;
                        if (!calledByTest) {
                            square = new Square(x * BOARD_CELL_SIZE, y * BOARD_CELL_SIZE,
                                    new Sprite(new Texture(texturePath)));
                        }
                        grid[x][y] = square;
                        wallMap.add(new Square(x * BOARD_CELL_SIZE, y * BOARD_CELL_SIZE, null));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
