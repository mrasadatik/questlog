package com.zynotic.studios.quadsquad.questlog.components;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class DashboardToday {
    VBox taskSection;

    public DashboardToday() {
        taskSection = new VBox();
        TaskCard task = new TaskCard("today");

        taskSection.getChildren().addAll(task.getTask());
    }

    public VBox getTodaySection() {
        return taskSection;
    }
}
