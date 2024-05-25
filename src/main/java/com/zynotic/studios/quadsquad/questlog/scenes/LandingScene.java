package com.zynotic.studios.quadsquad.questlog.scenes;

import atlantafx.base.controls.ModalPane;
import atlantafx.base.theme.Styles;
import atlantafx.base.util.BBCodeParser;
import com.zynotic.studios.quadsquad.questlog.QuestLog;
import com.zynotic.studios.quadsquad.questlog.components.About;
import com.zynotic.studios.quadsquad.questlog.components.Dialog;
import javafx.geometry.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2OutlinedAL;
import org.kordamp.ikonli.material2.Material2OutlinedMZ;

import java.io.IOException;

public class LandingScene {
    private final StackPane root;

    public LandingScene() {
        final String APP_NAME = About.getAppName();
        final String ROOT_TEAM = About.getRootTeam();
        final String PROJECT_GROUP = About.getProjectGroup();

        root = new StackPane();
        BorderPane sceneRoot = new BorderPane();
        VBox logoBox = new VBox();
        HBox authPreGateBox = new HBox();
        VBox logoAndAuthPreGateWrapperBox = new VBox();
        VBox copyrightTextBox = new VBox();
        Dialog aboutDialog = new Dialog(650, 450);
        GridPane bottomBar = new GridPane();
        ModalPane aboutModalPane = About.getAboutModal(aboutDialog);
        ScrollPane logoAndAuthPreGateScrollWrapper = new ScrollPane();

        ImageView startupLogo = new ImageView(new Image("/assets/images/logo/questlog/questlog--horizontal--light.png"));
        startupLogo.setFitHeight(100);
        startupLogo.setPreserveRatio(true);

        Button signInBtn = new Button("Sign In", new FontIcon(Material2OutlinedAL.LOG_IN));
        signInBtn.setMnemonicParsing(true);
        signInBtn.setOnAction(e -> QuestLog.viewSignInScene());

        Button signUpBtn = new Button("Get Started", new FontIcon(Material2OutlinedMZ.PERSON_ADD_ALT_1));
        signUpBtn.setDefaultButton(true);
        signUpBtn.setMnemonicParsing(true);
        signUpBtn.setOnAction(e -> {
            try {
                QuestLog.viewSignUpScene();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        logoBox.getChildren().addAll(startupLogo);
        logoBox.setAlignment(Pos.CENTER);

        authPreGateBox.getChildren().addAll(signInBtn, signUpBtn);
        authPreGateBox.setSpacing(5);
        authPreGateBox.setAlignment(Pos.CENTER);

        logoAndAuthPreGateWrapperBox.getChildren().addAll(logoBox, authPreGateBox, aboutModalPane);
        logoAndAuthPreGateWrapperBox.setSpacing(50);
        logoAndAuthPreGateWrapperBox.setAlignment(Pos.CENTER);

        TextFlow copyrightText = BBCodeParser.createFormattedText("[b]" + APP_NAME + "[/b] © 2024 by [b]" + PROJECT_GROUP + "[/b] in collaboration with [b]" + ROOT_TEAM + "[/b].");
        copyrightText.getStyleClass().addAll(Styles.TEXT_SMALL);
        copyrightText.setTextAlignment(TextAlignment.CENTER);

        copyrightTextBox.getChildren().addAll(copyrightText);
        copyrightTextBox.setMaxWidth(500);
        copyrightTextBox.setAlignment(Pos.CENTER);

        Button aboutDialogOpenBtn = new Button(null, new FontIcon(Material2OutlinedAL.INFO));
        aboutDialogOpenBtn.getStyleClass().addAll(Styles.ROUNDED, Styles.BUTTON_ICON);
        aboutDialogOpenBtn.setOnAction(evt -> aboutModalPane.show(aboutDialog));

        bottomBar.add(aboutDialogOpenBtn, 0, 0);
        bottomBar.add(copyrightTextBox, 1, 0);
        bottomBar.setHgap(10);
        bottomBar.setVgap(10);
        bottomBar.setPadding(new Insets(10));

        logoAndAuthPreGateScrollWrapper.setContent(logoAndAuthPreGateWrapperBox);
        logoAndAuthPreGateScrollWrapper.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        logoAndAuthPreGateScrollWrapper.setFitToHeight(true);
        logoAndAuthPreGateScrollWrapper.setFitToWidth(true);

        // Add elements to the root
        sceneRoot.setCenter(logoAndAuthPreGateScrollWrapper);
        sceneRoot.setBottom(bottomBar);

        root.getChildren().addAll(sceneRoot, aboutModalPane);
    }
    public StackPane getRoot() {
        return root;
    }
}
