package com.zynotic.studios.quadsquad.questlog.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zynotic.studios.quadsquad.questlog.annotations.AllowedValues;
import com.zynotic.studios.quadsquad.questlog.annotations.YearsElapsed;
import com.zynotic.studios.quadsquad.questlog.enums.Gender;
import com.zynotic.studios.quadsquad.questlog.interfaces.DataIdentifier;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.UniqueElements;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.TimeZone;

import static com.zynotic.studios.quadsquad.questlog.configs.AppConfig.getRequiredApplicationProperty;

/**
 * Represents a user entity in the system.
 * Each user has a unique ID, name, date of birth, gender, username, password, email, phone number, timezone,
 * date when added to the system, and status.
 */
public class User implements Serializable, DataIdentifier {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private static final String APP_DEFAULT_TIMEZONE = getRequiredApplicationProperty("APP_DEFAULT_TIMEZONE");

    @NotNull(message = "User ID cannot be null")
    @PositiveOrZero(message = "User ID must greater that or equal to 0")
    @Min(value = 0, message = "User ID must greater that or equal to 0")
    @UniqueElements(message = "User ID must be unique")
    @JsonProperty("userId") // Maps 'userId' field to JSON key
    private int userId; // Unique ID of the user

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    @Length(min = 5, max = 100, message = "Name should be at least 5 characters long and less than 100")
    @Pattern(regexp = "^(?!.*(?:\\s\\s|-{2,}|'{2,})).{2,}(?:\\s(?!.*(?:\\s\\s|-{2,}|'{2,})).{2,})+$", message = "Please enter a full name with a space separating first and last name. Avoid consecutive spaces, hyphens, or apostrophes")
    @JsonProperty("name") // Maps 'name' field to JSON key
    private String name; // Name of the user

    @NotNull(message = "Date of Birth cannot be null")
    @Past(message = "Date of Birth should be in the past")
    @YearsElapsed(min = 8, message = "Minimum age requirements is 8 years")
    @JsonProperty("dateOfBirth") // Maps 'dateOfBirth' field to JSON key
    private LocalDate dateOfBirth; // Date of birth of the user

    @NotNull(message = "Gender cannot be null")
    @NotBlank(message = "Gender cannot be blank")
    @AllowedValues(enumClass = Gender.class)
    @JsonProperty("gender") // Maps 'gender' field to JSON key
    private Gender gender; // Gender of the user

    @NotNull(message = "Username cannot be null")
    @NotBlank(message = "Username cannot be blank")
    @Length(min = 4, max = 30, message = "Username should be at least 4 characters long and less than 30")
    @Pattern(regexp = "^[a-z0-9]+$", message = "Username can only contain letters (a-z) and digits (0-9)")
    @UniqueElements(message = "Username must be unique")
    @JsonProperty("username") // Maps 'username' field to JSON key
    private String username; // Username of the user

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    @JsonProperty("password") // Maps 'password' field to JSON key
    private String password; // Password of the user

    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be blank")
    @Length(min = 5, max = 100, message = "Email should be at least 5 characters long and less than 100")
    @Email(message = "Invalid email address")
    @UniqueElements(message = "Email address must be unique")
    @JsonProperty("email") // Maps 'email' field to JSON key
    private String email; // Email of the user

    @NotNull(message = "Phone number cannot be null")
    @UniqueElements(message = "Phone number must be unique")
    @JsonProperty("phoneNumber") // Maps 'phoneNumber' field to JSON key
    private UserPhoneNumber phoneNumber; // Phone number of the user

    @NotNull(message = "TimeZone cannot be null")
    @NotBlank(message = "TimeZone cannot be blank")
    @Length(min = 3, message = "Invalid timezone")
    @JsonProperty("timezone") // Maps 'timezone' field to JSON key
    private String timezone; // Timezone of the user

    @NotNull(message = "User creation date cannot be null")
    @PastOrPresent(message = "User creation date should be in the past")
    @JsonProperty("addedAt") // Maps 'addedAt' field to JSON key
    private ZonedDateTime addedAt; // Date when the user was added to the system

    @NotNull(message = "Status cannot be null")
    @Range(min = 0, max = 2, message = "Invalid status")
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
     */
    public User(
            @NotNull(message = "Name cannot be null")
            @NotBlank(message = "Name cannot be blank")
            @Length(min = 5, max = 100, message = "Name should be at least 5 characters long and less than 100")
            @Pattern(regexp = "^(?!.*(?:\\s\\s|-{2,}|'{2,})).{2,}(?:\\s(?!.*(?:\\s\\s|-{2,}|'{2,})).{2,})+$", message = "Please enter a full name with a space separating first and last name. Avoid consecutive spaces, hyphens, or apostrophes")
            String name,

            @NotNull(message = "Date of Birth cannot be null")
            @Past(message = "Date of Birth should be in the past")
            @YearsElapsed(min = 8, message = "Minimum age requirements is 8 years")
            LocalDate dateOfBirth,

            @NotNull(message = "Gender cannot be null")
            @NotBlank(message = "Gender cannot be blank")
            @AllowedValues(enumClass = Gender.class)
            Gender gender,

            @NotNull(message = "Username cannot be null")
            @NotBlank(message = "Username cannot be blank")
            @Length(min = 4, max = 30, message = "Username should be at least 4 characters long and less than 30")
            @Pattern(regexp = "^[a-z0-9]+$", message = "Username can only contain letters (a-z) and digits (0-9)")
            @UniqueElements(message = "Username must be unique")
            String username,

            @NotNull(message = "Password cannot be null")
            @NotBlank(message = "Password cannot be blank")
            @Length(min = 8, message = "Password should be at least 8 characters long")
            @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,}$", message = "Password must be at least 8 characters with a mix of uppercase, lowercase letters, digits, and special characters (no spaces or underscore)")
            String password,

