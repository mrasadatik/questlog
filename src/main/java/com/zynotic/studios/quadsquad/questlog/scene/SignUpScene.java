package com.zynotic.studios.quadsquad.questlog.scene;

import atlantafx.base.controls.CustomTextField;
import atlantafx.base.theme.Styles;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2OutlinedAL;
import org.kordamp.ikonli.material2.Material2OutlinedMZ;

public class SignUpScene {
    private final StackPane root;

    public SignUpScene() {
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

        ImageView signUpIcon = new ImageView(new Image("/assets/images/graphics/icons/sign_in.png"));
        signUpIcon.setFitHeight(100);
        signUpIcon.setPreserveRatio(true);

        Text signUpHeading = new Text("Sign In");
        signUpHeading.getStyleClass().addAll(Styles.TITLE_2);

        signUpFormHeader.getChildren().addAll(signUpIcon, signUpHeading);

        CustomTextField username = new CustomTextField();
        username.setPromptText("Username/Email/Phone number");
        username.setLeft(new FontIcon(Material2OutlinedMZ.PERSON_ADD_ALT_1));
        username.setPrefWidth(250);

        CustomTextField password = new CustomTextField();
        password.setPromptText("Password");
        password.setLeft(new FontIcon(Material2OutlinedAL.LOCK));
        password.setPrefWidth(250);

        Button createAccountBtn = new Button("Create account");
        createAccountBtn.getStyleClass().addAll(Styles.FLAT);
        createAccountBtn.setMnemonicParsing(true);

        Button signUpBtn = new Button("Sign Up", new FontIcon(Material2OutlinedAL.LOG_IN));
        signUpBtn.getStyleClass().addAll(Styles.SUCCESS);
        signUpBtn.setMnemonicParsing(true);

        signUpActionBtnGroup.setSpacing(10);
        signUpActionBtnGroup.setAlignment(Pos.CENTER_RIGHT);
        signUpActionBtnGroup.getChildren().addAll(createAccountBtn, signUpBtn);

        signUpForm.addRow(0, signUpFormHeader);
        signUpForm.addRow(1, username);
        signUpForm.addRow(2, password);
        GridPane.setConstraints(signUpActionBtnGroup, 0, 3, 1, 1, HPos.RIGHT, VPos.CENTER);

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
