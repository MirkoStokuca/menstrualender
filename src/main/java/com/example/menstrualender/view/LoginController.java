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


    /**
     * Action Handling from login Screen
     */
    @FXML
    private MensApplication mensApp;
    @FXML
    private PasswordField passwordField;
    @FXML
    public Label plsSetPw;

    /**
     * Constructor
     */
    public LoginController() {
    }

    public void setMainApp(MensApplication mensApp) {
        this.mensApp = mensApp;
    }

    /**
     * Checks the Login Password
     */
    public void loginValidation() {
        String password = passwordField.getText();
        if (password == "") {
            password = null;
        }
        //if login is Wrong:
        if (this.mensApp.db.setUp(password)) {
            plsSetPw.setText("Login Erfolgreich!");
            plsSetPw.setTextFill(Color.GREEN);
            this.mensApp.showDefaultWindow();

        //if login is Right:
        } else {
            plsSetPw.setText("Falsches Passwort");
            plsSetPw.setTextFill(Color.RED);
        }
    }

    /**
     * On first start you can choose a password
     */
    public void displayLogin() {
        if (!Util.isDbExisting()) {
            plsSetPw.setTextFill(Color.RED);
            plsSetPw.setText("Wähle ein Passwort");
        } else
            plsSetPw.setText("Login!");
        plsSetPw.setTextFill(Color.GREEN);

    }

    /**
     * initializer
     * @param url
     * @param resourceBundle
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
