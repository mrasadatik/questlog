/*
 * Task.java
 * Represents a task entity in the system.
 * Created by Md Asaduzzaman Atik on Thursday, May 23, 2024, 3:24:45 PM.
 * Copyright (C) 2024 Zynotic Studios, Quad Squad
 * Licensed under the GNU General Public License, Version 3.0 (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://www.gnu.org/licenses/gpl-3.0.html
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zynotic.studios.quadsquad.questlog.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zynotic.studios.quadsquad.questlog.interfaces.DataIdentifier;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

import static com.zynotic.studios.quadsquad.questlog.configs.AppConfig.getRequiredApplicationProperty;

/**
 * Represents a task entity in the system.
 * Tasks can be created, updated, and deleted.
 * Each task has a unique ID, title, description, due date, completion status, and other attributes.
 */
public class Task implements Serializable, DataIdentifier {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private static final String APP_DEFAULT_TIMEZONE = getRequiredApplicationProperty("APP_DEFAULT_TIMEZONE");

    @NotNull(message = "Task ID cannot be null")
    @PositiveOrZero(message = "Task ID must greater that or equal to 0")
    @Min(value = 0, message = "Task ID must greater that or equal to 0")
    @JsonProperty("taskId") // Maps 'taskId' field to JSON key
    private int taskId; // Unique ID of the task

    @JsonProperty("boundToProject") // Maps 'boundToProject' field to JSON key
    private Integer boundToProject; // ID of the project to which the task is bound

    @NotNull(message = "Task title cannot be null")
    @NotBlank(message = "Task title cannot be blank")
    @JsonProperty("title") // Maps 'title' field to JSON key
    private String title; // Title of the task

    @JsonProperty("description") // Maps 'description' field to JSON key
    private String description; // Description of the task

    @NotNull(message = "Task due date cannot be null")
    @JsonProperty("dueDate") // Maps 'dueDate' field to JSON key
    private ZonedDateTime dueDate; // Due date and time of the task

    @NotNull(message = "Task creation date cannot be null")
    @PastOrPresent(message = "Task creation date should be in past")
    @JsonProperty("addDate") // Maps 'addDate' field to JSON key
    private ZonedDateTime addDate; // Date and time when the task was added

    @NotNull(message = "Competed status cannot be null")
    @JsonProperty("completed") // Maps 'completed' field to JSON key
    private boolean completed; // Completion status of the task

    @NotNull(message = "Username for Task cannot be null")
    @NotBlank(message = "Username for Task cannot be blank")
    @Length(min = 4, max = 30, message = "Username should be at least 4 characters long and less than 30")
    @Pattern(regexp = "^[a-z0-9]+$", message = "Username can only contain letters (a-z) and digits (0-9)")
    @JsonProperty("boundToUser") // Maps 'boundToUser' field to JSON key
    private String boundToUser; // User to whom the task is bound

    @NotNull(message = "Status cannot be null")
    @Range(min = 0, max = 2, message = "Invalid status")
    @JsonProperty("status") // Maps 'status' field to JSON key
    private int status; // Status of the task: 0 - Deleted, 1 - Active, 2 - Archived

    /**
     * Constructs a new Task instance.
     */
    public Task() {
    }

    /**
     * Constructs a new Task instance with specified parameters.
     *
     * @param user        User to whom the task is bound.
     * @param project     Project to which the task is bound.
     * @param title       Title of the task.
     * @param description Description of the task.
     * @param dueDate     Due date and time of the task.
     */
    public Task(
            @NotNull(message = "User cannot be null")
            User user,

            Project project,

            @NotNull(message = "Task title cannot be null")
            @NotBlank(message = "Task title cannot be blank")
            String title,

            String description,

            @NotNull(message = "Task due date cannot be null")
            @FutureOrPresent(message = "Task due date should be in future")
            ZonedDateTime dueDate
    ) {
        this.boundToProject = project == null ? null : project.getProjectId();
        this.title = title;
        this.description = description;
        this.addDate = ZonedDateTime.now(ZoneId.of(APP_DEFAULT_TIMEZONE)); // Set creation date and time to current date and time
        this.dueDate = dueDate.withZoneSameInstant(ZoneId.of(APP_DEFAULT_TIMEZONE));
        this.completed = false;
        this.boundToUser = user.getUsername();
        this.status = 1;
    }

    /**
     * Retrieves the ID of the task.
     * Note: This method is implemented to fulfill the DataIdentifier interface requirement.
     *
     * @return The ID of the task.
     */
    @Override
    @JsonIgnore
    public int getId() {
        return taskId;
    }

    /**
     * Sets the ID of the task.
     * Note: This method is implemented to fulfill the DataIdentifier interface requirement.
     *
     * @param taskId The ID of the task.
     */
    @Override
    @JsonIgnore
    public void setId(int taskId) {
        this.taskId = taskId;
    }

