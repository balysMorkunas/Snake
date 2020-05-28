package screens;

import static org.mindrot.jbcrypt.BCrypt.gensalt;
import static org.mindrot.jbcrypt.BCrypt.hashpw;
import static screens.Setup.skin;

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

public class Register implements Screen {

    final transient String dismiss = "(click to dismiss)";
    final transient SnakeMasterGame game;
    final transient Stage stage;
    final transient TextButton registerButton;
    final transient TextButton backButton;
    final transient TextField loginTextField;
    final transient TextField passwordTextField;
    final transient TextField passwordConfirmTextField;
    final transient TextField emailTextField;
    final transient Label usernameLabel;
    final transient Label passwordLabel;
    final transient Label passwordConfirmLabel;
    final transient Label emailLabel;
    final transient Label titleLabel;

    /**
     * Constructor for class Register.
     */
    public Register(final SnakeMasterGame game) {
        this.game = game;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Setting up the register button
        Setup setup = new Setup();
        registerButton = new TextButton("Register  ", setup.textButtonStyle);
        registerButton.setSize(120, 30);
        registerButton.pad(30);
        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                registerButtonClicked();
            }
        });

        // Setting up the back button
        backButton = new TextButton("Back  ", setup.textButtonStyle);
        backButton.setSize(120, 30);
        backButton.pad(30);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PreGame(game));
            }
        });

        // Setting up the labels
        titleLabel = new Label("Register", setup.headingStyle);
        titleLabel.setFontScale(2);
        usernameLabel = new Label("Username:", setup.headingStyle);
        passwordLabel = new Label("Password:", setup.headingStyle);
        passwordConfirmLabel = new Label("Confirm password:", setup.headingStyle);
        emailLabel = new Label("Email address:", setup.headingStyle);

        loginTextField = new TextField("", setup.skin2);
        passwordTextField = new TextField("", setup.skin2);
        emailTextField = new TextField("", setup.skin2);
        passwordConfirmTextField = new TextField("", Setup.skin2);
        passwordTextField.setPasswordMode(true);
        passwordConfirmTextField.setPasswordMode(true);
        passwordTextField.setPasswordCharacter('*');
        passwordConfirmTextField.setPasswordCharacter('*');

        // Add Title, Login label and button
        table.add(titleLabel);
        table.row();
        table.add(new Label("", setup.headingStyle));
        table.row();
        table.add(usernameLabel);
        table.row();
        table.add(loginTextField);
        table.row();

        // Add Password label and button
        table.add(passwordLabel);
        table.row();
        table.add(passwordTextField);
        table.row();

        //Add Confirm password label and button
        table.add(passwordConfirmLabel);
        table.row();
        table.add(passwordConfirmTextField);
        table.row();

        //Add email label and button
        table.add(emailLabel);
        table.row();
        table.add(emailTextField);
        table.row();

        // Add Back button
        table.add(registerButton).height(GameConstants.HEIGHT_OF_SCREEN / 10f);
        table.getCell(registerButton).spaceTop(5f);
        table.row();
        table.add(backButton).height(GameConstants.HEIGHT_OF_SCREEN / 10f);
        table.getCell(backButton).spaceTop(5f);

        //table.debug();
        table.setBackground(new TextureRegionDrawable(new TextureRegion(
                new Texture("src/main/resources/grass.png"))));
        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    /**
     * This method allows to perform operations by clicking "REGISTER" button.
     */
    @SuppressWarnings("PMD")
    public void registerButtonClicked() {
        UserDao userDao = new UserDao(MySqlConnector.getDataSource());
        String password = passwordTextField.getText();
        String password2 = passwordConfirmTextField.getText();
        String username = loginTextField.getText();
        String email = emailTextField.getText();

        ValidatorService validator = new ValidatorService();
        if (!validator.passwordValidity(password, password2).equals("Password is valid")) {
            validator.generateNotification(
                    validator.passwordValidity(password, password2), stage, Setup.skin2);
            return;
        } else if (!validator.isValidEmail(email)) {
            validator.generateNotification(
                    "Please enter a valid email address.", stage, Setup.skin2);
        }
        try {
            if (userDao.isUsernameTaken(username)) {
                validator.generateNotification(
                        "The username is already taken.", stage, Setup.skin2);
            } else {
                String salt = gensalt();
                password = hashpw(password, salt);
                User registeredUser = userDao.addUser(
                        new User(0, username, password, salt, email));
                System.out.println("User has been registered with ID:"
                        + registeredUser.getUserId());
                game.user = registeredUser;
                game.setScreen(new MainMenu(game));
            }
        } catch (Exception e) {
            validator.generateNotification(e.getMessage(), stage, Setup.skin2);
        }
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
        skin.dispose();
    }
}
