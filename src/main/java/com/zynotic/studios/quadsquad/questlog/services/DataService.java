/*
 * DataService.java
 * Service class for managing data operations.
 * Created by Md Asaduzzaman Atik on Thursday, May 23, 2024, 11:42:02 PM.
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

package com.zynotic.studios.quadsquad.questlog.services;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zynotic.studios.quadsquad.questlog.interfaces.DataIdentifier;
import com.zynotic.studios.quadsquad.questlog.utils.DataContainer;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.HibernateValidator;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Service class for managing data operations.
 * Supports reading, writing, detecting duplicate key-value pair, adding, updating, and deleting data.
 *
 * @param <P> The type of data entity implementing the DataIdentifier interface.
 */
public class DataService<P extends DataIdentifier> {
    private final String DATA_FILE_PATH; // File path for storing data
    private final ObjectMapper objectMapper; // Object mapper for JSON serialization/deserialization
    private final Class<P> typeParameterClass; // Class type parameter
    private final List<String> uniqueKeys; // List of unique keys
    ValidatorFactory validatorFactory;
    Validator validator;

    /**
     * Constructs a DataService instance with the specified data file location, entity class, and unique keys.
     *
     * @param dataFileLocation   The file path for storing data.
     * @param typeParameterClass The class type parameter for the entity.
     */
    public DataService(String dataFileLocation, Class<P> typeParameterClass) {
        this.DATA_FILE_PATH = dataFileLocation;
        this.typeParameterClass = typeParameterClass;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.uniqueKeys = getUniqueKeys();
        this.validatorFactory = Validation.byProvider(HibernateValidator.class).configure().buildValidatorFactory();
        this.validator = validatorFactory.getValidator();
    }

    /**
     * Retrieves the next available ID for new data.
     * @return The next available ID.
     */
    private int getNextDataId() {
        List<P> data = readData();
        return data.isEmpty() ? 0 : data.stream().mapToInt(P::getId).max().getAsInt() + 1;
    }

    /**
     * Reads data from the file.
     *
     * @return The list of data read from the file.
     */
    public List<P> readData() {
        try {
            File file = new File(DATA_FILE_PATH);
            if (!file.exists()) {
                return new DataContainer<P>().getData();
            }
            JavaType type = objectMapper.getTypeFactory().constructParametricType(DataContainer.class, typeParameterClass);
            DataContainer<P> container = objectMapper.readValue(file, type);
            return container.getData();
        } catch (IOException e) {
            e.printStackTrace();
            return new DataContainer<P>().getData();
        }
    }

    /**
     * Writes data to the file.
     *
     * @param data The data to write.
     */
    protected void writeData(List<P> data) {
        DataContainer<P> container = new DataContainer<>();
        container.setData(data);

        try {
            File file = new File(DATA_FILE_PATH);
            JavaType type = objectMapper.getTypeFactory().constructParametricType(DataContainer.class, typeParameterClass);
            objectMapper.writerFor(type).writeValue(file, container);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the provided key-value pair matches any existing data.
     *
     * @param key   The key to check against existing data.
     * @param value The value to check against existing data.
     * @return True if the key-value pair matches existing data, false otherwise.
     */
    public boolean isDuplicate(String key, Object value) {
        try {
            Field field = typeParameterClass.getDeclaredField(key);
            field.setAccessible(true);
            String valueAsString = value != null ? value.toString() : null;
            return readData().stream()
                    .map(data -> {
                        try {
                            Object fieldValue = field.get(data);
                            return fieldValue != null ? fieldValue.toString() : null;
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                            return null;
                        }
                    })
                    .anyMatch(fieldValue -> Objects.equals(fieldValue, valueAsString));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Adds new data.
     *
     * @param data The data to add.
     * @throws IOException If an I/O error occurs.
     */
    public void addData(P data) throws IOException {
        Set<ConstraintViolation<P>> violations = validator.validate(data);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<P> violation : violations) {
                System.out.println("Validation error: " + violation.getMessage());
            }
        } else {
            for (String key : uniqueKeys) {
                if (isDuplicate(key, getFieldValue(data, key))) {
                    return;
                }
            }

            data.setId(getNextDataId());
            List<P> allData = readData();
            allData.add(data);
            writeData(allData);
        }
    }

    /**
     * Retrieves the field value of a given data entry by field name.
     *
     * @param data The data entry.
     * @param key  The field name.
     * @return The field value.
     */
    private Object getFieldValue(P data, String key) {
        try {
            Field field = typeParameterClass.getDeclaredField(key);
            field.setAccessible(true);
            return field.get(data);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Updates existing data.
     *
     * @param updatedData The updated data.
     */
    public void updateData(P updatedData) {
        Set<ConstraintViolation<P>> violations = validator.validate(updatedData);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<P> violation : violations) {
                System.out.println("Validation error: " + violation.getMessage());
            }
        } else {
            for (String key : uniqueKeys) {
                if (isDuplicate(key, getFieldValue(updatedData, key))) {
                    return;
                }
            }

            List<P> data = readData();
            data.replaceAll(d -> d.getId() == updatedData.getId() ? updatedData : d);
            writeData(data);
        }
    }

    /**
     * Deletes data by ID.
     *
     * @param dataId The ID of the data to delete.
     */
    public void deleteData(int dataId) {
        List<P> allData = readData();
        allData.removeIf(data -> data.getId() == dataId);
        writeData(allData);
    }

    /**
     * Retrieves data by ID.
     *
     * @param dataId The ID of the data to retrieve.
     * @return An optional containing the data, if found.
     */
    public Optional<P> getDataById(int dataId) {
        return readData().stream().filter(data -> data.getId() == dataId).findFirst();
    }

    /**
     * Retrieves the unique keys associated with the entity class.
     *
     * @return The list of unique keys.
     */
    private List<String> getUniqueKeys() {
        if (typeParameterClass == null) {
            return List.of();
        }
        try {
            Constructor<P> constructor = typeParameterClass.getDeclaredConstructor();
            P instance = constructor.newInstance();
            return instance.uniqueKeys();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
