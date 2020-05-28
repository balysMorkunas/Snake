package board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Movement {
    transient Snake snake;

    public Movement(Snake snake) {
        this.snake = snake;
    }

    /**
     * Method used to set the direction of the snake according to the input of the user.
     * @return returns true or false whether a key had been pressed or not.
     */
    public boolean getNewMovement() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)
                && snake.head.direction != Direction.RIGHT) {
            snake.head.direction = Direction.LEFT;
            return true;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)
                && snake.head.direction != Direction.LEFT) {
            snake.head.direction = Direction.RIGHT;
            return true;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)
                && snake.head.direction != Direction.DOWN) {
            snake.head.direction = Direction.UP;
            return true;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)
                && snake.head.direction != Direction.UP) {
            snake.head.direction = Direction.DOWN;
            return true;
        }
        return false;
    }
}
