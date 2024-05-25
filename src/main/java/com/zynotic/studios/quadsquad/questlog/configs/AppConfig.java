/*
 * AppConfig.java
 * Utility class for handling configuration-related tasks.
 * Handles loading application properties, displaying alerts, and managing exceptions.
 * Created by Md Asaduzzaman Atik on Saturday, May 18, 2024, 10:53:14 AM.
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

package com.zynotic.studios.quadsquad.questlog.configs;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
//
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;
//
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class for handling configuration-related tasks.
 * Handles loading application properties, displaying alerts, and managing exceptions.
 */
public class AppConfig {

    private static final String BUNDLE_NAME = "application";
    private static ResourceBundle properties;
    private static final Image errorIcon;

    private static final Logger logger = LogManager.getLogger(AppConfig.class);

    static {
        try {
            properties = ResourceBundle.getBundle(BUNDLE_NAME);
            errorIcon = new Image(Objects.requireNonNull(AppConfig.class.getResource("/assets/images/logo/questlog/icons/dark/icon16s.png")).toString()); // Load error icon
        } catch (MissingResourceException e) {
            handleException("Could not load resource bundle '" + BUNDLE_NAME + "'.", e);
            properties = null;
            throw new IllegalStateException("Properties not initialized");
        }
    }

    /**
     * Retrieves an application property by key.
     * @param key The key of the property.
     * @return The value of the property, or null if not found.
     */
    public static String getApplicationProperty(String key) {
        if (properties == null) {
            return null;
        }

        try {
            return properties.getString(key);
        } catch (MissingResourceException e) {
            logger.warn("Missing property: {}", key, e);
            return null;
        }
    }

    /**
     * Retrieves a required application property by key.
     * Throws an exception if the property is missing.
     * @param key The key of the property.
     * @return The value of the property.
     * @throws MissingPropertyException if the required property is missing.
     */
    public static String getRequiredApplicationProperty(String key) {
        String value = getApplicationProperty(key);
        if (value == null) {
            showAlert(
                    "Error: Missing Property",
                    "Property '" + key + "' not found in configuration.",
                    null
            );
            throw new MissingPropertyException("Required property '" + key + "' is missing.");
        }
        return value;
    }

    /**
     * Displays an alert dialog with the specified title, content, and optional icon.
     * @param title The title of the alert.
     * @param contentText The content text of the alert.
     * @param iconPath The path to the custom icon image, or null to use the default error icon.
     */
    private static void showAlert(String title, String contentText, String iconPath) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);

        if (errorIcon != null) {
            ImageView iconView = new ImageView(errorIcon);
            iconView.setFitHeight(48);
            iconView.setFitWidth(48);
            alert.getDialogPane().setGraphic(iconView);
        } else if (iconPath != null) {
            try {
                Image iconImage = new Image(Objects.requireNonNull(AppConfig.class.getResource(iconPath)).toString());
                ImageView iconView = new ImageView(iconImage);
                iconView.setFitHeight(48);
                iconView.setFitWidth(48);
                alert.getDialogPane().setGraphic(iconView);
            } catch (Exception e) {
                handleException("Error loading custom icon", e);
            }
        }

        alert.showAndWait();
    }

    /**
     * Handles an exception by logging it.
     * @param message The message to log.
     * @param e The exception to handle.
     */
    private static void handleException(String message, Exception e) {
        logger.error(message, e);
    }

    /**
     * Custom exception class for missing application properties.
     */
    public static class MissingPropertyException extends RuntimeException {
        public MissingPropertyException(String message) {
            super(message);
        }
    }
}
