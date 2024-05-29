package com.zynotic.studios.quadsquad.questlog.validation;

import atlantafx.base.controls.PasswordTextField;
import atlantafx.base.theme.Styles;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.text.Text;
import net.synedra.validatorfx.Check;
import net.synedra.validatorfx.Decoration;
import net.synedra.validatorfx.ValidationMessage;

import java.util.concurrent.atomic.AtomicBoolean;

public class SignInInputValidation {

    /**
     * Creates a decoration for the sign-in form.
     *
     * @param m            the validation message
     * @param controlBoxes an array of Control objects
     * @param passBoxes    an array of PasswordTextField objects
     * @return a Decoration object
     */
    public static Decoration signInFormDecorator(ValidationMessage m, Control[] controlBoxes, PasswordTextField[] passBoxes) {
        return new Decoration() {
            @Override
            public void add(Node target) {
                ((Text) target).setText(m.getText());
                target.getStyleClass().addAll(Styles.DANGER);
                if (controlBoxes != null) {
                    for (Control box : controlBoxes) {
                        box.pseudoClassStateChanged(Styles.STATE_DANGER, true);
                    }
                }
                if (passBoxes != null) {
                    for (Control box : passBoxes) {
                        box.pseudoClassStateChanged(Styles.STATE_DANGER, true);
                    }
                }
            }

            @Override
            public void remove(Node target) {
                ((Text) target).setText(" ");
                target.getStyleClass().removeAll(Styles.DANGER);
                if (controlBoxes != null) {
                    for (Control box : controlBoxes) {
                        box.pseudoClassStateChanged(Styles.STATE_DANGER, false);
                    }
                }
                if (passBoxes != null) {
                    for (Control box : passBoxes) {
                        box.pseudoClassStateChanged(Styles.STATE_DANGER, false);
                    }
                }
            }
        };
    }

    // Validation methods

    /**
     * Validates the username field.
     *
     * @param c               the Check.Context
     * @param touchedUsername indicates if the username field has been touched
     */
    public static void validatorUsername(Check.Context c, AtomicBoolean touchedUsername) {
        String username = c.get("username");
        if (username.isEmpty()) {
            if (touchedUsername.get()) {
                c.error("Username required");
            }
        } else {
            touchedUsername.set(true);
        }
    }


    /**
     * Validates the password field.
     *
     * @param c               the Check.Context
     * @param touchedPassword indicates if the password field has been touched
     */
    public static void validatorPassword(Check.Context c, AtomicBoolean touchedPassword) {
        String password = c.get("password");
        if (password.isEmpty()) {
            if (touchedPassword.get()) {
                c.error("Password required");
            }
        } else {
            touchedPassword.set(true);
        }
    }
}
