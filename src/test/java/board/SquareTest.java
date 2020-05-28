package board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SquareTest {

    private transient Square square;
    private transient Sprite spriteTest;
    private transient Direction directionTest;


    @BeforeEach
    void setUp() {
        spriteTest = Mockito.mock(Sprite.class);
        square = new Square(0, 0, spriteTest);
    }

    @Test
    public void drawSpriteTest() {
        SpriteBatch batch = Mockito.mock(SpriteBatch.class);
        square.drawSprite(batch);
        Mockito.verify(batch, times(1))
                .draw(square.sprite, square.xcoordinate, square.ycoordinate);
    }

    @Test
    void getXcoordinate() {
        assertEquals(0, square.getXcoordinate());
    }

    @Test
    void getYcoordinate() {
        assertEquals(0, square.getYcoordinate());
    }

    @Test
    void getSprite() {
        assertEquals(spriteTest, square.getSprite());
    }

    @Test
    void setXcoordinate() {
        square.setXcoordinate(1);
        assertEquals(1, square.getXcoordinate());
    }

    @Test
    void setYcoordinate() {
        square.setYcoordinate(1);
        assertEquals(1, square.getYcoordinate());
    }

    @Test
    void setSprite() {
        Sprite newSprite = new Sprite();
        square.setSprite(newSprite);
        assertEquals(newSprite, square.getSprite());
    }

    @Test
    void setPosition() {
        square.setPosition(1, 1);
        assertEquals(square.getXcoordinate(), 1);
        assertEquals(square.getYcoordinate(), 1);
        Mockito.verify(spriteTest, times(1)).setPosition(1, 1);
    }
}