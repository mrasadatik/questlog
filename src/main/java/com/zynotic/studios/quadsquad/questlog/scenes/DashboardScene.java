package com.zynotic.studios.quadsquad.questlog.scenes;

import atlantafx.base.controls.ModalPane;
import atlantafx.base.controls.Spacer;
import atlantafx.base.theme.Styles;
import atlantafx.base.theme.Tweaks;
import com.zynotic.studios.quadsquad.questlog.QuestLog;
import com.zynotic.studios.quadsquad.questlog.components.AddTask;
import com.zynotic.studios.quadsquad.questlog.components.DashboardProjects;
import com.zynotic.studios.quadsquad.questlog.components.DashboardTodos;
import com.zynotic.studios.quadsquad.questlog.components.Dialog;
import com.zynotic.studios.quadsquad.questlog.entities.Project;
import com.zynotic.studios.quadsquad.questlog.services.DataService;
import com.zynotic.studios.quadsquad.questlog.utils.SessionManager;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2OutlinedAL;
import org.kordamp.ikonli.material2.Material2OutlinedMZ;

import java.io.IOException;
import java.util.*;

public class DashboardScene {
    DataService<Project> projectService = new DataService<>("database/projects.json", Project.class);
    private final StackPane root;
    private final VBox dashboardContent = new VBox();
    private final Map<String, VBox> sections = new HashMap<>();
    private final Stage primaryStage;
    private final TreeItem<String> dashboardMenu;
    private int loadedProjectsCount = 0;
    private List<Project> loadedProjects = new ArrayList<Project>();
    SessionManager sessionManager;
    ModalPane addTaskModal = new ModalPane();

