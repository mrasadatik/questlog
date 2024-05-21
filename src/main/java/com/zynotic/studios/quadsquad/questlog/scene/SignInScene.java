package com.zynotic.studios.quadsquad.questlog.scene;

import atlantafx.base.controls.CustomTextField;
import atlantafx.base.theme.Styles;
import com.zynotic.studios.quadsquad.questlog.QuestLog;
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

public class SignInScene {
    private final StackPane root;

    public SignInScene() {
        root = new StackPane();
        BorderPane sceneRoot = new BorderPane();
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
        signInIcon.setFitHeight(100);
        signInIcon.setPreserveRatio(true);

        Text signInHeading = new Text("Sign In");
        signInHeading.getStyleClass().addAll(Styles.TITLE_2);

        signInFormHeader.getChildren().addAll(signInIcon, signInHeading);

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
        createAccountBtn.setOnAction(e -> QuestLog.viewSignUpScene());

        Button signInBtn = new Button("Sign In", new FontIcon(Material2OutlinedAL.LOG_IN));
        signInBtn.getStyleClass().addAll(Styles.SUCCESS);
        signInBtn.setMnemonicParsing(true);

        signInActionBtnGroup.setSpacing(10);
        signInActionBtnGroup.setAlignment(Pos.CENTER_RIGHT);
        signInActionBtnGroup.getChildren().addAll(createAccountBtn, signInBtn);

        signInForm.addRow(0, signInFormHeader);
        signInForm.addRow(1, username);
        signInForm.addRow(2, password);
        GridPane.setConstraints(signInActionBtnGroup, 0, 3, 1, 1, HPos.RIGHT, VPos.CENTER);

        signInForm.getChildren().addAll(signInActionBtnGroup);

        signInFormScrollWrapper.setContent(signInForm);
        signInFormScrollWrapper.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        signInFormScrollWrapper.setFitToHeight(true);
        signInFormScrollWrapper.setFitToWidth(true);

        // Add elements to the root
        sceneRoot.setTop(topBar);
        sceneRoot.setCenter(signInFormScrollWrapper);

        root.getChildren().addAll(sceneRoot);
    }
    public StackPane getRoot() {
        return root;
    }
}
