package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import game.GameConstants;
import game.SnakeMasterGame;


public class PauseScreen implements Screen {

    final transient String dismiss = "(click to dismiss)";
    final transient Stage stage;
    final transient SnakeMasterGame game;
    final transient  Label titleLabel;
    transient GameScreen screen;

    /**
     * Constructor for PauseScreen object.
     */
    public PauseScreen(final SnakeMasterGame game, GameScreen screen) {
        this.screen = screen;
        this.game = game;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Setting up the login button
        TextButton unpauseButton = new TextButton("Unpause  ", game.setup.textButtonStyle);
        unpauseButton.setSize(120, 30);
        unpauseButton.pad(30);
        unpauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    unpauseButtonClicked();
                } catch (InterruptedException e) {
                    Dialog d0 = new Dialog(dismiss, game.setup.skin2);
                    d0.center();
                    d0.text("Can't Pause the game right now");
                    d0.show(stage);
                    d0.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            d0.hide();
                        }
                    });
                }
            }
        });

        titleLabel = new Label("Game Paused", Setup.headingStyle);
        titleLabel.setFontScale(2);
        // Add New Password label and text field
        table.add(titleLabel);
        table.row();
        table.add(new Label("", Setup.headingStyle));
        table.row();

        // Add Unpause button
        table.add(unpauseButton).height(GameConstants.HEIGHT_OF_SCREEN / 10f);
        table.getCell(unpauseButton).spaceTop(5);
        table.row();

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

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(screen);
        }
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

    }

    /**
     * Method to unpause the game by changing its screen back to GAME
     * when the button is pressed.
     *
     * @throws InterruptedException thrown if the action has been interrupted.
     */
    public void unpauseButtonClicked() throws InterruptedException {
        game.setScreen(screen);
    }
}
