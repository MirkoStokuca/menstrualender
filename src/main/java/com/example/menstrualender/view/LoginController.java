package com.example.menstrualender.view;

import com.example.menstrualender.MensApplication;
import com.example.menstrualender.model.Cycles;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    //
    @FXML
    private MensApplication mensApp;
    @FXML
    Cycles zyklus = new Cycles();
    /**
     * Constructor
     */
    public LoginController() {
    }

    public void setMainApp(MensApplication mensApp) {
        this.mensApp = mensApp;
    }

    /**
     *Action Handling from login Screen
     */
    @FXML
    public void loginButton() {
        mensApp.showDefaultWindow();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
