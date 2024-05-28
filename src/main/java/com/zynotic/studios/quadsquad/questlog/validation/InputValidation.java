package com.zynotic.studios.quadsquad.questlog.validation;

import atlantafx.base.controls.PasswordTextField;
import atlantafx.base.theme.Styles;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.zynotic.studios.quadsquad.questlog.entities.User;
import com.zynotic.studios.quadsquad.questlog.entities.UserPhoneNumber;
import com.zynotic.studios.quadsquad.questlog.enums.Gender;
import com.zynotic.studios.quadsquad.questlog.services.DataService;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.text.Text;
import net.synedra.validatorfx.Check;
import net.synedra.validatorfx.Decoration;
import net.synedra.validatorfx.ValidationMessage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.zynotic.studios.quadsquad.questlog.configs.AppConfig.getRequiredApplicationProperty;
import static com.zynotic.studios.quadsquad.questlog.entities.UserPhoneNumber.getCountries;

/**
 * Utility class for input validation
 */
public class InputValidation {
    // Constants for date formatting
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_DATE;
    private static final String APP_DEFAULT_TIMEZONE = getRequiredApplicationProperty("APP_DEFAULT_TIMEZONE");
    private static final LocalDate today = LocalDate.now(ZoneId.of(String.valueOf(TimeZone.getTimeZone(APP_DEFAULT_TIMEZONE).toZoneId())));
    private static final String APP_AGE = getRequiredApplicationProperty("APP_AGE");

