/*
 * Project.java
 * Represents a project entity in the system.
 * Created by Md Asaduzzaman Atik on Thursday, May 23, 2024, 3:24:57 PM.
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

//
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
 * Represents a project entity in the system.
 * Projects can be created, updated, and deleted.
 * Each project has a unique ID, title, user to whom it's bound, creation date, and status.
 */
public class Project implements Serializable, DataIdentifier {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("projectId") // Maps 'projectId' field to JSON key
    private int projectId; // Unique ID of the project

    @JsonProperty("title") // Maps 'title' field to JSON key
    private String title; // Title of the project

    @JsonProperty("boundToUser") // Maps 'boundToUser' field to JSON key
    private String boundToUser; // User to whom the project is bound

    @JsonProperty("addedAt") // Maps 'addedAt' field to JSON key
    private Date addedAt; // Date and time when the project was added

    @JsonProperty("status") // Maps 'status' field to JSON key
    private int status; // Status of the project: 0 - Deleted, 1 - Active, 2 - Archived

    /**
     * Constructs a new Project instance.
     */
    public Project() {}

    /**
     * Constructor to initialize a project with specified title, bound user, and status.
     *
     * @param title      The title of the project.
     * @param boundToUser The user to whom the project is bound.
     * @param status     The status of the project (0: Deleted, 1: Active, 2: Archived).
     */
    public Project(String title, String boundToUser, int status) {
        this.title = title;
        this.boundToUser = boundToUser;
        this.addedAt = new Date(); // Set creation date to current date and time
        this.status = status;
    }

    /**
     * Constructor to initialize a project with specified title and bound user (status defaults to 'Active').
     *
     * @param title      The title of the project.
     * @param boundToUser The user to whom the project is bound.
     */
    public Project(String title, String boundToUser) {
        this.title = title;
        this.boundToUser = boundToUser;
        this.addedAt = new Date(); // Set creation date to current date and time
        this.status = 1; // Default status is 'Active'
    }

    /**
     * Retrieves the ID of the project.
     * Note: This method is implemented to fulfill the DataIdentifier interface requirement.
     *
     * @return The ID of the project.
     */
    @Override
    @JsonIgnore
    public int getId() {
        return projectId;
    }

    /**
     * Sets the ID of the project.
     * Note: This method is implemented to fulfill the DataIdentifier interface requirement.
     *
     * @param projectId The ID of the project.
     */
    @Override
    @JsonIgnore
    public void setId(int projectId) {
        this.projectId = projectId;
    }

    /**
     * Retrieves the project ID.
     *
     * @return The project ID.
     */
    public int getProjectId() {
        return projectId;
    }

    /**
     * Sets the project ID.
     * Note: This method is intended for internal use only.
     *
     * @param projectId The project ID to set.
     */
    private void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    /**
     * Retrieves the title of the project.
     *
     * @return The title of the project.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the project.
     *
     * @param title The title of the project.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Retrieves the user to whom the project is bound.
     *
     * @return The user to whom the project is bound.
     */
    public String getBoundToUser() {
        return boundToUser;
    }

    /**
     * Sets the user to whom the project is bound.
     *
     * @param boundToUser The user to whom the project is bound.
     */
    public void setBoundToUser(String boundToUser) {
        this.boundToUser = boundToUser;
    }

    /**
     * Retrieves the date when the project was added.
     *
     * @return The date when the project was added.
     */
    public Date getAddedAt() {
        return addedAt;
    }

    /**
     * Sets the date when the project was added.
     *
     * @param addedAt The date when the project was added.
     */
    public void setAddedAt(Date addedAt) {
        this.addedAt = addedAt;
    }

    /**
     * Gets the status of the project.
     *
     * @return The status of the project (0: Deleted, 1: Active, 2: Archived).
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the status of the project.
     *
     * @param status The status of the project (0: Deleted, 1: Active, 2: Archived).
     */
    public void setStatus(int status) {
        this.status = status;
    }
}