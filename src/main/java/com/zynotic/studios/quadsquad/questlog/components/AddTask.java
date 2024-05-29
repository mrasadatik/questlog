package com.zynotic.studios.quadsquad.questlog.components;

import atlantafx.base.controls.CustomTextField;
import atlantafx.base.controls.ModalPane;
import atlantafx.base.controls.Tile;
import atlantafx.base.theme.Styles;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.zynotic.studios.quadsquad.questlog.entities.Task;
import com.zynotic.studios.quadsquad.questlog.entities.User;
import com.zynotic.studios.quadsquad.questlog.entities.UserPhoneNumber;
import com.zynotic.studios.quadsquad.questlog.services.DataService;
import com.zynotic.studios.quadsquad.questlog.utils.OpenLink;
import javafx.event.ActionEvent;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2OutlinedAL;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AddTask {
    private static final DataService<Task> taskServices = new DataService<>("database/tasks.json", Task.class);
    public static ModalPane getAddTaskModal(Dialog addTaskDialog, User user) {
        VBox addTaskDialogContentBox = new VBox();
        GridPane addTaskDialogCloseBar = new GridPane();
        ModalPane addTaskModalPane = new ModalPane();
        VBox addTaskDialogCloseBarWrapper = new VBox();


        addTaskModalPane.setId("addTaskModal");
        addTaskModalPane.displayProperty().addListener((obs, old, val) -> {
            if (!val) {
                addTaskModalPane.setAlignment(Pos.CENTER);
                addTaskModalPane.usePredefinedTransitionFactories(null);
            }
        });

        Button addTaskDialogCloseBtn = new Button(null, new FontIcon(Material2OutlinedAL.CLOSE));
        addTaskDialogCloseBtn.getStyleClass().addAll(Styles.ROUNDED);
        addTaskDialogCloseBtn.setOnAction(evt -> addTaskModalPane.hide(true));

        Text addTaskTitle = new Text("Add Task");
        addTaskTitle.getStyleClass().addAll(Styles.TITLE_3);

        ColumnConstraints addTaskColumn = new ColumnConstraints();
        addTaskColumn.setPercentWidth(50);
        ColumnConstraints closeColumn = new ColumnConstraints();
        closeColumn.setPercentWidth(50);

        addTaskDialogCloseBar.getColumnConstraints().addAll(addTaskColumn, closeColumn);
        GridPane.setConstraints(addTaskTitle, 0, 0, 1, 1, HPos.LEFT, VPos.CENTER);
        GridPane.setConstraints(addTaskDialogCloseBtn, 1, 0, 1, 1, HPos.RIGHT, VPos.CENTER);
        addTaskDialogCloseBar.setVgap(10);
        addTaskDialogCloseBar.setHgap(10);
        addTaskDialogCloseBar.getChildren().addAll(addTaskTitle, addTaskDialogCloseBtn);
        addTaskDialogCloseBar.setPadding(new Insets(10, 10, 0, 10));

        addTaskDialogCloseBarWrapper.getChildren().addAll(addTaskDialogCloseBar);
        addTaskDialogCloseBarWrapper.setFillWidth(true);

        addTaskDialogContentBox.setSpacing(10);
        addTaskDialogContentBox.setPadding(new Insets(0, 10, 10, 10));

        CustomTextField taskTitle = new CustomTextField();
        taskTitle.setPrefWidth(280);
        taskTitle.setPromptText("Task Title");
        TextArea taskDescription = new TextArea();
        taskDescription.setPromptText("Task description");
        taskDescription.setPrefWidth(280);
        taskDescription.setPrefRowCount(10);
        ZonedDateTime today = ZonedDateTime.now(ZoneId.of("UTC"));
        DatePicker taskDueDate = new DatePicker();
        taskDueDate.setPrefWidth(280);
        Button addBtn = new Button("Add");
        addBtn.setOnAction(e  -> {
            try {
                taskServices.addData(new Task(user, null, taskTitle.getText(), taskDescription.getText(), taskDueDate.getValue().atStartOfDay(ZoneId.of(user.getTimezone()))));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        addTaskDialogContentBox.getChildren().addAll(taskTitle, taskDescription, taskDueDate, addBtn);

        addTaskDialog.getChildren().setAll(addTaskDialogCloseBarWrapper, addTaskDialogContentBox);

        return addTaskModalPane;
    }
}
