package services;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.validator.routines.EmailValidator;

@SuppressWarnings("checkstyle")
public class ValidatorService {

    /**
     * Checks if email contains a correct format.
     */
    public boolean isValidEmail(String email) {
        return EmailValidator.getInstance(false).isValid(email);
    }

    /**
     * Checks if provided password satisfies conditions.
     */
    @SuppressWarnings("PMD")
    public String passwordValidity(String pass, String repeat) {
        Pattern p = Pattern.compile(
                "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$");
        Matcher passwordMatcher = p.matcher(pass);
        if (!pass.equals(repeat)) {
            return "Passwords do not match!";
        } else if (!passwordMatcher.find()) {
            return "Password must be at least 8 characters long,"
                    + " contain 1 lower case letter, 1 upper"
                    + " case letter, one special character.";
        }
        return "Password is valid";
    }

    /**
     * Generates a notification for user.
     *
     * @param message Message to show in notification
     */
    public void generateNotification(String message, Stage stage, Skin skin2) {
        String dismiss = "click to dismiss";
        System.out.println(message);
        Dialog dialog01 = new Dialog(dismiss, skin2);
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
}
