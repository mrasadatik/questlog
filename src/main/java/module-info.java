module com.zynotic.studios.quadsquad.questlog {
    requires javafx.controls;
    requires javafx.fxml;
    requires atlantafx.base;
    requires org.fxmisc.flowless;
    requires net.synedra.validatorfx;
    requires java.logging;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.material2;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires libphonenumber;
    requires java.datatransfer;
    requires java.desktop;
    requires batik.anim;
    requires batik.bridge;
    requires batik.util;
    requires org.apache.logging.log4j;
    requires xml.apis.ext;
    requires batik.css;
    requires jdk.xml.dom;
    requires batik.svg.dom;

    opens com.zynotic.studios.quadsquad.questlog to javafx.fxml;
    exports com.zynotic.studios.quadsquad.questlog;
    exports com.zynotic.studios.quadsquad.questlog.components;
    exports com.zynotic.studios.quadsquad.questlog.scene;
    // exports com.zynotic.studios.quadsquad.questlog.model;
    exports com.zynotic.studios.quadsquad.questlog.config;
    exports com.zynotic.studios.quadsquad.questlog.utils;
}
