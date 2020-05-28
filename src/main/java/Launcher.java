import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import game.GameConstants;
import game.SnakeMasterGame;

public class Launcher {
    public static SnakeMasterGame snakeMasterGame;

    /**
     * Initialized the window required for the game. Sets parameters like FPS, width,
     * height or title for the window. Opens new windows (scenes) of the game.
     *
     * @param arg used to run main method
     */
    public static void main(String[] arg) {
        new Launcher().launch();
    }

    /**
     * Method used to set the right settings of the game and launch the game.
     */
    public static void launch() {
        snakeMasterGame = new SnakeMasterGame();
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = GameConstants.WIDTH_OF_SCREEN;
        config.height = GameConstants.HEIGHT_OF_SCREEN;
        config.vSyncEnabled = true;
        config.useGL30 = true;
        config.foregroundFPS = 60; //Set 60 FPS for every user
        config.fullscreen = false;
        config.resizable = true;
        config.title = "SNAKE";
        new LwjglApplication(snakeMasterGame, config);
    }

}
