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
//
import java.io.*;
//
import java.util.List;
import java.util.Optional;
//
import com.zynotic.studios.quadsquad.questlog.interfaces.DataIdentifier;
import com.zynotic.studios.quadsquad.questlog.utils.DataContainer;

/**
 * Service class for managing data operations.
 * Supports reading, writing, adding, updating, and deleting data.
 * @param <P> The type of data entity implementing the DataIdentifier interface.
 */
public class DataService<P extends DataIdentifier> {
    private final String DATA_FILE_PATH; // File path for storing data
    private final ObjectMapper objectMapper; // Object mapper for JSON serialization/deserialization
    private final Class<P> typeParameterClass; // Class type parameter

    /**
     * Constructs a DataService instance with the specified data file location and entity class.
     * @param dataFileLocation The file path for storing data.
     * @param typeParameterClass The class type parameter for the entity.
     */
    public DataService(String dataFileLocation, Class<P> typeParameterClass) {
        this.objectMapper = new ObjectMapper();
        this.DATA_FILE_PATH = dataFileLocation;
        this.typeParameterClass = typeParameterClass;
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
     * @return The list of data read from the file.
     */
    public List<P> readData() {
        try {
            File file = new File(DATA_FILE_PATH);
            if (!file.exists()) {
                // If file does not exist, return an empty list
                return new DataContainer<P>().getData();
            }
            JavaType type = objectMapper.getTypeFactory().constructParametricType(DataContainer.class, typeParameterClass);
            DataContainer<P> container = objectMapper.readValue(file, type);
            return container.getData();
        } catch (IOException e) {
            // Error handling for file reading
            e.printStackTrace();
            return new DataContainer<P>().getData();
        }
    }

    /**
     * Writes data to the file.
     * @param data The data to write.
     */
    public void writeData(List<P> data) {
        DataContainer<P> container = new DataContainer<>();
        container.setData(data);

        try {
            File file = new File(DATA_FILE_PATH);
            JavaType type = objectMapper.getTypeFactory().constructParametricType(DataContainer.class, typeParameterClass);
            objectMapper.writerFor(type).writeValue(file, container);
        } catch (IOException e) {
            // Error handling for file writing
            e.printStackTrace();
        }
    }

    /**
     * Adds new data.
     * @param data The data to add.
     * @throws IOException If an I/O error occurs.
     */
    public void addData(P data) throws IOException {
        data.setId(getNextDataId());
        List<P> allData = readData();
        allData.add(data);
        writeData(allData);
    }

    /**
     * Updates existing data.
     * @param updatedData The updated data.
     */
    public void updateData(P updatedData) {
        List<P> data = readData();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId() == updatedData.getId()) {
                data.set(i, updatedData);
                break;
            }
        }
        writeData(data);
    }

    /**
     * Deletes data by ID.
     * @param dataId The ID of the data to delete.
     */
    public void deleteProject(int dataId) {
        List<P> allData = readData();
        allData.removeIf(data -> data.getId() == dataId);
        writeData(allData);
    }

    /**
     * Retrieves data by ID.
     * @param dataId The ID of the data to retrieve.
     * @return An optional containing the data, if found.
     */
    public Optional<P> getDataById(int dataId) {
        List<P> allData = readData();
        return allData.stream().filter(data -> data.getId() == dataId).findFirst();
    }
}
