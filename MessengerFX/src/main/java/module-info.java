module com.messengerfx.messengerfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.messengerfx.messengerfx to javafx.fxml;
    exports com.messengerfx.messengerfx;
}