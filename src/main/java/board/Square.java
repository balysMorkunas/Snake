package board;

import static game.GameConstants.BOARD_CELL_SIZE;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;



public class Square {

    /**
     * Coordinates of the GameElement.
     */
    transient int xcoordinate;
    transient int ycoordinate;

    transient Sprite sprite;

    /**
     * Constructor for a new GameElement.
     * @param x is the x of the GameElement
     * @param y is the y of the GameElement
     * @param s is the sprite of the GameElement
     */
    public Square(int x, int y, Sprite s) {
        xcoordinate = x;
        ycoordinate = y;
        sprite = s;
    }

    public void drawSprite(SpriteBatch batch) {
        batch.draw(sprite, xcoordinate, ycoordinate);
    }

    public int getXcoordinate() {
        return xcoordinate;
    }

    public void setXcoordinate(int xcoordinate) {
        this.xcoordinate = xcoordinate;
    }

    public int getYcoordinate() {
        return ycoordinate;
    }

    public void setYcoordinate(int ycoordinate) {
        this.ycoordinate = ycoordinate;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    /**
     * Sets this GameObject to a new position.
     *
     * @param newX is the new x coordinate of this GameObject
     * @param newY is the new y coordinate of this GameObject
     */
    public void setPosition(int newX, int newY) {
        this.xcoordinate = newX;
        this.ycoordinate = newY;
        sprite.setPosition(newX, newY);
    }

}


