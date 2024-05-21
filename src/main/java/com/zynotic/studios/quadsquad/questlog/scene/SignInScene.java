package com.zynotic.studios.quadsquad.questlog.scene;

import com.zynotic.studios.quadsquad.questlog.config.AppConfig;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

public class SignInScene {
    private final StackPane root;

    public SignInScene() {
        final String APP_NAME = AppConfig.getRequiredApplicationProperty("APP_NAME");
        final String ROOT_TEAM = AppConfig.getRequiredApplicationProperty("ROOT_TEAM");
        final String ROOT_TEAM_PARENT = AppConfig.getRequiredApplicationProperty("ROOT_TEAM_PARENT");
        final String ROOT_TEAM_PARENT_AKA = AppConfig.getRequiredApplicationProperty("ROOT_TEAM_PARENT_AKA");
        final String PROJECT_TITLE = AppConfig.getRequiredApplicationProperty("PROJECT_TITLE");
        final String PROJECT_REPO = AppConfig.getRequiredApplicationProperty("PROJECT_REPO");
        final String PROJECT_CATEGORY = AppConfig.getRequiredApplicationProperty("PROJECT_CATEGORY");
        final String COURSE_CODE = AppConfig.getRequiredApplicationProperty("COURSE_CODE");
        final String COURSE_TITLE = AppConfig.getRequiredApplicationProperty("COURSE_TITLE");
        final String COURSE_SEMESTER = AppConfig.getRequiredApplicationProperty("COURSE_SEMESTER");
        final String COURSE_SECTION = AppConfig.getRequiredApplicationProperty("COURSE_SECTION");
        final String COURSE_INSTRUCTOR = AppConfig.getRequiredApplicationProperty("COURSE_INSTRUCTOR");
        final String COURSE_INSTRUCTOR_INITIAL = AppConfig.getRequiredApplicationProperty("COURSE_INSTRUCTOR_INITIAL");
        final String COURSE_INSTRUCTOR_EMAIL_OFFICIAL = AppConfig.getRequiredApplicationProperty("COURSE_INSTRUCTOR_EMAIL_OFFICIAL");
        final String COURSE_INSTRUCTOR_INSTITUTION = AppConfig.getRequiredApplicationProperty("COURSE_INSTRUCTOR_INSTITUTION");
        final String COURSE_INSTRUCTOR_INSTITUTION_INITIAL = AppConfig.getRequiredApplicationProperty("COURSE_INSTRUCTOR_INSTITUTION_INITIAL");
        final String COURSE_INSTRUCTOR_FACULTY = AppConfig.getRequiredApplicationProperty("COURSE_INSTRUCTOR_FACULTY");
        final String COURSE_INSTRUCTOR_TITLE = AppConfig.getRequiredApplicationProperty("COURSE_INSTRUCTOR_TITLE");
        final String COURSE_INSTRUCTOR_DEPARTMENT = AppConfig.getRequiredApplicationProperty("COURSE_INSTRUCTOR_DEPARTMENT");
        final String PROJECT_GROUP = AppConfig.getRequiredApplicationProperty("PROJECT_GROUP");
        final String PROJECT_GROUP_MEMBERS = AppConfig.getRequiredApplicationProperty("PROJECT_GROUP_MEMBERS");
        final String PROJECT_SUPERVISOR = AppConfig.getRequiredApplicationProperty("PROJECT_SUPERVISOR");
        final String PROJECT_SUPERVISOR_INSTITUTION = AppConfig.getRequiredApplicationProperty("PROJECT_SUPERVISOR_INSTITUTION");
        final String PROJECT_SUPERVISOR_INSTITUTION_INITIAL = AppConfig.getRequiredApplicationProperty("PROJECT_SUPERVISOR_INSTITUTION_INITIAL");
        final String PROJECT_SUPERVISOR_INITIAL = AppConfig.getRequiredApplicationProperty("PROJECT_SUPERVISOR_INITIAL");
        final String PROJECT_SUPERVISOR_EMAIL_OFFICIAL = AppConfig.getRequiredApplicationProperty("PROJECT_SUPERVISOR_EMAIL_OFFICIAL");
        final String PROJECT_SUPERVISOR_FACULTY = AppConfig.getRequiredApplicationProperty("PROJECT_SUPERVISOR_FACULTY");
        final String PROJECT_SUPERVISOR_TITLE = AppConfig.getRequiredApplicationProperty("PROJECT_SUPERVISOR_TITLE");
        final String PROJECT_SUPERVISOR_DEPARTMENT = AppConfig.getRequiredApplicationProperty("PROJECT_SUPERVISOR_DEPARTMENT");
        final String INSTITUTION = AppConfig.getRequiredApplicationProperty("INSTITUTION");
        final String INSTITUTION_INITIAL = AppConfig.getRequiredApplicationProperty("INSTITUTION_INITIAL");
        final String INSTITUTION_DOMAIN = AppConfig.getRequiredApplicationProperty("INSTITUTION_DOMAIN");
        final String INSTITUTION_DOMAIN_STUDENT = AppConfig.getRequiredApplicationProperty("INSTITUTION_DOMAIN_STUDENT");

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
