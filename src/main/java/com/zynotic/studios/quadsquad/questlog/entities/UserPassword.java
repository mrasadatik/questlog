package com.zynotic.studios.quadsquad.questlog.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.mkammerer.argon2.Argon2Advanced;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Version;
import de.mkammerer.argon2.HashResult;
import jakarta.validation.constraints.Null;

import java.io.Serial;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Represents the password of a user entity.
 * Provides functionality for hashing and verifying passwords using Argon2 algorithm.
 */
public class UserPassword implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private static final Argon2Advanced argon2 = Argon2Factory.createAdvanced();

    @JsonIgnore
    public static final Charset ZPU_DEFAULT_CHARSET = StandardCharsets.UTF_8;
    @JsonIgnore
    public static final int ZPU_DEFAULT_ITERATIONS = 10;
    @JsonIgnore
    public static final int ZPU_DEFAULT_MEMORY = 65536;
    @JsonIgnore
    public static final int ZPU_DEFAULT_PARALLELISM = 1;
    @JsonIgnore
    public static final int ZPU_DEFAULT_HASH_LENGTH = 32;
    @JsonIgnore
    public static final int ZPU_DEFAULT_SALT_LENGTH = 16;
    @JsonIgnore
    public static final Argon2Version ZPU_DEFAULT_VERSION = Argon2Version.V13;

    @JsonIgnore
    private byte[] password;
    @JsonIgnore
    private String hashedPassword;
    @JsonIgnore
    private int iterations;
    @JsonIgnore
    private int memory;
    @JsonIgnore
    private int parallelism;
    @JsonIgnore
    private int saltLength;
    @JsonIgnore
    private Argon2Version version;

    @JsonProperty("charset")
    private Charset charset;
    @JsonProperty("hashLength")
    private int hashLength;
    @JsonProperty("rawHashedPassword")
    private String rawHashedPassword;
    @JsonProperty("salt")
    private String salt;

    /**
     * Constructs a new UserPassword instance with default settings.
     */
    public UserPassword() {
        this.charset = ZPU_DEFAULT_CHARSET;
        this.iterations = ZPU_DEFAULT_ITERATIONS;
        this.memory = ZPU_DEFAULT_MEMORY;
        this.parallelism = ZPU_DEFAULT_PARALLELISM;
        this.hashLength = ZPU_DEFAULT_HASH_LENGTH;
        this.saltLength = ZPU_DEFAULT_SALT_LENGTH;
        this.version = ZPU_DEFAULT_VERSION;
    }

    /**
     * Retrieves the plain text password.
     *
     * @return The plain text password.
     */
    @JsonIgnore
    private byte[] getPassword() {
        return password;
    }

    /**
     * Sets the plain text password.
     *
     * @param password The plain text password to set.
     */
    @JsonIgnore
    public void setPassword(String password) {
        this.password = password == null ? null : password.getBytes(charset);
    }

    /**
     * Retrieves the character set used for encoding passwords.
     *
     * @return The character set used for encoding passwords.
     */
    @JsonIgnore
    public Charset getCharset() {
        return charset;
    }

    /**
     * Sets the character set used for encoding passwords.
     *
     * @param charset The character set to set.
     */
    @JsonIgnore
    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    /**
     * Retrieves the hashed password.
     *
     * @return The hashed password.
     */
    @JsonIgnore
    private String getHashedPassword() {
        return hashedPassword;
    }

    /**
     * Sets the hashed password.
     *
     * @param hashedPassword The hashed password to set.
     */
    @JsonIgnore
    private void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    /**
     * Retrieves the raw hashed password.
     *
     * @return The raw hashed password.
     */
    public String getRawHashedPassword() {
        return rawHashedPassword;
    }

    /**
     * Sets the raw hashed password.
     * This method is intended for internal use only.
     *
     * @param rawHashedPassword The raw hashed password to set.
     */
    private void setRawHashedPassword(String rawHashedPassword) {
        this.rawHashedPassword = rawHashedPassword;
    }

    /**
     * Retrieves the salt used in password hashing.
     *
     * @return The salt used in password hashing.
     */
    public String getSalt() {
        return salt;
    }

    /**
     * Sets the salt used in password hashing.
     * This method is intended for internal use only.
     */
    private void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * Retrieves the number of iterations used in password hashing.
     *
     * @return The number of iterations used in password hashing.
     */
    @JsonIgnore
    public int getIterations() {
        return iterations;
    }

    /**
     * Retrieves the amount of memory (in bytes) used in password hashing.
     *
     * @return The amount of memory (in bytes) used in password hashing.
     */
    @JsonIgnore
    public int getMemory() {
        return memory;
    }

    /**
     * Retrieves the parallelism factor used in password hashing.
     *
     * @return The parallelism factor used in password hashing.
     */
    @JsonIgnore
    public int getParallelism() {
        return parallelism;
    }

    /**
     * Retrieves the length of the hash generated during password hashing.
     *
     * @return The length of the hash.
     */
    public int getHashLength() {
        return hashLength;
    }

    /**
     * Sets the length of the hash generated during password hashing.
     *
     * @param hashLength The length of the hash to set.
     */
    public void setHashLength(int hashLength) {
        this.hashLength = hashLength;
    }

    /**
     * Retrieves the length of the salt used in password hashing.
     * This method is marked as ignored for JSON serialization.
     *
     * @return The length of the salt used in password hashing.
     */
    @JsonIgnore
    public int getSaltLength() {
        return saltLength;
    }

    /**
     * Retrieves the version of the Argon2 algorithm used in password hashing.
     *
     * @return The version of the Argon2 algorithm.
     */
    @JsonIgnore
    private Argon2Version getVersion() {
        return version;
    }

    /**
     * Hashes the password using Argon2 algorithm with current settings.
     */
    @JsonIgnore
    public void hashPassword() {
        setSalt(Base64.getEncoder().encodeToString(argon2.generateSalt(saltLength)));
        HashResult hashResult = argon2.hashAdvanced(iterations, memory, parallelism, password, Base64.getDecoder().decode(getSalt()), hashLength, version);
        setHashedPassword(hashResult.getEncoded());
        setRawHashedPassword(Base64.getEncoder().encodeToString(hashResult.getRaw()));
        argon2.wipeArray(password);
        setPassword(null);
    }

    /**
     * Verifies whether the provided password matches the hashed password.
     *
     * @param passwordToVerify   The password to verify.
     * @param salt               The salt used in hashing.
     * @param hashLength         The length of the hash.
     * @param rawHashedPassword  The raw hashed password.
     * @return                   True if the password matches, false otherwise.
     */
    @JsonIgnore
    public boolean verifyPassword(String passwordToVerify, byte[] salt, int hashLength, byte[] rawHashedPassword) {
        return argon2.verifyAdvanced(iterations, memory, parallelism, passwordToVerify.getBytes(), salt, null, null, hashLength, version, rawHashedPassword);
    }
}
