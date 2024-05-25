/*
 * User.java
 * Represents a user entity in the system.
 * Created by Md Asaduzzaman Atik on Thursday, May 23, 2024, 3:25:17 PM.
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
import com.zynotic.studios.quadsquad.questlog.enums.Gender;
import com.zynotic.studios.quadsquad.questlog.interfaces.DataIdentifier;

/**
 * Represents a user entity in the system.
 * Each user has a unique ID, name, date of birth, gender, username, password, email, phone number, timezone,
 * date when added to the system, and status.
 */
public class User implements Serializable, DataIdentifier {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("userId") // Maps 'userId' field to JSON key
    private int userId; // Unique ID of the user

    @JsonProperty("name") // Maps 'name' field to JSON key
    private String name; // Name of the user

    @JsonProperty("dateOfBirth") // Maps 'dateOfBirth' field to JSON key
    private String dateOfBirth; // Date of birth of the user

    @JsonProperty("gender") // Maps 'gender' field to JSON key
    private Gender gender; // Gender of the user

    @JsonProperty("username") // Maps 'username' field to JSON key
    private String username; // Username of the user

    @JsonProperty("password") // Maps 'password' field to JSON key
    private String password; // Password of the user

    @JsonProperty("email") // Maps 'email' field to JSON key
    private String email; // Email of the user

    @JsonProperty("phoneNumber") // Maps 'phoneNumber' field to JSON key
    private UserPhoneNumber phoneNumber; // Phone number of the user

    @JsonProperty("timezone") // Maps 'timezone' field to JSON key
    private String timezone; // Timezone of the user

    @JsonProperty("addedAt") // Maps 'addedAt' field to JSON key
    private Date addedAt; // Date when the user was added to the system

    @JsonProperty("status") // Maps 'status' field to JSON key
    private int status; // Status of the user: 0 - Inactive, 1 - Active, 2 - Suspended

    /**
     * Constructs a new User instance.
     */
    public User() {
    }

    /**
     * Constructs a new User instance with the specified attributes.
     *
     * @param name        The name of the user.
     * @param dateOfBirth The date of birth of the user.
     * @param username    The username of the user.
     * @param password    The password of the user.
     * @param email       The email of the user.
     * @param phoneNumber The phone number of the user.
     * @param timezone    The timezone of the user.
     * @param status      The status of the user (0: Inactive, 1: Active, 2: Suspended).
     */
    public User(String name, String dateOfBirth, String username, String password, String email, UserPhoneNumber phoneNumber, String timezone, int status) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.timezone = timezone;
        this.addedAt = new Date(); // Set creation date to current date and time
        this.status = status;
    }

    /**
     * Constructs a new User instance with the specified attributes (status defaults to 'Active').
     *
     * @param name        The name of the user.
     * @param dateOfBirth The date of birth of the user.
     * @param username    The username of the user.
     * @param password    The password of the user.
     * @param email       The email of the user.
     * @param phoneNumber The phone number of the user.
     * @param timezone    The timezone of the user.
     */
    public User(String name, String dateOfBirth, String username, String password, String email, UserPhoneNumber phoneNumber, String timezone) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.timezone = timezone;
        this.addedAt = new Date(); // Set creation date to current date and time
        this.status = 1; // Default status is 'Active'
    }

    /**
     * Retrieves the ID of the user.
     * Note: This method is implemented to fulfill the DataIdentifier interface requirement.
     *
     * @return The ID of the user.
     */
    @Override
    @JsonIgnore
    public int getId() {
        return userId;
    }

    /**
     * Sets the ID of the user.
     * Note: This method is implemented to fulfill the DataIdentifier interface requirement.
     *
     * @param userId The ID of the user.
     */
    @Override
    @JsonIgnore
    public void setId(int userId) {
        this.userId = userId;
    }

    /**
     * Retrieves the user ID.
     *
     * @return The user ID.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user ID.
     * Note: This method is intended for internal use only.
     *
     * @param userId The user ID to set.
     */
    private void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Retrieves the name of the user.
     *
     * @return The name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     *
     * @param name The name of the user.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the date of birth of the user.
     *
     * @return The date of birth of the user.
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the date of birth of the user.
     *
     * @param dateOfBirth The date of birth of the user.
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Retrieves the gender of the user.
     *
     * @return The gender of the user.
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Sets the gender of the user.
     *
     * @param gender The gender of the user.
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Retrieves the username of the user.
     *
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username The username of the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Retrieves the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password The password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retrieves the email of the user.
     *
     * @return The email of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     *
     * @param email The email of the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the phone number of the user.
     *
     * @return The phone number of the user.
     */
    public UserPhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the user.
     *
     * @param phoneNumber The phone number of the user.
     */
    public void setPhoneNumber(UserPhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Retrieves the timezone of the user.
     *
     * @return The timezone of the user.
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     * Sets the timezone of the user.
     *
     * @param timezone The timezone of the user.
     */
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    /**
     * Retrieves the date when the user was added to the system.
     *
     * @return The date when the user was added to the system.
     */
    public Date getAddedAt() {
        return addedAt;
    }

    /**
     * Sets the date when the user was added to the system.
     *
     * @param addedAt The date when the user was added to the system.
     */
    public void setAddedAt(Date addedAt) {
        this.addedAt = addedAt;
    }

    /**
     * Retrieves the status of the user.
     *
     * @return The status of the user (0: Inactive, 1: Active, 2: Suspended).
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the status of the user.
     *
     * @param status The status of the user (0: Inactive, 1: Active, 2: Suspended).
     */
    public void setStatus(int status) {
        this.status = status;
    }
}
