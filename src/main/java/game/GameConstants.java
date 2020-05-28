package game;

import com.badlogic.gdx.Gdx;

public class GameConstants {
    public static final int WIDTH_OF_SCREEN = 800;
    public static final int HEIGHT_OF_SCREEN = 800;

    public static final int BOARD_CELL_SIZE = 32;
    public static final int BOARD_WIDTH = Gdx.graphics.getWidth() / BOARD_CELL_SIZE;
    public static final int BOARD_HEIGHT = Gdx.graphics.getHeight() / BOARD_CELL_SIZE;

    public static final String boardGrass = "src/main/resources/grass.png";
    public static final String snakeHeadSpriteRight = "src/main/resources/snakehead0.png";

    public static final String snakeBodySpriteRight  = "src/main/resources/snakebody0.png";

    public static final String snakeTailSpriteRight  = "src/main/resources/snaketail0.png";

    public static final String foodSprite = "src/main/resources/cakefood.png";
    public static final String boardWood = "src/main/resources/wood.png";
    public static final String boardStone = "src/main/resources/stone_texture.png";
    public static final String boardBrick = "src/main/resources/brick_wall.png";
    public static final String boardDarkStone = "src/main/resources/dark_stone.png";
    public static final String boardWater = "src/main/resources/water_texture.png";
    public static final String boardGrassLighter = "src/main/resources/grass_lighter.png";



    public static final String wallMapLevel1 = "src/main/resources/wallMaps/wallMapLevel1.txt";
    public static final String wallMapLevel2 = "src/main/resources/wallMaps/wallMapLevel2.txt";
    public static final String wallMapLevel3 = "src/main/resources/wallMaps/wallMapLevel3.txt";


    public static final String soundtrack = "src/main/resources/snakeST.wav";
    public static final String deathSound = "src/main/resources/gamelost.wav";

}
