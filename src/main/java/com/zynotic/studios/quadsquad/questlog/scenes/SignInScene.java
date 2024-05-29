package com.zynotic.studios.quadsquad.questlog.scenes;

import atlantafx.base.controls.CustomTextField;
import atlantafx.base.controls.PasswordTextField;
import atlantafx.base.theme.Styles;
import com.zynotic.studios.quadsquad.questlog.QuestLog;
import com.zynotic.studios.quadsquad.questlog.entities.User;
import com.zynotic.studios.quadsquad.questlog.services.DataService;
import com.zynotic.studios.quadsquad.questlog.utils.SessionManager;
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
import net.synedra.validatorfx.Validator;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2OutlinedAL;
import org.kordamp.ikonli.material2.Material2OutlinedMZ;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.zynotic.studios.quadsquad.questlog.validation.SignInInputValidation.signInFormDecorator;
import static com.zynotic.studios.quadsquad.questlog.validation.SignInInputValidation.validatorUsername;
import static com.zynotic.studios.quadsquad.questlog.validation.SignInInputValidation.validatorPassword;

public class SignInScene {
    private final StackPane root;
    private DataService<User> usersService = new DataService<User>("database/users.json", User.class);

    private Validator validator = new Validator();

    private final AtomicBoolean touchedUsername = new AtomicBoolean(false);
    private final AtomicBoolean touchedPassword = new AtomicBoolean(false);

