package screens;

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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import database.MySqlConnector;
import database.UserDao;
import game.GameConstants;
import game.SnakeMasterGame;
import org.mindrot.jbcrypt.BCrypt;
import services.ValidatorService;


public class ChangePassword implements Screen {

    final transient Stage stage;
    final transient Label newPasswordLabel;
    final transient Label repeatNewPasswordLabel;
    final transient TextField newPasswordTextField;
    final transient TextField repeatPasswordTextField;
    final transient TextButton changePasswordButton;
    final transient  Label titleLabel;
    transient SnakeMasterGame game;

    /**
     * Constructor for ChangePassword class.
     */
    public ChangePassword(final SnakeMasterGame game) {
        this.game = game;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        newPasswordLabel = new Label("Enter new password:", game.setup.headingStyle);
        repeatNewPasswordLabel = new Label("Repeat new password:", game.setup.headingStyle);
        newPasswordTextField = new TextField("", Setup.skin2);
        newPasswordTextField.setPasswordCharacter('*');
        newPasswordTextField.setPasswordMode(true);
        repeatPasswordTextField = new TextField("", Setup.skin2);
        repeatPasswordTextField.setPasswordCharacter('*');
        repeatPasswordTextField.setPasswordMode(true);

        //setting up CHANGE button
        changePasswordButton = new TextButton("Change  ", game.setup.textButtonStyle);
        changePasswordButton.pad(20);
        changePasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                validateButtonClicked();
            }
        });

        // Setting up the BACK button
        TextButton goBack = new TextButton("Back  ", game.setup.textButtonStyle);
        goBack.setSize(120, 30);
        goBack.pad(30);
        goBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Settings(game));
            }
        });

        titleLabel = new Label("Change Password", Setup.headingStyle);
        titleLabel.setFontScale(2);
        // Add New Password label and text field
        table.add(titleLabel);
        table.row();
        table.add(new Label("", Setup.headingStyle));
        table.row();
        table.add(newPasswordLabel).width(GameConstants.WIDTH_OF_SCREEN / 2.5f).uniform();
        table.row();
        table.add(newPasswordTextField).width(GameConstants.WIDTH_OF_SCREEN / 2.5f);
        table.row();

        // Add Repeat Password label and text field
        table.add(repeatNewPasswordLabel).width(GameConstants.WIDTH_OF_SCREEN / 2.5f).uniform();
        table.row();
        table.add(repeatPasswordTextField).width(GameConstants.WIDTH_OF_SCREEN / 2.5f);
        table.row();

        // Add Forgot Password Button
        table.add(changePasswordButton);
        table.getCell(changePasswordButton).spaceTop(5);

        table.row();
        table.add(goBack);
        table.getCell(goBack).spaceTop(5);
        table.setBackground(new TextureRegionDrawable(new TextureRegion(
                new Texture("src/main/resources/grass.png"))));

        stage.addActor(table);
    }

    /**
     * Method that checks username and email validity.
     */
    private void validateButtonClicked() {
        ValidatorService validator = new ValidatorService();
        String passValidity = validator.passwordValidity(
                newPasswordTextField.getText(),
                repeatPasswordTextField.getText());
        if (!passValidity.equals("Password is valid")) {
            generateNotification(passValidity);
            return;
        }
        Dialog dialog = new Dialog("Warning", Setup.skin2, "dialog") {
            public void result(Object obj) {
                if ((boolean) obj) {
                    UserDao userDao = new UserDao(MySqlConnector.getDataSource());
                    String password = newPasswordTextField.getText();
                    String salt = BCrypt.gensalt();
                    String updatedPassword = BCrypt.hashpw(password, salt);
                    try {
                        userDao.changePassword(SnakeMasterGame.user.getUserId(),
                                updatedPassword, salt);
                    } catch (Exception e) {
                        generateNotification(e.getMessage());
                    }
                    game.setScreen(new MainMenu(game));
                }
            }
        };
        dialog.text("Are you sure you want to change your password?");
        dialog.button("Yes", true);
        dialog.button("No", false);
        dialog.show(stage);
    }

    /**
     * Generates a notification for user.
     *
     * @param message Message to show in notification
     */
    public void generateNotification(String message) {
        String dismiss = "click to dismiss";
        System.out.println(message);
        Dialog dialog01 = new Dialog(dismiss, Setup.skin2);
        dialog01.center();
        dialog01.text(message);
        dialog01.show(stage);
        dialog01.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog01.hide();
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
    }
}
