module org.dpassignments.employeedata {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens org.dpassignments.employeedata to javafx.fxml;
    exports org.dpassignments.employeedata;
}