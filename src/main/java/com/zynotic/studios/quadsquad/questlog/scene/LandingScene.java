package com.zynotic.studios.quadsquad.questlog.scene;

import atlantafx.base.controls.ModalPane;
import atlantafx.base.controls.Tile;
import atlantafx.base.theme.Styles;
import atlantafx.base.util.BBCodeParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zynotic.studios.quadsquad.questlog.QuestLog;
import com.zynotic.studios.quadsquad.questlog.config.AppConfig;
import com.zynotic.studios.quadsquad.questlog.model.UserPhoneNumber;
import com.zynotic.studios.quadsquad.questlog.utils.Dialog;
import com.zynotic.studios.quadsquad.questlog.utils.OpenLink;
import javafx.event.ActionEvent;
import javafx.geometry.*;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2OutlinedAL;
import org.kordamp.ikonli.material2.Material2OutlinedMZ;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LandingScene {
    private final StackPane root;

    public LandingScene() {
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
        VBox logoBox = new VBox();
        VBox aboutDialogContentBox = new VBox();
        VBox aboutDialogCloseBar = new VBox();
        HBox authPreGateBox = new HBox();
        GridPane aboutGridProjectTable = new GridPane();
        GridPane aboutGridCourseTable = new GridPane();
        VBox projectGroupBox = new VBox();
        GridPane projectGroupMembersRow = new GridPane();
        VBox logoAndAuthPreGateWrapperBox = new VBox();
        VBox copyrightTextBox = new VBox();
        GridPane bottomBar = new GridPane();
        ModalPane aboutModalPane = new ModalPane();
        ScrollPane aboutDialogScrollContainer = new ScrollPane();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode projectGroupMembersJsonRoot = objectMapper.readTree(PROJECT_GROUP_MEMBERS);
            JsonNode projectGroupMembersJson = projectGroupMembersJsonRoot.get("members");
            List<JsonNode> sortedProjectGroupMembersJson = new ArrayList<>();
            projectGroupMembersJson.elements().forEachRemaining(sortedProjectGroupMembersJson::add);
            sortedProjectGroupMembersJson.sort(Comparator.comparingInt(member -> member.get("order").asInt()));
            for (int i = 0; i < sortedProjectGroupMembersJson.size(); ++i) {
                String name = sortedProjectGroupMembersJson.get(i).get("name").asText();
                String studentID = sortedProjectGroupMembersJson.get(i).get("student_id").asText();
                String personalEmail = sortedProjectGroupMembersJson.get(i).get("personal_email").asText();
                String institutionEmail = studentID + "@" + INSTITUTION_DOMAIN_STUDENT;
                JsonNode phoneNumberJson = sortedProjectGroupMembersJson.get(i).get("phone_number");
                UserPhoneNumber phoneNumber = new UserPhoneNumber(phoneNumberJson.get("country_code").asText(), phoneNumberJson.get("country_calling_code").asText(), phoneNumberJson.get("national_number").asText());

                String memberDescription = String.format("""
                                [b]Student ID:[/b] %s
                                [b]Institution Email:[/b] [url=mailto:%s]%s[/url]
                                [b]Personal Email:[/b] [url=mailto:%s]%s[/url]
                                [b]Phone Number:[/b] [url=tel:%s]%s[/url]
                                """,
                        studentID,
                        institutionEmail,
                        institutionEmail,
                        personalEmail,
                        personalEmail,
                        phoneNumber.getInternationalNumber(),
                        phoneNumber.getInternationalNumber());

                ImageView profile = new ImageView(new Image("/assets/images/members/" + institutionEmail + ".jpg"));
                profile.setFitWidth(64);
                profile.setFitHeight(64);
                Tile projectGroupMemberTile = new Tile(name, memberDescription);
                projectGroupMemberTile.addEventFilter(ActionEvent.ACTION, e -> {
                    if (e.getTarget() instanceof Hyperlink link) {
                        try {
                            OpenLink.openInBrowser(link.getUserData().toString());
                        } catch (Exception ex) {
                            //
                        }
                    }
                    e.consume();
                });
                projectGroupMemberTile.setGraphic(profile);
                projectGroupMembersRow.addRow(i, projectGroupMemberTile);
            }
        } catch (IOException e) {
            // e.printStackTrace();
        }

        ImageView groupLogo = new ImageView(new Image("/assets/images/logo/quadsquad/quadsquad--light.png"));
        groupLogo.setFitWidth(64);
        groupLogo.setFitHeight(64);
        Tile projectGroupNameTile = new Tile(PROJECT_GROUP, null);
        projectGroupNameTile.setGraphic(groupLogo);

        ImageView zynoticStudiosLogo = new ImageView(new Image("/assets/images/logo/zynoticstudios/zynoticstudios--square--light.jpg"));
        zynoticStudiosLogo.setFitWidth(64);
        zynoticStudiosLogo.setFitHeight(64);
        Tile projectCollaborationTile = new Tile(ROOT_TEAM, String.format("""
                [b]Parent:[/b] %s [i]a.k.a.[/i] %s
                """, ROOT_TEAM_PARENT, ROOT_TEAM_PARENT_AKA));
        projectCollaborationTile.setGraphic(zynoticStudiosLogo);

        ImageView supervisor = new ImageView(new Image("/assets/images/peoples/supervisor.jpg"));
        supervisor.setFitWidth(64);
        supervisor.setFitHeight(64);
        Tile projectSupervisorTile = new Tile(PROJECT_SUPERVISOR, String.format("""
                [b]Faculty:[/b] %s
                [b]Title:[/b] %s
                [b]Department:[/b] %s
                [b]Institution:[/b] %s
                [b]Contact:[/b] [url=mailto:%s]%s[/url]
                """,
                PROJECT_SUPERVISOR_FACULTY,
                PROJECT_SUPERVISOR_TITLE,
                PROJECT_SUPERVISOR_DEPARTMENT,
                PROJECT_SUPERVISOR_INSTITUTION,
                PROJECT_SUPERVISOR_EMAIL_OFFICIAL,
                PROJECT_SUPERVISOR_EMAIL_OFFICIAL));
        projectSupervisorTile.addEventFilter(ActionEvent.ACTION, e -> {
            if (e.getTarget() instanceof Hyperlink link) {
                try {
                    OpenLink.openInBrowser(link.getUserData().toString());
                } catch (Exception ex) {
                    //
                }
            }
            e.consume();
        });
        projectSupervisorTile.setGraphic(supervisor);

        Text projectGroupMembersKeyWrapper = new Text("Members");
        projectGroupMembersKeyWrapper.getStyleClass().addAll(Styles.TEXT_CAPTION);
        Text projectGroupCollaborationKeyWrapper = new Text("Collaboration");
        projectGroupCollaborationKeyWrapper.getStyleClass().addAll(Styles.TEXT_CAPTION);
        Text projectGroupSupervisorKeyWrapper = new Text("Supervisor");
        projectGroupSupervisorKeyWrapper.getStyleClass().addAll(Styles.TEXT_CAPTION);

        projectGroupBox.getChildren().addAll(
                projectGroupNameTile,
                projectGroupMembersKeyWrapper,
                projectGroupMembersRow,
                projectGroupCollaborationKeyWrapper,
                projectCollaborationTile,
                projectGroupSupervisorKeyWrapper,
                projectSupervisorTile
        );

        aboutModalPane.setId("aboutModal");
        aboutModalPane.displayProperty().addListener((obs, old, val) -> {
            if (!val) {
                aboutModalPane.setAlignment(Pos.CENTER);
                aboutModalPane.usePredefinedTransitionFactories(null);
            }
        });

        ImageView startupLogo = new ImageView(new Image("/assets/images/logo/questlog/questlog--horizontal--light.png"));
        startupLogo.setFitHeight(100);
        startupLogo.setPreserveRatio(true);

        Button signInBtn = new Button("Sign In", new FontIcon(Material2OutlinedAL.LOG_IN));
        signInBtn.setMnemonicParsing(true);
        signInBtn.setOnAction(e -> QuestLog.viewSignInScene());

        Button signUpBtn = new Button("Sign Up", new FontIcon(Material2OutlinedMZ.PERSON_ADD_ALT_1));
        signUpBtn.setDefaultButton(true);
        signUpBtn.setMnemonicParsing(true);

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

        Dialog aboutDialog = new Dialog(650, 450);

        Button aboutDialogOpenBtn = new Button(null, new FontIcon(Material2OutlinedAL.INFO));
        aboutDialogOpenBtn.getStyleClass().addAll(Styles.ROUNDED);
        aboutDialogOpenBtn.setOnAction(evt -> aboutModalPane.show(aboutDialog));

        Button aboutDialogCloseBtn = new Button(null, new FontIcon(Material2OutlinedAL.CLOSE));
        aboutDialogCloseBtn.getStyleClass().addAll(Styles.ROUNDED);
        aboutDialogCloseBtn.setOnAction(evt -> aboutModalPane.hide(true));

        aboutDialogCloseBar.getChildren().addAll(aboutDialogCloseBtn);
        aboutDialogCloseBar.setAlignment(Pos.CENTER_RIGHT);
        aboutDialogCloseBar.setPadding(new Insets(10, 10, 0, 10));

        Text projectTitleKey = new Text("Project title:");
        projectTitleKey.getStyleClass().addAll(Styles.TEXT_CAPTION);
        Text projectNameKey = new Text("Project name:");
        projectNameKey.getStyleClass().addAll(Styles.TEXT_CAPTION);
        Text projectCategoryKey = new Text("Project category:");
        projectCategoryKey.getStyleClass().addAll(Styles.TEXT_CAPTION);
        aboutGridProjectTable.addRow(0, projectTitleKey, new Text(PROJECT_TITLE));
        aboutGridProjectTable.addRow(1, projectNameKey, new Text(APP_NAME));
        aboutGridProjectTable.addRow(2, projectCategoryKey, new Text(PROJECT_CATEGORY));
        Text projectGroupKey = new Text("Project group:");
        projectGroupKey.getStyleClass().addAll(Styles.TEXT_CAPTION);
        GridPane.setConstraints(projectGroupKey, 0, 3, 1, 1, HPos.LEFT, VPos.TOP);
        GridPane.setConstraints(projectGroupBox, 1, 3, 1, 1, HPos.LEFT, VPos.TOP);
        aboutGridProjectTable.getChildren().addAll(projectGroupKey, projectGroupBox);

        aboutGridProjectTable.setVgap(10);
        aboutGridProjectTable.setHgap(10);

        Text courseTitleKey = new Text("Course title:");
        courseTitleKey.getStyleClass().addAll(Styles.TEXT_CAPTION);
        Text courseCodeKey = new Text("Course code:");
        courseCodeKey.getStyleClass().addAll(Styles.TEXT_CAPTION);
        Text courseSectionKey = new Text("Course section:");
        courseSectionKey.getStyleClass().addAll(Styles.TEXT_CAPTION);
        Text semesterKey = new Text("Semester:");
        semesterKey.getStyleClass().addAll(Styles.TEXT_CAPTION);
        Text courseInstructorKey = new Text("Instructor:");
        courseInstructorKey.getStyleClass().addAll(Styles.TEXT_CAPTION);
        Text institutionKey = new Text("Institution:");
        institutionKey.getStyleClass().addAll(Styles.TEXT_CAPTION);

        ImageView instructor = new ImageView(new Image("/assets/images/peoples/instructor.jpg"));
        instructor.setFitWidth(64);
        instructor.setFitHeight(64);
        Tile projectInstructorTile = new Tile(COURSE_INSTRUCTOR, String.format("""
                [b]Faculty:[/b] %s
                [b]Title:[/b] %s
                [b]Department:[/b] %s
                [b]Institution:[/b] %s
                [b]Contact:[/b] [url=mailto:%s]%s[/url]
                """,
                COURSE_INSTRUCTOR_FACULTY,
                COURSE_INSTRUCTOR_TITLE,
                COURSE_INSTRUCTOR_DEPARTMENT,
                COURSE_INSTRUCTOR_INSTITUTION,
                COURSE_INSTRUCTOR_EMAIL_OFFICIAL,
                PROJECT_SUPERVISOR_EMAIL_OFFICIAL));
        projectInstructorTile.setGraphic(instructor);

        ImageView institutionLogo = new ImageView(new Image("/assets/images/logo/ewu/ewu-logo-art-square.jpg"));
        institutionLogo.setFitWidth(64);
        institutionLogo.setFitHeight(64);
        Tile institutionTile = new Tile(INSTITUTION, String.format("""
                [b]Website:[/b] [url=%s]%s[/url]
                """,
                INSTITUTION_DOMAIN,
                INSTITUTION_DOMAIN));
        institutionTile.setGraphic(institutionLogo);

        GridPane.setConstraints(courseInstructorKey, 0, 4, 1, 1, HPos.LEFT, VPos.TOP);
        GridPane.setConstraints(projectInstructorTile, 1, 4, 1, 1, HPos.LEFT, VPos.TOP);

        GridPane.setConstraints(institutionKey, 0, 5, 1, 1, HPos.LEFT, VPos.TOP);
        GridPane.setConstraints(institutionTile, 1, 5, 1, 1, HPos.LEFT, VPos.TOP);

        aboutGridCourseTable.addRow(0, courseTitleKey, new Text(COURSE_TITLE));
        aboutGridCourseTable.addRow(1, courseCodeKey, new Text(COURSE_CODE));
        aboutGridCourseTable.addRow(2, courseSectionKey, new Text(COURSE_SECTION));
        aboutGridCourseTable.addRow(3, semesterKey, new Text(COURSE_SEMESTER));
        aboutGridCourseTable.getChildren().addAll(courseInstructorKey, projectInstructorTile);
        aboutGridCourseTable.getChildren().addAll(institutionKey, institutionTile);

        aboutGridCourseTable.setVgap(10);
        aboutGridCourseTable.setHgap(10);

        Text about = new Text("This project involves developing a task management application. Created by a student group for a  course " + COURSE_CODE + " at " + INSTITUTION + ", the application aims to  improve productivity.");
        about.setWrappingWidth(550);

        aboutDialogContentBox.getChildren().addAll(new VBox(about), new Separator(Orientation.HORIZONTAL), aboutGridProjectTable, new Separator(Orientation.HORIZONTAL), aboutGridCourseTable);
        aboutDialogContentBox.setSpacing(10);
        aboutDialogContentBox.setPadding(new Insets(0, 10, 10, 10));
        aboutDialogScrollContainer.setContent(aboutDialogContentBox);
        aboutDialogScrollContainer.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        aboutDialog.getChildren().setAll(aboutDialogCloseBar, aboutDialogScrollContainer);

        bottomBar.add(aboutDialogOpenBtn, 0, 0);
        bottomBar.add(copyrightTextBox, 1, 0);
        bottomBar.setHgap(10);
        bottomBar.setVgap(10);
        bottomBar.setPadding(new Insets(10));

        // Add elements to the root
        sceneRoot.setCenter(logoAndAuthPreGateWrapperBox);
        sceneRoot.setBottom(bottomBar);

        root.getChildren().addAll(sceneRoot, aboutModalPane);
    }
    public StackPane getRoot() {
        return root;
    }
}
