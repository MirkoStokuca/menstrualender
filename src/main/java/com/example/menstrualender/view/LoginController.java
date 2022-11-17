package com.example.menstrualender.view;

import com.example.menstrualender.MensApplication;
import com.example.menstrualender.model.db.Util;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {


    @FXML
    private MensApplication mensApp;

    /**
     * Constructor
     */
    public LoginController() {
    }

    public void setMainApp(MensApplication mensApp) {
        this.mensApp = mensApp;
    }

    /**
     * Action Handling from login Screen
     */
    @FXML
    private PasswordField passwordField;
    @FXML
    public Label plsSetPw;

    public void loginValidation() {
        String password = passwordField.getText();
        if (password == "") {
            password = null;
        }
            if (this.mensApp.db.setUp(password)) {
                plsSetPw.setText("Login succesfull!");
                plsSetPw.setTextFill(Color.GREEN);
                this.mensApp.showDefaultWindow();
            } else {
                plsSetPw.setText("Wrong Password");
                plsSetPw.setTextFill(Color.RED);
            }
    }

    public void displayLogin() {
        if (!Util.isDbExisting()) {
            plsSetPw.setTextFill(Color.RED);
            plsSetPw.setText("Please choose a password");
        } else
            plsSetPw.setText("Login!");
        plsSetPw.setTextFill(Color.GREEN);

    }

    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
