package board;

import static game.GameConstants.boardGrass;
import static game.GameConstants.wallMapLevel1;

import com.badlogic.gdx.graphics.Texture;


public class BoardLevel1 extends Board {

    /**
     * Create a board for level one.
     */
    public BoardLevel1() {
        createBoard(new Texture(boardGrass), wallMapLevel1, false);
    }


    /**
     * Board constructor used for testing (Texture is injected).
     * @param texture the texture used for the map.
     */
    public BoardLevel1(Texture texture, boolean calledByTest) {
        createBoard(texture, wallMapLevel1, calledByTest);
    }

}
