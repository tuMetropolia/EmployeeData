module org.dpassignments.employeedata {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires jakarta.persistence;


    opens org.dpassignments.employeedata to javafx.fxml;
    exports org.dpassignments.employeedata;
}