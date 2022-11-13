package com.example.menstrualender.view;

import com.example.menstrualender.MensApplication;
import com.example.menstrualender.model.Cycles;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class MonthlyController implements Initializable {

    @FXML
    private Label buttonConf;
    @FXML
    private DatePicker datePicker;
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
    public MonthlyController() {
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
