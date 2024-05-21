package com.zynotic.studios.quadsquad.questlog.utils;

import java.awt.*;
import java.net.URI;

public class OpenLink {
    public static void openInBrowser(String url) throws Exception {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(URI.create(url));
        } else {
            System.out.println("Opening link in browser not supported.");
        }
    }
}
