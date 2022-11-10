package com.example.menstrualender.view;

import com.example.menstrualender.model.Cycles;
import com.example.menstrualender.model.Db;
import com.example.menstrualender.util.DateUtil;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import com.example.menstrualender.MensApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


public class MensController implements Initializable {
    //Declarations
    @FXML
    private Label kalenderAusgabe;
    @FXML
    private Label nextCycleStart;
    @FXML
    private Label averageInterval;
    @FXML
    private Label buttonConf;
    @FXML
    private DatePicker datePicker;

    @FXML
    private MensApplication mensApp;

    /*
    Braucht es dieses @ ?? und für was?
     */
    @FXML
    Db db = new Db();

    @FXML
    Cycles zyklus = new Cycles(db);



    /**
     * Constructor
     */
    public MensController() {
    }

    /**
     *Action Handling from login Screen
     */
    @FXML
    public void loginButton() {
        mensApp.showDefaultWindow();
    }

    @FXML
    public void loadData() {
        zyklus.readData();
    }

    /**
     * Takes the input from the datePicker and saves it in cycle
     */
    @FXML
    public void setDate() {
        try {
            LocalDate myDate = datePicker.getValue();
            myDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            zyklus.addDate(myDate);
            showButton("Cycle added", Color.GREEN);
        } catch (NullPointerException e) {

            showButton("Pick a Date", Color.RED);
        }
    }

    /**
     * Deletes the Data in the File
     * Naja, möchte vielleicht nicht immer alle Daten auf einmal löschen.
     * direkt auf Datenbank anpassen.
     */
    @FXML
    private void deleteData() {
        zyklus.deleteData();
        mensApp.showDefaultWindow();
    }

    /**
     * Makes Text appear and Disappear. Takes label String and color (true = red, false = green)
     * @param labelText is the output text
     * @param color
     */
    private void showButton(String labelText, Color color) {
        buttonConf.setTextFill(color);
        buttonConf.setText(labelText);
        FadeTransition fader = createFader(buttonConf);
        fader.play();
    }

    /**
     * sets fade Parameter
     * @param node
     * @return fade
     */
    private FadeTransition createFader(Node node) {
        FadeTransition fade = new FadeTransition(Duration.seconds(2), node);
        fade.setFromValue(100);
        fade.setToValue(0);

        return fade;
    }

    public void setMainApp(MensApplication mensApp) {
        this.mensApp = mensApp;
    }

    /**
     * saves Data into File
     */
    public void saveData() {

    }

//Output on Screen
    /**
     * Shows Average Interval and Next Cycle Start
     */
    @FXML
    public void showInfos(ActionEvent event) {
        printCalender();
        showAverageInterval();
    }

    /**
     * Changes Label averageInterval in hello-view to averageInterval from Cycles
     * returns Int averageInterval
     */
   public void showAverageInterval() {
       zyklus.getAverageInterval();
    }

    /**
     * Changes Label nextCycleStart in hello-view to nextCycleStart from Cycles
     * takes int "averageInterval"
     *//*
    public void showNextCycleStart() {
        nextCycleStart.setText(zyklus.calculateNextCycleStart());
    }

    *//**
     * Sets Text of Label kalenderAusgabe to the Calender Infos
     */
    public void printCalender() {
        String message = "Saved dates:\n\n";
        ResultSet rs = zyklus.getCycles();
        try {
            if(!rs.next()) { // false Check! rs.next() == false
                message += "None Found!\n\nHow to Add New Cycle:\n1. Choose Date\n2.\"Start new Cycle\"" +
                        "\n\nAdd at least two dates\nto calulate the start\nof the next cycle.";
            } else {
                do {
                    message += LocalDate.parse(rs.getString("cyc_date_start")).format(DateUtil.formatter) + "\n";
                } while (rs.next());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        kalenderAusgabe.setText(message);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * Opens default scene
     * 2 x verwendet, desshalb laden aller daten
     * @param event
     */
    public void switchToLogin(ActionEvent event) {
        // Warum heisst diese Funktion nicht Logout?
        // Warum wird hier beim Logout, alle Daten aufgerufen?
        mensApp.loginWindow();
        /*zyklus.readData();
        showAverageInterval();
        printCalender();
        showNextCycleStart();*/
    }
}