package board;

import static game.GameConstants.BOARD_CELL_SIZE;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import game.GameConstants;
import java.util.LinkedList;


/**
 * The Snake class represents the snake on the board with its functionality.
 * The idea of the methods handleEvents and moveBody of the Snake class
 * in https://github.com/ianparcs/snake-boi were used for the moveOnKeyPress
 * and moveBody methods. When using an external source we always make sure our
 * code is unique and written in our own personal way.
 */
public class Snake {

    transient LinkedList<RotatableSquare> snakeBody;
    transient RotatableSquare head;
    transient RotatableSquare tail;

    /**
     * Constructor for the snake on the board.
     */
    public Snake(Texture headtexture, Texture bodytexture, Texture tailtexture) {
        snakeBody = new LinkedList<>();
        head = new RotatableSquare(BOARD_CELL_SIZE * 3, 32, Direction.RIGHT, headtexture);
        RotatableSquare bodySquare = new RotatableSquare(BOARD_CELL_SIZE * 2, 32, Direction.RIGHT,
                bodytexture);
        tail = new RotatableSquare(BOARD_CELL_SIZE, 32, Direction.RIGHT,
                tailtexture);
        snakeBody.add(head);
        snakeBody.add(bodySquare);
        snakeBody.add(tail);
        this.addBody(bodytexture);
        this.addBody(bodytexture);
    }

    /**
     * Getter method for the linked list that stores the snake parts.
     *
     * @return the LinkedList of RotatableSquares that are the snake body parts.
     */
    public LinkedList<RotatableSquare> getSnakeBody() {
        return snakeBody;
    }

    /**
     * Getter method for the head of the snake.
     *
     * @return the RotatableSquare that represents the head of the snake.
     */
    public RotatableSquare getHead() {
        return head;
    }

    /**
     * Getter method for the tail of the snake.
     *
     * @return the RotatableSquare that represents the tail of the snake.
     */
    public RotatableSquare getTail() {
        return tail;
    }

    /**
     * Method used to render the snake body.
     */
    @SuppressWarnings("PMD")
    public void render(SpriteBatch batch) {
        for (Square body : snakeBody) {
            body.sprite.draw(batch);
        }
    }

    /**
     * This handles the keypress events when the user uses the arrow keys to play the game.
     */
    public boolean moveOnKeyPress() {
        Movement move = new Movement(this);
        return move.getNewMovement();
    }

    /**
     * Method used to mode the body of the snake.
     */
    public void moveBody() {
        int snakeSquare = snakeBody.size() - 1;
        while (snakeSquare > 0) {
            RotatableSquare body = snakeBody.get(snakeSquare);
            RotatableSquare nextBody = snakeBody.get(snakeSquare - 1);
            body.setPosition(nextBody.getXcoordinate(), nextBody.getYcoordinate());
            body.direction = nextBody.direction;
            body.setDirection(body.direction);
            snakeSquare--;
        }

        //update head seperately as there is no predecessor
        head.setDirection(head.direction);
        head.updateLocation(head.direction);
    }

    /**
     * Function that adds a body part to the snake.
     */
    public final void addBody(Texture bodytexture) {
        //get last snake body part
        RotatableSquare temp = snakeBody.get(snakeBody.size() - 2);
        //set new body part one cell to the left of last body part
        RotatableSquare newlast = new RotatableSquare(temp.getXcoordinate()
                - BOARD_CELL_SIZE, temp.getYcoordinate(), temp.direction, bodytexture);
        snakeBody.add(snakeBody.size() - 1, newlast);
        //update tail coordinates
        snakeBody.getLast().setXcoordinate(snakeBody.getLast().getXcoordinate() - BOARD_CELL_SIZE);
    }

    /**
     * Method that will check if the snake has eaten the food or not.
     *
     * @param food the Food object on the board.
     * @return returns true or false based on whether the food has been eaten or not.
     */
    public boolean ateFood(Food food) {
        if (head.getXcoordinate() == food.foodsquare.getXcoordinate()
                && head.getYcoordinate() == food.foodsquare.getYcoordinate()) {
            return true;
        }
        return false;
    }

    /**
     * Method that will check if the snake collided either with itself or with
     * a wall, the game will be set to the gameover screen if true.
     *
     * @param board the board to check collisions with.
     * @return returns true or false whether it collided with either the wall or itself.
     */
    @SuppressWarnings("PMD")
    public boolean hasCollided(Board board) {
        //wall collision
        if (this.head.getXcoordinate() < 0
                || this.head.getXcoordinate() >= GameConstants.WIDTH_OF_SCREEN
                || this.head.getYcoordinate() < 0
                || this.head.getYcoordinate() >= GameConstants.HEIGHT_OF_SCREEN) {
            return true;
        }


        //snake collision with itself
        for (RotatableSquare body : snakeBody) {
            // skip head itself
            if (body.equals(head)) {
                continue;
            }
            if (body.getXcoordinate() == head.getXcoordinate()
                    && body.getYcoordinate() == head.getYcoordinate()) {

                return true;
            }
        }
        for (int i = 0; i < board.getWallMap().size(); i++) {
            if (head.getXcoordinate() == board.getWallMap().get(i).getXcoordinate()
                    && head.getYcoordinate() == board.getWallMap().get(i).getYcoordinate()) {
                return true;
            }
        }

        return false;
    }

}
