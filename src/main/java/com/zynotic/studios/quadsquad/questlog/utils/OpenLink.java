/*
 * OpenLink.java
 * Utility class for opening links in a web browser.
 * Created by Md Asaduzzaman Atik on Tuesday, May 21, 2024, 5:13:02 PM.
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

import java.awt.*;
//
import java.net.URI;

/**
 * Utility class for opening links in a web browser.
 */
public class OpenLink {

    /**
     * Opens the specified URL in the default web browser.
     * @param url The URL to open.
     * @throws Exception If an error occurs while opening the URL.
     */
    public static void openInBrowser(String url) throws Exception {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(URI.create(url));
        } else {
            System.out.println("Opening link in browser not supported.");
        }
    }
}
