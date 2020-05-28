package board;

import static game.GameConstants.BOARD_CELL_SIZE;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class RotatableSquare extends Square {

    public transient Direction direction;

    /**
     * Constructor for a rotatable square.
     *
     * @param x         the x coordinate of the square
     * @param y         the y coordinate of the square
     * @param direction the direction this square is facing
     * @param texture the texture to be used for sprite creation
     */
    public RotatableSquare(int x, int y, Direction direction, Texture texture) {
        super(x, y, new Sprite(texture));
        this.direction = direction;
    }

    /**
     * This method is used to rotate a sprite.
     *
     * @param direction is the direction of the current GameObject
     */
    public void setDirection(Direction direction) {
        switch (direction) {
            case LEFT:
                sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
                sprite.setRotation(180.f);
                break;

            case RIGHT:
                sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
                sprite.setRotation(0.0f);
                break;

            case UP:
                sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
                sprite.setRotation(90f);
                break;

            case DOWN:
                sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
                sprite.setRotation(270.0f);
                break;

            default:
                break;
        }
    }

    /**
     * Method used to update the location of a square if it needs to be computed based on
     * the direction it is facing.
     *
     * @param direction the direction it is facing
     */
    public void updateLocation(Direction direction) {
        switch (direction) {
            case LEFT:
                setPosition(xcoordinate - BOARD_CELL_SIZE, ycoordinate);
                break;

            case RIGHT:
                setPosition(xcoordinate + BOARD_CELL_SIZE, ycoordinate);
                break;

            case UP:
                setPosition(xcoordinate, ycoordinate + BOARD_CELL_SIZE);
                break;

            case DOWN:
                setPosition(xcoordinate, ycoordinate - BOARD_CELL_SIZE);
                break;

            default:
                break;
        }
    }
}
