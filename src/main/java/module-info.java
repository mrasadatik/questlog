module com.zynotic.studios.quadsquad.questlog {
    requires javafx.controls;
    requires javafx.fxml;
    requires atlantafx.base;
    requires org.fxmisc.flowless;
    requires net.synedra.validatorfx;

    opens com.zynotic.studios.quadsquad.questlog to javafx.fxml;
    exports com.zynotic.studios.quadsquad.questlog;
}
