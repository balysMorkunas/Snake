package board;

import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.graphics.Texture;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class BoardLevel3Test {

    @Test
    public void constructorTest() {
        Texture texture = Mockito.mock(Texture.class);

        Board board = new BoardLevel3(texture, true);
        assertTrue(board instanceof BoardLevel3);

    }
}
