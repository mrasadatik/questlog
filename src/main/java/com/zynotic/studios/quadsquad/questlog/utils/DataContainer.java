/*
 * DataContainer.java
 * Utility class for holding data in a container.
 * Created by Md Asaduzzaman Atik on Thursday, May 23, 2024, 11:49:48 PM.
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

package com.zynotic.studios.quadsquad.questlog.utils;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
//
import java.io.Serial;
import java.io.Serializable;
//
import java.util.ArrayList;
import java.util.List;
//
import com.zynotic.studios.quadsquad.questlog.interfaces.DataIdentifier;

/**
 * Utility class for holding data in a container.
 * @param <P> The type of data identifier.
 */
@JsonInclude(JsonInclude.Include.ALWAYS)
public class DataContainer<P extends DataIdentifier> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("data") // JSON property name for the data list
    private List<P> data; // List to hold the data

    /**
     * Default constructor.
     */
    public DataContainer() {
        this.data = new ArrayList<>();
    }

    /**
     * Getter for the data list.
     * @return The data list.
     */
    @JsonGetter
    public List<P> getData() {
        return data;
    }

    /**
     * Setter for the data list.
     * @param data The data list to set.
     */
    @JsonSetter
    public void setData(List<P> data) {
        this.data = data;
    }
}
