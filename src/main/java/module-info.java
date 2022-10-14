module net.tavx.ipconverter {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens net.tavx.ipconverter to javafx.fxml;
    exports net.tavx.ipconverter;
}