module org.mouse.mousecameraapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;

    opens org.mouse.mousecameraapp to javafx.fxml;
    exports org.mouse.mousecameraapp;
    exports org.mouse.mousecameraapp.functions;
    opens org.mouse.mousecameraapp.functions to javafx.fxml;
}