package board;

import static game.GameConstants.BOARD_CELL_SIZE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import game.GameConstants;
import java.util.LinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;


public class SnakeTest {
    @Mock
    transient Texture mock;

    transient Snake snake;

    transient Board board;

    /**
     * This method sets up mocks used in this test class.
     */
    @BeforeEach
    public void setUp() {
        mock = Mockito.mock(Texture.class);
        snake = new Snake(mock, mock, mock);
        board = Mockito.mock(BoardLevel1.class);
    }

    @Test
    public void testConstructor() {

        assertEquals(5, snake.getSnakeBody().size());
        assertEquals(Direction.RIGHT, snake.getHead().direction);
    }

    @Test
    public void testGetSnakeBody() {
        LinkedList<RotatableSquare> squares = new LinkedList<>();
        snake.snakeBody = squares;
        assertEquals(squares, snake.getSnakeBody());
    }

    @Test
    public void testGetSnakeHead() {
        RotatableSquare head = new RotatableSquare(0, 0,  Direction.DOWN, mock);
        snake.head = head;
        assertEquals(head, snake.getHead());
    }

    @Test
    public void testGetSnakeTail() {
        RotatableSquare tail = new RotatableSquare(0, 0, Direction.DOWN, mock);
        snake.tail = tail;
        assertEquals(tail, snake.getTail());
    }

    @Test
    public void renderTest() {

        Sprite mocksprite = Mockito.mock(Sprite.class);
        LinkedList<RotatableSquare> squares = new LinkedList<>();
        squares.add(new RotatableSquare(0, 0, Direction.DOWN, mock));
        squares.get(0).sprite = mocksprite;
        snake.snakeBody = squares;
        SpriteBatch mocks = Mockito.mock(SpriteBatch.class);
        snake.render(mocks);
        Mockito.verify(mocksprite, times(snake.snakeBody.size())).draw(mocks);
    }

    @Test
    public void ateFoodTest() {
        Food food = new Food(snake, mock, board);
        food.foodsquare.setXcoordinate(3 * GameConstants.BOARD_CELL_SIZE);
        food.foodsquare.setYcoordinate(32);
        System.out.println(snake.getHead().getXcoordinate());
        System.out.println(snake.getHead().getYcoordinate());

        System.out.println(food.foodsquare.getXcoordinate());
        System.out.println(food.foodsquare.getYcoordinate());


        assertTrue(snake.ateFood(food));
    }

    @Test
    public void ateNoFoodTest() {
        Food food = new Food(snake, mock, board);
        food.foodsquare.setXcoordinate(4 * GameConstants.BOARD_CELL_SIZE);
        food.foodsquare.setYcoordinate(1);
        assertFalse(snake.ateFood(food));
    }

    @Test
    public void ateNoFoodYTest() {
        Food food = new Food(snake, mock, board);
        food.foodsquare.setXcoordinate(3 * GameConstants.BOARD_CELL_SIZE);
        food.foodsquare.setYcoordinate(100);
        assertFalse(snake.ateFood(food));
    }

    @Test
    public void hasCollidedTest() {
        Board mock = Mockito.mock(Board.class);

        LinkedList<Square> wallMap = new LinkedList<>();
        wallMap.add(new Square(10 * BOARD_CELL_SIZE, 10 * BOARD_CELL_SIZE, null));

        Mockito.when(mock.getWallMap()).thenReturn(wallMap);
        assertFalse(snake.hasCollided(mock));
    }

    @Test
    public void hasCollidedTrueTest() {
        Board mock = Mockito.mock(Board.class);
        snake.head.setXcoordinate(snake.head.getXcoordinate() - GameConstants.BOARD_CELL_SIZE);
        assertTrue(snake.hasCollided(mock));
    }

    @Test
    public void hasCollidedTrue2Test() {
        Board mock = Mockito.mock(Board.class);
        snake.head.setXcoordinate(snake.head.getXcoordinate() - GameConstants.BOARD_CELL_SIZE);
        snake.head.setYcoordinate(32);
        assertTrue(snake.hasCollided(mock));
    }
}
