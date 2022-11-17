package com.example.menstrualender.view;

import com.example.menstrualender.MensApplication;
import com.example.menstrualender.model.Cycles;
import com.example.menstrualender.model.Db;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;


public class DailyController implements Initializable {
    @FXML
    private DatePicker dailyDatePicker;
    @FXML
    private ChoiceBox<String> chooseMood;
    @FXML
    private ChoiceBox<String> chooseSlime;
    @FXML
    private MensApplication mensApp;
    @FXML
    private TextField dailyTemperature;
    @FXML
    private Button dayReturn, saveButton;
    @FXML
    private TextField dailyComments;
    @FXML
    private ToggleButton bleeding;
    @FXML ToggleButton eisprung;

    private String[] outflow = {"Trocken","Pampig","Durchsichtig"};
    private String[] mood = {"ängstlich","gereizt","aufgestellt","niedergeschlagen","motiviert","lustlos","hoffnungsvoll"};

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
        chooseSlime.getItems().addAll(outflow);
        chooseMood.getItems().addAll(mood);
    }

    public void dailySave() {
        System.out.println("Funktion: Save");
        String outflowChoice = chooseSlime.getValue();
        String moodChoice = chooseMood.getValue();
        String dailyTemp = dailyTemperature.getText();
        LocalDate dailyDate = dailyDatePicker.getValue(); // für was ist das?
        String commentsDaily = dailyComments.getText();

        int outflowChoiceInt = Arrays.asList(outflow).indexOf(outflowChoice) + 1; //Trocken = 1, Pampig = 2, Durchsichtig = 3
        int moodChoiceInt = Arrays.asList(mood).indexOf(moodChoice) + 1;  //ängstlich = 1, gereizt = 2, aufgestellt = 3, niedergeschlagen = 4, motiviert = 5, lustlos = 6, hoffnungsvoll =7

        DecimalFormat df = new DecimalFormat("#.00");

        // Todo: AddBleeding
        // Todo: AddOvulation (Eisprung) Datum

        if(outflowChoiceInt != 0) {
            this.mensApp.zyklus.addOutflow(outflowChoiceInt);
        }
        if(moodChoiceInt != 0) {
            this.mensApp.zyklus.addMood(moodChoiceInt);
        }
        if (dailyTemp != "") {
            double dailyTempDouble = Double.parseDouble(dailyTemp);
            this.mensApp.zyklus.addTemp(String.valueOf(dailyTempDouble));
        }
        if(commentsDaily != "") {
            this.mensApp.zyklus.addComments(commentsDaily);
        }
       //ToDo @Julia @Mirko
        if (bleeding.isSelected()){
            mensApp.zyklus.addBleeding(1);
        }
        if (eisprung.isSelected()){
            mensApp.zyklus.addOvulation(dailyDatePicker.getValue());
        }


        System.out.println(outflowChoiceInt);
        System.out.println(moodChoiceInt);
        System.out.println("Ausgabe Temperatur, unverändert: " + dailyTemp);
        System.out.println(commentsDaily);
        System.out.println(dailyDate);

    }


    public void dailyReturn() {
        Stage dayStage = (Stage) dayReturn.getScene().getWindow();
        dayStage.close();
    }
}