package screens;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import game.SnakeMasterGame;
import tween.SpriteAccessor;

public class Splash implements Screen {

    transient SpriteBatch batch;
    final transient Sprite splashMainSprite;
    final transient Texture splashMain;
    final transient TweenManager tweenManager;
    final transient SnakeMasterGame game;

    /**
     * Constructor for class Splash.
     */
    public Splash(final SnakeMasterGame game) {
        this.game = game;
        tweenManager = new TweenManager();
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());

        splashMain = new Texture("src/main/resources/splash_screen.png");

        splashMainSprite = new Sprite(splashMain);

        splashMainSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Tween.set(splashMainSprite, SpriteAccessor.ALPHA).target(0).start(tweenManager);
        Tween.to(splashMainSprite, SpriteAccessor.ALPHA, 1)
                .target(1).repeatYoyo(1, 3)
                .setCallback((type, source)
                    -> game.setScreen(new PreGame(game))).start(tweenManager);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); //set global color to black with opacity 1
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        tweenManager.update(0.05f);
        game.batch.begin();
        splashMainSprite.draw(game.batch);
        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        splashMainSprite.getTexture().dispose();

    }
}
