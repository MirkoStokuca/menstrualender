package com.example.menstrualender.view;

import com.example.menstrualender.MensApplication;
import com.example.menstrualender.model.Cycles;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class DailyController implements Initializable {
    @FXML
    private MensApplication mensApp;
    @FXML
    Cycles zyklus = new Cycles();
    public void setMainApp(MensApplication mensApp) {
        this.mensApp = mensApp;
    }

    /**
     * Constructor
     */
    public DailyController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
