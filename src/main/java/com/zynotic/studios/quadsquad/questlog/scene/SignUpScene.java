package com.zynotic.studios.quadsquad.questlog.scene;

import atlantafx.base.controls.CustomTextField;
import atlantafx.base.controls.PasswordTextField;
import atlantafx.base.layout.InputGroup;
import atlantafx.base.theme.Styles;
import atlantafx.base.theme.Tweaks;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.zynotic.studios.quadsquad.questlog.QuestLog;
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
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2OutlinedAL;
import org.kordamp.ikonli.material2.Material2OutlinedMZ;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.stream.Collectors;

import static com.zynotic.studios.quadsquad.questlog.utils.UserPhoneNumber.*;

public class SignUpScene {
    private final StackPane root;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_DATE;
    LocalDate today = LocalDate.now(ZoneId.of("Asia/Dhaka"));
    private record CountryCodeBadge(String text) {}

    private static class CountryCodeBadgeCell extends ListCell<CountryCodeBadge> {
        @Override
        protected void updateItem(CountryCodeBadge countryCodeBadge, boolean isEmpty) {
            super.updateItem(countryCodeBadge, isEmpty);

            if (isEmpty) {
                setGraphic(null);
                setText(null);
            } else {
                ImageView countryFlag = new ImageView(new Image("/assets/images/flags/3x2/png/" + countryCodeBadge.text() + ".png"));
                countryFlag.setFitHeight(2*6);
                countryFlag.setPreserveRatio(true);
                setGraphic(countryFlag);
                setAlignment(Pos.CENTER_LEFT);
                setText("(" + countryCodeBadge.text() + ") +" + getCountryCallingCode(countryCodeBadge.text()));
            }
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
        String[] countryCodes = getCountries();

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

        CustomTextField name = new CustomTextField();
        name.setPromptText("Enter your full name");
        name.setLeft(new FontIcon(Material2OutlinedMZ.PERSON_ADD_ALT_1));
        name.setPrefWidth(280);
        Label nameErrorMessage = new Label(" ");

        CustomTextField username = new CustomTextField();
        username.setPromptText("Enter an username");
        username.setLeft(new FontIcon(Material2OutlinedAL.ALTERNATE_EMAIL));
        username.setPrefWidth(280);
        Label usernameErrorMessage = new Label(" ");

        CustomTextField email = new CustomTextField();
        email.setPromptText("Enter your email address");
        email.setLeft(new FontIcon(Material2OutlinedAL.EMAIL));
        email.setPrefWidth(280);
        Label emailErrorMessage = new Label(" ");

        CustomTextField phoneNumberSubscriberNumber = new CustomTextField();

        ObservableList<CountryCodeBadge> countryCodeComboBoxItems = Arrays.stream(countryCodes).map(CountryCodeBadge::new).collect(Collectors.toCollection(
                FXCollections::observableArrayList
        ));

        ComboBox<CountryCodeBadge> phoneNumberCountryCodeInput = new ComboBox<>(countryCodeComboBoxItems);
        phoneNumberCountryCodeInput.setPrefWidth(170);
        phoneNumberCountryCodeInput.getStyleClass().add(Tweaks.ALT_ICON);
        phoneNumberCountryCodeInput.setButtonCell(new CountryCodeBadgeCell());
        phoneNumberCountryCodeInput.setCellFactory(c -> new CountryCodeBadgeCell());
        phoneNumberCountryCodeInput.setPlaceholder(new Label("Loading..."));
        phoneNumberCountryCodeInput.getSelectionModel().select(new CountryCodeBadge("BD"));
        phoneNumberCountryCodeInput.setOnAction(e -> {
            phoneNumberSubscriberNumber.setPromptText(getExampleNumber(phoneNumberCountryCodeInput.getSelectionModel().getSelectedItem().text(), PhoneNumberUtil.PhoneNumberType.MOBILE));
        });


        phoneNumberSubscriberNumber.setPromptText(getExampleNumber(phoneNumberCountryCodeInput.getSelectionModel().getSelectedItem().text(), PhoneNumberUtil.PhoneNumberType.MOBILE));
        Label phoneNumberErrorMessage = new Label(" ");

        InputGroup phoneNumber = new InputGroup(phoneNumberCountryCodeInput, phoneNumberSubscriberNumber);
        phoneNumber.setPrefWidth(280);

        DatePicker dateOfBirth = new DatePicker();
        dateOfBirth.setPromptText("yyyy-MM-dd");
        dateOfBirth.setEditable(true);
        dateOfBirth.setPrefWidth(280);
        dateOfBirth.setConverter(new StringConverter<>() {
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
        Label dateOfBirthErrorMessage = new Label(" ");

        PasswordTextField password = new PasswordTextField();
        password.setPromptText("Enter a new password");
        password.setLeft(new FontIcon(Material2OutlinedAL.LOCK));
        password.setPrefWidth(280);
        Label passwordErrorMessage = new Label(" ");

        PasswordTextField confirmPassword = new PasswordTextField();
        confirmPassword.setPromptText("Re-type your password");
        confirmPassword.setLeft(new FontIcon(Material2OutlinedAL.LOCK));
        confirmPassword.setPrefWidth(280);
        Label confirmPasswordErrorMessage = new Label(" ");

        FontIcon passwordToggleIcon= new FontIcon(Material2OutlinedMZ.VISIBILITY_OFF);
        passwordToggleIcon.setCursor(Cursor.HAND);
        passwordToggleIcon.setOnMouseClicked(e -> {
            passwordToggleIcon.setIconCode(password.getRevealPassword() ? Material2OutlinedMZ.VISIBILITY_OFF : Material2OutlinedMZ.VISIBILITY);
            password.setRevealPassword(!password.getRevealPassword());
            confirmPassword.setRevealPassword(password.getRevealPassword());
        });

        password.setRight(passwordToggleIcon);

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
        signUpForm.addRow(1, new VBox(5, new Label("Name"), name, nameErrorMessage));
        signUpForm.addRow(2, new VBox(5, new Label("Username"), username, usernameErrorMessage));
        signUpForm.addRow(3, new VBox(5, new Label("Email"), email, emailErrorMessage));
        signUpForm.addRow(4, new VBox(5, new Label("Phone"), phoneNumber, phoneNumberErrorMessage));
        signUpForm.addRow(5, new VBox(5, new Label("Date of Birth"), dateOfBirth, dateOfBirthErrorMessage));
        signUpForm.addRow(6, new VBox(5, new Label("Password"), password, passwordErrorMessage));
        signUpForm.addRow(7, new VBox(5, new Label("Confirm Password"), confirmPassword, confirmPasswordErrorMessage));
        GridPane.setConstraints(signUpActionBtnGroup, 0, 8, 1, 1, HPos.RIGHT, VPos.CENTER);

        signUpForm.getChildren().addAll(signUpActionBtnGroup);

        signUpFormScrollWrapper.setContent(signUpForm);
        signUpFormScrollWrapper.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        signUpFormScrollWrapper.setFitToHeight(true);
        signUpFormScrollWrapper.setFitToWidth(true);

        // Add elements to the root
        sceneRoot.setTop(topBar);
        sceneRoot.setCenter(signUpFormScrollWrapper);

        root.getChildren().addAll(sceneRoot);
    }
    public StackPane getRoot() {
        return root;
    }
}
