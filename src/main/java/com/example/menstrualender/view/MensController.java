package com.example.menstrualender.view;

import com.example.menstrualender.MensApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MensController {
    @FXML
    private Label welcomeText;
    private MensApplication mensApp;

public MensController(){
}

    @FXML
    public void setMainApp(MensApplication mensApp) {

        this.mensApp = mensApp;
    }
}