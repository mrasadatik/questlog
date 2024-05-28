package com.zynotic.studios.quadsquad.questlog.scenes;

import atlantafx.base.controls.CustomTextField;
import atlantafx.base.controls.PasswordTextField;
import atlantafx.base.layout.InputGroup;
import atlantafx.base.theme.Styles;
import atlantafx.base.theme.Tweaks;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.zynotic.studios.quadsquad.questlog.QuestLog;
import com.zynotic.studios.quadsquad.questlog.entities.User;
import com.zynotic.studios.quadsquad.questlog.entities.UserPhoneNumber;
import com.zynotic.studios.quadsquad.questlog.enums.Gender;
import com.zynotic.studios.quadsquad.questlog.services.DataService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.hibernate.validator.HibernateValidator;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2OutlinedAL;
import org.kordamp.ikonli.material2.Material2OutlinedMZ;

import net.synedra.validatorfx.Validator;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static com.zynotic.studios.quadsquad.questlog.configs.AppConfig.getRequiredApplicationProperty;
import static com.zynotic.studios.quadsquad.questlog.entities.UserPhoneNumber.*;
import static com.zynotic.studios.quadsquad.questlog.validation.InputValidation.*;

public class SignUpScene {
    DataService<User> usersService = new DataService<User>("database/users.json", User.class);
    private final StackPane root;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_DATE;
    private static final String APP_DEFAULT_TIMEZONE = getRequiredApplicationProperty("APP_DEFAULT_TIMEZONE");
    LocalDate today = LocalDate.now(ZoneId.of(APP_DEFAULT_TIMEZONE));
    private Validator validator = new Validator();
    private final AtomicBoolean touchedName = new AtomicBoolean(false);
    private final AtomicBoolean touchedUsername = new AtomicBoolean(false);
    private final AtomicBoolean touchedEmail = new AtomicBoolean(false);
    private final AtomicBoolean touchedPhoneNumber = new AtomicBoolean(false);
    private final AtomicBoolean touchedDateOfBirth = new AtomicBoolean(false);
    private final AtomicBoolean touchedGender = new AtomicBoolean(false);
    private final AtomicBoolean touchedPassword = new AtomicBoolean(false);
    private final AtomicBoolean touchedConfirmedPassword = new AtomicBoolean(false);
    
    private final String[] countryCodes = getCountries();

    public record CountryCallingCodeWithFlag(String text) {}

    private static class CountryCallingCodeWithFlagCell extends ListCell<CountryCallingCodeWithFlag> {
        @Override
        protected void updateItem(CountryCallingCodeWithFlag countryCallingCodeWithFlag, boolean isEmpty) {
            super.updateItem(countryCallingCodeWithFlag, isEmpty);

            if (isEmpty) {
                setGraphic(null);
                setText(null);
            } else {
                ImageView countryFlag = new ImageView(new Image("/assets/images/flags/3x2/png/" + countryCallingCodeWithFlag.text() + ".png"));
                countryFlag.setFitHeight(2*6);
                countryFlag.setPreserveRatio(true);
                setGraphic(countryFlag);
                setAlignment(Pos.CENTER_LEFT);
                setText("(" + countryCallingCodeWithFlag.text() + ") +" + getCountryCallingCode(countryCallingCodeWithFlag.text()));
            }
        }
    }

    public record GenderBadge(String text) {}

