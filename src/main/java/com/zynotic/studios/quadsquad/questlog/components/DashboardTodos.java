package com.zynotic.studios.quadsquad.questlog.components;

import com.zynotic.studios.quadsquad.questlog.entities.Task;
import com.zynotic.studios.quadsquad.questlog.entities.User;
import com.zynotic.studios.quadsquad.questlog.services.DataService;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.zynotic.studios.quadsquad.questlog.configs.AppConfig.getRequiredApplicationProperty;

public class DashboardTodos {
    VBox todosSection;
    DataService<Task> tasksService = new DataService<>("database/tasks.json", Task.class);
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_DATE;
    private static final String APP_DEFAULT_TIMEZONE = getRequiredApplicationProperty("APP_DEFAULT_TIMEZONE");

    public DashboardTodos(String section, User user) {
        todosSection = new VBox();
        ZonedDateTime today = ZonedDateTime.now(ZoneId.of(user.getTimezone()));

        List<Task> allTasks = tasksService.readData();

        for (Task task : allTasks) {
            tasksService.readData();
            if (user.getUsername().equals(task.getBoundToUser())) {
                switch (section) {
                    case "tasks":
                        if (task.getBoundToProject() == null) {
                            TaskCard taskCard = new TaskCard(task, tasksService);
                            todosSection.getChildren().addAll(taskCard.getTask());
                        }
                        break;
                    case "today":
                        if (task.getDueDate(user).equals(today)) {
                            TaskCard taskCard = new TaskCard(task, tasksService);
                            todosSection.getChildren().addAll(taskCard.getTask());
                        }
                        break;
                    case "upcoming":
                        if (task.getDueDate(user).isAfter(today)) {
                            TaskCard taskCard = new TaskCard(task, tasksService);
                            todosSection.getChildren().addAll(taskCard.getTask());
                        }
                        break;
                    case "overdue":
                        if (task.getDueDate(user).isBefore(today)) {
                            TaskCard taskCard = new TaskCard(task, tasksService);
                            todosSection.getChildren().addAll(taskCard.getTask());
                        }
                        break;
                    case "completed":
                        if (task.isCompleted()) {
                            TaskCard taskCard = new TaskCard(task, tasksService);
                            todosSection.getChildren().addAll(taskCard.getTask());
                        }
                        break;
                }
            }
        }
    }

    public VBox getTodosSection() {
        return todosSection;
    }
}
