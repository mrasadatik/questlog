package com.zynotic.studios.quadsquad.questlog.scenes;

import atlantafx.base.theme.CupertinoLight;

import javafx.application.Application;
import javafx.application.Preloader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

import static com.zynotic.studios.quadsquad.questlog.configs.AppConfig.getRequiredApplicationProperty;

/**
 * Preloader for the application splash screen.
 */
public class SplashScreenPreloader extends Preloader {

    private Stage preloaderStage;
    private ProgressIndicator progressIndicator;

    /**
     * Starts the splash screen preloader.
     * @param splashStage The stage for the splash screen.
     */
    @Override
    public void start(Stage splashStage) {
        // Set the application's user agent stylesheet
        Application.setUserAgentStylesheet(new CupertinoLight().getUserAgentStylesheet());

        // Get the application name from properties
        String APP_NAME = getRequiredApplicationProperty("APP_NAME");

        this.preloaderStage = splashStage;

        // Set stage properties
        splashStage.initStyle(StageStyle.TRANSPARENT);
        splashStage.setTitle(APP_NAME);
        splashStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/images/logo/questlog/icons/dark/icon16s.png"))));

        // Set the stage position to be centered
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        double centerX = bounds.getMinX() + (bounds.getWidth() - 4*160) / 2;
        double centerY = bounds.getMinY() + (bounds.getHeight() - 3*160) / 2;
        splashStage.setX(centerX);
        splashStage.setY(centerY);

        // Create splash image and progress bar
        ImageView splashImage = new ImageView(new Image("/assets/images/graphics/splashv1.png"));
        splashImage.setFitWidth(4*160);
        splashImage.setFitHeight(3*160);
        splashImage.setPreserveRatio(true);
        progressIndicator = new ProgressIndicator();
        progressIndicator.setMinSize(50, 50);
        progressIndicator.getStyleClass().add("splashProgress");

        // Create root layout
        StackPane root = new StackPane();
        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("splashRootLayout");

        root.getChildren().addAll(splashImage, progressIndicator);

        // Create and configure scene
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);

        scene.getStylesheets().add(Objects.requireNonNull(SplashScreenPreloader.class.getResource("SplashScreenPreloader/style.css")).toExternalForm());

        // Set scene to stage and show stage
        splashStage.setScene(scene);
        splashStage.show();
    }

    /**
     * Handles progress notification.
     * @param info The progress notification.
     */
    @Override
    public void handleApplicationNotification(PreloaderNotification info) {
        if (info instanceof ProgressNotification) {
            progressIndicator.setProgress(((ProgressNotification) info).getProgress());
        }
    }

    /**
     * Handles state change notification.
     * @param info The state change notification.
     */
    @Override
    public void handleStateChangeNotification(StateChangeNotification info) {
        if (info.getType() == StateChangeNotification.Type.BEFORE_START) {
            preloaderStage.hide();
        }
    }
}
