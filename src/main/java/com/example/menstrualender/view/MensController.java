package com.example.menstrualender.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MensController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}