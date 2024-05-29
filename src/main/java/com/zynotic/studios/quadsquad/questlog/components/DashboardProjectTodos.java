package com.zynotic.studios.quadsquad.questlog.components;

import javafx.scene.layout.VBox;

public class DashboardProjectTodos {
    VBox projectTodosSection;

    public DashboardProjectTodos(int projectId) {
        projectTodosSection = new VBox();
        // TaskCard task = new TaskCard("aaaaa");

        // projectTodosSection.getChildren().addAll(task.getTask());
    }

    public VBox getProjectTodosSection() {
        return projectTodosSection;
    }
}
