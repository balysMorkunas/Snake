package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class Setup {

    static BitmapFont black = new BitmapFont(Gdx.files.internal("font/black.fnt"), false);
    static BitmapFont white = new BitmapFont(Gdx.files.internal("font/white.fnt"), false);
    static TextureAtlas atlas = new TextureAtlas("ui/button.pack");
    static Skin skin = new Skin(atlas);
    static Skin skin2 = new Skin(Gdx.files.internal("uiskin.json"));
    static TextButtonStyle textButtonStyle = new TextButtonStyle();
    static LabelStyle headingStyle = new LabelStyle(white, Color.BLACK);

    /**
     * Constructor for class Setup.
     */
    public Setup() {
        textButtonStyle.up = skin.getDrawable("button.up");
        textButtonStyle.down = skin.getDrawable("button.down");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = black;

        // Creating label style
        headingStyle.font = white;
        headingStyle.fontColor = Color.WHITE;
    }
}
