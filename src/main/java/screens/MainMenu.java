package screens;

import static screens.Setup.atlas;
import static screens.Setup.black;
import static screens.Setup.skin;
import static screens.Setup.white;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import game.GameConstants;
import game.SnakeMasterGame;

public class MainMenu implements Screen {

    final transient SnakeMasterGame game;
    final transient TextButton playButton;
    final transient TextButton settingsButton;
    final transient TextButton logoutButton;
    final transient Stage stage;
    final transient  Label titleLabel;


    /**
     * Constructor for class MainMenu.
     */
    public MainMenu(final SnakeMasterGame game) {
        this.game = game;
        stage = new Stage();
        //Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Setting up the PLAY button.
        Setup setup = new Setup();
        playButton = new TextButton("Play  ", setup.textButtonStyle);
        playButton.setSize(120, 30);
        playButton.pad(30);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // What happens when we click register button
                game.setScreen(new GameScreen(game, 1));
                game.soundtrack.play();
            }
        });

        // Setting up the SETTINGS button.
        settingsButton = new TextButton("Settings  ", setup.textButtonStyle);
        settingsButton.setSize(120, 30);
        settingsButton.pad(30);
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Settings(game));
            }
        });

        // Setting up the LOGIN button.
        logoutButton = new TextButton("Logout  ", setup.textButtonStyle);
        logoutButton.setSize(120, 30);
        logoutButton.pad(30);
        logoutButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Maybe some additional functionality?
                game.user = null; //NOPMD
                game.setScreen(new PreGame(game));
            }
        });

        titleLabel = new Label("Main Menu", setup.headingStyle);
        titleLabel.setFontScale(2);

        // Displaying UI elements
        table.add(titleLabel);
        table.row();
        table.add(new Label("", setup.headingStyle));
        table.row();
        table.add(playButton).height(GameConstants.HEIGHT_OF_SCREEN / 10f);
        table.getCell(playButton).spaceBottom(10);
        table.row();
        table.add(settingsButton).height(GameConstants.HEIGHT_OF_SCREEN / 10f);
        table.getCell(settingsButton).spaceBottom(10);
        table.row();
        table.add(logoutButton).height(GameConstants.HEIGHT_OF_SCREEN / 10f);
        //  table.debug();
        table.setBackground(new TextureRegionDrawable(new TextureRegion(
                new Texture("src/main/resources/grass.png"))));
        stage.addActor(table);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.input.setInputProcessor(stage);
        Gdx.gl.glClearColor(0, 0, 0, 1); //set global color to black with opacity 1
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
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
        stage.dispose();
        atlas.dispose();
        skin.dispose();
        white.dispose();
        black.dispose();
    }
}
