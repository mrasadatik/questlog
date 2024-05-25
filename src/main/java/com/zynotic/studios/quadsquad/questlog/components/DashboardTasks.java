package com.zynotic.studios.quadsquad.questlog.components;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class DashboardTasks {
    VBox taskSection;

    public DashboardTasks() {
        taskSection = new VBox();
        TaskCard task = new TaskCard("aaaaa");

        taskSection.getChildren().addAll(task.getTask());
    }

    public VBox getTaskSection() {
        return taskSection;
    }
}
