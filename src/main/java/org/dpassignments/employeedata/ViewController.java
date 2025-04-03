package org.dpassignments.employeedata;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewController {
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/SOFTWARE2";
    private static final String DB_USER = "appuser";
    private static final String DB_PASSWORD = "app_password";

    private ObservableList<String> languages = FXCollections.observableArrayList("English", "Española", "Français", "中文");

    @FXML
    private Label welcomeText;

    @FXML
    private ComboBox languageSelector;

    @FXML
    private ListView languageDisplay;

    @FXML
    private TextArea key, translation;

    @FXML
    private Button addButton;

    @FXML
    public void initialize() {
        languageSelector.getItems().addAll(languages);
        languageSelector.setValue(languages.get(0));
        languageSelector.setOnAction(event -> changeLanguage());
        languageDisplay.getItems().clear();
        fetchLocalizedTranslations("en");
    }

    @FXML
    protected void buttonClick() {
        String selectedLanguage = (String) languageSelector.getValue();
        String languageCode = getLanguageCode(selectedLanguage);
        String keyText = key.getText();
        String translationText = translation.getText();

        if (keyText.isEmpty() || translationText.isEmpty()) {
            System.err.println("Key and translation cannot be empty.");
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sqlInsert = "INSERT INTO translations (Key_name, translation_text, Language_code) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlInsert);
            preparedStatement.setString(1, keyText);
            preparedStatement.setString(2, translationText);
            preparedStatement.setString(3, languageCode);
            preparedStatement.executeUpdate();
            System.out.println("Translation added successfully.");
            fetchLocalizedTranslations(languageCode);
            key.clear();
            translation.clear();
        } catch (Exception e) {
            System.err.println("Error saving translation: " + e.getMessage());
            e.printStackTrace();
        }

    }

    private void changeLanguage() {
        String selectedLanguage = (String) languageSelector.getValue();
        String languageCode = getLanguageCode(selectedLanguage);
        fetchLocalizedTranslations(languageCode);
    }

    private String getLanguageCode(String language) {
        switch (language) {
            case "English" -> {
                return  "en";
            }
            case "Española" ->
            {
                return "es";
            }
            case "Français" -> {
                return "fr";
            }
            case "中文" -> {
                return "zh";
            }
            default -> {
                return "en"; // Default to English if no match
            }
        }
    }

    private void fetchLocalizedTranslations(String languageCode) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sqlQuery = "SELECT Key_name, translation_text FROM translations WHERE Language_code = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, languageCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            languageDisplay.getItems().clear();
            while (resultSet.next()) {
                String key = resultSet.getString("Key_name");
                String translationText = resultSet.getString("translation_text");
                languageDisplay.getItems().add(key + ": " + translationText);
            }
        } catch (Exception e) {
            System.err.println("Error fetching translations: " + e.getMessage());
            e.printStackTrace();
        }
    }
}