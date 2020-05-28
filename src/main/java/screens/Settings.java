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
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import database.MySqlConnector;
import database.UserDao;
import game.GameConstants;
import game.SnakeMasterGame;

public class Settings implements Screen {

    final transient Stage stage;
    final transient Label heading;
    final transient SnakeMasterGame game;

    /**
     * Constructor for class Settings.
     */
    @SuppressWarnings("PMD")
    public Settings(final SnakeMasterGame game) {
        this.game = game;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Setting up the Forgot Password button
        Setup setup = new Setup();
        TextButton forgotPassword = new TextButton("Change Password", setup.textButtonStyle);
        forgotPassword.setSize(120, 30);
        forgotPassword.pad(30);
        forgotPassword.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ChangePassword(game));
            }
        });

        // Setting up the Delete button
        TextButton deleteAccount = new TextButton("Delete Account", setup.textButtonStyle);
        deleteAccount.setSize(120, 30);
        deleteAccount.pad(30);
        deleteAccount.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Logic for deleting an account.
                Dialog dialog = new Dialog("Warning", setup.skin2, "dialog") {
                    public void result(Object obj) {
                        if ((boolean) obj) {
                            UserDao userDao = new UserDao(MySqlConnector.getDataSource());
                            try {
                                userDao.deleteUser(game.user.getUserId());
                            } catch (Exception e) {
                                showDialog(e.getMessage());
                                return;
                            }
                            game.setScreen(new Login(game));
                        }
                    }

                };
                dialog.text("Are you sure you want to delete your account?");
                dialog.button("Yes", true);
                dialog.button("No", false);
                dialog.show(stage);
            }
        });

        // Setting up the BACK button
        TextButton goBack = new TextButton("Back", setup.textButtonStyle);
        goBack.setSize(120, 30);
        goBack.pad(30);
        goBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenu(game));
            }
        });

        // Setting up the HEADING label
        heading = new Label("Settings", setup.headingStyle);
        heading.setFontScale(2);

        // Displaying UI elements
        table.add(heading);
        table.getCell(heading).spaceBottom(100);
        table.row();
        table.add(forgotPassword).height(GameConstants.HEIGHT_OF_SCREEN / 10f);
        table.getCell(forgotPassword).spaceBottom(10);
        table.row();
        table.add(deleteAccount).height(GameConstants.HEIGHT_OF_SCREEN / 10f);
        table.getCell(deleteAccount).spaceBottom(10);
        table.row();
        table.add(goBack).height(GameConstants.HEIGHT_OF_SCREEN / 10f);
        //table.debug();
        table.setBackground(new TextureRegionDrawable(new TextureRegion(
                new Texture("src/main/resources/grass.png"))));

        stage.addActor(table);
    }

    private void showDialog(String s) {
        Dialog d2 = new Dialog("(click to dismiss)", Setup.skin2);
        d2.center();
        d2.text(s);
        d2.show(stage);
        d2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                d2.hide();
            }
        });
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