    public SignInScene(Stage primaryStage, SessionManager sessionManager) {
        root = new StackPane();
        BorderPane sceneRoot = new BorderPane();
        if (!sessionManager.isSignedIn()) {
            primaryStage.setTitle("QuestLog - Sign In");
            ScrollPane signInFormScrollWrapper = new ScrollPane();
            GridPane signInForm = new GridPane();
            HBox signInActionBtnGroup = new HBox();
            VBox signInFormHeader = new VBox();

            ImageView appLogo = new ImageView(new Image("/assets/images/logo/questlog/questlog--horizontal--light.png"));
            appLogo.setFitHeight(50);
            appLogo.setPreserveRatio(true);

            final ToolBar topBar = new ToolBar(
                    appLogo
            );

            signInForm.setVgap(10);
            signInForm.setHgap(10);
            signInForm.setPadding(new Insets(25));
            signInForm.setAlignment(Pos.CENTER);

            signInFormHeader.setSpacing(10);
            signInFormHeader.setPadding(new Insets(10));
            signInFormHeader.setAlignment(Pos.CENTER);

            ImageView signInIcon = new ImageView(new Image("/assets/images/graphics/icons/sign_in.png"));
            signInIcon.setFitHeight(50);
            signInIcon.setPreserveRatio(true);

            Text signInHeading = new Text("Sign In");
            signInHeading.getStyleClass().addAll(Styles.TITLE_2);

            signInFormHeader.getChildren().addAll(signInIcon, signInHeading);

            Label errorMessage = new Label(" ");

            CustomTextField usernameField = new CustomTextField();
            Label usernameLabel = new Label("Username");
            usernameLabel.setMaxWidth(280);
            usernameLabel.setLabelFor(usernameField);
            usernameField.setPromptText("Enter your username");
            usernameField.setLeft(new FontIcon(Material2OutlinedMZ.PERSON));
            usernameField.setPrefWidth(280);
            Text usernameErrorMessage = new Text(" ");
            usernameErrorMessage.setWrappingWidth(280);
            usernameErrorMessage.getStyleClass().addAll(Styles.TEXT);
            validator.createCheck()
                    .dependsOn("username", usernameField.textProperty())
                    .withMethod(c -> validatorUsername(c, touchedUsername))
                    .decoratingWith(m -> signInFormDecorator(m, new CustomTextField[]{usernameField}, null))
                    .decorates(usernameErrorMessage)
                    .immediate();

            PasswordTextField passwordField = new PasswordTextField();
            Label passwordLabel = new Label("Password");
            passwordLabel.setMaxWidth(280);
            passwordLabel.setLabelFor(passwordField);
            passwordField.setPromptText("Enter your password");
            passwordField.setLeft(new FontIcon(Material2OutlinedAL.LOCK));
            passwordField.setPrefWidth(280);
            Text passwordErrorMessage = new Text(" ");
            passwordErrorMessage.setWrappingWidth(280);
            passwordErrorMessage.getStyleClass().addAll(Styles.TEXT);
            validator.createCheck()
                    .dependsOn("password", passwordField.passwordProperty())
                    .withMethod(c -> validatorPassword(c, touchedPassword))
                    .decoratingWith(m -> signInFormDecorator(m, null, new PasswordTextField[]{passwordField}))
                    .decorates(passwordErrorMessage)
                    .immediate();

            FontIcon passwordToggleIcon= new FontIcon(Material2OutlinedMZ.VISIBILITY_OFF);
            passwordToggleIcon.setCursor(Cursor.HAND);
            passwordToggleIcon.setOnMouseClicked(e -> {
                passwordToggleIcon.setIconCode(passwordField.getRevealPassword() ? Material2OutlinedMZ.VISIBILITY_OFF : Material2OutlinedMZ.VISIBILITY);
                passwordField.setRevealPassword(!passwordField.getRevealPassword());
            });

            passwordField.setRight(passwordToggleIcon);

            Button createAccountBtn = new Button("Create account");
            createAccountBtn.getStyleClass().addAll(Styles.FLAT);
            createAccountBtn.setMnemonicParsing(true);
            createAccountBtn.setOnAction(e -> {
                try {
                    QuestLog.viewSignUpScene();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            Button signInBtn = new Button("Sign In", new FontIcon(Material2OutlinedAL.LOG_IN));
            signInBtn.getStyleClass().addAll(Styles.SUCCESS);
            signInBtn.setMnemonicParsing(true);
            signInBtn.setOnAction(e -> {
                validator.clear();

                boolean hasError = false;

                String username = usernameField.getText();
                String password = passwordField.getPassword();

                if (username.isBlank()) {
                    touchedUsername.set(true);
                    hasError = true;
                }

                if (password.isBlank()) {
                    touchedPassword.set(true);
                    hasError = true;
                }

                validator.validate();

                if (!hasError) {
                    signIn(username, password, sessionManager, usernameField, passwordField, passwordErrorMessage);
                }
            });

            signInActionBtnGroup.setSpacing(10);
            signInActionBtnGroup.setAlignment(Pos.CENTER_RIGHT);
            signInActionBtnGroup.getChildren().addAll(createAccountBtn, signInBtn);

            signInForm.addRow(0, signInFormHeader);
            signInForm.addRow(1, new VBox(5, usernameLabel, usernameField, usernameErrorMessage));
            signInForm.addRow(2, new VBox(5, passwordLabel, passwordField, passwordErrorMessage));
            GridPane.setConstraints(signInActionBtnGroup, 0, 3, 1, 1, HPos.RIGHT, VPos.CENTER);

            signInForm.getChildren().addAll(signInActionBtnGroup);

            signInFormScrollWrapper.setContent(signInForm);
            signInFormScrollWrapper.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            signInFormScrollWrapper.setFitToHeight(true);
            signInFormScrollWrapper.setFitToWidth(true);

            // Add elements to the root
            sceneRoot.setTop(topBar);
            sceneRoot.setCenter(signInFormScrollWrapper);
        } else {
            try {
                QuestLog.viewDashboardScene();
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }

        root.getChildren().addAll(sceneRoot);
    }
    public StackPane getRoot() {
        return root;
    }

    private void signIn(String username, String password, SessionManager sessionManager, Control fieldUsername, PasswordTextField fieldPassword, Text fieldError) {
        Optional<User> loginAttemptUserData = usersService.getDataByKeyValue("username", username);
        if (loginAttemptUserData.isPresent()) {
            User loginAttemptUser = loginAttemptUserData.get();
            if (loginAttemptUser.verifyPassword(password)) {
                try {
                    sessionManager.signInSuccess(loginAttemptUser);
                    validator.clear();
                    QuestLog.viewDashboardScene();
                } catch (IOException | URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            } else {
                validator.createCheck()
                        .withMethod(c -> c.error("Username or Password incorrect"))
                        .decoratingWith(m -> signInFormDecorator(m, new Control[]{fieldUsername}, new PasswordTextField[]{fieldPassword}))
                        .decorates(fieldError)
                        .immediate();
            }
        } else {
            validator.createCheck()
                    .withMethod(c -> c.error("Username or Password incorrect"))
                    .decoratingWith(m -> signInFormDecorator(m, new Control[]{fieldUsername}, new PasswordTextField[]{fieldPassword}))
                    .decorates(fieldError)
                    .immediate();
        }
    }
}
