package screens;

import board.Board;
import board.BoardFactory;
import board.BoardFactoryLevel;
import board.Food;
import board.Snake;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import game.GameConstants;
import game.SnakeMasterGame;

import java.util.Random;



public class GameScreen implements Screen {

    final transient int scoreNeededForLevelTwo = 300;
    final transient int scoreNeededForLevelThree = 600;

    final transient SnakeMasterGame game;
    transient BoardFactory boardFactory;
    transient Board board;
    transient Snake snake;
    transient Food food;

    transient float timeBetweenRenderCall;
    private transient boolean hasPressed;
    transient int level;

    /**
     * Constructor to make new SnakeGame Object.
     */
    public GameScreen(final SnakeMasterGame game, int level) {
        this.level = level;
        this.game = game;
        hasPressed = false;
        boardFactory = new BoardFactoryLevel();
        board = boardFactory.createBoard(level);
        snake = new Snake(new Texture(GameConstants.snakeHeadSpriteRight),
                new Texture(GameConstants.snakeBodySpriteRight),
                new Texture(GameConstants.snakeTailSpriteRight));
        food = new Food(snake, new Texture(GameConstants.foodSprite), board);
    }

    /**
     * Calls the methods to make the snake able to move on the right time.
     */
    @SuppressWarnings("PMD")
    public void update(float deltaTime) {
        //only register first press in time frame to prevent going opposite directions
        if (!hasPressed) {
            hasPressed = snake.moveOnKeyPress();
            if (snake.hasCollided(this.board)) {
                game.setScreen(new GameOver(game));
            }
        }
        //only update once every 0.1 seconds so the snake doesn't move too fast
        timeBetweenRenderCall += deltaTime;
        if (timeBetweenRenderCall > 0.1) {
            //check for collision
            if (snake.hasCollided(board)) {
                game.setScreen(new GameOver(game));
                game.soundtrack.pause();
                game.soundtrack = Gdx.audio.newMusic(Gdx.files.internal(GameConstants.deathSound));
                game.soundtrack.play();
                game.soundtrack = Gdx.audio.newMusic(Gdx.files.internal(GameConstants.soundtrack));
                game.soundtrack.setLooping(true);
            }
            //check if snake ate food
            if (snake.ateFood(food)) {
                game.score += 10;
                System.out.println(game.score);
                if (game.score >= scoreNeededForLevelTwo && level == 1) {
                    game.setScreen(new GameScreen(game, 2));
                    game.soundtrack.play();
                } else if (game.score >= scoreNeededForLevelThree && level == 2) {
                    game.setScreen(new GameScreen(game, 3));
                    game.soundtrack.play();
                }

                food.updateFoodLocation(snake, new Random(), board);
                snake.addBody(new Texture(GameConstants.snakeBodySpriteRight));
            }
            //move the snake every new frame
            snake.moveBody();
            timeBetweenRenderCall = 0;
            hasPressed = false;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)
                || Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            game.setScreen(new PauseScreen(game, this));
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        //update snakes location and direction
        update(delta);


        // render the objects to the screen
        game.batch.begin();
        board.render(game.batch);
        snake.render(game.batch);
        food.render(game.batch);
        game.font.setColor(Color.WHITE);
        game.font.getData().setScale(1.6f);
        game.font.draw(game.batch, "Score: " + game.score,
                (int) (GameConstants.WIDTH_OF_SCREEN * 0.45),
                (int) (GameConstants.HEIGHT_OF_SCREEN * 0.98));
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
    }
}