    // Regular expressions and patterns for validation
    public static final String regexName = "^(?!.*(?:\\s\\s|-{2,}|'{2,})).{2,}(?:\\s(?!.*(?:\\s\\s|-{2,}|'{2,})).{2,})+$";
    public static final String regexUsername = "^[a-z0-9]+$";
    public static final String regexDate = "^\\d{4}-\\d{2}-\\d{2}$";
    public static final String regexEmail = "^([^\\x00-\\x20\\x22\\x28\\x29\\x2c\\x2e\\x3a-\\x3c\\x3e\\x40\\x5b-\\x5d\\x7f-\\xff]+|\\x22([^\\x0d\\x22\\x5c\\x80-\\xff]|\\x5c[\\x00-\\x7f])*\\x22)(\\x2e([^\\x00-\\x20\\x22\\x28\\x29\\x2c\\x2e\\x3a-\\x3c\\x3e\\x40\\x5b-\\x5d\\x7f-\\xff]+|\\x22([^\\x0d\\x22\\x5c\\x80-\\xff]|\\x5c[\\x00-\\x7f])*\\x22))*\\x40([^\\x00-\\x20\\x22\\x28\\x29\\x2c\\x2e\\x3a-\\x3c\\x3e\\x40\\x5b-\\x5d\\x7f-\\xff]+|\\x5b([^\\x0d\\x5b-\\x5d\\x80-\\xff]|\\x5c[\\x00-\\x7f])*\\x5d)(\\x2e([^\\x00-\\x20\\x22\\x28\\x29\\x2c\\x2e\\x3a-\\x3c\\x3e\\x40\\x5b-\\x5d\\x7f-\\xff]+|\\x5b([^\\x0d\\x5b-\\x5d\\x80-\\xff]|\\x5c[\\x00-\\x7f])*\\x5d))*$";
    public static final String regexPassword = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,}$";
    private static final Pattern patternName = Pattern.compile(regexName);
    private static final Pattern patternDate = Pattern.compile(regexDate);
    private static final Pattern patternUsername = Pattern.compile(regexUsername);
    private static final Pattern patternEmail = Pattern.compile(regexEmail);
    private static final Pattern patternPassword = Pattern.compile(regexPassword);

    // Array of country codes
    private static final String[] countryCodes;

    // Static block to initialize country codes
    static {
        try {
            countryCodes = getCountries();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a decoration for the sign-up form.
     *
     * @param m          the validation message
     * @param controlBoxes an array of Control objects
     * @param comboBoxes an array of ComboBox objects
     * @param passBoxes an array of PasswordTextField objects
     * @return a Decoration object
     */
    public static Decoration signUpFormDecorator(ValidationMessage m, Control[] controlBoxes, ComboBox<?>[] comboBoxes, PasswordTextField[] passBoxes) {
        return new Decoration() {
            @Override
            public void add(Node target) {
                ((Text) target).setText(m.getText());
                target.getStyleClass().addAll(Styles.DANGER);
                if (comboBoxes != null) {
                    for (ComboBox<?> box : comboBoxes) {
                        box.pseudoClassStateChanged(Styles.STATE_DANGER, true);
                    }
                }
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
                if (comboBoxes != null) {
                    for (ComboBox<?> box : comboBoxes) {
                        box.pseudoClassStateChanged(Styles.STATE_DANGER, false);
                    }
                }
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
     * Validates the name field.
     *
     * @param c the Check.Context
     * @param touchedName indicates if the name field has been touched
     */
    public static void validatorName(Check.Context c, AtomicBoolean touchedName) {
        String name = c.get("name");
        Matcher matcher = patternName.matcher(name);
        if (name.isEmpty()) {
            if (touchedName.get()) {
                c.error("Name required");
            }
        } else {
            touchedName.set(true);
            if (!matcher.matches()) {
                c.error("Please enter a full name with a space separating first and last name. Avoid consecutive spaces, hyphens, or apostrophes");
            }
        }
    }

    /**
     * Validates the username field.
     *
     * @param c the Check.Context
     * @param touchedUsername indicates if the username field has been touched
     */
    public static void validatorUsername(Check.Context c, DataService<User> usersService, AtomicBoolean touchedUsername) {
        String username = c.get("username");
        Matcher matcher = patternUsername.matcher(username);
        if (username.isEmpty()) {
            if (touchedUsername.get()) {
                c.error("Username required");
            }
        } else {
            touchedUsername.set(true);
            if (!matcher.matches()) {
                c.error("Username can only contain letters (a-z) and digits (0-9)");
            } else {
                if (usersService.isDuplicate("username", username)) {
                    c.error("Username already exists");
                }
            }
        }
    }

    /**
     * Validates the username field.
     *
     * @param c the Check.Context
     * @param touchedEmail indicates if the email field has been touched
     */
    public static void validatorEmail(Check.Context c, DataService<User> usersService, AtomicBoolean touchedEmail) {
        String email = c.get("email");
        Matcher matcher = patternEmail.matcher(email);
        if (email.isEmpty()) {
            if (touchedEmail.get()) {
                c.error("Email required");
            }
        } else {
            touchedEmail.set(true);
            if (!matcher.matches()) {
                c.error("Please enter a valid email address");
            } else {
                if (usersService.isDuplicate("email", email)) {
                    c.error("Email address already exists");
                }
            }
        }
    }


    /**
     * Validates the phone number field.
     *
     * @param c the Check.Context
     * @param touchedPhoneNumber indicates if the phone number field has been touched
     */
    public static void validatorPhoneNumber(Check.Context c, DataService<User> usersService, AtomicBoolean touchedPhoneNumber) {
        String phoneNumberCountryCode = c.get("phoneNumberCountryCode");
        String phoneNumberSubscriberNumber = c.get("phoneNumberSubscriberNumber");
        if (phoneNumberSubscriberNumber.isEmpty()) {
            if (touchedPhoneNumber.get()) {
                c.error("Phone number required");
            }
        } else {
            touchedPhoneNumber.set(true);
            if (!Arrays.asList(countryCodes).contains(phoneNumberCountryCode)) {
                c.error("Invalid country code");
            } else {
                UserPhoneNumber phoneNumber = new UserPhoneNumber(phoneNumberCountryCode, phoneNumberSubscriberNumber, PhoneNumberUtil.PhoneNumberType.MOBILE);
                if (phoneNumber.impossibleReason() != null) {
                    c.error(phoneNumber.impossibleReason());
                } else {
                    if (!phoneNumber.isValid()) {
                        c.error("Invalid phone number");
                    } else {
                        if (usersService.isDuplicate("phoneNumber", phoneNumber)) {
                            c.error("Phone number already exists");
                        }
                    }
                }
            }
        }
    }


    /**
     * Validates the date of birth field.
     *
     * @param c the Check.Context
     * @param touchedDateOfBirth indicates if the date of birth field has been touched
     */
    public static void validatorDateOfBirth(Check.Context c, AtomicBoolean touchedDateOfBirth) {
        String dateOfBirth = c.get("dateOfBirth");
        if (Objects.equals(dateOfBirth, "")) {
            if (touchedDateOfBirth.get()) {
                c.error("Date of Birth required");
            }
        } else {
            touchedDateOfBirth.set(true);
            Matcher matcher = patternDate.matcher(dateOfBirth);
            if (matcher.matches()) {
                int minimum = Integer.parseInt(APP_AGE);
                LocalDate parsedDateOfBirth = LocalDate.parse(dateOfBirth, dateFormatter);
                if (!parsedDateOfBirth.isBefore(today.minusYears(minimum)) && !parsedDateOfBirth.equals(today.minusYears(minimum))) {
                    c.error("Must be " + minimum + "+ to create an account");
                }
            }
        }
    }


    /**
     * Validates the gender field.
     *
     * @param c the Check.Context
     * @param touchedGender indicates if the gender field has been touched
     */
    public static void validatorGender(Check.Context c, AtomicBoolean touchedGender) {
        String gender = c.get("gender");
        if (gender.isEmpty()) {
            if (touchedGender.get()) {
                c.error("Gender required");
            }
        } else {
            touchedGender.set(true);
            if (!Arrays.asList(Gender.values()).contains(Gender.valueOf(gender))) {
                c.error("Invalid gender");
            }
        }
    }


    /**
     * Validates the password field.
     *
     * @param c the Check.Context
     * @param touchedPassword indicates if the password field has been touched
     */
    public static void validatorPassword(Check.Context c, AtomicBoolean touchedPassword) {
        String password = c.get("password");
        Matcher matcher = patternPassword.matcher(password);
        if (password.isEmpty()) {
            if (touchedPassword.get()) {
                c.error("Password required");
            }
        } else {
            touchedPassword.set(true);
            if (!matcher.matches()) {
                c.error("Password must be at least 8 characters with a mix of uppercase, lowercase letters, digits, and special characters (no spaces or underscore)");
            }
        }
    }


    /**
     * Validates the confirmation password field.
     *
     * @param c the Check.Context
     * @param touchedConfirmedPassword indicates if the confirmation password field has been touched
     */
    public static void validatorConfirmPassword(Check.Context c, AtomicBoolean touchedConfirmedPassword) {
        String password = c.get("password");
        String confirmPassword = c.get("confirmPassword");
        if (confirmPassword.isEmpty()) {
            if (touchedConfirmedPassword.get()) {
                c.error("Confirm Password required");
            }
        } else {
            touchedConfirmedPassword.set(true);
            if (!password.equals(confirmPassword)) {
                c.error("Passwords do not match");
            }
        }
    }
}