    public DashboardScene(Stage primaryStage, SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        this.primaryStage = primaryStage;
        root = new StackPane();
        dashboardMenu = new TreeItem<>();
        BorderPane sceneRoot = new BorderPane();
        Dialog addTaskDialog = new Dialog(450, 450);
        addTaskModal = AddTask.getAddTaskModal(addTaskDialog, sessionManager.getUser());
        if (sessionManager.isSignedIn()) {
            primaryStage.setTitle("QuestLog - Dashboard > Task");

            initializeSections();
            ScrollPane dashboardContentScrollWrapper = new ScrollPane();
            GridPane dashboardMenuContent = new GridPane();

            dashboardContent.setPadding(new Insets(25));
            dashboardContent.setAlignment(Pos.TOP_LEFT);

            dashboardContentScrollWrapper.setContent(dashboardContent);
            dashboardContentScrollWrapper.setFitToHeight(true);
            dashboardContentScrollWrapper.setFitToWidth(true);

            Button signOut = new Button("Sign Out", new FontIcon(Material2OutlinedAL.LOG_OUT));
            ToolBar dashboardMenuBottomBar = new ToolBar(new Spacer(80), signOut);
            signOut.setOnAction(e -> {
                sessionManager.signOut();
                QuestLog.viewSignInScene();
            });

            TreeItem<String> dashboardMenuItemTasks = new TreeItem<>("Tasks", new FontIcon(Material2OutlinedAL.LIST_ALT));
            TreeItem<String> dashboardMenuItemToday = new TreeItem<>("Today", new FontIcon(Material2OutlinedMZ.TODAY));
            TreeItem<String> dashboardMenuItemUpcoming = new TreeItem<>("Upcoming", new FontIcon(Material2OutlinedAL.CALENDAR_TODAY));
            TreeItem<String> dashboardMenuItemOverdue = new TreeItem<>("Overdue", new FontIcon(Material2OutlinedMZ.PENDING_ACTIONS));
            TreeItem<String> dashboardMenuItemCompleted = new TreeItem<>("Completed", new FontIcon(Material2OutlinedAL.CHECK_CIRCLE));

            TreeItem<String> dashboardMenuItemProjects = new TreeItem<>("Projects");

            dashboardMenuItemProjects.setExpanded(true);

            List<Project> projects = projectService.readData();
            for (Project project : projects) {
                if (Objects.equals(sessionManager.getUser().getUsername(), project.getBoundToUser())) {
                    loadedProjects.add(loadedProjectsCount, project);
                    dashboardMenuItemProjects.getChildren().add(loadedProjectsCount++, new TreeItem<>(project.getTitle(), new FontIcon(Material2OutlinedMZ.WORK)));
                }
            }

            dashboardMenu.setExpanded(true);
            dashboardMenu.getChildren().add(0, dashboardMenuItemTasks);
            dashboardMenu.getChildren().add(1, dashboardMenuItemToday);
            dashboardMenu.getChildren().add(2, dashboardMenuItemUpcoming);
            dashboardMenu.getChildren().add(3, dashboardMenuItemOverdue);
            dashboardMenu.getChildren().add(4, dashboardMenuItemCompleted);
            // dashboardMenu.getChildren().add(5, dashboardMenuItemProjects);

            TreeView<String> dashboardMenuWrapper = new TreeView<>(dashboardMenu);
            dashboardMenuWrapper.setShowRoot(false);
            dashboardMenuWrapper.setPrefHeight(Region.USE_COMPUTED_SIZE);
            dashboardMenuWrapper.setMinHeight(Region.USE_COMPUTED_SIZE);
            dashboardMenuWrapper.getStyleClass().addAll(Tweaks.EDGE_TO_EDGE);
            dashboardMenuWrapper.getSelectionModel().selectFirst();

            Button addTaskBtn = new Button("Add Task", new FontIcon(Material2OutlinedAL.ADD_TASK));
            addTaskBtn.getStyleClass().addAll(Styles.SUCCESS);
            addTaskBtn.setDefaultButton(true);
            addTaskBtn.setMnemonicParsing(true);
            addTaskBtn.setPrefWidth(180);
            ModalPane finalAddTaskModalPane = addTaskModal;
            addTaskBtn.setOnAction(evt -> finalAddTaskModalPane.show(addTaskDialog));

            VBox addTaskBtnWrapper = new VBox(addTaskBtn);
            addTaskBtnWrapper.setPadding(new Insets(10));
            addTaskBtnWrapper.setAlignment(Pos.CENTER);
            addTaskBtnWrapper.setStyle("-fx-background-color: -color-base-0");

            TextField projectNameField = new TextField();
            projectNameField.setPromptText("Enter project name");
            Button addProjectBtn = new Button("Add Project");
            addProjectBtn.setOnAction(e -> {
                String projectName = projectNameField.getText();
                if (!projectName.isEmpty()) {
                    addProject(projectName);
                    projectNameField.clear();
                }
            });

            VBox addProjectWrapper = new VBox(5, projectNameField, addProjectBtn);
            addProjectWrapper.setPadding(new Insets(10));
            addProjectWrapper.setAlignment(Pos.CENTER);

            GridPane.setConstraints(addTaskBtnWrapper, 0, 0, 1, 1, HPos.CENTER, VPos.CENTER);
            GridPane.setConstraints(dashboardMenuWrapper, 0, 1, 1, 1, HPos.LEFT, VPos.TOP, Priority.ALWAYS, Priority.ALWAYS);
            GridPane.setConstraints(addProjectWrapper, 0, 2, 1, 1, HPos.CENTER, VPos.CENTER);
            dashboardMenuContent.addRow(0, addTaskBtnWrapper);
            dashboardMenuContent.addRow(1, dashboardMenuWrapper);
            // dashboardMenuContent.addRow(2, addProjectWrapper);
            dashboardMenuContent.addRow(2, dashboardMenuBottomBar);

            dashboardMenuContent.setMaxWidth(230);
            dashboardMenuContent.setMinWidth(230);
            dashboardMenuContent.setPrefWidth(230);

            dashboardMenuWrapper.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    switchSection(newValue.getValue().toLowerCase(), newValue.getValue());
                }
            });

            sceneRoot.setLeft(dashboardMenuContent);
            sceneRoot.setCenter(dashboardContentScrollWrapper);
        } else {
            sessionManager.signOut();
            QuestLog.viewSignInScene();
        }

        root.getChildren().addAll(sceneRoot, addTaskModal);
    }

    public StackPane getRoot() {
        return root;
    }

    private void initializeSections() {
        DashboardTodos defaultDashboardSection = new DashboardTodos("tasks", sessionManager.getUser());
        sections.put("tasks", defaultDashboardSection.getTodosSection());
        sections.put("today", new DashboardTodos("today", sessionManager.getUser()).getTodosSection());
        sections.put("upcoming", new DashboardTodos("upcoming", sessionManager.getUser()).getTodosSection());
        sections.put("overdue", new DashboardTodos("overdue", sessionManager.getUser()).getTodosSection());
        sections.put("completed", new DashboardTodos("completed", sessionManager.getUser()).getTodosSection());

        sections.put("projects", new DashboardProjects("completed").getProjectsSection());

        setupSection(defaultDashboardSection.getTodosSection());
    }

    private void setupSection(VBox section) {
        dashboardContent.getChildren().clear();
        //GridPane.setConstraints(section, 0, 0, 1, 1, HPos.LEFT, VPos.TOP, Priority.ALWAYS, Priority.ALWAYS);
        dashboardContent.getChildren().addAll(section);
    }

    private void switchSection(String sectionKey, String title) {
        VBox section = sections.getOrDefault(sectionKey, createProjectSection(title));
        setupSection(section);
        primaryStage.setTitle("QuestLog - Dashboard > " + title.substring(0, 1).toUpperCase() + title.substring(1));
    }

    private VBox createProjectSection(String sectionName) {
        VBox defaultSection = new VBox();
        defaultSection.setPadding(new Insets(10));
        defaultSection.getChildren().addAll(new Label("No content available for: " + sectionName));
        return defaultSection;
    }

    public void addProject(String projectName) {
        TreeItem<String> projectItem = new TreeItem<>(projectName, new FontIcon(Material2OutlinedMZ.WORK));
        Project newProject = new Project(sessionManager.getUser(), projectName);
        try {
            projectService.addData(newProject);
            loadedProjects.add(loadedProjectsCount, newProject);
            for (TreeItem<String> item : dashboardMenu.getChildren()) {
                if ("Projects".equals(item.getValue())) {
                    item.getChildren().add(loadedProjectsCount++, projectItem);
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
