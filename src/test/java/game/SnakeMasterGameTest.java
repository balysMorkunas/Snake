package game;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import screens.Login;
import screens.PreGame;

public class SnakeMasterGameTest {

    @Test
    public void setScreenTest() {
        SnakeMasterGame game = new SnakeMasterGame();
        PreGame mock2 = Mockito.mock(PreGame.class);
        Login mock3 = Mockito.mock(Login.class);

        //Mockito.verify(mock3, times(1)).show();
        game.setScreen(mock2);
        game.setScreen(mock3);
        Mockito.verify(mock2).show();
        Mockito.verify(mock2).hide();
        Mockito.verify(mock3).show();

    }

    @Test
    public void setScreenNullTest() {
        SnakeMasterGame game = new SnakeMasterGame();
        Login nullscreen = Mockito.mock(Login.class);

        game.setScreen(null);
        game.setScreen(nullscreen);
        Mockito.verify(nullscreen).show();
        game.setScreen(null);



    }
}
