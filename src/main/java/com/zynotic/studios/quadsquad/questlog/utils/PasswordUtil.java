package com.zynotic.studios.quadsquad.questlog.utils;

import de.mkammerer.argon2.Argon2Advanced;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Version;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class PasswordUtil {
    private static final Argon2Advanced argon2 = Argon2Factory.createAdvanced();

    public static final Charset ZPU_DEFAULT_CHARSET = StandardCharsets.UTF_8;
    public static final int ZPU_DEFAULT_ITERATIONS = 100000;
    public static final int ZPU_DEFAULT_MEMORY = 1048576;
    public static final int ZPU_DEFAULT_PARALLELISM = 1;
    public static final int ZPU_DEFAULT_HASH_LENGTH = 32;
    public static final int ZPU_DEFAULT_SALT_LENGTH = 16;
    public static final byte[] ZPU_DEFAULT_SECRET = null;
    public static final byte[] ZPU_DEFAULT_ASSOCIATED_DATA = null;
    public static final Argon2Version ZPU_DEFAULT_VERSION = Argon2Version.V13;

    private String password;
    private Charset charset;
    private String hashedPassword;
    private byte[] rawHashedPassword;
    private byte[] salt;
    private int iterations;
    private int memory;
    private int parallelism;
    private int hashLength;
    private int saltLength;
    private byte[] secret;
    private byte[] associatedData;
    private Argon2Version version;

    public PasswordUtil() {
        this.charset = ZPU_DEFAULT_CHARSET;
        this.salt = generateSalt();
        this.iterations = ZPU_DEFAULT_ITERATIONS;
        this.memory = ZPU_DEFAULT_MEMORY;
        this.parallelism = ZPU_DEFAULT_PARALLELISM;
        this.hashLength = ZPU_DEFAULT_HASH_LENGTH;
        this.saltLength = ZPU_DEFAULT_SALT_LENGTH;
        this.secret = ZPU_DEFAULT_SECRET;
        this.associatedData = ZPU_DEFAULT_ASSOCIATED_DATA;
        this.version = ZPU_DEFAULT_VERSION;
    }

    public PasswordUtil(int saltLength, int hashLength, Argon2Version version, Charset charset, byte[] secret, byte[] associatedData, int iterations, int memory) {
        this.charset = charset;
        this.salt = generateSalt(saltLength);
        this.iterations = iterations;
        this.memory = memory;
        this.parallelism = ZPU_DEFAULT_PARALLELISM;
        this.hashLength = hashLength;
        this.saltLength = saltLength;
        this.secret = secret;
        this.associatedData = associatedData;
        this.version = version;
    }

    public PasswordUtil(String password) {
        this.password = password;
        this.charset = ZPU_DEFAULT_CHARSET;
        this.salt = generateSalt();
        this.iterations = ZPU_DEFAULT_ITERATIONS;
        this.memory = ZPU_DEFAULT_MEMORY;
        this.parallelism = ZPU_DEFAULT_PARALLELISM;
        this.hashLength = ZPU_DEFAULT_HASH_LENGTH;
        this.saltLength = ZPU_DEFAULT_SALT_LENGTH;
        this.secret = ZPU_DEFAULT_SECRET;
        this.associatedData = ZPU_DEFAULT_ASSOCIATED_DATA;
        this.version = ZPU_DEFAULT_VERSION;
    }

    public PasswordUtil(String password, int saltLength, int hashLength, Argon2Version version, Charset charset, byte[] secret, byte[] associatedData, int iterations, int memory) {
        this.password = password;
        this.charset = charset;
        this.salt = generateSalt(saltLength);
        this.iterations = iterations;
        this.memory = memory;
        this.parallelism = ZPU_DEFAULT_PARALLELISM;
        this.hashLength = hashLength;
        this.saltLength = saltLength;
        this.secret = secret;
        this.associatedData = associatedData;
        this.version = version;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public byte[] getRawHashedPassword() {
        return rawHashedPassword;
    }

    public void setRawHashedPassword(byte[] rawHashedPassword) {
        this.rawHashedPassword = rawHashedPassword;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public void setSalt(int saltLength) {
        this.salt = generateSalt(saltLength);
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public int getParallelism() {
        return parallelism;
    }

    public void setParallelism(int parallelism) {
        this.parallelism = parallelism;
    }

    public int getHashLength() {
        return hashLength;
    }

    public void setHashLength(int hashLength) {
        this.hashLength = hashLength;
    }

    public int getSaltLength() {
        return saltLength;
    }

    public void setSaltLength(int saltLength) {
        this.saltLength = saltLength;
    }

    public byte[] getSecret() {
        return secret;
    }

    public void setSecret(byte[] secret) {
        this.secret = secret;
    }

    public byte[] getAssociatedData() {
        return associatedData;
    }

    public void setAssociatedData(byte[] associatedData) {
        this.associatedData = associatedData;
    }

    public Argon2Version getVersion() {
        return version;
    }

    public void setVersion(Argon2Version version) {
        this.version = version;
    }

    public byte[] generateSalt() {
        return argon2.generateSalt(this.saltLength);
    }

    public static byte[] generateSalt(int saltLength) {
        return argon2.generateSalt(saltLength);
    }
}
