package board;

import static game.GameConstants.boardWood;
import static game.GameConstants.wallMapLevel2;

import com.badlogic.gdx.graphics.Texture;


public class BoardLevel2 extends Board {

    /**
     * Create a board for level two.
     */
    public BoardLevel2() {
        createBoard(new Texture(boardWood), wallMapLevel2, false);
    }

    /**
     * Board constructor used for testing (Texture is injected).
     * @param texture the texture used for the map.
     */
    public BoardLevel2(Texture texture, boolean calledByTest) {
        createBoard(texture, wallMapLevel2, calledByTest);
    }

}
