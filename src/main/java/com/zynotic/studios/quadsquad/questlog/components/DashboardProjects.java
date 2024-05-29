package com.zynotic.studios.quadsquad.questlog.components;

import javafx.scene.layout.VBox;

public class DashboardProjects {
    VBox projectsSection;

    public DashboardProjects(String section) {
        projectsSection = new VBox();

        projectsSection.getChildren().addAll();
    }

    public VBox getProjectsSection() {
        return projectsSection;
    }
}
