package com.zynotic.studios.quadsquad.questlog;

import atlantafx.base.theme.CupertinoLight;

import com.zynotic.studios.quadsquad.questlog.components.About;
import com.zynotic.studios.quadsquad.questlog.scene.LandingScene;
import com.zynotic.studios.quadsquad.questlog.scene.SignInScene;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Main class for the QuestLog application.
 */
public class QuestLog extends Application {
    private static Stage primaryStage;

    /**
     * Initializes the application.
     *
     * @throws Exception if an error occurs during initialization.
     */
    @Override
    public void init() throws Exception {
        // Simulate some initialization time
        Thread.sleep(2000);
    }

    /**
     * Starts the application.
     *
     * @param primaryStage the primary stage of the application.
     */
    @Override
    public void start(Stage primaryStage) {
        QuestLog.primaryStage = primaryStage;
        // Set application user agent stylesheet
        Application.setUserAgentStylesheet(new CupertinoLight().getUserAgentStylesheet());

        // Get application properties
        String APP_NAME = About.getAppName();

        // Set stage properties
        primaryStage.setTitle(APP_NAME);
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/images/logo/questlog/icons/dark/icon16s.png"))));
        primaryStage.setWidth(4*200);
        primaryStage.setHeight(3*200);
        primaryStage.setMinWidth(320);

        // Set the stage position to be centered
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        double centerX = bounds.getMinX() + (bounds.getWidth() - primaryStage.getWidth()) / 2;
        double centerY = bounds.getMinY() + (bounds.getHeight() - primaryStage.getHeight()) / 2;
        primaryStage.setX(centerX);
        primaryStage.setY(centerY);

        // Create scene
        viewLandingScene();

        primaryStage.show();

    }

    public static void viewLandingScene() {
        LandingScene landingScene = new LandingScene();
        Scene scene = new Scene(landingScene.getRoot());
        scene.getStylesheets().add(Objects.requireNonNull(LandingScene.class.getResource("LandingScene/style.css")).toExternalForm());
        primaryStage.setScene(scene);
    }

    public static void viewSignInScene() {
        SignInScene signInScene = new SignInScene();
        Scene scene = new Scene(signInScene.getRoot());
        scene.getStylesheets().add(Objects.requireNonNull(SignInScene.class.getResource("SignInScene/style.css")).toExternalForm());
        primaryStage.setScene(scene);
    }
}
