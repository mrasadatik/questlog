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
 * Represents a project entity in the system.
 * Projects can be created, updated, and deleted.
 * Each project has a unique ID, title, user to whom it's bound, creation date and time, and status.
 */
public class Project implements Serializable, DataIdentifier {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private static final String APP_DEFAULT_TIMEZONE = getRequiredApplicationProperty("APP_DEFAULT_TIMEZONE");

    @NotNull(message = "Project ID cannot be null")
    @PositiveOrZero(message = "Project ID must greater that or equal to 0")
    @Min(value = 0, message = "Project ID must greater that or equal to 0")
    @JsonProperty("projectId") // Maps 'projectId' field to JSON key
    private int projectId; // Unique ID of the project

    @NotNull(message = "Project title cannot be null")
    @NotBlank(message = "Project title cannot be blank")
    @JsonProperty("title") // Maps 'title' field to JSON key
    private String title; // Title of the project

    @NotNull(message = "Username for Project cannot be null")
    @NotBlank(message = "Username for Project cannot be blank")
    @Length(min = 4, max = 30, message = "Username should be at least 4 characters long and less than 30")
    @Pattern(regexp = "^[a-z0-9]+$", message = "Username can only contain letters (a-z) and digits (0-9)")
    @JsonProperty("boundToUser") // Maps 'boundToUser' field to JSON key
    private String boundToUser; // User to whom the project is bound

    @NotNull(message = "Project creation date cannot be null")
    @PastOrPresent(message = "Project creation date should be in the past")
    @JsonProperty("addedAt") // Maps 'addedAt' field to JSON key
    private ZonedDateTime addedAt; // Date and time when the project was added

    @NotNull(message = "Status cannot be null")
    @Range(min = 0, max = 2, message = "Invalid status")
    @JsonProperty("status") // Maps 'status' field to JSON key
    private int status; // Status of the project: 0 - Deleted, 1 - Active, 2 - Archived

    /**
     * Constructs a new Project instance.
     */
    public Project() {
    }

    /**
     * Constructor to initialize a project with specified title and bound user (status defaults to 'Active').
     *
     * @param user  The user to whom the project is bound.
     * @param title The title of the project.
     */
    public Project(
            @NotNull(message = "User cannot be null")
            User user,

            @NotNull(message = "Project title cannot be null")
            String title
    ) {
        this.title = title;
        this.boundToUser = user.getUsername();
        this.addedAt = ZonedDateTime.now(ZoneId.of(APP_DEFAULT_TIMEZONE)); // Set creation date and time to current date and time
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
     * Sets or Gets unique keys associated with the project.
     *
     * @return The list of unique keys.
     */
    @Override
    @JsonIgnore
    public List<String> uniqueKeys() {
        return List.of("projectId"); // Specify unique keys
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
    private void setProjectId(
            @NotNull(message = "Project ID cannot be null")
            @PositiveOrZero(message = "Project ID must greater that or equal to 0")
            @Min(value = 0, message = "Project ID must greater that or equal to 0")
            int projectId
    ) {
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
    public void setTitle(
            @NotNull(message = "Project title cannot be null")
            @NotBlank(message = "Project title cannot be blank")
            String title
    ) {
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
     * @param user The user to whom the project is bound. The username of this user will be set.
     */
    public void setBoundToUser(
            @NotNull(message = "User cannot be null")
            User user
    ) {
        this.boundToUser = user.getUsername();
    }

    /**
     * Retrieves the date and time when the project was added, adjusted to the user's timezone.
     *
     * @param user The user whose timezone will be used for the adjustment.
     * @return The date and time when the project was added, in the user's timezone.
     */
    public ZonedDateTime getAddedAt(
            @NotNull(message = "User cannot be null")
            User user
    ) {
        return addedAt.withZoneSameInstant(ZoneId.of(user.getTimezone()));
    }

    /**
     * Sets the date and time when the project was added, adjusting it to the application's default timezone.
     * Note: This method is private and should only be used internally.
     *
     * @param addedAt The date and time when the project was added.
     */
    private void setAddedAt(
            @NotNull(message = "Project creation date cannot be null")
            @PastOrPresent(message = "Project creation date should be in the past")
            ZonedDateTime addedAt
    ) {
        this.addedAt = addedAt.withZoneSameInstant(ZoneId.of(APP_DEFAULT_TIMEZONE));
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
        Project that = (Project) o;
        return projectId == that.projectId;
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(projectId);
    }
}