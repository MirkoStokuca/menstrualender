package com.example.menstrualender.view;

import com.example.menstrualender.model.Cycles;
import com.example.menstrualender.util.DateUtil;
import javafx.animation.FadeTransition;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import com.example.menstrualender.MensApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;



public class MensController implements Initializable {
    //Deklarationen
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

    public MensController() {
    }
    @FXML
    public void loginButton() {
        mensApp.showDefaultWindow();
    }
    @FXML
    public void loadData() {
        zyklus.readData();
    }

    @FXML
    public void setDate() {
        try {
            LocalDate myDate = datePicker.getValue();
            myDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            zyklus.addDate(myDate);
            showButton("Cycle added",false);
        }
        catch(NullPointerException e) {

            showButton("Pick a Date",true);
        }



    }

    @FXML
    private void deleteData(){
        zyklus.deleteData();
    }

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
    private FadeTransition createFader(Node node){
        FadeTransition fade = new FadeTransition(Duration.seconds(2), node);
        fade.setFromValue(100);
        fade.setToValue(0);

        return fade;
    }
    public void setMainApp(MensApplication mensApp) {
        this.mensApp = mensApp;
    }

    public void saveData(){
        zyklus.saveData();
    }

//Output on Screen
    @FXML
    public void showInfos(ActionEvent event) {
        /**
         * Shows Average Interval and Next Cycle Start
         */
        showAverageInterval();
        printCalender();
        showNextCycleStart();
    }
    public void showAverageInterval() {
        /**
         * Changes Label averageInterval in hello-view to averageInterval from Cycles
         * returns Int averageInterval
         */
        int averInterval = zyklus.getAverageInterval();
        averageInterval.setText(Integer.toString(averInterval) + " days");
    }

    public void showNextCycleStart() {
        /**
         * Changes Label nextCycleStart in hello-view to nextCycleStart from Cycles
         * takes int "averageInterval"
         */
        LocalDate nCycleStart = zyklus.calculateNextCycleStart();
        String formattedString = nCycleStart.format(DateUtil.formatter);
        nextCycleStart.setText(formattedString);
    }

    public void printCalender() {
        /**
         * Sets Text of Label kalenderAusgabe to the Calender Infos
         */
        String message = "Previously saved dates:\n\n";
        var tempArray = zyklus.getCycles();
        for (int i = 0; i < (tempArray.size() - 0); i++) {
            message += tempArray.get(i).format(DateUtil.formatter) + "\n";
        }

        kalenderAusgabe.setText(message);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void switchToLogin(ActionEvent event) {
        mensApp.loginWindow();
        zyklus.readData();
        showAverageInterval();
        printCalender();
        showNextCycleStart();
    }
}