    /**
     * Sets or Gets unique keys associated with the task.
     *
     * @return The list of unique keys.
     */
    @Override
    @JsonIgnore
    public List<String> uniqueKeys() {
        return List.of("taskId"); // Specify unique keys
    }

    /**
     * Retrieves the project ID.
     *
     * @return The project ID.
     */
    public int getTaskId() {
        return taskId;
    }

    /**
     * Sets the task ID.
     * Note: This method is intended for internal use only.
     *
     * @param taskId The task ID to set.
     */
    private void setTaskId(
            @NotNull(message = "Task ID cannot be null")
            @PositiveOrZero(message = "Task ID must greater that or equal to 0")
            @Min(value = 0, message = "Task ID must greater that or equal to 0")
            int taskId
    ) {
        this.taskId = taskId;
    }

    /**
     * Retrieves the ID of the project to which the task is bound.
     *
     * @return The ID of the project.
     */
    public Integer getBoundToProject() {
        return boundToProject;
    }

    /**
     * Sets the ID of the project to which the task is bound.
     *
     * @param boundProjectOd The project whose ID will be set.
     */
    public void setBoundToProject(
            Integer boundProjectOd
    ) {
        this.boundToProject = boundProjectOd;
    }

    /**
     * Retrieves the title of the task.
     *
     * @return The title of the task.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the task.
     *
     * @param title The title of the task.
     */
    public void setTitle(
            @NotNull(message = "Task title cannot be null")
            @NotBlank(message = "Task title cannot be blank")
            String title
    ) {
        this.title = title;
    }

    /**
     * Retrieves the description of the task.
     *
     * @return The description of the task.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the task.
     *
     * @param description The description of the task.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the due date and time of the task, adjusted to the user's timezone.
     *
     * @param user The user whose timezone will be used for the adjustment.
     * @return The due date and time of the task, in the user's timezone.
     */
    public ZonedDateTime getDueDate(
            @NotNull(message = "User cannot be null")
            User user
    ) {
        return dueDate.withZoneSameInstant(ZoneId.of(user.getTimezone()));
    }

    /**
     * Sets the due date and time of the task, adjusting it to the application's default timezone.
     *
     * @param dueDate The due date and time of the task.
     */
    public void setDueDate(
            @NotNull(message = "Task due date cannot be null")
            @FutureOrPresent(message = "Task due date should be in future")
            ZonedDateTime dueDate
    ) {
        this.dueDate = dueDate.withZoneSameInstant(ZoneId.of(APP_DEFAULT_TIMEZONE));
    }

    /**
     * Retrieves the date and time when the task was added, adjusted to the user's timezone.
     *
     * @param user The user whose timezone will be used for the adjustment.
     * @return The date and time when the task was added, in the user's timezone.
     */
    public ZonedDateTime getAddDate(
            @NotNull(message = "User cannot be null")
            User user
    ) {
        return addDate.withZoneSameInstant(ZoneId.of(user.getTimezone()));
    }

    /**
     * Sets the date and time when the task was added, adjusting it to the application's default timezone.
     * Note: This method is private and should only be used internally.
     *
     * @param addDate The date and time when the task was added.
     */
    private void setAddDate(
            @NotNull(message = "Task creation date cannot be null")
            @PastOrPresent(message = "Task creation date should be in past")
            ZonedDateTime addDate
    ) {
        this.addDate = addDate.withZoneSameInstant(ZoneId.of(APP_DEFAULT_TIMEZONE));
    }

    /**
     * Checks if the task is completed.
     *
     * @return True if the task is completed, otherwise false.
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Sets the completion status of the task.
     *
     * @param completed True if the task is completed, otherwise false.
     */
    public void setCompleted(
            @NotNull(message = "Competed status cannot be null")
            boolean completed
    ) {
        this.completed = completed;
    }

    /**
     * Retrieves the user to whom the task is bound.
     *
     * @return The user to whom the task is bound.
     */
    public String getBoundToUser() {
        return boundToUser;
    }

    /**
     * Sets the username of the user to whom the task is bound.
     *
     * @param username The user object representing the user to whom the task is bound.
     */
    public void setBoundToUser(
            @NotNull(message = "User cannot be null")
            String username
    ) {
        this.boundToUser = username;
    }

    /**
     * Retrieves the status of the task.
     *
     * @return The status of the task (0: Deleted, 1: Active, 2: Archived).
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the status of the task.
     *
     * @param status The status of the task (0: Deleted, 1: Active, 2: Archived).
     */
    public void setStatus(
            @NotNull(message = "Status cannot be null")
            @Range(min = 0, max = 2, message = "Invalid status")
            int status
    ) {
        this.status = status;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o The reference object with which to compare.
     * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task that = (Task) o;
        return taskId == that.taskId;
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(taskId);
    }
}
