package board;

import static game.GameConstants.BOARD_CELL_SIZE;
import static game.GameConstants.WIDTH_OF_SCREEN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import game.GameConstants;
import java.util.LinkedList;
import java.util.Random;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class FoodTest {

    @Test
    public void constructorTest() {
        Texture mock = Mockito.mock(Texture.class);
        Board mockBoard = Mockito.mock(Board.class);
        Snake snake = new Snake(mock, mock, mock);

        Food food = new Food(snake, mock, mockBoard);
        assertNotEquals(snake.head.getXcoordinate()
                + GameConstants.BOARD_CELL_SIZE, food.foodsquare.getXcoordinate());
        assertNotEquals(snake.head.getYcoordinate()
                + 5 * GameConstants.BOARD_CELL_SIZE, food.foodsquare.getYcoordinate());
    }

    @Test
    public void renderTest() {
        Texture mocktex = Mockito.mock(Texture.class);
        Board mockBoard = Mockito.mock(Board.class);
        Snake snake = new Snake(mocktex, mocktex, mocktex);
        Food food = new Food(snake, mocktex, mockBoard);
        SpriteBatch mock = Mockito.mock(SpriteBatch.class);
        food.render(mock);
        Mockito.verify(mock, Mockito.times(1))
                .draw(food.foodsquare.getSprite(), food.foodsquare.getXcoordinate(),
                        food.foodsquare.getYcoordinate());
    }

    @Test
    public void updateCorrectFoodLocationTest() {
        Texture mocktex = Mockito.mock(Texture.class);
        Board mockBoard = Mockito.mock(Board.class);
        Snake snake = new Snake(mocktex, mocktex, mocktex);
        Food food = new Food(snake, mocktex, mockBoard);
        Random mock = Mockito.mock(Random.class);
        Mockito.when(mock.nextInt(WIDTH_OF_SCREEN / BOARD_CELL_SIZE - 1)).thenReturn(8)
                .thenReturn(8);
        food.updateFoodLocation(snake, mock, mockBoard);
        assertEquals(8 * BOARD_CELL_SIZE, food.foodsquare.getXcoordinate());
        assertEquals(8 * BOARD_CELL_SIZE, food.foodsquare.getYcoordinate());
    }

    @Test
    public void updateWrongFoodLocationBecauseOfSnakeTest() {
        Texture mocktex = Mockito.mock(Texture.class);
        Board mockBoard = Mockito.mock(Board.class);
        Snake snake = new Snake(mocktex, mocktex, mocktex);
        Food food = new Food(snake, mocktex, mockBoard);
        Random mock = Mockito.mock(Random.class);
        Mockito.when(mock.nextInt(WIDTH_OF_SCREEN / BOARD_CELL_SIZE - 1)).thenReturn(3)
                .thenReturn(1)
                .thenReturn(8)
                .thenReturn(8);
        food.updateFoodLocation(snake, mock, mockBoard);
        assertNotEquals(3 * BOARD_CELL_SIZE, food.foodsquare.getXcoordinate());
        assertNotEquals(0 * BOARD_CELL_SIZE, food.foodsquare.getYcoordinate());
        assertEquals(8 * BOARD_CELL_SIZE, food.foodsquare.getXcoordinate());
        assertEquals(8 * BOARD_CELL_SIZE, food.foodsquare.getYcoordinate());
    }

    @Test
    public void updateWrongFoodLocationBecauseOfWallTest1() {
        Texture mocktex = Mockito.mock(Texture.class);
        Board mockBoard = Mockito.mock(Board.class);
        Snake snake = new Snake(mocktex, mocktex, mocktex);
        Random mock = Mockito.mock(Random.class);
        Mockito.when(mock.nextInt(WIDTH_OF_SCREEN / BOARD_CELL_SIZE - 1)).thenReturn(10)
                .thenReturn(10)
                .thenReturn(8)
                .thenReturn(8);
        Food food = new Food(snake, mocktex, mockBoard);
        LinkedList<Square> wallMap = new LinkedList<>();
        wallMap.add(new Square(10 * BOARD_CELL_SIZE, 10 * BOARD_CELL_SIZE, null));
        Mockito.when(mockBoard.getWallMap()).thenReturn(wallMap);
        food.updateFoodLocation(snake, mock, mockBoard);
        assertNotEquals(3 * BOARD_CELL_SIZE, food.foodsquare.getXcoordinate());
        assertNotEquals(0 * BOARD_CELL_SIZE, food.foodsquare.getYcoordinate());
        assertEquals(8 * BOARD_CELL_SIZE, food.foodsquare.getXcoordinate());
        assertEquals(8 * BOARD_CELL_SIZE, food.foodsquare.getYcoordinate());
    }

    @Test
    public void updateWrongFoodLocationBecauseOfWallTest2() {
        Texture mocktex = Mockito.mock(Texture.class);
        Board mockBoard = Mockito.mock(Board.class);
        Snake snake = new Snake(mocktex, mocktex, mocktex);
        Random mock = Mockito.mock(Random.class);
        Mockito.when(mock.nextInt(WIDTH_OF_SCREEN / BOARD_CELL_SIZE - 1)).thenReturn(10)
                .thenReturn(15);
        Food food = new Food(snake, mocktex, mockBoard);
        LinkedList<Square> wallMap = new LinkedList<>();
        wallMap.add(new Square(10 * BOARD_CELL_SIZE, 10 * BOARD_CELL_SIZE, null));


        Mockito.when(mockBoard.getWallMap()).thenReturn(wallMap);
        food.updateFoodLocation(snake, mock, mockBoard);
        assertNotEquals(3 * BOARD_CELL_SIZE, food.foodsquare.getXcoordinate());
        assertNotEquals(0 * BOARD_CELL_SIZE, food.foodsquare.getYcoordinate());
        assertEquals(10 * BOARD_CELL_SIZE, food.foodsquare.getXcoordinate());
        assertEquals(15 * BOARD_CELL_SIZE, food.foodsquare.getYcoordinate());
    }
}
