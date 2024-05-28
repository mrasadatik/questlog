/*
 * DataIdentifier.java
 * Interface defining methods for objects with varying unique identifiers.
 * Created by Md Asaduzzaman Atik on Saturday, May 25, 2024, 3:07:18 PM.
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

package com.zynotic.studios.quadsquad.questlog.interfaces;

import java.util.List;

/**
 * Interface defining methods for objects with varying unique identifiers.
 * Classes implementing this interface must provide methods to get and set the identifier.
 */
public interface DataIdentifier {

    /**
     * Retrieves the unique identifier of the object.
     *
     * @return The unique identifier of the object.
     */
    int getId();

    /**
     * Sets the unique identifier of the object.
     *
     * @param id The unique identifier to set.
     */
    void setId(int id);

    /**
     * Sets or Gets unique keys associated with the user.
     *
     * @return The list of unique keys.
     */
    default List<String> uniqueKeys() {
        return List.of(); // Return an empty list by default
    }
}
