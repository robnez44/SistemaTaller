module com.example.sistemataller {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.sistemataller to javafx.fxml;
    exports com.example.sistemataller;
}