            @NotNull(message = "Email cannot be null")
            @NotBlank(message = "Email cannot be blank")
            @Length(min = 5, max = 100, message = "Email should be at least 5 characters long and less than 100")
            @Email(message = "Invalid email address")
            @UniqueElements(message = "Email address must be unique")
            String email,

            @NotNull(message = "Phone number cannot be null")
            @UniqueElements(message = "Phone number must be unique")
            UserPhoneNumber phoneNumber
    ) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.timezone = ZoneId.systemDefault().getId();
        this.addedAt = ZonedDateTime.now(ZoneId.of(TimeZone.getTimeZone(APP_DEFAULT_TIMEZONE).getID()));
        this.status = 1;
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
    public void setId(
            @NotNull(message = "User ID cannot be null")
            @PositiveOrZero(message = "User ID must greater that or equal to 0")
            @Min(value = 0, message = "User ID must greater that or equal to 0")
            @UniqueElements(message = "User ID must be unique")
            int userId
    ) {
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
    private void setUserId(
            @NotNull(message = "User ID cannot be null")
            @PositiveOrZero(message = "User ID must greater that or equal to 0")
            @Min(value = 0, message = "User ID must greater that or equal to 0")
            @UniqueElements(message = "User ID must be unique")
            int userId
    ) {
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
    public void setName(
            @NotNull(message = "Name cannot be null")
            @NotBlank(message = "Name cannot be blank")
            @Length(min = 5, max = 100, message = "Name should be at least 5 characters long and less than 100")
            @Pattern(regexp = "^(?!.*(?:\\s\\s|-{2,}|'{2,})).{2,}(?:\\s(?!.*(?:\\s\\s|-{2,}|'{2,})).{2,})+$", message = "Please enter a full name with a space separating first and last name. Avoid consecutive spaces, hyphens, or apostrophes")
            String name
    ) {
        this.name = name;
    }

    /**
     * Retrieves the date of birth of the user.
     *
     * @return The date of birth of the user.
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the date of birth of the user.
     *
     * @param dateOfBirth The date of birth of the user.
     */
    public void setDateOfBirth(
            @NotNull(message = "Date of Birth cannot be null")
            @Past(message = "Date of Birth should be in the past")
            @YearsElapsed(min = 8, message = "Minimum age requirements is 8 years")
            LocalDate dateOfBirth
    ) {
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
    public void setGender(
            @NotNull(message = "Gender cannot be null")
            @NotBlank(message = "Gender cannot be blank")
            @AllowedValues(enumClass = Gender.class)
            Gender gender
    ) {
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
    public void setUsername(
            @NotNull(message = "Username cannot be null")
            @NotBlank(message = "Username cannot be blank")
            @Length(min = 4, max = 30, message = "Username should be at least 4 characters long and less than 30")
            @Pattern(regexp = "^[a-z0-9]+$", message = "Username can only contain letters (a-z) and digits (0-9)")
            @UniqueElements(message = "Username must be unique")
            String username
    ) {
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
    public void setPassword(
            @NotNull(message = "Password cannot be null")
            @NotBlank(message = "Password cannot be blank")
            @Length(min = 8, message = "Password should be at least 8 characters long")
            @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,}$", message = "Password must be at least 8 characters with a mix of uppercase, lowercase letters, digits, and special characters (no spaces or underscore)")
            String password
    ) {
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
    public void setEmail(
            @NotNull(message = "Email cannot be null")
            @NotBlank(message = "Email cannot be blank")
            @Length(min = 5, max = 100, message = "Email should be at least 5 characters long and less than 100")
            @Email(message = "Invalid email address")
            @UniqueElements(message = "Email address must be unique")
            String email
    ) {
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
    public void setPhoneNumber(
            @NotNull(message = "Phone number cannot be null")
            @UniqueElements(message = "Phone number must be unique")
            UserPhoneNumber phoneNumber
    ) {
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
    public void setTimezone(
            @NotNull(message = "TimeZone cannot be null")
            @NotBlank(message = "TimeZone cannot be blank")
            @Length(min = 3, message = "Invalid timezone")
            String timezone
    ) {
        this.timezone = timezone;
    }

    /**
     * Retrieves the date when the user was added to the system.
     *
     * @return The date when the user was added to the system.
     */
    public ZonedDateTime getAddedAt() {
        return addedAt.withZoneSameInstant(ZoneId.of(TimeZone.getTimeZone(timezone).getID()));
    }

    /**
     * Sets the date when the user was added to the system.
     *
     * @param addedAt The date when the user was added to the system.
     */
    private void setAddedAt(
            @NotNull(message = "User creation date cannot be null")
            @PastOrPresent(message = "User creation date should be in the past")
            ZonedDateTime addedAt
    ) {
        this.addedAt = addedAt.withZoneSameInstant(ZoneId.of(TimeZone.getTimeZone(APP_DEFAULT_TIMEZONE).getID()));
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
        User that = (User) o;
        return userId == that.userId &&
                Objects.equals(username, that.username) &&
                Objects.equals(email, that.email) &&
                Objects.equals(phoneNumber, that.phoneNumber);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId, username, email, phoneNumber);
    }
}