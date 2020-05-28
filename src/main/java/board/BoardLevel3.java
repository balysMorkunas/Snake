package board;

import static game.GameConstants.boardDarkStone;
import static game.GameConstants.wallMapLevel3;

import com.badlogic.gdx.graphics.Texture;

public class BoardLevel3 extends Board {

    /**
     * Create a board for level three.
     */
    public BoardLevel3() {
        createBoard(new Texture(boardDarkStone), wallMapLevel3, false);
    }

    /**
     * Board constructor used for testing (Texture is injected).
     * @param texture the texture used for the map.
     */
    public BoardLevel3(Texture texture, boolean calledByTest) {
        createBoard(texture, wallMapLevel3, calledByTest);
    }
}
