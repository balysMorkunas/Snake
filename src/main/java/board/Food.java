package board;

import static game.GameConstants.BOARD_CELL_SIZE;
import static game.GameConstants.HEIGHT_OF_SCREEN;
import static game.GameConstants.WIDTH_OF_SCREEN;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Random;

public class Food {

    public transient Square foodsquare;

    /**
     * Given two coordinates this will create the food object at that location.
     *
     * @param snake the snake on the board
     */
    public Food(Snake snake, Texture foodtexture, Board board) {
        // set start location
        foodsquare = new Square(0,0, new Sprite(foodtexture));
        //set the food to a non-hardcoded position
        this.updateFoodLocation(snake, new Random(), board);
    }

    /**
     * This method will render the board given the SpriteBatch for the board.
     *
     * @param batch is the required sprite batch
     */
    @SuppressWarnings("PMD")
    public void render(SpriteBatch batch) {
        batch.draw(foodsquare.getSprite(), foodsquare.getXcoordinate(),
                foodsquare.getYcoordinate());
    }

    /**
     * Method that sets a new location of the food.
     * It also checks that the food is not spawned on
     * a snake body part
     */
    @SuppressWarnings("PMD")
    public void updateFoodLocation(Snake snake, Random random, Board board) {
        int newX = random
                .nextInt(WIDTH_OF_SCREEN / BOARD_CELL_SIZE - 1) * BOARD_CELL_SIZE;
        int newY = random
                .nextInt(HEIGHT_OF_SCREEN / BOARD_CELL_SIZE - 1) * BOARD_CELL_SIZE;

        //check whether this square is occupied by the snake's body

        for (int index = 0; index < 1; index++) {

            //check all the parts of the snake
            for (RotatableSquare square : snake.snakeBody) {
                RotatableSquare s = square;
                if (newX == s.getXcoordinate()
                        && newY == s.getYcoordinate()) {
                    System.out.println("snake body is here!");
                    newX = random.nextInt(WIDTH_OF_SCREEN / BOARD_CELL_SIZE - 1)
                            * BOARD_CELL_SIZE;
                    newY = random.nextInt(HEIGHT_OF_SCREEN / BOARD_CELL_SIZE - 1)
                            * BOARD_CELL_SIZE;
                    index = -1;

                }
            }
        }

        //check whether this square is occupied by a wall

        for (int index = 0; index < 1; index++) {

            //check all the parts of the snake
            for (Square square : board.getWallMap()) {
                Square s = square;
                if (newX == s.getXcoordinate()
                        && newY == s.getYcoordinate()) {
                    System.out.println("wall is here!");
                    newX = random.nextInt(WIDTH_OF_SCREEN / BOARD_CELL_SIZE - 1)
                            * BOARD_CELL_SIZE;
                    newY = random.nextInt(HEIGHT_OF_SCREEN / BOARD_CELL_SIZE - 1)
                            * BOARD_CELL_SIZE;
                    index = -1;

                }
            }
        }

        this.foodsquare.setXcoordinate(newX);
        this.foodsquare.setYcoordinate(newY);
    }
}

