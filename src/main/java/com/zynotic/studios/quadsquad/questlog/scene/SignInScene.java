package com.zynotic.studios.quadsquad.questlog.scene;

import javafx.scene.control.Button;
import javafx.scene.layout.*;

public class SignInScene {
    private final StackPane root;

    public SignInScene() {
        root = new StackPane();
        BorderPane sceneRoot = new BorderPane();


        Button ok = new Button("OK");

        // Add elements to the root
        sceneRoot.setCenter(ok);

        root.getChildren().addAll(sceneRoot);
    }
    public StackPane getRoot() {
        return root;
    }
}
