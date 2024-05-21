package com.zynotic.studios.quadsquad.questlog.utils;

import javafx.scene.layout.VBox;

public class Dialog extends VBox {

    public Dialog(int width, int height) {
        super();
        setSpacing(10);
        setMinSize(width, height);
        setMaxSize(width, height);
        setStyle("-fx-background-color: -color-bg-default;");
    }
}