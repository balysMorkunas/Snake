package screens;

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
import game.SnakeMasterGame;

public class PreGame implements Screen {

    final transient Stage stage;
    final transient Label title;
    final transient SnakeMasterGame game;

    /**
     * Constructor for class PreGame.
     */
    public PreGame(final SnakeMasterGame game) {
        this.game = game;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Setting up the LOGIN button.
        Setup setup = new Setup();
        TextButton loginButton = new TextButton("Login  ", setup.textButtonStyle);
        loginButton.setSize(120, 30);
        loginButton.pad(30);
        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // What happens when we click login button
                game.setScreen(new Login(game));
            }
        });

        // Setting up the REGISTER button.
        TextButton registerButton = new TextButton("Register  ", setup.textButtonStyle);
        registerButton.setSize(120, 30);
        registerButton.pad(30);
        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // What happens when we click register button
                game.setScreen(new Register(game));
            }
        });

        // Setting up the EXIT button.
        TextButton exitButton = new TextButton("Exit  ", setup.textButtonStyle);
        exitButton.setSize(120, 30);
        exitButton.pad(20);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // When we click EXIT button, the game closes;
                // Maybe some additional functionality?
                Gdx.app.exit();
            }
        });

        // Setting up the TITLE label
        title = new Label("Snake", setup.headingStyle);
        title.setFontScale(3);

        // Displaying UI elements
        table.add(title);
        table.getCell(title).spaceBottom(100);
        table.row();
        table.add(loginButton);
        table.getCell(loginButton).spaceBottom(10);
        table.row();
        table.add(registerButton);
        table.getCell(registerButton).spaceBottom(10);
        table.row();
        table.add(exitButton);

        table.setBackground(new TextureRegionDrawable(new TextureRegion(
                new Texture("src/main/resources/grass.png"))));

        //table.debug();
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
    }
}
