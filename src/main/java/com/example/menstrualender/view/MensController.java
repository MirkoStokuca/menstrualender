package com.example.menstrualender.view;

import com.example.menstrualender.model.Cycles;
import javafx.animation.FadeTransition;
import javafx.concurrent.Task;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    @FXML
    Cycles zyklus = new Cycles();

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
            showButton("Cycle added", false);
        } catch (NullPointerException e) {

            showButton("Pick a Date", true);
        }
    }

    /**
     * Deletes the Data in the File
     */
    @FXML
    private void deleteData() {
        zyklus.deleteData();
    }

    /**
     * Makes Text appear and Disappear. Takes label String and color (true = red, false = green)
     * @param labelText
     * @param color
     */
    private void showButton(String labelText, boolean color) {
        if (color == true) {
            buttonConf.setTextFill(Color.RED);
        } else {
            buttonConf.setTextFill(Color.GREEN);
        }
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
        zyklus.saveData();
    }

//Output on Screen
    /**
     * Shows Average Interval and Next Cycle Start
     */
    @FXML
    public void showInfos(ActionEvent event) {

        showAverageInterval();
        printCalender();
        showNextCycleStart();
    }

    /**
     * Changes Label averageInterval in hello-view to averageInterval from Cycles
     * returns Int averageInterval
     */
    public void showAverageInterval() {

        int averInterval = zyklus.getAverageInterval();
        averageInterval.setText(Integer.toString(averInterval));
    }

    /**
     * Changes Label nextCycleStart in hello-view to nextCycleStart from Cycles
     * takes int "averageInterval"
     */
    public void showNextCycleStart() {

        LocalDate nCycleStart = zyklus.calculateNextCycleStart();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        String formattedString = nCycleStart.format(formatter);
        nextCycleStart.setText(formattedString);
    }

    /**
     * Sets Text of Label kalenderAusgabe to the Calender Infos
     */
    public void printCalender() {

        ArrayList<LocalDate> allInfosLocalDate = zyklus.getCycles();
        String allInfosRawString = allInfosLocalDate.toString();
        String[] allInfosString = allInfosRawString.split(",");

        //Add Newline
        String finalString = " ";
        for (int i = 0; i < allInfosString.length; i++) {

            finalString += "\n" + allInfosString[i];
        }

        //set Kalender Ausgabe
        kalenderAusgabe.setText(finalString.replace("[", "").replace("]", ""));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * Opens default scene
     *
     * @param event
     */
    public void switchToLogin(ActionEvent event) {
        mensApp.loginWindow();
        zyklus.readData();
        showAverageInterval();
        printCalender();
        showNextCycleStart();
    }
}