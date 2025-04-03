package org.dpassignments.employeedata;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class ViewController {
    @FXML
    private Label welcomeText;

    @FXML
    private ComboBox languageSelector;

    @FXML
    private ListView languageDisplay;

    @FXML
    private TextArea key, translation;

    @FXML
    protected void buttonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}