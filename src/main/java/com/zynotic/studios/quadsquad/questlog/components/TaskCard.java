package com.zynotic.studios.quadsquad.questlog.components;

import atlantafx.base.controls.Card;
import atlantafx.base.controls.Tile;
import atlantafx.base.theme.Styles;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2OutlinedAL;

import javax.swing.text.Style;

public class TaskCard {
    Card taskCard;
    public TaskCard(String taskID) {
        taskCard = new Card();

        HBox actionComplete = new HBox();
        VBox actionCompleteWrapper = new VBox();
        HBox actionDelete = new HBox();
        VBox actionDeleteWrapper = new VBox();
        HBox actionPrimary = new HBox();
        VBox actionPrimaryWrapper = new VBox();
        HBox taskCardHeader = new HBox();
        VBox taskCardHeaderWrapper = new VBox();

        actionComplete.setAlignment(Pos.CENTER);
        actionComplete.setFillHeight(true);
        actionComplete.setSpacing(5);
        actionComplete.setPadding(new Insets(5, 20, 5, 5));

        actionDelete.setAlignment(Pos.CENTER);
        actionDelete.setFillHeight(true);
        actionDelete.setSpacing(5);
        actionDelete.setPadding(new Insets(5, 5, 5, 20));

        actionPrimary.setAlignment(Pos.TOP_LEFT);
        actionPrimary.setFillHeight(true);
        actionPrimary.setSpacing(5);
        actionPrimary.setPadding(new Insets(5));

        CheckBox checkAsComplete = new CheckBox();
        taskCard.getStyleClass().addAll(Styles.INTERACTIVE);
        taskCard.setPrefWidth(Region.USE_COMPUTED_SIZE);

        Button deleteTaskBtn = new Button(null, new FontIcon(Material2OutlinedAL.DELETE));
        deleteTaskBtn.getStyleClass().addAll(Styles.BUTTON_CIRCLE, Styles.ROUNDED, Styles.DANGER, Styles.BUTTON_OUTLINED);
        deleteTaskBtn.setMnemonicParsing(true);

        actionComplete.getChildren().addAll(checkAsComplete);
        actionDelete.getChildren().addAll(deleteTaskBtn);

        Tile taskCardHeaderContent = new Tile("Task Title" + taskID, "Task description", null);
        HBox.setHgrow(taskCardHeaderContent, Priority.ALWAYS);

        taskCardHeader.getChildren().addAll(actionComplete, taskCardHeaderContent, actionDelete);
        taskCardHeaderWrapper.getChildren().addAll(taskCardHeader);

        taskCardHeaderWrapper.setFillWidth(true);
        taskCardHeader.setFillHeight(true);

        taskCard.setHeader(taskCardHeaderWrapper);
        taskCard.setBody(actionPrimary);

    }
    public Card getTask() {
        return taskCard;
    }
}
