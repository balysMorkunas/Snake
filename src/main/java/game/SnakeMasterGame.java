package game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dataclasses.User;
import screens.MainMenu;
import screens.Setup;
import screens.Splash;

@SuppressWarnings("PMD")
public class SnakeMasterGame extends Game implements ApplicationListener {

    public static User user;
    public static int score;
    public Setup setup;
    public transient BitmapFont font;
    public transient SpriteBatch batch;
    public transient Music soundtrack;
    private Screen screen;

    @Override
    public void create() {
        soundtrack = Gdx.audio.newMusic(Gdx.files.internal(GameConstants.soundtrack));
        soundtrack.setLooping(true);
        setup = new Setup();
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new Splash(this));
    }

    @Override
    public void render() {
        screen.render(Gdx.graphics.getDeltaTime());
    }


    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        soundtrack.dispose();
    }

    @Override
    public void setScreen(Screen screen) {
        if (this.screen != null) {
            this.screen.hide();
        }
        this.screen = screen;
        if (this.screen != null) {
            this.screen.show();
        }
    }

}
