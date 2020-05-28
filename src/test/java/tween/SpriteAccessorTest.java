package tween;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.badlogic.gdx.graphics.g2d.Sprite;
import org.junit.jupiter.api.Test;

public class SpriteAccessorTest {

    public static final int ALPHA = 0;

    @Test
    public void getValuesTest() {
        SpriteAccessor spriteAccessor = new SpriteAccessor();
        Sprite target = new Sprite();
        assertEquals(1, spriteAccessor.getValues(target, ALPHA, new float[1]));
        assertEquals(-1, spriteAccessor.getValues(target, 1, new float[1]));
    }
}