    private static class GenderBadgeCell extends ListCell<GenderBadge> {
        @Override
        protected void updateItem(GenderBadge genderBadge, boolean isEmpty) {
            super.updateItem(genderBadge, isEmpty);

            if (isEmpty) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(null);
                setText(genderBadge.text());
            }
        }
    }

    private class PastDateCell extends DateCell {
        @Override
        public void updateItem(LocalDate date, boolean empty) {
            super.updateItem(date, empty);
            setDisable(empty | date.isAfter(today));
        }
    }

    public SignUpScene(Stage primaryStage) throws IOException {

        primaryStage.setTitle("QuestLog - Sign Up");

        root = new StackPane();
        BorderPane sceneRoot = new BorderPane();
        ScrollPane signUpFormScrollWrapper = new ScrollPane();
        GridPane signUpForm = new GridPane();
        HBox signUpActionBtnGroup = new HBox();
        VBox signUpFormHeader = new VBox();

        ImageView appLogo = new ImageView(new Image("/assets/images/logo/questlog/questlog--horizontal--light.png"));
        appLogo.setFitHeight(50);
        appLogo.setPreserveRatio(true);

        final ToolBar topBar = new ToolBar(
                appLogo
        );

        signUpForm.setVgap(10);
        signUpForm.setHgap(10);
        signUpForm.setPadding(new Insets(25));
        signUpForm.setAlignment(Pos.CENTER);

        signUpFormHeader.setSpacing(10);
        signUpFormHeader.setPadding(new Insets(10));
        signUpFormHeader.setAlignment(Pos.CENTER);

        ImageView signUpIcon = new ImageView(new Image("/assets/images/graphics/icons/sign_up.png"));
        signUpIcon.setFitHeight(50);
        signUpIcon.setPreserveRatio(true);

        Text signUpHeading = new Text("Sign Up");
        signUpHeading.getStyleClass().addAll(Styles.TITLE_2);

        signUpFormHeader.getChildren().addAll(signUpIcon, signUpHeading);

        CustomTextField nameField = new CustomTextField();
        Label nameLabel = new Label("Name");
        nameLabel.setMaxWidth(280);
        nameLabel.setLabelFor(nameField);
        nameField.setPromptText("Enter your full name");
        nameField.setLeft(new FontIcon(Material2OutlinedMZ.PERSON_ADD_ALT_1));
        nameField.setPrefWidth(280);
        Text nameErrorMessage = new Text(" ");
        nameErrorMessage.setWrappingWidth(280);
        nameErrorMessage.getStyleClass().addAll(Styles.TEXT);
        validator.createCheck()
                .dependsOn("name", nameField.textProperty())
                .withMethod(c -> validatorName(c, touchedName))
                .decoratingWith(m -> signUpFormDecorator(m, new CustomTextField[]{nameField}, null, null))
                .decorates(nameErrorMessage)
                .immediate();

        CustomTextField usernameField = new CustomTextField();
        Label usernameLabel = new Label("Username");
        usernameLabel.setMaxWidth(280);
        usernameLabel.setLabelFor(usernameField);
        usernameField.setPromptText("Enter an username");
        usernameField.setLeft(new FontIcon(Material2OutlinedAL.ALTERNATE_EMAIL));
        usernameField.setPrefWidth(280);
        Text usernameErrorMessage = new Text(" ");
        usernameErrorMessage.setWrappingWidth(280);
        usernameErrorMessage.getStyleClass().addAll(Styles.TEXT);
        validator.createCheck()
                .dependsOn("username", usernameField.textProperty())
                .withMethod(c -> validatorUsername(c, usersService, touchedUsername))
                .decoratingWith(m -> signUpFormDecorator(m, new CustomTextField[]{usernameField}, null, null))
                .decorates(usernameErrorMessage)
                .immediate();

        CustomTextField emailField = new CustomTextField();
        Label emailLabel = new Label("Email");
        emailLabel.setMaxWidth(280);
        emailLabel.setLabelFor(emailField);
        emailField.setPromptText("Enter your email address");
        emailField.setLeft(new FontIcon(Material2OutlinedAL.EMAIL));
        emailField.setPrefWidth(280);
        Text emailErrorMessage = new Text(" ");
        emailErrorMessage.setWrappingWidth(280);
        emailErrorMessage.getStyleClass().addAll(Styles.TEXT);
        validator.createCheck()
                .dependsOn("email", emailField.textProperty())
                .withMethod(c -> validatorEmail(c, usersService, touchedEmail))
                .decoratingWith(m -> signUpFormDecorator(m, new CustomTextField[]{emailField}, null, null))
                .decorates(emailErrorMessage)
                .immediate();

        CustomTextField phoneNumberSubscriberNumberField = new CustomTextField();

        ObservableList<CountryCallingCodeWithFlag> countryCodeComboBoxItems = Arrays.stream(countryCodes).map(CountryCallingCodeWithFlag::new).collect(Collectors.toCollection(
                FXCollections::observableArrayList
        ));

        ComboBox<CountryCallingCodeWithFlag> phoneNumberCountryCodeInputField = new ComboBox<>(countryCodeComboBoxItems);
        phoneNumberCountryCodeInputField.setPrefWidth(170);
        phoneNumberCountryCodeInputField.getStyleClass().add(Tweaks.ALT_ICON);
        phoneNumberCountryCodeInputField.setButtonCell(new CountryCallingCodeWithFlagCell());
        phoneNumberCountryCodeInputField.setCellFactory(c -> new CountryCallingCodeWithFlagCell());
        phoneNumberCountryCodeInputField.setPlaceholder(new Label("Loading..."));
        phoneNumberCountryCodeInputField.getSelectionModel().select(new CountryCallingCodeWithFlag("BD"));
        CustomTextField phoneNumberCountryCodeField = new CustomTextField();
        phoneNumberCountryCodeField.setVisible(false);
        phoneNumberCountryCodeField.setText(new CountryCallingCodeWithFlag("BD").text());
        phoneNumberCountryCodeInputField.setOnAction(e -> {
            phoneNumberSubscriberNumberField.setPromptText(getExampleNumber(phoneNumberCountryCodeInputField.getSelectionModel().getSelectedItem().text(), PhoneNumberUtil.PhoneNumberType.MOBILE));
            phoneNumberCountryCodeField.setText(phoneNumberCountryCodeInputField.getSelectionModel().getSelectedItem().text());
        });


        phoneNumberSubscriberNumberField.setPromptText(getExampleNumber(phoneNumberCountryCodeInputField.getSelectionModel().getSelectedItem().text(), PhoneNumberUtil.PhoneNumberType.MOBILE));

        InputGroup phoneNumberField = new InputGroup(phoneNumberCountryCodeInputField, phoneNumberSubscriberNumberField);
        phoneNumberField.setPrefWidth(280);
        Label phoneNumberLabel = new Label("Phone Number");
        phoneNumberLabel.setMaxWidth(280);
        phoneNumberLabel.setLabelFor(phoneNumberField);
        Text phoneNumberErrorMessage = new Text(" ");
        phoneNumberErrorMessage.setWrappingWidth(280);
        phoneNumberErrorMessage.getStyleClass().addAll(Styles.TEXT);
        validator.createCheck()
                .dependsOn("phoneNumberCountryCode", phoneNumberCountryCodeField.textProperty())
                .dependsOn("phoneNumberSubscriberNumber", phoneNumberSubscriberNumberField.textProperty())
                .withMethod(c -> validatorPhoneNumber(c, usersService, touchedPhoneNumber))
                .decoratingWith(m -> signUpFormDecorator(m, new CustomTextField[]{phoneNumberSubscriberNumberField}, new ComboBox<?>[]{phoneNumberCountryCodeInputField}, null))
                .decorates(phoneNumberErrorMessage)
                .immediate();

        DatePicker dateOfBirthField = new DatePicker();
        Label dateOfBirthLabel = new Label("Date of Birth (Georgian Calendar)");
        dateOfBirthLabel.setMaxWidth(280);
        dateOfBirthLabel.setLabelFor(dateOfBirthField);
        dateOfBirthField.setPromptText("yyyy-MM-dd");
        dateOfBirthField.setEditable(true);
        dateOfBirthField.setPrefWidth(280);
        dateOfBirthField.setDayCellFactory(c -> new PastDateCell());
        dateOfBirthField.setConverter(new StringConverter<>() {
            @Override
            public String toString(LocalDate localDate) {
                if (localDate == null) {
                    return "";
                }
                return dateFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if (dateString == null || dateString.trim().isEmpty()) {
                    return today;
                }
                try {
                    return LocalDate.parse(dateString, dateFormatter);
                } catch (DateTimeParseException e) {
                    return today;
                }
            }
        });
        Text dateOfBirthErrorMessage = new Text(" ");
        dateOfBirthErrorMessage.setWrappingWidth(280);
        dateOfBirthErrorMessage.getStyleClass().addAll(Styles.TEXT);
        validator.createCheck()
                .dependsOn("dateOfBirth", dateOfBirthField.getEditor().textProperty())
                .withMethod(c -> validatorDateOfBirth(c, touchedDateOfBirth))
                .decoratingWith(m -> signUpFormDecorator(m, new Control[]{dateOfBirthField.getEditor(), dateOfBirthField}, null, null))
                .decorates(dateOfBirthErrorMessage)
                .immediate();

        ObservableList<GenderBadge> genderComboBoxItems = Arrays.stream(Gender.values())
                .map(gender -> new GenderBadge(gender.name()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        ComboBox<GenderBadge> genderField = new ComboBox<>(genderComboBoxItems);
        Label genderLabel = new Label("Gender");
        genderLabel.setMaxWidth(280);
        genderLabel.setLabelFor(genderField);
        genderField.setPrefWidth(280);
        genderField.getStyleClass().add(Tweaks.ALT_ICON);
        genderField.setButtonCell(new GenderBadgeCell());
        genderField.setCellFactory(c -> new GenderBadgeCell());
        genderField.setPlaceholder(new Label("Loading..."));
        // gender.getSelectionModel().select(new GenderBadge("MALE"));
        Text genderErrorMessage = new Text(" ");
        genderErrorMessage.setWrappingWidth(280);
        genderErrorMessage.getStyleClass().addAll(Styles.TEXT);
        CustomTextField genderValidationHelper = new CustomTextField();
        genderValidationHelper.setVisible(false);
        genderField.setOnAction(e -> genderValidationHelper.setText(genderField.getSelectionModel().getSelectedItem().text()));
        validator.createCheck()
                .dependsOn("gender", genderValidationHelper.textProperty())
                .withMethod(c -> validatorGender(c, touchedGender))
                .decoratingWith(m -> signUpFormDecorator(m, null, new ComboBox<?>[]{genderField}, null))
                .decorates(genderErrorMessage)
                .immediate();

        PasswordTextField passwordField = new PasswordTextField();
        Label passwordLabel = new Label("Password");
        passwordLabel.setMaxWidth(280);
        passwordLabel.setLabelFor(passwordField);
        passwordField.setPromptText("Enter a new password");
        passwordField.setLeft(new FontIcon(Material2OutlinedAL.LOCK));
        passwordField.setPrefWidth(280);
        Text passwordErrorMessage = new Text(" ");
        passwordErrorMessage.setWrappingWidth(280);
        passwordErrorMessage.getStyleClass().addAll(Styles.TEXT);
        validator.createCheck()
                .dependsOn("password", passwordField.passwordProperty())
                .withMethod(c -> validatorPassword(c, touchedPassword))
                .decoratingWith(m -> signUpFormDecorator(m, null, null, new PasswordTextField[]{passwordField}))
                .decorates(passwordErrorMessage)
                .immediate();

        PasswordTextField confirmPasswordField = new PasswordTextField();
        Label confirmPasswordLabel = new Label("Confirm Password");
        confirmPasswordLabel.setMaxWidth(280);
        confirmPasswordLabel.setLabelFor(confirmPasswordField);
        confirmPasswordField.setPromptText("Re-type your password");
        confirmPasswordField.setLeft(new FontIcon(Material2OutlinedAL.LOCK));
        confirmPasswordField.setPrefWidth(280);
        Text confirmPasswordErrorMessage = new Text(" ");
        confirmPasswordErrorMessage.setWrappingWidth(280);
        confirmPasswordErrorMessage.getStyleClass().addAll(Styles.TEXT);
        validator.createCheck()
                .dependsOn("confirmPassword", confirmPasswordField.passwordProperty())
                .dependsOn("password", passwordField.passwordProperty())
                .withMethod(c -> validatorConfirmPassword(c, touchedConfirmedPassword))
                .decoratingWith(m -> signUpFormDecorator(m, null, null, new PasswordTextField[]{confirmPasswordField}))
                .decorates(confirmPasswordErrorMessage)
                .immediate();

        FontIcon passwordToggleIcon = getPasswordToggleIcon(passwordField, confirmPasswordField);

        passwordField.setRight(passwordToggleIcon);

        Button haveAccountBtn = new Button("Already have an account?");
        haveAccountBtn.getStyleClass().addAll(Styles.FLAT);
        haveAccountBtn.setMnemonicParsing(true);
        haveAccountBtn.setOnAction(e -> QuestLog.viewSignInScene());

        Button signUpBtn = new Button("Sign Up", new FontIcon(Material2OutlinedAL.LOG_IN));
        signUpBtn.getStyleClass().addAll(Styles.SUCCESS);
        signUpBtn.setMnemonicParsing(true);

        signUpActionBtnGroup.setSpacing(10);
        signUpActionBtnGroup.setAlignment(Pos.CENTER_RIGHT);
        signUpActionBtnGroup.getChildren().addAll(haveAccountBtn, signUpBtn);

        signUpForm.addRow(0, signUpFormHeader);
        signUpForm.addRow(1, new VBox(5, nameLabel, nameField, nameErrorMessage));
        signUpForm.addRow(2, new VBox(5, usernameLabel, usernameField, usernameErrorMessage));
        signUpForm.addRow(3, new VBox(5, emailLabel, emailField, emailErrorMessage));
        signUpForm.addRow(4, new VBox(5, phoneNumberLabel, phoneNumberField, phoneNumberErrorMessage));
        signUpForm.addRow(5, new VBox(5, dateOfBirthLabel, dateOfBirthField, dateOfBirthErrorMessage));
        signUpForm.addRow(6, new VBox(5, genderLabel, genderField, genderErrorMessage));
        signUpForm.addRow(7, new VBox(5, passwordLabel, passwordField, passwordErrorMessage));
        signUpForm.addRow(8, new VBox(5, confirmPasswordLabel, confirmPasswordField, confirmPasswordErrorMessage));
        GridPane.setConstraints(signUpActionBtnGroup, 0, 9, 1, 1, HPos.RIGHT, VPos.CENTER);

        signUpForm.getChildren().add(9, signUpActionBtnGroup);

        signUpFormScrollWrapper.setContent(signUpForm);
        signUpFormScrollWrapper.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        signUpFormScrollWrapper.setFitToHeight(true);
        signUpFormScrollWrapper.setFitToWidth(true);

        signUpBtn.setOnAction(e -> {
            boolean hasError = false;

            String name = nameField.getText();
            LocalDate dateOfBirth = null;
            Gender gender = null;
            String username = usernameField.getText();
            String password = passwordField.getPassword();
            String email = emailField.getText();
            String phoneNumberCountryCode = null;
            String phoneNumberSubscriberNumber = null;

            if (name.isBlank()) {
                touchedName.set(true);
                hasError = true;
            }

            if (dateOfBirthField.getValue() == null) {
                touchedDateOfBirth.set(true);
                hasError = true;
            } else {
                dateOfBirth = dateOfBirthField.getValue();
            }

            if (genderField.getSelectionModel().getSelectedItem() == null) {
                touchedGender.set(true);
                hasError = true;
            } else {
                gender = Gender.valueOf(genderField.getSelectionModel().getSelectedItem().text());
            }

            if (username.isBlank()) {
                touchedUsername.set(true);
                hasError = true;
            }

            if (password.isBlank()) {
                touchedPassword.set(true);
                touchedConfirmedPassword.set(true);
                hasError = true;
            }

            if (email.isBlank()) {
                touchedEmail.set(true);
                hasError = true;
            }

            if (phoneNumberCountryCodeInputField.getSelectionModel().getSelectedItem() == null || phoneNumberSubscriberNumberField.getText().isBlank()) {
                touchedPhoneNumber.set(true);
                hasError = true;
            } else {
                phoneNumberCountryCode = phoneNumberCountryCodeInputField.getSelectionModel().getSelectedItem().text();
                phoneNumberSubscriberNumber = phoneNumberSubscriberNumberField.getText();
            }

            validator.validate();

            if (!hasError && !validator.containsErrors()) {
                try {
                    signUp(name, dateOfBirth, gender, username, password, email, phoneNumberCountryCode, phoneNumberSubscriberNumber);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // Add elements to the root
        sceneRoot.setTop(topBar);
        sceneRoot.setCenter(signUpFormScrollWrapper);

        root.getChildren().addAll(sceneRoot);
    }

    private static FontIcon getPasswordToggleIcon(PasswordTextField passwordField, PasswordTextField confirmPasswordField) {
        FontIcon passwordToggleIcon= new FontIcon(Material2OutlinedMZ.VISIBILITY_OFF);
        passwordToggleIcon.setCursor(Cursor.HAND);
        passwordToggleIcon.setOnMouseClicked(e -> {
            passwordToggleIcon.setIconCode(passwordField.getRevealPassword() ? Material2OutlinedMZ.VISIBILITY_OFF : Material2OutlinedMZ.VISIBILITY);
            passwordField.setRevealPassword(!passwordField.getRevealPassword());
            confirmPasswordField.setRevealPassword(passwordField.getRevealPassword());
        });
        return passwordToggleIcon;
    }

    public StackPane getRoot() {
        return root;
    }

    private void signUp(String name, LocalDate dateOfBirth, Gender gender, String username, String password, String email, String phoneNumberCountryCode, String phoneNumberSubscriberNumber) throws IOException {
        UserPhoneNumber newUserPhoneNumber = new UserPhoneNumber(phoneNumberCountryCode, phoneNumberSubscriberNumber, PhoneNumberUtil.PhoneNumberType.MOBILE);
        User newUser = new User(name, dateOfBirth, gender, username, password, email, newUserPhoneNumber);
        usersService.addData(newUser);
    }
}
