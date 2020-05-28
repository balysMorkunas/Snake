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
import services.ForgotPasswordService;
import services.SendEmailService;
import services.ValidatorService;


public class ForgotPassword implements Screen {

    final transient Label titleLabel;
    transient Stage stage;
    transient TextField usernameTextField;
    transient TextField emailTextField;
    transient Label usernameLabel;
    transient Label emailLabel;
    transient SnakeMasterGame game;

    /**
     * Constructor for ForgotPassword class.
     */
    public ForgotPassword(final SnakeMasterGame game) {
        this.game = game;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Setting up the validate button
        Setup setup = new Setup();
        TextButton validateButton = new TextButton("Send   ", setup.textButtonStyle);

        validateButton.setSize(120, 30);
        validateButton.pad(30);
        validateButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                validateButtonClicked();
            }
        });

        // Setting up the back button
        TextButton backButton = new TextButton("Back  ", setup.textButtonStyle);
        backButton.setSize(120, 30);
        backButton.pad(30);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Login(game));
            }
        });

        // Creating text fields for all player information necessary for validation.
        usernameTextField = new TextField("", setup.skin2);
        emailTextField = new TextField("", setup.skin2);

        // Creating labels for each defined text field in the class.
        usernameLabel = new Label("Username:", setup.headingStyle);
        emailLabel = new Label("Email:", setup.headingStyle);


        titleLabel = new Label("Forgot Password", Setup.headingStyle);
        titleLabel.setFontScale(2);
        // Add New Password label and text field
        table.add(titleLabel);
        table.row();
        table.add(new Label("", Setup.headingStyle));
        table.row();

        table.add(usernameLabel);
        table.row();
        table.add(usernameTextField);
        table.row();

        // Add Email label and text field
        table.add(emailLabel);
        table.row();
        table.add(emailTextField);
        table.row();

        // Add Validate button
        table.add(validateButton).height(GameConstants.HEIGHT_OF_SCREEN / 10f);
        table.getCell(validateButton).spaceTop(5f);
        table.row();

        // Add Back button
        table.add(backButton).height(GameConstants.HEIGHT_OF_SCREEN / 10f);
        table.getCell(backButton).spaceTop(5f);
        table.row();

        table.setBackground(new TextureRegionDrawable(new TextureRegion(
                new Texture("src/main/resources/grass.png"))));
        stage.addActor(table);
    }

    /**
     * Generates a notification for user.
     *
     * @param message Message to show in notification
     */
    public void generateNotification(String message) {
        String d = "click to dismiss";
        System.out.println(message);
        Dialog dialog01 = new Dialog(d, Setup.skin2);
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

    /**
     * Method that checks username and email validity.
     */
    public void validateButtonClicked() {
        ValidatorService validator = new ValidatorService();
        if (!validator.isValidEmail(emailTextField.getText())) {
            generateNotification("Email is invalid!");
            return;
        }
        Dialog dialog = new Dialog("Warning", Setup.skin2, "dialog") {
            public void result(Object obj) {
                if ((boolean) obj) {
                    UserDao userDao = new UserDao(MySqlConnector.getDataSource());
                    try {
                        ForgotPasswordService fps = new ForgotPasswordService(userDao,
                                new SendEmailService());
                        fps.forgotPassword(usernameTextField.getText(),
                                emailTextField.getText());
                        game.setScreen(new MainMenu(game));
                    } catch (IllegalArgumentException ex) {
                        generateNotification(ex.getMessage());
                    } catch (Exception e) {
                        generateNotification(e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        };
        dialog.text("Are you sure you want to change your password?");
        dialog.button("Yes", true);
        dialog.button("No", false);
        dialog.show(stage);
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

    }
}
