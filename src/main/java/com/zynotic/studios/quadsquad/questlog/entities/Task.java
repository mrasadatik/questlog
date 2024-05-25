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
//
import java.io.Serial;
import java.io.Serializable;
//
import java.util.Date;
//
import com.zynotic.studios.quadsquad.questlog.interfaces.DataIdentifier;

/**
 * Represents a task entity in the system.
 * Tasks can be created, updated, and deleted.
 * Each task has a unique ID, title, description, due date, completion status, and other attributes.
 */
public class Task implements Serializable, DataIdentifier {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("taskId") // Maps 'taskId' field to JSON key
    private int taskId; // Unique ID of the task

    @JsonProperty("boundToProject") // Maps 'boundToProject' field to JSON key
    private int boundToProject; // ID of the project to which the task is bound

    @JsonProperty("title") // Maps 'title' field to JSON key
    private String title; // Title of the task

    @JsonProperty("description") // Maps 'description' field to JSON key
    private String description; // Description of the task

    @JsonProperty("dueDate") // Maps 'dueDate' field to JSON key
    private Date dueDate; // Due date of the task

    @JsonProperty("addDate") // Maps 'addDate' field to JSON key
    private Date addDate; // Date when the task was added

    @JsonProperty("completed") // Maps 'completed' field to JSON key
    private boolean completed; // Completion status of the task

    @JsonProperty("boundToUser") // Maps 'boundToUser' field to JSON key
    private String boundToUser; // User to whom the task is bound

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
     * @param boundToProject ID of the project to which the task is bound
     * @param title          Title of the task
     * @param description    Description of the task
     * @param dueDate        Due date of the task
     * @param completed      Completion status of the task
     * @param boundToUser    User to whom the task is bound
     * @param status         Status of the task (0 - Deleted, 1 - Active, 2 - Archived)
     */
    public Task(int boundToProject, String title, String description, Date dueDate, boolean completed, String boundToUser, int status) {
        this.boundToProject = boundToProject;
        this.title = title;
        this.description = description;
        this.addDate = new Date(); // Set creation date to current date and time
        this.dueDate = dueDate;
        this.completed = completed;
        this.boundToUser = boundToUser;
        this.status = status;
    }

    /**
     * Constructs a new Task instance with specified parameters (status defaults to 'Active').
     *
     * @param boundToProject ID of the project to which the task is bound
     * @param title          Title of the task
     * @param description    Description of the task
     * @param dueDate        Due date of the task
     * @param completed      Completion status of the task
     * @param boundToUser    User to whom the task is bound
     */
    public Task(int boundToProject, String title, String description, Date dueDate, boolean completed, String boundToUser) {
        this.boundToProject = boundToProject;
        this.title = title;
        this.description = description;
        this.addDate = new Date(); // Set creation date to current date and time
        this.dueDate = dueDate;
        this.completed = completed;
        this.boundToUser = boundToUser;
        this.status = 1; // Default status is 'Active'
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
    private void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    /**
     * Retrieves the ID of the project to which the task is bound.
     *
     * @return The ID of the project.
     */
    public int getBoundToProject() {
        return boundToProject;
    }

    /**
     * Sets the ID of the project to which the task is bound.
     *
     * @param boundToProject The ID of the project.
     */
    public void setBoundToProject(int boundToProject) {
        this.boundToProject = boundToProject;
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
    public void setTitle(String title) {
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
     * Retrieves the due date of the task.
     *
     * @return The due date of the task.
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * Sets the due date of the task.
     *
     * @param dueDate The due date of the task.
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Retrieves the date when the task was added.
     *
     * @return The date when the task was added.
     */
    public Date getAddDate() {
        return addDate;
    }

    /**
     * Sets the date when the task was added.
     *
     * @param addDate The date when the task was added.
     */
    public void setAddDate(Date addDate) {
        this.addDate = addDate;
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
    public void setCompleted(boolean completed) {
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
     * Sets the user to whom the task is bound.
     *
     * @param boundToUser The user to whom the task is bound.
     */
    public void setBoundToUser(String boundToUser) {
        this.boundToUser = boundToUser;
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
    public void setStatus(int status) {
        this.status = status;
    }
}
