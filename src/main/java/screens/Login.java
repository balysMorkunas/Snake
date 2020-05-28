package screens;

import static org.mindrot.jbcrypt.BCrypt.hashpw;
import static screens.Setup.atlas;
import static screens.Setup.skin;
import static screens.Setup.skin2;

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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import database.MySqlConnector;
import database.UserDao;
import dataclasses.User;
import game.GameConstants;
import game.SnakeMasterGame;
import services.ValidatorService;

public class Login implements Screen {

    final transient String dismiss = "(click to dismiss)";
    final transient TextField loginTextField;
    final transient TextField passwordTextField;
    final transient Label loginLabel;
    final transient Label passwordLabel;
    final transient Stage stage;
    final transient SnakeMasterGame game;
    final transient  Label titleLabel;

    /**
     * Constructor for class Login.
     */
    public Login(final SnakeMasterGame game) {

        this.game = game;

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Setting up the login button
        Setup setup = new Setup();
        TextButton logInButton = new TextButton("Login  ", setup.textButtonStyle);

        logInButton.setSize(120, 30);
        logInButton.pad(30);
        logInButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                logInButtonClicked();

            }
        });

        // Setting up the forgot password button
        TextButton forgotPasswordButton = new TextButton(
                "  Forgot Password       ", setup.textButtonStyle);
        forgotPasswordButton.setSize(120, 20);
        forgotPasswordButton.pad(30);
        forgotPasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ForgotPassword(game));
            }
        });

        // Setting up the back button
        TextButton backButton = new TextButton("Back  ", setup.textButtonStyle);
        backButton.setSize(120, 30);
        backButton.pad(30);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PreGame(game));
            }
        });

        // Creating text fields for all player information necessary for login.
        loginTextField = new TextField("", setup.skin2);
        passwordTextField = new TextField("", setup.skin2);
        passwordTextField.setPasswordCharacter('*');
        passwordTextField.setPasswordMode(true);

        // Creating labels for each defined text field in the class.
        titleLabel = new Label("Login", setup.headingStyle);
        titleLabel.setFontScale(2);
        loginLabel = new Label("Username:", setup.headingStyle);
        passwordLabel = new Label("Password:", setup.headingStyle);


        // Add Login label and button
        table.add(titleLabel);
        table.row();
        table.add(new Label("", setup.headingStyle));
        table.row();
        table.add(loginLabel);
        table.row();
        table.add(loginTextField);
        table.row();

        // Add Password label and button
        table.add(passwordLabel);
        table.row();
        table.add(passwordTextField);
        table.row();

        // Add Login button
        table.add(logInButton).height(GameConstants.HEIGHT_OF_SCREEN / 10f);
        table.getCell(logInButton).spaceTop(5);
        table.row();

        // Add Forgot Password button
        table.add(forgotPasswordButton).height(GameConstants.HEIGHT_OF_SCREEN / 10f);
        table.getCell(forgotPasswordButton).spaceTop(5);
        table.row();

        // Add Back button
        table.add(backButton).height(GameConstants.HEIGHT_OF_SCREEN / 10f);
        table.getCell(backButton).spaceTop(5);
        table.row();
        //table.debug();
        table.setBackground(new TextureRegionDrawable(new TextureRegion(
                new Texture("src/main/resources/grass.png"))));

        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    //TODO extract this from GUI

    /**
     * This method allows to perform operations by clicking "LOGIN!" button.
     */
    public void logInButtonClicked() {
        String username = loginTextField.getText();
        String password = passwordTextField.getText();
        ValidatorService validator = new ValidatorService();

        if (username.length() <= 0 || password.length() <= 0) {
            System.out.println("Username or password field is empty.");
            validator.generateNotification(
                    "Username or password field is empty.", stage, skin2);
        }
        UserDao userDao = new UserDao(MySqlConnector.getDataSource());
        try {
            User user = userDao.getUserByUsername(username);
            if (user == null) {
                validator.generateNotification(
                        "Incorrect login credentials.", stage, skin2);
                return;
            }
            String hashedpw = hashpw(password, user.getSeed());
            user.setPassword(hashedpw);

            if (userDao.isAuthorized(user)) {
                game.user = user;
                game.setScreen(new MainMenu(game));
            } else {
                validator.generateNotification(
                        "Incorrect login credentials.", stage, skin2);
            }
        } catch (Exception e) {
            validator.generateNotification(
                    e.getMessage(), stage, skin2);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.input.setInputProcessor(stage);
        Gdx.gl.glClearColor(0, 0, 0, 1); //set global color to black with opacity 1
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(0.05f);
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
    }
}
