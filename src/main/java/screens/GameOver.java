package screens;

import static screens.Setup.atlas;
import static screens.Setup.black;
import static screens.Setup.skin;
import static screens.Setup.skin2;
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
import com.badlogic.gdx.utils.Align;
import database.LeaderboardDao;
import database.MySqlConnector;
import database.ScoreDao;
import dataclasses.LeaderboardItem;
import dataclasses.Score;
import game.GameConstants;
import game.SnakeMasterGame;

import java.util.ArrayList;
import java.util.List;

public class GameOver implements Screen {

    final transient Stage stage;
    final transient Label score;
    final transient Label gameOverLabel;
    final transient Table table;
    final transient Setup setup;
    final transient SnakeMasterGame game;
    final transient ScoreDao scoreDao;
    final transient LeaderboardDao leaderboardDao;
    final transient TextButton mainMenu;
    final transient TextButton startOver;
    transient int ranking = 1;

    /**
     * Constructor for GameOver class.
     */
    @SuppressWarnings("PMD")
    public GameOver(final SnakeMasterGame game) {
        this.game = game;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // Creating GAME OVER label
        setup = new Setup();
        gameOverLabel = new Label("GAME OVER", setup.headingStyle);
        gameOverLabel.setFontScale(2);
        // Creating Your Score: + result label
        Score currentScore = new Score(0, game.user.getUserId(), game.score);
        scoreDao = new ScoreDao(MySqlConnector.getDataSource());
        leaderboardDao = new LeaderboardDao(MySqlConnector.getDataSource());
        try {
            scoreDao.addScore(currentScore);
        } catch (Exception e) {
            showDialog(e.getMessage());
        }
        score = new Label("Your score: " + game.score, setup.headingStyle);
        // Creating Start Over button
        startOver = new TextButton("  Try Again     ", setup.textButtonStyle);
        setUpStartOverButton();
        // Creating Main Menu button
        mainMenu = new TextButton("  Main Menu     ", setup.textButtonStyle);
        setUpMainMenuButton();
        // Add Password label and button
        table.add(gameOverLabel).width(GameConstants.WIDTH_OF_SCREEN / 5f).uniform();
        gameOverLabel.setAlignment(Align.center);
        table.row();
        table.add(score).width(GameConstants.WIDTH_OF_SCREEN / 5f).uniform();
        score.setAlignment(Align.center);

        table.row();
        table.add(new Label("Leaderboard:", setup.headingStyle));
        table.row();

        List<LeaderboardItem> leaderboardItemList = new ArrayList<>();
        try {
            leaderboardItemList = leaderboardDao.getTop10Scores();
        } catch (Exception e) {
            showDialog(e.getMessage());
        }

        for (int i = 0; i < leaderboardItemList.size(); i++) {
            String nameScore = ranking + ". " + leaderboardItemList.get(i).getUsername()
                    + " : " + leaderboardItemList.get(i).getHighestScore();
            ranking++;
            table.add(new Label(nameScore, setup.headingStyle));
            table.row();
        }

        if (leaderboardItemList.isEmpty()) {
            table.row();
        }

        table.add(new Label("", setup.headingStyle));
        table.row();
        table.add(startOver).height(GameConstants.HEIGHT_OF_SCREEN / 10f).spaceTop(5f);
        table.row();
        table.add(mainMenu).height(GameConstants.HEIGHT_OF_SCREEN / 10f);
        table.row();
        table.setBackground(new TextureRegionDrawable(new TextureRegion(
                new Texture("src/main/resources/grass.png"))));
        stage.addActor(table);
    }

    /**
     * Set up the start over button.
     */
    private void setUpStartOverButton() {
        startOver.pad(20);
        startOver.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //reset score
                game.score = 0;
                game.setScreen(new GameScreen(game, 1));
                game.soundtrack.play();
            }
        });

    }

    /**
     * Sets up the main menu button.
     */
    private void setUpMainMenuButton() {
        mainMenu.pad(20);
        mainMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenu(game));
            }
        });

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
        skin2.dispose();
        white.dispose();
        black.dispose();
    }
}
