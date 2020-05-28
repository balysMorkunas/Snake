package board;

import static org.junit.Assert.assertEquals;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import game.GameConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class RotatableSquareTest {

    transient Texture mock;
    transient Sprite sprite;

    @BeforeEach
    public void setUp() {
        mock = Mockito.mock(Texture.class);
        sprite = new Sprite(mock);
    }

    @Test
    public void constructorTest() {
        RotatableSquare square = new RotatableSquare(10, 11, Direction.RIGHT, mock);
        assertEquals(10, square.getXcoordinate());
        assertEquals(11, square.getYcoordinate());
        assertEquals(Direction.RIGHT, square.direction);
        assertEquals(square.sprite.getTexture(), mock);
    }

    @Test
    public void setDirectionRightTest() {
        RotatableSquare square = new RotatableSquare(12, 12, Direction.RIGHT, mock);
        square.setDirection(square.direction);
        assertEquals(0.f, square.getSprite().getRotation(), 0.01);
    }

    @Test
    public void setDirectionUpTest() {
        RotatableSquare square = new RotatableSquare(13, 13, Direction.UP, mock);
        square.setDirection(square.direction);
        assertEquals(90.f, square.getSprite().getRotation(), 0.01);
    }

    @Test
    public void setDirectionLeftTest() {
        RotatableSquare square = new RotatableSquare(14, 15, Direction.LEFT, mock);
        square.setDirection(square.direction);
        assertEquals(180.f, square.getSprite().getRotation(), 0.01);
    }

    @Test
    public void setDirectionDownTest() {
        RotatableSquare square = new RotatableSquare(120, 121, Direction.DOWN, mock);
        square.setDirection(square.direction);
        assertEquals(270.f, square.getSprite().getRotation(), 0.01);
    }

    @Test
    public void updateLocationRightTest() {
        RotatableSquare square = new RotatableSquare(122, 121, Direction.RIGHT, mock);
        square.updateLocation(square.direction);
        assertEquals(122 + GameConstants.BOARD_CELL_SIZE, square.getXcoordinate());
        assertEquals(121, square.getYcoordinate());
    }

    @Test
    public void updateLocationLeftTest() {
        RotatableSquare square = new RotatableSquare(102, 111, Direction.LEFT, mock);
        square.updateLocation(square.direction);
        assertEquals(102 - GameConstants.BOARD_CELL_SIZE, square.getXcoordinate());
        assertEquals(111, square.getYcoordinate());
    }

    @Test
    public void updateLocationUpTest() {
        RotatableSquare square = new RotatableSquare(142, 141,Direction.UP, mock);
        square.updateLocation(square.direction);
        assertEquals(141 + GameConstants.BOARD_CELL_SIZE, square.getYcoordinate());
        assertEquals(142, square.getXcoordinate());
    }

    @Test
    public void updateLocationDownTest() {
        RotatableSquare square = new RotatableSquare(222, 151,Direction.DOWN, mock);
        square.updateLocation(square.direction);
        assertEquals(151 - GameConstants.BOARD_CELL_SIZE, square.getYcoordinate());
        assertEquals(222, square.getXcoordinate());
    }
}
